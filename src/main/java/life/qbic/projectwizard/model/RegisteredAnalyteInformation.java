package life.qbic.projectwizard.model;

import java.util.Set;

public class RegisteredAnalyteInformation {

	private Set<String> analytes;
	private boolean measurePeptides;
	private boolean shortGel;
	private String purificationMethod;

	// Proteins
	private String composition; //also used for smallMolecules *
	private boolean precipitation; //*
	private boolean digestion;
	private String other; //*
	private boolean none; //*
	private boolean silver;
	private boolean coomassie;

	private boolean identification; //*
	private boolean quantification; 
	private String duration;
	private boolean evaluation;
	private boolean molecularWeight; //*
	private String comments; //*


	// SmallMolecules
	private String substanceClass;
	private String molFormulaMass;
	private boolean extraction;
	private String molecularWeightRange;
	private String polarity;	
	private boolean relQuantification;
	private boolean absQuantification;
	private String internalStandards;
	

	public RegisteredAnalyteInformation(Set<String> analytes, boolean measurePeptides, boolean shortGel,
			String purification, String composition, boolean precipitation, boolean digestion, String other,
			boolean none, boolean silver, boolean coomassie, boolean identification, boolean quantification,
			String duration, boolean evaluation, boolean molecularWeight, String comments) {
		this.analytes = analytes;
		this.measurePeptides = measurePeptides;
		this.shortGel = shortGel;
		this.purificationMethod = purification;
		//CFH
		this.composition = composition;
		this.precipitation = precipitation;
		this.digestion = digestion;
		this.other = other;
		this.none = none;
		this.silver = silver;
		this.coomassie = coomassie;
		this.identification = identification;
		this.quantification = quantification;
		this.duration = duration;
		this.evaluation = evaluation;
		this.molecularWeight = molecularWeight;
		this.comments = comments;
	}
	
	public RegisteredAnalyteInformation(Set<String> analytes, String composition, String substanceClass,String molFormulaMass,
			boolean extraction, boolean precipitation, String other, boolean none, String molecularWeightRange, String polarity, boolean molecularWeight,  boolean identification,  
			boolean relQuantification,  boolean absQuantification, boolean evaluation, String internalStandards, String comments) {
		this.analytes = analytes;
		//CFH
		this.composition = composition;
		this.substanceClass = substanceClass;
		this.molFormulaMass = molFormulaMass;
		this.extraction = extraction;
		this.precipitation = precipitation;
		this.other = other;
		this.none = none;
		this.molecularWeightRange = molecularWeightRange;
		this.polarity = polarity;
		this.molecularWeight = molecularWeight;
		this.identification = identification;
		this.relQuantification = relQuantification;
		this.absQuantification = absQuantification;
		this.evaluation = evaluation;
		this.internalStandards = internalStandards;
		this.comments = comments;
		
	}

	public Set<String> getAnalytes() {
		return analytes;
	}

	public boolean isMeasurePeptides() {
		return measurePeptides;
	}

	public boolean isShortGel() {
		return shortGel;
	}

	public boolean isPrecipitation() {
		return precipitation;
	}

	public String getPurificationMethod() {
		return purificationMethod;
	}

	public String getDuration() {
		return duration;
	}

	public String getComposition() {
		return composition;
	}

	public String getSubstanceClass() {
		return substanceClass;
	}

	public String getMolFormulaMass() {
		return molFormulaMass;
	}

	public boolean isDigestion() {
		return digestion;
	}

	public String getOther() {
		return other;
	}
	
	public String getComments() {
		return comments;
	}
	public boolean isNone() {
		return none;
	}

	public boolean isSilver() {
		return silver;
	}

	public boolean isIdentification() {
		return identification;
	}

	public boolean isCoomassie() {
		return coomassie;
	}

	public boolean isQuantification() {
		return quantification;
	}

	public boolean isEvaluation() {
		return evaluation;
	}

	public boolean isMolecularWeight() {
		return molecularWeight;
	}

	public boolean isExtraction() {
		return extraction;
	}

	public String getMolecularWeightRange() {
		return molecularWeightRange;
	}

	public String getPolarity() {
		return polarity;
	}

	public boolean isRelQuantification() {
		return relQuantification;
	}

	public boolean isAbsQuantification() {
		return absQuantification;
	}

	public String getInternaStandards() {
		return internalStandards;
	}
}
