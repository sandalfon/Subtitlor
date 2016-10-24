package com.subtitlor.dao;

import com.subtitlor.beans.BeanException;
import com.subtitlor.beans.SubtitleInfo;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.Part;




public interface SubtitleInfoDao {
	List<SubtitleInfo> lister() throws DaoException, BeanException;
	List<String> videoNameLister() throws DaoException, BeanException;
	List<String> tableNameLister() throws DaoException, BeanException;
	String generateTableName(String name);
	String managePart(Part part) throws IOException;
	void persistSubtitleInfo(SubtitleInfo subtitleInfo) throws DaoException;
	SubtitleInfo getSubtitleInfoFromId(int id) throws BeanException, DaoException;
	String getNameFromLanguage(SubtitleInfo subtitleInfo,String language);
	String getNameFromIdLanguage(int id, String language) throws DaoException;
	void updateNameFromIdLanguage(int id, String name, String language) throws DaoException;
}

