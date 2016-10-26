package com.subtitlor.form;

import java.io.IOException;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.subtitlor.beans.BeanException;
import com.subtitlor.beans.Subtitle;
import com.subtitlor.beans.SubtitleMultiLanguage;
import com.subtitlor.beans.SubtitleInfo;
import com.subtitlor.dao.DaoException;
import com.subtitlor.dao.DaoFactory;
import com.subtitlor.dao.SubtitleMultiLanguageDao;
import com.subtitlor.dao.SubtitleDao;
import com.subtitlor.dao.SubtitleInfoDao;
import com.subtitlor.utilities.FileHandler;


//Gestion du formulaire sur la visualisation des sous-titres
public class ManageSubtitleForm {

	private DaoFactory daoFactory;
	private SubtitleInfoDao subtitleInfoDao;
	private SubtitleDao subtitleDao;
	private SubtitleMultiLanguageDao subtitleMultiLanguageDao;

	//Gestion des actions des boutons 
	public HttpServletRequest dispacher(HttpServletRequest request,HttpServletResponse response) throws DaoException, BeanException, IOException{
		daoFactory=DaoFactory.getInstance();
		subtitleInfoDao=daoFactory.getSubtitleInfoDao();
		subtitleMultiLanguageDao=daoFactory.getSubtitleContentDao();
		subtitleDao=daoFactory.getSubtitleDao();
		
		String jspName="/WEB-INF/subtitle/show_subtitle.jsp";
		HttpSession session=request.getSession();
		
		request.setAttribute("jspName",jspName);
		
		String button= request.getParameter("selectedButton");
		String[] buttonValues=button.split("_");
		String action=buttonValues[0];
		String languageTarget=buttonValues[1];
		int subtitleInfoId= Integer.parseInt(buttonValues[2]);
		
		//case dl => bouton télécharger pressé
		//case edit => bouton éditer tpressé
		switch(action){
		case "dl":
			jspName="/WEB-INF/subtitle/save_subtitle.jsp";
			subtitleInfoDao = daoFactory.getSubtitleInfoDao();
			SubtitleInfo subtitleInfo = subtitleInfoDao.getSubtitleInfoFromId(subtitleInfoId);
			SubtitleMultiLanguage subtitleMultiLanguage = subtitleMultiLanguageDao.getSubtitleMultiLanguageFromTable(subtitleInfo.getTableName());
			Subtitle subtitle = subtitleDao.getSubtitleFromLanguageFromSubtitleMultiLanguage(subtitleMultiLanguage, languageTarget);
			String fileName= subtitleInfoDao.getNameFromLanguage(subtitleInfo, languageTarget);
			
			response= FileHandler.exportFile(request, response, subtitle, fileName);
			request.setAttribute("jspName",jspName);
			session.setAttribute("case","dl");
			break;
		case "edit":
			EditSubtitleForm editSubtitleForm = new EditSubtitleForm();
			request=editSubtitleForm.genereateSubtitlesRequest(request,languageTarget,subtitleInfoId);
			jspName="/WEB-INF/subtitle/edit_subtitle.jsp";
			
			session.setAttribute("languageTarget", languageTarget);
			session.setAttribute("subtitlenfoId",subtitleInfoId);
			request.setAttribute("jspName",jspName);
			session.setAttribute("case","edit");
			break;
		}
		return request;
	}
}
