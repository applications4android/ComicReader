package com.blogspot.applications4android.comicreader.comics;

import java.io.BufferedReader;
import java.io.IOException;
import com.blogspot.applications4android.comicreader.comictypes.RandomIndexedComic;
import com.blogspot.applications4android.comicreader.core.Strip;
import com.blogspot.applications4android.comicreader.exceptions.ComicLatestException;


public class AGirlandHerFed extends RandomIndexedComic {

	@Override
	protected String getFrontPageUrl() {
		return "https://agirlandherfed.com/";
	}

	@Override
	public String getComicWebPageUrl() {
		return "https://agirlandherfed.com/";
	}

	@Override
	protected int parseForLatestId(BufferedReader reader) throws IOException, ComicLatestException {
		String str;
		String final_str = null;
		while((str = reader.readLine()) != null) {
			int index1 = str.indexOf("img/strip/1-");
			if (index1 != -1) {
				final_str = str;
			}
		}
		if(final_str == null) {
			String msg = "Failed to get the latest id for "+this.getClass().getSimpleName();
			ComicLatestException e = new ComicLatestException(msg);
			throw e;
		}
		final_str = final_str.replaceAll(".*img/strip/1-","");
		final_str = final_str.replaceAll(".jpg.*","");
	   	int finalid = Integer.parseInt(final_str);
	   	return finalid;
	}

	@Override
	public String getStripUrlFromId(int num) {
		return "https://agirlandherfed.com/1." + num + ".html";
	}

	@Override
	protected int getIdFromStripUrl(String url) {
		String temp = url.replaceAll("https.*/1.", "");
		temp = temp.replaceAll(".html", "");
		return Integer.parseInt(temp);
	}

	@Override
	protected int parseForPrevId(String line, int def) {
		if(line == null) {
			return def;
		}
                return getIdFromStripUrl (line);
	}

	@Override
	protected int parseForNextId(String line, int def) {
		if(line == null) {
			return def;
		}
                return getIdFromStripUrl (line);
	}

        @Override
	protected boolean htmlNeeded() {
		return true;
	}

	@Override
	protected String parse(String url, BufferedReader reader, Strip strip)
			throws IOException {
		String str;
		String final_str = null;
		String final_title = null;
		while ((str = reader.readLine()) != null) {
			int index1 = str.indexOf("img/strip/1-");
			if (index1 != -1) {
				final_str = str;
				final_title = str;
			}
		}
		final_str = final_str.replaceAll(".*src=\"","");
		final_str = final_str.replaceAll("\".*","");
		final_title = final_title.replaceAll(".*img/strip/1-","");
		final_title = final_title.replaceAll(".jpg.*","");
		strip.setTitle("A Girl and Her Fed: 1."+final_title);
		strip.setText("-NA-");
		return "https://agirlandherfed.com/"+final_str;
	}


        @Override
	protected int getNextStripId(BufferedReader br, String url) {
          String str;
          try {
            while ((str = br.readLine()) != null) {
              int i = str.indexOf("next.png");
              if (i != -1) {
                int i2 = str.lastIndexOf ("href=\"1.", i);
                if (i2 != -1) {
                  i2 += 8;
                  int i3 = str.indexOf (".html", i2);
                  if (i3 != -1) {
                    return Integer.parseInt (str.substring (i2, i3));
                  }
                }
              }
            }
          }
          catch (Exception e) {
            e.printStackTrace();
          }
          return 0;
        }

        @Override
	protected int getPreviousStripId(BufferedReader br, String url) {
          String str;
          try {
            while ((str = br.readLine()) != null) {
              int i = str.indexOf("back.png");
              if (i != -1) {
                int i2 = str.lastIndexOf ("href=\"1.", i);
                if (i2 != -1) {
                  i2 += 8;
                  int i3 = str.indexOf (".html", i2);
                  if (i3 != -1) {
                    return Integer.parseInt (str.substring (i2, i3));
                  }
                }
              }
            }
          }
          catch (Exception e) {
            e.printStackTrace();
          }
          return 0;
        }

	@Override
	protected String getRandUrl() {
          // Not implemented.
          return getStripUrlFromId (1);
	}
}
