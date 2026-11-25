package com.dzleague.app.model;

import androidx.annotation.ColorRes;

/** Équipe : nom, initiales affichées dans l'écusson et couleurs de l'écusson. */
public class Team {
    public final String name;
    public final String initials;
    @ColorRes public final int textColor;
    @ColorRes public final int strokeColor;
    @ColorRes public final int fillColor;

    public Team(String name, String initials,
                @ColorRes int textColor, @ColorRes int strokeColor, @ColorRes int fillColor) {
        this.name = name;
        this.initials = initials;
        this.textColor = textColor;
        this.strokeColor = strokeColor;
        this.fillColor = fillColor;
    }
}
