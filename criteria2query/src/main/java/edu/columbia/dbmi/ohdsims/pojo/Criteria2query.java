package edu.columbia.dbmi.ohdsims.pojo;

public class Criteria2query {
int id;
String criteria;
String database;
String patient;
boolean ehrstatus;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}

public boolean isEhrstatus() {
	return ehrstatus;
}
public void setEhrstatus(boolean ehrstatus) {
	this.ehrstatus = ehrstatus;
}
public String getCriteria() {
	return criteria;
}

public void setCriteria(String criteria) {
	this.criteria = criteria;
}
public String getDatabase() {
	return database;
}
public void setDatabase(String database) {
	this.database = database;
}
public String getPatient() {
	return patient;
}
public void setPatient(String patient) {
	this.patient = patient;
}

}
