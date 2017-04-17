package edu.columbia.dbmi.ohdsims.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
//import org.apache.commons.httpclient.HttpClient;
//import org.apache.commons.httpclient.methods.PostMethod;
//import org.apache.commons.httpclient.methods.RequestEntity;
//import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import edu.columbia.dbmi.ohdsims.pojo.Concept;
import edu.columbia.dbmi.ohdsims.pojo.ConceptRecordCount;
import edu.columbia.dbmi.ohdsims.pojo.ConceptSet;
import edu.columbia.dbmi.ohdsims.pojo.Criteria;
import edu.columbia.dbmi.ohdsims.pojo.Entity;
import edu.columbia.dbmi.ohdsims.pojo.Item;
import net.sf.json.JSONArray;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;

/*
* 使用的类：
* HttpClient
* HttpPost
* FileBody
* FormBodyPart
* MultipartEntity
* HttpResponse
* EntityUtils
* 
*/

public class ATLASUtil {
	// 接口地址
	private final static String recordcounturl = "http://api.ohdsi.org/WebAPI/cdmresults/1PCT/conceptRecordCount";
	private final static String vocubularyurl = "http://api.ohdsi.org/WebAPI/vocabulary/1PCT/search";
	private final static String conceptseturl = "http://api.ohdsi.org/WebAPI/conceptset/";
	private final String pattern = "yyyy-MM-dd HH:mm:ss:SSS";
	private HttpClient httpClient = null;
	private HttpPost method = null;
	private static long startTime = 0L;
	private static long endTime = 0L;
	private int status = 0;

	public static void main(String[] args) throws IOException {
		// Concept concept=getMaxCountConcept("type 2 diabetes","Condition");
		// System.out.println("----->"+concept.getCONCEPT_NAME());
		// System.out.println("conceptid="+conceptidindex.get(rindex));
		searchConcept("insulin", "Drug");
	}

	// public static Concept getMaxCountConcept(String entity,String domainid)
	// throws UnsupportedEncodingException, IOException, ClientProtocolException
	// {
	// Concept concept=new Concept();
	// //<Integer,Integer> conceptidindex=new LinkedHashMap<Integer,Integer>();
	// HashMap<Integer,ConceptRecordCount> hm=new
	// HashMap<Integer,ConceptRecordCount>();
	//
	// JSONObject queryjson=new JSONObject();
	// queryjson.accumulate("QUERY", entity);
	// queryjson.accumulate("DOMAIN_ID", "['"+domainid+"']");
	// queryjson.accumulate("VOCABULARY_ID", "['SNOMED']");
	// System.out.println("queryjson:"+queryjson);
	// String vocabularyresult=getConcept(queryjson);
	// JSONArray array = JSONArray.fromObject(vocabularyresult);
	// List<Concept> list = JSONArray.toList(array, Concept.class);// 过时方法
	// //System.out.println(list.get(0).getCONCEPT_NAME());
	// List<Integer> conceptidlist=new ArrayList<Integer>();
	// for(int i=0;i<list.size();i++){
	// conceptidlist.add(list.get(i).getCONCEPT_ID());
	// }
	// JSONArray conceptarr = JSONArray.fromObject(conceptidlist);
	// if(conceptarr.size()>0){
	// String co=getRecordCount(conceptarr);
	// JSONArray carray = JSONArray.fromObject(co);
	// //JSONNull jnull = new JSONNull();
	// //List<ConceptRecordCount> rclist=new ArrayList<ConceptRecordCount>();
	// for(int x=0;x<carray.size();x++){
	// //if(){
	//
	// JSONObject js=(JSONObject)carray.get(x);
	//
	// JSONArray v=JSONArray.fromObject(js.get("value"));
	// ConceptRecordCount crd=new ConceptRecordCount();
	// crd.setRC((int) v.get(0));
	// crd.setDRC((int) v.get(1));
	// hm.put((Integer) js.get("key"),crd);
	//
	// }
	//
	// int rindex=0;
	// int drcmax=0;
	// int rcmax=0;
	// for (Entry<Integer, ConceptRecordCount> entry : hm.entrySet()) {
	// if(entry.getValue().getDRC()>=drcmax){
	// drcmax=entry.getValue().getDRC();
	// rcmax=entry.getValue().getRC();
	// rindex=entry.getKey();
	// }
	//
	// }
	// for(int y=0;y<list.size();y++){
	// if(list.get(y).getCONCEPT_ID()==rindex){
	// concept=list.get(y);
	//// concept.setRC(rcmax);
	//// concept.setDRC(drcmax);
	//
	// }
	// }
	// return concept;
	// }else{
	// return concept;
	// }
	// }

