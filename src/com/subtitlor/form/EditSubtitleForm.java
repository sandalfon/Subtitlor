package com.subtitlor.form;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.subtitlor.beans.BeanException;
import com.subtitlor.beans.SubtitleContent;
import com.subtitlor.beans.SubtitleInfo;
import com.subtitlor.dao.DaoException;
import com.subtitlor.dao.DaoFactory;
import com.subtitlor.dao.SubtitleContentDao;
import com.subtitlor.dao.SubtitleInfoDao;

public class EditSubtitleForm {
	private DaoFactory daoFactory;
	private SubtitleInfoDao subtitleInfoDao;
	private SubtitleContentDao subtitleContentDao;

	public void updateSubtitles(HttpServletRequest request) throws DaoException, BeanException{
		daoFactory=DaoFactory.getInstance();
		subtitleInfoDao=daoFactory.getSubtitleInfoDao();
		subtitleContentDao=daoFactory.getSubtitleContentDao();
		HttpSession session=request.getSession();
		int subtitlenfoId= (int) session.getAttribute("subtitlenfoId");
		String languageTarget=(String) session.getAttribute("languageTarget");
		updateName(subtitlenfoId,(String)session.getAttribute("nameTarget"),languageTarget);
	}

	public void updateName(int id , String name, String language) throws DaoException{
		if(!name.equals("non défini")){
			String refName=subtitleInfoDao.getNameFromIdLanguage(id,language);
			if(!name.equals(refName)){
				subtitleInfoDao.updateNameFromIdLanguage(id,name,language);
			}
		}
	}

	public void  updateLines(){

	}
	public void  updateFinish(){

	}

	public HttpServletRequest genereateSubtitlesRequest(HttpServletRequest request,String languageTarget, int subtitlenfoId) throws DaoException, BeanException {
		daoFactory=DaoFactory.getInstance();
		subtitleInfoDao=daoFactory.getSubtitleInfoDao();
		subtitleContentDao=daoFactory.getSubtitleContentDao();
		SubtitleInfo subtitleInfo =subtitleInfoDao.getSubtitleInfoFromId(subtitlenfoId);
		SubtitleContent subtitleContent =subtitleContentDao.getSubtitleContentFromTable( subtitleInfo.getTableName());
		String languageRef=subtitleInfo.getVo();
		String nameTarget=subtitleInfoDao.getNameFromLanguage(subtitleInfo, languageTarget);
		request.setAttribute("videoName", subtitleInfo.getNameVideo());
		request.setAttribute("tableName", subtitleInfo.getTableName());
		request.setAttribute("nameRef", subtitleInfoDao.getNameFromLanguage(subtitleInfo, languageRef));
		request.setAttribute("nameTarget",nameTarget );
		request.setAttribute("languageTarget", languageTarget);
		request.setAttribute("subtitleRef", subtitleContentDao.getSubtitleFromLanguage(subtitleContent, languageRef));
		request.setAttribute("subtitleTarget", subtitleContentDao.getSubtitleFromLanguage(subtitleContent, languageTarget));
		request.setAttribute("ids", subtitleContent.getIds());
		request.setAttribute("subtitlenfoId",subtitlenfoId);
		request.getSession().setAttribute("nameTarget",nameTarget);
		return request;
	}


}
