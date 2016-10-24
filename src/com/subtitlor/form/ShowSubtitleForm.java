package com.subtitlor.form;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
		HttpSession session=request.getSession();
		request.setAttribute("jspName",jspName);
		String button= request.getParameter("selectedButton");
		System.out.println(button);
		String[] buttonValues=button.split("_");
		String action=buttonValues[0];
		String languageTarget=buttonValues[1];
		int subtitlenfoId= Integer.parseInt(buttonValues[2]);
		System.out.println(languageTarget+" "+action+" "+subtitlenfoId);
		daoFactory=DaoFactory.getInstance();
		subtitleInfoDao=daoFactory.getSubtitleInfoDao();
		subtitleContentDao=daoFactory.getSubtitleContentDao();
		switch(action){
		case "dl":
			break;
		case "edit":
			EditSubtitleForm editSubtitleForm = new EditSubtitleForm();
			request=editSubtitleForm.genereateSubtitlesRequest(request,languageTarget,subtitlenfoId);
			jspName="/WEB-INF/subtitle/edit_subtitle.jsp";
			session.setAttribute("languageTarget", languageTarget);
			session.setAttribute("subtitlenfoId",subtitlenfoId);
			request.setAttribute("jspName",jspName);
			break;
		}
		return request;
	}





}
