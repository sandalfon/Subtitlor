package com.subtitlor.dao;

import com.subtitlor.beans.Subtitle;

public interface SubtitleDao {
 Subtitle generateSubtitleFromfile(String fileName);
  void writeString(Subtitle suntitle, String fileName);
  
}
