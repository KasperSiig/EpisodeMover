/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package episodemover.be;

/**
 *
 * @author Kasper Siig
 */
public class Show {
    private String title;
    private String tmdbId;

    public Show(String title, String tmdbId) {
        this.title = title;
        this.tmdbId = tmdbId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTmdbId() {
        return tmdbId;
    }

    public void setTmdbId(String tmdbId) {
        this.tmdbId = tmdbId;
    }
    
    

    @Override
    public String toString() {
        return "Show{" + "title=" + title + ", tmdbId=" + tmdbId + '}';
    }

    
    
    
}
