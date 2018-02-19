/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package episodemover.dal;

import episodemover.be.Episode;
import episodemover.be.Show;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONObject;

/**
 *
 * @author Kasper Siig
 */
public class EpisodeDAO {
    
    private PropertiesDAO pDAO;

    public EpisodeDAO() throws DALException {
        this.pDAO = new PropertiesDAO();
    }
    
    public Show getShow(String search) throws DALException {
        try {
            String apiKey = pDAO.getProperty("tmdb_id");
            URL url = new URL("https://api.themoviedb.org/3/search/tv?api_key=" + apiKey + "&query=" + search);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));
            String json = br.readLine();
            JSONObject obj = new JSONObject(json);
            int numResults = obj.getInt("total_results");
            if (numResults != 0) {
                String title = obj.getJSONArray("results").getJSONObject(0).getString("name");
                String id = String.valueOf(obj.getJSONArray("results").getJSONObject(0).getInt("id"));
                Show show = new Show(title, id);
                return show;
            }
            return null;
        } catch (MalformedURLException ex) {
            throw new DALException(ex);
        } catch (ProtocolException ex) {
            throw new DALException(ex);
        } catch (IOException ex) {
            throw new DALException(ex);
        }
    }
    
    public List<Episode> getEpisodes() throws DALException {
        List<File> episodesInDir = getFilesInDir(pDAO.getProperty("curPath"));
        List<Episode> resultList = new ArrayList();
        for (File file : episodesInDir) {
            resultList.add(getEpisode(file));
        }
        return resultList;
    }
    
    public Episode getEpisode(File file) throws DALException {
        String showTitle = getName(file.getAbsolutePath());
        System.out.println(showTitle);
        Show show = getShow(showTitle.replace(" ", "+"));
        showTitle = show.getTitle();
        System.out.println(showTitle);
        
        return null;
    }
    
    public List<File> getFilesInDir(String path) {
        File directory = new File(path);

        List<File> resultList = new ArrayList<File>();

        // get all the files from a directory
        File[] fList = directory.listFiles();
        resultList.addAll(Arrays.asList(fList));
        for (File file : fList) {
            if (file.isDirectory()) {
                resultList.addAll(getFilesInDir(file.getAbsolutePath()));
            }
        }
        List<File> returnList = new ArrayList();
        for (File episode : resultList) {
            if (episode.getAbsolutePath().endsWith("mkv")) {
                returnList.add(episode);
            }
            if (episode.getAbsolutePath().endsWith("mp4")) {
                returnList.add(episode);
            }
            if (episode.getAbsolutePath().endsWith("avi")) {
                returnList.add(episode);
            }

        }
        return returnList;
    }
    
    private String getName(String filePath) {
        filePath = filePath.replaceAll("\\.", " ");
        filePath = filePath.replaceAll("\\S\\d.*$", "");
        filePath = filePath.replaceAll("^.*\\\\", "");
        return filePath;
    }

    private String getSeasonEpisode(String filePath) {
        filePath = filePath.toLowerCase();
        Pattern p = Pattern.compile("(s\\d\\de\\d\\d)");   // the pattern to search for
        Matcher m = p.matcher(filePath);
        m.find();
        String episode = m.group(1);
        return episode = episode.replaceAll("[^\\d]", "");
    }
    
}
