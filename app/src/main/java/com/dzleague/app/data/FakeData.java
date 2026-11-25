package com.dzleague.app.data;

import com.dzleague.app.R;
import com.dzleague.app.model.League;
import com.dzleague.app.model.Match;
import com.dzleague.app.model.MatchStatus;
import com.dzleague.app.model.Team;

import java.util.ArrayList;
import java.util.List;

/** Données de démonstration (reprises de la maquette). À remplacer plus tard par une API / base de données. */
public final class FakeData {

    private FakeData() {}

    private static Team team(String name, String initials, int textColor, int strokeColor, int fillColor) {
        return new Team(name, initials, textColor, strokeColor, fillColor);
    }

    public static List<League> getLeagues() {
        List<League> leagues = new ArrayList<>();

        // ---------- Ligue Inter-Communes Tlemcen Ouest ----------
        League l1 = new League("Ligue Inter-Communes Tlemcen Ouest", "Journée 19",
                League.REGION_TLEMCEN, R.color.primary, false);

        Match live = new Match();
        live.status = MatchStatus.LIVE;
        live.statusLabel = "EN DIRECT • 78'";
        live.period = "2e MT";
        live.stadium = "Stade Akid Lotfi";
        live.home = team("IRB Maghnia", "IRB", R.color.primary, R.color.primary_40, R.color.surface_container_highest);
        live.away = team("WA Tlemcen", "WAT", R.color.white, R.color.white_10, R.color.surface_container_highest);
        live.homeScore = 2;
        live.awayScore = 1;
        live.homeSub = "1er au classement";
        live.awaySub = "3e au classement";
        live.scorersHome = "42' Benali, 68' Kaddour";
        live.scorersAway = "56' Belhadj";
        live.footerAction = "Détails du match";
        l1.matches.add(live);

        Match m2 = new Match();
        m2.status = MatchStatus.UPCOMING;
        m2.time = "16:30";
        m2.stadium = "Stade Municipal Hennaya";
        m2.home = team("CRB Hennaya", "CRB", R.color.white, R.color.white_10, R.color.surface_container);
        m2.away = team("US Remchi", "USR", R.color.primary, R.color.white_10, R.color.surface_container_highest);
        m2.footerLeft = "Cote: 2.10 • 3.20 • 2.85";
        m2.footerAction = "Compos probables";
        m2.actionHighlight = true;
        l1.matches.add(m2);

        Match m3 = new Match();
        m3.status = MatchStatus.UPCOMING;
        m3.time = "18:00";
        m3.stadium = "Stade Sidi Said";
        m3.home = team("JS Sidi Said", "JSS", R.color.white, R.color.white_10, R.color.surface_container);
        m3.away = team("ES Sebdou", "ESS", R.color.secondary, R.color.white_10, R.color.surface_container_highest);
        m3.footerLeft = "Arbitre: M. Bensalem";
        m3.footerAction = "Historique H2H";
        l1.matches.add(m3);
        leagues.add(l1);

        // ---------- Championnat Honneur Wilaya d'Oran ----------
        League l2 = new League("Championnat Honneur Wilaya d'Oran", "Journée 14",
                League.REGION_ORAN, R.color.secondary, false);

        Match m4 = new Match();
        m4.status = MatchStatus.FINISHED;
        m4.statusLabel = "TERMINÉ";
        m4.time = "15:00";
        m4.stadium = "Stade Said Ahmed";
        m4.home = team("CC Sig", "CCS", R.color.white, R.color.white_10, R.color.crest_red);
        m4.away = team("RC Oran", "RCO", R.color.on_surface_variant, R.color.white_10, R.color.surface_container_highest);
        m4.homeScore = 1;
        m4.awayScore = 0;
        m4.footerLeft = "Buteur: 64' Sahraoui (CC Sig)";
        m4.footerAction = "Résumé du match";
        l2.matches.add(m4);

        Match m5 = new Match();
        m5.status = MatchStatus.WARMUP;
        m5.statusLabel = "ÉCHAUFFEMENT • 17:00";
        m5.stadium = "Stade Communal Misserghin";
        m5.home = team("JS Misserghin", "JSM", R.color.primary, R.color.primary_30, R.color.surface_container_highest);
        m5.away = team("USM Sénia", "UMS", R.color.white, R.color.white_10, R.color.surface_container_highest);
        m5.footerLeft = "Coups d'envoi dans 15 min";
        m5.footerAction = "Feuille de match";
        m5.actionHighlight = true;
        l2.matches.add(m5);
        leagues.add(l2);

        // ---------- Tournoi Inter-Quartiers Alger Centre ----------
        League l3 = new League("Tournoi Inter-Quartiers Alger Centre", "Nocturne",
                League.REGION_ALGER, R.color.tertiary, true);

        Match m6 = new Match();
        m6.status = MatchStatus.STREAM;
        m6.time = "20:30 • Coup d'envoi";
        m6.stadium = "Stade Ouaguenouni";
        m6.home = team("Casbah FC", "CFC", R.color.tertiary, R.color.tertiary_40, R.color.surface_container_highest);
        m6.away = team("B.E.O Stars", "BEO", R.color.primary, R.color.primary_40, R.color.surface_container_highest);
        m6.homeSub = "Alger Centre";
        m6.awaySub = "Bab El Oued";
        m6.footerLeft = "Diffusion HD disponible";
        m6.footerAction = "Activer Rappel";
        l3.matches.add(m6);
        leagues.add(l3);

        return leagues;
    }
}
