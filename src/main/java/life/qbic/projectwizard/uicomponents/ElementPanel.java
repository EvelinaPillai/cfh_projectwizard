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

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import matrix.ChemElement;
import matrix.PeriodicTable;
import life.qbic.portal.Styles;
import life.qbic.portal.Styles.NotificationType;
import life.qbic.projectwizard.io.DBVocabularies;

/**
 * UI Component to put information about the Samples for Element Analysis
 *
 */
//TODO it would make sense to make an interface for AA and Element bc two methods are identical
public class ElementPanel extends HorizontalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7227082472253643451L;

	private ExtractionPanel extractionPanel;
	private List<String> allElements = new ArrayList<String>();

	public ElementPanel(DBVocabularies vocabs) {

		setSpacing(true);
		setMargin(true);
		this.setCaption("Elements to analyze");

		extractionPanel = new ExtractionPanel(vocabs.getExtractions(), vocabs.getDevices());

		List<ChemElement> elements = new ArrayList<ChemElement>();
		elements.add(new ChemElement("H", "Hydrogen", 1, 1, 1));
		elements.add(new ChemElement("He", "Helium", 1, 18, 2));
		elements.add(new ChemElement("Li", "Lithium", 2, 1, 3));
		elements.add(new ChemElement("Be", "Beryllium", 2, 2, 4));
		elements.add(new ChemElement("B", "Boron", 2, 13, 5));
		elements.add(new ChemElement("C", "Carbon", 2, 14, 6));
		elements.add(new ChemElement("N", "Nitrogen", 2, 15, 7));
		elements.add(new ChemElement("O", "Oxygen", 2, 16, 8));
		elements.add(new ChemElement("F", "Fluorine", 2, 17, 9));
		elements.add(new ChemElement("Ne", "Neon", 2, 18, 10));
		elements.add(new ChemElement("Na", "Sodium", 3, 1, 11));
		elements.add(new ChemElement("Mg", "Magnesium", 3, 2, 12));
		elements.add(new ChemElement("Al", "Aluminium", 3, 13, 13));
		elements.add(new ChemElement("Si", "Silicon", 3, 14, 14));
		elements.add(new ChemElement("P", "Phosphorus", 3, 15, 15));
		elements.add(new ChemElement("S", "Sulfur", 3, 16, 16));
		elements.add(new ChemElement("Cl", "Chlorine", 3, 17, 17));
		elements.add(new ChemElement("Ar", "Argon", 3, 18, 18));
		elements.add(new ChemElement("K", "Potassium", 4, 1, 19));
		elements.add(new ChemElement("Ca", "Calcium", 4, 2, 20));
		elements.add(new ChemElement("Sc", "Scandium", 4, 3, 21));
		elements.add(new ChemElement("Ti", "Titanium", 4, 4, 22));
		elements.add(new ChemElement("V", "Vanadium", 4, 5, 23));
		elements.add(new ChemElement("Cr", "Chromium", 4, 6, 24));
		elements.add(new ChemElement("Mn", "Manganese", 4, 7, 25));
		elements.add(new ChemElement("Fe", "Iron", 4, 8, 26));
		elements.add(new ChemElement("Co", "Cobalt", 4, 9, 27));
		elements.add(new ChemElement("Ni", "Nickel", 4, 10, 28));
		elements.add(new ChemElement("Cu", "Copper", 4, 11, 29));
		elements.add(new ChemElement("Zn", "Zinc", 4, 12, 30));
		elements.add(new ChemElement("Ga", "Gallium", 4, 13, 31));
		elements.add(new ChemElement("Ge", "Germanium", 4, 14, 32));
		elements.add(new ChemElement("As", "Arsenic", 4, 15, 33));
		elements.add(new ChemElement("Se", "Selenium", 4, 16, 34));
		elements.add(new ChemElement("Br", "Bromine", 4, 17, 35));
		elements.add(new ChemElement("Kr", "Krypton", 4, 18, 36));
		elements.add(new ChemElement("Rb", "Rubidium", 5, 1, 37));
		elements.add(new ChemElement("Sr", "Strontium", 5, 2, 38));
		elements.add(new ChemElement("Y", "Yttrium", 5, 3, 39));
		elements.add(new ChemElement("Zr", "Zirconium", 5, 4, 40));
		elements.add(new ChemElement("Nb", "Niobium", 5, 5, 41));
		elements.add(new ChemElement("Mo", "Molybdenum", 5, 6, 42));
		elements.add(new ChemElement("Tc", "Technetium", 5, 7, 43));
		elements.add(new ChemElement("Ru", "Ruthenium", 5, 8, 44));
		elements.add(new ChemElement("Rh", "Rhodium", 5, 9, 45));
		elements.add(new ChemElement("Pd", "Palladium", 5, 10, 46));
		elements.add(new ChemElement("Ag", "Silver", 5, 11, 47));
		elements.add(new ChemElement("Cd", "Cadmium", 5, 12, 48));
		elements.add(new ChemElement("In", "Indium", 5, 13, 49));
		elements.add(new ChemElement("Sn", "Tin", 5, 14, 50));
		elements.add(new ChemElement("Sb", "Antimony", 5, 15, 51));
		elements.add(new ChemElement("Te", "Tellurium", 5, 16, 52));
		elements.add(new ChemElement("I", "Iodine", 5, 17, 53));
		elements.add(new ChemElement("Xe", "Xenon", 5, 18, 54));
		elements.add(new ChemElement("Cs", "Cesium", 6, 1, 55));
		elements.add(new ChemElement("Ba", "Barium", 6, 2, 56));
		elements.add(new ChemElement("La", "Lanthanum", 6, 3, 57));
		elements.add(new ChemElement("Hf", "Hafnium", 6, 4, 72));
		elements.add(new ChemElement("Ta", "Tantalum", 6, 5, 73));
		elements.add(new ChemElement("W", "Tungsten", 6, 6, 74));
		elements.add(new ChemElement("Re", "Rhenium", 6, 7, 75));
		elements.add(new ChemElement("Os", "Osmium", 6, 8, 76));
		elements.add(new ChemElement("Ir", "Irdium", 6, 9, 77));
		elements.add(new ChemElement("Pt", "Platinum", 6, 10, 78));
		elements.add(new ChemElement("Au", "Gold", 6, 11, 79));
		elements.add(new ChemElement("Hg", "Mercury", 6, 12, 80));
		elements.add(new ChemElement("Tl", "Thallium", 6, 13, 81));
		elements.add(new ChemElement("Pb", "Lead", 6, 14, 82));
		elements.add(new ChemElement("Bi", "Bismuth", 6, 15, 83));
		elements.add(new ChemElement("Po", "Polonium", 6, 16, 84));
		elements.add(new ChemElement("At", "Astatine", 6, 17, 85));
		elements.add(new ChemElement("Rn", "Radon", 6, 18, 86));
		elements.add(new ChemElement("Fr", "Francium", 7, 1, 87));
		elements.add(new ChemElement("Ra", "Radium", 7, 2, 88));
		elements.add(new ChemElement("Ac", "Actinium", 7, 3, 89));
		elements.add(new ChemElement("Rf", "Rutherfordium", 7, 4, 104));
		elements.add(new ChemElement("Db", "Dubnium", 7, 5, 105));
		elements.add(new ChemElement("Sg", "Seaborgium", 7, 6, 106));
		elements.add(new ChemElement("Bh", "Bohrium", 7, 7, 107));
		elements.add(new ChemElement("Hs", "Hassium", 7, 8, 108));
		elements.add(new ChemElement("Mt", "Meitnerium", 7, 9, 109));
		elements.add(new ChemElement("Ds", "Darmstadtium", 7, 10, 110));
		elements.add(new ChemElement("Rg", "Roentgenium", 7, 11, 111));
		elements.add(new ChemElement("Cn", "Copernicum", 7, 12, 112));
		elements.add(new ChemElement("Nh", "Nihonium", 7, 13, 113));
		elements.add(new ChemElement("Fl", "Flerovium", 7, 14, 114));
		elements.add(new ChemElement("Mc", "Moscovium", 7, 15, 115));
		elements.add(new ChemElement("Lv", "Livermorium", 7, 16, 116));
		elements.add(new ChemElement("Ts", "Tennessine", 7, 17, 117));
		elements.add(new ChemElement("Og", "Oganesson", 7, 18, 118));
		elements.add(new ChemElement("Ce", "Cerium", 8, 4, 58));
		elements.add(new ChemElement("Pr", "Praseodymium", 8, 5, 59));
		elements.add(new ChemElement("Nd", "Neodymium", 8, 6, 60));
		elements.add(new ChemElement("Pm", "Promethium", 8, 7, 61));
		elements.add(new ChemElement("Sm", "Samarium", 8, 8, 62));
		elements.add(new ChemElement("Eu", "Europium", 8, 9, 63));
		elements.add(new ChemElement("Gd", "Gadolinium", 8, 10, 64));
		elements.add(new ChemElement("Tb", "Terbium", 8, 11, 65));
		elements.add(new ChemElement("Dy", "Dysprosium", 8, 12, 66));
		elements.add(new ChemElement("Ho", "Holmium", 8, 13, 67));
		elements.add(new ChemElement("Er", "Erbium", 8, 14, 68));
		elements.add(new ChemElement("Tm", "Thulium", 8, 15, 69));
		elements.add(new ChemElement("Yb", "Ytterbium", 8, 16, 70));
		elements.add(new ChemElement("Lu", "Lutetium", 8, 17, 71));
		elements.add(new ChemElement("Th", "Thorium", 9, 4, 90));
		elements.add(new ChemElement("Pa", "Protactinium", 9, 5, 91));
		elements.add(new ChemElement("U", "Uranium", 9, 6, 92));
		elements.add(new ChemElement("Np", "Neptunium", 9, 7, 93));
		elements.add(new ChemElement("Pu", "Plutonium", 9, 8, 94));
		elements.add(new ChemElement("Am", "Americium", 9, 9, 95));
		elements.add(new ChemElement("Cm", "Curium", 9, 10, 96));
		elements.add(new ChemElement("Bk", "Berkelium", 9, 11, 97));
		elements.add(new ChemElement("Cf", "Californium", 9, 12, 98));
		elements.add(new ChemElement("Es", "Einsteinium", 9, 13, 99));
		elements.add(new ChemElement("Fm", "Fermium", 9, 14, 100));
		elements.add(new ChemElement("Md", "Mendelevium", 9, 15, 101));
		elements.add(new ChemElement("No", "Nobelium", 9, 16, 102));
		elements.add(new ChemElement("Lr", "Lawrencium", 9, 17, 103));

		PeriodicTable table = new PeriodicTable(this);
		table.setElements(elements);

		for (ChemElement chemEl : elements) {
			allElements.add(chemEl.getAbbreviation());
		}

		addComponent(extractionPanel);
		addComponent(table);
	}

	/**
	 * Fills out the text field "Selected Elements" for Element Analysis Experiment.
	 * By pressing on an element on the periodic table the text field is filled out.
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
	 * Mapping the selected entries for digestion, machine type and selected
	 * elements to openBIS property types this information is important for the tsv
	 * creation
	 * 
	 * @return
	 */
	public List<Map<String, String>> getElementProperties() {
		List<Map<String, String>> res = new ArrayList<Map<String, String>>();
		for (int i = 0; i < extractionPanel.getElements().size(); i++) {

			Map<String, String> res1 = new HashMap<String, String>();
			if (extractionPanel.getExtractions().isEmpty()) {
				res1.put("Q_CFH_DIGESTION", "");
			} else {
				res1.put("Q_CFH_DIGESTION", extractionPanel.getExtractions().get(i).toString());
			}
			res1.put("Q_ELEMENT_DESC", extractionPanel.getElements().get(i).getValue());
			res1.put("Q_CFH_DEVICES", extractionPanel.getDevices().get(i));
			res.add(res1);
		}
		return res;
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
				if (!this.allElements.contains(elements.trim())) {
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
