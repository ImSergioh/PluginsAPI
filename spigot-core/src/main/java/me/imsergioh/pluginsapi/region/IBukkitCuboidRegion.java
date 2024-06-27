package me.imsergioh.pluginsapi.region;


import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.joml.Vector3d;

public interface IBukkitCuboidRegion extends ICuboidRegion {

    Location getBukkitPos1();
    Location getBukkitPos2();

    boolean isInsideLocation(Location location);

    String getWorldName();

    default boolean isInsidePlayer(Player player) {
        return isInsideLocation(player.getLocation());
    }

    World getWorld();

    static Vector3d toVector3d(Location location) {
        return new Vector3d(location.x(), location.y(), location.z());
    }

    static Location toBukkitLocation(World world, Vector3d vector3d) {
        return new Location(world, vector3d.x, vector3d.y, vector3d.z);
    }
}
