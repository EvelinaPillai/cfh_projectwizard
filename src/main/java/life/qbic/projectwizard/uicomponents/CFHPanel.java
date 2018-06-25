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

import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import life.qbic.projectwizard.model.TestSampleInformation;
import life.qbic.portal.Styles;

import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.OptionGroup;

/**
 * Composite UI component for inputting an arbitrary number of experimental conditions
 * 
 * @author Andreas Friedrich
 * 
 */
public class CFHPanel extends HorizontalLayout {

  private static final long serialVersionUID = -1578503116738309380L;
  List<String> options;
  Set<String> persons;
  Set<String> matrices;
  List<CFHChooser> choosers;
  Button.ClickListener buttonListener;
  ValueChangeListener poolListener;
  ValueChangeListener elementListeners;
  ValueChangeListener fatListener;
  ValueChangeListener aaListener;
  ValueChangeListener nminListener;
  Button.ClickListener refreshPeopleListener;
  GridLayout buttonGrid;
  Button add;
  Button remove;

  OptionGroup conditionsSet;

  /**
   * Create a new Conditions Panel component to select experimental conditions
   * 
   * @param techOptions List of different possible conditions
   * @param conditionsSet (empty) option group that makes it possible to listen to the conditions
   *        inside this component from the outside
   * @param proteinListeners
   */
  public CFHPanel(List<String> techOptions, Set<String> persons, Set<String> matrices, OptionGroup conditionsSet,
       ValueChangeListener elementListeners, ValueChangeListener aaListener,
      ValueChangeListener fatListener, ValueChangeListener nminListener) {
    this.options = techOptions;
    this.persons = persons;

    this.conditionsSet = conditionsSet;
    this.conditionsSet.addItem("set");
    add = new Button();
    remove = new Button();
    Styles.iconButton(add, FontAwesome.PLUS_SQUARE);
    Styles.iconButton(remove, FontAwesome.MINUS_SQUARE);
    initListener();
    this.poolListener = poolListener;
    this.elementListeners = elementListeners;
    this.fatListener = fatListener;
    this.aaListener = aaListener;
    this.nminListener = nminListener;
    this.refreshPeopleListener = refreshPeopleListener;
    
   

    choosers = new ArrayList<CFHChooser>();
    CFHChooser c = new CFHChooser(techOptions, persons, matrices);
    c.setImmediate(true);
    //c.addPoolListener(poolListener);
    //c.addRefreshPeopleListener(refreshPeopleListener);
    
    c.addElementListener(elementListeners);
    c.addFatListener(fatListener);
    c.addNminListener(nminListener);
    c.addAAListener(aaListener);
    
    choosers.add(c);
    addComponent(c);
    c.showHelpers();

    buttonGrid = new GridLayout(1, 2);
    buttonGrid.setSpacing(true);
    buttonGrid.addComponent(add);
    buttonGrid.addComponent(remove);
    addComponent(buttonGrid);
    setSpacing(true);
  }

  private void initListener() {
    buttonListener = new Button.ClickListener() {

      private static final long serialVersionUID = 2240224129259577437L;

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

  public boolean poolingSet() {
    boolean res = false;
    for (CFHChooser c : choosers) {
      res |= c.poolingSet();
    }
    return res;
  }

  public List<TestSampleInformation> getCfhInfo() {
    List<TestSampleInformation> res = new ArrayList<TestSampleInformation>();
    for (CFHChooser c : choosers) {
      if (c.isSet())
        res.add(c.getChosenCfhInfo());
    }
    return res;
  }

  private void add() {
    choosers.get(choosers.size() - 1).hideHelpers();
    CFHChooser c = new CFHChooser(options, persons, matrices);
    //c.addPoolListener(poolListener);
    c.addElementListener(elementListeners);
    c.addFatListener(fatListener);
    c.addAAListener(aaListener);
    c.addNminListener(nminListener);
    //c.addRefreshPeopleListener(refreshPeopleListener);
    choosers.add(c);

    c.showHelpers();
    removeComponent(buttonGrid);
    addComponent(c);
    addComponent(buttonGrid);
  }

  private void remove() {
    int size = choosers.size();
    if (size > 1) {
      CFHChooser last = choosers.get(size - 1);
      last.reset();
      removeComponent(last);
      choosers.remove(last);
      last.removePoolListener(poolListener);
      last.removeElementListener(elementListeners);
      last.removeFatListener(fatListener);
      last.removeAAListener(aaListener);
      last.removeNminListener(nminListener);
      last.removeRefreshPeopleListener(refreshPeopleListener);
      choosers.get(size - 2).showHelpers();
    }
  }

  public boolean isValid() {
    for (CFHChooser c : choosers) {
      if (c.isSet())
        return true;
    }
    return false;
  }

  public void resetInputs() {
    for (CFHChooser c : choosers) {
      c.reset();
    }
  }

  public void select(String analyte) {
    boolean added = false;
    for (CFHChooser c : choosers) {
      if (!c.hasAnalyteInput()) {
        c.setValue(analyte);
        added = true;
        break;
      }
    }
    if (!added) {
      add();
      select(analyte);
    }
  }

  public void updatePeople(Set<String> people) {
    for (CFHChooser c : choosers)
      c.updatePeople(people);
  }

}
