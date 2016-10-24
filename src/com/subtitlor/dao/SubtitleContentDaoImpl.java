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
import com.subtitlor.beans.SubtitleContent;
import com.subtitlor.beans.SubtitleInfo;
import com.subtitlor.form.FormException;

public class SubtitleContentDaoImpl implements SubtitleContentDao{

	private DaoFactory daoFactory;



	SubtitleContentDaoImpl(DaoFactory daoFactory) throws DaoException {
		this.daoFactory = daoFactory;
	}

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

			String queryStr="CREATE TABLE $tableName ("
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

			String query =queryStr.replace("$tableName",tableName);
			preparedStatement = connexion.prepareStatement(query);
			preparedStatement.executeUpdate();
			connexion.commit();


		} catch (SQLException e) {
			try {
				if (connexion != null) {
					connexion.rollback();
				}
			} catch (SQLException e2) {
			}
			throw new DaoException("Impossible de manipuler la base de données :: subtitleContentContent");
		}
		finally {
			try {
				if (connexion != null) {
					connexion.close();  
				}
			} catch (SQLException e) {
				throw new DaoException("Impossible de communiquer avec la base de données :: subtitleContentContent ");
			}
		}

	}
	public void persistSubtitleContent(SubtitleContent subtitleContent) throws DaoException{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		List<Integer> listOfIds = getListOfIds(subtitleContent);
		Map<Integer, String>starts=subtitleContent.getTimeStarts();
		Map<Integer, String>ends=subtitleContent.getTimeStops();
		Map<Integer, String>ens=subtitleContent.getEns();
		Map<Integer, String>frs=subtitleContent.getFrs();
		Map<Integer, String>als=subtitleContent.getAls();
		Map<Integer, String>ess=subtitleContent.getEss();
		Map<Integer, String>pts=subtitleContent.getPts();
		List<Integer> ids = subtitleContent.getIds();
		String tableName=subtitleContent.getTableName();
		String queryAddIndexAndTime="INSERT INTO "+tableName+"(id,start,end) VALUES (?,?,?);";
		
		
		try {
			connexion = daoFactory.getConnection();
			for(int indexMap : ids){
				
				if(!listOfIds.contains(indexMap)){
					
					preparedStatement = connexion.prepareStatement(queryAddIndexAndTime);
					preparedStatement.setInt(1, indexMap);
					preparedStatement.setString(2, starts.get(indexMap));
					preparedStatement.setString(3,ends.get(indexMap));
					preparedStatement.executeUpdate();
				}
				connexion.commit();


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
			throw new DaoException("Impossible de manipuler avec la base de données :: subtitleContent");
		}
		finally {
			try {
				if (connexion != null) {
					connexion.close();  
				}
			} catch (SQLException e) {
				throw new DaoException("Impossible de communiquer avec la base de données :: subtitleContent");
			}
		}
	}
	public List<Integer> getListOfIds(SubtitleContent subtitleContent) throws DaoException{
		List<Integer> listOfIds = new ArrayList<Integer>();
		Connection connexion = null;
		Statement statement = null;
		ResultSet result = null;

		try {
			connexion = daoFactory.getConnection();
			statement = connexion.createStatement();
			String queryStr="SELECT id FROM $tableName;";
			String query =queryStr.replace("$tableName",subtitleContent.getTableName());			
			result = statement.executeQuery(query);

			while (result.next()) {
				int id = result.getInt("id");;
				listOfIds.add(id);

			}
		} catch (SQLException e) {
			throw new DaoException("Impossible de communiquer avec la base de données :: subtitleContent");
		}
		finally {
			try {
				if (connexion != null) {
					connexion.close();  
				}
			} catch (SQLException e) {
				throw new DaoException("Impossible de communiquer avec la base de données :: subtitleContent");
			}
		}
		return listOfIds;

	}
	private PreparedStatement updateSubtitle( Connection connexion,  String tableName, String tagLang,  int index, Map<Integer,String> map) throws SQLException{
		
		String queryAddSub="Update "+tableName+" SET "+tagLang+"=? where id=?;";
		PreparedStatement preparedStatement = connexion.prepareStatement(queryAddSub);
		preparedStatement.setString(1, map.get(index));
		preparedStatement.setInt(2, index);
		 return preparedStatement;

	}
	
	public List<SubtitleContent> lister(){
		
		return null;
		
	}
	
	public SubtitleContent getSubtitleContentFromTable(String tableName) throws DaoException{
		SubtitleContent subtitleContent = new SubtitleContent();
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
			System.out.println(query);	
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
			subtitleContent.setAls(als);
			subtitleContent.setEns(ens);
			subtitleContent.setEss(ess);
			subtitleContent.setFrs(frs);
			subtitleContent.setIds(ids);
			subtitleContent.setPts(pts);
			subtitleContent.setTableName(tableName);
			subtitleContent.setTimeStarts(timeStarts);
			subtitleContent.setTimeStops(timeStops);
		} catch (SQLException e) {
			throw new DaoException("Impossible de communiquer avec la base de données :: subtitleContent");
		}
		finally {
			try {
				if (connexion != null) {
					connexion.close();  
				}
			} catch (SQLException e) {
				throw new DaoException("Impossible de communiquer avec la base de données :: subtitleContent");
			}
		}
		return subtitleContent;
		
	}

	@Override
	public Map<Integer, String> getSubtitleFromLanguage(SubtitleContent subtitleContent, String language) {
		switch(language){
		case "en":
			return subtitleContent.getEns();
		case "fr":
			return subtitleContent.getFrs();
		case "al":
			return subtitleContent.getAls();
		case "es":
			return subtitleContent.getEss();
		case "pt":
			return subtitleContent.getPts();
		default: return null;
		
		}
	}


}
