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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.text.WordUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.ui.Label;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.data.util.converter.StringToIntegerConverter;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import life.qbic.datamodel.samples.AOpenbisSample;
import life.qbic.portal.Styles;
import life.qbic.portal.components.StandardTextField;
import life.qbic.projectwizard.io.DBVocabularies;

public class NminPanel extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5610939053224210938L;

	private List<String> soildepth; // TODO do we really need it in openBIS or can we define it here because it
											// wont change in the future

	private Table nminSamples;
	private TextField depth0;
	private TextField depth30;
	private TextField depth60;
	private Button resetDepths;
	
	

	private static final Logger logger = LogManager.getLogger(NminPanel.class);

	//TODO think about if noSamples is necessary
	public NminPanel(DBVocabularies vocabs, int noSamples) {
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
		nminSamples.addContainerProperty("Bulk density [kg/l]", Label.class, null); // Lagerungsdichte

		resetDepths = new Button("RESET");
		
		addComponents(depth0,depth30,depth60, resetDepths);

		addComponent(nminSamples);
		
		resetDepths.addClickListener(clickEvent -> nminSamples.removeAllItems());

		depth0.addValueChangeListener(new ValueChangeListener() {

		      /**
			 * 
			 */
			private static final long serialVersionUID = -7376707757078434969L;

			@Override
		      public void valueChange(ValueChangeEvent event) {
		    	  setNminSamples(depth0Count.getValue(),true, "0-30CM");
		      }
		});
		
		depth30.addValueChangeListener(new ValueChangeListener() {

		      /**
			 * 
			 */
			private static final long serialVersionUID = 3562252517440808059L;

			@Override
		      public void valueChange(ValueChangeEvent event) {
		    	  setNminSamples(depth30Count.getValue(),false, "30-60CM");
		      }
		});
		
		depth60.addValueChangeListener(new ValueChangeListener() {

		      /**
			 * 
			 */
			private static final long serialVersionUID = 2074019785991895515L;

			@Override
		      public void valueChange(ValueChangeEvent event) {
		    	  setNminSamples(depth60Count.getValue(),false, "60-90CM");
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
	
	
	public void setNminSamples(int noSamples, boolean density, String type) { //List<AOpenbisSample> extracts
	    //nminSamples.removeAllItems();
	    //tableIdToBarcode = new HashMap<Integer, String>();
	    int i = nminSamples.size();
	   // for (AOpenbisSample s : extracts) {
	    for (int s=0; s<noSamples; s++) {
	      i++;
	      //tableIdToBarcode.put(i, s.getCode());

	      List<Object> row = new ArrayList<Object>();

	      row.add("sample name");
	      //row.add(generateTableIntegerInput());
	      row.add(generateTableSoildepthBox(type));
	      
	      if(density) {
	    	  row.add(generateTableLabel("1,3kg/L"));
	      }else { 
	    	  row.add(generateTableLabel("1,5kg/L"));}

	      nminSamples.addItem(row.toArray(new Object[row.size()]), i);
	    }
	    //nminSamples.setPageLength(noSamples);
	  }

}
