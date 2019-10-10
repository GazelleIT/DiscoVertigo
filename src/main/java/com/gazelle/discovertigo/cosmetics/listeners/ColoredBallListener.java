package com.gazelle.discovertigo.cosmetics.listeners;

import com.gazelle.discovertigo.Vertigo;
import com.gazelle.discovertigo.cosmetics.effects.ColoredBall;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ColoredBallListener implements Listener {

    private Vertigo plugin;

    public ColoredBallListener(Vertigo plugin){
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler (priority = EventPriority.LOW)
    public void onPlayerLaunchingBall(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if (player != null){
            if (event.getAction().equals(Action.RIGHT_CLICK_AIR)){
                ItemStack item = player.getInventory().getItemInMainHand();
                if (item.getType().equals(Material.SKULL_ITEM) && item.hasItemMeta()){
                    String scolor = player.getInventory().getItemInMainHand().getItemMeta().getDisplayName();
                    scolor = scolor.replaceAll("Ball", "").toLowerCase();
                    scolor = scolor.replaceAll(" ", "");
                    Color color;
                    switch (scolor){
                        case "red":
                            color = Color.RED;
                            break;
                        case "green":
                            color = Color.GREEN;
                            break;
                        case "lime":
                            color = Color.LIME;
                            break;
                        case "blue":
                            color  = Color.BLUE;
                            break;
                        case "purple":
                            color = Color.FUCHSIA;
                            break;
                        case "yellow":
                            color = Color.YELLOW;
                            break;
                        default:
                            color = null;
                    }
                    if (color != null) {
                        ColoredBall ball = new ColoredBall(plugin, player, 2, 30);
                        ball.playEffect(color);
                    }
                }
            }
        }
    }
}
