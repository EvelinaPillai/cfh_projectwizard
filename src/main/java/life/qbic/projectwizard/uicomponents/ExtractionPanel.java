package life.qbic.projectwizard.uicomponents;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

import life.qbic.portal.Styles;

public class ExtractionPanel extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7085065908744807101L;
	
	private List<String> extractions;
	private List<String> devices;
	private List<ExtractionChooser> choosers;
	private List<TextField> elements;
	private GridLayout buttonGrid;
	private Button add;
	private Button remove;
	private Button.ClickListener buttonListener;
	public List<Boolean> status;

	public ExtractionPanel(List<String> extractions, List<String> devices) {
		this.extractions = extractions;
		this.devices = devices;
		
		add = new Button();
		remove = new Button();
		Styles.iconButton(add, FontAwesome.PLUS_SQUARE);
		Styles.iconButton(remove, FontAwesome.MINUS_SQUARE);
		initListener();

		choosers = new ArrayList<ExtractionChooser>();
		ExtractionChooser c = new ExtractionChooser(extractions, devices);
		choosers.add(c);

		elements = new ArrayList<TextField>();
		// TODO set values for the TextFields
		TextField element = new TextField("Selected Elements");
		elements.add(element);

		//when creating the textfield it status is true
		status = new ArrayList<Boolean>();
		status.add(true);

		addComponent(c);
		addComponent(element);

		buttonGrid = new GridLayout(2, 1);
		buttonGrid.setSpacing(true);
		buttonGrid.addComponent(add);
		buttonGrid.addComponent(remove);
		addComponent(buttonGrid);
		setSpacing(true);
	}

	private void initListener() {
		buttonListener = new Button.ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 5324440894306652747L;

			@Override
			public void buttonClick(ClickEvent event) {
				if (event.getButton().equals(add))
					add();
				else
					remove();
			}
		};
		add.addClickListener(buttonListener);
		remove.addClickListener(buttonListener);
	}

	public List<String> getExtractions() {
		List<String> res = new ArrayList<String>();
		for (ExtractionChooser c : choosers) {
			if (c.isSet())
				res.add(c.getExtraction());
		}
		return res;
	}
	
	public List<String> getDevices() {
		List<String> res = new ArrayList<String>();
		for (ExtractionChooser c : choosers) {
			if (c.isDevSet())
				res.add(c.getDevice());
		}
		return res;
	}

	private void add() {
		if (choosers.size() < 4) {
			ExtractionChooser c = new ExtractionChooser(extractions, devices);
			TextField selectedElements = new TextField("Selected Elements");
			choosers.add(c);
			elements.add(selectedElements);
			Collections.replaceAll(status, true, false);
			status.add(true);
			removeComponent(buttonGrid);
			addComponent(c);
			addComponent(selectedElements);
			addComponent(buttonGrid);
		}
	}

	private void remove() {
		int size = choosers.size();
		if (size > 1) {
			ExtractionChooser last = choosers.get(size - 1);
			//TODO check if really needed
			TextField lastEle = elements.get(size - 1);
			last.reset();
			removeComponent(last);
			removeComponent(lastEle);
			choosers.remove(last);
			elements.remove(lastEle);
			devices.remove(last);
		}
	}

	public void resetInputs() {
		for (ExtractionChooser c : choosers) {
			c.reset();
		}
	}
	public List<TextField> getElements() {
		List<TextField> res = new ArrayList<TextField>();
		for (TextField t : elements) {
			//if (!t.getValue().isEmpty()) {
				res.add(t);
				//c.getExtraction());
		}
		return res;
	}
	
	public boolean isElementsEmpty() {
		boolean checkEmptyTextfield = false;
		for (TextField t : elements) {
			 if(t.getValue().isEmpty()) {
			  checkEmptyTextfield = true;
			 }
		
		}
		return checkEmptyTextfield;
	
	}
	

	public boolean isDevicesEmpty() {
		boolean fieldcheck = false;
		for(ExtractionChooser c : choosers){
			if(c.getDevice() == null || c.getDevice().isEmpty()) {
				fieldcheck = true;
			}
		}
		return fieldcheck;
	}

}
