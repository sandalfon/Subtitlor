package com.subtitlor.servlets;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.subtitlor.beans.BeanException;
import com.subtitlor.dao.DaoException;
import com.subtitlor.form.EditSubtitleForm;

@WebServlet("/EditSubtitle")
public class EditSubtitle extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		this.getServletContext().getRequestDispatcher("/WEB-INF/subtitle/edit_subtitle.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EditSubtitleForm editSubtitleForm =new EditSubtitleForm();
		System.out.println("edit sub : "+(String)request.getAttribute("language"));
		System.out.println("test "+request.getAttribute("test"));
		try {
			System.out.println("att : "+request.getAttribute("newNameTarget"));
			System.out.println("param : "+request.getParameter("newNameTarget"));
			request.getSession().setAttribute("targetName",request.getAttribute("nameTarget"));
			editSubtitleForm.updateSubtitles(request);
		} catch (DaoException | BeanException e) {
			request.setAttribute("erreur", e.getMessage());
		}
		this.getServletContext().getRequestDispatcher("/WEB-INF/subtitle/show_subtitle.jsp").forward(request, response);
	}

}
