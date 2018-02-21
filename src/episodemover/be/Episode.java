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

    @Override
    public String toString() {
        return "Episode{" + "title=" + title + ", season=" + season + ", episode=" + episode + ", curPath=" + curPath + ", newPath=" + newPath + ", posterPath=" + posterPath + '}';
    }

    /**
     * Returns showTitle
     * 
     * @return 
     */
    public String getShowTitle() {
        return showTitle;
    }

    /**
     * Returns title
     * 
     * @return 
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns season
     * 
     * @return 
     */
    public String getSeason() {
        return season;
    }

    /**
     * Returns episode
     * 
     * @return 
     */
    public String getEpisode() {
        return episode;
    }

    /**
     * Returns current path
     * 
     * @return 
     */
    public String getCurPath() {
        return curPath;
    }

    /**
     * Returns new path
     * 
     * @return 
     */
    public String getNewPath() {
        return newPath;
    }

    /**
     * Returns poster path
     * 
     * @return 
     */
    public String getPosterPath() {
        return posterPath;
    }

}
