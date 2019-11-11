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

	private boolean identification;
	private boolean quantification; 
	private String duration;
	private boolean evaluation;
	private boolean molecularWeight; //*


	// SmallMolecules
	private String substanceClass;
	private String molFormulaMass;
	private boolean extraction;
	private String molecularWeightRange;
	private String polarity;	
	private boolean hplcMS;
	private String quantificationSM;
	private String internalStandards;
	

	public RegisteredAnalyteInformation(Set<String> analytes, boolean measurePeptides, boolean shortGel,
			String purification, String composition, boolean precipitation, boolean digestion, String other,
			boolean none, boolean silver, boolean coomassie, boolean identification, boolean quantification,
			String duration, boolean evaluation, boolean molecularWeight) {
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
	}
	
	public RegisteredAnalyteInformation(Set<String> analytes, String purification, String composition, String substanceClass,String molFormulaMass,
			boolean extraction, boolean precipitation, String other, boolean none, String molecularWeightRange, String polarity, boolean molecularWeight, 
			boolean hplcMS,  String quantificationSM, String internalStandards) {
		this.analytes = analytes;
		this.purificationMethod = purification;
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
		this.hplcMS = hplcMS;
		this.quantificationSM = quantificationSM;
		this.internalStandards = internalStandards;
		
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

	public boolean isHplcMS() {
		return hplcMS;
	}

	public String getQuantification() {
		return quantificationSM;
	}

	public String getInternaStandards() {
		return internalStandards;
	}
}
