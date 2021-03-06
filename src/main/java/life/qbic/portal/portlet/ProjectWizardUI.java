package life.qbic.portal.portlet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletContext;
import javax.portlet.PortletSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.vaadin.teemu.wizards.Wizard;
import org.vaadin.teemu.wizards.event.WizardCancelledEvent;
import org.vaadin.teemu.wizards.event.WizardCompletedEvent;
import org.vaadin.teemu.wizards.event.WizardProgressListener;
import org.vaadin.teemu.wizards.event.WizardStepActivationEvent;
import org.vaadin.teemu.wizards.event.WizardStepSetChangedEvent;

import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.WrappedPortletSession;
import com.vaadin.ui.themes.ValoTheme;

import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

import life.qbic.datamodel.attachments.AttachmentConfig;
import life.qbic.openbis.openbisclient.IOpenBisClient;
import life.qbic.openbis.openbisclient.OpenBisClient;
import life.qbic.openbis.openbisclient.OpenBisClientMock;
import life.qbic.portal.portlet.QBiCPortletUI;
import life.qbic.portal.utils.PortalUtils;
import life.qbic.projectwizard.control.ExperimentImportController;
import life.qbic.projectwizard.control.WizardController;
import life.qbic.projectwizard.io.ConfigurationManager;
import life.qbic.projectwizard.io.ConfigurationManagerFactory;
import life.qbic.projectwizard.io.DBConfig;
import life.qbic.projectwizard.io.DBManager;
import life.qbic.projectwizard.io.DBVocabularies;
import life.qbic.projectwizard.registration.OpenbisCreationController;
import life.qbic.projectwizard.views.AdminView;
import life.qbic.projectwizard.views.MetadataUploadView;

@Theme("mytheme")
@SuppressWarnings("serial")
@Widgetset("life.qbic.portlet.AppWidgetSet")
public class ProjectWizardUI extends QBiCPortletUI {

  public static boolean testMode = false;// TODO
  public static boolean development = false;
  public static String MSLabelingMethods;
  public static String tmpFolder;

  // hardcoded stuff (main experiment types used in the wizard)
  List<String> expTypes = new ArrayList<String>(
      Arrays.asList("Q_EXPERIMENTAL_DESIGN", "Q_SAMPLE_EXTRACTION", "Q_SAMPLE_PREPARATION"));


  private Logger logger = LogManager.getLogger(ProjectWizardUI.class);

  private ConfigurationManager config;

  private IOpenBisClient openbis;

  private final TabSheet tabs = new TabSheet();
  private boolean isAdmin = false;

