package com.subtitlor.dao;

import java.util.Map;
import java.util.List;

import com.subtitlor.beans.BeanException;
import com.subtitlor.beans.SubtitleMultiLanguage;
import com.subtitlor.beans.SubtitleInfo;
import com.subtitlor.form.FormException;



public interface SubtitleMultiLanguageDao {
	// Sauvegarde les sous-titres multilangues
	void persistSubtitleMultiLanguage(SubtitleMultiLanguage subtitleMultiLanguage) throws DaoException;
	
	//creation de la table pour les sous titres multilangues
	void createTable( SubtitleInfo subtitleInfo ) throws DaoException, FormException, BeanException;
	
	//Récupération de identitifiants de l'objets multilangue
	List<Integer> getListOfIds(SubtitleMultiLanguage subtitleMultiLanguage) throws DaoException;
	
	//Récupérer les sous titres à partir de la table
	SubtitleMultiLanguage getSubtitleMultiLanguageFromTable(String table) throws DaoException;
	
	//recupérer les lignes de sous-titres à partir d'une langue spécifiques
	Map<Integer,String> getSubtitleFromLanguage(SubtitleMultiLanguage subtitleMultiLanguage,String language);
	
	//persister dans la base de données les lignes de sous titres pour un identifant str et une langue précise
	void persistLine(String tableName, String languageTarget, int id,String line) throws DaoException;
}


