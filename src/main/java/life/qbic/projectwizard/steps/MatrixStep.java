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

import life.qbic.portal.Styles;
import life.qbic.projectwizard.control.WizardController.Steps;
import life.qbic.projectwizard.io.DBVocabularies;
import life.qbic.projectwizard.model.RegisteredAnalyteInformation;
import life.qbic.projectwizard.model.TestSampleInformation;
import life.qbic.projectwizard.uicomponents.ElementPanel;
import life.qbic.projectwizard.uicomponents.AminoAcidPanel;
import life.qbic.projectwizard.uicomponents.CFHPanel;
import life.qbic.projectwizard.uicomponents.NminPanel;
import life.qbic.projectwizard.uicomponents.FatPanel;


import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.VerticalLayout;
/**
 * Wizard Step to life.qbic.projectwizard.model the biological entities of an experiment
 * 
 * @author Andreas Friedrich
 * 
 */
public class MatrixStep implements WizardStep {

	
	
	private VerticalLayout main;
	private CFHPanel cfhPanel;
	//private MSOptionComponent msPanel;
	private ElementPanel elementPanel;
	private AminoAcidPanel AminoAcidPanel;
	private NminPanel NminPanel;
	private FatPanel FatPanel;
	
	
	private CheckBox noMeasure;

	private DBVocabularies vocabs;
	private boolean containsElement = false;
	private boolean containsNmin = false;
	private boolean containsFat = false;
	private boolean containsAA = false;
	
	private Wizard wizard;

	/**
	 * Create a new Sample Preparation step for the wizard
	 * 
	 * @param sampleTypes
	 *            Available list of sample types, e.g. Proteins, RNA etc.
	 */
	public MatrixStep(Wizard wizard, DBVocabularies vocabs) {
		this.wizard = wizard;
		this.vocabs = vocabs;
		main = new VerticalLayout();
		main.setMargin(true);
		main.setSpacing(true);
		main.setSizeUndefined();
		Label header = new Label("CFH Analysis Method");
		main.addComponent(Styles.questionize(header,
				"CFH",
				"CFH Method"));
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
		return "CFH Method";
	}

	public CheckBox getNotMeasured() {
		return noMeasure;
	}

	public void changeTechPanel() {
		if (noMeasure.getValue())
			cfhPanel.resetInputs();
		cfhPanel.setEnabled(!noMeasure.getValue());
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
		resetNextSteps(hasPools());
		for (WizardStep step : nextMSSteps)
			wizard.addStep(step);
	}

	@Override
	public boolean onBack() {
		return true;
	}

	public List<TestSampleInformation> getCFHInformation() {
		return cfhPanel.getCfhInfo();
	}

	public boolean hasPools() {
		return cfhPanel.poolingSet(); 
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

	public void initTestStep(ClickListener refreshPeopleListener, Map<Steps, WizardStep> steps) {

	
		
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
				for (TestSampleInformation i : getCFHInformation()) {
					String cfh = i.getCfhInfo();
					containsElement |= cfh.equals("ELEMENT");
				}
				//if (containsElement) {
					elementPanel.setVisible(containsElement);
					
				//}
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
				for (TestSampleInformation i : getCFHInformation()) {
					String cfh = i.getCfhInfo();
					containsAA |= cfh.equals("AMINOACID");
				}
				if (containsAA) {
					AminoAcidPanel.setVisible(containsAA);
					
				}
			}
		};
		
		
		ValueChangeListener fatListener = new ValueChangeListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -2385386550989828127L;

			/**
			 * 
			 */
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				containsFat = false;
				for (TestSampleInformation i : getCFHInformation()) {
					String cfh = i.getCfhInfo();
					containsFat |= cfh.equals("fat");
				}
				if (containsFat) {
					FatPanel.setVisible(containsFat);
					
				}
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
				for (TestSampleInformation i : getCFHInformation()) {
					String cfh = i.getCfhInfo();
					containsNmin |= cfh.equals("NMIN");
				}
				if (containsNmin) {
					NminPanel.setVisible(containsNmin);
					
				}
			}
		};
		
		//cfhPanel = new CFHPanel(vocabs.getCfhMethodTypes(), vocabs.getPeople().keySet(), vocabs.getMatrixMap().keySet(), new OptionGroup(""),elementListener,aaListener, fatListener, nminListener);
		cfhPanel = new CFHPanel(vocabs.getCfhMethodTypes(), vocabs.getPeople().keySet(), new OptionGroup(""),elementListener,aaListener, fatListener, nminListener , vocabs.getMatrixMap());

		main.addComponent(cfhPanel);
		main.addComponent(new Label("<hr />", Label.CONTENT_XHTML));
//		msPanel = new MSOptionComponent(vocabs);
//		msPanel.setVisible(false);
		ValueChangeListener msExpChangedListener = new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
	//			replaceWizardSteps(cfhPanel.get);
			}
		};

//		msPanel.addMSListener(msExpChangedListener);

