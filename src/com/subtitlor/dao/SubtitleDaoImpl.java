package com.subtitlor.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.subtitlor.beans.BeanException;
import com.subtitlor.beans.Subtitle;

public class SubtitleDaoImpl implements SubtitleDao {
	private DaoFactory daoFactory;

	SubtitleDaoImpl(DaoFactory daoFactory) throws DaoException {
		this.daoFactory = daoFactory;
	}

	@Override
	public void add(Subtitle subtitle) throws DaoException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;

		try {
			connexion = daoFactory.getConnection();
            preparedStatement = connexion.prepareStatement("INSERT INTO subtitle_info(name, name_video,vo,finished,language,table_name) VALUES(?, ?,?,?,?,?);");
            preparedStatement.setString(1, subtitle.getName());
            preparedStatement.setString(2, subtitle.getNameVideo());
            preparedStatement.setBoolean(3, subtitle.isVo());
            preparedStatement.setBoolean(4, subtitle.isFinished());
            preparedStatement.setString(5, subtitle.getLanguage());
            preparedStatement.setString(6, subtitle.getTableName());
            preparedStatement.executeUpdate();
            connexion.commit();
			
			
		} catch (SQLException e) {
            try {
                if (connexion != null) {
                    connexion.rollback();
                }
            } catch (SQLException e2) {
            }
            throw new DaoException("Impossible de manipuler avec la base de données");
        }
        finally {
            try {
                if (connexion != null) {
                    connexion.close();  
                }
            } catch (SQLException e) {
                throw new DaoException("Impossible de communiquer avec la base de données");
            }
        }

	}

	@Override
	public List<Subtitle> lister() throws DaoException, BeanException {
		List<Subtitle> subtitles = new ArrayList<Subtitle>();
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

				Subtitle subtitle = new Subtitle();
				subtitle.setFinished(finished);	
				subtitle.setId(id);
				subtitle.setLanguage(language);
				subtitle.setName(name);
				subtitle.setNameVideo(nameVideo);
				subtitle.setTableName(tableName);
				subtitle.setVo(vo);

				subtitles.add(subtitle);
			}
		} catch (SQLException e) {
			throw new DaoException("Impossible de communiquer avec la base de données");
		}
		finally {
			try {
				if (connexion != null) {
					connexion.close();  
				}
			} catch (SQLException e) {
				throw new DaoException("Impossible de communiquer avec la base de données");
			}
		}
		return subtitles;
	}

}