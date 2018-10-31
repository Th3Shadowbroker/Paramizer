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

import io.m4taiori.paramizer.core.ParameterString;
import io.m4taiori.paramizer.core.util.Flag;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class ParamizerTest
{

    @Test
    void test()
    {
        String testInput = "unassigned1 -a assigned-value1OfA unassigned2 -b assigned-value1OfB -c --d";
        System.out.println("Creating example from: " + testInput + "...\n");

        ParameterString param = ParameterString.from(testInput);

        System.out.println
                (
                        "Unassigned (" + param.getUnassigned().length + "): " + String.join(", ", param.getUnassigned()) + "\n" +
                        "Flags (" + param.getFlagNames().length + "): " + String.join(", ", param.getFlagNames()) + "\n" +
                        "ValueFlags (" + param.getValueFlagNames().length + "): " + String.join(", ", param.getValueFlagNames())+ "\n" +
                        "Scheme (" + param.getScheme().length + "): " + Arrays.toString( param.getScheme() ) + "\n\n"
                );

        System.out.println("Value-Flags:");

        for ( Flag flag : param.getValueFlags() )
        {
            System.out.println( "\t" + flag.getName() + ": " + flag.getValue() );
        }
    }

}
