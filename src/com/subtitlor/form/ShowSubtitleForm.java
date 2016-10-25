package com.subtitlor.form;

import java.io.IOException;
import java.util.List;

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
import com.subtitlor.utilities.ExportSubtitles;

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
		int subtitleInfoId= Integer.parseInt(buttonValues[2]);
		System.out.println(languageTarget+" "+action+" "+subtitleInfoId);
		daoFactory=DaoFactory.getInstance();
		subtitleInfoDao=daoFactory.getSubtitleInfoDao();
		subtitleContentDao=daoFactory.getSubtitleContentDao();
		switch(action){
		case "dl":
			ExportSubtitles exportSubtitles=new ExportSubtitles();
			jspName="/WEB-INF/subtitle/show_subtitle.jsp";
			SubtitleInfoDao subtitleInfoDao = daoFactory.getSubtitleInfoDao();
			List<SubtitleInfo> subtitleInfos =subtitleInfoDao.lister();
			request.setAttribute("subtitleInfos",subtitleInfos);
//			request=exportSubtitles.prepareExport(request,languageTarget,subtitleInfoId);
//			session.setAttribute("languageTarget", languageTarget);
//			session.setAttribute("subtitlenfoId",subtitleInfoId);
			request.setAttribute("jspName",jspName);
			break;
		case "edit":
			EditSubtitleForm editSubtitleForm = new EditSubtitleForm();
			request=editSubtitleForm.genereateSubtitlesRequest(request,languageTarget,subtitleInfoId);
			jspName="/WEB-INF/subtitle/edit_subtitle.jsp";
			session.setAttribute("languageTarget", languageTarget);
			session.setAttribute("subtitlenfoId",subtitleInfoId);
			request.setAttribute("jspName",jspName);
			break;
		}
		return request;
	}





}
