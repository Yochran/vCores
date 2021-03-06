package net.vectromc.vnitrogen.listeners.starterlisteners;

import net.vectromc.vnitrogen.vNitrogen;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class MCStarterListener implements Listener {

    private vNitrogen plugin;

    public MCStarterListener() {
        plugin = vNitrogen.getPlugin(vNitrogen.class);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();
        if (message.startsWith("! ")) {
            if (player.hasPermission(plugin.getConfig().getString("ManagementChat.permission"))) {
                event.setCancelled(true);
                String str = message.replaceFirst("! ", "");
                String world = player.getWorld().getName();
                plugin.setPlayerColor(player);
                for (Player managers : Bukkit.getOnlinePlayers()) {
                    if (managers.hasPermission(plugin.getConfig().getString("ManagementChat.permission"))) {
                        managers.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("ManagementChat.format").replaceAll("%world%", world).replaceAll("%player%", player.getDisplayName()).replaceAll("%message%", str)));
                    }
                }
            }
        }
    }
}
