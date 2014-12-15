package com.peteydog7.bukkit.Utilities;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Countdown implements Runnable {

    public int timeCD = 0;
    public Player playerCD = null;
    public List<Player> cantDoCommandExplosivePickCD = new ArrayList<Player>();

    public void setPlayer(Player player){
        playerCD = player;
    }

    public void setList(List<Player> list){
        cantDoCommandExplosivePickCD = list;
    }

    public  List<Player> getList(){
        return cantDoCommandExplosivePickCD;
    }

    public void setTime(int time){
        timeCD = time;
    }

    //default
    public void run() {
        try {
            Thread.sleep(1);
        } catch (InterruptedException ignored) {
            //ignored
        }
    }
}