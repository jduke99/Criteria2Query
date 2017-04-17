package edu.columbia.dbmi.ohdsims.nlp;

import java.io.IOException;
import java.util.ArrayList;

import edu.columbia.dbmi.ohdsims.pojo.Criteria;
import edu.columbia.dbmi.ohdsims.pojo.Entity;
import edu.columbia.dbmi.ohdsims.util.CalculateUtil;
import edu.columbia.dbmi.ohdsims.util.Word2vec;

public class TestWord2vec {
	final static String WORD2VEC="/Users/yuanchi/Documents/dbmi/NERdata/word2vec-model/ct2vectorsD300.bin";
	public static void main(String[] args) throws IOException{
		//testWord2Vec();
		Criteria c=new Criteria();
		
		ArrayList<Entity> x=new ArrayList<Entity>();
		ArrayList<Object> al=new ArrayList<Object>();
		
		//c.setDependency(al);
		
	}

	public static void testWord2Vec() throws IOException {
		String sentence= "I love China";
		String sentence2 = "Some patients age older than three";
		String sentence3= "Someone has Alzheimer";
		float[] sf=calculateSenVec(sentence);
		float[] sf2=calculateSenVec(sentence2);
		float[] sf3=calculateSenVec(sentence3);
		System.out.println(caldis2f(sf2,sf3));
		System.out.println(caldis2f(sf,sf2));
		System.out.println(caldis2f(sf,sf3));
	}
	
	public static float caldis2f(float[] sf,float[] sf2){
		float dist=0;
		
		for(int i=0;i<sf.length;i++){
			dist+=sf[i] * sf2[i];
		}
		
		return dist;
	}
	public static float[] calculateSenVec(String sentence) throws IOException {
		Word2vec vec = new Word2vec();
		vec.loadModel(WORD2VEC);
		
		//create an empty float array to sum all the vectors of words
		float[] totalf=new float[300];
		
		String[] attstr=sentence.split(" ");
		for(int j=0;j<attstr.length;j++){
			//System.out.println(attstr[j]);
			float[] f=vec.getWordVector(attstr[j]);	
			if(f==null){
				continue;
			}
			totalf=CalculateUtil.add(totalf, f);		
		}
		for(int k=0;k<totalf.length;k++){
			totalf[k]=totalf[k]/attstr.length;
		}
		return totalf;
	}
	
//	public static String getUMLS()
	
}
