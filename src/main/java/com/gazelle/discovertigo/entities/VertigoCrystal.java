package com.gazelle.discovertigo.entities;

import org.bukkit.Location;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public class VertigoCrystal {

    private EnderCrystal crystal;
    private Location location;

    public VertigoCrystal(Location loc){
       location = loc;
       for (Entity entity : loc.getWorld().getEntities()){
           if (entity instanceof EnderCrystal){
               if (entity.getLocation().equals(loc))
                   crystal = (EnderCrystal) entity;
           }
       }
       if (crystal == null)
           spawnCrystal();
    }

    public void setCrystalLocation(Location loc){
        for (Entity entity : loc.getWorld().getEntities()){
            if (entity instanceof EnderCrystal){
                if (entity.getLocation().equals(loc)){
                    entity.remove();
                }
            }
        }
        crystal = (EnderCrystal) loc.getWorld().spawnEntity(loc, EntityType.ENDER_CRYSTAL);
        crystal.setShowingBottom(false);
    }

    public void destroy(){
        for (Entity entity : location.getWorld().getEntities()){
            if (entity instanceof EnderCrystal){
                if (entity.getLocation().equals(location)){
                    entity.remove();
                }
            }
        }
    }

    public void spawnCrystal(){
        for (Entity entity : location.getWorld().getEntities()){
            if (entity instanceof EnderCrystal){
                if (entity.getLocation().equals(location)){
                    entity.remove();
                }
            }
        }
        crystal = (EnderCrystal) location.getWorld().spawnEntity(location, EntityType.ENDER_CRYSTAL);
        crystal.setShowingBottom(false);
    }

    public Location getCrystalLocation(){
        if (crystal != null)
            return crystal.getLocation();
        else
            return null;
    }

    public void setCrystalTarget(Location loc){
        if (loc == null)
            spawnCrystal();
        else if (crystal != null)
            crystal.setBeamTarget(loc);
    }

    public Location getCrystalTarget(){
        if (crystal != null)
            return crystal.getBeamTarget();
        else
            return null;
    }
}
