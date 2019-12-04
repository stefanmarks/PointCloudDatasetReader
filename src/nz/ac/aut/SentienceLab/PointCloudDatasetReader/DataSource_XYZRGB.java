package nz.ac.aut.SentienceLab.PointCloudDatasetReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import javax.swing.JOptionPane;


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
            
            // check for the header            
            reader.mark(128);
            String header = reader.readLine();
            if (!header.trim().startsWith("//"))
            {
                header = JOptionPane.showInputDialog(null, 
                    "There is no header information.\n" +
                    "This is the first line of data:\n" + header + "\n" +
                    "Please enter the header line", 
                    "Missing Header", 
                    JOptionPane.QUESTION_MESSAGE);
                reader.reset();
            }
            if (header != null)
            {
                header = header.replace("//", "").trim().toLowerCase();
                determineSeparator(header);
                if (separator != null)
                {
                    headers = header.split(separator);

                    // check for point count line
                    reader.mark(128);
                    String strPointCount = reader.readLine();
                    if (strPointCount.matches("^[0-9]+$"))
                    {
                        pointCount = Long.parseLong(strPointCount);
                        System.out.println("Opening XYZRGB file with " + pointCount + " points "
                            + "and headers '" + header + "'");
                    }
                    else
                    {
                        reader.reset();
                        pointCount = 0;
                        System.out.println("Opening XYZRGB file with headers '" + header + "'");
                    }
                    success = true;
                }
                else
                {
                    System.err.println("Could not determine separator for XYZRGB file");
                }
            }
            else
            {
                System.err.println("No suitable header found");
            }
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
                double value = Double.parseDouble(parts[idx]);
                switch (headers[idx])
                {
                    case "x" : pd.x = value; break;
                    case "y" : pd.y = value; break;
                    case "z" : pd.z = value; break;

                    case "nx" : pd.nx = (float) value; break;
                    case "ny" : pd.ny = (float) value; break;
                    case "nz" : pd.nz = (float) value; break;

                    case "r" : pd.r = (float) value / 255; break;
                    case "g" : pd.g = (float) value / 255; break;
                    case "b" : pd.b = (float) value / 255; break;
                    case "a" : pd.a = (float) value / 255; break;
                    
                    case "intensity" : pd.intensity = (float) value; break;
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
        final String[] separators = { ",", "\t", " " };
        for (String sep : separators)
        {
            String[] parts = line.split(sep);
            if ( parts.length >= 3) 
            { 
                separator = sep; 
            }            
        } 
    }
    
    
    private BufferedReader reader;
    private String[]       headers;
    private String         separator;
    private long           pointCount;
}
