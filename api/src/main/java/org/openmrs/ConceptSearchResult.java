/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs;

/**
 * An Object of this class represents a search result returned when searching for concepts, it holds
 * extra metadata about the matched concept(s).
 * 
 * @since 1.8
 */
public class ConceptSearchResult implements java.io.Serializable {
	
	private static final long serialVersionUID = 6394792520635644989L;
	
	// the matching concept that was found
	private Concept concept;
	
	// the actual conceptName that was matching a given word in the search
	private ConceptName conceptName;
	
	// the word in the search string that was matched against the conceptNameHit
	private String word;
	
	private Double transientWeight = 0.0;
	
	/** default constructor */
	public ConceptSearchResult() {
	}
	
	/**
	 * Optional constructor for turning a conceptWord into a conceptSearchResult, the constructor is
	 * hidden from API users so as to hide the idea of conceptWord, it is meant to be used
	 * underneath the API for convenience purposes.
	 * 
	 * @param conceptWord the conceptWord from which to construct a search result
	 */
	protected ConceptSearchResult(ConceptWord conceptWord) {
		if (conceptWord != null) {
			this.concept = conceptWord.getConcept();
			this.conceptName = conceptWord.getConceptName();
			this.word = conceptWord.getWord();
			// if a null value is passed in, ignore it and maintain the default
			// of 0.0
			if (conceptWord.getWeight() != null)
				this.transientWeight = conceptWord.getWeight();
		}
	}
	
	/**
	 * Convenience constructor
	 * 
	 * @param word the single word that will be matched to search terms
	 * @param concept the concept that is being matched to
	 * @param conceptName the specific name that will be matched
	 */
	public ConceptSearchResult(String word, Concept concept, ConceptName conceptName) {
		this.concept = concept;
		this.conceptName = conceptName;
		this.word = word;
	}
	
	/**
	 * Convenience constructor that takes in a weight too
	 * 
	 * @param word the single word that will be matched to search terms
	 * @param concept the concept that is being matched to
	 * @param conceptName the specific name that will be matched
	 * @param transientWeight the weight for this conceptSearchResult
	 */
	public ConceptSearchResult(String word, Concept concept, ConceptName conceptName, Double transientWeight) {
		this.concept = concept;
		this.conceptName = conceptName;
		this.word = word;
		if (transientWeight != null)
			this.transientWeight = transientWeight;
	}
	
	/**
	 * @return the concept
	 */
	public Concept getConcept() {
		return concept;
	}
	
	/**
	 * @param concept the concept to set
	 */
	public void setConcept(Concept concept) {
		this.concept = concept;
	}
	
	/**
	 * @return the conceptName
	 */
	public ConceptName getConceptName() {
		return conceptName;
	}
	
	/**
	 * @param conceptName the conceptName to set
	 */
	public void setConceptName(ConceptName conceptName) {
		this.conceptName = conceptName;
	}
	
	/**
	 * @return the word
	 */
	public String getWord() {
		return word;
	}
	
	/**
	 * @param word the word to set
	 */
	public void setWord(String word) {
		this.word = word;
	}
	
	/**
	 * Getter for transientWeight
	 * 
	 * @return
	 */
	public Double getTransientWeight() {
		return transientWeight;
	}
	
	/**
	 * Setter transientWeight
	 * 
	 * @param transientWeight
	 */
	public void setTransientWeight(Double transientWeight) {
		this.transientWeight = transientWeight;
	}
}
