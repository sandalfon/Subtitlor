package com.subtitlor.dao;

import com.subtitlor.beans.BeanException;
import com.subtitlor.beans.Subtitle;
import com.subtitlor.form.FormException;



public interface SubtitleContentDao {
	void createTable( Subtitle subtitle ) throws DaoException, FormException, BeanException;
}


