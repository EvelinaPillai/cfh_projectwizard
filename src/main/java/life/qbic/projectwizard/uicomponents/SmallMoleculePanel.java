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
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import life.qbic.portal.Styles;
import life.qbic.projectwizard.control.WizardController.Steps;
import life.qbic.projectwizard.io.DBVocabularies;
import life.qbic.projectwizard.steps.MSAnalyteStep;

/**
 * Core Facility Hohenheim (CFH) - For SmallMolecules we want the same as for
 * Proteins but different MSOptions
 * 
 */

public class SmallMoleculePanel extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8294079438870201281L;
	private CheckBox purification;
	private ComboBox purificationMethods;

	// CFH
	private TextArea composition;
	private TextField substanceClass;
	private TextField molFormulaMass;
	private Label samplePrep;
	private CheckBox extraction;
	private CheckBox precipitation;
	private TextField other;
	private CheckBox none;

	private Label analysis;
	private TextField molecularWeightRange;
	private ComboBox polarity;
	private CheckBox molecularWeight;
	private CheckBox hplcMS;
	private ComboBox quantification;
	private TextField internalStandards;

	private static final Logger logger = LogManager.getLogger(SmallMoleculePanel.class);

	public SmallMoleculePanel(DBVocabularies vocabs) {

		setSpacing(true);

		this.setCaption("Small Molecules Experiment Options");

		composition = new TextArea("Composition (Buffer, concentration, estimated sample quantities)");
		substanceClass = new TextField("Substance class");
		molFormulaMass = new TextField("Molecular formula/mass ");

		addComponent(composition);
		addComponent(substanceClass);
		addComponent(molFormulaMass);

		// sample preparation:
		samplePrep = new Label("Sample Preparation:");
		extraction = new CheckBox("Extraction");
		precipitation = new CheckBox("Precipitation"); // Fällung
		other = new TextField("Other:");
		none = new CheckBox("None");

		addComponent(samplePrep);
		addComponent(extraction);
		addComponent(precipitation);
		addComponent(other);
		addComponent(none);

		analysis = new Label("Analysis: ");
		molecularWeightRange = new TextField("Determination of molecular weight range from ... to ");
		polarity = new ComboBox("Polarity");
		List<String> polOpts = new ArrayList<String>();
		polOpts.add("positive");
		polOpts.add("negative");
		polOpts.add("both");
		polarity.addItems(polOpts);
		molecularWeight = new CheckBox("Determination of molecular weight");
		hplcMS = new CheckBox("HPLC-MS");
		quantification = new ComboBox("Quantification");
		List<String> quantOpts = new ArrayList<String>();
		quantOpts.add("relatively");
		quantOpts.add("absolute");
		quantification.addItems(quantOpts);
		internalStandards = new TextField("Internal Standards");

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
		boolean poolProteins = false; // proteinPooling.getValue(); cfh
		boolean peps = false; // measurePeptides.getValue(); cfh
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

	public boolean usesComposition() {
		return !composition.getValue().isEmpty();
	}

	public String getComposition() {
		return composition.getValue().toString();
	}
	
	public void selectComposition(String option) {
		if (option.isEmpty()) {
			composition.setNullSettingAllowed(true);
			composition.setValue(composition.getNullRepresentation());
			composition.setNullSettingAllowed(false);
		} else {
			composition.setValue(option);
		}
	}

	public boolean usesSubstanceClass() {
		return !substanceClass.getValue().isEmpty();
	}

	public String getSubstanceClass() {
		return substanceClass.getValue().toString();
	}

	public boolean usesMolFormulaMass() {
		return !molFormulaMass.getValue().isEmpty();
	}

	public String getMolFormulaMass() {
		return molFormulaMass.getValue().toString();
	}

	public boolean usesExtraction() {
		return extraction.getValue();
	}

	public void selectUseExtraction(boolean select) {
		extraction.setValue(select);
	}

	public boolean usesPrecipitation() {
		return precipitation.getValue();
	}

	public void selectUsePrecipitation(boolean select) {
		precipitation.setValue(select);
	}

	public boolean usesNone() {
		return none.getValue();
	}

	public void selectUsesNone(boolean select) {
		none.setValue(select);
	}

	public boolean usesOther() {
		return !other.getValue().isEmpty();
	}

	public String getOther() {
		return other.getValue().toString();
	}

	public void selectOther(String option) {
		if (option.isEmpty()) {
			other.setNullSettingAllowed(true);
			other.setValue(other.getNullRepresentation());
			other.setNullSettingAllowed(false);
		} else {
			other.setValue(option);
		}
	}

	public boolean usesMolecularWeightRange() {
		return !molecularWeightRange.getValue().isEmpty();
	}

	public String getMolecularWeightRange() {
		return molecularWeightRange.getValue().toString();
	}

	public void selectMolecularWeightRange(String option) {
		if (option.isEmpty()) {
			molecularWeightRange.setNullSettingAllowed(true);
			molecularWeightRange.setValue(molecularWeightRange.getNullRepresentation());
			molecularWeightRange.setNullSettingAllowed(false);
		} else {
			molecularWeightRange.setValue(option);
		}
	}

	public boolean usesPolarity() {
		return polarity.getValue() != null;
	}

	public String getPolarity() {
		return polarity.getValue().toString();
	}

	public void selectPolarity(String option) {
		if (option.isEmpty()) {
			polarity.setNullSelectionAllowed(true);
			polarity.setValue(polarity.getNullSelectionItemId());
			polarity.setNullSelectionAllowed(false);
		} else {
			polarity.setValue(true);
			polarity.setValue(option);
		}
	}

	public boolean usesMolecularWeight() {
		return molecularWeight.getValue();
	}

	public void selectUseMolecularWeight(boolean select) {
		molecularWeight.setValue(select);
	}

	public boolean usesHplcMS() {
		return hplcMS.getValue();
	}

	public void selectUseHplcMS(boolean select) {
		hplcMS.setValue(select);
	}

	public boolean usesQuantification() {
		return quantification.getValue() != null;
	}

	public String getQuantification() {
		return quantification.getValue().toString();
	}

	public void selectQuantification(String option) {
		if (option.isEmpty()) {
			quantification.setNullSelectionAllowed(true);
			quantification.setValue(quantification.getNullSelectionItemId());
			quantification.setNullSelectionAllowed(false);
		} else {
			quantification.setValue(true);
			quantification.setValue(option);
		}
	}

	public boolean usesInternalStandards() {
		return !internalStandards.getValue().isEmpty();
	}

	public String getInternalStandards() {
		return internalStandards.getValue().toString();
	}

	public void selectInternalStandards(String option) {
		if (option.isEmpty()) {
			internalStandards.setNullSettingAllowed(true);
			internalStandards.setValue(internalStandards.getNullRepresentation());
			internalStandards.setNullSettingAllowed(false);
		} else {
			internalStandards.setValue(option);
		}
	}

	public void selectSubstanceClass(String option) {
		if (option.isEmpty()) {
			substanceClass.setNullSettingAllowed(true);
			substanceClass.setValue(substanceClass.getNullRepresentation());
			substanceClass.setNullSettingAllowed(false);
		} else {
			substanceClass.setValue(option);
		}
	}

	public void selectMolFormulaMass(String option) {
		if (option.isEmpty()) {
			molFormulaMass.setNullSettingAllowed(true);
			molFormulaMass.setValue(molFormulaMass.getNullRepresentation());
			molFormulaMass.setNullSettingAllowed(false);
		} else {
			molFormulaMass.setValue(option);
		}
	}
}