  @Override
  protected Layout getPortletContent(final VaadinRequest request) {
    tabs.addStyleName(ValoTheme.TABSHEET_FRAMED);
    final VerticalLayout layout = new VerticalLayout();
    
    // read in the configuration file
    config = ConfigurationManagerFactory.getInstance();
    tmpFolder = config.getTmpFolder();
    MSLabelingMethods = config.getVocabularyMSLabeling();

    layout.setMargin(true);
    setContent(layout);
    String userID = "";
    boolean success = true;
    if (PortalUtils.isLiferayPortlet()) {
      logger.info("Wizard is running on Liferay and user is logged in.");
      userID = PortalUtils.getUser().getScreenName();
    } else {
      if (development) {
        logger.warn("Checks for local dev version successful. User is granted admin status.");
        userID = "admin";
        isAdmin = true;
      } else {
        success = false;
        logger.info(
            "User \"" + userID + "\" not found. Probably running on Liferay and not logged in.");
        layout.addComponent(new Label("User not found. Are you logged in?"));
      }
    }
    // establish connection to the OpenBIS API
    if (!development || !testMode) {
      try {
        logger.debug("trying to connect to openbis");
        this.openbis = new OpenBisClient(config.getDataSourceUser(), config.getDataSourcePassword(),
            config.getDataSourceUrl());
        this.openbis.login();
      } catch (Exception e) {
        success = false;
        logger.error(
            "User \"" + userID + "\" could not connect to openBIS and has been informed of this.");
        layout.addComponent(new Label(
            "Data Management System could not be reached. Please try again later or contact us."));
      }
    }
    if (development && testMode) {
      logger.error("No connection to openBIS. Trying mock version for testing.");
      this.openbis = new OpenBisClientMock(config.getDataSourceUser(),
          config.getDataSourcePassword(), config.getDataSourceUrl());
      layout.addComponent(new Label(
          "openBIS could not be reached. Resuming with mock version. Some options might be non-functional. Reload to retry."));
    }
    if (success) {
      // stuff from openbis
      Map<String, String> matrixMap = openbis.getVocabCodesAndLabelsForVocab("Q_CFH_MATRIX");
      Map<String, String> taxMap = openbis.getVocabCodesAndLabelsForVocab("Q_NCBI_TAXONOMY");
      Map<String, String> tissueMap = openbis.getVocabCodesAndLabelsForVocab("Q_PRIMARY_TISSUES");
      Map<String, String> deviceMap = openbis.getVocabCodesAndLabelsForVocab("Q_MS_DEVICES");
      Map<String, String> cellLinesMap = openbis.getVocabCodesAndLabelsForVocab("Q_CELL_LINES");
      Map<String, String> enzymeMap =
          openbis.getVocabCodesAndLabelsForVocab("Q_DIGESTION_PROTEASES");
      Map<String, String> chromTypes =
          openbis.getVocabCodesAndLabelsForVocab("Q_CHROMATOGRAPHY_TYPES");
      List<String> sampleTypes = openbis.getVocabCodesForVocab("Q_SAMPLE_TYPES");
      Map<String, String> purificationMethods =
          openbis.getVocabCodesAndLabelsForVocab("Q_PROTEIN_PURIFICATION_METHODS");
      List<String> fractionationTypes =
          openbis.getVocabCodesForVocab("Q_MS_FRACTIONATION_PROTOCOLS");
      List<String> enrichmentTypes = openbis.getVocabCodesForVocab("Q_MS_ENRICHMENT_PROTOCOLS");
      Map<String, String> antibodiesWithLabels =
          openbis.getVocabCodesAndLabelsForVocab("Q_ANTIBODY");
      List<String> msProtocols = openbis.getVocabCodesForVocab("Q_MS_PROTOCOLS");
      List<String> lcmsMethods = openbis.getVocabCodesForVocab("Q_MS_LCMS_METHODS");
       List<String> extractions = openbis.getVocabCodesForVocab("Q_CFH_EXTRACTIONS");
      List<String> devices = openbis.getVocabCodesForVocab("Q_CFH_DEVICES");
      List<String> soildepth = openbis.getVocabCodesForVocab("Q_CFH_SOILDEPTH");
      List<String> nmrTypes = openbis.getVocabCodesForVocab("Q_CFH_NMR_EXP_TYPES");
      List<String> solvents = openbis.getVocabCodesForVocab("Q_CFH_NMR_SOLVENT");
      List<String> storage = openbis.getVocabCodesForVocab("Q_CFH_NMR_STORAGE");
      List<String> quantitation = openbis.getVocabCodesForVocab("Q_CFH_NMR_QUANTITATION");
      List<String> probes = openbis.getVocabCodesForVocab("Q_CFH_NMR_PROBE");
      List<String> spectrometers = openbis.getVocabCodesForVocab("Q_CFH_NMR_SPECTROMETER");
      
      final List<String> spaces = openbis.getUserSpaces(userID);
      isAdmin = openbis.isUserAdmin(userID);
      // stuff from mysql database
      DBConfig mysqlConfig = new DBConfig(config.getMysqlHost(), config.getMysqlPort(),
          config.getMysqlDB(), config.getMysqlUser(), config.getMysqlPass());
      DBManager dbm = new DBManager(mysqlConfig);
      Map<String, Integer> peopleMap = dbm.fetchPeople();
      DBVocabularies vocabs = new DBVocabularies(matrixMap,taxMap, tissueMap, cellLinesMap, sampleTypes,
          spaces, peopleMap, expTypes, enzymeMap, antibodiesWithLabels, deviceMap, msProtocols,
          lcmsMethods, chromTypes, fractionationTypes, enrichmentTypes, purificationMethods, extractions, devices, soildepth, nmrTypes, 
          solvents, storage, quantitation, probes, spectrometers);
      // initialize the View with sample types, spaces and the dictionaries of tissues and species
      initView(dbm, vocabs, userID);
      layout.addComponent(tabs);
    }
    return layout;
  }

