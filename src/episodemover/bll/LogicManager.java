/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package episodemover.bll;

import episodemover.be.Episode;
import episodemover.dal.DALException;
import episodemover.dal.DataManager;
import java.util.List;

/**
 *
 * @author Kasper Siig
 */
public class LogicManager {
    DataManager dm;

    public LogicManager() throws DALException {
        this.dm = new DataManager();
    }
    
    /**
     * Returns episodes List
     * 
     * @return 
     */
    public List<Episode> getEpisodes() {
        return dm.getEpisodes();
    }
    
    /**
     * Returns queued episodes.
     * 
     * @return 
     */
    public List<Episode> getQueuedEpisodes() {
        return dm.getQueuedEpisodes();
    }
    
    /**
     * Returns finished episodes.
     * 
     * @return 
     */
    public List<Episode> getFinishedEpisodes() {
        return dm.getFinishedEpisodes();
    }
    
    /**
     * Sets property in config file
     * 
     * @param key
     * @param input
     * @throws DALException 
     */
    public void setProperty(String key, String input) throws DALException {
        dm.setProperty(key, input);
    }
    
    /**
     * Returns property from config file.
     * 
     * @param key
     * @return 
     */
    public String getProperty(String key) {
        return dm.getProperty(key);
    }
}
