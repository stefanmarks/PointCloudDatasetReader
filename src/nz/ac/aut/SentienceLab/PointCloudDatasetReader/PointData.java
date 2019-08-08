package nz.ac.aut.SentienceLab.PointCloudDatasetReader;

import java.nio.ByteBuffer;

/**
 * Class for data of a single point cloud point.
 * 
 * @author  Stefan Marks
 */
public class PointData 
{
    public double x, y, z;
    public float  size;
    public float  r, g, b, a, intensity;
    public float  nx, ny, nz;
    
    
    public PointData()
    {
        x = y = z = 0;
        size = 1;
        r = g = b = 0;
        a = intensity = 1;
        nx = ny = nz = 0;
    }
    
    
    public void serialize(ByteBuffer buf, PointCloudData pc)
    {
        buf.putFloat((float) (x - pc.xOffset));
        buf.putFloat((float) (y - pc.yOffset));
        buf.putFloat((float) (z - pc.zOffset));
        buf.putFloat(size);
        buf.putFloat(r).putFloat(g).putFloat(b).putFloat(a);
        buf.putFloat(intensity);
        buf.putFloat(nx).putFloat(ny).putFloat(nz);
    }
	
	
    public static final int SERIALIZED_SIZE = Float.BYTES * 12;
}
