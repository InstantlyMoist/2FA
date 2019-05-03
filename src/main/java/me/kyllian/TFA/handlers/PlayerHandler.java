package me.kyllian.TFA.handlers;

import me.kyllian.TFA.TFAPlugin;
import me.kyllian.TFA.utils.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class PlayerHandler {

    private TFAPlugin plugin;

    private HashMap<Player, PlayerData> playerData;

    private File file;
    private FileConfiguration fileConfiguration;

    public PlayerHandler(TFAPlugin plugin) {
        this.plugin = plugin;

        playerData = new HashMap<>();

        file = new File(plugin.getDataFolder(), "players.yml");
        if (!file.exists()) plugin.saveResource("players.yml", false);
        fileConfiguration = YamlConfiguration.loadConfiguration(file);
    }

    public void save() {
        try {
            fileConfiguration.save(file);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void reload() {
        fileConfiguration = YamlConfiguration.loadConfiguration(file);
    }

    public PlayerData getPlayerData(Player player) {
        return playerData.computeIfAbsent(player, f -> new PlayerData(plugin, player));
    }

    public FileConfiguration getFileConfiguration() {
        return fileConfiguration;
    }

    public ItemStack getItemInHand(Player player) {
        return Bukkit.getVersion().contains("1.8") || Bukkit.getVersion().contains("1.7") ? player.getItemInHand() : player.getInventory().getItemInMainHand();
    }

    public void setItemInHand(Player player, ItemStack itemStack) {
        if (Bukkit.getVersion().contains("1.8") || Bukkit.getVersion().contains("1.7")) player.setItemInHand(itemStack);
        else player.getInventory().setItemInMainHand(itemStack);
    }
}
