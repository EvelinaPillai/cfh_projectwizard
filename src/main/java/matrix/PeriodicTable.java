package matrix;

import java.util.List;

import com.vaadin.annotations.JavaScript;
import com.vaadin.ui.AbstractJavaScriptComponent;

import life.qbic.projectwizard.uicomponents.AminoAcidPanel;
import life.qbic.projectwizard.uicomponents.ElementPanel;

@JavaScript({ "vaadin://js/d3.v4.min.js", "periodic_connector.js" })
public class PeriodicTable extends AbstractJavaScriptComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3945104288294554973L;

	@Override
	public PeriodicTableState getState() {
		return (PeriodicTableState) super.getState();
	}

	public void setElements(final List<ChemElement> elements) {
		getState().setElements(elements);
	}

	public PeriodicTable(final ElementPanel layout) {

		registerRpc(new ElementClickRpc() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1164923539981845663L;

			public void onElementClick(ChemElement element) {
				layout.useSelectedElement(element);
			}

		});
	}

	public PeriodicTable(final AminoAcidPanel aa_layout) {

		registerRpc(new ElementClickRpc() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -5959320109989051317L;

			public void onElementClick(ChemElement element) {
				aa_layout.useSelectedElement(element);
			}

		});
	}
}
