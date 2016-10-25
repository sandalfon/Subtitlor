package com.subtitlor.form;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

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
	private int subtitlenfoId;
	private String languageTarget;
	private String tableName;

	public void updateSubtitles(HttpServletRequest request) throws DaoException, BeanException, SQLException{
		HttpSession session=request.getSession();
		subtitlenfoId= (int) session.getAttribute("subtitlenfoId");
		languageTarget=(String) session.getAttribute("languageTarget");;
		daoFactory=DaoFactory.getInstance();
		subtitleInfoDao=daoFactory.getSubtitleInfoDao();
		subtitleContentDao=daoFactory.getSubtitleContentDao();
		tableName=(String)request.getSession().getAttribute("tableName");
		updateName(request.getParameter("nameTarget"));
		updateFinish(request.getParameter("finish"));
		updateSubtitleLines(request);
		//		for(String name  : request.getParameterMap().keySet()){
		//			System.out.println(name+" "+request.getParameter(name));
		//		}
		System.out.println(tableName);
	}

	public void updateFinish(String status) throws DaoException{
		Boolean state=false;
		if(status.equals("yes")){
			state=true;
		}
		subtitleInfoDao.updateFinishFromIdLanguage(subtitlenfoId,state,languageTarget);
	}


	public void updateName( String name) throws DaoException{
		if(!name.equals("non défini")){
			String refName=subtitleInfoDao.getNameFromIdLanguage(subtitlenfoId,languageTarget);
			if(!name.equals(refName)){
				subtitleInfoDao.updateNameFromIdLanguage(subtitlenfoId,name,languageTarget);
			}
		}
	}

	public void  updateSubtitleLines(HttpServletRequest  request) throws DaoException, SQLException{
		SubtitleContent subtitleContent= subtitleContentDao.getSubtitleContentFromTable(tableName);
		Map<Integer,String> oriSubtitles=subtitleContentDao.getSubtitleFromLanguage(subtitleContent,languageTarget);
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
							System.out.println(oriSubtitle+" différent de "+oriSubtitle);
							subtitleContentDao.persistLine(tableName,languageTarget,id,newSubtitle);
						}
					}
				}
				else{
					if(newSubtitle!= null){
						System.out.println(" null remplacé par "+newSubtitle);
						subtitleContentDao.persistLine(tableName,languageTarget,id,newSubtitle);

					}
				}


			}

		}
		
	}

	public Map<Integer,String> parseSubtitleLines(HttpServletRequest request){
		Map<Integer,String> newSubtitles = new HashMap<Integer, String>();
		//		for(String name  : request.getParameterMap().keySet()){
		//			System.out.println(name+" "+request.getParameter(name));
		//			
		//		}
		return newSubtitles;
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
		request.getSession().setAttribute("tableName",subtitleInfo.getTableName());
		return request;
	}


}
