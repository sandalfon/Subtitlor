package com.subtitlor.dao;

import com.subtitlor.beans.BeanException;
import com.subtitlor.beans.SubtitleInfo;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.Part;




public interface SubtitleInfoDao {
	
	//Récupérer les objets subtileInfo
	List<SubtitleInfo> lister() throws DaoException, BeanException;
	
	//Récupérer les noms des videos
	List<String> videoNameLister() throws DaoException, BeanException;
	
	//Récupérer les noms des tables
	List<String> tableNameLister() throws DaoException, BeanException;
	
	//Générer un nom de tableau
	String generateTableName(String name);
	
	//Gestion des fichiers uploadés
	String managePart(Part part) throws IOException;
	
	// sauvegardes des informations
	void persistSubtitleInfo(SubtitleInfo subtitleInfo) throws DaoException;
	
	//Récupérations de l'information sur les sous-titres à partir d'un identifiant
	SubtitleInfo getSubtitleInfoFromId(int id) throws BeanException, DaoException;
	
	//Récupérer le noms du sous titres en fonction de la langue
	String getNameFromLanguage(SubtitleInfo subtitleInfo,String language);
	
	//Récupérer le nom de la langue à partir d'un identidant et d'un langue
	String getNameFromIdLanguage(int id, String language) throws DaoException;
	
	//mise à jour du nom des sous titres d'une langues 
	void updateNameFromIdLanguage(int id, String name, String language) throws DaoException;
	
	// mise à jour de l'état de la traduction
	void updateFinishFromIdLanguage(int subtitlenfoId, Boolean state, String languageTarget) throws DaoException;
}

