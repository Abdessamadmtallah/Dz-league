package com.dzleague.app.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.dzleague.app.R;
import com.dzleague.app.model.League;
import com.dzleague.app.model.ListItem;
import com.dzleague.app.model.Match;
import com.dzleague.app.model.MatchStatus;
import com.dzleague.app.util.UiUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Adaptateur de l'écran d'accueil : barre de dates, filtres, en-têtes de ligue
 * et trois styles de cartes de match (en direct, standard, streaming).
 */
public class MatchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int FILTER_ALL = 0;
    public static final int FILTER_LIVE = 1;
    public static final int FILTER_TLEMCEN = 2;
    public static final int FILTER_ORAN = 3;
    public static final int FILTER_ALGER = 4;

    public interface Listener {
        void onDaySelected(int dayIndex);

        void onFilterSelected(int filter);

        void onMatchClicked(Match match);

        void onActionClicked(Match match, String action);
    }

    private final List<ListItem> items = new ArrayList<>();
    private final int[] counts = new int[5];
    private final Listener listener;
    private int selectedDay = 0;
    private int selectedFilter = FILTER_ALL;

    public MatchAdapter(Listener listener) {
        this.listener = listener;
    }

    public void setItems(List<ListItem> newItems, int[] newCounts, int filter) {
        items.clear();
        items.addAll(newItems);
        System.arraycopy(newCounts, 0, counts, 0, counts.length);
        selectedFilter = filter;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).type;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case ListItem.TYPE_DATES:
                return new DatesHolder(inflater.inflate(R.layout.item_dates, parent, false));
            case ListItem.TYPE_CHIPS:
                return new ChipsHolder(inflater.inflate(R.layout.item_chips, parent, false));
            case ListItem.TYPE_LEAGUE:
                return new LeagueHolder(inflater.inflate(R.layout.item_league, parent, false));
            case ListItem.TYPE_MATCH_LIVE:
                return new LiveHolder(inflater.inflate(R.layout.item_match_live, parent, false));
            case ListItem.TYPE_MATCH_STREAM:
                return new StreamHolder(inflater.inflate(R.layout.item_match_stream, parent, false));
            default:
                return new StandardHolder(inflater.inflate(R.layout.item_match, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ListItem item = items.get(position);
        if (holder instanceof DatesHolder) {
            ((DatesHolder) holder).refresh();
        } else if (holder instanceof ChipsHolder) {
            ((ChipsHolder) holder).bind();
        } else if (holder instanceof LeagueHolder) {
            ((LeagueHolder) holder).bind(item.league);
        } else if (holder instanceof LiveHolder) {
            ((LiveHolder) holder).bind(item.match);
        } else if (holder instanceof StreamHolder) {
            ((StreamHolder) holder).bind(item.match);
        } else if (holder instanceof StandardHolder) {
            ((StandardHolder) holder).bind(item.match);
        }
    }

    // ------------------------------------------------------------------
    // Barre de dates
    // ------------------------------------------------------------------
    private class DatesHolder extends RecyclerView.ViewHolder {
        private final View[] tabs = new View[3];

        DatesHolder(View view) {
            super(view);
            tabs[0] = view.findViewById(R.id.tabToday);
            tabs[1] = view.findViewById(R.id.tabTomorrow);
            tabs[2] = view.findViewById(R.id.tabSaturday);
            for (int i = 0; i < tabs.length; i++) {
                final int index = i;
                tabs[i].setOnClickListener(v -> {
                    selectedDay = index;
                    refresh();
                    listener.onDaySelected(index);
                });
            }
            view.findViewById(R.id.btnCalendar).setOnClickListener(
                    v -> listener.onActionClicked(null, "calendar"));
        }

        void refresh() {
            for (int i = 0; i < tabs.length; i++) {
                tabs[i].setSelected(i == selectedDay);
            }
        }
    }

    // ------------------------------------------------------------------
    // Filtres
    // ------------------------------------------------------------------
    private class ChipsHolder extends RecyclerView.ViewHolder {
        private final View[] chips = new View[5];
        private final TextView[] labels = new TextView[5];
        private final int[] formats = {
                R.string.chip_all, R.string.chip_live, R.string.chip_tlemcen,
                R.string.chip_oran, R.string.chip_alger
        };

        ChipsHolder(View view) {
            super(view);
            chips[0] = view.findViewById(R.id.chipAll);
            chips[1] = view.findViewById(R.id.chipLive);
            chips[2] = view.findViewById(R.id.chipTlemcen);
            chips[3] = view.findViewById(R.id.chipOran);
            chips[4] = view.findViewById(R.id.chipAlger);
            labels[0] = view.findViewById(R.id.tvChipAll);
            labels[1] = view.findViewById(R.id.tvChipLive);
            labels[2] = view.findViewById(R.id.tvChipTlemcen);
            labels[3] = view.findViewById(R.id.tvChipOran);
            labels[4] = view.findViewById(R.id.tvChipAlger);
            for (int i = 0; i < chips.length; i++) {
                final int filter = i;
                chips[i].setOnClickListener(v -> listener.onFilterSelected(filter));
            }
        }

        void bind() {
            Context context = itemView.getContext();
            for (int i = 0; i < chips.length; i++) {
                labels[i].setText(context.getString(formats[i], counts[i]));
                chips[i].setSelected(i == selectedFilter);
            }
        }
    }

    // ------------------------------------------------------------------
    // En-tête de ligue
    // ------------------------------------------------------------------
    private static class LeagueHolder extends RecyclerView.ViewHolder {
        private final View accent;
        private final TextView name;
        private final TextView tag;

        LeagueHolder(View view) {
            super(view);
            accent = view.findViewById(R.id.viewAccent);
            name = view.findViewById(R.id.tvLeagueName);
            tag = view.findViewById(R.id.tvLeagueTag);
        }

        void bind(League league) {
            Context context = itemView.getContext();
            name.setText(league.name);
            tag.setText(league.tag);
            int accentColor = ContextCompat.getColor(context, league.accentColor);
            ViewCompat.setBackgroundTintList(accent, ColorStateList.valueOf(accentColor));
            if (league.tagHighlighted) {
                ViewCompat.setBackgroundTintList(tag, ColorStateList.valueOf(0x26FCA416));
                tag.setTextColor(ContextCompat.getColor(context, R.color.tertiary));
            } else {
                ViewCompat.setBackgroundTintList(tag, ColorStateList.valueOf(
                        ContextCompat.getColor(context, R.color.surface_container_highest)));
                tag.setTextColor(ContextCompat.getColor(context, R.color.on_surface_variant));
            }
        }
    }

    // ------------------------------------------------------------------
    // Carte : match en direct
    // ------------------------------------------------------------------
    private class LiveHolder extends RecyclerView.ViewHolder {
        private final TextView status, period, stadium, homeCrest, homeName, homeSub;
        private final TextView awayCrest, awayName, awaySub, scoreHome, scoreAway;
        private final TextView scorersHome, scorersAway;
        private final View btnDetails;

        LiveHolder(View view) {
            super(view);
            status = view.findViewById(R.id.tvStatus);
            period = view.findViewById(R.id.tvPeriod);
            stadium = view.findViewById(R.id.tvStadium);
            homeCrest = view.findViewById(R.id.tvHomeCrest);
            homeName = view.findViewById(R.id.tvHomeName);
            homeSub = view.findViewById(R.id.tvHomeSub);
            awayCrest = view.findViewById(R.id.tvAwayCrest);
            awayName = view.findViewById(R.id.tvAwayName);
            awaySub = view.findViewById(R.id.tvAwaySub);
            scoreHome = view.findViewById(R.id.tvScoreHome);
            scoreAway = view.findViewById(R.id.tvScoreAway);
            scorersHome = view.findViewById(R.id.tvScorersHome);
            scorersAway = view.findViewById(R.id.tvScorersAway);
            btnDetails = view.findViewById(R.id.btnDetails);
            UiUtils.pulse(view.findViewById(R.id.dotLive));
            UiUtils.pulse(view.findViewById(R.id.dotComments));
        }

        void bind(Match m) {
            status.setText(m.statusLabel);
            period.setText(m.period);
            stadium.setText(m.stadium);
            UiUtils.applyCrest(homeCrest, m.home);
            UiUtils.applyCrest(awayCrest, m.away);
            homeName.setText(m.home.name);
            awayName.setText(m.away.name);
            homeSub.setText(m.homeSub);
            awaySub.setText(m.awaySub);
            scoreHome.setText(String.valueOf(m.homeScore));
            scoreAway.setText(String.valueOf(m.awayScore));
            scorersHome.setText(m.scorersHome);
            scorersAway.setText(m.scorersAway);
            itemView.setOnClickListener(v -> listener.onMatchClicked(m));
            btnDetails.setOnClickListener(v -> listener.onActionClicked(m, m.footerAction));
        }
    }

    // ------------------------------------------------------------------
    // Carte : match standard (à venir / terminé / échauffement)
    // ------------------------------------------------------------------
    private class StandardHolder extends RecyclerView.ViewHolder {
        private final View dotWarm, layoutScore, btnAction;
        private final ImageView ivClock, ivStar, ivChevron;
        private final TextView badge, time, stadium, homeCrest, homeName, vs;
        private final TextView awayCrest, awayName, scoreHome, scoreAway, footerLeft, action;

        StandardHolder(View view) {
            super(view);
            dotWarm = view.findViewById(R.id.dotWarm);
            layoutScore = view.findViewById(R.id.layoutScore);
            btnAction = view.findViewById(R.id.btnAction);
            ivClock = view.findViewById(R.id.ivClock);
            ivStar = view.findViewById(R.id.ivStar);
            ivChevron = view.findViewById(R.id.ivActionChevron);
            badge = view.findViewById(R.id.tvBadge);
            time = view.findViewById(R.id.tvTime);
            stadium = view.findViewById(R.id.tvStadium);
            homeCrest = view.findViewById(R.id.tvHomeCrest);
            homeName = view.findViewById(R.id.tvHomeName);
            vs = view.findViewById(R.id.tvVs);
            awayCrest = view.findViewById(R.id.tvAwayCrest);
            awayName = view.findViewById(R.id.tvAwayName);
            scoreHome = view.findViewById(R.id.tvScoreHome);
            scoreAway = view.findViewById(R.id.tvScoreAway);
            footerLeft = view.findViewById(R.id.tvFooterLeft);
            action = view.findViewById(R.id.tvAction);
            UiUtils.pulse(dotWarm);
        }

        void bind(Match m) {
            Context c = itemView.getContext();
            int white = ContextCompat.getColor(c, R.color.white);
            int variant = ContextCompat.getColor(c, R.color.on_surface_variant);
            int primary = ContextCompat.getColor(c, R.color.primary);
            int tertiary = ContextCompat.getColor(c, R.color.tertiary);

            boolean upcoming = m.status == MatchStatus.UPCOMING;
            boolean finished = m.status == MatchStatus.FINISHED;
            boolean warmup = m.status == MatchStatus.WARMUP;

            // ligne du haut
            dotWarm.setVisibility(warmup ? View.VISIBLE : View.GONE);
            ivClock.setVisibility(upcoming ? View.VISIBLE : View.GONE);
            if (m.statusLabel != null) {
                badge.setVisibility(View.VISIBLE);
                badge.setText(m.statusLabel);
                if (warmup) {
                    badge.setBackgroundResource(R.drawable.bg_badge_warm);
                    badge.setTextColor(tertiary);
                } else {
                    badge.setBackgroundResource(R.drawable.bg_badge_neutral);
                    badge.setTextColor(ContextCompat.getColor(c, R.color.on_surface));
                }
            } else {
                badge.setVisibility(View.GONE);
            }
            if (m.time != null && !warmup) {
                time.setVisibility(View.VISIBLE);
                time.setText(m.time);
                time.setTextColor(upcoming ? white : variant);
                time.setTypeface(Typeface.DEFAULT, upcoming ? Typeface.BOLD : Typeface.NORMAL);
            } else {
                time.setVisibility(View.GONE);
            }
            stadium.setText(m.stadium);
            ivStar.setSelected(m.starred);
            ivStar.setOnClickListener(v -> {
                m.starred = !m.starred;
                ivStar.setSelected(m.starred);
            });

            // équipes
            UiUtils.applyCrest(homeCrest, m.home);
            UiUtils.applyCrest(awayCrest, m.away);
            homeName.setText(m.home.name);
            awayName.setText(m.away.name);

            if (finished) {
                vs.setVisibility(View.GONE);
                layoutScore.setVisibility(View.VISIBLE);
                scoreHome.setText(String.valueOf(m.homeScore));
                scoreAway.setText(String.valueOf(m.awayScore));
                boolean homeWins = m.homeScore > m.awayScore;
                boolean awayWins = m.awayScore > m.homeScore;
                boolean draw = !homeWins && !awayWins;
                scoreHome.setTextColor(homeWins ? primary : variant);
                scoreAway.setTextColor(awayWins ? primary : variant);
                styleName(homeName, draw || homeWins, white, variant);
                styleName(awayName, draw || awayWins, white, variant);
            } else {
                vs.setVisibility(View.VISIBLE);
                layoutScore.setVisibility(View.GONE);
                if (warmup) {
                    vs.setBackgroundResource(R.drawable.bg_vs);
                    vs.setTextColor(tertiary);
                } else {
                    vs.setBackgroundResource(R.drawable.bg_vs);
                    vs.setTextColor(variant);
                }
                styleName(homeName, true, white, variant);
                styleName(awayName, true, white, variant);
            }

            // pied de carte
            footerLeft.setText(m.footerLeft);
            action.setText(m.footerAction);
            int actionColor = m.actionHighlight ? primary : variant;
            action.setTextColor(actionColor);
            ivChevron.setColorFilter(actionColor);
            btnAction.setOnClickListener(v -> listener.onActionClicked(m, m.footerAction));
            itemView.setOnClickListener(v -> listener.onMatchClicked(m));
        }

        private void styleName(TextView tv, boolean strong, int strongColor, int weakColor) {
            tv.setTextColor(strong ? strongColor : weakColor);
            tv.setTypeface(Typeface.DEFAULT, strong ? Typeface.BOLD : Typeface.NORMAL);
        }
    }

    // ------------------------------------------------------------------
    // Carte : match en streaming
    // ------------------------------------------------------------------
    private class StreamHolder extends RecyclerView.ViewHolder {
        private final TextView time, stadium, homeCrest, homeName, homeSub;
        private final TextView awayCrest, awayName, awaySub, footerLeft, action;
        private final View btnReminder;

        StreamHolder(View view) {
            super(view);
            time = view.findViewById(R.id.tvTime);
            stadium = view.findViewById(R.id.tvStadium);
            homeCrest = view.findViewById(R.id.tvHomeCrest);
            homeName = view.findViewById(R.id.tvHomeName);
            homeSub = view.findViewById(R.id.tvHomeSub);
            awayCrest = view.findViewById(R.id.tvAwayCrest);
            awayName = view.findViewById(R.id.tvAwayName);
            awaySub = view.findViewById(R.id.tvAwaySub);
            footerLeft = view.findViewById(R.id.tvFooterLeft);
            action = view.findViewById(R.id.tvAction);
            btnReminder = view.findViewById(R.id.btnReminder);
            UiUtils.pulse(view.findViewById(R.id.dotStream));
        }

        void bind(Match m) {
            time.setText(m.time);
            stadium.setText(m.stadium);
            UiUtils.applyCrest(homeCrest, m.home);
            UiUtils.applyCrest(awayCrest, m.away);
            homeName.setText(m.home.name);
            awayName.setText(m.away.name);
            homeSub.setText(m.homeSub);
            awaySub.setText(m.awaySub);
            footerLeft.setText(m.footerLeft);
            action.setText(m.footerAction);
            itemView.setOnClickListener(v -> listener.onMatchClicked(m));
            btnReminder.setOnClickListener(v -> listener.onActionClicked(m, m.footerAction));
        }
    }
}
