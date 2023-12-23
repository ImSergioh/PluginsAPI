package me.imsergioh.pluginsapi.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class LocationSerializer {

    private static final char SEPARATOR = ' ';

    public static String toString(Location location) {
        return location.getWorld().getName() +
                SEPARATOR +
                location.getX() +
                SEPARATOR +
                location.getY() +
                SEPARATOR +
                location.getZ() +
                SEPARATOR +
                location.getYaw() +
                SEPARATOR +
                location.getPitch();
    }

    public static Location toLocation(String strLocation) {
        String[] args = strLocation.split(String.valueOf(SEPARATOR));
        World world = Bukkit.getWorld(args[0]);
        if (world == null) Bukkit.getWorlds().get(0);
        return new Location(world, Double.parseDouble(args[1]), Double.parseDouble(args[2]), Double.parseDouble(args[3]),
                Float.parseFloat(args[4]), Float.parseFloat(args[5]));
    }
}
