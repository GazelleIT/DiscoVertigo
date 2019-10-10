package com.gazelle.discovertigo.beacons;

import com.gazelle.discovertigo.Vertigo;
import com.gazelle.discovertigo.entities.Color;
import com.gazelle.discovertigo.entities.Stage;
import com.gazelle.discovertigo.entities.VertigoBeacon;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Random;

public class SingleBeaconEffect extends BukkitRunnable {

    private List<VertigoBeacon> beacons;
    private List<Color> colors;
    private final Vertigo plugin;
    private final Stage stage;
    private String name;
    private boolean runned;

    private VertigoBeacon lastBeacon;
    private int lastBeaconIndex;
    private long lastBeaconDelay;
    private boolean mode;

    public SingleBeaconEffect(Vertigo plugin, Stage stage, List<VertigoBeacon> beacons, List<Color> colors, String effectName, long lastBeaconDelay, boolean mode){
        this.plugin = plugin;
        this.stage = stage;
        this.beacons = beacons;
        this.name = effectName;
        runned = false;
        this.lastBeaconDelay = lastBeaconDelay;
        this.colors = colors;
        lastBeaconIndex = 0;
        this.mode = mode;
    }

    @Override
    public void run(){

        // Check if the beacons aren't controlled by any other Thread
        if (stage.getCurrentBeaconEffect().equalsIgnoreCase("") && stage.getNextBeaconEffect().equalsIgnoreCase(name)) {

            // Setting "red" semaphore
            stage.setCurrentBeaconEffect(name);

            // Switching off last beacon, in case "mode" set to "false"
            setOffLastBeacon();

            // Target a new beacon to turn on
            setRandomBeacon();

            // Coloring with a random color selected by "colors" pool the beacon
            setColorToBeacon();

            // Checking the "mode" of the effect, if true a runnable will start
            checkMode();

            // Setting "green" semaphore
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
                        new SingleBeaconEffect(plugin, stage, beacons, colors, name, lastBeaconDelay, mode);
                        this.cancel();
                    }
                } else {
                    // A new effect got on the list, cancelling
                    this.cancel();
                }
            }

        }.runTaskTimer(plugin, 0, (long) (0.3 * 20));
    }

    /**
     * Set a random VertigoBeacon, but different from last switched on
     */
    private void setRandomBeacon(){
        Random rand = new Random();
        int index;
        do{ index = rand.nextInt(beacons.size()); } while(index == lastBeaconIndex);
        lastBeaconIndex = index;
        lastBeacon = beacons.get(index);
    }

    private void setColorToBeacon(){
        Random rand = new Random();
        int index = rand.nextInt(colors.size());
        lastBeacon.setColor(colors.get(index));
    }

    private void checkMode(){
        if (mode)
            runDelayedOff();
    }

    private void runDelayedOff(){
        new BukkitRunnable() {

            @Override
            public void run() {
                lastBeacon.setVisibleBeam(false);
            }

        }.runTaskLater(this.plugin, lastBeaconDelay);
    }

    private void setOffLastBeacon(){
        if (!mode && lastBeacon != null)
            lastBeacon.setVisibleBeam(false);
    }

}
