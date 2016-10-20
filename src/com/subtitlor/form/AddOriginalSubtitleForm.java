package com.subtitlor.form;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import com.subtitlor.beans.BeanException;
import com.subtitlor.beans.Subtitle;

public class AddOriginalSubtitleForm {
	private Subtitle subtitle;
	public static final String FILE_PATH = "F:/Perso/workspace/Subtitlor/WebContent/Upload"; // A changer   
	public static final int BUFFER_SIZE = 10240;
	public void initAddOriginalArguments(HttpServletRequest request) throws FormException, BeanException, IOException, ServletException{
		
			subtitle= new Subtitle();
			String name = request.getParameter("name");

			String nameVideo = request.getParameter("nameVideo");
			if(request.getParameter("finish").equals("yes")){
				subtitle.setFinished(true);
			}
			else{
				subtitle.setFinished(false);
			}
			String language = request.getParameter("language");

			if( language == null){
				throw new FormException("Erreur langue non défini");
			}else{
				subtitle.setLanguage(language);
			}
			if( nameVideo == null){
				throw new FormException("Erreur nom de la video non défini");
			}else{
				subtitle.setNameVideo(nameVideo);
			}
			
			String tableName = generateTableName(nameVideo)+"_"+language;
			subtitle.setTableName(tableName);
			if( name == null){
				throw new FormException("Erruer nom non défini");
			}else{
				subtitle.setName(name);
			}
			
			subtitle.setVo(true);

			// On récupère le champ du fichier
			Part part = request.getPart("file");

			// On vérifie qu'on a bien reçu un fichier
			String fileName = getFileName(part);

			// Si on a bien un fichier
			if (fileName != null && !fileName.isEmpty()) {
				String nomChamp = part.getName();
				// Corrige un bug du fonctionnement d'Internet Explorer
				fileName = fileName.substring(fileName.lastIndexOf('/') + 1)
						.substring(fileName.lastIndexOf('\\') + 1);

				// On écrit définitivement le fichier sur le disque
				writeFile(part, fileName, FILE_PATH);

				request.setAttribute(nomChamp, fileName);
			}


	


	}

	public Subtitle getSubtitle(){

		return subtitle;
	}

	private void writeFile( Part part, String fileName, String path ) throws IOException {
		BufferedInputStream input = null;
		BufferedOutputStream output = null;
		try {
			input = new BufferedInputStream(part.getInputStream(), BUFFER_SIZE);
			output = new BufferedOutputStream(new FileOutputStream(new File(path + fileName)), BUFFER_SIZE);

			byte[] buffer = new byte[BUFFER_SIZE];
			int length;
			while ((length = input.read(buffer)) > 0) {
				output.write(buffer, 0, length);
			}
		} finally {
			try {
				output.close();
			} catch (IOException ignore) {
			}
			try {
				input.close();
			} catch (IOException ignore) {
			}
		}
	}

	private static String getFileName( Part part ) {
		for ( String contentDisposition : part.getHeader( "content-disposition" ).split( ";" ) ) {
			if ( contentDisposition.trim().startsWith( "filename" ) ) {
				return contentDisposition.substring( contentDisposition.indexOf( '=' ) + 1 ).trim().replace( "\"", "" );
			}
		}
		return null;
	}
	
	private static String generateTableName(String name){
	    String sampleAlphabet = name.replaceAll("[^A-Za-z]+", "");
	    Random random = new Random();
	    char[] buf = new char[12];
	    for (int i = 0 ; i < 12 ; i++)
	        buf[i] = sampleAlphabet.charAt(random.nextInt(sampleAlphabet.length()));
	    return new String(buf);
	}

}
