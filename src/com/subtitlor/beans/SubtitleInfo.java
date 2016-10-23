package com.subtitlor.beans;

public class SubtitleInfo {
	//id unique de la BD
	private int id;
	//nom de la video associ�e aux sous titre
	private String nameVideo="";
	//nom du sous titre anglais
	private String nameEn="";
	//statu de l'�dition du sous-titre anglais
	private boolean finishedEn=false;
	//nom du sous titre français
	private String nameFr="";
	//statu de l'�dition du sous-titre français
	private boolean finishedFr=false;
	//nom du sous titre allemand
	private String nameAl="";
	//statu de l'�dition du sous-titre Allemand
	private boolean finishedAl=false;
	//nom du sous titre espagnol
	private String nameEs="";
	//statu de l'�dition du sous-titre espagnol
	private boolean finishedEs=false;
	//nom du sous titre protugais
	private String namePt="";
	//statu de l'�dition du sous-titre protugais
	private boolean finishedPt=false;
	//nom de la table contenant les sous titres
	private String tableName="";
	//tag de la langue des sous-titres originaux
	private String vo="";


	public String getNameEn() {
		return nameEn;
	}
	public void setNameEn(String nameEn) throws BeanException {
		if (nameEn.length() > 50) {
			throw new BeanException("Le nom est trop grand ! (50 caractères maximum)");
		}
		else {
			this.nameEn = nameEn;
		}
	}
	public boolean isFinishedEn() {
		return finishedEn;
	}
	public void setFinishedEn(boolean finishedEn) {
		this.finishedEn = finishedEn;
	}
	public String getNameFr() {

		return nameFr;
	}
	public void setNameFr(String nameFr) throws BeanException {
		if (nameFr.length() > 50) {
			throw new BeanException("Le nom est trop grand ! (50 caractères maximum)");
		}
		else {
			this.nameFr = nameFr;
		}
	}
	public boolean isFinishedFr() {
		return finishedFr;
	}
	public void setFinishedFr(boolean finishedFr) {
		this.finishedFr = finishedFr;
	}
	public String getNameAl() {
		return nameAl;
	}
	public void setNameAl(String nameAl) throws BeanException {
		if (nameAl.length() > 50) {
			throw new BeanException("Le nom est trop grand ! (50 caractères maximum)");
		}
		else {
			this.nameAl = nameAl;
		}
	}
	public boolean isFinishedAl() {
		return finishedAl;
	}
	public void setFinishedAl(boolean finishedAl) {
		this.finishedAl = finishedAl;
	}
	public String getNameEs() {
		return nameEs;
	}
	public void setNameEs(String nameEs) throws BeanException {
		if (nameEs.length() > 50) {
			throw new BeanException("Le nom est trop grand ! (50 caractères maximum)");
		}
		else {
			this.nameEs = nameEs;
		}
	}
	public boolean isFinishedEs() {
		return finishedEs;
	}
	public void setFinishedEs(boolean finishedEs) {
		this.finishedEs = finishedEs;
	}
	public String getNamePt() {
		return namePt;
	}
	public void setNamePt(String namePt) throws BeanException {
		if (namePt.length() > 50) {
			throw new BeanException("Le nom est trop grand ! (50 caractères maximum)");
		}
		
		else {
			this.namePt = namePt;
		}
	}
	public boolean isFinishedPt() {
		return finishedPt;
	}
	public void setFinishedPt(boolean finishedPt) {
		this.finishedPt = finishedPt;
	}
	public String getVo() {
		return vo;
	}
	public void setVo(String vo) throws BeanException {
		if(vo.equals("en") || vo.equals("fr") ||vo.equals("al")||vo.equals("es")||vo.equals("pt")){
		this.vo = vo;
		}else{
			throw new BeanException("Tag de langue inconnu");
		}
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	
	
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) throws BeanException {
		if (tableName.length() !=15) {
			throw new BeanException("Le nom de la table n'a pas la bonne longueur ! (15 caractères maximum)");
		}
		else {
			this.tableName = tableName.toLowerCase(); 
		}
	}

}
