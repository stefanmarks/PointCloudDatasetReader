package nz.ac.aut.SentienceLab.PointCloudDatasetReader;

/**
 * Class for managing an axis-aligned bounding box.
 * 
 * @author  Stefan Marks
 */
public class BoundingBox 
{
    public double xMin, yMin, zMin, xMax, yMax, zMax;
    
    
    public BoundingBox()
    {
        // initialise with opposite values to force first update
        xMin = yMin = zMin = Double.MAX_VALUE;
        xMax = yMax = zMax = Double.MIN_VALUE;
    }
    
    
    public void encapsulate(PointData p)
    {
        if (p.x < xMin) xMin=p.x;
        if (p.y < yMin) yMin=p.y;
        if (p.z < zMin) zMin=p.z;

        if (p.x > xMax) xMax=p.x;
        if (p.y > yMax) yMax=p.y;
        if (p.z > zMax) zMax=p.z;
    }   
    
    
    @Override
    public String toString()
    {
        return String.format("[%f / %f / %f ... %f / %f / %f]",
            xMin, yMin, zMin, xMax, yMax, zMax);
    }
}
