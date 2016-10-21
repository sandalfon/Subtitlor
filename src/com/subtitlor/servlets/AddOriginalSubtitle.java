package com.subtitlor.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.subtitlor.beans.SubtitleInfo;
import com.subtitlor.dao.DaoException;
import com.subtitlor.dao.DaoFactory;
import com.subtitlor.dao.SubtitleContentDao;
import com.subtitlor.dao.SubtitleInfoDao;
import com.subtitlor.form.AddOriginalSubtitleForm;

/**
 * Servlet implementation class AddNewOriginalSubtitle
 */
@WebServlet("/AddNewOriginalSubtitle")
public class AddOriginalSubtitle extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SubtitleInfoDao subtitleInfoDao;
	private SubtitleContentDao subtitleContentDao;
	public void init() throws ServletException {
		DaoFactory daoFactory = DaoFactory.getInstance();
		try {
			this.subtitleInfoDao = daoFactory.getSubtitleInfoDao();
			this.subtitleContentDao=daoFactory.getSubtitleContentDao();
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public AddOriginalSubtitle() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.getServletContext().getRequestDispatcher("/WEB-INF/subtitle/add_original_subtitle.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {

			AddOriginalSubtitleForm addOriginalSubtitleForm = new AddOriginalSubtitleForm();
			addOriginalSubtitleForm.initAddOriginalArguments(request);
			this.getServletContext().getRequestDispatcher("/WEB-INF/acceuil.jsp").forward(request, response);
		}
		catch (Exception e) {
			request.setAttribute("erreur", e.getMessage());
		}
		this.getServletContext().getRequestDispatcher("/WEB-INF/subtitle/add_original_subtitle.jsp").forward(request, response);
	}

}
