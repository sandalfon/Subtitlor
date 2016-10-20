package com.subtitlor.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.subtitlor.beans.BeanException;
import com.subtitlor.beans.Subtitle;
import com.subtitlor.beans.SubtitleContent;
import com.subtitlor.form.FormException;

public class SubtitleContentDaoImpl implements SubtitleContentDao{

	private DaoFactory daoFactory;

	

	SubtitleContentDaoImpl(DaoFactory daoFactory) throws DaoException {
		this.daoFactory = daoFactory;
	}

	public void createTable(Subtitle subtitle) throws DaoException, FormException, BeanException {
		daoFactory = DaoFactory.getInstance();
		SubtitleDao subtitleDao = daoFactory.getSubtitleDao();
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		
		try {
			String tableName=subtitle.getTableName();
			
			if( tableName == null){
				throw new FormException("Erreur nom de la base non défini");
			}else{
				for(String tableNameRef : subtitleDao.tableNameLister()){
					if(tableName.contains(tableNameRef)){
						throw new FormException("Erreur nom de la base déjà présent");
					}
				}
			}
			connexion = daoFactory.getConnection();
			
					String queryStr="CREATE TABLE $tableName ("
					+" id MEDIUMINT NOT NULL AUTO_INCREMENT, "
					+"name CHAR(30) NOT NULL, "
					+"start CHAR(30) NOT NULL ,"
					+"end CHAR(30) NOT NULL, "
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
			throw new DaoException("Impossible de manipuler la base de données :: subtitleContent");
		}
		finally {
			try {
				if (connexion != null) {
					connexion.close();  
				}
			} catch (SQLException e) {
				throw new DaoException("Impossible de communiquer avec la base de données :: subtitleContent ");
			}
		}

	}


}
