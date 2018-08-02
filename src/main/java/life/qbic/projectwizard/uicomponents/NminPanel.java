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
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.data.Item;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.ValoTheme;

import life.qbic.datamodel.samples.AOpenbisSample;
import life.qbic.portal.Styles;
import life.qbic.portal.Styles.NotificationType;
import life.qbic.projectwizard.io.DBVocabularies;
import life.qbic.projectwizard.model.TestSampleInformation;

/**
 * Core Facility Hohenheim (CFH) - Collect Nmin (mineralized Nitrogen) Sample information
 * a Nmin Sample differs in the depth of the soil.
 * Depending on the depth of the soil you get a different bulk density.
 * 
 * The samples were collected from the ExtractionStep and a valie for the soildepth can be selected.
 * It's either 0-30cm , 30-60cm, 60-90cm
 * a table with exact this number of samples each is created. 
 * The sample name comes from the TestStep if a secondary name is inserted.
 *
 */
public class NminPanel extends VerticalLayout {

	/**
	 * serial
	 */
	private static final long serialVersionUID = 5610939053224210938L;

	private List<String> soildepth;
	private Table nminSamples;

	private static final Logger logger = LogManager.getLogger(NminPanel.class);

	public NminPanel(DBVocabularies vocabs, List<TestSampleInformation> list) {
		this.setCaption("Nmin Options");
		this.soildepth = vocabs.getSoildepth();
	
		setSpacing(true);

		this.nminSamples = new Table();
		nminSamples.setWidth("250px");

		nminSamples.setStyleName(Styles.tableTheme);
		nminSamples.addContainerProperty("Sample", String.class, null); // sample name
		nminSamples.addContainerProperty("Soil depth [cm]", Component.class, null);

		addComponent(nminSamples);

	}

	private ComboBox generateTableSoildepthBox(List<String> entries, String width) {
		ComboBox b = new ComboBox();
		Collections.sort(entries);
		b.addItems(entries);
		b.setWidth(width);
		b.setFilteringMode(FilteringMode.CONTAINS);
		b.setStyleName(Styles.boxTheme);
		return b;
	}

	/**
	 * Creates the sample table. Columns are || sample Name | depths
	 * 
	 * @param extracts
	 */
	public void setNminSamples(List<AOpenbisSample> extracts) {
		nminSamples.removeAllItems();
		// tableIdToBarcode = new HashMap<Integer, String>();
		int i = 0;
		for (AOpenbisSample s : extracts) {
			i++;

			List<Object> row = new ArrayList<Object>();
			row.add(s.getQ_SECONDARY_NAME());
			ComboBox soildepthBox = generateTableSoildepthBox(soildepth, "150px");
			row.add(createComplexCellComponent(nminSamples, soildepthBox, "Soil depth [cm]", i));

			nminSamples.addItem(row.toArray(new Object[row.size()]), i);
		}
		nminSamples.setPageLength(nminSamples.size());
	}

	private Object createComplexCellComponent(Table t, ComboBox contentBox, String propertyName, final int rowID) {
		HorizontalLayout complexComponent = new HorizontalLayout();
		complexComponent.setWidth(contentBox.getWidth() + 10, contentBox.getWidthUnits());
		complexComponent.addComponent(contentBox);
		complexComponent.setExpandRatio(contentBox, 1);

		Button copy = new Button();
		Styles.iconButton(copy, FontAwesome.ARROW_CIRCLE_O_DOWN);
		copy.setStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		VerticalLayout vBox = new VerticalLayout();
		vBox.setWidth("15px");
		vBox.addComponent(copy);
		complexComponent.addComponent(vBox);
		complexComponent.setComponentAlignment(vBox, Alignment.BOTTOM_RIGHT);
		copy.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				ComboBox b = parseBoxRow(t, rowID, propertyName);
				Object selection = b.getValue();
				pasteSelectionToColumn(t, propertyName, selection, rowID);
			}
		});
		return complexComponent;
	}

	private ComboBox parseBoxRow(Table table, Object rowID, String propertyName) {
		Item item = nminSamples.getItem(rowID);
		Object prop = item.getItemProperty(propertyName).getValue();
		if (prop == null)
			return new ComboBox();
		Object component = prop;
		if (component instanceof ComboBox)
			return (ComboBox) component;
		else {
			HorizontalLayout h = (HorizontalLayout) component;
			return (ComboBox) h.getComponent(0);
		}
	}

	private void pasteSelectionToColumn(Table t, String propertyName, Object selection, int curRow) {
		for (int i = curRow; i <= (t.getItemIds().size()); i++) {
			ComboBox b = parseBoxRow(t, i, propertyName);
			if (selection != null)
				b.setValue(selection);
		}
	}

	public List<Map<String, String>> getNminProperties() {
		List<Map<String, String>> res = new ArrayList<Map<String, String>>();
		for (Object id : nminSamples.getItemIds()) {

			Map<String, String> res1 = new HashMap<String, String>();
			Item item = nminSamples.getItem(id);
			Object component = item.getItemProperty("Soil depth [cm]").getValue();
			HorizontalLayout h = (HorizontalLayout) component;
			ComboBox cb = (ComboBox) h.getComponent(0);
			String depth = (String) cb.getValue();
			res1.put("Q_CFH_NMIN_DEPTH", depth);
			if (depth.equals("0-30CM")) {
				res1.put("Q_CFH_NMIN_DENSITY", "1,3");
			} else
				res1.put("Q_CFH_NMIN_DENSITY", "1,5");
			res.add(res1);
		}

		return res;

	}
	
	public boolean isValid() {
		boolean fieldcheck = true;
		
		for (Object id : nminSamples.getItemIds()) {
			Item item = nminSamples.getItem(id);
			Object component = item.getItemProperty("Soil depth [cm]").getValue();
			HorizontalLayout h = (HorizontalLayout) component;
			ComboBox cb = (ComboBox) h.getComponent(0);
			String depth = (String) cb.getValue();
		
			try {
				if(depth.isEmpty()) {
					fieldcheck= false;
				}
			}
			catch(NullPointerException e) {
				Styles.notification("Missing information",
						"Please update depth for your samples.", NotificationType.ERROR);
				fieldcheck= false;
			}
		}
		
		
		return fieldcheck;
	}

}
