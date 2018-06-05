package life.qbic.projectwizard.uicomponents;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.ui.ComboBox;
import com.vaadin.ui.VerticalLayout;

import life.qbic.portal.Styles;
import life.qbic.projectwizard.io.DBVocabularies;

public class NminOptions extends VerticalLayout{
	 /**
	   * 
	   */
	  private static final long serialVersionUID = 6966022066367510739L;
	
	  private ComboBox matrix;
	  
	  private static final Logger logger = LogManager.getLogger(NminOptions.class);
	  
	  public NminOptions(DBVocabularies vocabs) {
		    this.setCaption("Nmin Options");
		    setSpacing(true);
		    
		    matrix = new ComboBox("Matrix");
		    matrix.setNullSelectionAllowed(false);
		    matrix.setStyleName(Styles.boxTheme);
		    matrix.setVisible(true);
		    
		    List<String> methods =
		        new ArrayList<String>(vocabs.getMatrixMap().values());
		    Collections.sort(methods);
		    matrix.addItems(methods);
		    addComponent(matrix);

	  }
	  
	  public Map<String, Object> getExperimentalProperties() {
		    Map<String, Object> res = new HashMap<String, Object>();

		    if (matrix.getValue() != null) {
		    	//TODO 
		    	res.put("Q_ADDITIONAL_INFO", "Matrix: " + matrix);
		    	
		    }
			return res;

	  }	    
}
