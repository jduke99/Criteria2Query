package edu.columbia.dbmi.ohdsims.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import edu.columbia.dbmi.ohdsims.pojo.Attribute;
import edu.columbia.dbmi.ohdsims.pojo.Concept;
import edu.columbia.dbmi.ohdsims.pojo.ConceptRecordCount;
import edu.columbia.dbmi.ohdsims.pojo.ConceptSet;
import edu.columbia.dbmi.ohdsims.pojo.Criteria;
import edu.columbia.dbmi.ohdsims.pojo.Criteria2query;
import edu.columbia.dbmi.ohdsims.pojo.Entity;
import edu.columbia.dbmi.ohdsims.pojo.Expression;
import edu.columbia.dbmi.ohdsims.pojo.ExtendConcept;
import edu.columbia.dbmi.ohdsims.pojo.Item;
import edu.columbia.dbmi.ohdsims.util.CmdUtil;
import edu.columbia.dbmi.ohdsims.util.HttpUtil;
import edu.columbia.dbmi.ohdsims.util.ATLASUtil;
import edu.columbia.dbmi.ohdsims.util.JSONUtil;
import edu.columbia.dbmi.ohdsims.util.NLPUtil;
import edu.columbia.dbmi.ohdsims.util.StringUtil;
import edu.columbia.dbmi.ohdsims.util.WebUtil;
import edu.columbia.dbmi.ohdsims.util.XMLUtil;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.SentenceUtils;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.util.CoreMap;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/nlpmethod")
public class NlpController {

