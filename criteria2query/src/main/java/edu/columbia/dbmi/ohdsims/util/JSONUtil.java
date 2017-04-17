package edu.columbia.dbmi.ohdsims.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

import com.google.gson.Gson;

import edu.columbia.dbmi.ohdsims.pojo.Attribute;
import edu.columbia.dbmi.ohdsims.pojo.Concept;
import edu.columbia.dbmi.ohdsims.pojo.ConceptSet;
import edu.columbia.dbmi.ohdsims.pojo.Criteria;
import edu.columbia.dbmi.ohdsims.pojo.Entity;
import edu.columbia.dbmi.ohdsims.pojo.Expression;
import edu.columbia.dbmi.ohdsims.pojo.Item;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JSONUtil {
	public static void main(String[] args) {
		JSONArray criterialist = new JSONArray();

		JSONObject criteriaunit = new JSONObject();
		JSONObject occurrence = new JSONObject();
		JSONObject startwindow = new JSONObject();
		JSONObject criteria = new JSONObject();
		criteria = setCriteria("Condition", 2);
		boolean inc = true;
		boolean exc = false;
		if (inc == true) {
			occurrence = setOccurrence(2, 1);
			startwindow = setTemporalWindow(1);
		}
		if (exc == true) {
			occurrence = setOccurrence(0, 0);
		}
		criteriaunit.accumulate("Criteria", criteria);
		criteriaunit.accumulate("StartWindow", startwindow);
		criteriaunit.accumulate("Occurrence", occurrence);

		System.out.println(criteriaunit);
	}
	public static JSONArray transForm2JSON(List<Criteria> incriterias,List<Criteria> excriterias,JSONArray ja){
		int inlength=0;
		JSONArray includeconceptsetarr=new JSONArray();
		JSONArray excludeconceptsetarr=new JSONArray();
		JSONArray totalconceptsetarr=new JSONArray();
		for (int x = 0; x < incriterias.size(); x++) {
			List<Entity> entitylist = incriterias.get(x).getEntities();
			for (int y = 0; y < entitylist.size(); y++) {
				Entity entity = entitylist.get(y);
				entity.getEntityname();
				String inentityindex=entity.getIndex();
				System.out.println("include-index="+inentityindex+" : inentity="+entity.getEntityname().trim());
				JSONObject jo = JSONObject.fromObject(ja.get(inlength));
				String entitystr=(String) jo.get("entity");
				inlength++;
				includeconceptsetarr.add(ATLASUtil.querybyconceptid((int) jo.get("conceptsetid")));
				totalconceptsetarr.add(ATLASUtil.querybyconceptid((int) jo.get("conceptsetid")));
			}
		}
		
		for (int x = 0; x < excriterias.size(); x++) {
			List<Entity> entitylist = excriterias.get(x).getEntities();
			for (int y = 0; y < entitylist.size(); y++) {
				Entity entity = entitylist.get(y);	
				String exentityindex=entity.getIndex();
				JSONObject jo = JSONObject.fromObject(ja.get(inlength));
				String entitystr=(String) jo.get("entity");
				inlength++;
				excludeconceptsetarr.add(ATLASUtil.querybyconceptid((int) jo.get("conceptsetid")));
				totalconceptsetarr.add(ATLASUtil.querybyconceptid((int) jo.get("conceptsetid")));
			}
		}
		return totalconceptsetarr;
	}

	public static JSONObject setOccurrence(int type, int count) {
		JSONObject jsonob = new JSONObject();
		jsonob.accumulate("Type", type);
		jsonob.accumulate("Count", count);
		return jsonob;
	}

	/**
	 * index 1: has history of 
	 * index 2: has 
	 * */
	public static JSONObject setTemporalWindow(int index) {
		JSONObject window = new JSONObject();
		JSONObject start = new JSONObject();
		JSONObject end = new JSONObject();
		if(index ==1){
			start.accumulate("Coeff", -1);
			// start.accumulate("Count", 0);
			end.accumulate("Coeff", "1");
			// end.accumulate("Count", 0);
			end.accumulate("Days", "0");	
		}
		window.accumulate("Start", start);
		window.accumulate("End", end);
		return window;
	}

	public static JSONObject setCriteria(String type, int conceptsetid) {
		JSONObject criteriatype = new JSONObject();
		JSONObject codesetId = new JSONObject();
		codesetId.accumulate("CodesetId", conceptsetid);
		if (type.equals("Condition")) {
			criteriatype.accumulate("ConditionOccurrence", codesetId);
		} else if (type.equals("Drug")) {
			criteriatype.accumulate("DrugExposure", codesetId);
		} else if (type.equals("Observation")) {
			criteriatype.accumulate("Observation", codesetId);
		} else if (type.equals("Procedure_Device")) {
			criteriatype.accumulate("DeviceExposure", codesetId);
		}
		return criteriatype;
	}

	public static JSONObject setAdditionalCriteria(List<Criteria> incriterias,List<Criteria> excriterias,JSONArray ja){	
		int conceptsetindex=0;
		JSONObject additionalcriteria=new JSONObject();
		JSONArray jarr=new JSONArray();
		for (int x = 0; x < incriterias.size(); x++) {
			List<Entity> entitylist=incriterias.get(x).getEntities();
			for(int y=0;y<entitylist.size();y++){
				JSONObject jo = JSONObject.fromObject(ja.get(conceptsetindex));
				jarr.add(setCriteriaUnit((int) jo.get("conceptsetid"),entitylist.get(y).isNeg(),true,entitylist.get(y).getDomain(),1));
				conceptsetindex++;
			}
			//jarr.add(setCriteriaUnit(conceptsetindex,))
		}
		for (int x = 0; x < excriterias.size(); x++) {
			List<Entity> exentitylist=excriterias.get(x).getEntities();
			for(int y=0;y<exentitylist.size();y++){	
				JSONObject jo = JSONObject.fromObject(ja.get(conceptsetindex));
				jarr.add(setCriteriaUnit((int) jo.get("conceptsetid"),exentitylist.get(y).isNeg(),false,exentitylist.get(y).getDomain(),1));
				conceptsetindex++;
			}
		}
		additionalcriteria.accumulate("Type", "ALL");
		additionalcriteria.accumulate("CriteriaList", jarr);
		JSONArray janull=new JSONArray();
		additionalcriteria.accumulate("DemographicCriteriaList", janull);
		additionalcriteria.accumulate("Groups", janull);
		return additionalcriteria;
	}
	/**
	 * inclusion :flag = true ; exclusion : flag=false;
	 * neg
	 * temporal 1 has history of 
	 * 
	 * */
	
	public static JSONObject setCriteriaUnit(int index,boolean neg,boolean flag,String type,int temporal){
		JSONObject criteriaunit = new JSONObject();
		JSONObject occurrence = new JSONObject();
		JSONObject startwindow = new JSONObject();
		JSONObject criteria = new JSONObject();
		criteria = setCriteria(type, index);
		if (neg^flag == true) {
			occurrence = setOccurrence(2, 1);	
		}else {
			occurrence = setOccurrence(0, 0);
		}
		startwindow = setTemporalWindow(temporal);
		criteriaunit.accumulate("Criteria", criteria);
		criteriaunit.accumulate("StartWindow", startwindow);
		criteriaunit.accumulate("Occurrence", occurrence);
		return criteriaunit;
	}
	
	
}
