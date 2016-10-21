package com.subtitlor.dao;

import com.subtitlor.beans.BeanException;
import com.subtitlor.beans.SubtitleInfo;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.Part;




public interface SubtitleInfoDao {
	void add( SubtitleInfo subtitleInfo ) throws DaoException;
	List<SubtitleInfo> lister() throws DaoException, BeanException;
	List<String> videoNameLister() throws DaoException, BeanException;
	List<String> tableNameLister() throws DaoException, BeanException;
	String generateTableName(String name);
	String managePart(Part part) throws IOException;
}

