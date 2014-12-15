package com.peteydog7.bukkit;

import com.peteydog7.bukkit.Handlers.CommandHandler;
import com.peteydog7.bukkit.Handlers.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class TestPlugin extends JavaPlugin {

    public static TestPlugin instance;


    public void onEnable(){

        getLogger().info("onEnable function initiated!");

        instance = this;

        createConfig();

        CommandHandler cmd = new CommandHandler();
        getCommand("TestPlugin").setExecutor(cmd);

        getServer().getPluginManager().registerEvents(new EventHandler(), getInstance());

        getLogger().info("onEnable function completed!");

    }

    public void onDisable(){

        getLogger().info("onDisable function initiated!");

        instance = null;

        getLogger().info("onDisable function completed!");

    }

    private void createConfig(){

        //TODO: Make "Player Data" file
        //TODO: Cooldown Times Configurable

        try {
            if(!getDataFolder().exists()){
                getDataFolder().mkdirs();
            }
            File file = new File(getDataFolder(), "config.yml");
            if(!file.exists()){
                getLogger().info("config.yml not found, creating...");
                saveConfig();
            }
            else{
                getLogger().info("config.yml found, loading...");
            }
        }
        catch (Exception e){
            e.printStackTrace();
            getLogger().warning("Error: "+e);
        }

    }

    public static TestPlugin getInstance(){

        return instance;

    }

}