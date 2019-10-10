package com.gazelle.discovertigo.cosmetics.events;

import com.gazelle.discovertigo.Vertigo;
import com.gazelle.discovertigo.cosmetics.listeners.TrickTreatListener;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Random;
import java.util.UUID;
import java.util.logging.Level;

public class TrickTreatEvent extends Event {

    protected final TrickTreatListener listener;
    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled;
    private boolean done;

    private Player sender;
    private Player receiver;

    protected ItemStack trickItem;
    protected ItemStack treatItem;

    public TrickTreatEvent(Vertigo plugin, TrickTreatListener listener, Player sender, Player receiver){ ;
        this.listener = listener;
        this.sender = sender;
        this.receiver = receiver;

        Material trickMaterial = Material.getMaterial(plugin.getConfigReader().getTrickItemID());
        Material treatMaterial = Material.getMaterial(plugin.getConfigReader().getTreatItemID());

        if (trickMaterial != null && treatMaterial != null){
            trickItem = new ItemStack(trickMaterial, 1);
            treatItem = new ItemStack(treatMaterial, 1);

            if (checkSenderInventory())
                this.run();
            else
                sender.sendMessage(ChatColor.RED + "You can't do that! You're inventory is full!");

        } else {
            this.setCancelled(true);
            plugin.getLogger().log(Level.SEVERE, "Defined items ID in the config.yml are wrong! (Halloween Stuff)");
        }
    }

    private void run(){
        sender.sendMessage(ChatColor.GRAY + "You Trick or Treated " + receiver.getDisplayName());
        receiver.sendMessage(ChatColor.GOLD + sender.getDisplayName() + "just Trick or Treated you!");

        TextComponent msg = new TextComponent("[Trick]");
        msg.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/trick " + sender.getUniqueId() + " " + receiver.getUniqueId()));
        msg.setColor(net.md_5.bungee.api.ChatColor.RED);
        receiver.spigot().sendMessage(msg);

        msg = new TextComponent("[Treat]");
        msg.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/treat " + sender.getUniqueId() + " " + receiver.getUniqueId()));
        msg.setColor(net.md_5.bungee.api.ChatColor.GREEN);
        receiver.spigot().sendMessage(msg);
    }

    public void setCancelled(boolean cancel){
        cancelled = cancel;
    }

    public boolean isCancelled(){
        return cancelled;
    }

    public void setDone(boolean done){
        this.done = done;
    }

    public boolean isDone(){
        return done;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    private boolean checkSenderInventory(){
        boolean trick = false;
        boolean treat = false;

        for (ItemStack item : sender.getInventory().getContents()){

            if (item.isSimilar(trickItem) && item.getAmount() < 64 && !trick){
                trick = true;
            }

            if (item.isSimilar(treatItem) && item.getAmount() < 64 && !treat){
                treat = true;
            }
        }

        if (!trick || !treat){
            if (sender.getInventory().firstEmpty() != -1){
                trick = true;
                treat = true;
            }
        }

        return (trick && treat);
    }
}

class TrickTreatCommand implements CommandExecutor{

    private Player sender;
    private Player receiver;
    private TrickTreatEvent event;

    public TrickTreatCommand(Player sender, Player receiver, TrickTreatEvent event){
        this.sender = sender;
        this.receiver = receiver;
        this.event = event;
    }

    @Override
    public boolean onCommand(CommandSender cmdSender, Command cmd, String s, String[] args) {

        if (cmdSender instanceof Player) {
            if (args.length == 2) {
                if (cmd.getName().equalsIgnoreCase("trick")) {
                    Player sender = Bukkit.getPlayer(UUID.fromString(args[0]));
                    Player receiver = Bukkit.getPlayer(UUID.fromString(args[1]));
                    if (sender != null && receiver != null){
                        if (sendTrickItem())
                            new TrickEffects(receiver).setRandomEffect();
                        else
                            receiver.sendMessage(ChatColor.RED + "You can't do that! You don't have enough items!");
                        event.listener.senders.remove(sender);
                    }
                }

                if (cmd.getName().equalsIgnoreCase("treat")){
                    Player sender = Bukkit.getPlayer(UUID.fromString(args[0]));
                    Player receiver = Bukkit.getPlayer(UUID.fromString(args[1]));
                    if (sender != null && receiver != null){
                        if (!sendTreatItem())
                            receiver.sendMessage(ChatColor.RED + "You can't do that! You don't have enough items!");
                        event.listener.senders.remove(sender);
                    }
                }
            }
        }
        return false;
    }

    private boolean sendTrickItem(){
        for (ItemStack item : receiver.getInventory().getContents()){
            if (item.isSimilar(event.trickItem)){
                if (item.getAmount() >= 1){
                    sender.getInventory().addItem(event.trickItem);
                    receiver.getInventory().remove(event.trickItem);
                    return true;
                }
            }
        }

        return false;
    }

    private boolean sendTreatItem(){
        for (ItemStack item : receiver.getInventory().getContents()){
            if (item.isSimilar(event.treatItem)){
                if (item.getAmount() >= 1){
                    sender.getInventory().addItem(event.treatItem);
                    receiver.getInventory().remove(event.treatItem);
                    return true;
                }
            }
        }

        return false;
    }
}

class TrickTreatRunnable extends BukkitRunnable {

    private Player sender;
    private Player received;

    private TrickTreatEvent event;

    public TrickTreatRunnable(Player sender, Player received, TrickTreatEvent event){
        this.sender = sender;
        this.received = received;
        this.event = event;
    }

    @Override
    public void run() {
        if (!event.isDone()) {
            sender.sendMessage(ChatColor.GOLD + "Your Trick or Treat to " + received.getDisplayName() + " has expired");
            received.sendMessage(ChatColor.GOLD + "Your Trick or Treat from " + sender.getDisplayName() + " has expired");
            event.setCancelled(true);
            event.listener.senders.remove(sender);
        }
    }
}

class TrickEffects {

    private Player target;

    public TrickEffects(Player target){
        this.target = target;
    }

    public void setRandomEffect(){
        Random rnd = new Random();
        switch (rnd.nextInt(5)){
            case 1:
                setOnFire();
                break;
        }
    }

    private void setOnFire(){
        target.setFireTicks(100);
    }

    private void slapPlayer(){
        target.setHealth(target.getHealth() - 2);
        target.playSound(target.getLocation(), Sound.ENTITY_PLAYER_HURT, SoundCategory.MASTER, 100, 1);
    }

    private void setInventoryCobweb(){
        for (int i = 0; i < target.getInventory().getContents().length; i++){
            if (target.getInventory().getItem(i) == null){
                target.getInventory().setItem(i, new ItemStack(Material.WEB, 64));
            }
        }
    }

    private void launchPlayer(){
        target.setVelocity(new Vector(0, 5, 0));
    }
}
