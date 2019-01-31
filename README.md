## Paramizer
![travis-ci-build-status](https://travis-ci.com/Th3Shadowbroker/Paramizer.svg?branch=master)

Paramizer is a java library for unix-like command-line interpretation.

## Example

#### The code
``` java
String testInput = "unassigned1 -a assigned-value1OfA unassigned2 -b assigned-value1OfB -c --d";

ParameterString param = ParameterString.from(testInput);

System.out.println
(
    "Unassigned (" + param.getUnassigned().length + "): " + String.join(", ", param.getUnassigned()) + "\n" +
    "Flags (" + param.getFlagNames().length + "): " + String.join(", ", param.getFlagNames()) + "\n" +
    "ValueFlags (" + param.getValueFlagNames().length + "): " + String.join(", ", param.getValueFlagNames())+ "\n" +
    "Scheme (" + param.getScheme().length + "): " + Arrays.toString( param.getScheme() ) + "\n\n"
);    
```

#### The result
```

Unassigned (2): unassigned1, unassigned2
Flags (2): c, -d
ValueFlags (2): a, b
Scheme (8): [UNASSIGNED, VALUE_FLAG, VALUE, UNASSIGNED, VALUE_FLAG, VALUE, FLAG, FLAG]


Value-Flags:
	a: assigned-value1OfA
	b: assigned-value1OfB
```

## Maven
```xml
...
<repository>
    <id>th3shadowbroker-releases</id>
    <url>https://nexus.m4taiori.io/repository/th3shadowbroker-releases/</url>
</repository>
...
<dependency>
    <groupId>io.m4taiori.paramizer</groupId>
    <artifactId>paramizer-core</artifactId>
    <version>20181128</version>
</dependency>
...
```

## Documentation
The docs of this project are available [here](https://docs.m4taiori.io/paramizer/).

## License
Paramizer is licensed under the MIT-License
