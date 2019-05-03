package me.kyllian.TFA.handlers;

import com.warrenstrange.googleauth.ICredentialRepository;
import me.kyllian.TFA.TFAPlugin;
import org.bukkit.Bukkit;

import java.util.Base64;
import java.util.List;

public class KeyHandler implements ICredentialRepository {

    private TFAPlugin plugin;

    public KeyHandler(TFAPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getSecretKey(String s) {
        String key = plugin.getPlayerHandler().getFileConfiguration().getString(s + ".code");
        if (key == null) return null;
        String decoded = new String(Base64.getDecoder().decode(key));
        return decoded;
}

    @Override
    public void saveUserCredentials(String s, String s1, int i, List<Integer> list) {
        String encoded = new String(Base64.getEncoder().encodeToString(s1.getBytes()));
        plugin.getPlayerHandler().getFileConfiguration().set(s + ".code", encoded);
        plugin.getPlayerHandler().save();
    }
}