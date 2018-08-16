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

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;

import life.qbic.portal.Styles;
import life.qbic.portal.Styles.NotificationType;
import life.qbic.projectwizard.io.DBVocabularies;
import matrix.ChemElement;
import matrix.PeriodicTable;

/**
 * UI Component to put information about the Samples for Amino Acid Analysis
 *
 */
//TODO it would make sense to make an interface for AA and Element bc two methods are identical
public class AminoAcidPanel extends HorizontalLayout {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9105358366778311786L;

	private ExtractionPanel extractionPanel;
	private List<String> allAAs = new ArrayList<String>();

	public AminoAcidPanel(DBVocabularies vocabs) {

		setSpacing(true);
		setMargin(true);
		this.setCaption("Amino acids to analyze");

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

		for (ChemElement chemEl : elements) {
			allAAs.add(chemEl.getAbbreviation());
		}

		addComponent(extractionPanel);

		addComponent(table);

	}

	/**
	 * Fills out the text field "Selected Elements" for Amino Acid Analysis
	 * Experiment. By pressing on an amino acid on the periodic table the text field
	 * is filled out.
	 * 
	 * @param element
	 */
	public void useSelectedElement(ChemElement element) {
		for (int i = 0; i < extractionPanel.getElements().size(); i++) {
			TextField t = extractionPanel.getElements().get(i);
			if (extractionPanel.status.get(i)) {

				String currentElement = t.getValue();
				if (!currentElement.contains(element.getAbbreviation())) {
					String newElemntlist = currentElement + ", " + element.getAbbreviation();
					// remove first comma
					newElemntlist = newElemntlist.startsWith(",") ? newElemntlist.substring(1) : newElemntlist;
					t.setValue(newElemntlist);
				}
			}
		}
	}

	/**
	 * Validation if "Machine type" and "Selected Elements" are filled out properly.
	 * 
	 * @return
	 */
	public boolean isValid() {
		boolean fieldcheck = true;

		if (extractionPanel.getExtractions().isEmpty()) {
			extractionPanel.getExtractions().add(" "); // eg. no digestion is necessary if matrix was solution
		}
		if (extractionPanel.isDevicesEmpty()) {
			fieldcheck = false;
			Styles.notification("Missing information", "Please select one measuring device for your samples.",
					NotificationType.ERROR);
		}
		if (extractionPanel.isElementsEmpty()) {
			fieldcheck = false;
			Styles.notification("Missing information", "Please select at least one element you want to measure.",
					NotificationType.ERROR);
		}
		// if someone typed something in instead of using the periodic table we check
		// for entries
		for (TextField t : extractionPanel.getElements()) {
			String[] enteredText = t.getValue().split(",");
			for (String elements : enteredText) {
				if (!this.allAAs.contains(elements.trim())) {
					fieldcheck = false;
					Styles.notification("Wrong information",
							"The element you entered is not represented in the periodic table, please correct your input.",
							NotificationType.ERROR);
				}
			}
		}
		return fieldcheck;
	}

}
