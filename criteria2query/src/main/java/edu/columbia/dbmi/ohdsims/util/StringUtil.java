package edu.columbia.dbmi.ohdsims.util;

import java.util.LinkedHashMap;
import java.util.List;

import edu.columbia.dbmi.ohdsims.pojo.Entity;

public class StringUtil {
	public static String markResult(String sentence,List<Entity> entities){
		StringBuffer sb=new StringBuffer();
		if(entities.size()==0){
			sb.append("<del>"+sentence+"</del>");
			return sb.toString();
		}else{
		
		
		int sstart=0;
		String[] sen=sentence.split(" ");
		for (int i = 0; i < entities.size(); i++) {
//			System.out.println("<"+sstart+"-"+entities.get(i).getStartindex()+"+"+entities.get(i).getEntityname()+">");
//			System.out.println("se->"+sentence.substring(sstart,entities.get(i).getStartindex()));
			sb.append(subSentence(sen,sstart,entities.get(i).getStartindex()-1));
			sb.append("<mark data-entity=\""+entities.get(i).getDomain().toLowerCase()+"\">"+entities.get(i).getEntityname()+"</mark> ");
			sstart=entities.get(i).getStartindex()-1+getwordlength(entities.get(i).getEntityname());
			//System.out.println(sb.toString());
		}
		sb.append(subSentence(sen,sstart,sen.length-1));
		System.out.println(sb.toString());
		return sb.toString();
		}
	   
	}
	public static String subSentence(String[] arrstr,int start,int endindex){
		StringBuffer sb=new StringBuffer();
		for(int x=start;x<endindex;x++){
			sb.append(arrstr[x]+" ");
		}
		return sb.toString();
	}
	public static int getwordlength(String entity){
		return entity.trim().split(" ").length;
		
	}
}