	public static String getConcept(JSONObject jjj)
			throws UnsupportedEncodingException, IOException, ClientProtocolException {
		HttpPost httppost = new HttpPost(vocubularyurl);
		// httppost.setHeader("X-GWT-Permutation",
		// "3DE824138FE65400740EC1816A73CACC");
		httppost.setHeader("Content-Type", "application/json");
		StringEntity se = new StringEntity(jjj.toString());
		httppost.setEntity(se);
		startTime = System.currentTimeMillis();
		HttpResponse httpresponse = new DefaultHttpClient().execute(httppost);
		endTime = System.currentTimeMillis();
		System.out.println("statusCode:" + httpresponse.getStatusLine().getStatusCode());
		System.out.println("Call API time (unit:millisecond)：" + (endTime - startTime));

		if (httpresponse.getStatusLine().getStatusCode() == 200) {
			// System.out.println("succeed!");
			String strResult = EntityUtils.toString(httpresponse.getEntity());
			return strResult;
			// httppost.
		} else {
			return null;
		}
	}

	public static String getRecordCount(JsonArray array)
			throws UnsupportedEncodingException, IOException, ClientProtocolException {
		HttpPost httppost = new HttpPost(recordcounturl);
		httppost.setHeader("Content-Type", "application/json");
		StringEntity se = new StringEntity(array.toString());
		httppost.setEntity(se);
		startTime = System.currentTimeMillis();
		HttpResponse httpresponse = new DefaultHttpClient().execute(httppost);
		endTime = System.currentTimeMillis();
		System.out.println("statusCode:" + httpresponse.getStatusLine().getStatusCode());
		System.out.println("Call API time (unit:millisecond)：" + (endTime - startTime));

		if (httpresponse.getStatusLine().getStatusCode() == 200) {
			String strResult = EntityUtils.toString(httpresponse.getEntity());
			return strResult;
		} else {
			return null;
		}
	}

	public static List<ConceptSet> getallConceptSet() {
		String strResult = HttpUtil.doGet(conceptseturl);
		JSONArray array = JSONArray.fromObject(strResult);
		// System.out.println("-->"+array);
		List<ConceptSet> list = JSONArray.toList(array, ConceptSet.class);// 过时方法
		return list;
	}

	/**
	 * 
	 * Traversing All ConceptSets for String match all the Concept from Name
	 * entity recognition results Author: chiyuan
	 * 
	 */
	public static LinkedHashMap<ConceptSet, Integer> matchConceptSetByContent(List<Criteria> clist) {
		HashMap<ConceptSet, Integer> candidatecs = new HashMap<ConceptSet, Integer>();
		int count = 0;
		List<ConceptSet> cslist = getallConceptSet();
		for (int y = 0; y < cslist.size(); y++) {
			count = 0;
			ConceptSet cst = cslist.get(y);
			Integer conceptsetid = cst.getId();
			String path = conceptseturl + conceptsetid + "/expression";
			// System.out.println("path="+path);
			String exp = HttpUtil.doGet(path);
			try {
				Gson gson = new Gson();
				JsonObject jsonobject = new JsonParser().parse(exp).getAsJsonObject();
				JsonArray ja = (JsonArray) jsonobject.get("items");
				List<Item> ilist = gson.fromJson(ja, new TypeToken<List<Item>>() {
				}.getType());

				// System.out.println(items);
				for (int j = 0; j < clist.size(); j++) {
					List<Entity> elist = clist.get(j).getEntities();
					for (int k = 0; k < elist.size(); k++) {
						// System.out.println("entity="+elist.get(k).getEntityname());
						for (int i = 0; i < ilist.size(); i++) {

							if (ilist.get(i).getConcept() != null) {
								if (ilist.get(i).getConcept().getCONCEPT_NAME()
										.contains(elist.get(k).getEntityname())) {
									count++;
									System.out.println(ilist.get(i).getConcept().getCONCEPT_NAME());
									break;
								}
							}
						}
					}
				}
				if (count > 0) {
					candidatecs.put(cst, count);
					System.out.println(conceptsetid + ":" + count);
				}
			} catch (Exception ex) {
				System.out.println("-->" + ex.getMessage());
			}
		}
		List<Map.Entry<ConceptSet, Integer>> sort = new ArrayList<>();
		sort.addAll(candidatecs.entrySet());
		ValueComparator vc = new ValueComparator();
		Collections.sort(sort, vc);
		LinkedHashMap<ConceptSet, Integer> lm = new LinkedHashMap<ConceptSet, Integer>();
		for (int i = 0; i < sort.size(); i++) {
			System.out.println(sort.get(i));
			lm.put(sort.get(i).getKey(), sort.get(i).getValue());
		}
		return lm;
	}

	public static LinkedHashMap<ConceptSet, Integer> matchConceptSetByName(List<Criteria> clist) {
		HashMap<ConceptSet, Integer> candidatecs = new HashMap<ConceptSet, Integer>();
		// arrange by length similarity
		int distance = 0;
		List<ConceptSet> cslist = getallConceptSet();
		for (int i = 0; i < clist.size(); i++) {
			List<Entity> elist = clist.get(i).getEntities();
			for (int j = 0; j < elist.size(); j++) {
				String entityname = elist.get(j).getEntityname();
				System.out.println("=>" + entityname + "!");
				for (int k = 0; k < cslist.size(); k++) {
					if (cslist.get(k).getName().toLowerCase().contains(entityname.trim())) {
						System.out.println("match=" + cslist.get(k).getName());
						distance = cslist.get(k).getName().length() - entityname.trim().length();
						candidatecs.put(cslist.get(k), distance);
					}
				}
			}
		}
		List<Map.Entry<ConceptSet, Integer>> sort = new ArrayList<>();
		sort.addAll(candidatecs.entrySet());
		ValueComparator vc = new ValueComparator();
		Collections.sort(sort, vc);
		LinkedHashMap<ConceptSet, Integer> lm = new LinkedHashMap<ConceptSet, Integer>();
		for (int i = sort.size() - 1; i >= 0; i--) {
			lm.put(sort.get(i).getKey(), sort.get(i).getValue());
		}
		return lm;

	}

