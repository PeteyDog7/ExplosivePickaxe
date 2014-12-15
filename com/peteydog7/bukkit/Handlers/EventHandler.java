package com.peteydog7.bukkit.Handlers;

import com.peteydog7.bukkit.Permissions;
import com.peteydog7.bukkit.TestPlugin;
import com.peteydog7.bukkit.Utilities.Countdown;
import com.peteydog7.bukkit.Utilities.ParticleEffects;
import com.peteydog7.bukkit.Utilities.Reference;
import me.confuser.barapi.BarAPI;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collection;

public class EventHandler implements Listener {

    @org.bukkit.event.EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){

        Player player = event.getPlayer();

        welcomeMessage(player);

        TestPlugin.getInstance().getConfig().set("players."+player.getName()+".online", true);
        TestPlugin.getInstance().saveConfig();

    }

    public static void welcomeMessage(Player player){

        player.sendMessage(Reference.CHAT_PREFIX+ChatColor.GOLD+"This server has "+ChatColor.DARK_AQUA+"Test Plugin"+ChatColor.GOLD+" installed!");
        player.sendMessage(Reference.CHAT_PREFIX+ChatColor.GOLD+"Type "+ChatColor.DARK_AQUA+"/testplugin"+ChatColor.GOLD+" for more information!");

    }

    @org.bukkit.event.EventHandler
    public void onPlayerDisconnect(PlayerQuitEvent event){

        Player player = event.getPlayer();
        TestPlugin.getInstance().getConfig().set("players."+player.getName()+".online", false);
        TestPlugin.getInstance().saveConfig();

    }

    @org.bukkit.event.EventHandler
    public void onGamemodeChange(PlayerGameModeChangeEvent event){

        Player player = event.getPlayer();
        GameMode gameMode = player.getGameMode();

    }

    @org.bukkit.event.EventHandler
    public void onClick(PlayerInteractEvent event){

        Player player = event.getPlayer();
        Action action = event.getAction();

        if (action.equals(Action.RIGHT_CLICK_AIR)||action.equals(Action.RIGHT_CLICK_BLOCK)){

            ItemStack itemInHand = player.getItemInHand();
            Material material = itemInHand.getType();
            ItemMeta itemMeta = itemInHand.getItemMeta();

            if ((material == Material.DIAMOND_PICKAXE)&&(itemInHand.hasItemMeta())&&(itemMeta.getDisplayName().contains(ChatColor.RED+""+ChatColor.BOLD+"Explosive Pickaxe"))){

                if(player.hasPermission(Permissions.USE_SUPER_EXPLOSION.getPermission())) {

                    if (!(Reference.cantSuperExplosion.contains(player))) {

                        Reference.cantSuperExplosion.add(player);
                        player.sendMessage("Super Explosion Activated");
                        if (BarAPI.hasBar(player)) {
                            BarAPI.removeBar(player);
                        }
                        BarAPI.setMessage(player, String.format("%s%sSuper Explosion Activated", ChatColor.RED, ChatColor.BOLD), 100f);

                        SuperExplosionCountdown superExplosionCountdown = new SuperExplosionCountdown();

                        superExplosionCountdown.setTime(1000);
                        superExplosionCountdown.setPlayer(player);
                        superExplosionCountdown.setList(Reference.cantSuperExplosion);

                        new Thread(superExplosionCountdown).start();

                        //TODO: Super Explosion Cool-Down

                    }

                    //TODO: Cooldown Message
                }
                else; //no perm
            }

        }

        if(action.equals(Action.RIGHT_CLICK_BLOCK)){

            Block block = event.getClickedBlock();
            Material material = player.getItemInHand().getType();

            if(material==Material.ARROW){

                if(player.hasPermission(Permissions.THROW_BLOCKS.getPermission())) {

                    @SuppressWarnings("deprecation")
                    FallingBlock fallingBlock = block.getWorld().spawnFallingBlock(block.getLocation(), block.getType(), block.getData());

                    org.bukkit.util.Vector velocity = fallingBlock.getVelocity();
                    velocity.setY(velocity.getY() + 1.5);
                    fallingBlock.setVelocity(velocity);
                    block.setType(Material.AIR);

                }
                else player.sendMessage(Reference.CHAT_PREFIX+ChatColor.RED+"You don't have permission to do that!");

            }

        }

    }

    @org.bukkit.event.EventHandler
    public void onBlockBreak(BlockBreakEvent event){

        Player player = event.getPlayer();
        ItemStack itemInHand = player.getItemInHand();
        Material material = itemInHand.getType();
        ItemMeta itemMeta = itemInHand.getItemMeta();

        ArrayList<Material> materials = new ArrayList<Material>();

        materials.add(Material.COBBLESTONE);
        materials.add(Material.STONE);
        materials.add(Material.SANDSTONE);
        materials.add(Material.MOSSY_COBBLESTONE);
        materials.add(Material.OBSIDIAN);
        materials.add(Material.IRON_ORE);
        materials.add(Material.IRON_BLOCK);
        materials.add(Material.COAL_ORE);
        materials.add(Material.COAL_BLOCK);
        materials.add(Material.GOLD_ORE);
        materials.add(Material.GOLD_BLOCK);
        materials.add(Material.DIAMOND_ORE);
        materials.add(Material.DIAMOND_BLOCK);
        materials.add(Material.LAPIS_ORE);
        materials.add(Material.LAPIS_BLOCK);
        materials.add(Material.REDSTONE_ORE);
        materials.add(Material.GLOWING_REDSTONE_ORE);
        materials.add(Material.REDSTONE_BLOCK);
        materials.add(Material.EMERALD_ORE);
        materials.add(Material.EMERALD_BLOCK);
        materials.add(Material.NETHERRACK);
        materials.add(Material.NETHER_BRICK);

        if ((material == Material.DIAMOND_PICKAXE)&&(itemInHand.hasItemMeta())&&(itemMeta.getDisplayName().contains(ChatColor.RED+""+ChatColor.BOLD+"Explosive Pickaxe"))){

            Block block = event.getBlock();

            boolean effective = false;
            for (Material material1 : materials){
                if (block.getType()==material1){
                    effective = true;
                }
            }

            if (effective==false){
                event.setCancelled(true);
                ItemStack diamondPick = new ItemStack(Material.DIAMOND_PICKAXE);
                block.breakNaturally(diamondPick);
                player.sendMessage("Not Effective");
                return;
            }

            if(player.hasPermission(Permissions.EXPLOSION_EFFECT.getPermission())) {

                Inventory inventory = player.getInventory();
                Collection<ItemStack> drops = block.getDrops();
                Location location = block.getLocation();

                event.setCancelled(true);

                Block blockUp = block.getRelative(BlockFace.UP);
                Block blockDown = block.getRelative(BlockFace.DOWN);

                ArrayList<Block> blocks = new ArrayList<Block>();

                if (!(Reference.cantSuperExplosion.contains(player))) {

                    blocks.add(block.getRelative(BlockFace.SELF));
                    blocks.add(block.getRelative(BlockFace.NORTH));
                    blocks.add(block.getRelative(BlockFace.SOUTH));
                    blocks.add(block.getRelative(BlockFace.EAST));
                    blocks.add(block.getRelative(BlockFace.WEST));
                    blocks.add(block.getRelative(BlockFace.NORTH_EAST));
                    blocks.add(block.getRelative(BlockFace.NORTH_WEST));
                    blocks.add(block.getRelative(BlockFace.SOUTH_EAST));
                    blocks.add(block.getRelative(BlockFace.SOUTH_WEST));

                    blocks.add(blockUp.getRelative(BlockFace.SELF));
                    blocks.add(blockUp.getRelative(BlockFace.NORTH));
                    blocks.add(blockUp.getRelative(BlockFace.SOUTH));
                    blocks.add(blockUp.getRelative(BlockFace.EAST));
                    blocks.add(blockUp.getRelative(BlockFace.WEST));
                    blocks.add(blockUp.getRelative(BlockFace.NORTH_EAST));
                    blocks.add(blockUp.getRelative(BlockFace.NORTH_WEST));
                    blocks.add(blockUp.getRelative(BlockFace.SOUTH_EAST));
                    blocks.add(blockUp.getRelative(BlockFace.SOUTH_WEST));

                    blocks.add(blockDown.getRelative(BlockFace.SELF));
                    blocks.add(blockDown.getRelative(BlockFace.NORTH));
                    blocks.add(blockDown.getRelative(BlockFace.SOUTH));
                    blocks.add(blockDown.getRelative(BlockFace.EAST));
                    blocks.add(blockDown.getRelative(BlockFace.WEST));
                    blocks.add(blockDown.getRelative(BlockFace.NORTH_EAST));
                    blocks.add(blockDown.getRelative(BlockFace.NORTH_WEST));
                    blocks.add(blockDown.getRelative(BlockFace.SOUTH_EAST));
                    blocks.add(blockDown.getRelative(BlockFace.SOUTH_WEST));

                }
                else {

                    //TODO: Super Explosion Effects
                    player.sendMessage("Super Explosion");

                }

                for (Block o : blocks){

                     Collection<ItemStack> itemStack = o.getDrops();


                    boolean breakable = false;

                    for (Material material1 : materials){

                        if (o.getType()==material1){
                            breakable = true;
                        }

                    }

                    if (breakable) {

                        for (ItemStack itemStack1 : itemStack){
                            drops.add(itemStack1);
                        }

                        //TODO: Respect WorldGuard Permissions
                        // (Needed for large explosions that reach outside of mine.)
                        o.setType(Material.AIR);
                    }
                }

                for (ItemStack o : drops) {
                    inventory.addItem(o);
                    player.updateInventory();
                }

                drops.clear();

                try {
                    ParticleEffects.HUGE_EXPLOSION.sendToPlayer(player, location, 1, 1, 1, 0, 3);
                    player.playSound(location, Sound.EXPLODE, 10, 1);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            else {
                event.setCancelled(false);
                player.sendMessage(Reference.CHAT_PREFIX+ChatColor.RED+"You don't have permission to use explosion effect!");
            }

        }
        else player.sendMessage("Pick not explosive.");

    }

    public class SuperExplosionCountdown extends Countdown {

        @Override
        public void run() {

            try {
                playerCD.playSound(playerCD.getLocation(), Sound.EXPLODE, 10, 1);
                for (int i = 10; i>-1; i--) {
                    Thread.sleep(timeCD);
                    Integer number = i;
                    float percent = number.floatValue()*10f;
                    BarAPI.setMessage(playerCD, String.format("%s%sSuper Explosion - Time Left: "+number.toString(),ChatColor.RED,ChatColor.BOLD), percent);
                    playerCD.playSound(playerCD.getLocation(), Sound.ORB_PICKUP, 10, 1);
                }
                //TODO: Fix Bar API Removal
                BarAPI.setMessage(playerCD, String.format("%s%sSuper Explosion - Time Left: 0",ChatColor.RED,ChatColor.BOLD), 0f);
                BarAPI.removeBar(playerCD);
                playerCD.playSound(playerCD.getLocation(), Sound.BLAZE_DEATH, 10, 1);
                Reference.cantSuperExplosion.remove(playerCD);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public class SuperExplosionCooldown extends Countdown {

        @Override
        public void run() {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}