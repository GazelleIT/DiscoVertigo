package com.gazelle.discovertigo.entities;

import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class VertigoBeacon {

    private Location beacon_loc;
    private Location block_loc;

    // For GUI: last set color
    private Color s_color;

    public VertigoBeacon(Location beacon_location){
        beacon_loc = beacon_location;
        double x = beacon_loc.getX();
        double y = beacon_loc.getY();
        double z = beacon_loc.getZ();
        block_loc = new Location(beacon_location.getWorld(), x, y + 1, z);
        s_color = Color.WHITE;
    }

    public Color getLastColor(){ return s_color; }

    public Location getBeacon_loc(){ return beacon_loc; }

    @SuppressWarnings("deprecation")
    public void setColor(Color color){
        s_color = getColor();

        switch(color){
            case WHITE: block_loc.getBlock().setType(Material.STAINED_GLASS);
                block_loc.getBlock().setData((byte) 0);
                break;
            case MAGENTA: block_loc.getBlock().setType(Material.STAINED_GLASS);
                block_loc.getBlock().setData((byte) 2);
                break;
            case ORANGE: block_loc.getBlock().setType(Material.STAINED_GLASS);
                block_loc.getBlock().setData((byte) 1);
                break;
            case LIGHT_BLUE: block_loc.getBlock().setType(Material.STAINED_GLASS);
                block_loc.getBlock().setData((byte) 3);
                break;
            case YELLOW: block_loc.getBlock().setType(Material.STAINED_GLASS);
                block_loc.getBlock().setData((byte) 4);
                break;
            case LIGHT_GREEN: block_loc.getBlock().setType(Material.STAINED_GLASS);
                block_loc.getBlock().setData((byte) 5);
                break;
            case PINK: block_loc.getBlock().setType(Material.STAINED_GLASS);
                block_loc.getBlock().setData((byte) 6);
                break;
            case DARK_GRAY: block_loc.getBlock().setType(Material.STAINED_GLASS);
                block_loc.getBlock().setData((byte) 7);
                break;
            case LIGHT_GRAY: block_loc.getBlock().setType(Material.STAINED_GLASS);
                block_loc.getBlock().setData((byte) 8);
                break;
            case CYANO: block_loc.getBlock().setType(Material.STAINED_GLASS);
                block_loc.getBlock().setData((byte) 9);
                break;
            case PURPLE: block_loc.getBlock().setType(Material.STAINED_GLASS);
                block_loc.getBlock().setData((byte) 10);
                break;
            case BLUE: block_loc.getBlock().setType(Material.STAINED_GLASS);
                block_loc.getBlock().setData((byte) 11);
                break;
            case BROWN: block_loc.getBlock().setType(Material.STAINED_GLASS);
                block_loc.getBlock().setData((byte) 12);
                break;
            case DARK_GREEN: block_loc.getBlock().setType(Material.STAINED_GLASS);
                block_loc.getBlock().setData((byte) 13);
                break;
            case RED: block_loc.getBlock().setType(Material.STAINED_GLASS);
                block_loc.getBlock().setData((byte) 14);
                break;
            case BLACK: block_loc.getBlock().setType(Material.STAINED_GLASS);
                block_loc.getBlock().setData((byte) 15);
                break;
            default: block_loc.getBlock().setType(Material.STAINED_CLAY);
                block_loc.getBlock().setData((byte) 9);
        }
    }

    @SuppressWarnings("deprecation")
    public void setVisibleBeam(boolean flag){
        if (!flag){
            block_loc.getBlock().setType(Material.STAINED_CLAY);
            block_loc.getBlock().setData((byte) 9);
            s_color = Color.BLOCKED;
        } else {
            block_loc.getBlock().setType(Material.STAINED_GLASS);
            s_color = Color.WHITE;
        }
    }

    @SuppressWarnings("deprecation")
    public Color getColor(){
        Block block = block_loc.getBlock();
        if (block.getType() == Material.STAINED_GLASS){
            switch (block.getData()){
                case (byte) 0:
                    return Color.WHITE;
                case (byte) 1:
                    return Color.ORANGE;
                case (byte) 2:
                    return Color.MAGENTA;
                case (byte) 3:
                    return Color.LIGHT_BLUE;
                case (byte) 4:
                    return Color.YELLOW;
                case (byte) 5:
                    return Color.LIGHT_GREEN;
                case (byte) 6:
                    return Color.PINK;
                case (byte) 7:
                    return Color.DARK_GRAY;
                case (byte) 8:
                    return Color.LIGHT_GRAY;
                case (byte) 9:
                    return Color.CYANO;
                case (byte) 10:
                    return Color.PURPLE;
                case (byte) 11:
                    return Color.BLUE;
                case (byte) 12:
                    return Color.BROWN;
                case (byte) 13:
                    return Color.DARK_GREEN;
                case (byte) 14:
                    return Color.RED;
                case (byte) 15:
                    return Color.BLACK;
                default:
                    return Color.BLOCKED;
            }
        }
        return Color.BLOCKED;
    }
}
