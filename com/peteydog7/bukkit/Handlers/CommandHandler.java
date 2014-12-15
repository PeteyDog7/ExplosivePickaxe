package com.peteydog7.bukkit.Handlers;

import com.peteydog7.bukkit.GUIs.CommandMenuGUI;
import com.peteydog7.bukkit.Permissions;
import com.peteydog7.bukkit.TestPlugin;
import com.peteydog7.bukkit.Utilities.Countdown;
import com.peteydog7.bukkit.Utilities.LocationUtils;
import com.peteydog7.bukkit.Utilities.Reference;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class CommandHandler implements CommandExecutor {

    pickCooldown pickCooldown = new pickCooldown();

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){

           if(args.length>0&&args[0]!=null){

               if(args[0].equalsIgnoreCase("info"))infoCommand(sender);
               else if(args[0].equalsIgnoreCase("help"))helpCommand(sender);
               else if(args[0].equalsIgnoreCase("setHome"))setHomeCommand(sender);
               else if(args[0].equalsIgnoreCase("clearHome"))clearHomeCommand(sender);
               else if(args[0].equalsIgnoreCase("home"))homeCommand(sender);
               else if(args[0].equalsIgnoreCase("setSpawn"))setSpawnCommand(sender);
               else if(args[0].equalsIgnoreCase("spawn"))spawnCommand(sender);
               else if(args[0].equalsIgnoreCase("menu"))menuCommand(sender);
               else if(args[0].equalsIgnoreCase("pick"))explosivePickCommand(sender);
               else errorCommand(sender);
           }
            else infoCommand(sender);

        return false;
    }

    //Commands

    public static void infoCommand(CommandSender sender){

        if(sender instanceof Player){

            Player player = (Player) sender;

            player.sendMessage(Reference.CHAT_PREFIX + ChatColor.GREEN + "This is the Test Plugin \"info\" command.");

        }
        else sender.sendMessage(ChatColor.RED+"This command can only be used in-game!");

    }

    public static void helpCommand(CommandSender sender){

        if(sender instanceof Player){

            Player player = (Player) sender;

            player.sendMessage(Reference.CHAT_PREFIX+ChatColor.GREEN+"This is the Test Plugin \"help\" command.");

        }
        else sender.sendMessage(ChatColor.RED+"This command can only be used in-game!");

    }

    public static void errorCommand(CommandSender sender){

            Player player = (Player) sender;

            player.sendMessage(Reference.CHAT_PREFIX+ChatColor.RED+"That command could not be found!");

    }

    private static void setHomeCommand(CommandSender sender) {

        if(sender instanceof Player){

            Player player = (Player) sender;

            if(player.hasPermission(Permissions.SETHOME_COMMAND.getPermission())) {

                Location location = player.getLocation();
                String locationString = LocationUtils.getStringFromLocation(location);

                TestPlugin.getInstance().getConfig().set("players." + player.getName() + ".homeCommand", locationString);
                TestPlugin.getInstance().saveConfig();

                player.sendMessage(Reference.CHAT_PREFIX + ChatColor.GREEN + "Home location saved!");

            }

            else player.sendMessage(Reference.CHAT_PREFIX+ChatColor.RED+"You don't have permission to do that!");

        }

        else sender.sendMessage(ChatColor.RED+"This command can only be used in-game!");

    }

    public static void homeCommand(CommandSender sender) {

        if(sender instanceof Player){

            Location tempLoc;
            String home;

            final Player player = (Player) sender;

            if(player.hasPermission(Permissions.HOME_COMMAND.getPermission())) {

                if (TestPlugin.getInstance().getConfig().isSet("players." + player.getName() + ".homeCommand"))
                    home = TestPlugin.getInstance().getConfig().getString("players." + player.getName() + ".homeCommand");
                else home = null;

                final String locationString = home;

                if (locationString != null) tempLoc = LocationUtils.getLocationFromString(locationString);
                else tempLoc = null;

                final Location location = tempLoc;

                if (location != null) {

                    player.sendMessage(Reference.CHAT_PREFIX + ChatColor.AQUA + "Teleporting you to your home in " + ChatColor.DARK_AQUA + "2 seconds");

                    Bukkit.getScheduler().scheduleSyncDelayedTask(TestPlugin.getInstance(), new Runnable() {
                        @Override
                        public void run() {

                            if (player.teleport(location))
                                player.sendMessage(Reference.CHAT_PREFIX + ChatColor.GREEN + "Teleport Successful!");
                            else player.sendMessage(Reference.CHAT_PREFIX + ChatColor.RED + "Teleport Failed!");

                        }
                    }, 20 * 2);

                }

                else player.sendMessage(Reference.CHAT_PREFIX+ChatColor.RED+"You haven't set a home.");

            }

            else player.sendMessage(Reference.CHAT_PREFIX+ChatColor.RED+"You don't have permission to do that!");

        }

        else sender.sendMessage(ChatColor.RED+"This command can only be used in-game!");

    }

    public static void clearHomeCommand(CommandSender sender){

        if (sender instanceof Player) {

            Player player = (Player) sender;

            if(player.hasPermission(Permissions.CLEARHOME_COMMAND.getPermission())) {

                TestPlugin.getInstance().getConfig().set("players." + player.getName() + ".homeCommand", null);
                TestPlugin.getInstance().saveConfig();

                player.sendMessage(Reference.CHAT_PREFIX + ChatColor.GREEN + "Home location cleared!");

            }

            else player.sendMessage(Reference.CHAT_PREFIX+ChatColor.RED+"You don't have permission to do that!");

        }

        else sender.sendMessage(ChatColor.RED+"This command can only be used in-game!");

    }

    public static void setSpawnCommand(CommandSender sender){

        if (sender instanceof Player) {

            Player player = (Player) sender;

            if(player.hasPermission(Permissions.SETSPAWN_COMMAND.getPermission())) {

                String location = LocationUtils.getStringFromLocation(player.getLocation());

                String[] split = location.split("!");

                Double x = Double.parseDouble(split[1]);
                Double y = Double.parseDouble(split[2]);
                Double z = Double.parseDouble(split[3]);
                int xInt = x.intValue();
                int yInt = y.intValue();
                int zInt = z.intValue();

                player.getWorld().setSpawnLocation(xInt, yInt, zInt);

                player.sendMessage(Reference.CHAT_PREFIX + ChatColor.GREEN + "Spawn location for " + ChatColor.BOLD + player.getWorld().getName() + ChatColor.GREEN + " set!");

            }

            else player.sendMessage(Reference.CHAT_PREFIX+ChatColor.RED+"You don't have permission to do that!");

        }

        else sender.sendMessage(ChatColor.RED+"This command can only be used in-game!");

    }

    public static void spawnCommand(CommandSender sender) {

        if (sender instanceof Player) {

            final Player player = (Player) sender;

            if(player.hasPermission(Permissions.SPAWN_COMMAND.getPermission())) {

                player.sendMessage(Reference.CHAT_PREFIX + ChatColor.AQUA + "Teleporting you to spawn in " + ChatColor.DARK_AQUA + "2 seconds");

                Bukkit.getScheduler().scheduleSyncDelayedTask(TestPlugin.getInstance(), new Runnable() {
                    @Override
                    public void run() {

                        if (player.teleport(player.getWorld().getSpawnLocation()))
                            player.sendMessage(Reference.CHAT_PREFIX + ChatColor.GREEN + "Teleport Successful!");
                        else player.sendMessage(Reference.CHAT_PREFIX + ChatColor.RED + "Teleport Failed!");

                    }
                }, 20 * 2);

            }

            else player.sendMessage(Reference.CHAT_PREFIX+ChatColor.RED+"You don't have permission to do that!");

        }

        else sender.sendMessage(ChatColor.RED + "This command can only be used in-game!");

    }

    public static void menuCommand(CommandSender sender){

        if (sender instanceof Player) {

            Player player = (Player) sender;

            if(player.hasPermission(Permissions.MENU_COMMAND.getPermission())) {

                CommandMenuGUI.open(player);

            }

            else player.sendMessage(Reference.CHAT_PREFIX+ChatColor.RED+"You don't have permission to do that!");

        }

        else sender.sendMessage(ChatColor.RED+"This command can only be used in-game!");

    }

    public boolean explosivePickCommand(CommandSender sender){

        if (sender instanceof Player) {

            Player player = (Player) sender;

            if (player.hasPermission(Permissions.EXPLOSIVE_PICK_COMMAND.getPermission())) {

                if(Reference.cantDoCommandExplosivePick.contains(player)){

                    //TODO: Show Time Left

                    player.sendMessage("You can't do that right now.");

                    return false;

                }

                player.sendMessage("Command Successful");

                Reference.cantDoCommandExplosivePick.add(player);

                pickCooldown.setList(Reference.cantDoCommandExplosivePick);
                pickCooldown.setPlayer(player);
                pickCooldown.setTime(10000);
                new Thread(pickCooldown).start();

                ItemStack pickaxe = new ItemStack(Material.DIAMOND_PICKAXE);
                ItemMeta pickIM = pickaxe.getItemMeta();

                pickIM.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Explosive Pickaxe");
                List<String> lore = Arrays.asList(ChatColor.DARK_RED + "This " + ChatColor.UNDERLINE + "Explosive Pick" + ChatColor.RESET + "" + ChatColor.DARK_RED + " will mine", ChatColor.DARK_RED + "blocks in a 3 block radius from", ChatColor.DARK_RED + "the block you broke!", ChatColor.WHITE + "" + ChatColor.ITALIC + "(Does not take durability!)");
                pickIM.setLore(lore);
                pickaxe.setItemMeta(pickIM);
                pickaxe.addUnsafeEnchantment(Enchantment.DIG_SPEED, 10);

                Inventory inventory = player.getInventory();
                inventory.addItem(pickaxe);

                return true;

            }

            else player.sendMessage(Reference.CHAT_PREFIX+ChatColor.RED+"You don't have permission to do that!");

            return false;

        }

        else sender.sendMessage(ChatColor.RED+"This command can only be used in-game!");


        return false;

    }

    public class pickCooldown extends Countdown {

        @Override
        public void run() {
            try {
                Thread.sleep(timeCD);
                cantDoCommandExplosivePickCD.remove(playerCD);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}

