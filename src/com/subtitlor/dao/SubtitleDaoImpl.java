package com.subtitlor.dao;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.subtitlor.beans.Subtitle;
import com.subtitlor.beans.SubtitleMultiLanguage;

public class SubtitleDaoImpl implements SubtitleDao {
	private DaoFactory daoFactory;

	SubtitleDaoImpl(DaoFactory daoFactory)  throws DaoException {
		this.daoFactory = daoFactory;
	}

	// faire les sous-titres à partir d'un fichier
	public Subtitle generateSubtitleFromfile( String fileName) throws DaoException{
		BufferedReader br=null;
		int stage=0;
		int index=-1;

		Subtitle subtitle =new Subtitle();
		List<Integer> ids = new ArrayList<Integer>();
		Map<Integer, String> timeStart = new HashMap<Integer, String>();
		Map<Integer, String> timeStop = new HashMap<Integer, String>();
		Map<Integer, String> linesContent = new HashMap<Integer, String>();

		try {
			br = new BufferedReader( new InputStreamReader(new FileInputStream(fileName), "UTF-8"));
			String line;
			String tmpLineContent="";
			while ((line = br.readLine()) != null) {
				line=line.trim();
				switch(stage){
				case 0:
					if(!line.trim().isEmpty()){
						index=Integer.parseInt(line);
						ids.add(index);
						stage=1;
					}
					break;
				case 1:
					String[] times=line.split(" --> ");
					timeStart.put(index, times[0]);
					timeStop.put(index, times[1]);
					stage=2;
					break;
				case 2:
					if(line.trim().isEmpty()){
						linesContent.put(index, tmpLineContent.substring(1, tmpLineContent.length()));
						tmpLineContent="";
						stage=0;
					}else{
						tmpLineContent=tmpLineContent+'\n'+line;
					}
					break;
				}
			}
			//ajout du dernier text des sous-titres si il n'y a pas de double saut de ligne à la fin du fichier
			if(tmpLineContent.length()>1){
				
				linesContent.put(index, tmpLineContent.substring(1, tmpLineContent.length()));
			}

			subtitle.setIds(ids);
			subtitle.setLinesContent(linesContent);
			subtitle.setTimeStart(timeStart);
			subtitle.setTimeStop(timeStop);


		} 
		catch (IOException e) {

			throw new DaoException("[Erreur SubtileDaoImpl  generateSubtitleFromfile] erreur read");
		}
		finally {
			try{
				br.close();
			}
			catch (IOException e) {
				throw new DaoException("[Erreur SubtileDaoImpl generateSubtitleFromfile] erreur close buffer");
			}
		}
		return(subtitle);


	}


	//récupérer les sous-titres d'une lange à partir des sous titre multilangues
	public Subtitle getSubtitleFromLanguageFromSubtitleMultiLanguage(SubtitleMultiLanguage subtitleMultiLanguage, String language) {
		Subtitle subtitle = new Subtitle();
		subtitle.setIds(subtitleMultiLanguage.getIds());
		subtitle.setTimeStart(subtitleMultiLanguage.getTimeStarts());
		subtitle.setTimeStop(subtitleMultiLanguage.getTimeStops());

		switch(language){
		case "en":
			subtitle.setLinesContent(subtitleMultiLanguage.getEns());
			break;
		case "fr":
			subtitle.setLinesContent(subtitleMultiLanguage.getFrs());
			break;
		case "al":
			subtitle.setLinesContent(subtitleMultiLanguage.getAls());
			break;
		case "es":
			subtitle.setLinesContent(subtitleMultiLanguage.getEss());
			break;
		case "pt":
			subtitle.setLinesContent(subtitleMultiLanguage.getPts());
			break;
		}
		return subtitle;
	}


}
