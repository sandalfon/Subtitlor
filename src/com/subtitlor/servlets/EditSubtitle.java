package com.subtitlor.servlets;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.subtitlor.utilities.SubtitlesHandler;

@WebServlet("/EditSubtitle")
public class EditSubtitle extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String FILE_NAME = "/WEB-INF/data/password_presentation.srt";
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext context = getServletContext();
		System.out.println(context.getRealPath(FILE_NAME));
		SubtitlesHandler subtitles = new SubtitlesHandler(context.getRealPath(FILE_NAME));
		String finish =request.getParameter("finish");
		System.out.println(finish);
		request.setAttribute("subtitles", subtitles.getSubtitles());
		request.setAttribute("finish",finish);
		this.getServletContext().getRequestDispatcher("/WEB-INF/subtitle/edit_subtitle.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String finish =request.getParameter("finish");
		System.out.println(finish);
		request.setAttribute("finish",finish);
		this.getServletContext().getRequestDispatcher("/WEB-INF/subtitle/edit_subtitle.jsp").forward(request, response);
	}

}
