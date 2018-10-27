package io.m4taiori.paramizer.core.util;

public class TemporaryFlag
{

    private final String name;

    private String value;

    private final int location;

    public TemporaryFlag(String name, int location)
    {
        this.name = name.replaceFirst("-", "");
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

    public void setValue(String value )
    {
        this.value = value;
    }

    public boolean hasValue()
    {
        return value != null;
    }

    public io.m4taiori.paramizer.core.util.Flag toFlag()
    {
        return new io.m4taiori.paramizer.core.util.Flag(name, value, location);
    }

}
