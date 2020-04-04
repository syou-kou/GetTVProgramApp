package com.example.get_tvprogram_app.pojo;

public class TVProgramItem {

    private String title;
    private String subtitle;
    private String startTime;
    private String endTime;

    public TVProgramItem(String title, String subtitle, String startTime, String endTime) {
        this.title = title;
        this.subtitle = subtitle;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getSubtitle() { return subtitle; }
    public void setSubtitle(String subtitle) { this.subtitle = subtitle; }

    public String getStartTime() { return startTime; }
    public void setStartTime(String startTime) { this.startTime = startTime; }

    public String getEndTime() { return endTime; }
    public void setEndTime(String endTime) { this.endTime = endTime; }

}
