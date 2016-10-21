package com.subtitlor.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
		System.out.println("persist");
		String tableName=subtitleContent.getTableName();
		String queryAddIndexAndTime="INSERT INTO "+tableName+"(id,start,end) VALUES (?,?,?);";
		String queryAddSub;
		
		try {
			System.out.println(ids.size());
			connexion = daoFactory.getConnection();
			for(int indexMap : ids){
				System.out.println(indexMap+" vs ");
				
				if(!listOfIds.contains(indexMap)){
					
					preparedStatement = connexion.prepareStatement(queryAddIndexAndTime);
					preparedStatement.setInt(1, indexMap);
					preparedStatement.setString(2, starts.get(indexMap));
					preparedStatement.setString(3,ends.get(indexMap));
					System.out.println(preparedStatement);
					preparedStatement.executeUpdate();
					System.out.println(preparedStatement);
				}
				connexion.commit();


				if(ens.keySet().contains(indexMap)){
					preparedStatement=updateSubtitle(connexion, tableName, "en", indexMap, ens);
					preparedStatement.executeUpdate();
					System.out.println(preparedStatement);
					
				}
				if(frs.keySet().contains(indexMap)){
					preparedStatement=updateSubtitle(connexion, tableName, "fr", indexMap, frs);
					preparedStatement.executeUpdate();
					System.out.println(preparedStatement);
					
				}
				if(als.keySet().contains(indexMap)){
					preparedStatement=updateSubtitle(connexion, tableName, "al", indexMap, als);
					preparedStatement.executeUpdate();
					System.out.println(preparedStatement);
					
				}
				if(ess.keySet().contains(indexMap)){
					preparedStatement=updateSubtitle(connexion, tableName, "es", indexMap, ess);
					preparedStatement.executeUpdate();
					System.out.println(preparedStatement);
					
				}
				if(pts.keySet().contains(indexMap)){
					preparedStatement=updateSubtitle(connexion, tableName, "pt", indexMap, pts);
					preparedStatement.executeUpdate();
					System.out.println(preparedStatement);
					
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
		System.out.println(preparedStatement);
		 return preparedStatement;

	}
}
