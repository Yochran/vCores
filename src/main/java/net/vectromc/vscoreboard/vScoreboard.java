package net.vectromc.vscoreboard;

import net.vectromc.vnitrogen.vNitrogen;
import net.vectromc.vscoreboard.commands.ListCommand;
import net.vectromc.vscoreboard.commands.ScoreboardCommand;
import net.vectromc.vscoreboard.commands.ToggleScoreboardCommand;
import net.vectromc.vscoreboard.healthbar.HealthbarSetter;
import net.vectromc.vscoreboard.healthbar.HealthbarUpdater;
import net.vectromc.vscoreboard.listeners.CommandListener;
import net.vectromc.vscoreboard.listeners.PlayerLogListener;
import net.vectromc.vscoreboard.listeners.WorldChangeListener;
import net.vectromc.vscoreboard.nametags.NametagSetter;
import net.vectromc.vscoreboard.nametags.NametagUpdater;
import net.vectromc.vscoreboard.utils.ScoreboardRunnable;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.UUID;

public final class vScoreboard extends JavaPlugin {

    public PlayerScoreboard scoreboard;
    public NametagSetter nametag;
    public HealthbarSetter healthbar;

    private vNitrogen nitrogen;

    public vScoreboard() {
        nitrogen = vNitrogen.getPlugin(vNitrogen.class);
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        registerEvents();
        registerCommands();
        runRunnables();
        registerScoreboard();
        startupAnnouncements();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        shutdownAnnouncements();
    }

    public ArrayList<UUID> tsb = new ArrayList<>();

    private void startupAnnouncements() {
        System.out.println("[VectroMC] vScoreboard v1.0 by Yochran is loading...");
        System.out.println("[VectroMC] vScoreboard v1.0 by Yochran has successfully loaded.");
    }

    private void shutdownAnnouncements() {
        System.out.println("[VectroMC] vScoreboard v1.0 by Yochran is unloading...");
        System.out.println("[VectroMC] vScoreboard v1.0 by Yochran has successfully unloaded.");
    }

    private void registerEvents() {
        PluginManager manager = getServer().getPluginManager();
        manager.registerEvents(new PlayerScoreboard(), this);
        manager.registerEvents(new CommandListener(), this);
        manager.registerEvents(new WorldChangeListener(), this);
        manager.registerEvents(new PlayerLogListener(), this);
    }

    private void registerCommands() {
        getCommand("Onlineplayers").setExecutor(new ListCommand());
        getCommand("ToggleScoreboard").setExecutor(new ToggleScoreboardCommand());
        getCommand("vScoreboard").setExecutor(new ScoreboardCommand());
    }

    private void runRunnables() {
        new ScoreboardRunnable().runTaskTimer(this, 0, 5);
        new NametagUpdater().runTaskTimer(this, 0, 10);
        new HealthbarUpdater().runTaskTimer(this, 0, 5);
    }

    private void registerScoreboard() {
        nametag = new NametagSetter();
        healthbar = new HealthbarSetter();
        scoreboard = new PlayerScoreboard();
    }
}
