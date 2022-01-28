package main;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import commands.commands;
import core.logger;
import core.network;
import eventlistener.eventlistener;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class whes1015 extends JavaPlugin implements Listener {

    Integer configVer=2;
    List<String> BkkitVersion= Arrays.asList("1.18.1-R0.1-SNAPSHOT","1.18-R0.1-SNAPSHOT","1.17.1-R0.1-SNAPSHOT","1.17-R0.1-SNAPSHOT");

    public static JsonObject DATA = new JsonObject();
    public static String LogLevel;
    public static File folder;
    public static Integer VersionCode=220615;
    public static Integer state=0;
    //pre 01~10
    //rc 11~14
    //release 15

    @Override
    public void onEnable() {
        saveDefaultConfig();
        logger.log("DEBUG", "Core_onEnable", "Bukkit Version: " + getServer().getBukkitVersion());
        LogLevel=getConfig().getString("LogLevel");
        DATA.addProperty("APIkey", getConfig().getString("APIKey"));
        DATA.addProperty("Function", "server");
        DATA.addProperty("UUID", getConfig().getString("ServerUUID"));
        logger.log("INFO", "Core_main", "ServerUUID >> " + getConfig().getString("ServerUUID"));
        folder=getDataFolder();
        logger.log("INFO", "Core_onEnable", "Loading! Version: " + getDescription().getVersion());
        Objects.requireNonNull(getCommand("et")).setExecutor(new commands(this));
        Objects.requireNonNull(getCommand("dc")).setExecutor(new commands(this));
        getServer().getPluginManager().registerEvents(new eventlistener(this), this);
        Update();
    }

    @Override
    public void onDisable(){
        state=1;
        JsonElement Data= JsonParser.parseString(DATA.toString());
        JsonObject data=Data.getAsJsonObject();
        data.addProperty("Type","onDisable");
        data.addProperty("FormatVersion", 1);
        network.Post(data);
        logger.log("INFO","Core_onDisable","Closing! Version: "+getDescription().getVersion());
    }

    public void Update(){
        String webPage = "https://api.github.com/repos/ExpTechTW/ExpTech_Core/releases";
        InputStream is = null;
        try {
            is = new URL(webPage).openStream();
        } catch (IOException e) {
            logger.log("ERROR","Core_Update",e.getMessage());
            main();
            return;
        }
        assert is != null;
        Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8);
        JsonElement jsonElement = JsonParser.parseReader(reader);
        JsonArray jsonArray = jsonElement.getAsJsonArray();
        if(jsonArray.size()!=0) {
            JsonObject jsonObject = (JsonObject) jsonArray.get(0);
            saveDefaultConfig();
            if (getConfig().getInt("ConfigVersion") < configVer) {
                logger.log("WARN", "Core_Update", "Please delete the old Config.yml file, the plugin will automatically generate a new Config.yml file!");
                Bukkit.getServer().shutdown();
            } else {
                if (!Objects.equals(jsonObject.get("tag_name").getAsString(), getDescription().getVersion())) {
                    if (getConfig().getBoolean("BetaVersion") && jsonObject.get("prerelease").getAsBoolean() || (!getConfig().getBoolean("BetaVersion")) && !jsonObject.get("prerelease").getAsBoolean()) {
                        logger.log("WARN", "Core_Update", "Please Update Your Plugin!");
                        logger.log("WARN", "Core_Update", "DownloadLink: https://github.com/ExpTechTW/ExpTech_Core/releases");
                    } else {
                        logger.log("INFO", "Core_Update", "Loading Success! - Designed by ExpTech.tw!");
                    }
                } else {
                    logger.log("INFO", "Core_Update", "Loading Success! - Designed by ExpTech.tw!");
                }
            }
        }
        main();
    }

    public void main() {
        Server server = getServer();
        JsonElement Data = JsonParser.parseString(DATA.toString());
        JsonObject data = Data.getAsJsonObject();
        data.addProperty("Type", "onEnable");
        data.addProperty("Ip", server.getIp());
        data.addProperty("Port", server.getPort());
        data.addProperty("ServerName", getConfig().getString("ServerName"));
        data.addProperty("FormatVersion", 1);
        data.addProperty("Ver", getDescription().getVersion());
        data.addProperty("Config", configVer);
        data.addProperty("BukkitVersion", server.getBukkitVersion());
        JsonObject jsonObject= network.Post(data);
        if (jsonObject == null){
            logger.log("ERROR", "Core_main", "API return null");
            Bukkit.getPluginManager().disablePlugins();
        }else {
            if (Objects.equals(jsonObject.get("response").getAsString(), "Key verification failed")) {
                logger.log("WARN", "main", "APIkey verification failed");
                Bukkit.getPluginManager().disablePlugins();
            } else if (Objects.equals(jsonObject.get("response").getAsString(), "UUID can not be null")) {
                logger.log("WARN", "Core_main", "UUID can not be null");
                Bukkit.getPluginManager().disablePlugins();
            } else {
                if (!BkkitVersion.contains(getServer().getBukkitVersion())) {
                    logger.log("WARN", "Core_main", "Server Version is not Supported,Please upgrade!");
                    Bukkit.getPluginManager().disablePlugins();
                }
            }
        }
    }
}
