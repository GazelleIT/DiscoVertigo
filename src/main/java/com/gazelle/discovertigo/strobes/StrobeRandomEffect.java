package com.gazelle.discovertigo.strobes;

import com.gazelle.discovertigo.Vertigo;
import com.gazelle.discovertigo.entities.Stage;
import com.gazelle.discovertigo.entities.VertigoStrobe;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Random;

public class StrobeRandomEffect extends BukkitRunnable {

    private List<VertigoStrobe> strobes;
    private Stage stage;

    public StrobeRandomEffect(List<VertigoStrobe> strobes, Stage stage){
        this.strobes = strobes;
        this.stage = stage;
    }

    @Override
    public void run() {
        // Check if the beacons aren't controlled by any other Thread
        if (stage.getCurrentStrobeEffect().equalsIgnoreCase("")) {

            //Setting "red" semaphore
            stage.setCurrentStrobeEffect("stroberandomeffect");

            playEffect(getRandomStrobe());

            //Setting "green" semaphore
            stage.setCurrentStrobeEffect("");

            // Next this Thread will wait x seconds
        } else {
            // Delete this Thread
            this.cancel();
        }
    }

    private VertigoStrobe getRandomStrobe(){
        int rnd = new Random().nextInt(strobes.size());
        return strobes.get(rnd);
    }

    private void playEffect(VertigoStrobe strobe){
        if (strobe.isVisible()){
            strobe.setVisible(false);
        } else {
            strobe.setVisible(true);
        }
    }
}
