package com.subtitlor.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Subtitle {
	private List<Integer> ids = new ArrayList<Integer>();
	private Map<Integer, String> timeStart = new HashMap<Integer, String>();
	private Map<Integer, String> timeStop = new HashMap<Integer, String>();
	private Map<Integer, String> linesContent = new HashMap<Integer, String>();

	

	public List<Integer> getIds() {
		return ids;
	}


	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}


	public Map<Integer, String> getTimeStart() {
		return timeStart;
	}


	public void setTimeStart(Map<Integer, String> timeStart) {
		this.timeStart = timeStart;
	}


	public Map<Integer, String> getTimeStop() {
		return timeStop;
	}


	public void setTimeStop(Map<Integer, String> timeStop) {
		this.timeStop = timeStop;
	}


	public Map<Integer, String> getLinesContent() {
		return linesContent;
	}


	public void setLinesContent(Map<Integer, String> linesContent) {
		this.linesContent = linesContent;
	}


	public String generateBlocTextFromId(int id){
		String result="";
		if(id>=ids.size())
			return result;
		result=result+ids.get(id)+'\n';
		result=result+timeStart.get(id)+" --> "+timeStop.get(id);
		result=result+linesContent.get(id);
		return result;
	}


	public String toString(){
		String result="";
		int maxSize=ids.size();
		for(int i=0;i<maxSize;i++){
			result=result+generateBlocTextFromId(i)+"\n\n";
		}
		result.substring(0,result.length()-1);
		return result;
	}

	
}
