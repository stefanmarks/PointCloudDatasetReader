package nz.ac.aut.SentienceLab.PointCloudDatasetReader;

/**
 * Enumeration with coordinate systems and conversion methods.
 * 
 * @author Stefan Marks
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
        double coordRight, coordUp, coordForward;
        switch (systemFrom)
        {
            case XR_YF_ZU : coordRight = in.x; coordUp = in.z; coordForward = in.y; break;
            default       : coordRight = in.x; coordUp = in.y; coordForward = in.z; break;
        }
        switch (systemTo)
        {
            case XR_YF_ZU : in.x = coordRight; in.z = coordUp; in.y = coordForward; break;
            default       : in.x = coordRight; in.y = coordUp; in.z = coordForward; break;
        }
        
        float normRight, normUp, normForward;
        switch (systemFrom)
        {
            case XR_YF_ZU : normRight = in.nx; normUp = in.nz; normForward = in.ny; break;
            default       : normRight = in.nx; normUp = in.ny; normForward = in.nz; break;
        }
        switch (systemTo)
        {
            case XR_YF_ZU : in.nx = normRight; in.nz = normUp; in.ny = normForward; break;
            default       : in.nx = normRight; in.ny = normUp; in.nz = normForward; break;
        }
    }
    
    private final String name;    
}
