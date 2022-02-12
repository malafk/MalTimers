package lol.maltest.timers.commands;

import lol.maltest.timers.Timers;
import lol.maltest.timers.impl.TimersObject;
import lol.maltest.timers.util.ChatUtil;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * The main command to control the timers
 * @author maltest
 * @since 29/01/2022
 */
public class TimerCommand implements CommandExecutor {

    private Timers plugin;

    public TimerCommand(Timers plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(args.length < 2) {
                player.sendMessage(ChatUtil.colour("&cUsage: /timers <create/remove/set> <name> [unix time]"));
            } else {
                String id = args[1];
                switch (args[0].toLowerCase()) {
                    case "create":
                        if(!plugin.hasTimer(id)) {
                            if(args.length > 2) {
                                if(isLong(args[2])) {
                                    if(validTimestamp(Long.parseLong(args[2]))) {
                                        long time = Long.parseLong(args[2]);
                                        createTimer(id, time, player);
                                    } else {
                                        player.sendMessage(ChatUtil.colour("&cDouble check that timestamp as it isnt valid. Make sure it isn't in the past!"));
                                    }
                                } else {
                                    player.sendMessage(ChatUtil.colour("&cThat isn't unix timestamp!"));
                                }
                            } else {
                                player.sendMessage(ChatUtil.colour("&cYou need to provide a name and a unix timestamp!"));
                            }
                        } else {
                            player.sendMessage(ChatUtil.colour("&cA timer with that name already exists!"));
                        }
                        break;
                    case "remove":
                        if(plugin.hasTimer(id)) {
                            deleteTimer(player, id);
                        } else {
                            player.sendMessage(ChatUtil.colour("&cI can't find a timer with that name!"));
                        }
                        break;
                    case "set":
                        if(args.length > 2) {
                            if(validTimestamp(Long.parseLong(args[2]))) {
                                long time = Long.parseLong(args[2]);
                                if(plugin.hasTimer(id)) {
                                    setTimer(player, id, time);
                                } else {
                                    player.sendMessage(ChatUtil.colour("&cI can't find a timer with that name!"));
                                }
                            } else {
                                player.sendMessage(ChatUtil.colour("&cThat isn't unix timestamp!"));
                            }
                        } else {
                            player.sendMessage(ChatUtil.colour("&cYou need to provide a name and a unix timestamp!"));
                        }
                        break;
                }
            }
        }
        return false;
    }

    /**
     * Check if the timestamp is valid by making sure it isn't in the past
     * @param ts The timestamp you want to validate
     * @return if timestamp is valid
     */
    public boolean validTimestamp(long ts) {
        return ts >= System.currentTimeMillis() / 1000;
    }

    /**
     * Checks if a long is valid using {@link NumberUtils}
     * @param l The string you want to validate as a long
     * @return if NumberUtils can parse the number or not
     */
    public boolean isLong(String l) {
        return NumberUtils.isNumber(l);
    }

    /**
     * This creates a timer and stores it in the config and register's it's Placeholder!
     * @param id The identifier of the timer
     * @param time The time for the timer to track
     * @param creator The creator of the timer
     */
    public void createTimer(String id, Long time, Player creator) {
        long beforeCreated = System.currentTimeMillis();
        plugin.getTimerData().get().set("timers." + id + ".time", time);
        plugin.getTimerData().get().set("timers." + id + ".made", System.currentTimeMillis() / 1000);
        plugin.getTimerData().get().set("timers." + id + ".author", creator.getUniqueId().toString());
        plugin.getTimerData().save();
        plugin.getTimers().add(new TimersObject(id, time, System.currentTimeMillis() / 1000, creator.getUniqueId()));
        long msTaken = System.currentTimeMillis() - beforeCreated;
        creator.sendMessage(ChatUtil.colour("&7The timer &e" + id + " &7was made in &a" + msTaken + "&7ms!"));
    }

    /**
     * This deletes a timer from the config and the cache!
     * @param player The player who ordered for the timer to be deleted
     * @param id The identifier for the timer
     */
    public void deleteTimer(Player player, String id) {
        long beforeCreated = System.currentTimeMillis();
        plugin.getTimerData().get().set("timers." + id, null);
        plugin.getTimerData().save();
        plugin.getTimers().remove(plugin.getTimer(id));
        long msTaken = System.currentTimeMillis() - beforeCreated;
        player.sendMessage(ChatUtil.colour("&7The timer &e" + id + " &7was removed in &a" + msTaken + "&7ms!"));
    }

    /**
     * This updates a timer's time to config and cache
     * @param player The player who ordered for the timer to be changed
     * @param id The identifier for the timer
     * @param newTime The new time in unix timestamp
     */
    public void setTimer(Player player, String id, long newTime) {
        long beforeCreated = System.currentTimeMillis();
        plugin.getTimerData().get().set("timers." + id + ".time", newTime);
        plugin.getTimerData().save();
        plugin.getTimer(id).setTime(newTime);
        long msTaken = System.currentTimeMillis() - beforeCreated;
        player.sendMessage(ChatUtil.colour("&7The timer &e" + id + " &7was updated in &a" + msTaken + "&7ms!"));
    }
}
