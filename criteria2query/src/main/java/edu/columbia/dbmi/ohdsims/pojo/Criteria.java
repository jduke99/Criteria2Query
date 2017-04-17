package edu.columbia.dbmi.ohdsims.pojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Criteria
 * logic: List are store each entities
 * 
 * */
public class Criteria {
	String sentence;
	//List<HashSet<Entity>> logic = new ArrayList<>();
	List<Entity> entities;
	HashSet<Attribute> ahs;
	HashMap<String,String> relations;
		
	public HashMap<String, String> getRelations() {
		return relations;
	}

	public void setRelations(HashMap<String, String> relations) {
		this.relations = relations;
	}

	public List<Entity> getEntities() {
		return entities;
	}

	public void setEntities(List<Entity> entities) {
		this.entities = entities;
	}

	public HashSet<Attribute> getAhs() {
		return ahs;
	}
	
	public String getSentence() {
		return sentence;
	}

	public void setSentence(String sentence) {
		this.sentence = sentence;
	}
//
//	public List<HashSet<Entity>> getLogic() {
//		return logic;
//	}
//
//	public void setLogic(List<HashSet<Entity>> logic) {
//		this.logic = logic;
//	}

	public void setAhs(HashSet<Attribute> ahs) {
		this.ahs = ahs;
	}
}
