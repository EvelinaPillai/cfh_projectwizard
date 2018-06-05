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
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.vaadin.teemu.wizards.WizardStep;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import life.qbic.portal.portlet.ProjectWizardUI;
import life.qbic.projectwizard.uicomponents.ConditionsPanel;
import life.qbic.projectwizard.uicomponents.EAComponent;
import life.qbic.portal.Styles;
import life.qbic.portal.Styles.NotificationType;
import life.qbic.portal.components.OpenbisInfoTextField;
import life.qbic.portal.components.StandardTextField;

/**
 * Wizard Step to life.qbic.projectwizard.model the biological entities of an experiment
 * 
 * @author Andreas Friedrich
 * 
 */
public class MatrixStep implements WizardStep {

	private VerticalLayout main;
	private HorizontalLayout matrixPanel;
	private ComboBox matrix;
	private StandardTextField noOfSamples;
	private EAComponent elementAnalysisPanel;

	private VerticalLayout aaAnalysisPanel;
	private StandardTextField aaAnalysis;
	private Button btnAAAnalysis;
	private static final Logger logger = LogManager.getLogger(MSAnalyteStep.class);
	
	public MatrixStep(Map<String, String> matrixMap, Set<String> keySet) {
		main = new VerticalLayout();
		main.setSpacing(true);
		main.setMargin(true);
		
		//Matrix Panel
		matrix = new ComboBox("Matrix");
	    matrix.setNullSelectionAllowed(false);
	    matrix.setStyleName(Styles.boxTheme);
	    matrix.setVisible(true);
	    
	    List<String> methods =
	        new ArrayList<String>(matrixMap.keySet());
	    Collections.sort(methods);
	    matrix.addItems(methods);
	    noOfSamples = new StandardTextField("Number of Samples");
	    matrixPanel = new HorizontalLayout(matrix,noOfSamples);
	    matrixPanel.setSpacing(true);
	    
	    //TODO set visible to false and add new Techpanel
	    //ElementAnalysis Panel
	    if(matrix.isVisible()) {
	    	elementAnalysisPanel = new EAComponent();
	    	elementAnalysisPanel.setVisible(true);
	    }
	    
	    //Amino Acid Panel
	    aaAnalysis = new StandardTextField("Amino Acids Analysis");
	    btnAAAnalysis = new Button("Select");
	    aaAnalysisPanel = new VerticalLayout(Styles.questionize(aaAnalysis, "Type in amino acids that will be analyzed in this experiment. Use amino acids symbols and separate them by \";\". Or click the select button to show a table of all amino acids and make a selection there.","Element Analysis"), 
	    		btnAAAnalysis);
	    aaAnalysisPanel.setSpacing(true);
	    
	    main.addComponent(matrixPanel);
	    main.addComponent(elementAnalysisPanel);
	    main.addComponent(aaAnalysisPanel);
		
	}

	@Override
	public String getCaption() {
		return "Matrix";
	}

	@Override
	public Component getContent() {
		return main;
	}

	@Override
	public boolean onAdvance() {
		// TODO maybe write a isvalid() method like in MSAnalyteStep.java
		return true;
	}

	@Override
	public boolean onBack() {
		return true;
	}

}