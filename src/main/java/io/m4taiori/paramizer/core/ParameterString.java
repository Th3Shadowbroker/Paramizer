package io.m4taiori.paramizer.core;

import io.m4taiori.paramizer.core.util.Flag;
import io.m4taiori.paramizer.core.util.InterpretationUtilities;
import io.m4taiori.paramizer.core.util.SchemeEntry;
import io.m4taiori.paramizer.core.util.TemporaryFlag;

import java.util.*;

public class ParameterString
{

    private final String origin;

    private final SchemeEntry[] scheme;

    private final String[] splitted;

    private final Map<String, Flag> flags = new HashMap<>();

    private final List<String> unassigned = new ArrayList<>();

    private ParameterString(String origin)
    {
        this.origin = origin;
        this.splitted = origin.split(" ");
        this.scheme = new SchemeEntry[splitted.length];
        this.interpret();
    }

    private void interpret()
    {
        TemporaryFlag currentFlag = null;
        for( int i = 0; i < splitted.length; i++ )
        {
            String v = splitted[i];
            boolean isFlag = InterpretationUtilities.isFlag(v);

            //Collect unassigned flags and set first flag
            if ( currentFlag == null )
            {
                if ( isFlag )
                {
                    currentFlag = new TemporaryFlag(v, i);
                    scheme[i] = SchemeEntry.FLAG;
                    continue;
                }
                else
                {
                    unassigned.add(v);
                    scheme[i] = SchemeEntry.UNASSIGNED;
                    continue;
                }
            }

            //Assign values
            if (isFlag)
            {
                flags.put(currentFlag.getName(), currentFlag.toFlag());
                currentFlag = new TemporaryFlag(v, i);
                scheme[i] = SchemeEntry.FLAG;
            }
            else
            {
                if (currentFlag.hasValue())
                {
                    unassigned.add(v);
                    scheme[i] = SchemeEntry.UNASSIGNED;
                }
                else
                {
                    currentFlag.setValue(v);
                    scheme[i - 1] = SchemeEntry.VALUE_FLAG;
                    scheme[i] = SchemeEntry.VALUE;
                }
            }
        }

        //Finalize interpretation
        if ( !flags.containsKey(currentFlag.getName()) )
        {
            flags.put(currentFlag.getName(), currentFlag.toFlag());
        }
    }

    public Flag[] getAllFlags()
    {
        return flags.values().toArray( new Flag[flags.size()] );
    }


    public Flag[] getFlags()
    {
        return flags.values().stream().filter(Flag::hasValue).toArray(Flag[]::new);
    }

    public String[] getFlagNames()
    {
        return flags.keySet().stream().filter( flagName -> !flags.get(flagName).hasValue() ).toArray(String[]::new);
    }


    public Flag[] getValueFlags()
    {
        return flags.values().stream().filter(Flag::hasValue).toArray(Flag[]::new);
    }

    public String[] getValueFlagNames()
    {
        return flags.keySet().stream().filter( flagName -> flags.get(flagName).hasValue() ).toArray(String[]::new);
    }


    public SchemeEntry[] getScheme()
    {
        return scheme;
    }

    public String[] getUnassigned()
    {
        return unassigned.toArray( new String[unassigned.size()] );
    }

    public String getOrigin()
    {
        return origin;
    }


    public boolean firstIs(SchemeEntry schemeEntry)
    {
        return getScheme().length > 0 && getScheme()[0].equals(schemeEntry);
    }


    public static ParameterString from(String origin)
    {
        return new ParameterString(origin);
    }

}
