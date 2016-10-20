package com.subtitlor.beans;

public class Subtitle {
	//id unique de la BD
	private int id;
	//nom du sous titre
	private String name;
	//nom de la video associ�e aux sous titre
	private String nameVideo;
	//statu de l'�dition du sous-titre
	private boolean finished;
	//langue de la traduction
	private String language;
	//nom de la table contenant les sous titres
	private String tableName;
	//est ce la langue originale
	private boolean vo;


	public boolean isVo() {
		return vo;
	}
	public void setVo(boolean vo) {
		this.vo = vo;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {

		return name;
	}
	public void setName(String name) throws BeanException {
		if (name.length() > 50) {
			throw new BeanException("Le nom est trop grand ! (50 caractères maximum)");
		}
		if (name.length() < 4) {
			throw new BeanException("Le nom est trop petit ! (5 caractères minimum)");
		}
		else {
			this.name = name; 
		}
	}
	public String getNameVideo() {
		return nameVideo;
	}
	public void setNameVideo(String nameVideo) throws BeanException {
		if (nameVideo.length() > 50) {
			throw new BeanException("Le nom de la video originale est trop grand ! (50 caractères maximum)");
		}if (nameVideo.length() < 4) {
			throw new BeanException("Le nom de la video originale est trop petit ! (4 caractères minimum)");
		}
		else {
			this.nameVideo = nameVideo; 
		}
	}
	public boolean isFinished() {
		return finished;
	}
	public void setFinished(boolean finished) {
		this.finished = finished;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) throws BeanException {
		if (language.length() > 20) {
			throw new BeanException("Le nom du pays est trop grand ! (20 caractères maximum)");
		}
		if (!language.equals("En") && !language.equals("Fr") && !language.equals("Al") &&!language.equals("Es") && !language.equals("Pt")) {
			throw new BeanException("La référence du pays est inconnue "+language);
		}
		else {
			this.language = language; 
		}

	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) throws BeanException {
		if (tableName.length() !=15) {
			throw new BeanException("Le nom de la table n'a pas la bonne longueur ! (15 caractères maximum)");
		}
		else {
			this.tableName = tableName; 
		}
	}

}
