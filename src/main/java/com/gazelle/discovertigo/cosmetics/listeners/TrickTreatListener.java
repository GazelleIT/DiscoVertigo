package com.gazelle.discovertigo.cosmetics.listeners;

import com.gazelle.discovertigo.Vertigo;
import com.gazelle.discovertigo.cosmetics.events.TrickTreatEvent;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.List;

public class TrickTreatListener implements Listener {

    private Vertigo plugin;
    public List<Player> senders;

    public TrickTreatListener(Vertigo plugin){
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler (priority = EventPriority.LOW)
    public void onPlayerClickEvent(PlayerInteractEntityEvent event){
        if (event.getRightClicked().getType() == EntityType.PLAYER) {
            Player player = event.getPlayer();
            Player clickPlayer = (Player) event.getRightClicked();
            if (player != null) {
                if (player.getInventory().getItemInMainHand().hasItemMeta()) {
                    if (StringUtils.containsIgnoreCase(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName(), plugin.getConfigReader().getHalloweenBasketName())) {
                        if (!senders.contains(player)) {
                            senders.add(player);
                            new TrickTreatEvent(plugin, this, player, clickPlayer);
                        } else {
                            player.sendMessage(ChatColor.RED + "You must wait 10 seconds before you can do another Trick or Treat");
                        }
                    }
                }
            }
        }
    }
}
