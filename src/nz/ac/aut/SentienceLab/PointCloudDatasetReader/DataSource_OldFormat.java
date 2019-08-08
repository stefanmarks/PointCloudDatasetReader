package nz.ac.aut.SentienceLab.PointCloudDatasetReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.text.ParseException;


/**
 * Class for loading/converting the old bin format.
 * 
 * @author  Stefan Marks
 */
public class DataSource_OldFormat implements DataSource 
{
    public DataSource_OldFormat()
    {
        
    }
    

    @Override
    public boolean openSource(File file)
    {
        boolean success = false;
        try
        {
            reader = new FileInputStream(file);
            buf = ByteBuffer.allocate(Integer.BYTES);
            buf.order(ByteOrder.LITTLE_ENDIAN);

            reader.getChannel().read(buf);
            buf.flip();
            pointCount = buf.getInt();
            
            System.out.println("Opening Old Binary file");
            
            buf = ByteBuffer.allocate(12*Float.BYTES);
            buf.order(ByteOrder.LITTLE_ENDIAN);
            
            success = pointCount > 0;
        }
        catch (IOException e)
        {
            // ignore
        }
        return success;
    }

    
    @Override
    public long getPointCount()
    {
        return pointCount; // don't know
    }
    
    
    @Override
    public PointData readSource() throws IOException, ParseException
    {
        PointData pd = null;
        int read = reader.getChannel().read(buf);
        if (read == 12*Float.BYTES)
        {
            pd = new PointData();
            buf.flip();
            pd.x = buf.getFloat(); pd.y = buf.getFloat(); pd.z = buf.getFloat();
            pd.size = buf.getFloat(); 
            pd.r = buf.getFloat(); pd.g = buf.getFloat(); pd.b = buf.getFloat(); pd.a = buf.getFloat();
            pd.intensity = buf.getFloat();
            pd.nx = buf.getFloat(); pd.ny = buf.getFloat(); pd.nz = buf.getFloat();
            buf.clear();
        }
        return pd;
    }

    
    @Override
    public void closeSource()
    {
        try
        {
            if (reader != null) reader.close();
        }
        catch (IOException e)
        {
            
        }
    }
    
    
    @Override
    public String toString()
    {
        return "Old Binary format";
    }

    
    private FileInputStream reader;
    private ByteBuffer      buf;
    private long            pointCount;
}
