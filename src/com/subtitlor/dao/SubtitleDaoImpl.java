package com.subtitlor.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.subtitlor.beans.Subtitle;
import com.subtitlor.beans.SubtitleContent;

public class SubtitleDaoImpl implements SubtitleDao {
	private DaoFactory daoFactory;

	SubtitleDaoImpl(DaoFactory daoFactory)  throws DaoException {
		this.daoFactory = daoFactory;
	}

	public Subtitle generateSubtitleFromfile( String fileName){
		BufferedReader br=null;
		int stage=0;
		int index=-1;
		Subtitle subtitle =new Subtitle();
		List<Integer> ids = new ArrayList<Integer>();
		Map<Integer, String> timeStart = new HashMap<Integer, String>();
		Map<Integer, String> timeStop = new HashMap<Integer, String>();
		Map<Integer, String> linesContent = new HashMap<Integer, String>();
		try {
			br = new BufferedReader(new FileReader(fileName));
			String line;
			String tmpLineContent="";
			while ((line = br.readLine()) != null) {
				switch(stage){
				case 0:

					index=Integer.parseInt(line);
					ids.add(index);
					stage=1;
					break;
				case 1:
					String[] times=line.split(" --> ");
					timeStart.put(index, times[0]);
					timeStop.put(index, times[1]);
					stage=2;
					break;
				case 2:
					if(line.trim().isEmpty()){
						
						linesContent.put(index, tmpLineContent.substring(1, tmpLineContent.length()));
						tmpLineContent="";
						stage=0;
					}else{
						tmpLineContent=tmpLineContent+'\n'+line;
					}

					break;
				}

			}
			//ajout du dernier text des sous-titres.
			linesContent.put(index, tmpLineContent.substring(1, tmpLineContent.length()));
			subtitle.setIds(ids);
			subtitle.setLinesContent(linesContent);
			subtitle.setTimeStart(timeStart);
			subtitle.setTimeStop(timeStop);


		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				// fermer le buffer 
				br.close();

			} catch (Exception e) {
			}
		}
		return(subtitle);
	}



	public void writeString(Subtitle subtitle, String fileName){
		BufferedWriter writer = null;
		try {
			File subFile = new File(fileName);
			System.out.println(subFile.getCanonicalPath());
			writer = new BufferedWriter(new FileWriter(subFile));
			writer.write(subtitle.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// fermer le buffer 
				writer.close();
			} catch (Exception e) {
			}
		}
	}

	@Override
	public Subtitle getSubtitleFromSubtitleContent(SubtitleContent subtitleContent, String language) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
