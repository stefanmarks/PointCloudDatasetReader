package nz.ac.aut.SentienceLab.PointCloudDatasetReader;

/**
 * Enumeration with coordinate systems
 * 
 * @author smarks
 */

public enum CoordinateSystem 
{
    XR_YF_ZU("X Right, Y Forward, Z Up"), // X right, Y
    XR_YU_ZF("X Right, Y Up, Z Forward");
    
    
    private CoordinateSystem(String name)
    {
        this.name = name;
    }
    
    
    @Override
    public String toString()
    {
        return name;
    }
    
    
    public static CoordinateSystem fromString(String name)
    {
        for (CoordinateSystem cs : CoordinateSystem.values())
        {
            if ( cs.toString().compareToIgnoreCase(name) == 0) return cs;
        }
        return CoordinateSystem.XR_YU_ZF; // fallback
    }
    
    
    public static void convert(PointData in, CoordinateSystem systemFrom, CoordinateSystem systemTo)
    {
        float right, up, forward;
        switch (systemFrom)
        {
            case XR_YF_ZU : right = in.x; up = in.z; forward = in.y; break;
            default       : right = in.x; up = in.y; forward = in.z; break;
        }
        switch (systemTo)
        {
            case XR_YF_ZU : in.x = right; in.z = up; in.y = forward; break;
            default       : in.x = right; in.y = up; in.z = forward; break;
        }
        
        switch (systemFrom)
        {
            case XR_YF_ZU : right = in.nx; up = in.nz; forward = in.ny; break;
            default       : right = in.nx; up = in.ny; forward = in.nz; break;
        }
        switch (systemTo)
        {
            case XR_YF_ZU : in.nx = right; in.nz = up; in.ny = forward; break;
            default       : in.nx = right; in.ny = up; in.nz = forward; break;
        }
    }
            
    private String name;    
}
