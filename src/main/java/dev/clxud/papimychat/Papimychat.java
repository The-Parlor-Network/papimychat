package dev.clxud.papimychat;

import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Papimychat extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Plugin startup logic

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null) {
            getLogger().severe("Could not find PlaceholderAPI! Disabling plugin...");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        getLogger().info("PAPIMyChat has been enabled!");
        // register events
        getServer().getPluginManager().registerEvents(this, this);

    }

    @Override
    public @NotNull ComponentLogger getComponentLogger() {
        return super.getComponentLogger();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("PAPIMyChat has been disabled!");
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String format = event.getFormat();

        Pattern pattern = Pattern.compile("\\{(.*?)\\}");
        Matcher matcher = pattern.matcher(format);

        StringBuffer buffer = new StringBuffer();
        while (matcher.find()) {
            String placeholder = matcher.group(1);
            String replacement = PlaceholderAPI.setPlaceholders(player, "%" + placeholder + "%");
            matcher.appendReplacement(buffer, replacement);
        }

        matcher.appendTail(buffer);
        event.setFormat(buffer.toString());
    }
}
