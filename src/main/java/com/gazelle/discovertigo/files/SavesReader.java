package com.gazelle.discovertigo.files;

import com.gazelle.discovertigo.Vertigo;
import com.gazelle.discovertigo.entities.Color;
import org.apache.commons.lang.StringUtils;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class SavesReader extends YamlManager{

    private FileConfiguration fc;

    private Vertigo plugin;

    public SavesReader(Vertigo plugin){
        super(plugin);

        fc = loadFile("saves.yml");
        if (fc == null){
            fc = createFile("saves.yml", "saves.yml");
        }
    }

    public void addColorTheme(String stageName, List<Color> colors){
        List<String> sColors = new ArrayList<>();
        for (Color color : colors){ sColors.add(color.name()); }

        for (String v : fc.getConfigurationSection("color_themes").getKeys(false)){
            // Check if stage is already saved
            if (v.equalsIgnoreCase(stageName)){

                int i = existColorTheme(v, sColors);
                if (i != 0)
                    // Saving color theme under an already created stage save
                    saveColorTheme(v, "theme_" + i, sColors);
                return;
            }
        }
        // Saving the new theme under a new stage saves
        saveColorTheme(stageName.toLowerCase(), "theme_1", sColors);
    }

    public void deleteColorTheme(String stageName, List<Color> colors){

    }

    private int existColorTheme(String stageName, List<String> colors){
        List<String> sColors;
        int flag = 0; // Counter for check if theme contains all colors
        int i = 1; // Counter for theme id to save
        // Reading all saved themes
        for (String v : fc.getConfigurationSection("color_themes." + stageName).getKeys(false)){
            sColors = fc.getStringList("color_themes." + stageName + "." + v + ".colors");
            // Checking if the theme contains all the colors we want to save
            for (String color : colors){
                if (sColors.contains(color)){ flag++; }
            }
            if (flag == colors.size()){ return 0; }
            flag = 0;
            i++;
        }
        return i;
    }

    private void saveColorTheme(String stageName, String theme_name, List<String> colors){
        fc.set("color_themes." + "." + stageName + "." + theme_name + ".colors", colors);
    }
}
