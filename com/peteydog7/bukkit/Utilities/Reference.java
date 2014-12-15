package com.peteydog7.bukkit.Utilities;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public final class Reference {

    public static final String CHAT_PREFIX = ChatColor.DARK_RED+"["+ChatColor.DARK_AQUA+"TestPlugin"+ChatColor.DARK_RED+"] "+ChatColor.RESET;

    public static List<Player> cantDoCommandExplosivePick = new ArrayList<Player>();
    public static List<Player> cantSuperExplosion = new ArrayList<Player>();
    public static List<Player> cantActivateSuperExplosion = new ArrayList<Player>();

}
