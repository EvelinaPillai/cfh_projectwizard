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

import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import life.qbic.projectwizard.io.DBVocabularies;
import matrix.ChemElement;
import matrix.PeriodicTable;

public class AminoAcidPanel extends VerticalLayout {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9105358366778311786L;

	private ExtractionPanel extractionPanel;

	public AminoAcidPanel(DBVocabularies vocabs) {

		setSpacing(true);
		setMargin(true);
		this.setCaption("Elements to analyze");

		// TODO different devices and maybe different extractions
		extractionPanel = new ExtractionPanel(vocabs.getExtractions(), vocabs.getDevices());

		List<ChemElement> elements = new ArrayList<ChemElement>();
		elements.add(new ChemElement("GLY", "Glycine", 1, 1, 1));
		elements.add(new ChemElement("ASP", "Aspartic Acid", 1, 7, 2));
		elements.add(new ChemElement("ALA", "Alanine", 2, 1, 3));
		elements.add(new ChemElement("GLU", "Glutamic Acid", 2, 7, 4));
		elements.add(new ChemElement("LEU", "Leucine", 3, 1, 5));
		elements.add(new ChemElement("HIS", "Histidine", 3, 7, 6));
		elements.add(new ChemElement("VAL", "Valine", 4, 1, 7));
		elements.add(new ChemElement("PHE", "Phenylalanine", 4, 2, 8));
		elements.add(new ChemElement("TYR", "Tyrosine", 4, 3, 9));
		elements.add(new ChemElement("MET", "Methionine", 4, 4, 10));
		elements.add(new ChemElement("THR", "Threonine", 4, 5, 11));
		elements.add(new ChemElement("ASN", "Asparagine", 4, 6, 12));
		elements.add(new ChemElement("LYS", "Lysine", 4, 7, 13));
		elements.add(new ChemElement("ILE", "Isoleucine", 5, 1, 7));
		elements.add(new ChemElement("TRP", "Tryptophan", 5, 2, 8));
		elements.add(new ChemElement("PRO", "Proline", 5, 3, 9));
		elements.add(new ChemElement("CYS", "Cysteine", 5, 4, 10));
		elements.add(new ChemElement("SER", "Serine", 5, 5, 11));
		elements.add(new ChemElement("GLN", "Glutamine", 5, 6, 12));
		elements.add(new ChemElement("ARG", "Arginine", 5, 7, 13));

		PeriodicTable table = new PeriodicTable(this);
		table.setElements(elements);

		addComponent(extractionPanel);

		addComponent(table);

	}

	public void useSelectedElement(ChemElement element) {
		List<TextField> list = extractionPanel.getElements();

		for (int i = 0; i < list.size(); i++) {
			TextField t = list.get(i);
			// t.focus();
			if (extractionPanel.status.get(i)) {

				String currentElement = t.getValue();
				String newElemntlist = currentElement + ", " + element.getAbbreviation();
				// remove first comma
				newElemntlist = newElemntlist.startsWith(",") ? newElemntlist.substring(1) : newElemntlist;
				t.setValue(newElemntlist);
			}

		}
	}

}
