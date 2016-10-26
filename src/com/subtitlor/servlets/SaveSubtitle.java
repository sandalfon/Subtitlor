package com.subtitlor.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.subtitlor.beans.BeanException;
import com.subtitlor.beans.SubtitleInfo;
import com.subtitlor.dao.DaoException;
import com.subtitlor.dao.DaoFactory;
import com.subtitlor.dao.SubtitleInfoDao;
import com.subtitlor.form.EditSubtitleForm;


@WebServlet("/SaveSubtitle")
//servlet de sauve garde des sous-titres
public class SaveSubtitle extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SaveSubtitle() {
		super();

	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		DaoFactory daoFactory = DaoFactory.getInstance();
		try {
			//Récupération des informations des sous-titres
			SubtitleInfoDao subtitleInfoDao = daoFactory.getSubtitleInfoDao();
			List<SubtitleInfo> subtitleInfos =subtitleInfoDao.lister();
			request.setAttribute("subtitleInfos",subtitleInfos);
		} catch (DaoException | BeanException    e) {
			request.setAttribute("erreur", e.getMessage());
		}
		this.getServletContext().getRequestDispatcher("/WEB-INF/subtitle/show_subtitle.jsp").forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Gestion par le formulaire
		EditSubtitleForm editSubtitleForm =new EditSubtitleForm();
		DaoFactory daoFactory = DaoFactory.getInstance();
		
		try {
			//Récupération des informations des sous-titres
			editSubtitleForm.updateSubtitles(request);
			SubtitleInfoDao subtitleInfoDao = daoFactory.getSubtitleInfoDao();
			List<SubtitleInfo> subtitleInfos =subtitleInfoDao.lister();
			request.setAttribute("subtitleInfos",subtitleInfos);
		} catch (DaoException | BeanException  | SQLException e) {
			request.setAttribute("erreur", e.getMessage());
		}
		this.getServletContext().getRequestDispatcher("/WEB-INF/subtitle/show_subtitle.jsp").forward(request, response);
	}


}
