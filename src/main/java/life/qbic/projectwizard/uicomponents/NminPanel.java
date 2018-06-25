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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.ui.Label;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
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

	private Map<String, String> soildepth; // TODO do we really need it in openBIS or can we define it here because it
											// wont change in the future

	private Table nminSamples;

	private static final Logger logger = LogManager.getLogger(NminPanel.class);

	public NminPanel(DBVocabularies vocabs, int noSamples) {
		this.setCaption("Nmin Options");
		this.soildepth = vocabs.getAntibodiesMap();
		setSpacing(true);
		this.nminSamples = new Table();

		nminSamples.setStyleName(Styles.tableTheme);
		nminSamples.addContainerProperty("Sample", String.class, null); // sample name
		nminSamples.addContainerProperty("Soil depth [cm]", ComboBox.class, null);
		nminSamples.addContainerProperty("Bulk density [kg/l]", Label.class, null); // Lagerungsdichte

		addComponent(nminSamples);
	}

	private ComboBox generateTableSoildepthBox() {
		ComboBox b = new ComboBox();
		b.setWidth("100px");
		b.addItems(this.soildepth.keySet());
		b.setStyleName(Styles.boxTheme);
		return b;
	}

	private Label generateTableLabel() {
		Label l = new Label();
		l.setImmediate(true);
		l.setWidth("55px");
		return l;
	}
	
	
	public void setNminSamples(int noSamples) { //List<AOpenbisSample> extracts
	    nminSamples.removeAllItems();
	    //tableIdToBarcode = new HashMap<Integer, String>();
	    int i = 0;
	   // for (AOpenbisSample s : extracts) {
	    for (int s=0; s<noSamples; s++) {
	      i++;
	      //tableIdToBarcode.put(i, s.getCode());

	      List<Object> row = new ArrayList<Object>();

	      //row.add(s.getQ_SECONDARY_NAME());
	      //row.add(generateTableIntegerInput());
	      row.add(generateTableSoildepthBox());
	      row.add(generateTableLabel());

	      nminSamples.addItem(row.toArray(new Object[row.size()]), i);
	    }
	    nminSamples.setPageLength(noSamples);
	  }

}
