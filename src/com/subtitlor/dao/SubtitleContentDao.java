package com.subtitlor.dao;

import com.subtitlor.beans.SubtitleContent;



public interface SubtitleContentDao {
	void createTable( SubtitleContent subtitleContent ) throws DaoException;
}


