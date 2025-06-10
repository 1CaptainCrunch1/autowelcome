package com.captaincrunch.autowelcome;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.EventHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AutoWelcome extends JavaPlugin implements Listener {

    private Connection connection;

    @Override
    public void onEnable() {
        getLogger().info("AutoWelcome plugin enabled!");
        Bukkit.getPluginManager().registerEvents(this, this);
        connectToDatabase();
    }

    @Override
    public void onDisable() {
        getLogger().info("AutoWelcome plugin disabled.");
        closeDatabaseConnection();
    }

    @EventHandler
    public void onPlayerJoin(@org.jetbrains.annotations.NotNull PlayerJoinEvent event) {
        String playerName = event.getPlayer().getName();
        event.getPlayer().sendMessage("§aWelcome to the server, " + playerName + "!");

        logJoinToDatabase(playerName);
    }

    private void connectToDatabase() {
        String url = "jdbc:mysql://localhost:3306/minecraft"; // Change database name if needed
        String user = "root"; // Replace with your MySQL user
        String password = ""; // Replace with your MySQL password

        try {
            connection = DriverManager.getConnection(url, user, password);
            getLogger().info("Connected to MySQL database.");
        } catch (SQLException e) {
            getLogger().severe("Could not connect to MySQL database: " + e.getMessage());
        }
    }

    private void logJoinToDatabase(String playerName) {
        if (connection == null) return;

        String sql = "INSERT INTO player_joins (player_name, join_time) VALUES (?, NOW())";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, playerName);
            stmt.executeUpdate();
        } catch (SQLException e) {
            getLogger().warning("Could not log join to database: " + e.getMessage());
        }
    }

    private void closeDatabaseConnection() {
        if (connection != null) {
            try {
                connection.close();
                getLogger().info("Disconnected from MySQL database.");
            } catch (SQLException e) {
                getLogger().warning("Error closing database connection: " + e.getMessage());
            }
        }
    }
}
