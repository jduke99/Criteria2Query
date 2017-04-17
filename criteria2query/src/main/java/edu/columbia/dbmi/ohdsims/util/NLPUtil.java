package edu.columbia.dbmi.ohdsims.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

import edu.columbia.dbmi.ohdsims.pojo.Criteria;
import edu.columbia.dbmi.ohdsims.pojo.Entity;
import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.Label;
import edu.stanford.nlp.ling.SentenceUtils;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.parser.nndep.DependencyParser;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.trees.Dependency;
import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.trees.GrammaticalStructureFactory;
import edu.stanford.nlp.trees.PennTreebankLanguagePack;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreebankLanguagePack;
import edu.stanford.nlp.trees.TypedDependency;
import edu.stanford.nlp.util.PropertiesUtils;
import edu.stanford.nlp.util.CoreMap;

public class NLPUtil {
	//public static final String path = "/Users/yuanchi/Documents/dbmi/NERdata/stanford-corenlp-full-2016-10-31/ner-model.ser.gz";
	//public static final String path = "/Users/yuanchi/Dropbox/ner-model.ser.gz";
	//public static final String path = "/Users/yuanchi/Dropbox/whole-ner-model.ser.gz";
	//public static final String grammars = "/Users/yuanchi/Documents/dbmi/NLPmodel/englishPCFG.ser.gz";
	public static final String grammars="edu/columbia/dbmi/ohdsims/model/englishPCFG.ser.gz";
	public static final String path="edu/columbia/dbmi/ohdsims/model/ner-model.ser.gz";
	
	
	public static void main(String[] args) {
		String input = "Normal B12, rapid plasma reagin ( RPR ) and Thyroid Function Tests or without any clinically significant abnormalities that would be expected to interfere with the study.";
		System.out.println("=========Part1: Sentence Segment===========");
		List<String> sentencelist = preprocessDoc(input);
		System.out.println("=========End Part1==================");
	
		for (int i = 0; i < sentencelist.size(); i++) {
			System.out.println(sentencelist.get(i));
			Tree tree=parseSentence(sentencelist.get(i));
			ArrayList<TaggedWord> twlist=tagWords(tree);
			ArrayList<Entity> nerlist=ner4Sentence(sentencelist.get(i),twlist);
			System.out.println("--->ner--start");
			for(int j=0;j<nerlist.size();j++){
				System.out.println(nerlist.get(j).getEntityname()+"-->"+nerlist.get(j).getDomain()+" :["+nerlist.get(j).getStartindex()+","+nerlist.get(j).getEndindex()+"]");
			}
			System.out.println("--->ner-->end");
			//optimizeNerResult(sentencelist.get(i),nerlist,twlist);
			Collection<TypedDependency> coltd=findDependency( tree,nerlist);
			//
		}
		NLPUtil np=new NLPUtil();
		
		//ArrayList<String[]> le = ner4Sentence(input);		

	}
	public static List<Criteria> startParse(String cr){
		String[] incarr=cr.split("\n");
		List<Criteria> cra=new ArrayList<Criteria>();
		for(int j=0;j<incarr.length;j++){
			Criteria ca=new Criteria();
			ca.setSentence(incarr[j]);
			Tree tree=parseSentence(incarr[j]);
			ArrayList<TaggedWord> twlist=tagWords(tree);
			ArrayList<Entity> nerlist=ner4Sentence(incarr[j],twlist);
			ca.setEntities(nerlist);
			cra.add(ca);
		}
		return cra;
	}
	
	
	public static void getRelations(Collection<TypedDependency> coltd,ArrayList<String[]> nerlist){
		
	}

