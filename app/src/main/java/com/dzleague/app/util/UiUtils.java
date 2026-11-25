package com.dzleague.app.util;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.dzleague.app.model.Team;

public final class UiUtils {

    private UiUtils() {}

    public static int dp(Context context, float value) {
        return Math.round(value * context.getResources().getDisplayMetrics().density);
    }

    /** Fait clignoter doucement une vue (point "en direct"). */
    public static void pulse(View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "alpha", 1f, 0.2f);
        animator.setDuration(900);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.start();
    }

    /** Dessine l'écusson (cercle + initiales) d'une équipe dans un TextView. */
    public static void applyCrest(TextView view, Team team) {
        Context context = view.getContext();
        GradientDrawable circle = new GradientDrawable();
        circle.setShape(GradientDrawable.OVAL);
        circle.setColor(ContextCompat.getColor(context, team.fillColor));
        circle.setStroke(dp(context, 1), ContextCompat.getColor(context, team.strokeColor));
        view.setBackground(circle);
        view.setText(team.initials);
        view.setTextColor(ContextCompat.getColor(context, team.textColor));
    }
}
