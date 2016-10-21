package com.subtitlor.form;

import java.io.FileOutputStream;
import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import com.subtitlor.beans.BeanException;
import com.subtitlor.beans.Subtitle;
import com.subtitlor.beans.SubtitleContent;
import com.subtitlor.beans.SubtitleInfo;
import com.subtitlor.dao.DaoException;
import com.subtitlor.dao.DaoFactory;
import com.subtitlor.dao.SubtitleContentDao;
import com.subtitlor.dao.SubtitleInfoDao;
import com.subtitlor.dao.SubtitleDao;
public class AddOriginalSubtitleForm {
	private SubtitleInfo subtitleInfo;
	private SubtitleContent subtitleContent;




	public void initAddOriginalArguments(HttpServletRequest request) throws FormException, BeanException, IOException, ServletException, DaoException{
		DaoFactory daoFactory = DaoFactory.getInstance();
		SubtitleInfoDao subtitleInfoDao = daoFactory.getSubtitleInfoDao();
		SubtitleDao subtitleDao = daoFactory.getSubtitleDao();
		SubtitleContentDao subtitleContentDao = daoFactory.getSubtitleContentDao();
		subtitleInfo= new SubtitleInfo();
		String name = request.getParameter("name");
		subtitleInfoDao.videoNameLister();
		String nameVideo = request.getParameter("nameVideo");
		String language = request.getParameter("language");
		subtitleInfo.setVo(language);
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
			subtitleInfo.setNameEn(name);
			break;
		}
		
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

		String tableName = subtitleInfoDao.generateTableName(nameVideo)+"_"+language;
		subtitleInfo.setTableName(tableName);
		
		
		subtitleContentDao.createTable(subtitleInfo);
		subtitleInfoDao.add(subtitleInfo);
		// On récupère le champ du fichier
		Part part = request.getPart("file");
		String filePathAndName=subtitleInfoDao.managePart(part);
		Subtitle subtitle= subtitleDao.generateSubtitleFromfile(filePathAndName);
		System.out.println("start sub");
		System.out.println(subtitle.getTimeStart().toString());
		subtitleContent = new SubtitleContent();
		subtitleContent.makeSubtitleContentFromOriginal(subtitleInfo, subtitle);
		subtitleContentDao.persistSubtitleContent(subtitleContent);

	}
			
		





	

	public SubtitleInfo getSubtitleInfo(){

		return subtitleInfo;
	}

	



}
