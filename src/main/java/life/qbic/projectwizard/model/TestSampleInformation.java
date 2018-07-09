package life.qbic.projectwizard.model;

public class TestSampleInformation {

  private String technology;
  private boolean pool;
  private int replicates;
  private String person;
  private int sampleNo;
  private String sampleName;
  private String matrix;


  public TestSampleInformation(String tech, boolean pool, int reps, int sampleNo, String person, String sampleName, String matrix) {
    this.replicates = reps;
    this.sampleNo = sampleNo;
    this.pool = pool;
    this.person = person;
    this.technology = tech;
    this.sampleName = sampleName;
    this.matrix = matrix;

  }

  public String getTechnology() {
    return technology;
  }

  public boolean isPooled() {
    return pool;
  }

  public int getReplicates() {
    return replicates;
  }

  public String getPerson() {
    return person;
  }
  
  public int getSampleNo() {
	  return sampleNo;
  }
  
  @Override
  public String toString() {
    String res = technology;
    if (pool)
      res += " (pooled)";
    res += "\n" + Integer.toString(replicates) + " replicates";
    res += "\n" + person;
    return res;
  }

public String getSampleName() {
	return sampleName;
}

public String getMatrix() {
	return matrix;
}


}
