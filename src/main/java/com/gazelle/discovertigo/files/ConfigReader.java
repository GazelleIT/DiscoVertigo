package com.gazelle.discovertigo.files;

import com.gazelle.discovertigo.Vertigo;
import com.gazelle.discovertigo.crystals.CustomPathEffect;
import com.gazelle.discovertigo.entities.*;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.*;
import java.util.logging.Level;

public class ConfigReader{

    private final Vertigo plugin;
    private final FileConfiguration config;

    public ConfigReader(Vertigo plugin){
        this.plugin = plugin;
        config = plugin.getConfig();
    }

    public Location getLocationFromString(String stringLocation){
        String[] s = stringLocation.split(",");
        double x = Double.parseDouble(s[0]);
        double y = Double.parseDouble(s[1]);
        double z = Double.parseDouble(s[2]);
        String world = s[3];
        return new Location(plugin.getServer().getWorld(world), x, y, z);
    }
    public String getLocationString(Location location){
        return location.getBlockX() + "," + location.getBlockY() + "," + location.getBlockZ() + "," + location.getWorld().getName();
    }

    // Checking if crystals are protected, if user didn't define it, it will be true for default
    public boolean isVertigoCrystalProtected(){
        for (String values : config.getKeys(false)){
            if (values.equalsIgnoreCase("protect_endercrystal")){
                return config.getBoolean(values);
            }
        }
        return true;
    }

    // Checking if colored balls are enabled, if user didn't define it, it will be true for default
    public boolean isVertigoColoredBallEnabled(){
        for (String values : config.getKeys(false)){
            if (values.equalsIgnoreCase("enable_coloredballs") || values.equalsIgnoreCase("enable_coloredball")){
                return config.getBoolean(values);
            }
        }
        return true;
    }

    public int getCrystalsRadius(){
        for (String values : config.getKeys(false)){
            if (values.equalsIgnoreCase("crystals_radius") || values.equalsIgnoreCase("crystal_radius")){
                return config.getInt(values);
            }
        }
        return 3;
    }

    public int getCrystalsIterations(){
        for (String values : config.getKeys(false)){
            if (values.equalsIgnoreCase("crystals_iterations") || values.equalsIgnoreCase("crystal_iterations")){
                return config.getInt(values);
            }
        }
        return 1;
    }
    public int getCrystalsBeamLenght(){
        for (String values : config.getKeys(false)){
            if (values.equalsIgnoreCase("crystals_beam_lenght") || values.equalsIgnoreCase("crystal_beam_lenght")){
                return config.getInt(values);
            }
        }
        return 20;
    }

    public List<String> getStagesName(){
        Set<String> names = new HashSet<>();
        for (String value : config.getKeys(false)){
            if (value.equalsIgnoreCase("stagenames")|| value.equalsIgnoreCase("stagename")){
                names.addAll(config.getStringList(value));
            }
        }
        return new ArrayList<>(names);
    }

    public List<VertigoBeacon> getStageBeacons(String stageName){
        List<VertigoBeacon> beacons = new ArrayList<>();
        Location loc;

        for (String path : config.getKeys(true)){
            if (StringUtils.containsIgnoreCase(path, "beacons") || StringUtils.containsIgnoreCase(path, "beacon")){
                if (StringUtils.containsIgnoreCase(path, stageName)){
                    for (String sLoc : config.getStringList(path)){
                        loc = getLocationFromString(sLoc);
                        if (loc != null){
                            beacons.add(new VertigoBeacon(loc));
                        }
                    }
                }
            }
        }
        return beacons;
    }

    public List<VertigoCrystal> getStageCrystals(String stageName){
        List<VertigoCrystal> crystals = new ArrayList<>();
        Location loc;

        for (String path : config.getKeys(true)){
            if (StringUtils.containsIgnoreCase(path, "crystals") || StringUtils.containsIgnoreCase(path, "crystal")){
                if (!StringUtils.containsIgnoreCase(path, "custom")) {
                    if (StringUtils.containsIgnoreCase(path, stageName)) {
                        for (String sLoc : config.getStringList(path)) {
                            loc = getLocationFromString(sLoc);
                            if (loc != null) {
                                crystals.add(new VertigoCrystal(loc));
                            }
                        }
                    }
                }
            }
        }
        return crystals;
    }

    public List<VertigoStrobe> getStageStrobes(String stageName){
        List<VertigoStrobe> strobes = new ArrayList<>();
        Location loc;

        for (String path : config.getKeys(true)){
            if (StringUtils.containsIgnoreCase(path, "glowstone")){
                if (StringUtils.containsIgnoreCase(path, stageName)){
                    for (String sLoc : config.getStringList(path)){
                        loc = getLocationFromString(sLoc);
                        if (loc != null){
                            strobes.add(new VertigoStrobe(loc));
                        }
                    }
                }
            }
        }
        return strobes;
    }

