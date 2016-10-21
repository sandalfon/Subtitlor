package com.subtitlor.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubtitleContent {
	private String tableName;
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName.toLowerCase();
	}
	public List<Integer> getIds() {
		return ids;
	}
	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}

	private List<Integer> ids;
	private Map<Integer, String> timeStarts=new HashMap<Integer, String>();
	private Map<Integer, String> timeStops=new HashMap<Integer, String>();
	private Map<Integer, String> frs=new HashMap<Integer, String>();
	private Map<Integer, String> ens=new HashMap<Integer, String>();
	private Map<Integer, String> als=new HashMap<Integer, String>();
	private Map<Integer, String> ess=new HashMap<Integer, String>();
	private Map<Integer, String> pts=new HashMap<Integer, String>();

	

	public Map<Integer, String> getTimeStarts() {
		return timeStarts;
	}
	public void setTimeStarts(Map<Integer, String> timeStarts) {
		this.timeStarts = timeStarts;
	}
	public Map<Integer, String> getTimeStops() {
		return timeStops;
	}
	public void setTimeStops(Map<Integer, String> timeStops) {
		this.timeStops = timeStops;
	}
	public Map<Integer, String> getFrs() {
		return frs;
	}
	public void setFrs(Map<Integer, String> frs) {
		this.frs = frs;
	}
	public Map<Integer, String> getEns() {
		return ens;
	}
	public void setEns(Map<Integer, String> ens) {
		this.ens = ens;
	}
	public Map<Integer, String> getAls() {
		return als;
	}
	public void setAls(Map<Integer, String> als) {
		this.als = als;
	}
	public Map<Integer, String> getEss() {
		return ess;
	}
	public void setEss(Map<Integer, String> ess) {
		this.ess = ess;
	}
	public Map<Integer, String> getPts() {
		return pts;
	}
	public void setPts(Map<Integer, String> pts) {
		this.pts = pts;
	}
	public void addSubtile(Subtitle suntitle, String tagLang){

	}
	
	public Subtitle getSubtile(String tagLang){
		Subtitle subtitle= new Subtitle();
		return subtitle;
	}
	
	private boolean checkBlockSubtitle( int index){
		return true;
	}
	
	public void makeSubtitleContentFromOriginal(SubtitleInfo subtitleInfo, Subtitle subtitle){
		ids=subtitle.getIds();
		timeStarts=subtitle.getTimeStart();
		timeStops=subtitle.getTimeStop();
		tableName=subtitleInfo.getTableName();
		switch(subtitleInfo.getVo().toLowerCase()){
		case "en":
			ens=subtitle.getLinesContent();
			break;
		case "fr":
			frs=subtitle.getLinesContent();
			break;
		case "al":
			als=subtitle.getLinesContent();
			break;
		case "es":
			ess=subtitle.getLinesContent();
			break;
		case "pt":
			pts=subtitle.getLinesContent();
			break;
		}
			 
	}
}
