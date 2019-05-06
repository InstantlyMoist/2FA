package me.kyllian.TFA.handlers;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MapHandler {

    public void sendMap(Player player, String text) {
        BufferedImage image = new BufferedImage(128, 128, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setBackground(Color.WHITE);
        graphics.setColor(Color.BLACK);

        graphics.clearRect(0, 0, 128, 128);

        int yPos = 10;
        for (String string : text.split("%n")) {
            int width = graphics.getFontMetrics().stringWidth(string);
            graphics.drawString(string, 128 / 2 - width / 2, yPos);
            yPos += graphics.getFontMetrics().getHeight();
        }
        graphics.dispose();
        sendMap(player, image);
    }

    public void sendMap(Player player, Image image) {
        ItemStack map = new ItemStack(Material.MAP);
        MapView mapView = Bukkit.createMap(player.getWorld());
        mapView.getRenderers().clear();
        mapView.addRenderer(new MapRenderer() {
            @Override
            public void render(MapView mapView, MapCanvas mapCanvas, Player player) {
                mapView.setScale(MapView.Scale.CLOSEST);
                mapCanvas.drawImage(0, 0, image);
            }
        });
        if (Bukkit.getVersion().contains("1.13") || Bukkit.getVersion().contains("1.14")) {
            MapMeta meta = (MapMeta) map.getItemMeta();
            meta.setMapId(getMapID(mapView));
            map.setItemMeta(meta);
        } else map.setDurability(getMapID(mapView));
        if (Bukkit.getServer().getVersion().contains("1.8")) player.setItemInHand(map);
        else player.getInventory().setItemInMainHand(map);
    }

    public static Class<?> getMapNMS(String name) {
        try {
            return Class.forName("org.bukkit.map." + name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static short getMapID(MapView view) {
        try {
            return (short) view.getId();
        } catch (NoSuchMethodError e) {
            try {
                Class<?> MapView = getMapNMS("MapView");
                Object mapID = MapView.getMethod("getId").invoke(view);
                return (short) mapID;
            } catch (Exception ex) {
                return 1;
            }
        }
    }
}
