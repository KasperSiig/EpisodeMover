/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package episodemover.dal;

import episodemover.be.Episode;
import java.util.List;

/**
 *
 * @author Kasper Siig
 */
public class DataManager {
    private EpisodeDAO eDAO;
    private PropertiesDAO pDAO;

    /**
     * Constructor for DataManager
     * 
     * @throws DALException 
     */
    public DataManager() throws DALException {
        this.pDAO = new PropertiesDAO();
        this.eDAO = new EpisodeDAO();
    }
    
    /**
     * Returns episodes List
     * 
     * @return 
     */
    public List<Episode> getEpisodes() {
        return eDAO.getEpisodes();
    }
    
    /**
     * Returns queued episodes.
     * 
     * @return 
     */
    public List<Episode> getQueuedEpisodes() {
        return eDAO.getQueuedEpisodes();
    }
    
    /**
     * Returns finished episodes.
     * 
     * @return 
     */
    public List<Episode> getFinishedEpisodes() {
        return eDAO.getFinishedEpisodes();
    }
    
    /**
     * Sets property in config file
     * 
     * @param key
     * @param input
     * @throws DALException 
     */
    public void setProperty(String key, String input) throws DALException {
        pDAO.setProperty(key, input);
    }
    
    /**
     * Returns property from config file.
     * 
     * @param key
     * @return 
     */
    public String getProperty(String key) {
        return pDAO.getProperty(key);
    }
}
