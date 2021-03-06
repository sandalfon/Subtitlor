package com.subtitlor.servlets;

import java.io.IOException;
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
import com.subtitlor.form.ManageSubtitleForm;


@WebServlet("/ShowSubtitlor")
//servlet de la gestion générale des sous-titres
public class ShowSubtitle extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ShowSubtitle() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DaoFactory daoFactory = DaoFactory.getInstance();
		try {
			//recupération des inforamtions des sous-titres pour l'affichage
			SubtitleInfoDao subtitleInfoDao = daoFactory.getSubtitleInfoDao();
			List<SubtitleInfo> subtitleInfos =subtitleInfoDao.lister();
			request.setAttribute("subtitleInfos",subtitleInfos);

		} catch (DaoException | BeanException e) {
			request.setAttribute("erreur", e.getMessage());

		} 
		this.getServletContext().getRequestDispatcher("/WEB-INF/subtitle/show_subtitle.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Passage par le formulaire
		ManageSubtitleForm manageSubtitleForm = new ManageSubtitleForm();
		String caseTest="";
		try {
			request=manageSubtitleForm.dispacher(request,response);
			caseTest=(String) request.getSession().getAttribute("case");

		} catch (DaoException | BeanException | IOException e) {
			request.setAttribute("erreur", e.getMessage());
		}
		//froward uniquement en cas d'édition, en cas de téléchagement la page reste inchangée
		if(caseTest!=null){
			if(caseTest.equals("edit")){
				this.getServletContext().getRequestDispatcher((String) request.getAttribute("jspName")).forward(request, response);
			}
		}
	}

}
