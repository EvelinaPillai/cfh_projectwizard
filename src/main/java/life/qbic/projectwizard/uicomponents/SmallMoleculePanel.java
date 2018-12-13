package life.qbic.projectwizard.uicomponents;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.vaadin.teemu.wizards.WizardStep;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.VerticalLayout;
import life.qbic.portal.Styles;
import life.qbic.projectwizard.control.WizardController.Steps;
import life.qbic.projectwizard.io.DBVocabularies;
import life.qbic.projectwizard.steps.MSAnalyteStep;

/**
 * Core Facility Hohenheim (CFH) - For SmallMolecules we want the same as for Proteins
 * but less MSOptions  
 * 
 */

public class SmallMoleculePanel extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8294079438870201281L;
	private CheckBox purification;
	private ComboBox purificationMethods;
	
	private static final Logger logger = LogManager.getLogger(SmallMoleculePanel.class);

	public SmallMoleculePanel(DBVocabularies vocabs) {

		setSpacing(true);
		
		this.setCaption("Small Molecules Experiment Options");

		purification = new CheckBox("Protein Purification");

		addComponent(purification);
		purificationMethods = new ComboBox("Purification Method");
		purificationMethods.setNullSelectionAllowed(false);
		purificationMethods.setStyleName(Styles.boxTheme);
		purificationMethods.setVisible(false);

		List<String> methods = new ArrayList<String>(vocabs.getProteinPurificationMethodsMap().values());
		Collections.sort(methods);
		purificationMethods.addItems(methods);
		addComponent(purificationMethods);

		purification.addValueChangeListener(new ValueChangeListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -2669873589648835159L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				purificationMethods.setVisible(purification.getValue());
			}
		});
	}

	public List<WizardStep> getNextMSSteps(
			Map<life.qbic.projectwizard.control.WizardController.Steps, WizardStep> steps, int register) {
		boolean poolProteins = false; //proteinPooling.getValue(); cfh
		boolean peps = false; //measurePeptides.getValue(); cfh
		boolean proteins = false;
		List<WizardStep> res = new ArrayList<WizardStep>();
		if (poolProteins) {
			res.add(steps.get(Steps.Test_Sample_Pooling));
		}
		MSAnalyteStep f1 = (MSAnalyteStep) steps.get(Steps.SmallMolecule_Fractionation);
		res.add(f1);
		if (peps) {
			MSAnalyteStep f2 = (MSAnalyteStep) steps.get(Steps.Peptide_Fractionation);
			f1.setNeedsDigestion(true);
			res.add(f2);
		} else {
			f1.setNeedsDigestion(false);
		}
		if (register == 1)
			res.add(steps.get(Steps.Registration));
		return res;
	}

	public boolean usesPurification() {
		return purification.getValue() && purificationMethods.getValue() != null;
	}

	public String getPurificationMethod() {
		return purificationMethods.getValue().toString();
	}

	public boolean isValid() {
		return true;
	}

	public void selectProteinPurification(String option) {
		if (option.isEmpty()) {
			purification.setValue(false);
			purificationMethods.setNullSelectionAllowed(true);
			purificationMethods.setValue(purificationMethods.getNullSelectionItemId());
			purificationMethods.setNullSelectionAllowed(false);
		} else {
			purification.setValue(true);
			purificationMethods.setValue(option);
		}
	}
}
