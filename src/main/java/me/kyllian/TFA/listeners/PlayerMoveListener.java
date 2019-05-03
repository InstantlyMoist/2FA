package me.kyllian.TFA.listeners;

import me.kyllian.TFA.TFAPlugin;
import me.kyllian.TFA.utils.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {

    private TFAPlugin plugin;

    public PlayerMoveListener(TFAPlugin plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        PlayerData playerData = plugin.getPlayerHandler().getPlayerData(player);
        if (playerData.getAuthenticationTask() != null || playerData.isSetup() && (event.getTo().getX() != event.getFrom().getX() || event.getTo().getZ() != event.getFrom().getZ() || event.getFrom().getY() != event.getTo().getY())) event.setCancelled(true);
    }
}
