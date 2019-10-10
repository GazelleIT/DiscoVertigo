package com.gazelle.discovertigo.beacons;

import com.gazelle.discovertigo.Vertigo;
import com.gazelle.discovertigo.entities.Color;
import com.gazelle.discovertigo.entities.Stage;
import com.gazelle.discovertigo.entities.VertigoBeacon;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;;

import java.util.List;

public class RainbowEffect extends BukkitRunnable {

    private List<VertigoBeacon> beacons;
    private final Vertigo plugin;
    private final Stage stage;
    private String name;
    private boolean runned;

    private int colorIndex;
    private int beaconIndex;

    public RainbowEffect(Vertigo plugin, Stage stage, List<VertigoBeacon> beacons, String effectName){
        this.plugin = plugin;
        this.beacons = beacons;
        this.stage = stage;
        name = effectName;
        runned = false;

        colorIndex = 1;
        beaconIndex = 0;
    }

    @Override
    public void run(){

        // Check if the beacons aren't controlled by any other Thread
        if (stage.getCurrentBeaconEffect().equalsIgnoreCase("") && stage.getNextBeaconEffect().equalsIgnoreCase(name)) {

            //Setting "red" semaphore
            stage.setCurrentBeaconEffect(name);

            // Checking indexes
            setIndexes();

            // Changing color of next beacon and updating indexes
            beacons.get(beaconIndex++).setColor(Color.getColor(colorIndex));

            //Setting "green" semaphore
            stage.setCurrentBeaconEffect("");

            // Setting "true", so this effect knows it has ran before
            runned = true;

            // Next this Thread will wait x seconds
        } else {
            if (!runned) {
                // Add this Thread to the waiting list
                this.runWaitingList();

            }
            // This effect has already been used, so it get removed
            this.cancel();
        }
    }

    private void runWaitingList(){
        // Requesting effect getting on waiting list
        stage.setNextBeaconEffect(name);

        new BukkitRunnable() {

            @Override
            public void run() {
                // Checking if still on the waiting list
                if (stage.getNextBeaconEffect().equalsIgnoreCase(name)){
                    // Checking if current effect can be run
                    if (stage.getCurrentBeaconEffect().equalsIgnoreCase("")){
                        // Launching the effect
                        new RainbowEffect(plugin, stage, beacons, name);
                        this.cancel();
                    }
                } else {
                    // A new effect got on the list, cancelling
                    this.cancel();
                }
            }

        }.runTaskTimer(plugin, 0, (long) (0.3 * 20));
    }

    private void setIndexes(){
        if (beaconIndex >= beacons.size()){
            beaconIndex = 0;
            colorIndex++;
        }
        if (colorIndex >= 15){
            colorIndex = 1;
        }
    }
}
