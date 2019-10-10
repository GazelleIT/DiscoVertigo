package com.gazelle.discovertigo.crystals;

import com.gazelle.discovertigo.Vertigo;
import com.gazelle.discovertigo.beacons.DualSwitchEffect;
import com.gazelle.discovertigo.entities.Stage;
import com.gazelle.discovertigo.entities.VertigoCrystal;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

public class RotateEffect extends BukkitRunnable {

    private final Vertigo plugin;
    private final Stage stage;
    private String name;
    private boolean runned;

    private double i;
    private int radius;
    private int height;

    // Crystal + lastLoc for circle effect
    private LinkedHashMap<VertigoCrystal,Location> crystals;

    public RotateEffect(Vertigo plugin, Stage stage, String effectName, List<VertigoCrystal> crystals, int radius, int height){
        this.plugin = plugin;
        this.stage = stage;
        name = effectName;
        runned = false;
        this.radius = radius;
        this.height = height;

        Location loc;
        this.crystals = new LinkedHashMap<>();

        for (VertigoCrystal crystal : crystals){
            loc = crystal.getCrystalLocation();
            loc.add(0, height, 0);
            this.crystals.put(crystal, loc);
        }

        i = 0.0;
    }

    @Override
    public void run(){

        // Check if the beacons aren't controlled by any other Thread
        if (stage.getCurrentCrystalEffect().equalsIgnoreCase("") && stage.getNextCrystalEffect().equalsIgnoreCase(name)) {

            //Setting "red" semaphore
            stage.setCurrentCrystalEffect(name);

            for (VertigoCrystal crystal : crystals.keySet()){
                drawCircle(crystal, crystals.get(crystal));
            }

            //Setting "green" semaphore
            stage.setCurrentCrystalEffect("");

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
        stage.setNextCrystalEffect(name);

        new BukkitRunnable() {

            @Override
            public void run() {
                // Checking if still on the waiting list
                if (stage.getNextCrystalEffect().equalsIgnoreCase(name)){
                    // Checking if current effect can be run
                    if (stage.getCurrentCrystalEffect().equalsIgnoreCase("")){
                        // Launching the effect
                        new RotateEffect(plugin, stage, name, new ArrayList<>(crystals.keySet()), radius, height);
                        this.cancel();
                    }
                } else {
                    // A new effect got on the list, cancelling
                    this.cancel();
                }
            }

        }.runTaskTimer(plugin, 0, (long) (0.3 * 20));
    }

    private void drawCircle(VertigoCrystal crystal, Location loc){
        double angle = drawAngle();
        double x = radius * Math.sin(angle);
        double z = radius * Math.cos(angle);
        loc = loc.clone().add(radius * Math.sin(angle), 0.0d, radius * Math.cos(angle));

        flashOnCrystal(crystal, loc);

        i += 1;
    }

    private double drawAngle(){
        if (i == 100){ i = 0; }
        return (i / 100) * (2 * Math.PI);
    }

    private void flashOnCrystal(VertigoCrystal crystal, Location loc){
        crystal.setCrystalTarget(loc);
    }

    private void flashOffCrystal(){

    }
}
