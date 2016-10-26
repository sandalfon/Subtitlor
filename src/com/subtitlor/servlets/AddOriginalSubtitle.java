package com.subtitlor.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.subtitlor.beans.SubtitleInfo;
import com.subtitlor.dao.DaoException;
import com.subtitlor.dao.DaoFactory;
import com.subtitlor.dao.SubtitleMultiLanguageDao;
import com.subtitlor.dao.SubtitleInfoDao;
import com.subtitlor.form.AddOriginalSubtitleForm;


@WebServlet("/AddNewOriginalSubtitle")
//servelet de l'ajout de nouveaux sous-titres
public class AddOriginalSubtitle extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private SubtitleInfoDao subtitleInfoDao;
	private SubtitleMultiLanguageDao subtitleMultiLanguageDao;
	private DaoFactory daoFactory ;
	
	public void init() throws ServletException {
		daoFactory = DaoFactory.getInstance();
		subtitleMultiLanguageDao=null;
		subtitleInfoDao=null;
		try {
			subtitleInfoDao = daoFactory.getSubtitleInfoDao();
			subtitleMultiLanguageDao=daoFactory.getSubtitleContentDao();
		} catch (DaoException e) {
			e.printStackTrace();
		}
	}
	public AddOriginalSubtitle() {
		super();
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher("/WEB-INF/subtitle/add_original_subtitle.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//passage par le gestionnaire de formulaire pour garder le servlet 
			AddOriginalSubtitleForm addOriginalSubtitleForm = new AddOriginalSubtitleForm();
			addOriginalSubtitleForm.makeNewSubtitlesFromFile(request);
			subtitleInfoDao = daoFactory.getSubtitleInfoDao();
			List<SubtitleInfo> subtitleInfos =subtitleInfoDao.lister();
			request.setAttribute("subtitleInfos",subtitleInfos);
		}
		catch (Exception e) {
			request.setAttribute("erreur", e.getMessage());
		}
		this.getServletContext().getRequestDispatcher("/WEB-INF/subtitle/show_subtitle.jsp").forward(request, response);
	}

}
