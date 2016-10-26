package com.subtitlor.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DaoFactory {
	private String url;
	private String username;
	private String password;

	DaoFactory(String url, String username, String password) {
		this.url = url;
		this.username = username;
		this.password = password;
	}

	public static DaoFactory getInstance() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {

		}

		DaoFactory instance = new DaoFactory(
				"jdbc:mysql://localhost:3306/subtitlor", "subtitlor", "SubtitlorOC2016");
		return instance;
	}

	public Connection getConnection() throws SQLException {
		Connection connection = DriverManager.getConnection(url, username, password);
		connection.setAutoCommit(false);
		return connection;

	}

	// Récupération des daos
	//Information sur les sous-titres
	public SubtitleInfoDao getSubtitleInfoDao() throws DaoException {
		return new SubtitleInfoDaoImpl(this);
	}

	// sous titre multilangue
	public SubtitleMultiLanguageDao getSubtitleContentDao() throws DaoException {
		return  new SubtitleMultiLanguageDaoImpl(this);
	}
	
	//sous titre mono langue
	public SubtitleDao getSubtitleDao() throws DaoException {
		return  new SubtitleDaoImpl(this);
	}

}