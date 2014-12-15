package com.peteydog7.bukkit.Utilities;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

public class LocationUtils{

    private static double round(double d) {
        return Math.ceil(d);
    }

    public static String getLocationStringFromBlock (Block block){
        StringBuilder builder = new StringBuilder();
        Location location = block.getLocation();

        builder.append(location.getWorld().getName()).append('!');
        builder.append(location.getBlockX()).append('!');
        builder.append(location.getBlockY()).append('!');
        builder.append(location.getBlockZ());

        return builder.toString();
    }

    public static String getStringFromLocation(Location location) {
        StringBuilder builder = new StringBuilder();

        builder.append(location.getWorld().getName()).append('!');
        builder.append(round(location.getX())).append('!');
        builder.append(round(location.getY())).append('!');
        builder.append(round(location.getZ())).append('!');
        builder.append(round(location.getYaw())).append('!');
        builder.append(round(location.getPitch()));

        return builder.toString();
    }

    public static Location getLocationFromString(String string) {
        String[] split = string.split("!");

        World world = Bukkit.getWorld(split[0]);
        double x = Double.parseDouble(split[1]);
        double y = Double.parseDouble(split[2]);
        double z = Double.parseDouble(split[3]);
        float yaw = 0.0f;
        float pitch = 0.0f;
        if (split.length == 6) {
            yaw = Float.parseFloat(split[4]);
            pitch = Float.parseFloat(split[5]);
        }
        return new Location(world, x, y, z, yaw, pitch);
    }
}