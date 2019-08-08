package nz.ac.aut.SentienceLab.PointCloudDatasetReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;


/**
 * Class for a XYZRGB data source.
 * 
 * @author  Stefan Marks
 */
public class DataSource_XYZRGB implements DataSource 
{
    public DataSource_XYZRGB()
    {
        separator = null;
    }
    

    @Override
    public boolean openSource(File file)
    {
        boolean success = false;
        try
        {
            reader = new BufferedReader(new FileReader(file));
            
            String header = reader.readLine();
            header  = header.replace("//", "").trim().replace(" ", "").toLowerCase();
            determineSeparator(header);
            headers = header.split(separator);
            
            pointCount = Long.parseLong(reader.readLine());
            System.out.println("Opening XYZRGB file with " + pointCount + " points "
                + "and headers '" + header + "'");
            
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
        return pointCount;
    }
    
    
    @Override
    public PointData readSource() throws IOException, ParseException
    {
        PointData pd  = null;
        String   line = reader.readLine();
        if ( line != null )
        {
            pd = new PointData();
            
            String[] parts = line.trim().split(separator);
                
            for ( int idx = 0 ; idx < parts.length ; idx++ )
            {
                float value = Float.parseFloat(parts[idx]);
                switch (headers[idx])
                {
                    case "x" : pd.x = value; break;
                    case "y" : pd.y = value; break;
                    case "z" : pd.z = value; break;

                    case "nx" : pd.nx = value; break;
                    case "ny" : pd.ny = value; break;
                    case "nz" : pd.nz = value; break;

                    case "r" : pd.r = value / 255; break;
                    case "g" : pd.g = value / 255; break;
                    case "b" : pd.b = value / 255; break;
                    case "a" : pd.a = value / 255; break;
                    
                    case "intensity" : pd.intensity = value; break;
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
            if (reader != null) reader.close();
        }
        catch (IOException e)
        {
            
        }
    }
    
    
    @Override
    public String toString()
    {
        return "XYZRGB";
    }

    
    private void determineSeparator(String line)
    {
        String[] separators   = { ",", "\t", " " };
        String[] parts;
        int      idx = 0;
        do
        {
            parts = line.split(separators[idx]);
            if ( parts.length >= 3) { separator = separators[idx]; }
            idx++;
            if ( idx == separators.length ) { separator = ","; } // fallback
        } while (separator == null);
    }
    
    
    private BufferedReader reader;
    private String[]       headers;
    private String         separator;
    private long           pointCount;
}
