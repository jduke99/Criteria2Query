package edu.columbia.dbmi.ohdsims.controller;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.columbia.dbmi.ohdsims.util.WebUtil;
import edu.columbia.dbmi.ohdsims.util.Word2vec;
import edu.columbia.dbmi.ohdsims.util.Word2vec.WordEntry;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

import org.python.core.PyFunction;
import org.python.core.PyInteger;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

@Controller
@RequestMapping("/ohdsi")
public class OhdsiController {
	@Autowired
	private Properties applicationProps;

	public Properties getApplicationProps() {
		return applicationProps;
	}

	public void setApplicationProps(Properties applicationProps) {
		this.applicationProps = applicationProps;
	}

	final static String WORD2VEC = "/Users/yuanchi/Downloads/GoogleNews-vectors-negative300.bin";

	@RequestMapping("/main")
	public String showmain() {
		//
		return "main";
	}

	@RequestMapping("/word2vec")
	public String showword2vec() {
		//
		return "word2vec";
	}

	@RequestMapping("/ft2query")
	public String showft2query() {
		String jumpUrl = applicationProps.getProperty("nermodel");
		System.out.println(jumpUrl);
		return "freetext2sql";
	}

	@RequestMapping("/addtable")
	public String showaddtable() {
		//
		return "addtable";
	}

	@RequestMapping("/conceptset")
	public String showConceptSet() {
		//
		return "conceptSetPage";
	}

	@RequestMapping("/word2vecbib")
	public String showword2vecbib() {
		//
		return "word2vecbib";
	}

	@RequestMapping("/eliresult")
	public String showeliresult() {
		//
		return "eliieresult";
	}

	// @RequestMapping("/getct")
	// @ResponseBody
	// public String payOrder(HttpSession httpSession, HttpServletRequest
	// request, String nctid) throws Exception {
	// // String resultString = null;
	// String url = "https://clinicaltrials.gov/show/" + nctid +
	// "?displayxml=true";
	// String response = WebUtil.getCTByNctid(nctid);
	// String criteria = WebUtil.parse(response);
	// // System.out.println(criteria);
	// //Using stanfordNLP to do sentence segment
	// Properties properties = new Properties();
	// properties.setProperty("annotators", "tokenize, ssplit, parse");
	// StanfordCoreNLP pipeline = new StanfordCoreNLP(properties);
	// List<CoreMap> sentences =
	// pipeline.process(criteria).get(CoreAnnotations.SentencesAnnotation.class);
	// StringBuffer sb = new StringBuffer();
	// for (CoreMap sentence : sentences) {
	//
	// if(sentence.toString().toLowerCase().contains("inclusion criteria:")){
	// sb.append("inclusion criteria:");
	// sb.append("\n");
	// sb.append(sentence.toString().substring("inclusion criteria:".length()));
	// sb.append("\n");
	// }
	// else if(sentence.toString().toLowerCase().contains("exclusion
	// criteria:")){
	// sb.append("exclusion criteria:");
	// sb.append("\n");
	// sb.append(sentence.toString().substring("exclusion criteria:".length()));
	// sb.append("\n");
	//
	// }
	// else{
	// sb.append(sentence.toString());
	// sb.append("\n");
	// }
	//
	// // System.out.println(sentence.toString());
	// }
	//
	// return sb.toString();
	// }

	@RequestMapping("/format")
	@ResponseBody
	public String formatFreetext(HttpSession httpSession, HttpServletRequest request, String freetextinput)
			throws Exception {
		// String resultString = null;

		String criteria = freetextinput;
		criteria = criteria.replaceAll("\\s+", " ");
		// System.out.println(criteria);
		// Using stanfordNLP to do sentence segment
		// Properties properties = new Properties();
		// properties.setProperty("annotators", "tokenize, ssplit, parse");
		// StanfordCoreNLP pipeline = new StanfordCoreNLP(properties);
		// List<CoreMap> sentences =
		// pipeline.process(criteria).get(CoreAnnotations.SentencesAnnotation.class);
		// StringBuffer sb = new StringBuffer();
		// for (CoreMap sentence : sentences) {
		// sb.append(sentence.toString());
		// sb.append("\n");
		// // System.out.println(sentence.toString());
		// }
		// return sb.toString();
		return "";
	}

