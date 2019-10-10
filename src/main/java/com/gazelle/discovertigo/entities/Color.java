package com.gazelle.discovertigo.entities;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public enum Color {
    WHITE,
    ORANGE,
    MAGENTA,
    LIGHT_BLUE,
    YELLOW,
    LIGHT_GREEN,
    PINK,
    DARK_GRAY,
    LIGHT_GRAY,
    CYANO,
    PURPLE,
    BLUE,
    BROWN,
    DARK_GREEN,
    RED,
    BLACK,
    BLOCKED;

    private static Color[] colors = Color.values();
    private static List<Color> colorSets = new ArrayList<>();

    public static Color getColor(int index){
        return colors[index];
    }

    public static Color getColor(String colorName){
        for (Color color : colors){
            if (color.toString().equalsIgnoreCase(colorName)){
                return color;
            }
        }
        return null;
    }

    public static void addColorToSet(Color color){
        colorSets.add(color);
    }

    public static void resetColorSet(){
        colorSets.clear();
    }

    public static List<Color> getColorSet(){
        return colorSets;
    }

    public String toColoredString() {
        switch(this) {
            case WHITE: return ChatColor.WHITE + "WHITE";
            case MAGENTA: return ChatColor.LIGHT_PURPLE + "MAGENTA";
            case ORANGE: return ChatColor.GOLD + "ORANGE";
            case LIGHT_BLUE: return ChatColor.AQUA + "LIGHT_BLUE";
            case YELLOW:return ChatColor.YELLOW + "YELLOW";
            case LIGHT_GREEN: return ChatColor.GREEN + "LIGHT_GREEN";
            case PINK: return ChatColor.LIGHT_PURPLE + "PINK";
            case DARK_GRAY: return ChatColor.DARK_GRAY + "DARK_GRAY";
            case LIGHT_GRAY: return ChatColor.GRAY + "LIGHT_GRAY";
            case CYANO: return ChatColor.DARK_AQUA + "CYANO";
            case PURPLE: return ChatColor.DARK_PURPLE + "PURPLE";
            case BLUE: return ChatColor.BLUE + "BLUE";
            case BROWN: return ChatColor.WHITE + "BROWN";
            case DARK_GREEN: return ChatColor.DARK_GREEN + "DARK_GREEN";
            case RED: return ChatColor.RED + "RED";
            case BLACK: return ChatColor.BLACK + "BLACK";
            default: throw new IllegalArgumentException();
        }
    }
}
