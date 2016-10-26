package com.subtitlor.dao;

import com.subtitlor.beans.Subtitle;
import com.subtitlor.beans.SubtitleMultiLanguage;

public interface SubtitleDao {

	// faire les sous-titres à partir d'un fichier
	Subtitle generateSubtitleFromfile(String fileName) throws DaoException;

	//récupérer les sous-titres d'une lange à partir des sous titre multilangues
	Subtitle getSubtitleFromLanguageFromSubtitleMultiLanguage(SubtitleMultiLanguage subtitleMultiLanguage, String language);

}
