package lol.maltest.timers.impl;

import lol.maltest.timers.Timers;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;

public class TimerExpansion extends PlaceholderExpansion {

    private Timers plugin;

    public TimerExpansion(Timers plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getIdentifier() {
        return "maltimers";
    }

    @Override
    public String getAuthor() {
        return "maltest";
    }

    @Override
    public String getVersion() {
        return "0.0.1";
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {

        if(params.startsWith("full_")) {
            String timerName = params.substring(5);
            if(plugin.hasTimer(timerName)) {
                TimersObject timer = plugin.getTimer(timerName);
                if(timer.hasCompleted()) {
                    return "0s";
                }
                return plugin.getTimer(timerName).toFullDate();
            }
            return "???";
        }
        if(params.startsWith("timer_")) {
            String timerName = params.substring(6);
            if(plugin.hasTimer(timerName)) {
                TimersObject timer = plugin.getTimer(timerName);
                if (timer.hasCompleted()) {
                    return "0s";
                }
                return plugin.getTimer(timerName).toTimer();
            }
            return "???";
        }


        return null; // Placeholder is unknown by the Expansion
    }

}
