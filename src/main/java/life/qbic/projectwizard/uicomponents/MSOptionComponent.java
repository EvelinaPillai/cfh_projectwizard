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
package life.qbic.projectwizard.uicomponents;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.vaadin.teemu.wizards.WizardStep;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;

import life.qbic.projectwizard.control.WizardController.Steps;
import life.qbic.projectwizard.io.DBVocabularies;
import life.qbic.projectwizard.steps.MSAnalyteStep;
import life.qbic.projectwizard.steps.TestStep;
import life.qbic.portal.Styles;

public class MSOptionComponent extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6966022066367510739L;

	// CFH
	private TextArea composition;
	private Label samplePrep;
	private CheckBox digestionSolution;
	private CheckBox digestionGel;
	private CheckBox precipitation;
	private TextField other;
	private CheckBox none;

	private Label colouring;
	private CheckBox silver;
	private CheckBox coomassie;

	private Label analysis;
	private CheckBox identification;
	private CheckBox quantification;
	private TextField duration;
	private CheckBox evaluation;
	private CheckBox molecularWeight;
	private TextField comments;

	private CheckBox proteinPooling;
	private CheckBox shortGel;
	private CheckBox purification;
	private CheckBox measurePeptides;
	private ComboBox purificationMethods;

	private static final Logger logger = LogManager.getLogger(MSOptionComponent.class);

	public MSOptionComponent(DBVocabularies vocabs) {
		//this.setCaption("MS Experiment Options");
		setSpacing(true);
		
		// CFH gel below analyte Proteins box
		colouring = new Label("Gel Staining:");
		silver = new CheckBox("Silver");
		coomassie = new CheckBox("Coomassie");

		addComponent(colouring);
		addComponent(silver);
		addComponent(coomassie);
		// CFH Specific info required for module 1 proteins
		composition = new TextArea("Sample Composition (Buffer, concentration, estimated sample quantities)");
		addComponent(composition);

		// sample preparation:
		samplePrep = new Label("Sample Preparation (CFH):");
		precipitation = new CheckBox("Precipitation"); // Fällung
		digestionSolution = new CheckBox("in solution digest");// in Lösung-Verdau 
		digestionGel = new CheckBox("in gel digest");// in Gel-Verdau
		shortGel = new CheckBox("Use Short Gel");
		other = new TextField("Other:");
		none = new CheckBox("None");

		addComponent(samplePrep);
		addComponent(digestionSolution);
		addComponent(digestionGel);
		addComponent(precipitation);
		addComponent(shortGel);
		addComponent(other);
		addComponent(none); //TODO if none grey out others if possible

		// CFH Analysis:
		analysis = new Label("Analysis: ");
		identification = new CheckBox("Identification");
		quantification = new CheckBox("Quantification");
		duration = new TextField("Instrument time");
		evaluation = new CheckBox("No data analysis");
		molecularWeight = new CheckBox("Determination of molecular weight");

		addComponent(analysis);
		addComponent(identification);
		addComponent(quantification);
		addComponent(molecularWeight);
		addComponent(evaluation);
		addComponent(duration);
		
		comments = new TextField("Comments");
		addComponent(comments);

		//maybe we need in future for project with QBiC so just deactivate 
		addComponent(new Label("<hr />",ContentMode.HTML));
		addComponent(new Label("Optional: "));
		proteinPooling = new CheckBox("Pool Before Protein Fractionation/Enrichment");
		measurePeptides = new CheckBox("Measure Peptides");
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

			@Override
			public void valueChange(ValueChangeEvent event) {
				purificationMethods.setVisible(purification.getValue());
			}
		});
		
		addComponent(proteinPooling);
		addComponent(measurePeptides);
		
		//disable them for now
		proteinPooling.setEnabled(false);
		measurePeptides.setEnabled(false);
		purification.setEnabled(false);
	}

	public List<WizardStep> getNextMSSteps(
			Map<life.qbic.projectwizard.control.WizardController.Steps, WizardStep> steps, int register) {
		boolean poolProteins = proteinPooling.getValue();
		boolean peps = measurePeptides.getValue();
		List<WizardStep> res = new ArrayList<WizardStep>();
		if (poolProteins) {
			res.add(steps.get(Steps.Test_Sample_Pooling));
		}
		MSAnalyteStep f1 = (MSAnalyteStep) steps.get(Steps.Protein_Fractionation);
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

	public boolean usesDigestionSolution() {
		return digestionSolution.getValue();
	}
	public boolean usesDigestionGel() {
		return digestionGel.getValue();
	}

	public boolean usesPrecipitation() {
		return precipitation.getValue();
	}

	public boolean usesNone() {
		return none.getValue();
	}

	public boolean usesShortGel() {
		return shortGel.getValue();
	}

	public boolean usesSilver() {
		return silver.getValue();
	}

	public boolean usesCoomassie() {
		return coomassie.getValue();
	}

	public boolean usesIdentification() {
		return identification.getValue();
	}

	public boolean usesQuantification() {
		return quantification.getValue();
	}

	public boolean usesEvaluation() {
		return evaluation.getValue();
	}

	public boolean usesMolecularWeight() {
		return molecularWeight.getValue();
	}

	public boolean usesComposition() {
		return !composition.getValue().isEmpty();
	}

	public String getComposition() {
		return composition.getValue().toString();
	}

	public boolean usesOther() {
		return !other.getValue().isEmpty();
	}

	public String getOther() {
		return other.getValue().toString();
	}

	public boolean usesDuration() {
		return !duration.getValue().isEmpty();
	}

	public String getDuration() {
		return duration.getValue().toString();
	}
	
	public boolean usesComments() {
		return !comments.getValue().isEmpty();
	}

	public String getComments() {
		return comments.getValue().toString();
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

	public void addMSListener(ValueChangeListener msExpChangedListener) {
		proteinPooling.addValueChangeListener(msExpChangedListener);
		measurePeptides.addValueChangeListener(msExpChangedListener);
	}

	public boolean hasProteinPoolBeforeFractionation() {
		return proteinPooling.getValue();
	}

	public void selectMeasurePeptides(boolean select) {
		measurePeptides.setValue(select);
	}

	public void selectUseShortGel(boolean select) {
		shortGel.setValue(select);
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

	public void selectOther(String option) {
		if (option.isEmpty()) {
			other.setNullSettingAllowed(true);
			other.setValue(other.getNullRepresentation());
			other.setNullSettingAllowed(false);
		} else {
			other.setValue(option);
		}
	}

	public void selectUsesPrecipitation(boolean select) {
		precipitation.setValue(select);
	}

	public void selectUsesDigestionSolution(boolean select) {
		digestionSolution.setValue(select);
	}
	
	public void selectUsesDigestionGel(boolean select) {
		digestionGel.setValue(select);
	}

	public void selectUsesNone(boolean select) {
		none.setValue(select);
	}

	public void selectUsesSilver(boolean select) {
		silver.setValue(select);
	}

	public void selectUsesCoomassie(boolean select) {
		coomassie.setValue(select);
	}

	public void selectUsesIdentification(boolean select) {
		identification.setValue(select);
	}

	public void selectUsesQuantification(boolean select) {
		quantification.setValue(select);
	}

	public void selectUsesEvaluation(boolean select) {
		evaluation.setValue(select);
	}

	public void selectUsesMolecularWeight(boolean select) {
		molecularWeight.setValue(select);
	}

	public void selectDuration(String option) {
		if (option.isEmpty()) {
			duration.setNullSettingAllowed(true);
			duration.setValue(duration.getNullRepresentation());
			duration.setNullSettingAllowed(false);
		} else {
			duration.setValue(option);
		}
	}
	
	public void selectComments(String option) {
		if (option.isEmpty()) {
			comments.setNullSettingAllowed(true);
			comments.setValue(comments.getNullRepresentation());
			comments.setNullSettingAllowed(false);
		} else {
			comments.setValue(option);
		}
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