	public static LinkedHashMap<ConceptSet, Integer> matchConceptSetByEntity(String entityname) {
		HashMap<ConceptSet, Integer> candidatecs = new HashMap<ConceptSet, Integer>();
		// arrange by length similarity
		int distance = 0;
		List<ConceptSet> cslist = getallConceptSet();
		for (int k = 0; k < cslist.size(); k++) {
			if (cslist.get(k).getName().toLowerCase().contains(entityname.toLowerCase().trim())) {
				System.out.println("match=" + cslist.get(k).getName());
				distance = cslist.get(k).getName().length() - entityname.trim().length();
				candidatecs.put(cslist.get(k), distance);
			}

		}
		List<Map.Entry<ConceptSet, Integer>> sort = new ArrayList<>();
		sort.addAll(candidatecs.entrySet());
		ValueComparator vc = new ValueComparator();
		Collections.sort(sort, vc);
		LinkedHashMap<ConceptSet, Integer> lm = new LinkedHashMap<ConceptSet, Integer>();
		for (int i = sort.size() - 1; i >= 0; i--) {
			lm.put(sort.get(i).getKey(), sort.get(i).getValue());
		}
		return lm;

	}

	public static Map<Concept, ConceptRecordCount> searchConcept(String entity, String domainid)
			throws UnsupportedEncodingException, IOException, ClientProtocolException {
		List<Concept> conceptlist = new ArrayList<Concept>();
		HashMap<Concept, ConceptRecordCount> hm = new HashMap<Concept, ConceptRecordCount>();
		JSONObject queryjson = new JSONObject();
		queryjson.accumulate("QUERY", entity);
		queryjson.accumulate("DOMAIN_ID", "['" + domainid + "']");
		queryjson.accumulate("VOCABULARY_ID", "['SNOMED']");
		System.out.println("queryjson:" + queryjson);
		String vocabularyresult = getConcept(queryjson);
		System.out.println("vocabularyresult  length=" + vocabularyresult.length());
		Gson gson = new Gson();
		JsonArray ja = new JsonParser().parse(vocabularyresult).getAsJsonArray();
		if (ja.size() == 0) {
			System.out.println("size=" + ja.size());
			return null;
		}
		List<Concept> list = gson.fromJson(ja, new TypeToken<List<Concept>>() {
		}.getType());

		// extract concept id
		List<Integer> conceptidlist = new ArrayList<Integer>();
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i).getCONCEPT_ID() + "--" + list.get(i).getCONCEPT_NAME());
			conceptidlist.add(list.get(i).getCONCEPT_ID());
		}

		JsonArray conceptarr = (JsonArray) new Gson().toJsonTree(conceptidlist, new TypeToken<List<Integer>>() {
		}.getType());

		// get record count by concept id array
		if (conceptarr.size() > 0) {
			String co = getRecordCount(conceptarr);
			JSONArray carray = JSONArray.fromObject(co);
			System.out.println("carray-" + carray.size());
			for (int c = 0; c < list.size(); c++) {
				if (carray.size() == 0) {
					ConceptRecordCount crd = new ConceptRecordCount();
					crd.setRC(-1);
					crd.setDRC(-1);
					hm.put(list.get(c), crd);
				} else {
					for (int x = 0; x < carray.size(); x++) {
						JSONObject js = (JSONObject) carray.get(x);
						JSONArray v = JSONArray.fromObject(js.get("value"));
						ConceptRecordCount crd = new ConceptRecordCount();
						crd.setRC((int) v.get(0));
						crd.setDRC((int) v.get(1));
						if (list.get(c).getCONCEPT_ID().equals(js.get("key"))) {
							hm.put(list.get(c), crd);
						}
					}
				}

			}
			System.out.println("yes!!");
			return hm;
		} else {
			System.out.println("no!!");
			return null;
		}
	}
	public static JSONObject querybyconceptid(int conceptid){
		JSONObject jot=new JSONObject();
    	String re2=HttpUtil.doGet("http://api.ohdsi.org/WebAPI/conceptset/"+conceptid);
    	JSONObject jore2=JSONObject.fromObject(re2);
    	jot.accumulateAll(jore2);
    	String re3=HttpUtil.doGet("http://api.ohdsi.org/WebAPI/conceptset/"+conceptid+"/expression");
    	
    	JSONObject expression=JSONObject.fromObject(re3);
    	jot.accumulate("expression",expression);
    	
    	return jot;
	}
	

}