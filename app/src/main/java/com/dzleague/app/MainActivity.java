package com.dzleague.app;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dzleague.app.adapter.MatchAdapter;
import com.dzleague.app.data.FakeData;
import com.dzleague.app.model.League;
import com.dzleague.app.model.ListItem;
import com.dzleague.app.model.Match;
import com.dzleague.app.model.MatchStatus;
import com.dzleague.app.util.UiUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MatchAdapter.Listener {

    private List<League> leagues;
    private MatchAdapter adapter;
    private android.widget.TextView tvLiveCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Laisse la place aux barres système (statut / navigation)
        View root = findViewById(R.id.root);
        ViewCompat.setOnApplyWindowInsetsListener(root, (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return insets;
        });

        leagues = FakeData.getLeagues();
        tvLiveCount = findViewById(R.id.tvLiveCount);
        UiUtils.pulse(findViewById(R.id.dotLogo));
        UiUtils.pulse(findViewById(R.id.dotLivePill));
        findViewById(R.id.btnSearch).setOnClickListener(v -> showSoon());

        RecyclerView recycler = findViewById(R.id.recyclerMatches);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MatchAdapter(this);
        recycler.setAdapter(adapter);

        applyFilter(MatchAdapter.FILTER_ALL);
        setupBottomNav();
    }

    /** Reconstruit la liste selon le filtre choisi. */
    private void applyFilter(int filter) {
        int[] counts = new int[5];
        for (League league : leagues) {
            for (Match m : league.matches) {
                counts[MatchAdapter.FILTER_ALL]++;
                if (m.status == MatchStatus.LIVE) counts[MatchAdapter.FILTER_LIVE]++;
                if (League.REGION_TLEMCEN.equals(league.region)) counts[MatchAdapter.FILTER_TLEMCEN]++;
                if (League.REGION_ORAN.equals(league.region)) counts[MatchAdapter.FILTER_ORAN]++;
                if (League.REGION_ALGER.equals(league.region)) counts[MatchAdapter.FILTER_ALGER]++;
            }
        }

        List<ListItem> items = new ArrayList<>();
        items.add(ListItem.dates());
        items.add(ListItem.chips());
        for (League league : leagues) {
            List<Match> selected = new ArrayList<>();
            for (Match m : league.matches) {
                if (accepts(filter, league, m)) selected.add(m);
            }
            if (!selected.isEmpty()) {
                items.add(ListItem.league(league));
                for (Match m : selected) items.add(ListItem.match(m));
            }
        }

        adapter.setItems(items, counts, filter);
        tvLiveCount.setText(getString(R.string.live_count, counts[MatchAdapter.FILTER_LIVE]));
    }

    private boolean accepts(int filter, League league, Match m) {
        switch (filter) {
            case MatchAdapter.FILTER_LIVE:
                return m.status == MatchStatus.LIVE;
            case MatchAdapter.FILTER_TLEMCEN:
                return League.REGION_TLEMCEN.equals(league.region);
            case MatchAdapter.FILTER_ORAN:
                return League.REGION_ORAN.equals(league.region);
            case MatchAdapter.FILTER_ALGER:
                return League.REGION_ALGER.equals(league.region);
            default:
                return true;
        }
    }

    private void setupBottomNav() {
        final View[] tabs = {
                findViewById(R.id.navHome),
                findViewById(R.id.navLeagues),
                findViewById(R.id.navCalendar),
                findViewById(R.id.navFavorites),
                findViewById(R.id.navSettings)
        };
        tabs[0].setSelected(true);
        for (View tab : tabs) {
            tab.setOnClickListener(v -> {
                for (View other : tabs) other.setSelected(other == v);
                if (v.getId() != R.id.navHome) showSoon();
            });
        }
    }

    private void showSoon() {
        Toast.makeText(this, R.string.soon, Toast.LENGTH_SHORT).show();
    }

    // ---------------- MatchAdapter.Listener ----------------
    @Override
    public void onDaySelected(int dayIndex) {
        // TODO : charger les matchs du jour choisi depuis une API
    }

    @Override
    public void onFilterSelected(int filter) {
        applyFilter(filter);
    }

    @Override
    public void onMatchClicked(Match match) {
        Toast.makeText(this, match.home.name + " - " + match.away.name, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActionClicked(Match match, String action) {
        if (match == null) {
            showSoon();
        } else {
            Toast.makeText(this, action + " : " + match.home.name + " - " + match.away.name,
                    Toast.LENGTH_SHORT).show();
        }
    }
}
