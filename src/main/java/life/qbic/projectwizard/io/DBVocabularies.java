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
package life.qbic.projectwizard.io;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DBVocabularies {

	private Map<String, String> matrixMap;
	private Map<String, String> taxMap;
	private Map<String, String> tissueMap;
	private Map<String, String> deviceMap;
	private Map<String, String> cellLinesMap;
	private Map<String, String> proteinPurificationMethods;
	private List<String> measureTypes;
	private List<String> spaces;
	private Map<String, Integer> investigators;
	private List<String> experimentTypes;
	private List<String> enzymes;
	private Map<String, String> antibodies;
	private List<String> msProtocols;
	private List<String> lcmsMethods;
	private Map<String, String> chromTypes;
	private List<String> fractionationTypes;
	private List<String> enrichmentTypes;
	private List<String> extractionTypes;
	private List<String> devices;
	private List<String> soildepth;
	private List<String> nmrTypes;
	private List<String> solvents;
	private List<String> storage;
	private List<String> quantitation;
	private List<String> probes;
	private List<String> spectrometers;

	public DBVocabularies(Map<String, String> matrixMap, Map<String, String> taxMap, Map<String, String> tissueMap,
			Map<String, String> cellLinesMap, List<String> measureTypes, List<String> spaces,
			Map<String, Integer> piMap, List<String> experimentTypes, Map<String, String> enzymeMap,
			Map<String, String> antibodiesWithDescriptions, Map<String, String> deviceMap, List<String> msProtocols,
			List<String> lcmsMethods, Map<String, String> chromTypes2, List<String> fractionationTypes,
			List<String> enrichmentTypes, Map<String, String> purificationMethods, List<String> extractionTypes,
			List<String> devices, List<String> soildepth, List<String> nmrTypes, List<String> solvents,
			List<String> storage, List<String> quantitation, List<String> probes, List<String> spectrometers) {
		this.matrixMap = matrixMap;
		this.taxMap = taxMap;
		this.tissueMap = tissueMap;
		this.cellLinesMap = cellLinesMap;
		this.deviceMap = deviceMap;
		this.measureTypes = measureTypes;
		this.spaces = spaces;
		this.investigators = piMap;
		this.experimentTypes = experimentTypes;
		this.antibodies = antibodiesWithDescriptions;
		this.msProtocols = msProtocols;
		this.lcmsMethods = lcmsMethods;
		this.chromTypes = chromTypes2;
		this.fractionationTypes = fractionationTypes;
		this.enrichmentTypes = enrichmentTypes;
		this.proteinPurificationMethods = purificationMethods;
		this.enzymes = new ArrayList<String>();
		this.extractionTypes = extractionTypes;
		this.devices = devices;
		enzymes.addAll(enzymeMap.keySet());
		this.soildepth = soildepth;
		this.nmrTypes = nmrTypes;
		this.solvents = solvents;
		this.storage = storage;
		this.quantitation = quantitation;
		this.spectrometers = spectrometers;
		this.probes = probes;
	}

	public List<String> getFractionationTypes() {
		return fractionationTypes;
	}

	public List<String> getEnrichmentTypes() {
		return enrichmentTypes;
	}

	public Map<String, String> getProteinPurificationMethodsMap() {
		return proteinPurificationMethods;
	}

	public Map<String, String> getCellLinesMap() {
		return cellLinesMap;
	}

	public Map<String, String> getMatrixMap() {
		return matrixMap;
	}

	public Map<String, String> getTaxMap() {
		return taxMap;
	}

	public Map<String, String> getTissueMap() {
		return tissueMap;
	}

	public Map<String, String> getDeviceMap() {
		return deviceMap;
	}

	public List<String> getAnalyteTypes() {
		return measureTypes;
	}

	public List<String> getSpaces() {
		return spaces;
	}

	public Map<String, Integer> getPeople() {
		return investigators;
	}

	public List<String> getExperimentTypes() {
		return experimentTypes;
	}

	public List<String> getEnzymes() {
		return enzymes;
	}

	public List<String> getMsProtocols() {
		return msProtocols;
	}

	public List<String> getLcmsMethods() {
		return lcmsMethods;
	}

	public Map<String, String> getChromTypesMap() {
		return chromTypes;
	}

	public Map<String, String> getAntibodiesMap() {
		return antibodies;
	}

	public void setPeople(Map<String, Integer> people) {
		this.investigators = people;
	}

	public void setSpaces(List<String> userSpaces) {
		this.spaces = userSpaces;
	}

	public List<String> getExtractions() {
		return extractionTypes;
	}

	public List<String> getDevices() {
		return devices;
	}

	public List<String> getSoildepth() {
		return soildepth;
	}

	public List<String> getNMRTypes() {
		return nmrTypes;
	}

	public List<String> getSolvents() {
		return solvents;
	}

	public List<String> getStorage() {
		return storage;
	}

	public List<String> getQuantitation() {
		return quantitation;
	}

	public List<String> getProbes() {
		return probes;
	}

	public List<String> getSpectrometers() {
		return spectrometers;
	}
}
