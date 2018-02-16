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
public class DALException extends Exception {

    public DALException(String string) {
        super(string);
    }

    public DALException(String string, Throwable thrwbl) {
        super(string, thrwbl);
    }

    public DALException(Throwable thrwbl) {
        super(thrwbl);
    }
    
}
