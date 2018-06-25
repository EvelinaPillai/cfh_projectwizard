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
import java.util.Set;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.PopupView;
import com.vaadin.ui.VerticalLayout;
import life.qbic.projectwizard.model.TestSampleInformation;
import life.qbic.portal.Styles;
import life.qbic.portal.components.OpenbisInfoTextField;

public class CFHChooser extends VerticalLayout {
  private static final long serialVersionUID = 7196121933289471757L;
  private ComboBox chooser;
  private OpenbisInfoTextField sampleNo;
  private OpenbisInfoTextField sampleName;
  private ComboBox person;
  private ComboBox matrix;
  private Button reloadPeople;
  private Button matrixRefresh;
  private CheckBox pool;
  private List<HorizontalLayout> helpers;

  /**
   * Creates a new condition chooser component
   * 
   * @param options List of different possible conditions
   * @param other Name of the "other" condition, which when selected will enable an input field for
   *        free text
   * @param special Name of a "special" condition like species for the entity input, which when
   *        selected will disable the normal species input because there is more than one instance
   * @param nullSelectionAllowed true, if the conditions may be empty
   */
  public CFHChooser(List<String> options, Set<String> persons, Set<String> matrices) {
    chooser = new ComboBox("Cfh Method", options);
    chooser.setStyleName(Styles.boxTheme);

    sampleNo = new OpenbisInfoTextField("Number of Samples", "", "50px", "1");
    sampleName = new OpenbisInfoTextField("Sample Name", "", "50px", "1");
    pool = new CheckBox("Pool/Multiplex Samples");
    setSpacing(true);
    helpers = new ArrayList<HorizontalLayout>();
    HorizontalLayout help1 =
        Styles.questionize(chooser, "Choose the analyte that is measured.", "Analytes");
    addComponent(help1);
    HorizontalLayout help2 = Styles.questionize(sampleNo.getInnerComponent(),
        "Number of prepared replicates (1 means no replicates) of this analyte", "Replicates");
    addComponent(help2);
    
    HorizontalLayout help3 = Styles.questionize(sampleName.getInnerComponent(),
            "Number of prepared replicates (1 means no replicates) of this analyte", "Replicates");
        addComponent(help3);
    
    HorizontalLayout persBoxH = new HorizontalLayout();
    persBoxH.setCaption("Contact Person");
    person = new ComboBox();
    person.addItems(persons);
    person.setFilteringMode(FilteringMode.CONTAINS);
    person.setStyleName(Styles.boxTheme);
    
    
    reloadPeople = new Button();
    Styles.iconButton(reloadPeople, FontAwesome.REFRESH);
    persBoxH.addComponent(person);
    persBoxH.addComponent(reloadPeople);
    
    HorizontalLayout help4 = Styles.questionize(persBoxH,
        "Person responsible for this part of the experiment", "Contact Person");
    addComponent(help4);
    HorizontalLayout help5 = Styles.questionize(pool,
        "Select if multiple samples are pooled into a single " + "sample before measurement.",
        "Pooling");

    
    HorizontalLayout matrixBoxH = new HorizontalLayout();
    matrixBoxH.setCaption("Matrix");
    matrix = new ComboBox();
    matrix.addItems(matrices);
    matrix.setFilteringMode(FilteringMode.CONTAINS);
    matrix.setStyleName(Styles.boxTheme);
    
    
    matrixRefresh = new Button();
    Styles.iconButton(matrixRefresh, FontAwesome.REFRESH);
    matrixBoxH.addComponent(matrix);
    matrixBoxH.addComponent(matrixRefresh);
    
    HorizontalLayout help6 = Styles.questionize(matrixBoxH,
        "Matrix which contains the sample", "Matrix");
    addComponent(help6);
    
    
    chooser.addValueChangeListener(new ValueChangeListener() {

      @Override
      public void valueChange(ValueChangeEvent event) {
        if (chooser.getValue() != null) {
          help5.setVisible(!chooser.getValue().equals("PROTEINS"));
        }
      }
    });

    addComponent(help5);
    addComponent(help6);
    helpers.add(help1);
    helpers.add(help2);
    helpers.add(help3);
    helpers.add(help4);
    helpers.add(help5);
    helpers.add(help6);
  }

  public boolean hasAnalyteInput() {
    return chooser.getItemIds().contains(chooser.getValue());
  }
  
  public String getPerson() {
    if (person.getValue() != null)
      return person.getValue().toString();
    else
      return null;
  }
  
  public void updatePeople(Set<String> people) {
    String contact = getPerson();
    person.removeAllItems();
    person.addItems(people);
    if (contact != null && !contact.isEmpty())
      person.select(contact);
  }

  public boolean isSet() {
	    return chooser.getItemIds().contains(chooser.getValue()) && sampleNo.getValue() != null;
	  }
 
  public TestSampleInformation getChosenCfhInfo() {
	    return new TestSampleInformation(chooser.getValue().toString(), pool.getValue(),
	        Integer.parseInt(sampleNo.getValue()), getPerson(), chooser.getValue().toString());
	  }

  public void showHelpers() {
    for (HorizontalLayout h : helpers)
      for (Component c : h)
        if (c instanceof PopupView)
          c.setVisible(true);
  }

  public void hideHelpers() {
    for (HorizontalLayout h : helpers)
      for (Component c : h)
        if (c instanceof PopupView)
          c.setVisible(false);
  }

  public void reset() {
    pool.setValue(false);
    chooser.setValue(chooser.getNullSelectionItemId());
  }

  public void addPoolListener(ValueChangeListener l) {
    this.pool.addValueChangeListener(l);
  }

  public void removePoolListener(ValueChangeListener poolListener) {
    this.pool.removeValueChangeListener(poolListener);
  }

 
  public boolean poolingSet() {
    return pool.getValue();
  }

  

  
  public void addNminListener(ValueChangeListener nminListener) {
	    this.chooser.addValueChangeListener(nminListener);
  }
  public void removeNminListener(ValueChangeListener nminListener) {
	    this.chooser.removeValueChangeListener(nminListener);
  }

  public void addElementListener(ValueChangeListener elementListener) {
    this.chooser.addValueChangeListener(elementListener);
  }

  public void removeElementListener(ValueChangeListener elementListener) {	
	  this.chooser.removeValueChangeListener(elementListener);
  }

  public void addFatListener(ValueChangeListener fatListener) {
    this.chooser.addValueChangeListener(fatListener);
  }

  public void removeFatListener(ValueChangeListener fatListener) {
    this.chooser.removeValueChangeListener(fatListener);
  }

  public void addAAListener(ValueChangeListener aaListener) {
	  this.chooser.addValueChangeListener(aaListener);
  }

  public void removeAAListener(ValueChangeListener aaListener) {	
	  this.chooser.removeValueChangeListener(aaListener);
  }


  public void setValue(String analyte) {
    chooser.setValue(analyte);
  }

  public void addRefreshPeopleListener(ClickListener refreshPeopleListener) {
    this.reloadPeople.addClickListener(refreshPeopleListener);
  }

  public void removeRefreshPeopleListener(ClickListener refreshPeopleListener) {
    this.reloadPeople.removeClickListener(refreshPeopleListener);
  }
}