	public static ArrayList<Entity> ner4Sentence(String str,ArrayList<TaggedWord> twlist) {
		ArrayList<String[]> listmap = new ArrayList<String[]>();
		ArrayList<Entity> resultner = new ArrayList<Entity>();
		AbstractSequenceClassifier<CoreLabel> ner;
		// input your model path
		String serializedClassifier = path;
		ner = CRFClassifier.getClassifierNoExceptions(serializedClassifier);
		String result = ner.classifyWithInlineXML(str);
		System.out.println("nerresult="+result);
		String pattern = "<(B|I)-(Condition|Qualifier|Measurement|Observation|Drug|Procedure_Device|Temporal_measurement)>([\\s\\S]*?)</(B|I)-(Condition|Qualifier|Measurement|Observation|Drug|Procedure_Device|Temporal_measurement)>";

		String s = result;
		Pattern pat = Pattern.compile(pattern);
		Matcher mat = pat.matcher(s);
		int count = 0;

		while (mat.find()) {
			count = count + 1;
			String[] arrs = new String[3];
			arrs[0] = mat.group(3);
			arrs[1] = mat.group(1) + "-" + mat.group(2);
			listmap.add(arrs);
		}
		StringBuffer sb = new StringBuffer();

		String lastentity = new String();

		for (int x = 0; x < listmap.size(); x++) {
			if (listmap.get(x)[1].startsWith("B-")) {
				Entity entity=new Entity();
				entity.setEntityname(sb.toString());
				entity.setDomain(lastentity);
				resultner.add(entity);
				sb.setLength(0);
				sb.append(listmap.get(x)[0]);
				lastentity = listmap.get(x)[1].substring(2);
			} else if (listmap.get(x)[1].startsWith("I-") && (listmap.get(x)[1].substring(2).equals(lastentity))) {
				if (str.contains(sb.toString() + " " + listmap.get(x)[0])) {
					sb.append(" " + listmap.get(x)[0]);
				} else if (str.contains(sb.toString() + listmap.get(x)[0])) {
					sb.append(listmap.get(x)[0]);
				}

				lastentity = lastentity = listmap.get(x)[1].substring(2);
			}
			if (x == (listmap.size() - 1)) {
				
				Entity entity=new Entity();
				entity.setEntityname(sb.toString());
				entity.setDomain(lastentity);
				resultner.add(entity);
			}

		}
		if (resultner.size() >= 1) {
			resultner.remove(0);
		}
		ArrayList<Entity> listentities=new ArrayList<Entity>();
		int y=0;
		int startindex,endindex;
		//add startindex and endindex to entity
		//System.out.println("twlist="+twlist.size());
		for(int x=0;x<twlist.size();x++){
			if(y<resultner.size()&&resultner.get(y).getEntityname().contains(twlist.get(x).word())){
				
				if(isMatch(resultner.get(y).getEntityname(),twlist,x)){
					
					startindex=x;
					Entity newentity=new Entity();
					newentity.setStartindex(startindex+1);
					newentity.setEndindex(startindex+resultner.get(y).getEntityname().split(" ").length);
					newentity.setEntityname(resultner.get(y).getEntityname());
					newentity.setDomain(resultner.get(y).getDomain());
					listentities.add(newentity);
					y++;
					
				}
			}
		}
		return listentities;
	}
	public static boolean isMatch(String str,ArrayList<TaggedWord> twlist,int start){
		int k=start;
		boolean flag=true;
		
		String[] att=str.split(" ");
		for(int i=0;i<att.length;i++){
			if(att[i].equals(twlist.get(k).word())==false){
				
				flag=false;
				break;
			}else{
				k++;
			}
		}
		return flag;
		
	}

	public static void printNERresult(ArrayList<String[]> list) {
		for (int j = 0; j < list.size(); j++) {
			System.out.println(list.get(j)[0] + "\t" + list.get(j)[1]);
		}
	}

	/**
	 * parseSentence Author:chi Date:2017-3-22
	 * 
	 */
	public static Tree parseSentence(String input) {
		LexicalizedParser lp = LexicalizedParser.loadModel(grammars);
		Tree tree = lp.parse(input);
		return tree;
	}

