package com.subtitlor.dao;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.Part;

import com.subtitlor.beans.BeanException;
import com.subtitlor.beans.SubtitleInfo;
import com.subtitlor.utilities.FileHandler;

public class SubtitleInfoDaoImpl implements SubtitleInfoDao {
	private DaoFactory daoFactory;

	SubtitleInfoDaoImpl(DaoFactory daoFactory) throws DaoException {
		this.daoFactory = daoFactory;
	}

	@Override
	public void persistSubtitleInfo(SubtitleInfo subtitleInfo) throws DaoException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		try {
			connexion = daoFactory.getConnection();
			preparedStatement = connexion.prepareStatement("INSERT INTO subtitle_info("
					+"name_en, finished_en,"
					+"name_fr, finished_fr,"
					+"name_al, finished_al,"
					+"name_es, finished_es,"
					+"name_pt, finished_pt,"
					+"name_video,vo,table_name)"
					+ "VALUES("
					+ "?,?,"
					+ "?,?,"
					+ "?,?,"
					+ "?,?,"
					+ "?,?,"
					+ "?,?,?);");
			preparedStatement.setString(1, subtitleInfo.getNameEn());
			preparedStatement.setBoolean(2, subtitleInfo.isFinishedEn());
			preparedStatement.setString(3, subtitleInfo.getNameFr());
			preparedStatement.setBoolean(4, subtitleInfo.isFinishedFr());
			preparedStatement.setString(5, subtitleInfo.getNameAl());
			preparedStatement.setBoolean(6, subtitleInfo.isFinishedAl());
			preparedStatement.setString(7, subtitleInfo.getNameEs());
			preparedStatement.setBoolean(8, subtitleInfo.isFinishedEs());
			preparedStatement.setString(9, subtitleInfo.getNamePt());
			preparedStatement.setBoolean(10, subtitleInfo.isFinishedPt());
			preparedStatement.setString(11, subtitleInfo.getNameVideo());
			preparedStatement.setString(12, subtitleInfo.getVo());
			preparedStatement.setString(13, subtitleInfo.getTableName());

			preparedStatement.executeUpdate();
			connexion.commit();


		} catch (SQLException e) {
			try {
				if (connexion != null) {
					connexion.rollback();
				}
			} catch (SQLException e2) {
			}
			throw new DaoException("Impossible de manipuler la base de données :: subtitleInfo add");
		}
		finally {
			try {
				if (connexion != null) {
					connexion.close();  
				}
			} catch (SQLException e) {
				throw new DaoException("Impossible de communiquer avec la base de données :: subtitleInfo add");
			}
		}

	}

	@Override
	public List<SubtitleInfo> lister() throws DaoException, BeanException {
		List<SubtitleInfo> subtitleInfos = new ArrayList<SubtitleInfo>();
		Connection connexion = null;
		Statement statement = null;
		ResultSet result = null;

		try {
			connexion = daoFactory.getConnection();
			statement = connexion.createStatement();
			result = statement.executeQuery("SELECT * FROM subtitle_info;");
			while (result.next()) {

				int id = result.getInt("ID");
				String nameVideo = result.getString("name_video");
				String vo = result.getString("vo");
				String nameEn = result.getString("name_en");
				boolean finishedEn =result.getBoolean("finished_en");	
				String nameFr = result.getString("name_fr");
				boolean finishedFr =result.getBoolean("finished_fr");		
				String nameAl = result.getString("name_al");
				boolean finishedAl =result.getBoolean("finished_al");		
				String nameEs = result.getString("name_es");
				boolean finishedEs =result.getBoolean("finished_es");		
				String namePt = result.getString("name_pt");
				boolean finishedPt =result.getBoolean("finished_pt");					
				String tableName =result.getString("table_name");
				SubtitleInfo subtitleInfo = new SubtitleInfo();
				subtitleInfo.setId(id);
				subtitleInfo.setFinishedEn(finishedEn);	
				subtitleInfo.setNameEn(nameEn);
				subtitleInfo.setFinishedEn(finishedEn);	
				subtitleInfo.setNameFr(nameFr);
				subtitleInfo.setFinishedFr(finishedFr);	
				subtitleInfo.setNameAl(nameAl);
				subtitleInfo.setFinishedAl(finishedAl);	
				subtitleInfo.setNameEs(nameEs);
				subtitleInfo.setFinishedEs(finishedEs);	
				subtitleInfo.setNamePt(namePt);
				subtitleInfo.setFinishedPt(finishedPt);	
				subtitleInfo.setNameVideo(nameVideo);
				subtitleInfo.setTableName(tableName);
				subtitleInfo.setVo(vo);

				subtitleInfos.add(subtitleInfo);
			}
		} catch (SQLException e) {
			throw new DaoException("Impossible de manipuler la base de données :: subtitleInfo List");
		}
		finally {
			try {
				if (connexion != null) {
					connexion.close();  
				}
			} catch (SQLException e) {
				throw new DaoException("Impossible de communiquer avec la base de données :: subtitleInfo List");
			}
		}
		return subtitleInfos;
	}

	@Override
	public List<String> videoNameLister() throws DaoException, BeanException {
		List<String> videoNames = new ArrayList<String>();
		Connection connexion = null;
		Statement statement = null;
		ResultSet result = null;
		try {
			connexion = daoFactory.getConnection();
			statement = connexion.createStatement();
			result = statement.executeQuery("SELECT name_video FROM subtitle_info;");
			while (result.next()) {
				String nameVideo = result.getString("name_video");
				videoNames.add(nameVideo);
			}
		} catch (SQLException e) {
			throw new DaoException("Impossiblede manipuler la base de données :: subtitleInfo List video name");
		}
		finally {
			try {
				if (connexion != null) {
					connexion.close();  
				}
			} catch (SQLException e) {
				throw new DaoException("Impossible de communiquer avec la base de données :: subtitleInfo List video name");
			}
		}
		return videoNames;
	}


	public List<String> tableNameLister() throws DaoException, BeanException {
		List<String> tableNames = new ArrayList<String>();
		Connection connexion = null;
		ResultSet result = null;
		try {
			connexion = daoFactory.getConnection();
			DatabaseMetaData md = connexion.getMetaData();
			result = md.getTables(null, null, "%", null);
			while (result.next()) {
				tableNames.add(result.getString(3));
			}
		} catch (SQLException e) {
			throw new DaoException("Impossible de manipuler  la base de données :: subtitleInfo list table name");
		}
		finally {
			try {
				if (connexion != null) {
					connexion.close();  
				}
			} catch (SQLException e) {
				throw new DaoException("Impossible de communiquer avec la base de données :: subtitleInfo list table name");
			}
		}
		return tableNames;
	}

	public String generateTableName(String name){
		String sampleAlphabet = name.replaceAll("[^A-Za-z]+", "").toLowerCase();
		Random random = new Random();
		char[] buf = new char[12];
		for (int i = 0 ; i < 12 ; i++)
			buf[i] = sampleAlphabet.charAt(random.nextInt(sampleAlphabet.length()));
		return new String(buf);
	}

	public String managePart(Part part) throws IOException{
		String fileName = FileHandler.getFileName(part);
		//Move file
		String filePathAndName=FileHandler.writeFile(part, fileName);
		return filePathAndName;
	}

	public SubtitleInfo getSubtitleInfoFromId(int id) throws BeanException, DaoException{
		SubtitleInfo subtitleInfo= new SubtitleInfo();
		Connection connexion = null;
		Statement statement = null;
		ResultSet result = null;
		PreparedStatement preparedStatement = null;
		try {
			connexion = daoFactory.getConnection();
			statement = connexion.createStatement();
			preparedStatement = connexion.prepareStatement("SELECT * FROM subtitle_info Where id= ?;");
			preparedStatement.setInt(1, id);
			result = preparedStatement.executeQuery();
			while (result.next()) {

				String nameVideo = result.getString("name_video");
				String vo = result.getString("vo");
				String nameEn = result.getString("name_en");
				boolean finishedEn =result.getBoolean("finished_en");	
				String nameFr = result.getString("name_fr");
				boolean finishedFr =result.getBoolean("finished_fr");		
				String nameAl = result.getString("name_al");
				boolean finishedAl =result.getBoolean("finished_al");		
				String nameEs = result.getString("name_es");
				boolean finishedEs =result.getBoolean("finished_es");		
				String namePt = result.getString("name_pt");
				boolean finishedPt =result.getBoolean("finished_pt");					
				String tableName =result.getString("table_name");
				subtitleInfo.setId(id);
				subtitleInfo.setFinishedEn(finishedEn);	
				subtitleInfo.setNameEn(nameEn);
				subtitleInfo.setFinishedEn(finishedEn);	
				subtitleInfo.setNameFr(nameFr);
				subtitleInfo.setFinishedFr(finishedFr);	
				subtitleInfo.setNameAl(nameAl);
				subtitleInfo.setFinishedAl(finishedAl);	
				subtitleInfo.setNameEs(nameEs);
				subtitleInfo.setFinishedEs(finishedEs);	
				subtitleInfo.setNamePt(namePt);
				subtitleInfo.setFinishedPt(finishedPt);	
				subtitleInfo.setNameVideo(nameVideo);
				subtitleInfo.setTableName(tableName);
				subtitleInfo.setVo(vo);


			}
		} catch (SQLException e) {
			throw new DaoException("Impossible de manipuler la base de données :: subtitleInfo List");
		}
		finally {
			try {
				if (connexion != null) {
					connexion.close();  
				}
			} catch (SQLException e) {
				throw new DaoException("Impossible de communiquer avec la base de données :: subtitleInfo List");
			}
		}
		return subtitleInfo;
	}

	@Override
	public String getNameFromLanguage(SubtitleInfo subtitleInfo, String language) {
		switch(language){
		case "en":
			return subtitleInfo.getNameEn();
		case "fr":
			return subtitleInfo.getNameFr();
		case "al":
			return subtitleInfo.getNameAl();
		case "es":
			return subtitleInfo.getNameEs();
		case "pt":
			return subtitleInfo.getNamePt();
		default: return null;

		}

	}

	@Override
	public String getNameFromIdLanguage(int id, String language) throws DaoException {

		Connection connexion = null;
		Statement statement = null;
		ResultSet result = null;
		PreparedStatement preparedStatement = null;
		String name=null;
		try {
			connexion = daoFactory.getConnection();
			statement = connexion.createStatement();
			switch(language){
			case "en":
				preparedStatement = connexion.prepareStatement("SELECT name_en as name FROM subtitle_info Where id= ?;");
				break;
			case "fr":
				preparedStatement = connexion.prepareStatement("SELECT name_fr as name FROM subtitle_info Where id= ?;");
				break;
			case "al":
				preparedStatement = connexion.prepareStatement("SELECT name_al as name FROM subtitle_info Where id= ?;");
				break;
			case "es":
				preparedStatement = connexion.prepareStatement("SELECT name_es as name FROM subtitle_info Where id= ?;");
				break;
			case "pt":
				preparedStatement = connexion.prepareStatement("SELECT name_pt as name FROM subtitle_info Where id= ?;");
				break;
			}

			preparedStatement.setInt(1, id);
			result = preparedStatement.executeQuery();

			while (result.next()) {

				name = result.getString("name");
			}


		} catch (SQLException e) {
			throw new DaoException("Impossible de manipuler la base de données :: subtitleInfo List");
		}
		finally {
			try {
				if (connexion != null) {
					connexion.close();  
				}
			} catch (SQLException e) {
				throw new DaoException("Impossible de communiquer avec la base de données :: subtitleInfo List");
			}
		}
		return name;
	}

	@Override
	public void updateNameFromIdLanguage(int id, String name, String language) throws DaoException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		try {
			connexion = daoFactory.getConnection();
			switch(language){
			case "en":
				preparedStatement = connexion.prepareStatement("Update subtitle_info SET name_en=? Where ID= ?;");
				break;
			case "fr":
				preparedStatement = connexion.prepareStatement("Update subtitle_info SET name_fr=? Where ID= ?;");
				break;
			case "al":
				preparedStatement = connexion.prepareStatement("Update subtitle_info SET name_al=? Where ID= ?;");
				break;
			case "es":
				preparedStatement = connexion.prepareStatement("Update subtitle_info SET name_es=? Where ID= ?;");
				break;
			case "pt":
				preparedStatement = connexion.prepareStatement("Update subtitle_info SET name_pt=? Where ID= ?;");
				break;
			}


			preparedStatement.setString(1, name);
			preparedStatement.setInt(2, id);
			System.out.println(preparedStatement);
			preparedStatement.executeUpdate();
			connexion.commit();


		} catch (SQLException e) {
			try {
				if (connexion != null) {
					connexion.rollback();
				}
			} catch (SQLException e2) {
			}
			throw new DaoException("Impossible de manipuler la base de données :: subtitleInfo add");
		}
		finally {
			try {
				if (connexion != null) {
					connexion.close();  
				}
			} catch (SQLException e) {
				throw new DaoException("Impossible de communiquer avec la base de données :: subtitleInfo add");
			}
		}

	}
	
	public void updateFinishFromIdLanguage(int id, Boolean state, String language)throws DaoException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		try {
			connexion = daoFactory.getConnection();
			switch(language){
			case "en":
				preparedStatement = connexion.prepareStatement("Update subtitle_info SET finished_en=? Where ID= ?;");
				break;
			case "fr":
				preparedStatement = connexion.prepareStatement("Update subtitle_info SET finished_fr=? Where ID= ?;");
				break;
			case "al":
				preparedStatement = connexion.prepareStatement("Update subtitle_info SET finished_al=? Where ID= ?;");
				break;
			case "es":
				preparedStatement = connexion.prepareStatement("Update subtitle_info SET finished_es=? Where ID= ?;");
				break;
			case "pt":
				preparedStatement = connexion.prepareStatement("Update subtitle_info SET finished_pt=? Where ID= ?;");
				break;
			}


			preparedStatement.setBoolean(1, state);
			preparedStatement.setInt(2, id);
			System.out.println(preparedStatement);
			preparedStatement.executeUpdate();
			connexion.commit();


		} catch (SQLException e) {
			try {
				if (connexion != null) {
					connexion.rollback();
				}
			} catch (SQLException e2) {
			}
			throw new DaoException("Impossible de manipuler la base de données :: subtitleInfo add");
		}
		finally {
			try {
				if (connexion != null) {
					connexion.close();  
				}
			} catch (SQLException e) {
				throw new DaoException("Impossible de communiquer avec la base de données :: subtitleInfo add");
			}
		}

	}
}