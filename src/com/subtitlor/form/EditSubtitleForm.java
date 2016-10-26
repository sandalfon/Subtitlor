package com.subtitlor.form;

import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.subtitlor.beans.BeanException;
import com.subtitlor.beans.SubtitleMultiLanguage;
import com.subtitlor.beans.SubtitleInfo;
import com.subtitlor.dao.DaoException;
import com.subtitlor.dao.DaoFactory;
import com.subtitlor.dao.SubtitleMultiLanguageDao;
import com.subtitlor.dao.SubtitleInfoDao;

//Gestion du formulaire sur l'édition des sous-titres
public class EditSubtitleForm {
	
	private DaoFactory daoFactory;
	private SubtitleInfoDao subtitleInfoDao;
	private SubtitleMultiLanguageDao subtitleMultiLanguageDao;
	private int subtitlenfoId;
	private String languageTarget;
	private String tableName;

	
	//Mise à jour des sous-titres
	public void updateSubtitles(HttpServletRequest request) throws DaoException, BeanException, SQLException{
		HttpSession session=request.getSession();
		
		subtitlenfoId= (int) session.getAttribute("subtitlenfoId");
		languageTarget=(String) session.getAttribute("languageTarget");
		daoFactory=DaoFactory.getInstance();
		subtitleInfoDao=daoFactory.getSubtitleInfoDao();
		subtitleMultiLanguageDao=daoFactory.getSubtitleContentDao();
		tableName=(String)request.getSession().getAttribute("tableName");
		
		updateName(request.getParameter("nameTarget"));
		updateFinish(request.getParameter("finish"));
		updateSubtitleLines(request);
	}

	
	//mise à jour de l'état d'avancement
	public void updateFinish(String status) throws DaoException{
		Boolean state=false;
		if(status.equals("yes")){
			state=true;
		}
		subtitleInfoDao.updateFinishFromIdLanguage(subtitlenfoId,state,languageTarget);
	}


	//Mise à jour du nom du sous-titre
	public void updateName( String name) throws DaoException{
		if(!name.equals("non défini")){
			String refName=subtitleInfoDao.getNameFromIdLanguage(subtitlenfoId,languageTarget);
			if(!name.equals(refName)){
				subtitleInfoDao.updateNameFromIdLanguage(subtitlenfoId,name,languageTarget);
			}
		}
	}

	//mise à jour des lignes de text du sous-titre
	public void  updateSubtitleLines(HttpServletRequest  request) throws DaoException, SQLException{
		SubtitleMultiLanguage subtitleMultiLanguage= subtitleMultiLanguageDao.getSubtitleMultiLanguageFromTable(tableName);
		Map<Integer,String> oriSubtitles=subtitleMultiLanguageDao.getSubtitleFromLanguage(subtitleMultiLanguage,languageTarget);
		String[] splitStr;
		int id;
		String oriSubtitle;
		String newSubtitle;
		
		for(String name  : request.getParameterMap().keySet()){
			if(name.startsWith("subTarget_")){
				splitStr=name.split("_");
				id=Integer.parseInt(splitStr[1]);
				oriSubtitle=oriSubtitles.get(Integer.parseInt(splitStr[1]));
				newSubtitle=request.getParameter(name);
				
				if(oriSubtitle!=null){
					if(newSubtitle!= null){

						if(!newSubtitle.equals(oriSubtitle)){
							subtitleMultiLanguageDao.persistLine(tableName,languageTarget,id,newSubtitle);
						}
					}
				}
				else{
					if(newSubtitle!= null){
						subtitleMultiLanguageDao.persistLine(tableName,languageTarget,id,newSubtitle);
					}
				}

			}

		}
		
	}


	//Créer la requet pour le servlet
	public HttpServletRequest genereateSubtitlesRequest(HttpServletRequest request,String languageTarget, int subtitlenfoId) throws DaoException, BeanException {
		daoFactory=DaoFactory.getInstance();
		subtitleInfoDao=daoFactory.getSubtitleInfoDao();
		subtitleMultiLanguageDao=daoFactory.getSubtitleContentDao();
		
		SubtitleInfo subtitleInfo =subtitleInfoDao.getSubtitleInfoFromId(subtitlenfoId);
		SubtitleMultiLanguage subtitleMultiLanguage =subtitleMultiLanguageDao.getSubtitleMultiLanguageFromTable( subtitleInfo.getTableName());
		String languageRef=subtitleInfo.getVo();
		String nameTarget=subtitleInfoDao.getNameFromLanguage(subtitleInfo, languageTarget);
		
		request.setAttribute("videoName", subtitleInfo.getNameVideo());
		request.setAttribute("tableName", subtitleInfo.getTableName());
		request.setAttribute("nameRef", subtitleInfoDao.getNameFromLanguage(subtitleInfo, languageRef));
		request.setAttribute("nameTarget",nameTarget );
		request.setAttribute("languageRef", languageRef);
		request.setAttribute("languageTarget", languageTarget);
		request.setAttribute("subtitleRef", subtitleMultiLanguageDao.getSubtitleFromLanguage(subtitleMultiLanguage, languageRef));
		request.setAttribute("subtitleTarget", subtitleMultiLanguageDao.getSubtitleFromLanguage(subtitleMultiLanguage, languageTarget));
		request.setAttribute("ids", subtitleMultiLanguage.getIds());
		request.setAttribute("subtitlenfoId",subtitlenfoId);
		request.getSession().setAttribute("nameTarget",nameTarget);
		request.getSession().setAttribute("tableName",subtitleInfo.getTableName());
		
		return request;
	}
}
