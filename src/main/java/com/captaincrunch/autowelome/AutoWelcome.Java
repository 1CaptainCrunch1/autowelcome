package com.captaincrunch.autowelcome;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.EventHandler;

public class AutoWelcome extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getLogger().info("AutoWelcome plugin enabled!");
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        getLogger().info("AutoWelcome plugin disabled.");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.getPlayer().sendMessage("§aWelcome to the server, " + event.getPlayer().getName() + "!");
    }
}
