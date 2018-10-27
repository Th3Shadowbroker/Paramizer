package io.m4taiori.paramizer.core.util;

import java.util.ArrayList;
import java.util.List;

public class ParameterMap
{

    private final List<ParameterEntry> entries = new ArrayList<>();

    public int[] getLocations()
    {
        return entries.stream().mapToInt( entry -> entry.location ).toArray();
    }

    class  ParameterEntry
    {

        private final int location;

        private final String name;

        private final String value;

        public ParameterEntry(int location, String name, String value)
        {
            this.location = location;
            this.name = name;
            this.value = value;
        }

        public ParameterEntry(int location, String value)
        {
            this.location = location;
            this.name = null;
            this.value = value;
        }

        public int getLocation()
        {
            return location;
        }

        public String getName()
        {
            return name;
        }

        public String getValue()
        {
            return value;
        }

        public boolean isFlag()
        {
            return name != null;
        }

        public boolean isUnassigned()
        {
            return name == null;
        }
    }

}
