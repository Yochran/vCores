package net.vectromc.vstaffutils.listeners;

import com.destroystokyo.paper.event.server.PaperServerListPingEvent;
import net.vectromc.vstaffutils.utils.Utils;
import net.vectromc.vstaffutils.vStaffUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class VanishUpdater implements Listener {

    private vStaffUtils plugin;

    public VanishUpdater() {
        plugin = vStaffUtils.getPlugin(vStaffUtils.class);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
            if (plugin.vanished.contains(onlinePlayers.getUniqueId())) {
                if (!player.hasPermission(plugin.getConfig().getString("VanishPermission"))) {
                    player.hidePlayer(onlinePlayers);
                }
            }
        }
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        if (event.getMessage().startsWith("/setrank ") || event.getMessage().startsWith("/pex ") || event.getMessage().startsWith("/op ")) {
            for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
                if (plugin.vanished.contains(onlinePlayers.getUniqueId())) {
                    if (player.hasPermission(plugin.getConfig().getString("VanishPermission"))) {
                        player.showPlayer(onlinePlayers);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent event) {
        Entity entity = event.getDamager();
        if (entity instanceof Player) {
            Player player = (Player) event.getDamager();
            if (plugin.vanished.contains(player.getUniqueId())) {
                event.setCancelled(true);
                Utils.sendMessage(player, "&cYou cannot pvp as you are in vanish!");
            }
        }
    }

    @EventHandler
    public void onStaffQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (plugin.vanished.contains(player.getUniqueId())) {
            plugin.vanished.remove(player.getUniqueId());
            plugin.vanish_logged.add(player.getUniqueId());
        }
    }

    @EventHandler
    public void onStaffJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (plugin.vanish_logged.contains(player.getUniqueId())) {
            for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
                onlinePlayers.hidePlayer(player);
            }
            plugin.vanish_logged.remove(player.getUniqueId());
            plugin.vanished.add(player.getUniqueId());
            Utils.sendMessage(player, plugin.getConfig().getString("VanishLogin"));
        }
    }
}