package com.sleepsoft.downloader.pojos;

/**
 * Created by Alejandro on 3/27/16.
 */
public class Series extends Item {

    private Integer episode;
    private Integer season;

    public Integer getSeason() {
        return season;
    }

    public void setSeason(Integer season) {
        this.season = season;
    }

    public Integer getEpisode() {

        return episode;
    }

    public void setEpisode(Integer episode) {
        this.episode = episode;
    }

    public Series(Integer episode, Integer season, String title, Integer year){
        super(title, year);
        this.episode=episode;
        this.season = season;
    }
    public Series(Integer episode, Integer season) {
        this.episode = episode;
        this.season = season;
    }
}
