package edu.columbia.dbmi.ohdsims.pojo;

import java.util.List;

/**
 * 
 * */
public class Item {
Concept concept;

boolean includeDescendants;
boolean isExcluded;
boolean includeMapped;
public Concept getConcept() {
	return concept;
}
public void setConcept(Concept concept) {
	this.concept = concept;
}
public boolean isIncludeDescendants() {
	return includeDescendants;
}
public void setIncludeDescendants(boolean includeDescendants) {
	this.includeDescendants = includeDescendants;
}
public boolean isExcluded() {
	return isExcluded;
}
public void setExcluded(boolean isExcluded) {
	this.isExcluded = isExcluded;
}
public boolean isIncludeMapped() {
	return includeMapped;
}
public void setIncludeMapped(boolean includeMapped) {
	this.includeMapped = includeMapped;
}



}
