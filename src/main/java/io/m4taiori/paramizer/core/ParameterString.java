package io.m4taiori.paramizer.core;

import io.m4taiori.paramizer.core.util.Flag;
import io.m4taiori.paramizer.core.util.InterpretationUtilities;
import io.m4taiori.paramizer.core.util.TemporaryFlag;

import java.util.*;

public class ParameterString
{

    private final String origin;

    private final String[] splitted;

    private final Map<String, Flag> flags = new HashMap<>();

    private final List<String> unassigned = new ArrayList<>();

    private ParameterString(String origin)
    {
        this.origin = origin;
        this.splitted = origin.split(" ");
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
                    continue;
                }
                else
                {
                    unassigned.add(v);
                    continue;
                }
            }

            //Assign values
            if (isFlag)
            {
                flags.put(currentFlag.getName(), currentFlag.toFlag());
                currentFlag = new TemporaryFlag(v, i);
            }
            else
            {
                if (currentFlag.hasValue())
                {
                    unassigned.add(v);
                }
                else
                {
                    currentFlag.setValue(v);
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

    public String[] getFlagNames()
    {
        return flags.keySet().stream().filter( flagName -> !flags.get(flagName).hasValue() ).toArray(String[]::new);
    }

    public Flag[] getFlags()
    {
        return flags.values().stream().filter(Flag::hasValue).toArray(Flag[]::new);
    }

    public Flag[] getValueFlags()
    {
        return flags.values().stream().filter(Flag::hasValue).toArray(Flag[]::new);
    }

    public String[] getValueFlagNames()
    {
        return flags.keySet().stream().filter( flagName -> flags.get(flagName).hasValue() ).toArray(String[]::new);
    }

    public String [] getUnassigned()
    {
        return unassigned.toArray( new String[unassigned.size()] );
    }

    public String getOrigin()
    {
        return origin;
    }

    public static ParameterString from(String origin)
    {
        return new ParameterString(origin);
    }

}
