package com.peteydog7.bukkit.GUIs;

import com.peteydog7.bukkit.Handlers.CommandHandler;
import com.peteydog7.bukkit.TestPlugin;
import com.peteydog7.bukkit.Utilities.IconMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CommandMenuGUI {

    private static IconMenu menu = new IconMenu("Command Menu", 9, new IconMenu.OptionClickEventHandler(){

        @Override
        public void onOptionClick(IconMenu.OptionClickEvent event) {

            Player player = event.getPlayer();
            String option = event.getName().toLowerCase();

            if(option.equals("home")) CommandHandler.homeCommand(player);
            else if(option.equals("spawn")) CommandHandler.spawnCommand(player);
            else if(option.equals("exit")) close();

        }

    }, TestPlugin.getInstance());

    public static void open(Player player){

        generateMenu();

        menu.open(player);

    }

    public static void close(){

        menu.destroy();

    }

    private static void generateMenu(){

        menu.setOption(0, new ItemStack(Material.BED), "Home", "Teleports the player to their home.");
        menu.setOption(4, new ItemStack(Material.GRASS), "Spawn", "Teleports the player to spawn.");
        menu.setOption(8, new ItemStack(Material.REDSTONE), "Exit", "Closes this menu.");

    }

}