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

//import life.qbic.datamodel.samples.AOpenbisSample;
import life.qbic.portal.Styles;
import life.qbic.projectwizard.control.WizardController.Steps;
import life.qbic.projectwizard.io.DBVocabularies;
//import life.qbic.projectwizard.model.MHCLigandExtractionProtocol;
//import life.qbic.projectwizard.model.RegisteredAnalyteInformation;
//import life.qbic.projectwizard.model.TestSampleInformation;
import life.qbic.projectwizard.uicomponents.CFHSampleTable;
//import life.qbic.projectwizard.uicomponents.ElementPanel;
//import life.qbic.projectwizard.uicomponents.LigandExtractPanel;
//import life.qbic.projectwizard.uicomponents.MSOptionComponent;
//import life.qbic.projectwizard.uicomponents.NminOptions;
//import life.qbic.projectwizard.uicomponents.TechnologiesPanel;
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
public class CfhTableStep implements WizardStep {

	private VerticalLayout main;
	private CFHSampleTable tablePanel;
	//private MSOptionComponent msPanel;
	//private LigandExtractPanel mhcLigandPanel;
	//private ElementPanel elementPanel;
	//private CheckBox noMeasure;
	private DBVocabularies vocabs;
	//private boolean containsProteins = false;
	//private boolean containsMHCLigands = false;
	//private boolean containsMatrix = false;
	//private boolean containsElement = false;

	private Wizard wizard;

	/**
	 * Create a new Sample Preparation step for the wizard
	 * 
	 * @param sampleTypes
	 *            Available list of sample types, e.g. Proteins, RNA etc.
	 */
	public CfhTableStep(Wizard wizard, DBVocabularies vocabs) {
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
		//noMeasure = new CheckBox("No further preparation of samples?");
		//main.addComponent(Styles.questionize(noMeasure,
		//		"Check if no DNA etc. is extracted and measured, for example in tissue imaging.",
		//		"No Sample Preparation"));

		/*noMeasure.addValueChangeListener(new ValueChangeListener() {

			private static final long serialVersionUID = 2393762547426343668L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				changeTechPanel();
			}
		});*/
	}

	@Override
	public String getCaption() {
		return "Analysis Method";
	}

	
	@Override
	public Component getContent() {
		return main;
	}

	@Override
	public boolean onAdvance() {
		
		 return true;
		
	}

	private void replaceWizardSteps(List<WizardStep> nextMSSteps) {
		for (WizardStep step : nextMSSteps)
			wizard.addStep(step);
	}

	@Override
	public boolean onBack() {
		return true;
	}

}
