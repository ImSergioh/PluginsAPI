package me.imsergioh.pluginsapi.region;

import org.joml.Vector3d;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class CuboidRegion implements ICuboidRegion {

    private final int xMin;
    private final int xMax;
    private final int yMin;
    private final int yMax;
    private final int zMin;
    private final int zMax;

    public CuboidRegion(Vector3d point1, Vector3d point2) {
        this.xMin = (int) Math.min(point1.x(), point2.x());
        this.xMax = (int) Math.max(point1.x(), point2.x());
        this.yMin = (int) Math.min(point1.y(), point2.y());
        this.yMax = (int) Math.max(point1.y(), point2.y());
        this.zMin = (int) Math.min(point1.z(), point2.z());
        this.zMax = (int) Math.max(point1.z(), point2.z());
    }

    @Override
    public Vector3d getPos1() {
        return new Vector3d(xMin, yMin, zMin);
    }

    @Override
    public Vector3d getPos2() {
        return new Vector3d(xMax, yMax, zMax);
    }

    @Override
    public int getHeight() {
        return this.yMax - this.yMin +1;
    }

    @Override
    public int getXWidth() {
        return this.xMax - this.xMin + 1;
    }

    @Override
    public int getZWidth() {
        return this.zMax - this.zMin + 1;
    }

    @Override
    public Vector3d getCenter() {
        return new Vector3d((double) (this.xMax - this.xMin) / 2 + this.xMin, (double) (this.yMax - this.yMin) / 2 + this.yMin, (double) (this.zMax - this.zMin) / 2 + this.zMin);
    }

    @Override
    public boolean isInsideWithMarge(Vector3d loc, double marge) {
        double xMinCentered = xMin + 0.5;
        double xMaxCentered = xMax + 0.5;
        double yMinCentered = yMin + 0.5;
        double yMaxCentered = yMax + 0.5;
        double zMinCentered = zMin + 0.5;
        double zMaxCentered = zMax + 0.5;
        return loc.x() >= xMinCentered - marge && loc.x() <= xMaxCentered + marge && loc.y() >= yMinCentered - marge && loc
                .y() <= yMaxCentered + marge && loc.z() >= zMinCentered - marge && loc.z() <= zMaxCentered + marge;
    }

    @Override
    public boolean isInside(Vector3d loc) {
        return loc.x() >= this.xMin && loc.x() <= this.xMax && loc.y() >= this.yMin && loc.y() <= this.yMax && loc
                .z() >= this.zMin && loc.z() <= this.zMax;
    }

    @Override
    public int getTotalBlockSize() {
        return this.getHeight() * this.getXWidth() * this.getZWidth();
    }

    @Override
    public Iterator<Vector3d> getLocationsList() {
        Set<Vector3d> bL = new HashSet<>(this.getTotalBlockSize());
        for (int x = this.xMin; x <= this.xMax; ++x) {
            for (int y = this.yMin; y <= this.yMax; ++y) {
                for (int z = this.zMin; z <= this.zMax; ++z) {
                    Vector3d b = new Vector3d(x,y,z);
                    bL.add(b);
                }
            }
        }
        return bL.iterator();
    }
}
