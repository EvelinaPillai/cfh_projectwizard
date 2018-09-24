/*******************************************************************************
 * QBiC Project Wizard enables users to create hierarchical experiments including different study
 * conditions using factorial design. Copyright (C) "2016" Andreas Friedrich
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program. If
 * not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package life.qbic.projectwizard.steps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.vaadin.teemu.wizards.Wizard;
import org.vaadin.teemu.wizards.WizardStep;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Button.ClickListener;

import life.qbic.datamodel.samples.AOpenbisSample;
import life.qbic.portal.Styles;
import life.qbic.projectwizard.control.WizardController.Steps;
import life.qbic.projectwizard.io.DBVocabularies;
import life.qbic.projectwizard.model.MHCLigandExtractionProtocol;
import life.qbic.projectwizard.model.RegisteredAnalyteInformation;
import life.qbic.projectwizard.model.TestSampleInformation;
import life.qbic.projectwizard.uicomponents.ElementPanel;
import life.qbic.projectwizard.uicomponents.AminoAcidPanel;
import life.qbic.projectwizard.uicomponents.NminPanel;
import life.qbic.projectwizard.uicomponents.SmallMoleculePanel;
import life.qbic.projectwizard.uicomponents.FatPanel;
import life.qbic.projectwizard.uicomponents.LigandExtractPanel;
import life.qbic.projectwizard.uicomponents.MSOptionComponent;
import life.qbic.projectwizard.uicomponents.TechnologiesPanel;
import life.qbic.portal.Styles.NotificationType;

import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.VerticalLayout;

/**
 * Wizard Step to put in information about the Sample Preparation that leads to
 * a list of Test Samples
 * 
 * @author Andreas Friedrich
 * 
 */
public class TestStep implements WizardStep {

	private VerticalLayout main;
	private TechnologiesPanel techPanel;
	private MSOptionComponent msPanel;
	private LigandExtractPanel mhcLigandPanel;
	private ElementPanel elementPanel;
	private AminoAcidPanel AminoAcidPanel;
	private NminPanel NminPanel;
	private FatPanel FatPanel;
	private SmallMoleculePanel smallMoleculesPanel;
	private CheckBox noMeasure;
	private DBVocabularies vocabs;
	private boolean containsProteins = false;
	private boolean containsMHCLigands = false;
	private boolean containsMatrix = false;
	private boolean containsElement = false;
	private boolean containsNmin = false;
	private boolean containsFat = false;
	private boolean containsAA = false;
	private boolean containsSmallMolecules = false;

//	private static String sampleName = "";

	private Wizard wizard;

