package com.subtitlor.dao;

import com.subtitlor.beans.BeanException;
import com.subtitlor.beans.Subtitle;
import java.util.List;




public interface SubtitleDao {
	void add( Subtitle subtitle ) throws DaoException;
	List<Subtitle> lister() throws DaoException, BeanException;
}

