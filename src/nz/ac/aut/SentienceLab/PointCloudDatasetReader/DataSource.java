package nz.ac.aut.SentienceLab.PointCloudDatasetReader;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

/**
 * Interface for managing data sources for point cloud data.
 * 
 * @author  Stefan Marks
 */
public interface DataSource 
{
    public boolean openSource(File file);
    
    public long getPointCount();
    
    public PointData readSource() throws IOException, ParseException;
    
    public void closeSource();
}
