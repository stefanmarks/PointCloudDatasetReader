package nz.ac.aut.SentienceLab.PointCloudDatasetReader;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class for contextual data of a point cloud.
 * 
 * @author  Stefan Marks
 */
public class PointCloudData 
{
    public double          xOffset, yOffset, zOffset;
    public BoundingBox     bbox;
    public List<PointData> points;
    
    
    public PointCloudData()
    {
        reset();
    }
    
    
    public void reset()
    {
        xOffset = yOffset = zOffset = 0;
        bbox   = new BoundingBox();
        points = new ArrayList<>();
    }
    
    
    public void addPoint(PointData point)
    {
        points.add(point);
        bbox.encapsulate(point);
    }
    
    
    public int pointCount()
    {
        return points.size();
    }
    
    
    public void randomise()
    {
        Collections.shuffle(points);
    }
    
    
    public void serialize(ByteBuffer buf)
    {
        buf.putLong(points.size());
        buf.putDouble(xOffset).putDouble(yOffset).putDouble(zOffset);
        buf.putDouble(bbox.xMin).putDouble(bbox.yMin).putDouble(bbox.zMin);
        buf.putDouble(bbox.xMax).putDouble(bbox.yMax).putDouble(bbox.zMax);
    }

}
