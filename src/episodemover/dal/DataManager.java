/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package episodemover.dal;

/**
 *
 * @author Kasper Siig
 */
public class DataManager {
    private EpisodeDAO eDAO;
    private PropertiesDAO pDAO;

    public DataManager() throws DALException {
        this.pDAO = new PropertiesDAO();
        this.eDAO = new EpisodeDAO();
    }
}
