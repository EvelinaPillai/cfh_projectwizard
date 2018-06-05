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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.data.Item;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Notification;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import life.qbic.datamodel.samples.AOpenbisSample;
import life.qbic.projectwizard.io.DBVocabularies;
import life.qbic.projectwizard.model.MHCLigandExtractionProtocol;
import life.qbic.portal.Styles;
import life.qbic.portal.components.StandardTextField;

public class ElementPanel extends VerticalLayout {

  /**
   * 
   */


  private StandardTextField elements;
 	private OptionGroup multi;
  

  public ElementPanel(DBVocabularies vocabs) {
    this.setCaption("Elements for Analyze");
  
    setSpacing(true);
   	
	elements = new StandardTextField("Elements");
	multi = new OptionGroup();
	multi.setStyleName("multicol");
	multi.setMultiSelect(true);
	multi.addItems("H", " ", " "," ","  ","Rb","Cs","Fr"); 
	multi.addItems("Be", "MG", "Cr","Nat","Kt","Rbt","Cst","Frt"); 
	multi.addItems("Hr", "Lier", "Lir","Nar","Kr","Rbr","Csr","Frr"); 
	multi.addItems("Hv", "Liv", "Liv3","Nav","Kv","Rbv","Csv","Frv"); 
				
				/* Be                               B  C  N  O  F  Ne\n" + 
				"Mg                               Al Si P  S  Cl Ar\n" + 
				"Ca Sc Ti V  Cr Mn Fe Co Ni Cu Zn Ga Ge As Se Br Kr\n" + 
				"Sr Y  Zr Nb Mo Tc Ru Rh Pd Ag Cd In Sn Sb Te I  Xe\n" + 
				" Ba    Hf Ta W  Re Os Ir Pt Au Hg Tl Pb Bi Po At Rn\n" + 
				"Fr Ra    Rf Db Sg Bh Hs Mt Ds Rg Cn Nh Fl Mc Lv Ts Og\n" + 
				"Uue                                                   \n" + 
				"      La Ce Pr Nd Pm Sm Eu Gd Tb Dy Ho Er Tm Yb Lu    \n" + 
				"      Ac Th Pa U  Np Pu Am Cm Bk Cf Es Fm Md No Lr");
	*/
		
	addComponents(elements,multi);
		
		
  }


}
