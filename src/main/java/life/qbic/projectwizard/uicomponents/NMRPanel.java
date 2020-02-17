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

import com.vaadin.ui.ComboBox;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;

import life.qbic.datamodel.samples.AOpenbisSample;

/**
 * Core Facility Hohenheim (CFH) - Collect NMR Experiment information
 * just a description field
 */

public class NMRPanel extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2375779845543701025L;

	private TextArea description;
	
	private static final Logger logger = LogManager.getLogger(NMRPanel.class);
	
	
	public NMRPanel() {
		// CFH Specific info required for module 1 proteins
		description = new TextArea("Sample Description");
		addComponent(description);
	}
	
	public boolean usesDescription() {
		return !description.getValue().isEmpty();
	}

	public String getDescription() {
		return description.getValue().toString();
	}
	
	public void selectDescription(String option) {
		if (option.isEmpty()) {
			description.setNullSettingAllowed(true);
			description.setValue(description.getNullRepresentation());
			description.setNullSettingAllowed(false);
		} else {
			description.setValue(option);
		}
	}
	
	public boolean isValid() {
		return true;
	}
	

}
