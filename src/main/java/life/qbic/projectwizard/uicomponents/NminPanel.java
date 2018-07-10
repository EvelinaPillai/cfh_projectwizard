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
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.ui.Label;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.data.util.converter.StringToIntegerConverter;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import life.qbic.datamodel.samples.AOpenbisSample;
import life.qbic.portal.Styles;
import life.qbic.projectwizard.io.DBVocabularies;
import life.qbic.projectwizard.model.TestSampleInformation;
import life.qbic.projectwizard.steps.TestStep;

/**
 * Core Facility Hohenheim (CFH) - Collect Nmin (mineralized Nitrogen) Sample information
 * a Nmin Sample differs in the depth of the soil.
 * Depending on the depth of the soil you get a different bulk density.
 * 
 * By entering a number of samples for either 0-30cm , 30-60cm, 60-90cm
 * a table with exact this number of samples each is created. 
 * The sample name comes from the TestStep and is increased by one.
 * The reset button sets back sample names and table.
 * 
 * TODO sample name has no value change listener
 * 
 * @author Evelina Pillai
 *
 */
/**
 * @author openbis
 *
 */
public class NminPanel extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5610939053224210938L;

	private List<String> soildepth;
	private Table nminSamples;
	private TextField depth0;
	private TextField depth30;
	private TextField depth60;
	private Button resetDepths;
	private String sampleName;

	private static final Logger logger = LogManager.getLogger(NminPanel.class);

	public NminPanel(DBVocabularies vocabs, List<TestSampleInformation> list) {
		this.setCaption("Nmin Options");
		this.soildepth = vocabs.getSoildepth();
		

		setSpacing(true);

		this.depth0 = new TextField("Number of samples with depth 0-30cm");
		this.depth30 = new TextField("Number of samples with depth 30-60cm");
		this.depth60 = new TextField("Number of samples with depth 60-90cm");

		ObjectProperty<Integer> depth0Count = new ObjectProperty<Integer>(0);
		depth0.setConverter(new StringToIntegerConverter());
		depth0.setWidth("40px");
		depth0.setStyleName(Styles.fieldTheme);
		depth0.setPropertyDataSource(depth0Count);

		ObjectProperty<Integer> depth30Count = new ObjectProperty<Integer>(0);
		depth30.setConverter(new StringToIntegerConverter());
		depth30.setWidth("40px");
		depth30.setStyleName(Styles.fieldTheme);
		depth30.setPropertyDataSource(depth30Count);

		ObjectProperty<Integer> depth60Count = new ObjectProperty<Integer>(0);
		depth60.setConverter(new StringToIntegerConverter());
		depth60.setWidth("40px");
		depth60.setStyleName(Styles.fieldTheme);
		depth60.setPropertyDataSource(depth60Count);

		this.nminSamples = new Table();

		nminSamples.setStyleName(Styles.tableTheme);
		nminSamples.addContainerProperty("Sample", String.class, null); // sample name
		nminSamples.addContainerProperty("Soil depth [cm]", ComboBox.class, null);
		nminSamples.addContainerProperty("Bulk density [kg/L]", Label.class, null); // Lagerungsdichte

		resetDepths = new Button("RESET");

		addComponents(depth0, depth30, depth60, resetDepths);

		addComponent(nminSamples);

		resetDepths.addClickListener(clickEvent -> {
			nminSamples.removeAllItems();
			sampleName = TestStep.getSampleName();
			depth0Count.setValue(0);
			depth30Count.setValue(0);
			depth60Count.setValue(0);			
		});

		depth0.addValueChangeListener(new ValueChangeListener() {

			/**
			* 
			*/
			private static final long serialVersionUID = -7376707757078434969L;

			@Override
			public void valueChange(ValueChangeEvent event) {

				setNminSampleHelper(depth0Count, true, "0-30CM");
			}

		});

		depth30.addValueChangeListener(new ValueChangeListener() {

			/**
			* 
			*/
			private static final long serialVersionUID = 3562252517440808059L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				setNminSampleHelper(depth30Count, false, "30-60CM");

			}
		});

		depth60.addValueChangeListener(new ValueChangeListener() {

			/**
			* 
			*/
			private static final long serialVersionUID = 2074019785991895515L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				setNminSampleHelper(depth60Count, false, "60-90CM");
			}
		});

	}

	private ComboBox generateTableSoildepthBox(String soiltype) {
		ComboBox b = new ComboBox();
		b.setWidth("100px");
		b.addItems(this.soildepth);
		b.setStyleName(Styles.boxTheme);
		b.setValue(soiltype);
		return b;
	}

	private Label generateTableLabel(String bulkdensity) {
		Label l = new Label();
		l.setImmediate(true);
		l.setWidth("55px");
		l.setValue(bulkdensity);
		return l;
	}

	/**
	 * Creates the sample table.
	 * Columns are  || sample Name | depths | bulk density ||
	 * 
	 * @param noSamples
	 * @param density
	 * @param depths
	 */
	public void setNminSamples(int noSamples, boolean density, String depths) { // List<AOpenbisSample> extracts

		int i = nminSamples.size();
		int j = 0;

		if (sampleName == null) {
			sampleName = TestStep.getSampleName();
		}
		if (sampleName.matches("[A-Z]_+")) {
			sampleName = "P_0"; // default value 
		}

		for (int s = 0; s < noSamples; s++) {
	
			//first sample number shouldn't be changed
			if (i != 0) {
				j = 1;
			}
			i++;

			List<Object> row = new ArrayList<Object>();
	
			int nextInt = Integer.parseInt(sampleName.substring(2)) + j;
			sampleName = sampleName.substring(0, 2) + nextInt;
			row.add(sampleName);
			row.add(generateTableSoildepthBox(depths));

			if (density) {
				row.add(generateTableLabel("1,3kg/L"));
			} else {
				row.add(generateTableLabel("1,5kg/L"));
			}

			nminSamples.addItem(row.toArray(new Object[row.size()]), i);
		}
		nminSamples.setPageLength(nminSamples.size());
	}

	/**
	 * Helper to see if the number of samples to be created have been deleted, the
	 * field is empty and enter was triggered then it would give a
	 * NullPointerException. Instead this method resets the table. Or calls the
	 * setNimSamples function which creates the sample table.
	 * 
	 * @param depthCount
	 * @param density
	 * @param depths
	 */
	private void setNminSampleHelper(ObjectProperty<Integer> depthCount, boolean density, String depths) {
		if (!(depthCount.getValue() == null)) {
			setNminSamples(depthCount.getValue(), density, depths);
		} else {
			depthCount.setValue(0);
			nminSamples.removeAllItems();
		}
	}

}
