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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.vaadin.teemu.wizards.WizardStep;

import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;

import life.qbic.projectwizard.io.DBVocabularies;
import life.qbic.projectwizard.model.MSExperimentModel;
import life.qbic.projectwizard.model.TestSampleInformation;
import life.qbic.projectwizard.steps.MSAnalyteStep;
import life.qbic.datamodel.samples.AOpenbisSample;
import life.qbic.portal.Styles;

import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

/**
 * Composite UI component for inputting an arbitrary number of experimental conditions
 * 
 * @author Andreas Friedrich
 * 
 */
public class CFHSampleTable implements WizardStep {
	 private VerticalLayout main;
	  private static final Logger logger = LogManager.getLogger(MSAnalyteStep.class);

	  private OptionGroup cfhOptions = new OptionGroup();
	  private Table baseCfhSampleTable;
	  private DBVocabularies vocabs;
	  private TextArea additionalInfo;
	  

	  public void CFHSampleTableStep(DBVocabularies vocabs) {
	    
	    main = new VerticalLayout();
	    main.setSpacing(true);
	    main.setMargin(true);
	    this.vocabs = vocabs;

	    additionalInfo = new TextArea("Summary Table");
	    additionalInfo.setStyleName(Styles.areaTheme);
	    main.addComponent(additionalInfo);

	    String label = "Active Components";
	    String info =
	        "we created atable base on analysis which is done in previouse Step";
	   
	    Label header = new Label(label);
	    main.addComponent(Styles.questionize(header, info, label));

	    cfhOptions.addItems(new ArrayList<String>(Arrays.asList("Fractionation", "Enrichment")));
	    cfhOptions.setMultiSelect(true);
	    main.addComponent(cfhOptions);

	    baseCfhSampleTable = new Table();
	    baseCfhSampleTable.setStyleName(Styles.tableTheme);
	    baseCfhSampleTable.addContainerProperty("Sample", Label.class, null);
	    baseCfhSampleTable.addContainerProperty("matrix", TextField.class, null);
	    baseCfhSampleTable.addContainerProperty("element Analysis", TextField.class, null);
	    baseCfhSampleTable.addContainerProperty("Amino Acid", TextField.class, null);
	    baseCfhSampleTable.addContainerProperty("Fat Extraction", TextField.class, null);
	    baseCfhSampleTable.addContainerProperty("Nmin", TextField.class, null);
	  

	    baseCfhSampleTable.setColumnWidth("matrix", 70);
	    baseCfhSampleTable.setColumnWidth("element Analysis",121 );
	    baseCfhSampleTable.setColumnWidth("Amino Acid", 70);
	    baseCfhSampleTable.setColumnWidth("Fat Extraction", 130);
	    baseCfhSampleTable.setColumnWidth("Nmin", 90);
	  }
  @Override
public String getCaption() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public Component getContent() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public boolean onAdvance() {
	// TODO Auto-generated method stub
	return false;
}

@Override
public boolean onBack() {
	// TODO Auto-generated method stub
	return false;
}
 
}
