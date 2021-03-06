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
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.liferay.portal.kernel.log.Log;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import life.qbic.datamodel.persons.PersonType;
import life.qbic.datamodel.projects.ProjectInfo;
import life.qbic.portal.Styles;
import life.qbic.portal.Styles.NotificationType;
import life.qbic.portal.components.CustomVisibilityComponent;
import life.qbic.portal.components.StandardTextField;

public class ProjectInformationComponent extends VerticalLayout {

  /**
   * 
   */
  private static final long serialVersionUID = 3467663055161160735L;
  private ComboBox spaceBox;
  private Button reloadSpaces;
  private CustomVisibilityComponent projectBox;
  private TextField project;
  private Button reloadProjects;
  private TextField expName;
  private CustomVisibilityComponent personBox;
  private ComboBox piBox;
  private ComboBox contactBox;
  private ComboBox managerBox;
  private Button reloadPeople;

  private TextArea projectDescription;

  private ValueChangeListener projectSelectListener;

  public ProjectInformationComponent(List<String> spaces, Set<String> people) {
    setSpacing(true);
    setSizeUndefined();

    reloadSpaces = new Button();
    Styles.iconButton(reloadSpaces, FontAwesome.REFRESH);
    
    Collections.sort(spaces);
    
    spaceBox = new ComboBox("Project [UVB]", spaces);
    spaceBox.setStyleName(Styles.boxTheme);
    spaceBox.setNullSelectionAllowed(false);
    spaceBox.setImmediate(true);
    spaceBox.setFilteringMode(FilteringMode.CONTAINS);
    GridLayout  spaceBoxH = new GridLayout(2,1);
    spaceBoxH.addComponents(spaceBox,reloadSpaces);
    spaceBoxH.setComponentAlignment(reloadSpaces,Alignment.BOTTOM_LEFT);
    //CustomVisibilityComponent newSpace = new CustomVisibilityComponent(spaceBoxH);
    addComponent(spaceBoxH);
    addComponent(Styles.questionize(spaceBoxH, "Name of the project", "Project Name"));
    
    ComboBox prBox = new ComboBox("Sub-Project [Auftragsnummer]");
    prBox.setStyleName(Styles.boxTheme);
    projectBox = new CustomVisibilityComponent(prBox);
    projectBox.setStyleName(Styles.boxTheme);
    projectBox.setImmediate(true);
    addComponent(Styles.questionize(projectBox, "CFH project code", "Project"));

    project = new StandardTextField();
    project.setStyleName(Styles.fieldTheme);
    project.setMaxLength(15);
    project.setEnabled(false);
    project.setValidationVisible(true);

    reloadProjects = new Button();
    Styles.iconButton(reloadProjects, FontAwesome.REFRESH);

    HorizontalLayout proj = new HorizontalLayout();
    proj.setCaption("New Sub-Project [Neue Auftragsnummer]");
    proj.addComponent(project);
    proj.addComponent(reloadProjects);
    CustomVisibilityComponent newProj = new CustomVisibilityComponent(proj);

    addComponent(Styles.questionize(newProj,
        "Automatically create an unused CFH project code or fill in your own. "
            + "The code consists of 15 characters, must follow the pattern [year]-[module]-[4digits]-[3digits]. You can create the next unused code by clicking "
            + FontAwesome.REFRESH.getHtml() + ".",
        "New Sub-Project"));
    
    
    expName = new StandardTextField("Short name (e.g. Institution Number)");
    expName.setWidth("200px");
    // expName.setRequired(true);
    expName.setVisible(false);
    expName.setInputPrompt("Name of sub project");
    addComponent(expName);

    HorizontalLayout persBoxH = new HorizontalLayout();
    persBoxH.setCaption("Principal Investigator / Customer");
    VerticalLayout persBox = new VerticalLayout();

    piBox = new ComboBox();
    ArrayList<String> sortedPeople = new ArrayList<String>(people);
   
    Collections.sort(sortedPeople);
    piBox.addItems(sortedPeople);
    piBox.setFilteringMode(FilteringMode.CONTAINS);
    piBox.setStyleName(Styles.boxTheme);
    contactBox = new ComboBox("Contact Person / Customer ", sortedPeople);
    contactBox.setFilteringMode(FilteringMode.CONTAINS);
    contactBox.setStyleName(Styles.boxTheme);
    managerBox = new ComboBox("Project Manager CFH", sortedPeople);
    managerBox.setFilteringMode(FilteringMode.CONTAINS);
    managerBox.setStyleName(Styles.boxTheme);
    persBox.addComponent(piBox);
    persBox.addComponent(contactBox);
    persBox.addComponent(managerBox);

    reloadPeople = new Button();
    Styles.iconButton(reloadPeople, FontAwesome.REFRESH);
    persBoxH.addComponent(persBox);
    persBoxH.addComponent(reloadPeople);

    personBox = new CustomVisibilityComponent(persBoxH);
    personBox.setVisible(false);
    addComponent(Styles.questionize(personBox,
        "Investigator and contact person of this project. Please contact us if additional people need to be added. Press refresh button to show newly added people.",
        "Contacts"));

    projectDescription = new TextArea("Description");
    projectDescription.setRequired(true);
    projectDescription.setStyleName(Styles.fieldTheme);
    projectDescription.setInputPrompt("Sub-Project description, maximum of 2000 symbols.");
    projectDescription.setWidth("100%");
    projectDescription.setHeight("110px");
    projectDescription.setVisible(false);
    StringLengthValidator lv = new StringLengthValidator(
        "Description is only allowed to contain a maximum of 2000 letters.", 0, 2000, true);
    projectDescription.addValidator(lv);
    projectDescription.setImmediate(true);
    projectDescription.setValidationVisible(true);
    addComponent(projectDescription);
  }