  private void initView(final DBManager dbm, final DBVocabularies vocabularies, final String user) {
    tabs.removeAllComponents();
    AttachmentConfig attachConfig =
        new AttachmentConfig(Integer.parseInt(config.getAttachmentMaxSize()),
            config.getAttachmentURI(), config.getAttachmentUser(), config.getAttachmenPassword());
    WizardController c = new WizardController(openbis, dbm, vocabularies, attachConfig);
    c.init(user);
    Wizard w = c.getWizard();
    WizardProgressListener wl = new WizardProgressListener() {

      @Override
      public void activeStepChanged(WizardStepActivationEvent event) {}

      @Override
      public void stepSetChanged(WizardStepSetChangedEvent event) {}

      @Override
      public void wizardCompleted(WizardCompletedEvent event) {
        vocabularies.setPeople(dbm.fetchPeople());
        vocabularies.setSpaces(openbis.getUserSpaces(user));
        initView(dbm, vocabularies, user);
      }

      @Override
      public void wizardCancelled(WizardCancelledEvent event) {
        vocabularies.setPeople(dbm.fetchPeople());
        vocabularies.setSpaces(openbis.getUserSpaces(user));
        initView(dbm, vocabularies, user);
      }

    };
    w.addListener(wl);
    VerticalLayout wLayout = new VerticalLayout();
    wLayout.addComponent(w);
    wLayout.setMargin(true);

    tabs.addTab(wLayout, "Create Project").setIcon(FontAwesome.FLASK);

    OpenbisCreationController creationController = new OpenbisCreationController(openbis);// will
                                                                                          // not
                                                                                          // work
                                                                                          // when
                                                                                          // openbis
                                                                                          // is down

    ExperimentImportController uc =
        new ExperimentImportController(creationController, vocabularies, openbis, dbm);
    uc.init(user);
    tabs.addTab(uc.getView(), "Import Project").setIcon(FontAwesome.FILE);

    boolean overwriteAllowed = isAdmin || canOverwrite();
    tabs.addTab(new MetadataUploadView(openbis, vocabularies, overwriteAllowed), "Update Metadata")
        .setIcon(FontAwesome.PENCIL);;
    if (isAdmin) {
      logger.info("User is " + user + " and can see admin panel.");
      VerticalLayout padding = new VerticalLayout();
      padding.setMargin(true);
      padding.addComponent(new AdminView(openbis, vocabularies, creationController, user));
      tabs.addTab(padding, "Admin Functions").setIcon(FontAwesome.WRENCH);
    }
    if (overwriteAllowed)
      logger.info("User can overwrite existing metadata for their project.");
  }

  // TODO group that might be used to delete metadata or even sample/experiment objects in the
  // future
  private boolean canDelete() {
    try {
      User user = PortalUtils.getUser();
      for (UserGroup grp : user.getUserGroups()) {
        String group = grp.getName();
        if (config.getDeletionGrp().contains(group)) {
          logger.info(
              "User " + user.getScreenName() + " can delete because they are part of " + group);
          return true;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("Could not fetch user groups. User won't be able to delete.");
    }
    return false;
  }

  private boolean canOverwrite() {
    try {
      User user = PortalUtils.getUser();
      for (UserGroup grp : user.getUserGroups()) {
        String group = grp.getName();
        if (config.getMetadataWriteGrp().contains(group)) {
          logger.info("User " + user.getScreenName()
              + " can overwrite metadata because they are part of " + group);
          return true;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("Could not fetch user groups. User won't be able to overwrite metadata.");
    }
    return false;
  }

  private String getPortletContextName(VaadinRequest request) {
    WrappedPortletSession wrappedPortletSession =
        (WrappedPortletSession) request.getWrappedSession();
    PortletSession portletSession = wrappedPortletSession.getPortletSession();

    final PortletContext context = portletSession.getPortletContext();
    final String portletContextName = context.getPortletContextName();
    return portletContextName;
  }
}
