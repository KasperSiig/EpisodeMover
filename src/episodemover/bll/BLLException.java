/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package episodemover.bll;

/**
 *
 * @author Kasper Siig
 */
public class BLLException extends Exception {

    public BLLException(String string) {
        super(string);
    }

    public BLLException(String string, Throwable thrwbl) {
        super(string, thrwbl);
    }

    public BLLException(Throwable thrwbl) {
        super(thrwbl);
    }
    
}
