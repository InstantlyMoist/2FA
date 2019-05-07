package me.kyllian.TFA.handlers;

import me.kyllian.TFA.TFAPlugin;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.List;

public class MessageHandler {

    private TFAPlugin plugin;

    private File file;
    private FileConfiguration fileConfiguration;

    public MessageHandler(TFAPlugin plugin) {
        this.plugin = plugin;

        file = new File(plugin.getDataFolder(), "messages.yml");
        if (!file.exists()) plugin.saveResource("messages.yml", false);
        fileConfiguration = YamlConfiguration.loadConfiguration(file);
    }

    public void reload() {
        fileConfiguration = YamlConfiguration.loadConfiguration(file);
    }

    public String colorTranslate(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public String getNotAPlayerMessage() {
        return colorTranslate(fileConfiguration.getString("NotAPlayer"));
    }

    public String getNoPermissionMessage(String permission) {
        return colorTranslate(fileConfiguration.getString("NoPermission").replace("%permission%", permission));
    }

    public String getAlreadySetupMessage() {
        return colorTranslate(fileConfiguration.getString("AlreadySetup"));
    }

    public String getSetupMessage() {
        return colorTranslate(fileConfiguration.getString("Setup"));
    }

    public String getIncorrectMessage() {
        return colorTranslate(fileConfiguration.getString("Incorrect"));
    }

    public String getInfoMessage() {
        return colorTranslate(fileConfiguration.getString("Info"));
    }

    public String getNotSettingUpMessage() {
        return colorTranslate(fileConfiguration.getString("NotSettingUp"));
    }

    public String getCanceledMessage() {
        return colorTranslate(fileConfiguration.getString("Canceled"));
    }

    public String getReloadedMessage() {
        return colorTranslate(fileConfiguration.getString("Reloaded"));
    }

    public String getCorrectMessage() {
        return colorTranslate(fileConfiguration.getString("Correct"));
    }

    public String getEnterCodeMessage() {
        return fileConfiguration.getString("EnterCode");
    }

    public String getNoCodeMessage() {
        return colorTranslate(fileConfiguration.getString("NoCode"));
    }

    public String getCodeRemovedMessage() {
        return colorTranslate(fileConfiguration.getString("CodeRemoved"));
    }

    public List<String> getHelpMessages() {
        return fileConfiguration.getStringList("Help");
    }

    public String getEnterCodeChatMessage() {
        return colorTranslate(fileConfiguration.getString("EnterCodeChat"));
    }

    public String getKickMessage() {
        return colorTranslate(fileConfiguration.getString("Kick").replace("\\n","\n"));
    }
}
