package com.dzleague.app.model;

/** Élément de la liste de l'écran d'accueil (barre de dates, filtres, ligue ou match). */
public class ListItem {
    public static final int TYPE_DATES = 0;
    public static final int TYPE_CHIPS = 1;
    public static final int TYPE_LEAGUE = 2;
    public static final int TYPE_MATCH_LIVE = 3;
    public static final int TYPE_MATCH = 4;
    public static final int TYPE_MATCH_STREAM = 5;

    public final int type;
    public final League league;
    public final Match match;

    private ListItem(int type, League league, Match match) {
        this.type = type;
        this.league = league;
        this.match = match;
    }

    public static ListItem dates() {
        return new ListItem(TYPE_DATES, null, null);
    }

    public static ListItem chips() {
        return new ListItem(TYPE_CHIPS, null, null);
    }

    public static ListItem league(League league) {
        return new ListItem(TYPE_LEAGUE, league, null);
    }

    public static ListItem match(Match match) {
        int type;
        switch (match.status) {
            case LIVE:
                type = TYPE_MATCH_LIVE;
                break;
            case STREAM:
                type = TYPE_MATCH_STREAM;
                break;
            default:
                type = TYPE_MATCH;
                break;
        }
        return new ListItem(type, null, match);
    }
}
