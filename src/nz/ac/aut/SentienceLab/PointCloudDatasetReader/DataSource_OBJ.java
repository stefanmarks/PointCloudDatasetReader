package nz.ac.aut.SentienceLab.PointCloudDatasetReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;


/**
 * Class for an OBJ data source.
 * 
 * @author  Stefan Marks
 */
public class DataSource_OBJ implements DataSource 
{
    public DataSource_OBJ()
    {
        separatorIdx = 0;
    }
    

    @Override
    public boolean openSource(File file)
    {
        boolean success = false;
        try
        {
            reader = new BufferedReader(new FileReader(file));
            
            String lineIn;
            do
            {
                lineIn = reader.readLine();
            } while ( (lineIn != null) && !lineIn.trim().startsWith("v ")); 
            
            System.out.println("Opening OBJ file");
            
            success = (lineIn != null);
        }
        catch (IOException e)
        {
            // ignore
        }
        return success;
    }

    @Override
    public PointData readSource() throws IOException, ParseException
    {
        PointData pd  = null;
        String   line = reader.readLine();
        if ( line != null )
        {
            line.trim();
            
            String[] parts = line.split(separators[separatorIdx]);
            while ( parts.length < 3 )
            {
                separatorIdx = (separatorIdx + 1) % separators.length;
                parts = line.split(separators[separatorIdx]);
            }
                
            if (parts[0].matches("v"))
            {
                pd = new PointData();
            
                if (parts.length >= 4)
                {
                    pd.x = Float.parseFloat(parts[1]);
                    pd.y = Float.parseFloat(parts[2]);
                    pd.z = Float.parseFloat(parts[3]);
                }
                if (parts.length >= 7)
                {
                    pd.r = Float.parseFloat(parts[4]);
                    pd.g = Float.parseFloat(parts[5]);
                    pd.b = Float.parseFloat(parts[6]);
                }
            }
        }
        return pd;
    }

    
    @Override
    public void closeSource()
    {
        try
        {
            reader.close();
        }
        catch (IOException e)
        {
            
        }
    }
    
    
    @Override
    public String toString()
    {
        return "OBJ";
    }

    
    private       BufferedReader reader;
    private       String[]       headers;
    private final String[]       separators = { ",", "\t", " " };   
    private       int            separatorIdx;
}