  public void tryEnableCustomProject(String code) {
    boolean choseNewProject = selectionNull();
    if (choseNewProject) {
      project.setValue(code);
    } else {
      project.setValue("");
    }
    project.setEnabled(choseNewProject);
    expName.setVisible(choseNewProject);
    projectDescription.setVisible(choseNewProject);
    personBox.setVisible(choseNewProject);
  }

  public void updatePeople(Set<String> people) {
    String pi = getPerson(PersonType.Investigator);
    String contact = getPerson(PersonType.Contact);
    String manager = getPerson(PersonType.Manager);
    piBox.removeAllItems();
    contactBox.removeAllItems();
    managerBox.removeAllItems();
    ArrayList<String> sortedPeople = new ArrayList<String>(people);
    Collections.sort(sortedPeople);
    piBox.addItems(sortedPeople);
    contactBox.addItems(sortedPeople);
    managerBox.addItems(sortedPeople);
    if (pi != null && !pi.isEmpty())
      piBox.select(pi);
    if (contact != null && !contact.isEmpty())
      contactBox.select(contact);
    if (manager != null && !manager.isEmpty())
      managerBox.select(manager);
  }

  private boolean selectionNull() {
    return projectBox.getValue() == null;
  }

  public Button getCodeButton() {
    return reloadProjects;
  }

  public ComboBox getProjectBox() {
    return (ComboBox) projectBox.getInnerComponent();
  }

  public TextField getProjectField() {
    return project;
  }

  public Button getProjectReloadButton() {
    return reloadProjects;
  }

  public Button getPeopleReloadButton() {
    return reloadPeople;
  }

  /**
   * Returns either a selected existing project from the combobox or, if it is empty, the value from
   * the textfield. validity of the textfield should be checked elsewhere
   * 
   * @return project code
   */
  public String getSelectedProject() {
    if (selectionNull())
      return project.getValue();
    else {
      String project = projectBox.getValue().toString();
      if (project.contains(" "))
        // remove alternative name
        project = project.split(" ")[0];
      return project;
    }
  }

  public String getProjectDescription() {
    return projectDescription.getValue();
  }

  public String getSecondaryName() {
    return expName.getValue();
  }

  public void addProjects(List<String> projects) {
    ((AbstractSelect) projectBox.getInnerComponent()).addItems(projects);
  }

  public void resetProjects() {
    projectBox.setEnabled(false);
    ((ComboBox) projectBox.getInnerComponent()).removeAllItems();
    project.setEnabled(true);
  }

  public void enableProjectBox(boolean b) {
    projectBox.setEnabled(b);
  }

  public TextField getExpNameField() {
    return expName;
  }

  public String getPerson(PersonType type) {
    switch (type) {
      case Manager:
        return (String) managerBox.getValue();
      case Investigator:
        return (String) piBox.getValue();
      case Contact:
        return (String) contactBox.getValue();
      default:
        return null;
    }
  }

  public boolean spaceIsReady() {
    return spaceBox.getValue() != null && !spaceBox.getValue().toString().isEmpty();
  }

  public String getSpaceCode() {
    return (String) this.spaceBox.getValue();
  }

  public ComboBox getSpaceBox() {
    return spaceBox;
  }

  public void addInfoCompleteListener(ValueChangeListener infoCompleteListener) {
    ComboBox b = (ComboBox) projectBox.getInnerComponent();
    b.addValueChangeListener(infoCompleteListener);
    piBox.addValueChangeListener(infoCompleteListener);
    contactBox.addValueChangeListener(infoCompleteListener);
    managerBox.addValueChangeListener(infoCompleteListener);
    expName.addValueChangeListener(infoCompleteListener);
    project.addValueChangeListener(infoCompleteListener);
    projectDescription.addValueChangeListener(infoCompleteListener);
  }

  public boolean isValid(boolean notify) {
    if (spaceIsReady() && projectIsReady()) {
      if (getProjectBox().isEmpty()) {
        if (projectDescription.isValid() && !projectDescription.isEmpty())
          return true;
        else {
          if (notify)
            Styles.notification("No description", "Please fill in an experiment description.",
                NotificationType.ERROR);
        }
      } else
        return true;
    } else {
      if (notify)
        Styles.notification("No Sub-project selected", "Please select a project and sub-project.",
            NotificationType.ERROR);
    }
    return false;
  }

  public boolean projectIsReady() {
    return !selectionNull() || project.isValid();
    // return !getSelectedProject().toUpperCase().isEmpty();
  }

  public ProjectInfo getProjectInfo() {
    return new ProjectInfo(projectDescription.getValue(), getSecondaryName(), false,
        getPerson(PersonType.Investigator), getPerson(PersonType.Contact),
        getPerson(PersonType.Manager));
  }

public void updateSpaces(List<String> spaces) {
	Collections.sort(spaces);
	spaceBox.removeAllItems();
	spaceBox.addItems(spaces);
}

public Button getSpacesReloadButton() {
	return reloadSpaces;
}
}
