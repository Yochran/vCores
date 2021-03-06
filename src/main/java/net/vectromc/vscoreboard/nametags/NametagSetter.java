package net.vectromc.vscoreboard.nametags;

import net.vectromc.vnitrogen.vNitrogen;
import net.vectromc.vscoreboard.vScoreboard;
import net.vectromc.vstaffutils.vStaffUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.HashMap;

public class NametagSetter {

    private vScoreboard plugin;
    private vNitrogen nitrogen;
    private vStaffUtils staffUtils;

    public NametagSetter() {
        plugin = vScoreboard.getPlugin(vScoreboard.class);
        nitrogen = vNitrogen.getPlugin(vNitrogen.class);
        staffUtils = vStaffUtils.getPlugin(vStaffUtils.class);
    }

    public void setNametag(Player player1, Player player2) {
        Scoreboard scoreboard = player1.getScoreboard();
        if (!staffUtils.vanished.contains(player2.getUniqueId()) && !staffUtils.modmode.contains(player2.getUniqueId())) {
            int count = 0;
            for (String rank : nitrogen.getConfig().getConfigurationSection("Ranks").getKeys(false)) {
                HashMap<String, Integer> team_priority = new HashMap();
                count++;
                team_priority.put(rank, count);
                if (nitrogen.pData.config.getString(player2.getUniqueId().toString() + ".Rank").equalsIgnoreCase(rank)) {
                    if (scoreboard.getTeam("" + team_priority.get(rank)) != null) {
                        if (!scoreboard.getTeam("" + team_priority.get(rank)).hasPlayer(player2)) {
                            for (Team teams : scoreboard.getTeams()) {
                                if (teams.hasPlayer(player2)) {
                                    teams.removePlayer(player2);
                                }
                            }
                            scoreboard.getTeam("" + team_priority.get(rank)).addPlayer(player2);
                        } else {
                            return;
                        }
                    } else {
                        Team team = scoreboard.registerNewTeam("" + team_priority.get(rank));
                        team.setPrefix(ChatColor.translateAlternateColorCodes('&', nitrogen.getConfig().getString("Ranks." + rank + ".color")));
                        scoreboard.getTeam("" + team_priority.get(rank)).addPlayer(player2);
                    }
                }
            }
        } else if (staffUtils.vanished.contains(player2.getUniqueId()) && !staffUtils.modmode.contains(player2.getUniqueId())){
            if (scoreboard.getTeam("a") != null) {
                if (!scoreboard.getTeam("a").hasPlayer(player2)) {
                    for (Team teams : scoreboard.getTeams()) {
                        if (teams.hasPlayer(player2)) {
                            teams.removePlayer(player2);
                        }
                    }
                    scoreboard.getTeam("a").addPlayer(player2);
                } else {
                    return;
                }
            } else {
                Team team = scoreboard.registerNewTeam("a");
                team.setPrefix(ChatColor.translateAlternateColorCodes('&', "&7[V] &7"));
                scoreboard.getTeam("a").addPlayer(player2);
            }
        } else if (!staffUtils.vanished.contains(player2.getUniqueId()) && staffUtils.modmode.contains(player2.getUniqueId())) {
            if (scoreboard.getTeam("b") != null) {
                if (!scoreboard.getTeam("b").hasPlayer(player2)) {
                    for (Team teams : scoreboard.getTeams()) {
                        if (teams.hasPlayer(player2)) {
                            teams.removePlayer(player2);
                        }
                    }
                    scoreboard.getTeam("b").addPlayer(player2);
                } else {
                    return;
                }
            } else {
                Team team = scoreboard.registerNewTeam("b");
                team.setPrefix(ChatColor.translateAlternateColorCodes('&', "&7[M] &7"));
                scoreboard.getTeam("b").addPlayer(player2);
            }
        } else {
            if (scoreboard.getTeam("b") != null) {
                if (!scoreboard.getTeam("b").hasPlayer(player2)) {
                    for (Team teams : scoreboard.getTeams()) {
                        if (teams.hasPlayer(player2)) {
                            teams.removePlayer(player2);
                        }
                    }
                    scoreboard.getTeam("b").addPlayer(player2);
                } else {
                    return;
                }
            } else {
                Team team = scoreboard.registerNewTeam("b");
                team.setPrefix(ChatColor.translateAlternateColorCodes('&', "&7[M] &7"));
                scoreboard.getTeam("b").addPlayer(player2);
            }
        }
    }
}
