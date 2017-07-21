package nz.ac.aut.SentienceLab.PointCloudDatasetReader;

import java.nio.ByteBuffer;

/**
 * Class for data of a single point cloud point.
 * 
 * @author  Stefan Marks
 */
public class PointData 
{
    public float x, y, z, size;
    public float r, g, b, a, intensity;
    public float nx, ny, nz;
    
    
    public PointData()
    {
        x = y = z = 0;
        size = 1;
        r = g = b = 0;
        a = intensity = 1;
        nx = ny = nz = 0;
    }
    
    
    public void serialise(ByteBuffer buf)
    {
        buf.putFloat(x).putFloat(y).putFloat(z).putFloat(size);
        buf.putFloat(r).putFloat(g).putFloat(b).putFloat(a).putFloat(intensity);
        buf.putFloat(nx).putFloat(ny).putFloat(nz);
    }
    
    
    public static final int SIZE = 4 * 12;
}