    public List<VertigoFountain> getStageFountains(String stageName){
        List<VertigoFountain> fountains = new ArrayList<>();
        Location loc;

        for (String path : config.getKeys(true)){
            if (StringUtils.containsIgnoreCase(path, "glowstone")){
                if (StringUtils.containsIgnoreCase(path, stageName)){
                    for (String sLoc : config.getStringList(path)){
                        loc = getLocationFromString(sLoc);
                        if (loc != null){
                            fountains.add(new VertigoFountain(plugin, loc));
                        }
                    }
                }
            }
        }
        return fountains;
    }

    public List<VertigoScreen> getStageScreens(String stageName){
        List<VertigoScreen> screens = new ArrayList<>();
        Location loc;

        for (String path : config.getKeys(true)){
            if (StringUtils.containsIgnoreCase(path, "screens")){
                if (StringUtils.containsIgnoreCase(path, stageName)){
                    for (String sLoc : config.getStringList(path)){
                        loc = getLocationFromString(sLoc);
                        if (loc != null){
                            screens.add(new VertigoScreen(loc));
                        }
                    }
                }
            }
        }
        return screens;
    }

    public List<Material> getScreenMaterials(){
        List<Material> materials = new ArrayList<>();
        Material material;

        for (String path : config.getKeys(true)){
            if (StringUtils.containsIgnoreCase(path, "screen_blocks")){
                for (String sMat : config.getStringList(path)){
                    material = Material.getMaterial(sMat);
                    if (material != null){
                        materials.add(material);
                    }
                }
            }
        }
        return materials;
    }

