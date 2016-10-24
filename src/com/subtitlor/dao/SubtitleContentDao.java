package com.subtitlor.dao;

import java.util.Map;
import java.util.List;

import com.subtitlor.beans.BeanException;
import com.subtitlor.beans.SubtitleContent;
import com.subtitlor.beans.SubtitleInfo;
import com.subtitlor.form.FormException;



public interface SubtitleContentDao {
	void persistSubtitleContent(SubtitleContent subtitleContent) throws DaoException;
	void createTable( SubtitleInfo subtitleInfo ) throws DaoException, FormException, BeanException;
	List<Integer> getListOfIds(SubtitleContent subtitleContent) throws DaoException;
	List<SubtitleContent>lister() throws DaoException;
	SubtitleContent getSubtitleContentFromTable(String table) throws DaoException;
	Map<Integer,String> getSubtitleFromLanguage(SubtitleContent subtitleContent,String language);
}


