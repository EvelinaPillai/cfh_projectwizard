package matrix;

import java.util.List;

import com.vaadin.shared.ui.JavaScriptComponentState;

public class PeriodicTableState extends JavaScriptComponentState {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7202127928887060884L;

	private List<ChemElement> elements;

	public List<ChemElement> getElements() {
		return elements;
	}

	public void setElements(List<ChemElement> elements) {
		this.elements = elements;
	}
}
