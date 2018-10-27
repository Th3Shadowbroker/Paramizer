import io.m4taiori.paramizer.core.ParameterString;
import io.m4taiori.paramizer.core.util.Flag;
import org.junit.jupiter.api.Test;

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
                        "ValueFlags (" + param.getValueFlagNames().length + "): " + String.join(", ", param.getValueFlagNames())+ "\n\n"
                );

        System.out.println("Value-Flags:");

        for ( Flag flag : param.getValueFlags() )
        {
            System.out.println( "\t" + flag.getName() + ": " + flag.getValue() );
        }
    }

}
