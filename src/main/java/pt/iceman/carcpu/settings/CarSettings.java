package pt.iceman.carcpu.settings;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by iceman on 18/07/16.
 */
public class CarSettings {
    private int id;
    private double constantKilometers;
    private double tripKilometers;
    private double tripInitialFuelLevel;
    private double averageFuelConsuption;
    private String dashboardType;
    private double tyreOffSet;
    private double nextOilChange;
    private transient static boolean showInfo;
    private String serialPort;

    public CarSettings()
    {
        showInfo = true;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public double getTripKilometers()
    {
        return tripKilometers;
    }

    public void setTripKilometers(double tripKilometers)
    {
        this.tripKilometers = tripKilometers;

    }

    public double getConstantKilometers()
    {
        return constantKilometers;
    }

    public void setConstantKilometers(double constantKilometers)
    {
        this.constantKilometers = constantKilometers;
    }

    private synchronized void increaseConstantKilometers(double km)
    {
        this.constantKilometers += km;
    }

    public synchronized List<Double> increaseTripKilometers(double km)
    {
        this.tripKilometers += km;
        increaseConstantKilometers(km);
        return new ArrayList<Double>(2)
        {
            {
                add(tripKilometers);
                add(constantKilometers);
            }
        };
    }

    public double getFuelLevel()
    {
        return tripInitialFuelLevel;
    }

    public void setFuelLevel(double fuelLevel)
    {
        this.tripInitialFuelLevel = fuelLevel;
    }

    public double getAverageFuelConsuption()
    {
        return averageFuelConsuption;
    }

    public void setAverageFuelConsuption(double averageFuelConsuption)
    {
        this.averageFuelConsuption = averageFuelConsuption;
    }

    public String getSerialPort()
    {
        return serialPort;
    }

    public void setSerialPort(String serialPort)
    {
        this.serialPort = serialPort;
    }

    public String getDashboardType()
    {
        return dashboardType;
    }

    public void setDashboardType(String dashboardType)
    {
        this.dashboardType = dashboardType;
    }

    public double getTripInitialFuelLevel()
    {
        return tripInitialFuelLevel;
    }

    public void setTripInitialFuelLevel(double tripInitialFuelLevel)
    {
        this.tripInitialFuelLevel = tripInitialFuelLevel;
    }

    public double getTyreOffSet()
    {
        return tyreOffSet;
    }

    public void setTyreOffSet(double tyreOffSet)
    {
        this.tyreOffSet = tyreOffSet;
    }

    public double getNextOilChange()
    {
        return nextOilChange;
    }

    public void setNextOilChange(double nextOilChange)
    {
        this.nextOilChange = nextOilChange;
    }
}
