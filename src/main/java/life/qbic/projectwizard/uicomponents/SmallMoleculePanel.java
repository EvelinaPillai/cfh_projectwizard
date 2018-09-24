package life.qbic.projectwizard.uicomponents;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import life.qbic.portal.Styles;

/**
 * Core Facility Hohenheim (CFH) -
 * For SmallMolecules we want to write information on that analysis in a Textfield. 
 *
 */

public class SmallMoleculePanel extends VerticalLayout{

		private TextArea moleculesInfo;

		private static final Logger logger = LogManager.getLogger(SmallMoleculePanel.class);

		public SmallMoleculePanel() {
			
			setSpacing(true);

			this.moleculesInfo = new TextArea("Enter information on Small Molecules");
			moleculesInfo.setStyleName(Styles.fieldTheme);
			moleculesInfo.setInputPrompt("You can type in a maximum of 2000 symbols.");
			moleculesInfo.setWidth("100%");
			moleculesInfo.setHeight("110px");
		    
			//projectDescription.setVisible(false);
		    StringLengthValidator lv = new StringLengthValidator(
		        "Molecule Info is only allowed to contain a maximum of 2000 letters.", 0, 2000, true);
		    moleculesInfo.addValidator(lv);
		    moleculesInfo.setImmediate(true);
		    moleculesInfo.setValidationVisible(true);
		 

	
			addComponent(moleculesInfo);

		}

		public Map<String, Object> getSmallMoleculesProperties() {
	
			//TODO
			Map<String, Object> res = new HashMap<String, Object>();
			res.put("Q_ADDITIONAL_NOTES", moleculesInfo.getValue());
			
			return res;

		}
		
		public boolean isValid() {
			//TODO or to delete
			return false;
			 
		}

}
