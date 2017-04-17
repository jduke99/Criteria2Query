package edu.columbia.dbmi.ohdsims.util;

public class CalculateUtil {
	public static float[] add(float[] f1, float[] f2){
		float[] re=f1.clone();
		for(int i=0;i<f1.length;i++){
			re[i]=f1[i]+f2[i];
		}
		return re;
	}
	
	public float calculateDis(float[] f1, float[] f2){
		float dist=0;
		for(int i=0;i<f1.length;i++){
			dist+=f1[i] * f2[i];
		}
		return dist;
	}

}
