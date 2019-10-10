package com.gazelle.discovertigo.entities;

import com.gazelle.discovertigo.Vertigo;
import com.gazelle.discovertigo.beacons.RainbowEffect;
import com.gazelle.discovertigo.beacons.SingleBeaconEffect;
import com.gazelle.discovertigo.crystals.RotateEffect;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Stage {

    private String name;
    // beacons effects
    private String c_effect_b;
    private String n_effect_b;
    // Crystals effects
    private String c_effect_c;
    private String n_effect_c;
    // Strobe effects
    private String c_effect_s;
    private String n_effect_s;
    // Color Theme
    private String l_colorTheme;
    private List<Color> colorTheme;
    private List<String> invalid_colorThemes;

    public Stage(String name, List<String> invalid_colorThemes){
        this.name = name;
        this.c_effect_b = "";
        this.n_effect_b = "";
        this.c_effect_c = "";
        this.n_effect_c = "";
        this.c_effect_s = "";
        this.n_effect_s = "";

        this.l_colorTheme = "";
        this.colorTheme = new ArrayList<>();
        this.invalid_colorThemes = invalid_colorThemes;
    }

    public String getCurrentBeaconEffect(){ return c_effect_b; }
    public void setCurrentBeaconEffect(String effect){ c_effect_b = effect; }
    public String getNextBeaconEffect(){ return n_effect_b; }
    public void setNextBeaconEffect(String effect){ n_effect_b = effect; }

    public String getCurrentCrystalEffect(){ return c_effect_c; }
    public void setCurrentCrystalEffect(String effect){ c_effect_c = effect; }
    public String getNextCrystalEffect(){ return n_effect_c; }
    public void setNextCrystalEffect(String effect){ n_effect_c = effect; }

    public String getCurrentStrobeEffect(){return c_effect_s;}
    public void setCurrentStrobeEffect(String effect){c_effect_s = effect;}
    public String getNextStrobeEffect(){return n_effect_s;}
    public void setNextStrobeEffect(String effect){n_effect_s=effect;}

    public String getName(){ return name; }

    public void setColorTheme(List<Color> colors, String colorThemeName){
        colorTheme = colors;
        l_colorTheme = colorThemeName;
    }
    public List<Color> getColorTheme(){
        return colorTheme;
    }
    public String getLastColorThemeName(){ return l_colorTheme; }

    public boolean isValidColorTheme(String colorThemeName){
        for (String name : invalid_colorThemes){
            if (name.equalsIgnoreCase(colorThemeName))
                return false;
        }
        return true;
    }

}
