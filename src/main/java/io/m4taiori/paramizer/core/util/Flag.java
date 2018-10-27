package io.m4taiori.paramizer.core.util;

public class Flag
{

    private final String name;

    private final String value;

    private final int location;

    public Flag(String name, String value, int location)
    {
        this.name = name;
        this.value = value;
        this.location = location;
    }

    public Flag(String name, int location)
    {
        this.name = name;
        this.value = null;
        this.location = location;
    }

    public String getName()
    {
        return name;
    }

    public String getValue()
    {
        return value;
    }

    public int getLocation()
    {
        return location;
    }

    public boolean hasValue()
    {
        return value != null;
    }
}
