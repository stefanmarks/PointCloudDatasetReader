package nz.ac.aut.SentienceLab.PointCloudDatasetReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;


/**
 * Class for a PTS data source.
 * 
 * @author  Stefan Marks
 */
public class DataSource_PTS implements DataSource 
{
    public DataSource_PTS()
    {
        numPoints = 0;
    }
    

    @Override
    public boolean openSource(File file)
    {
        boolean success = false;
        try
        {
            reader = new BufferedReader(new FileReader(file));
            
            String lineIn = reader.readLine();
            numPoints = Long.parseLong(lineIn.trim());
            
            System.out.println("Opening PTS file");
            
            success = true;
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
        return numPoints;
    }
    
    
    @Override
    public PointData readSource() throws IOException, ParseException
    {
        PointData pd  = null;
        String   line = reader.readLine();
        if ( line != null )
        {
            String[] parts = line.trim().split(" ");
            if (parts.length >= 9)
            {
                pd = new PointData();
            
                pd.x  = Float.parseFloat(parts[0]);
                pd.y  = Float.parseFloat(parts[1]);
                pd.z  = Float.parseFloat(parts[2]);
                pd.r  = Float.parseFloat(parts[3]);
                pd.g  = Float.parseFloat(parts[4]);
                pd.b  = Float.parseFloat(parts[5]);
                pd.nx = Float.parseFloat(parts[6]);
                pd.nx = Float.parseFloat(parts[7]);
                pd.nx = Float.parseFloat(parts[8]);
            }
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
        return "PTS";
    }

    
    private BufferedReader reader;
    private long           numPoints;
}
