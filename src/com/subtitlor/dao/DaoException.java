package com.subtitlor.dao;

// gestion des erreurs de la DAO
public class DaoException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public DaoException(String message) {
		super(message);
	}
}

