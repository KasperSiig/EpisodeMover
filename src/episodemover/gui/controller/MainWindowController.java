/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package episodemover.gui.controller;

import episodemover.be.Episode;
import episodemover.bll.LogicManager;
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

    @FXML
    private void handleButtonAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            LogicManager logic = new LogicManager();
            List<Episode> ep = logic.getEpisodes();
            for (Episode episode : ep) {
                System.out.println(episode);
            }
        } catch (DALException ex) {
            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
