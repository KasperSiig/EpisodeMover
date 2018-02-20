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
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

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
        List<Show> shows = readShowFromXML();
        Show showRtn = null;
        for (Show show : shows) {
            if (show.getTitle().equals(search)) {
                showRtn = show;
            }
        }
        if (showRtn != null) {
            return showRtn;
        } else {
            try {
                String apiKey = pDAO.getProperty("tmdb_id");
                URL url = new URL("https://api.themoviedb.org/3/search/tv?api_key=" + apiKey + "&query=" + search);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                if (conn.getResponseCode() == 200) {

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
                        addToXML(search, id);
                        return show;
                    }
                }
            } catch (MalformedURLException ex) {
                throw new DALException(ex);
            } catch (ProtocolException ex) {
                throw new DALException(ex);
            } catch (IOException ex) {
                throw new DALException(ex);
            }
            return new Show("null", "null");
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
        try {
            String showTitle = getName(file.getName());
            Show show = getShow(showTitle.trim().replace(" ", "+"));
            String season = getSeasonEpisode(file.getAbsolutePath()).substring(0, 2);
            String episode = getSeasonEpisode(file.getAbsolutePath()).substring(2, 4);
            URL url = new URL("https://api.themoviedb.org/3/tv/" + show.getTmdbId() + "/season/" + season + "/episode/" + episode + "?api_key=6e60833d0f6349beaa9768b8eb6f4d7e");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            if (conn.getResponseCode() == 200) {
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        (conn.getInputStream())));
                String json = br.readLine();
                JSONObject obj = new JSONObject(json);
                String title = obj.getString("name");
                String curPath = file.getAbsolutePath();
                String newPath = getNewPath(show.getTitle().trim(), season, title, file.getAbsolutePath());
                String posterPath = null;
                return new Episode(show.getTitle(), title, season, episode, curPath, newPath, posterPath);

            } else {
                return new Episode(null, null, null, null, file.getAbsolutePath(), null, null);
            }
        } catch (MalformedURLException ex) {
            throw new DALException(ex);
        } catch (ProtocolException ex) {
            throw new DALException(ex);
        } catch (IOException ex) {
            throw new DALException(ex);
        }
    }

    public List<File> getFilesInDir(String path) {
        File directory = new File(path);

        List<File> resultList = new ArrayList<File>();

        // get all the files from a directory
        File[] fList = directory.listFiles();
        if (fList == null) {
            return resultList;
        }
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
        try {
            filePath = filePath.toLowerCase();
            Pattern p = Pattern.compile("(s\\d\\de\\d\\d)");   // the pattern to search for
            Matcher m = p.matcher(filePath);
            m.find();
            String episode = m.group(1);

            return episode = episode.replaceAll("[^\\d]", "");
        } catch (IllegalStateException ex) {
            return "0000";
        }
    }

    private String getNewPath(String showTitle, String season, String title, String filePath) {
        String extension = "";
        int i = filePath.lastIndexOf('.');
        if (i > 0) {
            extension = filePath.substring(i + 1);
        }
        season = season.startsWith("0") ? season.substring(1, 2) : season;
        return pDAO.getProperty("newPath") + showTitle + "\\Season " + season + "\\" + title + "." + extension;
    }

    public void moveFile(Episode e) {
        try {
            Path curPath = FileSystems.getDefault().getPath(e.getCurPath());
            Path newPath = FileSystems.getDefault().getPath(e.getNewPath());
            Files.move(curPath, newPath, REPLACE_EXISTING);
        } catch (IOException ex) {
            Logger.getLogger(EpisodeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addToXML(String showTitle, String tmdbId) throws DALException {
        try {
            System.out.println(showTitle);
            String filePath = "src/episodemover/res/xml/shows.xml";
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder;

            documentBuilder = documentBuilderFactory.newDocumentBuilder();

            Document document;

            document = documentBuilder.parse(filePath);

            Element root = document.getDocumentElement();

            Element show = document.createElement("show");

            Element showTitleEl = document.createElement("showTitle");
            showTitleEl.appendChild(document.createTextNode(showTitle));
            show.appendChild(showTitleEl);

            Element tmdbIdEl = document.createElement("tmdbId");
            tmdbIdEl.appendChild(document.createTextNode(tmdbId));
            show.appendChild(tmdbIdEl);

            root.appendChild(show);

            DOMSource source = new DOMSource(document);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer;
            try {
                transformer = transformerFactory.newTransformer();
            } catch (TransformerConfigurationException ex) {
                throw new DALException(ex);
            }
            StreamResult result = new StreamResult(filePath);
            try {
                transformer.transform(source, result);
            } catch (TransformerException ex) {
                throw new DALException(ex);
            }
        } catch (SAXException | IOException | ParserConfigurationException ex) {
            throw new DALException(ex);
        }
    }

    private List<Show> readShowFromXML() throws DALException {
        try {
            List<Show> shows = new ArrayList();

            String filePath = "src/episodemover/res/xml/shows.xml";
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(filePath);
            document.getDocumentElement().normalize();

            NodeList nList = document.getElementsByTagName("show");

            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);
                Element e = (Element) nNode;
                String showTitle = e.getElementsByTagName("showTitle").item(0).getTextContent();
                String tmdbId = e.getElementsByTagName("tmdbId").item(0).getTextContent();
                Show show = new Show(showTitle, tmdbId);
                shows.add(show);
            }

            return shows;
        } catch (SAXException | IOException | ParserConfigurationException ex) {
            throw new DALException(ex);
        }
    }

}
