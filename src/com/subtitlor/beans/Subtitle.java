package com.subtitlor.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// classe  de gestion simplifié d'un sous-titre
public class Subtitle {
	//Identifiants du format str
	private List<Integer> ids = new ArrayList<Integer>();
	//temps début au format str
	private Map<Integer, String> timeStart = new HashMap<Integer, String>();
	//temps fin au format str
	private Map<Integer, String> timeStop = new HashMap<Integer, String>();
	//lignes de suous titres au format str
	private Map<Integer, String> linesContent = new HashMap<Integer, String>();

	//Getters et setters
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


	//génération d'un bloc sous titre au froamt str (id ; temps; text)
	public String generateBlocTextFromId(int id){
		String result="";
		if(id>=ids.size())
			return result;
		int idRef=ids.get(id);
		result=result+idRef+'\n';
		result=result+timeStart.get(idRef)+" --> "+timeStop.get(idRef)+"\n";
		result=result+linesContent.get(idRef);
		return result;
	}


	//génération de l'ensemble de l'objet sous-titre  en text au format str
	public String toString(){
		String result="";
		int maxSize=ids.size();
		for(int i=0;i<maxSize;i++){
			result=result+generateBlocTextFromId(i)+"\n\n";
		}
		result.substring(0,result.length());
		return result;
	}


}
