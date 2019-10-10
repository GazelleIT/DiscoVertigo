package com.gazelle.discovertigo;

import com.gazelle.discovertigo.cosmetics.listeners.ColoredBallListener;
import com.gazelle.discovertigo.cosmetics.listeners.TrickTreatListener;
import com.gazelle.discovertigo.entities.Stage;
import com.gazelle.discovertigo.entities.VertigoCrystal;
import com.gazelle.discovertigo.files.ConfigReader;
import com.gazelle.discovertigo.listeners.CrystalListener;
import de.slikey.effectlib.EffectLib;
import de.slikey.effectlib.EffectManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public final class Vertigo extends JavaPlugin {

    private List<Stage> stages;
    private Location discospawn;

    private EffectManager effectManager;
    private ConfigReader configReader;

    @Override
    public void onEnable() {
        // Get required plugins
        if (getEffectLib() != null){
            effectManager = new EffectManager(this);
        }

        // Plugin startup logic
        configReader = new ConfigReader(this);
        saveDefaultConfig();
        loadDiscoLights();

        getCommand("test").setExecutor(new Commands(this));
        getCommand("disco").setExecutor(new Commands(this));

        // validating plugin's listeners
        if (configReader.isVertigoCrystalProtected())
            new CrystalListener(this);

        new ColoredBallListener(this);

        // Enabling Halloween
        if (configReader.isHalloweenEnabled())
            enableHalloween();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        effectManager.dispose();
    }

    private void loadDiscoLights(){
        stages = new ArrayList<>();
        for (String stage : configReader.getStagesName()){

            stages.add(new Stage(stage, checkThemeColors(stage)));

            getLogger().info("loaded stage: \"" + stage
                    + "\" with:");
            if (!checkThemeColors(stage).isEmpty()){
                StringBuilder sb = new StringBuilder();
                sb.append("Cannot load");
                for (String s : checkThemeColors(stage)){
                    sb.append(" ");
                    sb.append(s);
                }
                sb.append(" ThemeColors because of incorrect colors's syntax");
                getLogger().log(Level.WARNING, sb.toString());
            }
            getLogger().info(configReader.getStageBeacons(stage).size()
                    + " Beacons");
            getLogger().info(configReader.getStageCrystals(stage).size()
                    + " Crystals");

            // Spawning crystals if needed
            loadCrystals();

            if (getConfigReader().isValidScreenBlock()){

            }
        }
    }

    private void loadCrystals(){
        for (Stage stage : stages){
            for (VertigoCrystal crystal : getConfigReader().getStageCrystals(stage.getName())){
                crystal.spawnCrystal();
            }
        }
    }

    private void enableHalloween(){
        getLogger().log(Level.INFO, "Halloween mode enabled");
        new TrickTreatListener(this);
    }

    private List<String> checkThemeColors(String stageName){
        List<String> valid = new ArrayList<>();
        for (String themeColor : getConfigReader().getStageColorThemeNames(stageName)){
            if (!getConfigReader().isValidThemeColor(stageName, themeColor))
                valid.add(themeColor);
        }
        return valid;
    }

    public ConfigReader getConfigReader(){ return configReader; }

    public Location getDiscoSpawn(){
        return discospawn;
    }

    public Stage getStage(String stageName){
        for (Stage stage : stages){
            if (stage.getName().equalsIgnoreCase(stageName)){
                return stage;
            }
        }
        return null;
    }

    public EffectManager getEffectManager() {
        return effectManager;
    }

    private EffectLib getEffectLib() {
        Plugin effectLib = Bukkit.getPluginManager().getPlugin("EffectLib");
        if (!(effectLib instanceof EffectLib)) {
            return null;
        }
        return (EffectLib)effectLib;
    }


}