	/**
	 * Create a new Sample Preparation step for the wizard
	 * 
	 * @param sampleTypes Available list of sample types, e.g. Proteins, RNA etc.
	 */
	public TestStep(Wizard wizard, DBVocabularies vocabs) {
		this.wizard = wizard;
		this.vocabs = vocabs;
		main = new VerticalLayout();
		main.setMargin(true);
		main.setSpacing(true);
		main.setSizeUndefined();
		Label header = new Label("Analysis Method");
		main.addComponent(Styles.questionize(header,
				"Here you can specify what kind of material is extracted from the samples for measurement, how many measurements (techn. replicates) are taken per sample "
						+ "and if there is pooling for some or all of the technologies used.",
				"Analysis Method"));
		noMeasure = new CheckBox("No further preparation of samples?");
		main.addComponent(Styles.questionize(noMeasure,
				"Check if no DNA etc. is extracted and measured, for example in tissue imaging.",
				"No Sample Preparation"));

		noMeasure.addValueChangeListener(new ValueChangeListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 2393762547426343668L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				changeTechPanel();
			}
		});
	}

	@Override
	public String getCaption() {
		return "Analysis Method";
	}

	public CheckBox getNotMeasured() {
		return noMeasure;
	}

	public void changeTechPanel() {
		if (noMeasure.getValue())
			techPanel.resetInputs();
		techPanel.setEnabled(!noMeasure.getValue());
	}

	@Override
	public Component getContent() {
		return main;
	}

	@Override
	public boolean onAdvance() {
		if (techPanel.isValid() || noMeasure.getValue()) {
			if (containsProteins) {
				return (msPanel.isValid());
			}
			if(containsNmin) {
				return (NminPanel.isValid());
			}
			if(containsElement) {
				return (elementPanel.isValid());
			}
			if(containsAA) {
				return (AminoAcidPanel.isValid());
			}
			else
				return true;
		} else {
			Styles.notification("Missing information",
					"Please input at least one analyte and the number of replicates.", NotificationType.ERROR);
			return false;
		}
	}

	private void replaceWizardSteps(List<WizardStep> nextMSSteps) {
		resetNextSteps(hasPools());
		for (WizardStep step : nextMSSteps)
			wizard.addStep(step);
	}

	@Override
	public boolean onBack() {
		return true;
	}

	public List<TestSampleInformation> getAnalyteInformation() {
		return techPanel.getTechInfo();
	}

	public boolean hasProteins() {
		return containsProteins;
	}

	public boolean hasPools() {
		return techPanel.poolingSet();
	}

	public boolean hasElement() {
		return containsElement;
	}

	public boolean hasAA() {
		return containsAA;
	}

	public boolean hasFat() {
		return containsFat;
	}

	public boolean hasNmin() {
		return containsNmin;
	}

	public void initTestStep(ValueChangeListener testPoolListener, ValueChangeListener outerProteinListener,
			ClickListener refreshPeopleListener, Map<Steps, WizardStep> steps) {

		ValueChangeListener proteinListener = new ValueChangeListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -2885684670741817840L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				containsProteins = false;
				for (TestSampleInformation i : getAnalyteInformation()) {
					String tech = i.getTechnology();
					containsProteins |= tech.equals("PROTEINS");
				}
				msPanel.setVisible(containsProteins);
				if (!containsProteins) {
					resetNextSteps(hasPools());
					wizard.addStep(steps.get(Steps.Registration));
				} else {
					replaceWizardSteps(msPanel.getNextMSSteps(steps, 1));
				}
			}
		};

		ValueChangeListener mhcLigandListener = new ValueChangeListener() {

			/**
			   * 
			   */
			private static final long serialVersionUID = -2883346670741817840L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				containsMHCLigands = false;
				for (TestSampleInformation i : getAnalyteInformation()) {
					containsMHCLigands |= i.getTechnology().equals("CELL_LYSATE");
				}
				mhcLigandPanel.setVisible(containsMHCLigands);
			}
		};

	
		ValueChangeListener elementListener = new ValueChangeListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -4496972150185281578L;

			/**
			 * 
			 */

			@Override
			public void valueChange(ValueChangeEvent event) {
				containsElement = false;
				for (TestSampleInformation i : getAnalyteInformation()) {
					String cfh = i.getTechnology();
					containsElement |= cfh.equals("ELEMENT");
				}
				elementPanel.setVisible(containsElement);
			}
		};
		
		ValueChangeListener aaListener = new ValueChangeListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -2923460324752380814L;

			/**
			 * 
			 */

			@Override
			public void valueChange(ValueChangeEvent event) {
				containsAA = false;
				for (TestSampleInformation i : getAnalyteInformation()) {
					String cfh = i.getTechnology();
					containsAA |= cfh.equals("AMINOACID");
				}
				AminoAcidPanel.setVisible(containsAA);

			}
		};

		ValueChangeListener fatListener = new ValueChangeListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1795380934847003436L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				containsFat = false;
				for (TestSampleInformation i : getAnalyteInformation()) {
					String cfh = i.getTechnology();
					containsFat |= cfh.equals("FAT");
				}
				FatPanel.setVisible(containsFat);

			}
		
		};

		ValueChangeListener nminListener = new ValueChangeListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 7465051158010140212L;

			/**
			 * 
			 */

			@Override
			public void valueChange(ValueChangeEvent event) {
				containsNmin = false;
				for (TestSampleInformation i : getAnalyteInformation()) {
					String cfh = i.getTechnology();
					containsNmin |= cfh.equals("NMIN");
//					sampleName = i.getSampleName();
				}
				NminPanel.setVisible(containsNmin);
			}
		};
		
		ValueChangeListener smallMoleculesListener = new ValueChangeListener() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 7510797241217956538L;
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				containsSmallMolecules = false;
				for (TestSampleInformation i : getAnalyteInformation()) {
					String cfh = i.getTechnology();
					containsSmallMolecules |= cfh.equals("SMALLMOLECULES");
				}
				smallMoleculesPanel.setVisible(containsSmallMolecules);
			}
		};

		techPanel = new TechnologiesPanel(vocabs.getAnalyteTypes(), vocabs.getPeople().keySet(), new OptionGroup(""),
				testPoolListener,
				new ArrayList<ValueChangeListener>(Arrays.asList(outerProteinListener, proteinListener)),
				mhcLigandListener, refreshPeopleListener, elementListener, aaListener, fatListener, nminListener,smallMoleculesListener,
				vocabs.getMatrixMap());

		main.addComponent(techPanel);
		main.addComponent(new Label("<hr />", Label.CONTENT_XHTML));
		msPanel = new MSOptionComponent(vocabs);
		msPanel.setVisible(false);
		ValueChangeListener msExpChangedListener = new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				replaceWizardSteps(msPanel.getNextMSSteps(steps, 1));
			}
		};

		msPanel.addMSListener(msExpChangedListener);

		main.addComponent(msPanel);

		mhcLigandPanel = new LigandExtractPanel(vocabs);
		mhcLigandPanel.setVisible(false);

		main.addComponent(mhcLigandPanel);
		
		AminoAcidPanel = new AminoAcidPanel(vocabs);
		AminoAcidPanel.setVisible(false);
		
		elementPanel = new ElementPanel(vocabs);
		elementPanel.setVisible(false);
		
		NminPanel = new NminPanel(vocabs, getAnalyteInformation()); 
		NminPanel.setVisible(false);
		FatPanel = new FatPanel(vocabs);
		FatPanel.setVisible(false);

		smallMoleculesPanel = new SmallMoleculePanel();
		smallMoleculesPanel.setVisible(false);
		
		main.addComponent(elementPanel);
		main.addComponent(NminPanel);
		main.addComponent(FatPanel);
		main.addComponent(AminoAcidPanel);
		main.addComponent(smallMoleculesPanel);
	}

	public void setTissueExtracts(List<AOpenbisSample> extracts) {
		mhcLigandPanel.setTissueSamples(extracts);
	}
	
	public void setNminExtracts(List<AOpenbisSample> extracts) {
		NminPanel.setNminSamples(extracts);
	}

	public Map<String, MHCLigandExtractionProtocol> getAntibodyInfos() {
		return mhcLigandPanel.getAntibodyInfos();
	}

	public Map<String, Map<String, Object>> getMHCLigandExtractProperties() {
		if (containsMHCLigands)
			return mhcLigandPanel.getExperimentalProperties();
		else
			return null;
	}

	public boolean hasMHCLigands() {
		return containsMHCLigands;
	}

	public Map<String, Object> getProteinPreparationInformation() {
		Map<String, Object> res = new HashMap<String, Object>();
		if (msPanel.usesPurification())
			res.put("Q_MS_PURIFICATION_METHOD", msPanel.getPurificationMethod());
		if (msPanel.usesShortGel())
			res.put("Q_ADDITIONAL_INFO", "Short Gel");
		return res;
	}

	private void resetNextSteps(boolean pool) {
		List<WizardStep> steps = wizard.getSteps();
		List<WizardStep> copy = new ArrayList<WizardStep>();
		copy.addAll(steps);
		boolean isNew = false;
		for (int i = 0; i < copy.size(); i++) {
			WizardStep cur = copy.get(i);
			boolean keep = pool && cur instanceof PoolingStep && !hasProteins();
			if (isNew && !keep) {
				wizard.removeStep(cur);
			}
			if (cur.equals(this))
				isNew = true;
		}
	}

	public boolean hasMatrix() {
		return containsMatrix;
	}

	public boolean hasComplexProteinPoolBeforeFractionation() {
		return msPanel.hasProteinPoolBeforeFractionation();
	}

	public void setAnalyteInputs(RegisteredAnalyteInformation infos) {
		for (String analyte : infos.getAnalytes()) {
			techPanel.select(analyte);
		}
		msPanel.selectMeasurePeptides(infos.isMeasurePeptides());
		msPanel.selectUseShortGel(infos.isShortGel());
		msPanel.selectProteinPurification(infos.getPurificationMethod());
	}

	public void updatePeople(Set<String> people) {
		techPanel.updatePeople(people);
	}
	
//	public static String getSampleName() {
//		return sampleName;
//	}

	public List<Map<String,String>> getElementPanel()
	{
		if(containsElement)
		{
			return elementPanel.getElementProperties();
		}
		
		return null;
		
	}
	
	public List<Map<String,String>> getNminPanel()
	{
		if(containsNmin)
		{
			return NminPanel.getNminProperties();
		}
		
		return null;
		
	}
	
	public Map<String,Object> getSmallMoleculesPanel()
	{
		if(containsSmallMolecules)
		{
			return smallMoleculesPanel.getSmallMoleculesProperties();
		}
		
		return null;
		
	}
	

}