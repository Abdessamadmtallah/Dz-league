# DzLeague — Écran d'accueil (Java / Android Studio)

Application Android (Java, XML + RecyclerView) qui reproduit la maquette « DzLeague — Scores & Directs Algérie ».

## Lancer le projet
1. Ouvrir **Android Studio** (version récente : Koala / Ladybug / Meerkat ou plus).
2. `File > Open…` puis choisir le dossier **DzLeague** (celui qui contient `settings.gradle`).
3. Attendre la fin du *Gradle Sync* (la 1re fois, Android Studio télécharge Gradle 8.9 et les dépendances : connexion internet nécessaire).
   - Si Android Studio propose de mettre à jour le plugin Android Gradle ou le SDK, accepter.
4. Choisir un émulateur (Device Manager) ou brancher un téléphone en mode débogage USB.
5. Cliquer sur **Run ▶**.

## Structure
```
app/src/main/java/com/dzleague/app/
├── MainActivity.java        écran d'accueil (filtres, barre de navigation)
├── adapter/MatchAdapter.java  RecyclerView : dates, filtres, ligues, 3 styles de cartes
├── model/                   Match, Team, League, ListItem, MatchStatus
├── data/FakeData.java       données de démo (à remplacer par une API / base)
└── util/UiUtils.java        écussons, animation « live », conversions dp
app/src/main/res/
├── layout/                  activity_main + item_dates, item_chips, item_league,
│                            item_match_live, item_match, item_match_stream
├── drawable/                icônes vectorielles + fonds arrondis
├── color/ values/           couleurs de la maquette, thème sombre
```

## Fonctionnel
- Filtres : Tous / En direct / Tlemcen / Oran / Alger (compteurs calculés).
- Sélection du jour (visuelle pour l'instant), étoile favoris, boutons (Toast).
- Barre de navigation inférieure (seul « Accueil » est implémenté).

## Pistes pour la suite
- Remplacer `FakeData` par Retrofit + une API de scores, ou Room (SQLite).
- Ajouter les écrans Ligues, Calendrier, Favoris, Paramètres et le détail d'un match.
- Charger de vrais logos d'équipes (Glide) à la place des initiales.
