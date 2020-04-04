package com.example.get_tvprogram_app.pojo;

import com.example.get_tvprogram_app.nhk_json.Following;
import com.example.get_tvprogram_app.nhk_json.Itemtop;
import com.example.get_tvprogram_app.nhk_json.Present;
import com.example.get_tvprogram_app.nhk_json.Previous;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class JsonStrParser {

    private String jsonStr;
    public static final String DATE_PATTERN_IN  = "yyyy-MM-dd'T'HH:mm:ssX";
    public static final String DATE_PATTERN_OUT = "HH時mm分";

    public JsonStrParser(String jsonStr) { this.jsonStr = jsonStr; }

    public TVProgramItem parsePreviousProgram() {
        Itemtop itemtop = new Gson().fromJson(jsonStr, Itemtop.class);
        Previous previous = itemtop.getNowonairList().getG1().getPrevious();

        return new TVProgramItem(
                previous.getTitle(),
                previous.getSubtitle(),
                this.convertTime(previous.getStartTime()),
                this.convertTime(previous.getEndTime())
        );
    }

    public TVProgramItem parsePresentProgram() {
        Itemtop itemtop = new Gson().fromJson(jsonStr, Itemtop.class);
        Present present = itemtop.getNowonairList().getG1().getPresent();
        return new TVProgramItem(
                present.getTitle(),
                present.getSubtitle(),
                this.convertTime(present.getStartTime()),
                this.convertTime(present.getEndTime())
        );
    }

    public TVProgramItem parseFollowingProgram() {
        Itemtop itemtop = new Gson().fromJson(jsonStr, Itemtop.class);
        Following following = itemtop.getNowonairList().getG1().getFollowing();
        return new TVProgramItem(
                following.getTitle(),
                following.getSubtitle(),
                this.convertTime(following.getStartTime()),
                this.convertTime(following.getEndTime())
        );
    }

    private String convertTime(String timeStr) {
        SimpleDateFormat sdf1 = new SimpleDateFormat(DATE_PATTERN_IN);
        SimpleDateFormat sdf2 = new SimpleDateFormat(DATE_PATTERN_OUT);
        try {
            return sdf2.format(sdf1.parse(timeStr));
        } catch (ParseException e) {
            return timeStr;
        }
    }

}
