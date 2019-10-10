package com.gazelle.discovertigo.entities;

import org.bukkit.Location;
import org.bukkit.Material;

public class VertigoStrobe {

    private Location glowstone_loc;
    private String state;

    public VertigoStrobe(Location block_loc){
        glowstone_loc = block_loc;
    }

    public boolean isVisible(){
        return state.equals("on");
    }

    public void setVisible(boolean flag){
        if (flag){
            glowstone_loc.getBlock().setType(Material.GLOWSTONE);
            state = "on";
        } else {
            glowstone_loc.getBlock().setType(Material.AIR);
            state = "off";
        }
    }
}