    public String getGUIName(String stageName){
        for (String path : config.getKeys(true)){
            if (StringUtils.containsIgnoreCase(path, "gui")){
                if (StringUtils.containsIgnoreCase(path, "stagename")){
                    if (stageName.equals(config.getString(path))){
                        for (String v : config.getKeys(false)){
                            if (v.equalsIgnoreCase("gui")){
                                return (String) config.getConfigurationSection(v).getKeys(false).toArray()[0];
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    public int getGUISize(String GuiName){
        for (String path : config.getKeys(true)){
            if (StringUtils.containsIgnoreCase(path, "gui")){
                if (StringUtils.containsIgnoreCase(path, GuiName)){
                    if (StringUtils.containsIgnoreCase(path, "row")){
                        return config.getInt(path);
                    }
                }
            }
        }
        return 0;
    }

    public List<Integer> getGUIBeaconsSlots(String GuiName){
        for (String path : config.getKeys(true)){
            if (StringUtils.containsIgnoreCase(path, "gui")){
                if (StringUtils.containsIgnoreCase(path, GuiName)){
                    if (StringUtils.containsIgnoreCase(path, "beacon")||StringUtils.containsIgnoreCase(path, "beacons")){
                        return config.getIntegerList(path);
                    }
                }
            }
        }
        return null;
    }

    public List<Integer> getGUICrystalsSlots(String GuiName){
        for (String path : config.getKeys(true)){
            if (StringUtils.containsIgnoreCase(path, "gui")){
                if (StringUtils.containsIgnoreCase(path, GuiName)){
                    if (StringUtils.containsIgnoreCase(path, "crystal")||StringUtils.containsIgnoreCase(path, "crystals")){
                        return config.getIntegerList(path);
                    }
                }
            }
        }
        return null;
    }

    public LinkedHashMap<VertigoBeacon, Integer> getBeaconsSlotsHashMap(String stageName){
        String guiName = getGUIName(stageName);
        int i = 0;
        LinkedHashMap<VertigoBeacon, Integer> map = new LinkedHashMap<>();
        List<Integer> slots = getGUIBeaconsSlots(guiName);
        List<VertigoBeacon> beacons = getStageBeacons(stageName);
        if (slots != null && beacons != null) {
            if (beacons.size() == slots.size()) {
                for (VertigoBeacon beacon : beacons) {
                    map.put(beacon, slots.get(i++));
                }
                return map;
            }
        }
        return null;
    }

    public LinkedHashMap<VertigoCrystal, Integer> getCrystalSlotsHashMap(String stageName){
        String guiName = getGUIName(stageName);
        int i = 0;
        LinkedHashMap<VertigoCrystal, Integer> map = new LinkedHashMap<>();
        List<Integer> slots = getGUICrystalsSlots(guiName);
        List<VertigoCrystal> crystals = getStageCrystals(stageName);
        if (slots != null && crystals != null) {
            if (slots.size() == crystals.size()) {
                for (VertigoCrystal crystal : crystals) {
                    map.put(crystal, slots.get(i++));
                }
                return map;
            }
        }
        return null;
    }

    public List<String> getStageColorThemeNames(String stageName){
        Set<String> names = new HashSet<>();
        for (String path : config.getKeys(true)){
            if (path.equalsIgnoreCase("color_theme." + stageName) ||
            path.equalsIgnoreCase("color_themes." + stageName)){
                names.addAll(config.getConfigurationSection(path).getKeys(false));
            }
        }
        return new ArrayList<>(names);
    }

    public List<Color> getStageColorTheme(String stageName, String colorThemeName){
        List<Color> colors = new ArrayList<>();
        for (String path : config.getKeys(true)){
            if (StringUtils.containsIgnoreCase(path, "color_themes") ||
                    StringUtils.containsIgnoreCase(path, "color_theme")){
                if (StringUtils.containsIgnoreCase(path, stageName)){
                    if (StringUtils.containsIgnoreCase(path, colorThemeName)) {
                        for (String color : config.getStringList(path)) {
                            colors.add(Color.getColor(color));
                        }
                    }
                }
            }
        }
        return colors;
    }

    public List<String> getStageCustomCrystalsNames(String stageName){
        Set<String> names = new HashSet<>();
        for (String path : config.getKeys(true)){
            if (path.equalsIgnoreCase("custom_crystals." + stageName) ||
                    path.equalsIgnoreCase("custom_crystal." + stageName)){
                names.addAll(config.getConfigurationSection(path).getKeys(false));
            }
        }
        return new ArrayList<>(names);
    }

    public List<CustomPathEffect> getStageCustomCrystals(String stageName, String customCrystalName){
        List<CustomPathEffect> effects = new ArrayList<>();
        for (String path : config.getKeys(true)){
            if (StringUtils.containsIgnoreCase(path, "custom_crystals") ||
                    StringUtils.containsIgnoreCase(path, "custom_crystal")){
                if (StringUtils.containsIgnoreCase(path, stageName)){
                    if (StringUtils.containsIgnoreCase(path, customCrystalName)) {
                        Set<String> values = config.getConfigurationSection(path).getKeys(false);
                        String[] strings;
                        Set<String> crystals = new HashSet<>();
                        List<VertigoCrystal> vcrystals = getStageCrystals(stageName);
                        List<VertigoCrystal> crystalsf = new ArrayList<>();
                        for (String value : values) {
                            //plugin.getServer().getConsoleSender().sendMessage("new Value: " + value);
                            strings = value.split(",");
                            //plugin.getServer().getConsoleSender().sendMessage("position numbers: " + strings.length);
                            crystals.addAll(Arrays.asList(strings));

                            for (String pos : crystals) {
                                try {
                                    //plugin.getServer().getConsoleSender().sendMessage("Assigned Position: " + pos);
                                    //plugin.getServer().getConsoleSender().sendMessage("Assigned Crystal:" + vcrystals.get(Integer.parseInt(pos) - 1).getCrystalLocation().toString());
                                    crystalsf.add(vcrystals.get(Integer.parseInt(pos) - 1));
                                } catch (NumberFormatException ex){
                                    plugin.getLogger().log(Level.SEVERE, ex.getMessage());
                                    return null;
                                }
                            }

                            for (VertigoCrystal crystal : crystalsf){
                                //plugin.getServer().getConsoleSender().sendMessage("Testing Crystals: " + crystal.getCrystalLocation().toString());
                            }
                            effects.add(new CustomPathEffect(plugin, plugin.getStage(stageName), "cPath" + customCrystalName, crystalsf, config.getStringList(path + "." + value), 0, value));

                            crystalsf = new ArrayList<>();
                            crystals.clear();
                        }
                        return effects;
                    }
                }
            }
        }
        return null;
    }

    /**
     *
     * @return if the ThemeColor has valid colors written in the config
     */
    public boolean isValidThemeColor(String stageName, String colorThemeName){
        for (String path : config.getKeys(true)){
            if (StringUtils.containsIgnoreCase(path, "color_themes") ||
                    StringUtils.containsIgnoreCase(path, "color_theme")){
                if (StringUtils.containsIgnoreCase(path, stageName)){
                    if (StringUtils.containsIgnoreCase(path, colorThemeName)) {
                        for (String color : config.getStringList(path)) {
                            if (Color.getColor(color) == null)
                                return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     *
     * @return if the Screen blocks are valid materials
     */
    public boolean isValidScreenBlock(){
        for (String path : config.getKeys(true)){
            if (StringUtils.containsIgnoreCase(path, "screen_blocks")){
                for (String sMat : config.getStringList(path)){
                    if (Material.getMaterial(sMat) == null){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public String getTrickItemID(){
        for (String path : config.getKeys(false)){
            if (StringUtils.containsIgnoreCase(path, "trick_item")){
                return config.getString(path);
            }
        }
        return null;
    }

    public String getTreatItemID(){
        for (String path : config.getKeys(false)){
            if (StringUtils.containsIgnoreCase(path, "treat_item")){
                return config.getString(path);
            }
        }
        return null;
    }

    public String getHalloweenBasketName(){
        for (String path : config.getKeys(false)){
            if (StringUtils.containsIgnoreCase(path, "trick_or_treat_basket_item_name")){
                return config.getString(path);
            }
        }
        return null;
    }

    public boolean isHalloweenEnabled(){
        for (String path : config.getKeys(false)){
            if (StringUtils.containsIgnoreCase(path, "halloween_enabled")){
                return config.getBoolean(path);
            }
        }
        return false;
    }

}
