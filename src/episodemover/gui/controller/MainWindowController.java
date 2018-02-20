/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package episodemover.gui.controller;

import episodemover.be.Episode;
import episodemover.dal.DALException;
import episodemover.dal.EpisodeDAO;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 *
 * @author Kasper Siig
 */
public class MainWindowController implements Initializable {
    
    @FXML
    private Label label;
    private EpisodeDAO eDAO = null;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        List<Episode> fin = eDAO.getFinishedEpisodes();
        List<Episode> q = eDAO.getQueuedEpisodes();
        System.out.println("Queued Episodes: \n");
        for (Episode episode : q) {
            System.out.println(episode.getNewPath());
        }
        System.out.println("");
        System.out.println("");
        System.out.println("Finished Episodes: \n");
        for (Episode episode : fin) {
            System.out.println(episode.getNewPath());
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            eDAO = new EpisodeDAO();
        } catch (DALException ex) {
            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            List<Episode> ep = eDAO.getEpisodes();
            for (Episode episode : ep) {
//                System.out.println(episode);
                eDAO.moveFile(episode);
            }
            
        } catch (DALException ex) {
            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
}
