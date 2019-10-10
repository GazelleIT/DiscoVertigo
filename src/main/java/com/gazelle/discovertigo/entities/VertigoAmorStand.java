package com.gazelle.discovertigo.entities;

import com.gazelle.discovertigo.Vertigo;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.util.EulerAngle;

public class VertigoAmorStand {

    private Location location;
    private ArmorStand entity;

    public VertigoAmorStand(Location loc){
        location = loc;

        for (Entity entity : loc.getWorld().getEntities()){
            if (entity instanceof ArmorStand){
                if (entity.getLocation().equals(loc))
                    this.entity = (ArmorStand) entity;
            }
        }
        if (entity == null)
            spawnArmorStand();
    }

    public void spawnArmorStand(){
        for (Entity entity : location.getWorld().getEntities()){
            if (entity instanceof ArmorStand){
                if (entity.getLocation().equals(location)){
                    entity.remove();
                }
            }
        }
        entity = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ENDER_CRYSTAL);
    }
}
