/*
 * Copyright (c) 2018 Th3Shadowbroker
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.m4taiori.paramizer.core;

import io.m4taiori.paramizer.core.util.Flag;
import io.m4taiori.paramizer.core.util.InterpretationUtilities;
import io.m4taiori.paramizer.core.util.SchemeEntry;

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
        Flag currentFlag = null;
        for( int i = 0; i < splitted.length; i++ )
        {
            String v = splitted[i];
            boolean isFlag = InterpretationUtilities.isFlag(v);

            //Collect unassigned flags and set first flag
            if ( currentFlag == null )
            {
                if ( isFlag )
                {
                    currentFlag = new Flag(v, i);
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
                flags.put(currentFlag.getName(), currentFlag);
                currentFlag = new Flag(v, i);
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

        //Null prevention
        if (currentFlag == null) return;

        //Finalize interpretation
        if ( !flags.containsKey(currentFlag.getName()) )
        {
            flags.put(currentFlag.getName(), currentFlag);
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


    public boolean startsWith(SchemeEntry schemeEntry)
    {
        return getScheme().length > 0 && getScheme()[0].equals(schemeEntry);
    }

    public boolean hasFlag( String name )
    {
        return flags.containsKey(name) && !flags.get(name).hasValue();
    }

    public boolean hasValueFlag( String name )
    {
        return flags.containsKey(name) && flags.get(name).hasValue();
    }

    public Optional<String> getValueOf( String flagName )
    {
        return Optional.ofNullable( hasValueFlag(flagName) ? flags.get(flagName).getValue() : null );
    }

    public static ParameterString from(String origin)
    {
        return new ParameterString(origin);
    }

}