	@RequestMapping("/execeli")
	@ResponseBody
	public String executeEliIE(HttpSession httpSession, HttpServletRequest request, String freetextinput)
			throws Exception {

		// WebUtil.Write2File("/Users/yuanchi/Documents/git/EliIE/Tempfile/TempFile.txt",
		// freetextinput);
		// Properties props = new Properties();
		// props.put("python.home","path to the Lib folder");
		// props.put("python.console.encoding", "UTF-8"); // Used to prevent:
		// console: Failed to install '':
		// java.nio.charset.UnsupportedCharsetException: cp0.
		// props.put("python.security.respectJavaAccessibility", "false");
		// //don't respect java accessibility, so that we can access protected
		// members on subclasses
		// props.put("python.import.site","false");
		//
		// Properties preprops = System.getProperties();
		// PythonInterpreter interpreter = new PythonInterpreter();
		// interpreter.execfile("/Users/yuanchi/Documents/git/EliIE/my_utils.py");
		return "1";
	}

	@RequestMapping("/getjson")
	public String getJSON(HttpSession httpSession, HttpServletRequest request, String freetextinput) throws Exception {
		String[] args = new String[] { "-props", "NER.prop", "-train", "" };
		// CRFClassifier.main(args);
		return "getjson";
	}

	@RequestMapping("/getsimiword")
	@ResponseBody
	public String getSimilarWord(HttpSession httpSession, HttpServletRequest request, String word1) throws Exception {
		word1 = word1.toLowerCase();
		StringBuffer sb = new StringBuffer();
		Word2vec vec = new Word2vec();
		vec.loadModel(WORD2VEC);
		System.out.println("One word analysis");
		Set<WordEntry> result = new TreeSet<WordEntry>();
		result = vec.distance(word1);
		Iterator iter = result.iterator();
		sb.append("word" + "\t\t" + "similarity" + "\n");
		while (iter.hasNext()) {
			WordEntry word = (WordEntry) iter.next();
			sb.append(word.name + "   " + word.score + "\n");
			System.out.println(word.name + " " + word.score);
		}
		return sb.toString();
	}

	@RequestMapping("/cal3word")
	@ResponseBody
	public String getSimilarThreeWord(HttpSession httpSession, HttpServletRequest request, String word1, String word2,
			String word3) throws Exception {
		word1 = word1.toLowerCase();
		word2 = word2.toLowerCase();
		word3 = word3.toLowerCase();
		Word2vec vec = new Word2vec();
		StringBuffer sb = new StringBuffer();
		sb.append("word" + "\t\t" + "similarity" + "\n");
		vec.loadModel(WORD2VEC);
		Set<WordEntry> result = new TreeSet<WordEntry>();
		Iterator iter = result.iterator();
		System.out.println("Three word analysis");
		result = vec.analogy(word3, word1, word2);
		iter = result.iterator();
		while (iter.hasNext()) {
			WordEntry word = (WordEntry) iter.next();
			sb.append(word.name + "\t\t" + word.score + "\n");
			System.out.println(word.name + " " + word.score);
		}
		return sb.toString();
	}

	@RequestMapping("/cal2word")
	@ResponseBody
	public String calDistance(HttpSession httpSession, HttpServletRequest request, String word1, String word2)
			throws Exception {
		word1 = word1.toLowerCase();
		word2 = word2.toLowerCase();
		Word2vec vec = new Word2vec();
		vec.loadModel(WORD2VEC);
		Set<WordEntry> result = new TreeSet<WordEntry>();
		Iterator iter = result.iterator();
		System.out.println("Two word analysis");
		System.out.println(word1);
		System.out.println(word2);
		float dis = vec.calculateDis(word1, word2);
		return String.valueOf(dis);
	}

}
