package com.subtitlor.servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.subtitlor.beans.BeanException;
import com.subtitlor.dao.DaoException;
import com.subtitlor.form.EditSubtitleForm;

@WebServlet("/EditSubtitle")
//servlet  de l'édition des sous-titres
public class EditSubtitle extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		this.getServletContext().getRequestDispatcher("/WEB-INF/subtitle/edit_subtitle.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//passage par le gestionnaire de formulaire
		EditSubtitleForm editSubtitleForm =new EditSubtitleForm();
		String caseValue=(String)request.getSession().getAttribute("case");
		try {
			editSubtitleForm.updateSubtitles(request);
		} catch (DaoException | BeanException | SQLException e) {
			request.setAttribute("erreur", e.getMessage());
		}
		//forward  uniquement en cas d'édition 
		if(caseValue!=null){
			if(caseValue.equals("edit")){

				this.getServletContext().getRequestDispatcher("/WEB-INF/subtitle/show_subtitle.jsp").forward(request, response);
			}
		}
	}
}
