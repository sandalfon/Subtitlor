package com.subtitlor.utilities;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.http.Part;

public class FileHandler {
	public static final String FILE_PATH = "F:/Perso/workspace/Subtitlor/WebContent/Upload/data"; // A changer   
	public static final int BUFFER_SIZE = 10240;

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

	public static  String getFileName( Part part ) {

		for ( String contentDisposition : part.getHeader( "content-disposition" ).split( ";" ) ) {
			if ( contentDisposition.trim().startsWith( "filename" ) ) {
				return contentDisposition.substring( contentDisposition.indexOf( '=' ) + 1 ).trim().replace( "\"", "" );
			}
		}
		return null;
	}
}