	/**
	 * tagWords Author:chi Date:2017-3-22
	 * 
	 */
	public static ArrayList<TaggedWord> tagWords(Tree t) {
		ArrayList<TaggedWord> twlist = t.taggedYield();
//		for (int x = 0; x < twlist.size(); x++) {
//			TaggedWord tw = twlist.get(x);
//			System.out.println("["+(x+1)+"]:"+tw.tag() + "--" + tw.word() + " (" + tw.value() + ")" + "--" + tw.beginPosition() + "--"
//					+ tw.endPosition());
//		}
		return twlist;
	}

	/**
	 * Word Dependency Author:chi Date:2017-3-22
	 * 
	 */
	public static Collection<TypedDependency> findDependency(Tree t,ArrayList<Entity> entities) {
		TreebankLanguagePack tlp = new PennTreebankLanguagePack();
		// tlp.setGenerateOriginalDependencies(true); Standford Dependency
		GrammaticalStructureFactory gsf = tlp.grammaticalStructureFactory();
		GrammaticalStructure gs = gsf.newGrammaticalStructure(t);

		Collection<TypedDependency> tdl = gs.typedDependenciesCCprocessed();
		
		int countforitem=0;
		int source=0;
		int target=0;
		for (TypedDependency item : tdl) {
			System.out.println(item);
		}
		for (TypedDependency item : tdl) {
			countforitem=0;
			for(int i=0;i<entities.size();i++){
				if(item.dep().index()>=entities.get(i).getStartindex()&&item.dep().index()<=entities.get(i).getEndindex()){
					countforitem++;
					source=i;
				}
				if(item.gov().index()>=entities.get(i).getStartindex()&&item.gov().index()<=entities.get(i).getEndindex()){
					countforitem++;
					target=i;
				}
			}
			if(countforitem==2&&source!=target){
				//System.out.println(source+"-"+target);
				System.out.print(item.reln().getShortName()+"\t"+item.reln().getSpecific()+"\t");
				System.out.println(entities.get(source).getEntityname()+"  |  "+entities.get(target).getEntityname());
			}
			//System.out.println(item);
			
		}
		return tdl;

	}

	/**
	 * Document Preprocess Author:chi Date:2017-3-22
	 */

	public static List<String> preprocessDoc(String input) {

		String paragraph = input;
		Reader reader = new StringReader(paragraph);
		DocumentPreprocessor dp = new DocumentPreprocessor(reader);
		List<String> sentenceList = new ArrayList<String>();

		for (List<HasWord> sentence : dp) {
			// SentenceUtils not Sentence
			String sentenceString = SentenceUtils.listToString(sentence);
			sentenceList.add(sentenceString);
		}
		return sentenceList;
	}
	
	/**
	 * optimizeNerResult Author:chi Date:2017-4-10
	 * description: optimize NER result based on parsing result
	 */
	public static ArrayList<String[]> optimizeNerResult(String originalscentence,ArrayList<Entity> nerlist,ArrayList<TaggedWord> twlist){
		for(int i=0;i<nerlist.size();i++){
			System.out.println("Name Entity:"+nerlist.get(i).getEntityname()+" class:"+nerlist.get(i).getDomain());
			System.out.println("index->"+originalscentence.indexOf(nerlist.get(i).getEntityname()));
			int lastindex=originalscentence.indexOf(nerlist.get(i).getEntityname())+nerlist.get(i).getEntityname().length();
			String substr=originalscentence.substring(0, lastindex);
			System.out.println(substr);
			int x=substr.trim().lastIndexOf(" ");
			System.out.println("entire="+substr.length()+";"+"last space index->"+x);
			System.out.println("last word="+substr.substring(x+1));
			for(int j=0;j<twlist.size();j++){
				if((x+1)==twlist.get(j).beginPosition()){
				   if((nerlist.get(i).getDomain().equals("Qualifier")==false)&&(nerlist.get(i).getDomain().equals("Measurement")==false)&&(twlist.get(j).tag().equals("NN")==false)){
					   System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
				   }
				}
			}
			//x+1: is the last word start index;
			
		}
		return null;
	}
	
	

}
