package com.subtitlor.utilities;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.subtitlor.beans.Subtitle;

public class FileHandler {
	public static final String FILE_PATH = "F:/Perso/workspace/Subtitlor/WebContent/Upload/data"; // A changer   
	public static final int BUFFER_SIZE = 10240;
	public static final String TMP_PATH= "C:/Temp";

	public static String  writeFile( Part part, String fileName ) throws IOException {
		if (fileName != null && !fileName.isEmpty()) {
			// Corrige un bug du fonctionnement d'Internet Explorer
			fileName = fileName.substring(fileName.lastIndexOf('/') + 1)
					.substring(fileName.lastIndexOf('\\') + 1);


			BufferedInputStream input = null;
			BufferedOutputStream output = null;
			try {
				input = new BufferedInputStream(part.getInputStream(), BUFFER_SIZE);
				output = new BufferedOutputStream(new FileOutputStream(new File(FILE_PATH + fileName)), BUFFER_SIZE);

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
		return FILE_PATH+fileName;
	}


	public static String  writeTmpFile(Subtitle subtitle, String fileName ) throws IOException {
		String filePath=TMP_PATH+"/"+fileName+".srt";
		try {
			
			PrintWriter writer = new PrintWriter(filePath, "UTF-8");
			writer.println(subtitle.toString());
			writer.close();


		} catch (IOException ignore) {
		}

		return filePath;
	}



	public static  String getFileName( Part part ) {

		for ( String contentDisposition : part.getHeader( "content-disposition" ).split( ";" ) ) {
			if ( contentDisposition.trim().startsWith( "filename" ) ) {
				return contentDisposition.substring( contentDisposition.indexOf( '=' ) + 1 ).trim().replace( "\"", "" );
			}
		}
		return null;
	}

	public static HttpServletResponse exportFile(HttpServletRequest request, HttpServletResponse response,Subtitle subtitle, String fileName) throws IOException{
		
		String filePath=FileHandler.writeTmpFile( subtitle, fileName);
		File downloadFile = new File(filePath);
		FileInputStream inStream = new FileInputStream(downloadFile);
		ServletContext context = request.getServletContext();
		String mimeType = context.getMimeType(filePath);
		if (mimeType == null) {        
			mimeType = "application/octet-stream";
		}
		response.setContentType(mimeType);
		response.setContentLength((int) downloadFile.length());
		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
		response.setHeader(headerKey, headerValue);

		
		OutputStream outStream = response.getOutputStream();

		byte[] buffer = new byte[4096];
		int bytesRead = -1;

		while ((bytesRead = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, bytesRead);
		}

		inStream.close();
		outStream.close();     
		downloadFile.delete();
	return response;   
	}


}
