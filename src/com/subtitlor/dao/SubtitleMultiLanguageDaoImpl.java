package com.subtitlor.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.subtitlor.beans.BeanException;
import com.subtitlor.beans.SubtitleMultiLanguage;
import com.subtitlor.beans.SubtitleInfo;
import com.subtitlor.form.FormException;

public class SubtitleMultiLanguageDaoImpl implements SubtitleMultiLanguageDao{

	private DaoFactory daoFactory;


	SubtitleMultiLanguageDaoImpl(DaoFactory daoFactory) throws DaoException {
		this.daoFactory = daoFactory;
	}

	
	//creation de la table pour les sous titres multilangues
	public void createTable(SubtitleInfo subtitleInfo) throws DaoException, FormException, BeanException {

		daoFactory = DaoFactory.getInstance();
		SubtitleInfoDao subtitleInfoDao = daoFactory.getSubtitleInfoDao();
		Connection connexion = null;
		PreparedStatement preparedStatement = null;

		try {
			String tableName=subtitleInfo.getTableName();

			if( tableName == null){
				throw new FormException("Erreur nom de la base non défini");
			}else{
				for(String tableNameRef : subtitleInfoDao.tableNameLister()){
					if(tableName.contains(tableNameRef)){
						throw new FormException("Erreur nom de la base déjà présent");
					}
				}
			}

			connexion = daoFactory.getConnection();
			String queryStr="CREATE TABLE "+tableName+" ("
					+" id INT NOT NULL , "
					+"start CHAR(30) NOT NULL ,"
					+"end CHAR(30) NOT NULL , "
					+"en TEXT, "
					+"fr TEXT, "
					+"al TEXT, "
					+"es TEXT, "
					+"pt TEXT, "
					+"PRIMARY KEY (id)"
					+");";


			preparedStatement = connexion.prepareStatement(queryStr);
			preparedStatement.executeUpdate();
			connexion.commit();


		} catch (SQLException e) {
			try {
				if (connexion != null) {
					connexion.rollback();
				}
			} catch (SQLException e2) {
			}
			throw new DaoException("[erreur subtitleMultiLanguage createTable] Impossible de créer la table ");
		}
		finally {
			try {
				if (connexion != null) {
					connexion.close();  
				}
			} catch (SQLException e) {
				throw new DaoException("[erreur subtitleMultiLanguage createTable] Impossible de communiquer avec la base de données");
			}
		}

	}

	
	// Sauvegarde les sous-titres multilangues
	public void persistSubtitleMultiLanguage(SubtitleMultiLanguage subtitleMultiLanguage) throws DaoException{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;

		List<Integer> listOfIds = getListOfIds(subtitleMultiLanguage);
		Map<Integer, String>starts=subtitleMultiLanguage.getTimeStarts();
		Map<Integer, String>ends=subtitleMultiLanguage.getTimeStops();
		Map<Integer, String>ens=subtitleMultiLanguage.getEns();
		Map<Integer, String>frs=subtitleMultiLanguage.getFrs();
		Map<Integer, String>als=subtitleMultiLanguage.getAls();
		Map<Integer, String>ess=subtitleMultiLanguage.getEss();
		Map<Integer, String>pts=subtitleMultiLanguage.getPts();
		List<Integer> ids = subtitleMultiLanguage.getIds();
		String tableName=subtitleMultiLanguage.getTableName();

		String queryAddIndexAndTime="INSERT INTO "+tableName+"(id,start,end) VALUES (?,?,?);";
		try {
			connexion = daoFactory.getConnection();
			for(int indexMap : ids){

				//sauvegarde des identifiants de lignes et des temps de début et fin
				if(!listOfIds.contains(indexMap)){
					preparedStatement = connexion.prepareStatement(queryAddIndexAndTime);
					preparedStatement.setInt(1, indexMap);
					preparedStatement.setString(2, starts.get(indexMap));
					preparedStatement.setString(3,ends.get(indexMap));
					preparedStatement.executeUpdate();
				}
				connexion.commit();

				//sauvegarde des sous-titres en différentes langues si ils existent
				if(ens.keySet().contains(indexMap)){
					preparedStatement=updateSubtitle(connexion, tableName, "en", indexMap, ens);
					preparedStatement.executeUpdate();
				}

				if(frs.keySet().contains(indexMap)){
					preparedStatement=updateSubtitle(connexion, tableName, "fr", indexMap, frs);
					preparedStatement.executeUpdate();
				}

				if(als.keySet().contains(indexMap)){
					preparedStatement=updateSubtitle(connexion, tableName, "al", indexMap, als);
					preparedStatement.executeUpdate();
				}

				if(ess.keySet().contains(indexMap)){
					preparedStatement=updateSubtitle(connexion, tableName, "es", indexMap, ess);
					preparedStatement.executeUpdate();
				}

				if(pts.keySet().contains(indexMap)){
					preparedStatement=updateSubtitle(connexion, tableName, "pt", indexMap, pts);
					preparedStatement.executeUpdate();
				}
			}
			connexion.commit();

		} catch (SQLException e) {
			try {
				if (connexion != null) {
					connexion.rollback();
				}
			} catch (SQLException e2) {
			}
			throw new DaoException("[erreur subtitleMultiLanguage persistSubtitleMultiLanguage] Impossible de persister l'objet");
		}
		finally {
			try {
				if (connexion != null) {
					connexion.close();  
				}
			} catch (SQLException e) {
				throw new DaoException("[erreur subtitleMultiLanguage persistSubtitleMultiLanguage] Impossible de communiquer avec la base de données");
			}
		}
	}

	
	//Récupération de identitifiants de l'objets multilangue
	public List<Integer> getListOfIds(SubtitleMultiLanguage subtitleMultiLanguage) throws DaoException{
		List<Integer> listOfIds = new ArrayList<Integer>();
		Connection connexion = null;
		Statement statement = null;
		ResultSet result = null;

		try {
			connexion = daoFactory.getConnection();
			statement = connexion.createStatement();
			String queryStr="SELECT id FROM $tableName;";
			String query =queryStr.replace("$tableName",subtitleMultiLanguage.getTableName());			
			result = statement.executeQuery(query);

			while (result.next()) {
				int id = result.getInt("id");;
				listOfIds.add(id);

			}
		} catch (SQLException e) {
			throw new DaoException("[erreur subtitleMultiLanguage getListOfIds] Impossible de récupérer les identifiants");
		}
		finally {
			try {
				if (connexion != null) {
					connexion.close();  
				}
			} catch (SQLException e) {
				throw new DaoException("[erreur subtitleMultiLanguage getListOfIds] Impossible de communiquer avec la base de données");
			}
		}
		return listOfIds;

	}

	
	//Préparation du statement de la mise à jour des sous-titres
	private PreparedStatement updateSubtitle( Connection connexion,  String tableName, String tagLang,  int index, Map<Integer,String> map) throws SQLException{
		String queryAddSub="Update "+tableName+" SET "+tagLang+"=? where id=?;";
		PreparedStatement preparedStatement = connexion.prepareStatement(queryAddSub);
		preparedStatement.setString(1, map.get(index));
		preparedStatement.setInt(2, index);
		return preparedStatement;
	}

	
	//Récupérer les sous titres à partir de la table
	public SubtitleMultiLanguage getSubtitleMultiLanguageFromTable(String tableName) throws DaoException{
		SubtitleMultiLanguage subtitleMultiLanguage = new SubtitleMultiLanguage();
		Connection connexion = null;
		Statement statement = null;
		ResultSet result = null;

		List<Integer> ids=new ArrayList<Integer>();
		Map<Integer, String> timeStarts=new HashMap<Integer, String>();
		Map<Integer, String> timeStops=new HashMap<Integer, String>();
		Map<Integer, String> frs=new HashMap<Integer, String>();
		Map<Integer, String> ens=new HashMap<Integer, String>();
		Map<Integer, String> als=new HashMap<Integer, String>();
		Map<Integer, String> ess=new HashMap<Integer, String>();
		Map<Integer, String> pts=new HashMap<Integer, String>();
		int id;

		try {
			connexion = daoFactory.getConnection();
			statement = connexion.createStatement();
			String queryStr="SELECT * FROM $tableName;";
			String query =queryStr.replace("$tableName",tableName);		

			result = statement.executeQuery(query);

			while (result.next()) {
				id=result.getInt("id");
				ids.add(id);
				timeStarts.put(id, result.getString("start"));
				timeStarts.put(id, result.getString("start"));
				timeStops.put(id, result.getString("end"));
				ens.put(id, result.getString("en"));
				frs.put(id, result.getString("fr"));
				als.put(id, result.getString("al"));
				ess.put(id, result.getString("es"));
				pts.put(id, result.getString("pt"));

			}

			subtitleMultiLanguage.setAls(als);
			subtitleMultiLanguage.setEns(ens);
			subtitleMultiLanguage.setEss(ess);
			subtitleMultiLanguage.setFrs(frs);
			subtitleMultiLanguage.setIds(ids);
			subtitleMultiLanguage.setPts(pts);
			subtitleMultiLanguage.setTableName(tableName);
			subtitleMultiLanguage.setTimeStarts(timeStarts);
			subtitleMultiLanguage.setTimeStops(timeStops);

		} catch (SQLException e) {
			throw new DaoException("[erreur subtitleMultiLanguage getSubtitleMultiLanguageFromTable] Impossible de récupérer les sous titres");
		}
		finally {
			try {
				if (connexion != null) {
					connexion.close();  
				}
			} catch (SQLException e) {
				throw new DaoException("[erreur subtitleMultiLanguage getSubtitleMultiLanguageFromTable] Impossible de communiquer avec la base de données");
			}
		}
		return subtitleMultiLanguage;

	}

	
	//recupérer les lignes de sous-titres à partir d'une langue spécifiques
	public Map<Integer, String> getSubtitleFromLanguage(SubtitleMultiLanguage subtitleMultiLanguage, String language) {
		switch(language){
		case "en":
			return subtitleMultiLanguage.getEns();
		case "fr":
			return subtitleMultiLanguage.getFrs();
		case "al":
			return subtitleMultiLanguage.getAls();
		case "es":
			return subtitleMultiLanguage.getEss();
		case "pt":
			return subtitleMultiLanguage.getPts();
		default: return null;

		}
	}

	
	//persister dans la base de données les lignes de sous titres pour un identifant str et une langue précise
	public void persistLine(String tableName, String languageTarget, int id,String line) throws  DaoException{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;

		String queryAddIndexAndTime="Update  "+tableName+" set "+languageTarget+"=? where id= ?;";
		try {
			connexion = daoFactory.getConnection();
			preparedStatement = connexion.prepareStatement(queryAddIndexAndTime);
			preparedStatement.setInt(2, id);
			preparedStatement.setString(1, line);

			preparedStatement.executeUpdate();
			connexion.commit();
		} catch (SQLException e) {
			throw new DaoException("[erreur subtitleMultiLanguage persistLine]  Impossible de sauvegarder la ligne");
		}
		finally {
			try {
				if (connexion != null) {
					connexion.close();  
				}
			} catch (SQLException e) {
				throw new DaoException("[erreur subtitleMultiLanguage persistLine]  Impossible de communiquer avec la base de données");
			}
		}
	}

}
