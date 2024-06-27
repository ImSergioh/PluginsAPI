package me.imsergioh.pluginsapi.region;
import org.joml.Vector3d;

import java.util.Iterator;

public interface ICuboidRegion {

    Vector3d getPos1();
    Vector3d getPos2();

    int getHeight();

    int getXWidth();
    int getZWidth();

    int getTotalBlockSize();

    Vector3d getCenter();

    boolean isInsideWithMarge(Vector3d vector3d, double marge);
    boolean isInside(Vector3d vector);

    Iterator<Vector3d> getLocationsList();

}
