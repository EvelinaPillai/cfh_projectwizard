/*******************************************************************************
 * QBiC Project Wizard enables users to create hierarchical experiments including different study conditions using factorial design.
 * Copyright (C) "2016"  Andreas Friedrich
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package life.qbic.projectwizard.uicomponents;

import java.util.List;

import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.VerticalLayout;

import life.qbic.portal.Styles;

public class ExtractionChooser extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3474097386605654657L;

	private ComboBox chooser;
	private ComboBox devChooser;

	public ExtractionChooser(List<String> options, List<String> devices) {
		chooser = new ComboBox("Digestion / Extraction Type");
		chooser.addItems(options);
		chooser.setFilteringMode(FilteringMode.CONTAINS);
		// chooser.setNullSelectionAllowed(false);
		chooser.setStyleName(Styles.boxTheme);
		
		devChooser = new ComboBox("Machine Type");
		devChooser.addItems(devices);
		devChooser.setFilteringMode(FilteringMode.CONTAINS);
		// devChooser.setNullSelectionAllowed(false);
		devChooser.setStyleName(Styles.boxTheme);
		
		addComponent(chooser);
		addComponent(devChooser);
		setSpacing(true);
	}

	public boolean chooserSet() {
		return chooser.getValue() != null;
	}

	public boolean isSet() {
		return chooser.getItemIds().contains(chooser.getValue());
	}

	public String getExtraction() {
		if (chooser.getValue() != null)
			return chooser.getValue().toString();
		else
			return null;
	}
	
	public String getDevice() {
		if (devChooser.getValue() != null)
			return devChooser.getValue().toString();
		else
			return null;
	}

	public void reset() {
		chooser.setValue(chooser.getNullSelectionItemId());
		devChooser.setValue(devChooser.getNullSelectionItemId());
	}
}
