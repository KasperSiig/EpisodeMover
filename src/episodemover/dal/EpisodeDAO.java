/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package episodemover.dal;

import episodemover.be.Show;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
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
                System.out.println(show);
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
    
}
