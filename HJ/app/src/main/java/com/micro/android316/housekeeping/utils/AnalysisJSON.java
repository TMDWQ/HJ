package com.micro.android316.housekeeping.utils;

import android.util.Log;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * 工具类说明
 *   作者：张文
 *   作用：解析JSON格式的数据
 *   使用方法：静态调用function进行解析，返回一个Object。然后你可以通过关键字进行解析
 *        Object:可转化为hashMap和hashMap[]
 *           如[{},{},{}]：这种形式因该转化为hashMap[]
 *             {}:这种形式因该转化为hashMap
 *
 *             注意:本类这适合观赏，并无实际意义，原因，解析英文的时候倒是没什么，解析中文的时候，编码问题会出错
 *
 *
 * */

public class AnalysisJSON {
	private String json;
	private AnalysisJSON(String json){
		this.json=json;
	}



	public static Object function(String json){//执行解析
		AnalysisJSON a=new AnalysisJSON(json);
		return a.analisis();
	}

	private Object analisis(){
		char c=subHeadAndTail();
		if(c=='}'){
			return analisisClass(json);
		}else if(c==']'){
			return	analisisArray(json);
		}
		return null;
	}

	private char subHeadAndTail(){//去掉头尾的字符，并换回头尾字符以确定以什么方式继续进行解析
		String s="";
		char c='\0';
		for(int i=0;i<json.length();i++){
			char cc=json.charAt(i);
			if(i==0 || i==json.length()-1){
				//System.out.println(cc);
				c =cc;
			}else{
				s+=cc;
			}
		}
		json=s;
		return c;
	}



	private String subchars(String js,char ...cs){//字符过滤
		StringBuffer s=new StringBuffer();
		for(int i=0;i<js.length();i++){
			boolean b=true;
			char c=js.charAt(i);
			for(int j=0;j<cs.length;j++){
				if(cs[j]==c){
					b=false;
					j=cs.length;
				}
			}
			if(b){
				s.append(c);
			}
		}

		return s.toString();

	}

	private HashMap<String,Object>[] analisisArray(String s){//解析数组
		List<String> ss=division(s);
		if(ss.size()==0 || ss==null){

			return null;
		}
		Log.i("hhh","ss="+s);
		Log.i("hhh","ss.size="+ss.size());

		HashMap<String,Object> hash[]=new HashMap[ss.size()];
		int j=0;
		for(String i:ss){
			if(i.charAt(0)=='{'){
				i=subHeadAndTail(i,'{','}');
				hash[j++]=analisisClass(i);
			}
		}

		return hash;
	}


	private HashMap<String,Object> analisisClass(String s){//解析类
		HashMap<String,Object> hash=new HashMap<>();
		List<String> list=division(s);
		if(list.size()==0){
			return null;
		}
		for(String i:list){
			String[] sss=i.split(":",2);
			sss[0]=subHeadAndTail(sss[0],'"');
			sss[1]=subHeadAndTail(sss[1],'"');
			if(sss[1].charAt(0)=='{'){
				sss[1]=subHeadAndTail(sss[1],'{','}');
				hash.put(sss[0],analisisClass(sss[1]));
			}else if(sss[1].charAt(0)=='['){
				sss[1]=subHeadAndTail(sss[1],'[',']');
				hash.put(sss[0],analisisArray(sss[1]));
			}else{
				hash.put(sss[0],sss[1]);
			}
		}




		return hash;
	}




	private String subHeadAndTail(String json){//去掉头尾的所有字符
		String s="";
		for(int i=0;i<json.length();i++){
			char cc=json.charAt(i);
			if(i!=0 && i!=json.length()-1){
				s+=cc;
			}
		}
		return s;
	}

	private String subHeadAndTail(String json,char c){//去掉头尾的指定字符
		String s="";
		for(int i=0;i<json.length();i++){
			char cc=json.charAt(i);
			if(cc==c && (i==0 || i==json.length()-1)){

			}else{
				s+=cc;
			}
		}
		return s;
	}

	private String subHeadAndTail(String json,char c,char ccc){//去掉头尾的指定字符
		String s="";
		for(int i=0;i<json.length();i++){
			char cc=json.charAt(i);
			if((cc==c && i==0) || (cc==ccc && i==json.length()-1)){

			}else{
				s+=cc;
			}
		}
		return s;
	}


	public List<String> division(String s){//分割json格式的数据
		List<String> list=new ArrayList<>();
		String mid="";
		int j=0;
		for(int i=0;i<s.length();i++){
			char c=s.charAt(i);
			if(c=='{'){
				mid+=c;
				j++;
			}else if(c=='}'){
				mid+=c;
				j--;
			}else if(c==','&&j==0){
				list.add(mid);
				mid="";
			}else{
				mid+=c;
			}
		}
		if(mid.length()!=0){
			list.add(mid);
		}
		return list;
	}




}
