package me.kyllian.TFA.listeners;

import me.kyllian.TFA.TFAPlugin;
import me.kyllian.TFA.utils.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class PlayerCommandPreprocessListener implements Listener {

    private TFAPlugin plugin;

    public PlayerCommandPreprocessListener(TFAPlugin plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        PlayerData playerData = plugin.getPlayerHandler().getPlayerData(player);
        if (playerData.getAuthenticationTask() != null || playerData.isSetup()) event.setCancelled(true);
    }
}
