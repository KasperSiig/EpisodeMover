/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package episodemover.dal;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kasper Siig
 */
public class PropertiesDAO {

    private Properties properties;

    public PropertiesDAO() throws DALException {
        try {
            this.properties = new Properties();
            properties.load(new FileInputStream("config.properties"));
        } catch (FileNotFoundException ex) {
            throw new DALException(ex);
        } catch (IOException ex) {
            throw new DALException(ex);
        }
    }

    public void setProperty(String key, String input) throws DALException {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream("config.properties");
            properties.setProperty(key, input);
            properties.store(out, null);
        } catch (FileNotFoundException ex) {
            throw new DALException(ex);
        } catch (IOException ex) {
            throw new DALException(ex);
        }
    }
    
    public String getProperty(String key) {
        return properties.getProperty(key);
    }

}
