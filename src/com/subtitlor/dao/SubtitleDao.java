package com.subtitlor.dao;

import com.subtitlor.beans.Subtitle;
import com.subtitlor.beans.SubtitleContent;

public interface SubtitleDao {
 Subtitle generateSubtitleFromfile(String fileName);
  void writeString(Subtitle suntitle, String fileName);
  Subtitle getSubtitleFromSubtitleContent(SubtitleContent subtitleContent, String language);
  
}
