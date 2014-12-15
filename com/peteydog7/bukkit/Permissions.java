package com.peteydog7.bukkit;

public enum Permissions {

    //TODO: Test Permissions (May be Broken)

    THROW_BLOCKS("testplugin.throwblocks"),
    HOME_COMMAND("testplugin.home"),
    SETHOME_COMMAND("testplugin.sethome"),
    CLEARHOME_COMMAND("testplugin.clearhome"),
    SPAWN_COMMAND("testplugin.spawn"),
    SETSPAWN_COMMAND("testplugin.setspawn"),
    MENU_COMMAND("testplugin.menucommand"),
    EXPLOSION_EFFECT("testplugin.pickaxeexplosion"),
    EXPLOSIVE_PICK_COMMAND("testplugin.explosivepickaxecommand"),
    USE_SUPER_EXPLOSION("testplugin.superexplosion");

    private String permission;

    Permissions(String permission){

        this.permission = permission;

    }

    public String getPermission() {

        return permission;

    }

    public void setPermission(String permission) {

        this.permission = permission;

    }

}
