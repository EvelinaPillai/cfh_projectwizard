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
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.data.Item;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.ValoTheme;

import life.qbic.datamodel.samples.AOpenbisSample;
import life.qbic.portal.Styles;
import life.qbic.portal.Styles.NotificationType;
import life.qbic.portal.portlet.ProjectWizardUI;
import life.qbic.projectwizard.io.DBVocabularies;
import life.qbic.projectwizard.model.TestSampleInformation;

/**
 * Core Facility Hohenheim (CFH) - Collect NMR Sample Details
 */

public class NMRPanel extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2375779845543701025L;

	private Table nmrSamples;
	private DBVocabularies vocabs;
	private HashMap<Integer, List<String>> nmrTypeMap;
	private HashMap<Integer, String> probes;
	private HashMap<Integer, String> solvents;
	private HashMap<Integer, String> storages;
	private HashMap<Integer, String> otherExpTypes;

	private static final Logger logger = LogManager.getLogger(NMRPanel.class);

	public NMRPanel(DBVocabularies vocabs, List<TestSampleInformation> list) {
		this.setCaption("NMR Options");
		setSpacing(false);

		// TABLE setup
		this.nmrSamples = new Table();
		this.vocabs = vocabs;
		nmrTypeMap = new HashMap<Integer, List<String>>();
		probes = new HashMap<Integer, String>();
		solvents = new HashMap<Integer, String>();
		storages = new HashMap<Integer, String>();
		otherExpTypes = new HashMap<Integer, String>();
		nmrSamples.setStyleName(Styles.tableTheme);

		nmrSamples.addContainerProperty("Sample", String.class, null); // sample name
		nmrSamples.addContainerProperty("Experiment Type", Component.class, null);
		nmrSamples.addContainerProperty("Experiment Description", Component.class, null);
		nmrSamples.addContainerProperty("Solvent", Component.class, null);
		nmrSamples.addContainerProperty("Concentration", Component.class, null);
		nmrSamples.addContainerProperty("Volume  [\u03BCl]", Component.class, null);
		nmrSamples.addContainerProperty("pH", Component.class, null);
		nmrSamples.addContainerProperty("Buffer", Component.class, null);
		nmrSamples.addContainerProperty("Date", Component.class, null);
		nmrSamples.addContainerProperty("Storage", Component.class, null);
		nmrSamples.addContainerProperty("Quantitation", Component.class, null);
		nmrSamples.addContainerProperty("Sample Description", Component.class, null);
		nmrSamples.addContainerProperty("NMR Spectrometer", Component.class, null);
		nmrSamples.addContainerProperty("Probe", Component.class, "");
		nmrSamples.addContainerProperty("Temperature [K]", Component.class, null);

		nmrSamples.setColumnWidth("Experiment Type", 135);
		nmrSamples.setColumnWidth("Experiment Description", 200);
		nmrSamples.setColumnWidth("Solvent", 135);
		nmrSamples.setColumnWidth("Concentration", 150);
		nmrSamples.setColumnWidth("Volume  [\u03BCl]", 150);
		nmrSamples.setColumnWidth("pH", 150);
		nmrSamples.setColumnWidth("Buffer", 200);
		nmrSamples.setColumnWidth("Date", 200);
		nmrSamples.setColumnWidth("Storage", 135);
		nmrSamples.setColumnWidth("Quantitation", 135);
		nmrSamples.setColumnWidth("Sample Description", 200);
		nmrSamples.setColumnWidth("NMR Spectrometer", 135);
		nmrSamples.setColumnWidth("Probe", 135);
		nmrSamples.setColumnWidth("Temperature [K]", 130);

		addComponent(nmrSamples);

	}

	private ComboBox generateTableBox(Collection<String> entries, String width) {
		ComboBox b = new ComboBox();
		b.addItems(entries);
		b.setWidth(width);
		b.setFilteringMode(FilteringMode.CONTAINS);
		b.setStyleName(Styles.boxTheme);
		return b;
	}

	/**
	 * 
	 * 
	 * @param extracts
	 */
	public void setNmrSamples(List<AOpenbisSample> extracts) {
		nmrSamples.removeAllItems();

		int i = 0;
		for (AOpenbisSample s : extracts) {
			i++;
			boolean complexRow = i == 1; // the first row contains a combobox with added button to copy
			// its selection to the whole column

			List<Object> row = new ArrayList<Object>();
			row.add(s.getQ_SECONDARY_NAME());

			List<String> nmrTypes = vocabs.getNMRTypes();
			Collections.sort(nmrTypes);
			ComboBox nmrTypesBox = generateTableBox(nmrTypes, "105px");
			nmrTypesBox.removeAllItems();
			nmrTypesBox.addItem("[Multiple]");
			nmrTypesBox.addItems(nmrTypes);
			nmrTypesBox.setFilteringMode(FilteringMode.CONTAINS);
			if (complexRow)
				row.add(createComplexCellComponent(nmrSamples, nmrTypesBox, "Experiment Type", i));
			else
				row.add(nmrTypesBox);

			final int rowNum = i;
			nmrTypesBox.addValueChangeListener(new ValueChangeListener() {

				@Override
				public void valueChange(ValueChangeEvent event) {
					Object newVal = nmrTypesBox.getValue();
					if (newVal == null)
						newVal = "";
					else if (newVal.equals("[Multiple]"))
						createNmrTypesSelectionWindow(rowNum);
					else if (newVal.equals("OTHER"))
						createOtherSelectionWindow("Experiment Type", rowNum, otherExpTypes);
					else if (!newVal.equals("Custom"))
						nmrTypesBox.removeItem("Custom");
				}
			});

			TextArea expDesc = new TextArea();
			expDesc.setWidth("150");
			if (complexRow)
				row.add(createComplexCellTextAreaComponent(nmrSamples, expDesc, "Experiment Description", i));
			else
				row.add(expDesc);

			List<String> solvent = vocabs.getSolvents();
			Collections.sort(solvent);
			ComboBox solventBox = generateTableBox(solvent, "105px");
			solventBox.setValue("D2O");
			if (complexRow)
				row.add(createComplexCellComponent(nmrSamples, solventBox, "Solvent", i));
			else
				row.add(solventBox);

			solventBox.addValueChangeListener(new ValueChangeListener() {

				@Override
				public void valueChange(ValueChangeEvent event) {
					Object newVal = solventBox.getValue();
					if (newVal == null)
						newVal = "";
					else if (newVal.equals("OTHER"))
						createOtherSelectionWindow("Solvent", rowNum, solvents);
					else if (!newVal.equals("Custom"))
						solventBox.removeItem("Custom");
				}
			});

			TextField concentration = new TextField();
			concentration.setWidth("120");

			if (complexRow)
				row.add(createComplexCellTextComponent(nmrSamples, concentration, "Concentration", i));
			else
				row.add(concentration);

			TextField volume = new TextField();
			volume.setWidth("120");
			volume.setValue("600");

			if (complexRow)
				row.add(createComplexCellTextComponent(nmrSamples, volume, "Volume  [\u03BCl]", i));
			else
				row.add(volume);

			TextField pH = new TextField();
			pH.setWidth("100");

			if (complexRow)
				row.add(createComplexCellTextComponent(nmrSamples, pH, "pH", i));
			else
				row.add(pH);

			TextField buffer = new TextField();
			buffer.setWidth("150");
			if (complexRow)
				row.add(createComplexCellTextComponent(nmrSamples, buffer, "Buffer", i));
			else
				row.add(buffer);

			TextField date = new TextField();
			date.setWidth("150");
			if (complexRow)
				row.add(createComplexCellTextComponent(nmrSamples, date, "Date", i));
			else
				row.add(date);

			List<String> storage = vocabs.getStorage();
			Collections.sort(storage);
			ComboBox storageBox = generateTableBox(storage, "105px");
			if (complexRow)
				row.add(createComplexCellComponent(nmrSamples, storageBox, "Storage", i));
			else
				row.add(storageBox);

			storageBox.addValueChangeListener(new ValueChangeListener() {

				@Override
				public void valueChange(ValueChangeEvent event) {
					Object newVal = storageBox.getValue();
					if (newVal == null)
						newVal = "";
					else if (newVal.equals("OTHER"))
						createOtherSelectionWindow("Storage", rowNum, storages);
					else if (!newVal.equals("Custom"))
						storageBox.removeItem("Custom");
				}
			});

			List<String> quantitation = vocabs.getQuantitation();
			Collections.sort(quantitation);
			ComboBox quantitationBox = generateTableBox(quantitation, "105px");
			if (complexRow)
				row.add(createComplexCellComponent(nmrSamples, quantitationBox, "Quantitation", i));
			else
				row.add(quantitationBox);

			TextArea sampleDesc = new TextArea();
			sampleDesc.setWidth("150");
			if (complexRow)
				row.add(createComplexCellTextAreaComponent(nmrSamples, sampleDesc, "Sample Description", i));
			else
				row.add(sampleDesc);

			List<String> spectrometer = vocabs.getSpectrometers();
			Collections.sort(spectrometer);
			ComboBox spectrometerBox = generateTableBox(spectrometer, "105px");
			spectrometerBox.setValue("600_MHZ");
			if (complexRow)
				row.add(createComplexCellComponent(nmrSamples, spectrometerBox, "NMR Spectrometer", i));
			else
				row.add(spectrometerBox);

			List<String> probe = vocabs.getProbes();
			Collections.sort(probe);
			ComboBox probeBox = generateTableBox(probe, "105px");
			probeBox.setValue("CRYO_PRODIGY_BBO");
			if (complexRow)
				row.add(createComplexCellComponent(nmrSamples, probeBox, "Probe", i));
			else
				row.add(probeBox);

			probeBox.addValueChangeListener(new ValueChangeListener() {

				@Override
				public void valueChange(ValueChangeEvent event) {
					Object newVal = probeBox.getValue();
					if (newVal == null)
						newVal = "";
					else if (newVal.equals("OTHER"))
						createOtherSelectionWindow("Probe", rowNum, probes);
					else if (!newVal.equals("Custom"))
						probeBox.removeItem("Custom");
				}
			});

			TextField temperature = new TextField();
			temperature.setWidth("95");
			temperature.setValue("298");
			if (complexRow)
				row.add(createComplexCellTextComponent(nmrSamples, temperature, "Temperature [K]", i));
			else
				row.add(temperature);

			nmrSamples.addItem(row.toArray(new Object[row.size()]), i);
		}

		nmrSamples.setPageLength(nmrSamples.size());

	}

	protected void createNmrTypesSelectionWindow(int row) {
		Window subWindow = new Window("Experiment Type selection");
		subWindow.setWidth("400px");

		VerticalLayout layout = new VerticalLayout();
		layout.setSpacing(true);
		layout.setMargin(true);
		// dont show Other as option
		List<String> vocab = vocabs.getNMRTypes();
		int indexOfOther = vocab.indexOf("OTHER");
		if (indexOfOther >= 0) {
			vocab.remove(indexOfOther);
		}
		EnzymePanel pan = new EnzymePanel(vocab, "NMR Experiment Type"); // used Enzyme Panel as has same
																			// option should be abstracted
		Button ok = new Button("Okay.");
		ok.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				List<String> nmrTypes = pan.getEnzymes();
				ComboBox b = parseBoxRow(nmrSamples, row, "Experiment Type");
				if (nmrTypes.isEmpty()) {
					Styles.notification("No experiment type selected", "Please select at least one experiment type!",
							NotificationType.ERROR);
				} else if (nmrTypes.size() == 1) {
					b.setValue(nmrTypes.get(0));
					subWindow.close();
				} else {
					b.addItem("Custom");
					b.setValue("Custom");
					nmrTypeMap.put(row, nmrTypes);
					subWindow.close();
				}
			}
		});
		layout.addComponent(pan);
		layout.addComponent(ok);

		subWindow.setContent(layout);
		// Center it in the browser window
		subWindow.center();
		subWindow.setModal(true);
		subWindow.setIcon(FontAwesome.FLASK);
		subWindow.setResizable(false);
		ProjectWizardUI ui = (ProjectWizardUI) UI.getCurrent();
		ui.addWindow(subWindow);
	}

	protected void createOtherSelectionWindow(String selection, int row, HashMap<Integer, String> list) {
		Window subWindow = new Window(selection + " selection");
		subWindow.setWidth("400px");

		VerticalLayout layout = new VerticalLayout();
		layout.setSpacing(true);
		layout.setMargin(true);
		TextField other = new TextField();
		Button ok = new Button("Okay.");
		ok.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				String entry = other.getValue();
				ComboBox b = parseBoxRow(nmrSamples, row, selection);
				if (entry.isEmpty()) {
					Styles.notification("Nothing entered", "Please type some information!", NotificationType.ERROR);
				} else {
					b.addItem("Custom");
					b.setValue("Custom");
					list.put(row, entry);
					subWindow.close();
				}
			}
		});
		layout.addComponent(other);
		layout.addComponent(ok);

		subWindow.setContent(layout);
		// Center it in the browser window
		subWindow.center();
		subWindow.setModal(true);
		subWindow.setIcon(FontAwesome.FLASK);
		subWindow.setResizable(false);
		ProjectWizardUI ui = (ProjectWizardUI) UI.getCurrent();
		ui.addWindow(subWindow);
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
				pasteSelectionToColumn(t, propertyName, selection);
			}
		});
		return complexComponent;
	}

	private Object createComplexCellTextComponent(Table t, TextField textField, String propertyName, final int rowID) {
		HorizontalLayout complexComponent = new HorizontalLayout();
		complexComponent.setWidth(textField.getWidth() + 10, textField.getWidthUnits());
		complexComponent.addComponent(textField);
		complexComponent.setExpandRatio(textField, 1);

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
				TextField b = parseTextRow(t, rowID, propertyName);
				Object selection = b.getValue();
				pasteSelectionToText(t, propertyName, selection);
			}
		});
		return complexComponent;
	}

	private Object createComplexCellTextAreaComponent(Table t, TextArea textArea, String propertyName,
			final int rowID) {
		HorizontalLayout complexComponent = new HorizontalLayout();
		complexComponent.setWidth(textArea.getWidth() + 10, textArea.getWidthUnits());
		complexComponent.addComponent(textArea);
		complexComponent.setExpandRatio(textArea, 1);

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
				TextArea b = parseTextAreaRow(t, rowID, propertyName);
				Object selection = b.getValue();
				pasteSelectionToTextArea(t, propertyName, selection);
			}
		});
		return complexComponent;
	}

	private void pasteSelectionToColumn(Table t, String propertyName, Object selection) {
		for (Object id : t.getItemIds()) {
			// should always be ID = 1
			ComboBox b = parseBoxRow(t, id, propertyName);
			if (selection != null && selection.equals("Custom")) {
				Integer i = (int) id;
				if (propertyName.equals("Experiment Type")) {
					if (nmrTypeMap.get(1) != null)
						nmrTypeMap.put(i, nmrTypeMap.get(1));
					else
						otherExpTypes.put(i, otherExpTypes.get(1));
				}
				if (propertyName.equals("Probe")) {
					probes.put(i, probes.get(1));

				}
				if (propertyName.equals("Solvent")) {
					solvents.put(i, solvents.get(1));

				}
				if (propertyName.equals("Storage")) {
					storages.put(i, storages.get(1));

				}

				b.addItem("Custom");
			}
			if (b.isEnabled())// check if this value should be set
				b.setValue(selection);
		}
	}

	private void pasteSelectionToText(Table t, String propertyName, Object selection) {
		for (Object id : t.getItemIds()) {
			TextField b = parseTextRow(t, id, propertyName);
			if (selection != null) {
				b.setValue(selection.toString());
			}
			if (b.isEnabled())// check if this value should be set
				b.setValue(selection.toString());
		}
	}

	private void pasteSelectionToTextArea(Table t, String propertyName, Object selection) {
		for (Object id : t.getItemIds()) {
			TextArea b = parseTextAreaRow(t, id, propertyName);
			if (selection != null) {
				b.setValue(selection.toString());
			}
			if (b.isEnabled())// check if this value should be set
				b.setValue(selection.toString());
		}
	}

	private ComboBox parseBoxRow(Table table, Object rowID, String propertyName) {
		Item item = nmrSamples.getItem(rowID);
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

	private TextField parseTextRow(Table table, Object id, String propertyName) {
		Item item = nmrSamples.getItem(id);
		Object prop = item.getItemProperty(propertyName).getValue();

		if (prop == null)
			return new TextField();
		Object component = prop;
		if (component instanceof TextField)
			return (TextField) component;
		else {
			HorizontalLayout h = (HorizontalLayout) component;
			return (TextField) h.getComponent(0);
		}
	}

	private TextArea parseTextAreaRow(Table table, Object id, String propertyName) {
		Item item = nmrSamples.getItem(id);
		Object prop = item.getItemProperty(propertyName).getValue();

		if (prop == null)
			return new TextArea();
		Object component = prop;
		if (component instanceof TextArea)
			return (TextArea) component;
		else {
			HorizontalLayout h = (HorizontalLayout) component;
			return (TextArea) h.getComponent(0);
		}
	}

	private void pasteSelectionToColumn(Table t, String propertyName, Object selection, int curRow) {
		for (int i = curRow; i <= (t.getItemIds().size()); i += 3) {
			ComboBox b = parseBoxRow(t, i, propertyName);
			if (selection != null)
				b.setValue(selection);
		}
	}

	public List<Map<String, String>> getNMRProperties() {
		List<Map<String, String>> res = new ArrayList<Map<String, String>>();

		for (Object id : nmrSamples.getItemIds()) {

			Map<String, String> res1 = new HashMap<String, String>();
			Item item = nmrSamples.getItem(id);
			// Object expType = parseBoxRow(nmrSamples, id, "Experiment Type").getValue();

			List<String> nmrExpType = getExperimentTypesFromSampleRow(id);
			String expType = StringUtils.join(nmrExpType, ", ");
			String otherExpType = otherExpTypes.get(id);
			if (expType == null && otherExpType != null) {// "other" case
				res1.put("NMR_EXP_TYPES", "OTHER");
				res1.put("Q_ADDITIONAL_INFO", otherExpType);
			} else if (expType == null || !expType.contains(", ")) {
				res1.put("NMR_EXP_TYPES", expType);
			} else {
				res1.put("Q_ADDITIONAL_INFO", "Custom Experiment Type: " + expType);
			}

			String expDesc = parseTextAreaRow(nmrSamples, id, "Experiment Description").getValue();
			Object solvent = parseBoxRow(nmrSamples, id, "Solvent").getValue();
			String concentration = parseTextRow(nmrSamples, id, "Concentration").getValue();
			String volume = parseTextRow(nmrSamples, id, "Volume  [\u03BCl]").getValue();
			String pH = parseTextRow(nmrSamples, id, "pH").getValue();
			String buffer = parseTextRow(nmrSamples, id, "Buffer").getValue();
			String date = parseTextRow(nmrSamples, id, "Date").getValue();
			Object storage = parseBoxRow(nmrSamples, id, "Storage").getValue();
			Object quantitation = parseBoxRow(nmrSamples, id, "Quantitation").getValue();
			String sampleDesc = parseTextAreaRow(nmrSamples, id, "Sample Description").getValue();
			Object nmrSpec = parseBoxRow(nmrSamples, id, "NMR Spectrometer").getValue();
			Object probe = parseBoxRow(nmrSamples, id, "Probe").getValue();

			if (probe == null || !probe.equals("Custom")) {
				res1.put("NMR_PROBE", (String) probe);
			} else {
				res1.put("NMR_PROBE", "OTHER");
				res1.put("NMR_PROBE_DETAILS", probes.get(id));
			}

			if (solvent == null || !solvent.equals("Custom")) {
				res1.put("NMR_SOLVENT", (String) solvent);
			} else {
				res1.put("NMR_SOLVENT", "OTHER");
				res1.put("NMR_SOLVENT_DETAILS", solvents.get(id));
			}

			if (storage == null || !storage.equals("Custom")) {
				res1.put("NMR_STORAGE", (String) storage);
			} else {
				res1.put("NMR_STORAGE", "OTHER");
				res1.put("NMR_STORAGE_DETAILS", storages.get(id).toString());
			}

			String temperature = parseTextRow(nmrSamples, id, "Temperature [K]").getValue();

			res1.put("NMR_EXP_DESC", expDesc.replace("\n", " "));

			res1.put("NMR_CONCENTRATION", concentration);
			res1.put("NMR_VOLUME", volume);
			res1.put("NMR_PH", pH);
			res1.put("NMR_BUFFER", buffer);
			res1.put("NMR_DATE", date);
			res1.put("NMR_QUANTITATION", (String) quantitation);
			res1.put("NMR_SAMPLE_DESC", sampleDesc.replace("\n", " "));
			res1.put("NMR_SPECTROMETER", (String) nmrSpec);

			res1.put("NMR_TEMPERATURE", temperature);

			res.add(res1);

		}

		return res;

	}

	private List<String> getExperimentTypesFromSampleRow(Object i) {
		if (parseBoxRow(nmrSamples, i, "Experiment Type").getValue() == null)
			return null;
		else {
			String entry = parseBoxRow(nmrSamples, i, "Experiment Type").getValue().toString();
			if (entry.equals("Custom"))
				return nmrTypeMap.get(i);
			else
				return new ArrayList<String>(Arrays.asList(entry));
		}
	}

	public boolean isValid() {
		return true;
	}

}