	@RequestMapping("/getct")
	@ResponseBody
	public Map<String, Object> getCrteriafromCT(HttpSession httpSession, HttpServletRequest request, String nctid)
			throws Exception {
		String url = "https://clinicaltrials.gov/show/" + nctid + "?displayxml=true";
		String response = WebUtil.getCTByNctid(nctid);
		String criteria = WebUtil.parse(response);
		StringBuffer insb = new StringBuffer();
		StringBuffer exsb = new StringBuffer();
		// Using stanfordNLP to do sentence segment
		Reader reader = new StringReader(criteria);

		DocumentPreprocessor dp = new DocumentPreprocessor(reader);
		List<String> sentenceList = new ArrayList<String>();

		for (List<HasWord> sentence : dp) {
			// SentenceUtils not Sentence
			String sentenceString = SentenceUtils.listToString(sentence);
			sentenceList.add(sentenceString);
		}
		ArrayList<String> inlist = new ArrayList<String>();
		ArrayList<String> exlist = new ArrayList<String>();
		boolean flag = false;
		for (String sentence : sentenceList) {

			if (sentence.toLowerCase().contains("inclusion criteria")
					&& (sentence.toLowerCase().contains("exclusion criteria") == false)) {

				int pos = sentence.toLowerCase().indexOf("inclusion criteria :");
				inlist.add(sentence.toString().substring(pos + "inclusion criteria :".length()));

			} else if ((sentence.toLowerCase().contains("inclusion criteria") == false)
					&& sentence.toLowerCase().contains("exclusion criteria")) {
				flag = true;

				int pos = sentence.toLowerCase().indexOf("exclusion criteria :");
				exlist.add(sentence.toString().substring(pos + "exclusion criteria :".length()));

			} else if (sentence.toLowerCase().contains("inclusion criteria")
					&& sentence.toLowerCase().contains("exclusion criteria")) {
				flag = true;
				int inindex = sentence.toLowerCase().indexOf("inclusion criteria :");
				int posindex = sentence.toLowerCase().indexOf("exclusion criteria :");
				inlist.add(sentence.toString().substring(inindex + "inclusion criteria :".length(), posindex));
				exlist.add(sentence.toString().substring(posindex + "exclusion criteria :".length()));
			} else {
				if (flag == false) {
					inlist.add(sentence);
				} else {
					exlist.add(sentence);
				}
			}

		}

		for (int x = 0; x < inlist.size(); x++) {
			System.out.println(inlist.get(x));
			insb.append(inlist.get(x).replaceAll("-LRB-", "(").replaceAll("-RRB-", ")"));
			insb.append("\n");
		}

		for (int y = 0; y < exlist.size(); y++) {
			System.out.println(exlist.get(y));
			exsb.append(exlist.get(y).replaceAll("-LRB-", "(").replaceAll("-RRB-", ")"));
			exsb.append("\n");
		}
		Map<String, Object> map = null;
		map = new HashMap<String, Object>();
		map.put("inc", insb.toString());
		map.put("exc", exsb.toString());// ----
		JSONObject jsonObject = JSONObject.fromObject(map);
		System.out.println(jsonObject);
		return jsonObject;

	}
	//
	// @RequestMapping("/parse")
	// @ResponseBody
	// public Map<String, Object> oldparseCriteria(HttpSession httpSession,
	// HttpServletRequest request, String inc,
	// String exc) throws Exception {
	// Map<String, Object> map = new HashMap<String, Object>();
	// List<Criteria2query> inclu=new ArrayList<Criteria2query>();
	// List<Criteria2query> exclu=new ArrayList<Criteria2query>();
	// CmdUtil.save2File("/Users/yuanchi/Documents/git/EliIE/Tempfile/EliIE_input_free_text.txt",
	// inc);
	// String result = CmdUtil.callEliIE("");
	// String conceptsetname = "";
	// List<Criteria> criterias = XMLUtil.parseXML(result);
	// JSONArray ja=new JSONArray();
	// System.out.println("criterias size ="+criterias.size());
	// for (int i = 0; i < criterias.size(); i++) {
	// Criteria2query cq=new Criteria2query();
	// String sentence = criterias.get(i).getSentence();
	// List<Entity> le = criterias.get(i).getEntities();
	// String str = StringUtil.markResult(sentence, le);
	// cq.setId(i);
	// cq.setCriteria(str);
	// cq.setDatabase("1PCT");
	// cq.setPatient("0");
	// inclu.add(cq);
	// }
	// System.out.println("exc=="+exc);
	// CmdUtil.save2File("/Users/yuanchi/Documents/git/EliIE/Tempfile/EliIE_input_free_text.txt",
	// exc);
	// String exresult = CmdUtil.callEliIE("");
	// List<Criteria> excriterias = XMLUtil.parseXML(exresult);
	// System.out.println("criterias size ="+excriterias.size());
	// for (int i = 0; i < excriterias.size(); i++) {
	// Criteria2query cq=new Criteria2query();
	// String sentence = excriterias.get(i).getSentence();
	// List<Entity> le = excriterias.get(i).getEntities();
	// String str = StringUtil.markResult(sentence, le);
	// cq.setId(i);
	// cq.setCriteria(str);
	// cq.setDatabase("1PCT");
	// cq.setPatient("0");
	// exclu.add(cq);
	// }
	// map.put("include", inclu);
	// map.put("exclude", exclu);
	// long startTime = System.currentTimeMillis();
	// HashMap<Integer,Integer> hcs=ATLASUtil.matchConceptSet(criterias);
	//
	// for (Map.Entry<Integer, Integer> entry : hcs.entrySet()) {
	// System.out.println("Key = " + entry.getKey() + ", Value = " +
	// entry.getValue());
	// }
	// long endTime = System.currentTimeMillis();
	// System.out.println("Call API time (unit:millisecond)：" + (endTime -
	// startTime));
	// return map;
	//
	// }

	@RequestMapping("/parse")
	@ResponseBody
	public Map<String, Object> parseCriteria(HttpSession httpSession, HttpServletRequest request, String inc,
			String exc, ModelMap model) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Criteria2query> inclu = new ArrayList<Criteria2query>();
		List<Criteria2query> exclu = new ArrayList<Criteria2query>();
		NLPUtil nputil = new NLPUtil();
		List<Criteria> criterias = nputil.startParse(inc);
		for (int i = 0; i < criterias.size(); i++) {
			Criteria2query cq = new Criteria2query();
			String sentence = criterias.get(i).getSentence();
			List<Entity> le = criterias.get(i).getEntities();
			String str = StringUtil.markResult(sentence, le);
			cq.setId(i);
			cq.setCriteria(str);
			if (le.size() == 0) {
				cq.setEhrstatus(false);
			} else {
				cq.setEhrstatus(true);
			}
			inclu.add(cq);
		}

