package com.gazelle.discovertigo.entities;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

public class VertigoScreen {

    private Location block;
    private ArmorStand entity;

    public VertigoScreen(Location centerblock){
        block = centerblock;

        entity = getEntityWorld();
        if (entity == null){
            entity = (ArmorStand) block.getWorld().spawnEntity(block, EntityType.ARMOR_STAND);
            entity.setVisible(false);
            entity.setInvulnerable(true);
        }
    }

    public void setScreenType(Material material){
        entity.setHelmet(new ItemStack(material));
    }


    public void destroy(){
        entity.remove();
    }

    public boolean isScreenVisible(){
        return entity.getHelmet() != null;
    }

    public ArmorStand getScreenEntity(){
        return entity;
    }
    public Location getCenterBlock(){
        return block.add(0, 1, 0);
    }

    private ArmorStand getEntityWorld(){
        for (Entity entity : block.getWorld().getEntities()){
            if (entity instanceof ArmorStand){
                if (entity.getLocation().equals(block)){
                    return (ArmorStand) entity;
                }
            }
        }
        return null;
    }
}
