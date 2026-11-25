package com.dzleague.app.model;

/** Un match affiché sur l'écran d'accueil. */
public class Match {
    public MatchStatus status;
    public Team home;
    public Team away;
    public int homeScore;
    public int awayScore;

    public String time;          // ex : "16:30"
    public String statusLabel;   // ex : "EN DIRECT • 78'", "TERMINÉ", "ÉCHAUFFEMENT • 17:00"
    public String period;        // ex : "2e MT" (match en direct)
    public String stadium;

    public String homeSub;       // ex : "1er au classement"
    public String awaySub;
    public String scorersHome;   // ex : "42' Benali, 68' Kaddour"
    public String scorersAway;

    public String footerLeft;    // ex : "Cote: 2.10 • 3.20 • 2.85"
    public String footerAction;  // ex : "Compos probables"
    public boolean actionHighlight;

    public boolean starred;
}
