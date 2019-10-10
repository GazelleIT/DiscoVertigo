package com.gazelle.discovertigo.files;

import com.gazelle.discovertigo.Vertigo;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class YamlManager {

    Vertigo plugin;

    public YamlManager(Vertigo plugin){
        this.plugin = plugin;
    }

    /*
     * File .YML Management Methods
     */
    public FileConfiguration loadFile(String pathFile){
        File file = new File(plugin.getDataFolder(), pathFile);
        if (file.exists()){
            FileConfiguration fc = new YamlConfiguration();
            try {  fc.load(file); }
            catch (Exception ignored){}
            return fc;
        }
        return null;
    }
    public boolean save(FileConfiguration fc, String pathFile) {
        File file = new File(plugin.getDataFolder(), pathFile);

        try { fc.save(file); return true; }
        catch (Exception ignored){ return false; }
    }
    public FileConfiguration createFile(String pathToFile, String pathFromfile){
        File file = new File(plugin.getDataFolder(), pathToFile);
        file.getParentFile().mkdirs();
        copy(plugin.getResource(pathFromfile), file);
        return loadFile(pathToFile);
    }
    private void copy(InputStream in, File file) {
        try {
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while((len=in.read(buf))>0){
                out.write(buf,0,len);
            }
            out.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public int getNextFreeId(String path){
        File[] listFile = new File(plugin.getDataFolder(), path).listFiles();
        int id = 0;
        if (listFile == null){
            return id;
        }
        for (File file : listFile){
            if (id < Integer.parseInt(file.getName().replaceAll(".yml", ""))){
                id = Integer.parseInt(file.getName().replaceAll(".yml", ""));
            }
        }
        return id;
    }
    public Location getLocationFromString(String stringLocation){
        String[] s = stringLocation.split(",");
        try {
            double x = Double.parseDouble(s[0]);
            double y = Double.parseDouble(s[1]);
            double z = Double.parseDouble(s[2]);
            String world = s[3];
            return new Location(plugin.getServer().getWorld(world), x, y, z);
        } catch(Exception ignored){ return null; }
    }
    public String getLocationString(Location location){
        return location.getBlockX() + "," + location.getBlockY() + "," + location.getBlockZ() + "," + location.getWorld().getName();
    }
}
