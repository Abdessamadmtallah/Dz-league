package com.dzleague.app.model;

import androidx.annotation.ColorRes;

import java.util.ArrayList;
import java.util.List;

/** Une compétition et ses matchs. */
public class League {
    public static final String REGION_TLEMCEN = "tlemcen";
    public static final String REGION_ORAN = "oran";
    public static final String REGION_ALGER = "alger";

    public final String name;
    public final String tag;            // ex : "JOURNÉE 19" ou "NOCTURNE"
    public final String region;
    @ColorRes public final int accentColor;
    public final boolean tagHighlighted; // true = étiquette orange (ex : nocturne)
    public final List<Match> matches = new ArrayList<>();

    public League(String name, String tag, String region,
                  @ColorRes int accentColor, boolean tagHighlighted) {
        this.name = name;
        this.tag = tag;
        this.region = region;
        this.accentColor = accentColor;
        this.tagHighlighted = tagHighlighted;
    }
}
