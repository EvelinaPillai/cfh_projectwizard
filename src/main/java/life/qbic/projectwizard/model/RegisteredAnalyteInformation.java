package life.qbic.projectwizard.model;

import java.util.Set;

public class RegisteredAnalyteInformation {

  private Set<String> analytes;
  private Set<String> cfhMethod;
  private boolean measurePeptides;
  private boolean shortGel;
  private String purificationMethod;

  public RegisteredAnalyteInformation(Set<String> analytes, boolean measurePeptides,
      boolean shortGel, String purification ,Set<String> cfhMethod ) {
    this.analytes = analytes;
    this.measurePeptides = measurePeptides;
    this.shortGel = shortGel;
    this.purificationMethod = purification;
    this.cfhMethod = cfhMethod;
  }

  public Set<String> getAnalytes() {
    return analytes;
  }
  
  public Set<String> getCFHMethod() {
	    return cfhMethod;
  }

  public boolean isMeasurePeptides() {
    return measurePeptides;
  }

  public boolean isShortGel() {
    return shortGel;
  }

  public String getPurificationMethod() {
    return purificationMethod;
  }
}