		// List<Criteria> excriterias = XMLUtil.parseXML(exresult);
		List<Criteria> excriterias = nputil.startParse(exc);

		for (int i = 0; i < excriterias.size(); i++) {
			Criteria2query cq = new Criteria2query();
			String sentence = excriterias.get(i).getSentence();
			List<Entity> le = excriterias.get(i).getEntities();
			String str = StringUtil.markResult(sentence, le);
			cq.setId(i);
			cq.setCriteria(str);
			if (le.size() == 0) {
				cq.setEhrstatus(false);
			} else {
				cq.setEhrstatus(true);
			}
			exclu.add(cq);
		}
		map.put("include", inclu);
		map.put("exclude", exclu);
		System.out.println("done");
		httpSession.setAttribute("includecriterias", criterias);
		httpSession.setAttribute("excludecriterias", excriterias);

		return map;
	}

	@RequestMapping("/conceptset")
	public String conceptSet(HttpSession httpSession, HttpServletRequest request, String inc, String exc,
			ModelMap model) throws Exception {
		return "conceptSetPage";

	}

	@RequestMapping("/searchconceptsets")
	@ResponseBody
	public Map<String, Object> searchConceptSet(HttpSession httpSession, HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		List<Criteria> criterias = (List<Criteria>) httpSession.getAttribute("includecriterias");
		List<Criteria> excriterias = (List<Criteria>) httpSession.getAttribute("excludecriterias");
		List<Criteria> allcriterias = new ArrayList<Criteria>();
		allcriterias.addAll(criterias);
		allcriterias.addAll(excriterias);
		long startTime = System.currentTimeMillis();
		System.out.println("mapping!!!!!---->");
		int index = 0;
		try {
			for (int i = 0; i < allcriterias.size(); i++) {
				List<Entity> elist = allcriterias.get(i).getEntities();
				System.out.println("size=" + elist.size());
				for (int j = 0; j < elist.size(); j++) {
					String conceptsetname = elist.get(j).getEntityname();
					String domain = elist.get(j).getDomain();
					System.out.println(">" + conceptsetname);
					System.out.println("domain->" + domain);
					LinkedHashMap<ConceptSet, Integer> hcs = ATLASUtil.matchConceptSetByEntity(conceptsetname);
					List<ConceptSet> lscst = new ArrayList<ConceptSet>();
					for (Map.Entry<ConceptSet, Integer> entry : hcs.entrySet()) {
						lscst.add(entry.getKey());
					}
					map.put("conceptset" + index, lscst);
					map.put("cstname" + index, conceptsetname);

					map.put("domain" + index, domain);
					index++;
				}
			}

		} catch (Exception ex) {
			System.out.println("--->" + ex.getMessage());
		}
		return map;

	}

	@RequestMapping("/searchconcept")
	@ResponseBody
	public Map<String, Object> searchConcept(HttpSession httpSession, String word, String domain) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		System.out.println("word=" + word);
		System.out.println("domain=" + domain);
		if (word == null) {
			System.out.println("word?");
			map.put("conceptlist", null);
			JSONObject jsonObject = JSONObject.fromObject(map);
			return jsonObject;

		}
		List<ExtendConcept> econceptlist = new ArrayList<ExtendConcept>();
		Map<Concept, ConceptRecordCount> extendconcept = ATLASUtil.searchConcept(word.trim(), domain);
		if (extendconcept == null) {
			System.out.println("extendconcept?");
			map.put("conceptlist", null);
			JSONObject jsonObject = JSONObject.fromObject(map);
			return jsonObject;
		}
		for (Map.Entry<Concept, ConceptRecordCount> entry : extendconcept.entrySet()) {
			ExtendConcept ec = new ExtendConcept(entry.getKey(), entry.getValue());
			econceptlist.add(ec);
		}
		Collections.sort(econceptlist, new Comparator<ExtendConcept>() {
			@Override
			public int compare(ExtendConcept o1, ExtendConcept o2) {
				if (o1.getDRC() < o2.getDRC()) {
					return 1;
				}
				if (o1.getDRC() == o2.getDRC()) {
					return 0;
				}
				return -1;
			}
		});
		map.put("conceptlist", econceptlist);
		JSONObject jsonObject = JSONObject.fromObject(map);
		return jsonObject;
	}

	@RequestMapping("/allconceptsets")
	@ResponseBody
	public Map<String, Object> getallConceptSets(HttpSession httpSession, String conceptsets) throws Exception {
		System.out.println("conceptsets=" + conceptsets);
		List<Criteria> incriterias = (List<Criteria>) httpSession.getAttribute("includecriterias");
		List<Criteria> excriterias = (List<Criteria>) httpSession.getAttribute("excludecriterias");
		Map<String, Object> map = new HashMap<String, Object>();
		JSONArray ja = JSONArray.fromObject(conceptsets);
		int inlength = 0;
		JSONObject jcs = new JSONObject();
		JSONArray jaa = JSONUtil.transForm2JSON(incriterias, excriterias, ja);
		JSONArray janull = new JSONArray();
		JSONObject jonull = new JSONObject();
		JSONObject joaddc = JSONUtil.setAdditionalCriteria(incriterias, excriterias, ja);
		JSONObject jofirst = new JSONObject();
		jofirst.accumulate("Type", "First");
		jcs.accumulate("ConceptSets", jaa);
		jcs.accumulate("PrimaryCriteria", jonull);
		jcs.accumulate("AdditionalCriteria", joaddc);
		jcs.accumulate("QualifiedLimit", jofirst);
		jcs.accumulate("ExpressionLimit", jofirst);
		jcs.accumulate("InclusionRules", janull);
		jcs.accumulate("CensoringCriteria", janull);
		System.out.println(jcs);
		httpSession.setAttribute("jsonresult", jcs.toString());
		return map;
	}

	@RequestMapping("/jsonpage")
	public String toJSONPage(HttpSession httpSession, String conceptsets) throws Exception {
		return "jsonresultPage";
	}

	@RequestMapping("/jsonresult")
	@ResponseBody
	public Map<String, Object> jumptoConceptSets(HttpSession httpSession, String conceptsets) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String jsonstr = (String) httpSession.getAttribute("jsonresult");
		map.put("jsonstr", jsonstr);
		return map;
	}

	@RequestMapping("/formatdata")
	@ResponseBody
	public Map<String, Object> formatdata(HttpSession httpSession, String conceptsets, String inc, String exc)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String afterinc = inc.replaceAll("[\\t\\n\\r]", " ");
		String afterexc = exc.replaceAll("[\\t\\n\\r]", " ");
		List<String> incsentencelist = NLPUtil.preprocessDoc(afterinc);
		List<String> exccsentencelist = NLPUtil.preprocessDoc(afterexc);
		StringBuffer sbinc = new StringBuffer();
		StringBuffer sbexc = new StringBuffer();
		for (int a = 0; a < incsentencelist.size(); a++) {
			System.out.println("[[[[" + incsentencelist.get(a));
			sbinc.append(incsentencelist.get(a));
			if (a < (incsentencelist.size() - 1)) {
				sbinc.append("\n");
			}
		}
		for (int a = 0; a < exccsentencelist.size(); a++) {
			sbexc.append(exccsentencelist.get(a));
			if (a < (exccsentencelist.size() - 1)) {
				sbexc.append("\n");
			}
		}

		map.put("afterinc", sbinc.toString().replace("-LRB-", "(").replaceAll("-RRB-", ")"));
		map.put("afterexc", sbexc.toString().replace("-LRB-", "(").replaceAll("-RRB-", ")"));
		return map;
	}
}
