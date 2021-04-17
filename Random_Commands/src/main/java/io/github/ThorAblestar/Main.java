package io.github.ThorAblestar;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collection;

public class Main extends JavaPlugin implements CommandExecutor {
    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
    // add this to an API?
    private Object[] getOnlinePlayers() {
        Collection<Player> players = (Collection<Player>) Bukkit.getOnlinePlayers(); // lol what
        Object[] playerArray = players.toArray();
        return playerArray;
    }
    private Player getOnlinePlayerByUsername(String username) {
        Object[] players = getOnlinePlayers();
        for (Object obj : players) {
            Player plyr = (Player)obj;

            if (plyr.getName() == username) {
                return plyr;
            }
        }
        return null;
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player sender = (Player)commandSender;
            String senderName = sender.getName();
            Object[] players = getOnlinePlayers();

            switch(command.getName().toLowerCase()) {
                case "heal":
                    if (sender.hasPermission("bPermissions.RanCom.Heal")) {
                        sender.setFoodLevel(20);
                        sender.setHealth(20);
                    } else {
                        sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
                    }
                    break;
                case "fly":
                    if (sender.hasPermission("bPermissions.RanCom.Fly")) {
                        if (sender.getAllowFlight()) {
                            sender.setAllowFlight(false);
                        } else {
                            sender.setAllowFlight(true);
                        }
                    } else {
                        sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
                    }
                    break;
                case "hide":
                    if (sender.hasPermission("bPermissions.RanCom.ShowHide")) {
                        for (Object obj : players) {
                            Player plyr = (Player) obj;
                            if (plyr.getName() != senderName) {
                                sender.hidePlayer(this, plyr);
                            }
                        }
                    } else {
                        sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
                    }
                    break;
                case "show":
                    if (sender.hasPermission("bPermissions.RanCom.ShowHide")) {
                        for (Object obj : players) {
                            Player plyr = (Player)obj;
                            if (plyr.getName() != senderName) {
                                sender.showPlayer(this, plyr);
                            }
                        }
                    } else {
                        sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
                    }
                    break;
                case "enderchest":
                    if (sender.hasPermission("bPermissions.RanCom.EC")) {
                        Inventory ec = sender.getEnderChest();
                        sender.openInventory(ec);
                    } else {
                        sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
                    }
                    break;
                default:
                    sender.sendMessage(ChatColor.RED + command.getName() + " is not a valid command!");
                    return false;
            }
        }
        return true;
    }
}