//		main.addComponent(msPanel);
//
//		mhcLigandPanel = new LigandExtractPanel(vocabs);
//		mhcLigandPanel.setVisible(false);
//
//		main.addComponent(mhcLigandPanel);
		
		elementPanel = new ElementPanel(vocabs);
		elementPanel.setVisible(false);
		AminoAcidPanel = new AminoAcidPanel(vocabs);
		AminoAcidPanel.setVisible(false);
		NminPanel = new NminPanel(vocabs, 1); //TODO number of samples and how we get samples from steps before
		NminPanel.setVisible(false);
		
		main.addComponent(elementPanel);
		main.addComponent(AminoAcidPanel);
		main.addComponent(NminPanel);
	}

	

	private void resetNextSteps(boolean pool) {
		List<WizardStep> steps = wizard.getSteps();
		List<WizardStep> copy = new ArrayList<WizardStep>();
		copy.addAll(steps);
		boolean isNew = false;
		for (int i = 0; i < copy.size(); i++) {
			WizardStep cur = copy.get(i);
			boolean keep = pool && cur instanceof PoolingStep ;
			if (isNew && !keep) {
				wizard.removeStep(cur);
			}
			if (cur.equals(this))
				isNew = true;
		}
	}




	

	public void setCfhInputs(RegisteredAnalyteInformation infos) {
		for (String cfhMethod : infos.getCFHMethod()) {
			cfhPanel.select(cfhMethod);
		}	
		
	}

	public void updatePeople(Set<String> people) {
		cfhPanel.updatePeople(people);
	}
	
	
	public void setNminSamples(int noSamples) {
		NminPanel.setNminSamples(noSamples);
	}
	
	
	
	
	public List<Map<String,String>> getElementPanel()
	{
		if(containsElement)
		{
			return elementPanel.getElementProperties();
		}
		
		return null;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
//	private VerticalLayout main;
//	private HorizontalLayout matrixPanel;
//	private ComboBox matrix;
//	private ComboBox cfhMethods;
//	private StandardTextField noOfSamples;
//	private VerticalLayout elementAnalysisPanel;
//	private StandardTextField elementAnalysis;
//	private Button btnElementAnalysis; 
//	private VerticalLayout aaAnalysisPanel;
//	private StandardTextField aaAnalysis;
//	private Button btnAAAnalysis;
//	
//	public MatrixStep(Map<String, String> matrixMap, Set<String> keySet) {
//		main = new VerticalLayout();
//		main.setSpacing(true);
//		main.setMargin(true);
//		
//		//Matrix Panel
//		matrix = new ComboBox("Matrix");
//	    matrix.setNullSelectionAllowed(false);
//	    matrix.setStyleName(Styles.boxTheme);
//	    matrix.setVisible(true);
//	    
//	    List<String> methods =
//	        new ArrayList<String>(matrixMap.keySet());
//	    Collections.sort(methods);
//	    matrix.addItems(methods);
//	    noOfSamples = new StandardTextField("Number of Samples");
//	    matrixPanel = new HorizontalLayout(matrix,noOfSamples);
//	    matrixPanel.setSpacing(true);
//	    
//	    
//	    cfhMethods = new ComboBox("CFH Methods");
//	    //TODO set visible to false and add new Techpanel
//	    //ElementAnalysis Panel
//	    elementAnalysis = new StandardTextField("Element Analysis");
//	    btnElementAnalysis = new Button("Select");
//	    elementAnalysisPanel = new VerticalLayout(Styles.questionize(elementAnalysis, "Type in elements that will be analyzed in this experiment. Use element symbols and separate them by \";\". Or click the select button to show the Periodic Table of the Elements and make a selection there.","Element Analysis"), 
//	    		btnElementAnalysis);
//	    elementAnalysisPanel.setSpacing(true);
//	    
//	    //Amino Acid Panel
//	    aaAnalysis = new StandardTextField("Amino Acids Analysis");
//	    btnAAAnalysis = new Button("Select");
//	    aaAnalysisPanel = new VerticalLayout(Styles.questionize(aaAnalysis, "Type in amino acids that will be analyzed in this experiment. Use amino acids symbols and separate them by \";\". Or click the select button to show a table of all amino acids and make a selection there.","Element Analysis"), 
//	    		btnAAAnalysis);
//	    aaAnalysisPanel.setSpacing(true);
//	    
//	    main.addComponent(matrixPanel);
//	    main.addComponent(elementAnalysisPanel);
//	    main.addComponent(aaAnalysisPanel);
//		
//	}
//
//	@Override
//	public String getCaption() {
//		return "Matrix";
//	}
//
//	@Override
//	public Component getContent() {
//		return main;
//	}
//
//	@Override
//	public boolean onAdvance() {
//		// TODO maybe write a isvalid() method like in MSAnalyteStep.java
//		return true;
//	}
//
//	@Override
//	public boolean onBack() {
//		return true;
//	}

}