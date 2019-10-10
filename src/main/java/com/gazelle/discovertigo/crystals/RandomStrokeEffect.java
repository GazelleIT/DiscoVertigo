package com.gazelle.discovertigo.crystals;

import com.gazelle.discovertigo.Vertigo;
import com.gazelle.discovertigo.entities.Stage;
import com.gazelle.discovertigo.entities.VertigoCrystal;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

public class RandomStrokeEffect extends BukkitRunnable {

    private final Vertigo plugin;
    private final Stage stage;
    private String name;
    private boolean runned;
    // Crystal + lastLoc
    private LinkedHashMap<VertigoCrystal,Location> crystals;

    private int radius;
    private int lenght;
    private int iterations;

    private StrokeType strokeType;
    private Coordinates coordinate;

    public enum StrokeType {
        DOWNWARD,
        FORWARD,
        UPWARD,
    }
    public enum Coordinates {
        NORTH,
        EAST,
        SOUTH,
        WEST
    }

    public RandomStrokeEffect(Vertigo plugin, Stage stage, String effectName, List<VertigoCrystal> crystals, int radius, int lenght, int iterations, StrokeType strokeType, Coordinates coordinate){
        this.plugin = plugin;
        this.stage = stage;
        name = effectName;
        this.strokeType = strokeType;
        this.coordinate = coordinate;
        runned = false;
        this.radius = radius;
        this.lenght = lenght;
        this.iterations = iterations;

        Location loc;
        this.crystals = new LinkedHashMap<>();

        for (VertigoCrystal crystal : crystals){
            loc = crystal.getCrystalLocation();
            loc.add(0, lenght, 0);
            this.crystals.put(crystal, loc);
        }

    }

    @Override
    public void run(){

        // Check if the beacons aren't controlled by any other Thread
        if (stage.getCurrentCrystalEffect().equalsIgnoreCase("") && stage.getNextCrystalEffect().equalsIgnoreCase(name)) {

            //Setting "red" semaphore
            stage.setCurrentCrystalEffect(name);

            drawEffect();

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
                        //new RandomStrokeEffect(plugin, stage, name, crystals, radius, lenght, iterations, strokeType, coordinate);
                        this.cancel();
                    }
                } else {
                    // A new effect got on the list, cancelling
                    this.cancel();
                }
            }

        }.runTaskTimer(plugin, 0, (long) (0.3 * 20));
    }


    private void drawEffect(){
        for (VertigoCrystal crystal : crystals.keySet()){
            setBeamTarget(getRandomLocation(crystal), crystal);
        }
    }

    private void setBeamTarget(Location loc, VertigoCrystal crystal){
        crystal.setCrystalTarget(loc.add(0, lenght, 0));
    }

    private Location[] getDownwardLocations(VertigoCrystal crystal){
        Location[] values = new Location[(((radius * 3) - (radius)) + (((radius - 1) * 2))) + 1];
        int c = 0;

        values[c++] = getCentralLocation(crystal);

        for (int i = 1; i <= radius; i++){
            if (coordinate.equals(Coordinates.EAST) || coordinate.equals(Coordinates.WEST)){
                values[c++] = getCentralLocation(crystal).add(0, 0, +i);
                values[c++] = getCentralLocation(crystal).add(0, 0, -i);
            } else {
                values[c++] = getCentralLocation(crystal).add(+i, 0, 0);
                values[c++] = getCentralLocation(crystal).add(-i, 0, 0);
            }
        }

        if (radius > 1){
            for (int i = 1; i < radius; i++){
                if (coordinate.equals(Coordinates.EAST) || coordinate.equals(Coordinates.WEST)){
                    values[c++] = getCentralLocation(crystal).add(getLateralMath(i), 0, +radius);
                    values[c++] = getCentralLocation(crystal).add(getLateralMath(i), 0, -radius);
                } else {
                    values[c++] = getCentralLocation(crystal).add(+radius, 0, getLateralMath(i));
                    values[c++] = getCentralLocation(crystal).add(-radius, 0, getLateralMath(i));
                }
            }
        }
        return values;
    }

    private Location getCentralLocation(VertigoCrystal crystal){
        switch(coordinate){
            case EAST:
                return crystal.getCrystalLocation().add(+ radius, - radius, 0);
            case WEST:
                return crystal.getCrystalLocation().add(- radius, - radius, 0);
            case NORTH:
                return crystal.getCrystalLocation().add(0, - radius, - radius);
            case SOUTH:
                return crystal.getCrystalLocation().add(0, - radius, + radius);
            default:
                return crystal.getCrystalLocation();
        }
    }

    private int getLateralMath(int value){
        switch(coordinate){
            case EAST:
                return - value;
            case WEST:
                return + value;
            case NORTH:
                return + value;
            case SOUTH:
                return - value;
            default:
                return value;
        }
    }

    private Location getRandomLocation(VertigoCrystal crystal){
        Random rnd = new Random();
        Location[] locs = new Location[0];

        switch (strokeType){
            case UPWARD:
                break;
            case FORWARD:
                break;
            case DOWNWARD:
                locs = getDownwardLocations(crystal);
                break;
            default:
                break;
        }

        Location loc = null;
        do {
            int v = rnd.nextInt(locs.length - 1);
            if (v >= 0)
                loc = locs[v];
        } while (loc == crystals.get(crystal) && loc == null && crystals.get(crystal).distance(loc) >= iterations);
        return loc;
    }


}
