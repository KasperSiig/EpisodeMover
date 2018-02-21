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
    private String searchName;
    private String title;
    private String tmdbId;
    private String posterPath;

    public Show(String searchName, String title, String tmdbId, String posterPath) {
        this.searchName = searchName;
        this.title = title;
        this.tmdbId = tmdbId;
        this.posterPath = posterPath;
    }

    public String getSearchName() {
        return searchName;
    }

    public String getTitle() {
        return title;
    }

    public String getTmdbId() {
        return tmdbId;
    }

    public String getPosterPath() {
        return posterPath;
    }

    
    @Override
    public String toString() {
        return "Show{" + "title=" + title + ", tmdbId=" + tmdbId + '}';
    }

    
    
    
}
