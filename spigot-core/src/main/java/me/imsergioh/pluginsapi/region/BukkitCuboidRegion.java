
package me.imsergioh.pluginsapi.region;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


public class BukkitCuboidRegion extends CuboidRegion implements IBukkitCuboidRegion {

    private World world;

    @Getter
    private final String worldName;

    public BukkitCuboidRegion(String worldName, Location point1, Location point2) {
        super(IBukkitCuboidRegion.toVector3d(point1), IBukkitCuboidRegion.toVector3d(point2));
        this.worldName = worldName;
    }

    public Location getBukkitCenter() {
        return IBukkitCuboidRegion.toBukkitLocation(getWorld(), getCenter());
    }

    @Override
    public Location getBukkitPos1() {
        return IBukkitCuboidRegion.toBukkitLocation(getWorld(), getPos1());
    }

    @Override
    public Location getBukkitPos2() {
        return IBukkitCuboidRegion.toBukkitLocation(getWorld(), getPos2());
    }

    @Override
    public World getWorld() {
        if (world == null) world = Bukkit.getWorld(worldName);
        return world;
    }

    @Override
    public boolean isInsideLocation(Location location) {
        if (!location.getWorld().getName().equals(getWorldName())) return false;
        return isInside(IBukkitCuboidRegion.toVector3d(location));
    }

    public Iterator<Location> getBukkitLocationsList() {
        Set<Location> locations = new HashSet<>();
        getLocationsList().forEachRemaining(v -> {
            locations.add(IBukkitCuboidRegion.toBukkitLocation(getWorld(), v));
        });
        return locations.iterator();
    }
}