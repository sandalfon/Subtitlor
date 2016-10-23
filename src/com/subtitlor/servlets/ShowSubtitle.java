package com.subtitlor.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.subtitlor.beans.BeanException;
import com.subtitlor.beans.SubtitleContent;
import com.subtitlor.beans.SubtitleInfo;
import com.subtitlor.dao.DaoException;
import com.subtitlor.dao.DaoFactory;
import com.subtitlor.dao.SubtitleContentDao;
import com.subtitlor.dao.SubtitleInfoDao;
import com.subtitlor.form.FormException;
import com.subtitlor.form.ShowSubtitleForm;

/**
 * Servlet implementation class ShowSubtitlor
 */
@WebServlet("/ShowSubtitlor")
public class ShowSubtitle extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowSubtitle() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DaoFactory daoFactory = DaoFactory.getInstance();
		try {
			SubtitleInfoDao subtitleInfoDao = daoFactory.getSubtitleInfoDao();
			List<SubtitleInfo> subtitleInfos =subtitleInfoDao.lister();
			request.setAttribute("subtitleInfos",subtitleInfos);
		
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			request.setAttribute("erreur", e.getMessage());
			
		} catch (BeanException e) {
			// TODO Auto-generated catch block
			request.setAttribute("erreur", e.getMessage());
		}
		
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/subtitle/show_subtitle.jsp").forward(request, response);
		}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		String button= request.getParameter("selectedButton");
//		System.out.println(button);
		ShowSubtitleForm showSubtitleForm = new ShowSubtitleForm();
		
	
			
			try {
				request=showSubtitleForm.dispacher(request);
			} catch (DaoException | BeanException e) {
				request.setAttribute("erreur", e.getMessage());
			}
			
			
		this.getServletContext().getRequestDispatcher((String) request.getAttribute("jspName")).forward(request, response);
		}

}
