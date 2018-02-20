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
public class Episode {

    private String showTitle;
    private String title;
    private String season;
    private String episode;
    private String curPath;
    private String newPath;
    private String posterPath;

    public Episode(String showTitle, 
            String title, 
            String season, 
            String episode, 
            String curPath, 
            String newPath, 
            String posterPath) {
        this.showTitle = showTitle;
        this.title = title;
        this.season = season;
        this.episode = episode;
        this.curPath = curPath;
        this.newPath = newPath;
        this.posterPath = posterPath;
    }

    

    public String getShowTitle() {
        return showTitle;
    }

    public void setShowTitle(String showTitle) {
        this.showTitle = showTitle;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getEpisode() {
        return episode;
    }

    public void setEpisode(String episode) {
        this.episode = episode;
    }

    public String getCurPath() {
        return curPath;
    }

    public void setCurPath(String curPath) {
        this.curPath = curPath;
    }

    public String getNewPath() {
        return newPath;
    }

    public void setNewPath(String newPath) {
        this.newPath = newPath;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    @Override
    public String toString() {
        return "Episode{" + "title=" + title + ", season=" + season + ", episode=" + episode + ", curPath=" + curPath + ", newPath=" + newPath + ", posterPath=" + posterPath + '}';
    }

}
