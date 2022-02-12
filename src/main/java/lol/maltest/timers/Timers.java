package lol.maltest.timers;

import lol.maltest.timers.commands.TimerCommand;
import lol.maltest.timers.impl.TimerData;
import lol.maltest.timers.impl.TimerExpansion;
import lol.maltest.timers.impl.TimersObject;
import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.UUID;

/**
 * @author maltest
 * @since 29/01/2022
 * @version 0.0.1
 */

public final class Timers extends JavaPlugin {

    public static Timers plugin;
    private TimerData timerData;
    private ArrayList<TimersObject> timers = new ArrayList<>();

    @Override
    public void onEnable() {
        plugin = this;
        this.timerData = new TimerData();
        timerData.setup();

        if(plugin.getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new TimerExpansion(this).register();
            plugin.getServer().getLogger().info("[MalTimers] Registered the MalTimers PlaceholderAPI Expansion");
        } else {
            getLogger().severe("PlaceholderAPI isn't installed... Disabling");
            plugin.getServer().getPluginManager().disablePlugin(this);
        }

        getCommand("timers").setExecutor(new TimerCommand(this));

        for(String id : timerData.get().getConfigurationSection("timers.").getKeys(false)) {
            long time = timerData.get().getLong("timers." + id + ".time");
            long created = timerData.get().getLong("timers." + id + ".made");
            UUID author = UUID.fromString(timerData.get().getString("timers." + id + ".author"));
            timers.add(new TimersObject(id, time, created, author));
        }
        plugin.getServer().getLogger().info("[MalTimers] Synced " + timers.size() + " MalTimers.");
    }

    /**
     * Get the timerData instance
     * @return timerData instance
     */
    public TimerData getTimerData() {
        return timerData;
    }

    public ArrayList<TimersObject> getTimers() {
        return timers;
    }

    public boolean hasTimer(String id) {
        for(TimersObject timersObject : timers) {
            if(timersObject.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public TimersObject getTimer(String id) {
        for(TimersObject timersObject : timers) {
            if(timersObject.getId().equals(id)) {
                return timersObject;
            }
        }
        return null;
    }
}
