package com.subtitlor.form;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import com.subtitlor.beans.BeanException;
import com.subtitlor.beans.Subtitle;
import com.subtitlor.beans.SubtitleMultiLanguage;
import com.subtitlor.beans.SubtitleInfo;
import com.subtitlor.dao.DaoException;
import com.subtitlor.dao.DaoFactory;
import com.subtitlor.dao.SubtitleMultiLanguageDao;
import com.subtitlor.dao.SubtitleInfoDao;
import com.subtitlor.dao.SubtitleDao;

//
public class AddOriginalSubtitleForm {
	
	private SubtitleInfo subtitleInfo;
	private SubtitleMultiLanguage subtitleMultiLanguage;
	private Subtitle subtitle;
	private SubtitleMultiLanguageDao subtitleMultiLanguageDao;
	private SubtitleDao subtitleDao;
	private SubtitleInfoDao subtitleInfoDao;
	private DaoFactory daoFactory;

	//Gestion du formulaire sur l'ajout d'un nouveau fichier de sous-titre
	public void makeNewSubtitlesFromFile(HttpServletRequest request) throws FormException, BeanException, IOException, ServletException, DaoException{
		
		daoFactory = DaoFactory.getInstance();
		subtitleInfoDao = daoFactory.getSubtitleInfoDao();
		subtitleDao = daoFactory.getSubtitleDao();
		subtitleMultiLanguageDao = daoFactory.getSubtitleContentDao();
		subtitleInfo= new SubtitleInfo();
		
		//récupération des valeurs
		String name = request.getParameter("name");
		String nameVideo = request.getParameter("nameVideo");
		String language = request.getParameter("language").toLowerCase();
		
		subtitleInfo.setVo(language.toLowerCase());
		
		//gestion des langues pour l'état des sous titres 
		switch(language){
		case "en":
			if(request.getParameter("finish").equals("yes")){
				subtitleInfo.setFinishedEn(true);
			}
			else{
				subtitleInfo.setFinishedEn(false);
			}
			subtitleInfo.setNameEn(name);
			break;
		case "fr":
			if(request.getParameter("finish").equals("yes")){
				subtitleInfo.setFinishedFr(true);
			}
			else{
				subtitleInfo.setFinishedFr(false);
			}
			subtitleInfo.setNameFr(name);
			break;
		case "al":
			if(request.getParameter("finish").equals("yes")){
				subtitleInfo.setFinishedAl(true);
			}
			else{
				subtitleInfo.setFinishedAl(false);
			}
			subtitleInfo.setNameAl(name);
			break;
		case "es":
			if(request.getParameter("finish").equals("yes")){
				subtitleInfo.setFinishedEs(true);
			}
			else{
				subtitleInfo.setFinishedEs(false);
			}
			subtitleInfo.setNameEs(name);
			break;
		case "pt":
			if(request.getParameter("finish").equals("yes")){
				subtitleInfo.setFinishedPt(true);
			}
			else{
				subtitleInfo.setFinishedPt(false);
			}
			subtitleInfo.setNamePt(name);
			break;
		}
		
		//gestion du nom 
		if( nameVideo == null){
			throw new FormException("Erreur nom de la video non défini");
		}else{
			for(String videoNameRef : subtitleInfoDao.videoNameLister()){
				if(videoNameRef.trim().contains(nameVideo)){
					throw new FormException("Erreur nom de la video déjà présent");
				}
			}
			subtitleInfo.setNameVideo(nameVideo);
		}
		//génération du nom de la table pour l'enregistremetn des sous-titres
		String tableName = subtitleInfoDao.generateTableName(nameVideo)+"_"+language;

		subtitleInfo.setTableName(tableName);
		subtitleMultiLanguageDao.createTable(subtitleInfo);
		
		// Gestion du fichier de sous-titre
		Part part = request.getPart("file");
		String filePathAndName=subtitleInfoDao.managePart(part);
		
		//Génération des objets liés aux sous-titres
		subtitle= subtitleDao.generateSubtitleFromfile(filePathAndName);
		subtitleInfoDao.persistSubtitleInfo(subtitleInfo);
		subtitleMultiLanguage = new SubtitleMultiLanguage();
		subtitleMultiLanguage.makeSubtitleContentFromOriginal(subtitleInfo, subtitle);
		subtitleMultiLanguageDao.persistSubtitleMultiLanguage(subtitleMultiLanguage);
	}


}
