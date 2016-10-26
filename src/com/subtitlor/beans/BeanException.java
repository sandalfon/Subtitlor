package com.subtitlor.beans;


//Gestion des erreurs de Beans
public class BeanException extends Exception {

	private static final long serialVersionUID = 1L;

	public BeanException(String message) {
        super(message);
    }
}
