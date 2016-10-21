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
	public void add(SubtitleInfo subtitleInfo) throws DaoException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;

		try {
			connexion = daoFactory.getConnection();
            preparedStatement = connexion.prepareStatement("INSERT INTO subtitle_info(name, name_video,vo,finished,language,table_name) VALUES(?, ?,?,?,?,?);");
            preparedStatement.setString(1, subtitleInfo.getName());
            preparedStatement.setString(2, subtitleInfo.getNameVideo());
            preparedStatement.setBoolean(3, subtitleInfo.isVo());
            preparedStatement.setBoolean(4, subtitleInfo.isFinished());
            preparedStatement.setString(5, subtitleInfo.getLanguage());
            preparedStatement.setString(6, subtitleInfo.getTableName());
            preparedStatement.executeUpdate();

			System.out.println(preparedStatement);
            connexion.commit();
			
			
		} catch (SQLException e) {
            try {
                if (connexion != null) {
                    connexion.rollback();
                }
            } catch (SQLException e2) {
            }
            throw new DaoException("Impossible de manipuler avec la base de données :: subtitle");
        }
        finally {
            try {
                if (connexion != null) {
                    connexion.close();  
                }
            } catch (SQLException e) {
                throw new DaoException("Impossible de communiquer avec la base de données :: subtitle");
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
				String name = result.getString("name");
				String nameVideo = result.getString("name_video");
				boolean vo = result.getBoolean("vo");
				boolean finished =result.getBoolean("finish");
				String language = result.getString("language");
				String tableName =result.getString("table_name");

				SubtitleInfo subtitleInfo = new SubtitleInfo();
				subtitleInfo.setFinished(finished);	
				subtitleInfo.setId(id);
				subtitleInfo.setLanguage(language);
				subtitleInfo.setName(name);
				subtitleInfo.setNameVideo(nameVideo);
				subtitleInfo.setTableName(tableName);
				subtitleInfo.setVo(vo);

				subtitleInfos.add(subtitleInfo);
			}
		} catch (SQLException e) {
			throw new DaoException("Impossible de communiquer avec la base de données :: subtitle");
		}
		finally {
			try {
				if (connexion != null) {
					connexion.close();  
				}
			} catch (SQLException e) {
				throw new DaoException("Impossible de communiquer avec la base de données :: subtitle");
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
			throw new DaoException("Impossible de communiquer avec la base de données :: subtitle");
		}
		finally {
			try {
				if (connexion != null) {
					connexion.close();  
				}
			} catch (SQLException e) {
				throw new DaoException("Impossible de communiquer avec la base de données :: subtitle");
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
			throw new DaoException("Impossible de communiquer avec la base de données :: subtitle");
		}
		finally {
			try {
				if (connexion != null) {
					connexion.close();  
				}
			} catch (SQLException e) {
				throw new DaoException("Impossible de communiquer avec la base de données :: subtitle");
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
		System.out.println(fileName+" \n move to");
		//Move file
		String filePathAndName=FileHandler.writeFile(part, fileName);
		System.out.println(filePathAndName);
		return filePathAndName;
	}
	
}