package com.subtitlor.form;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.subtitlor.beans.BeanException;
import com.subtitlor.beans.SubtitleContent;
import com.subtitlor.beans.SubtitleInfo;
import com.subtitlor.dao.DaoException;
import com.subtitlor.dao.DaoFactory;
import com.subtitlor.dao.SubtitleContentDao;
import com.subtitlor.dao.SubtitleInfoDao;

public class ShowSubtitleForm {

	private DaoFactory daoFactory;
	private SubtitleInfoDao subtitleInfoDao;
	private SubtitleContentDao subtitleContentDao;

	public HttpServletRequest dispacher(HttpServletRequest request) throws DaoException, BeanException{
		String jspName="/WEB-INF/subtitle/show_subtitle.jsp";
		request.setAttribute("jspName",jspName);
		String button= request.getParameter("selectedButton");
		System.out.println(button);
		String[] buttonValues=button.split("_");
		String action=buttonValues[0];
		String languageTarget=buttonValues[1];
		String languageRef;
		int subtitlenfoId= Integer.parseInt(buttonValues[2]);
		System.out.println(languageTarget+" "+action+" "+subtitlenfoId);
		switch(action){
		case "dl":
			break;
		case "edit":
			jspName="/WEB-INF/subtitle/edit_subtitle.jsp";
			daoFactory=DaoFactory.getInstance();
			subtitleInfoDao=daoFactory.getSubtitleInfoDao();
			subtitleContentDao=daoFactory.getSubtitleContentDao();
			SubtitleInfo subtitleInfo =subtitleInfoDao.getSubtitleInfoFromId(subtitlenfoId);
			SubtitleContent subtitleContent =subtitleContentDao.getSubtitleContentFromTable( subtitleInfo.getTableName());
			languageRef=subtitleInfo.getVo();
			request.setAttribute("videoName", subtitleInfo.getNameVideo());
			request.setAttribute("nameRef", subtitleInfoDao.getNameFromLanguage(subtitleInfo, languageRef));
			request.setAttribute("nameTarget", subtitleInfoDao.getNameFromLanguage(subtitleInfo, languageTarget));
			request.setAttribute("subtitleRef", subtitleContentDao.getSubtitleFromLanguage(subtitleContent, languageRef));
			request.setAttribute("subtitleTarget", subtitleContentDao.getSubtitleFromLanguage(subtitleContent, languageTarget));
			request.setAttribute("ids", subtitleContent.getIds());
			request.setAttribute("jspName",jspName);
			break;
		}
		return request;
	}
	
	

	 
	
}
