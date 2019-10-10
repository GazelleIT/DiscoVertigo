package com.gazelle.discovertigo.listeners;

import com.gazelle.discovertigo.Vertigo;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.List;

public class CrystalListener implements Listener {

    private Vertigo plugin;

    public CrystalListener(Vertigo plugin){
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onDamageEvent(EntityDamageEvent event){
        if (event.getEntityType().equals(EntityType.ENDER_CRYSTAL)){
            List<String> worlds = plugin.getConfig().getStringList("enabled_worlds");
            if (worlds != null){
                if (worlds.contains(event.getEntity().getWorld().getName())){
                    event.setCancelled(true);
                }
            }
        }
    }
}
