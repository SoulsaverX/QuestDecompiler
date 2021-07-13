# QuestDecompiler

`QuestDecompiler` is a decompiler for .luc file to original Lua 5.X Source Code (GhostOnline / SoulSaverOnline Only)

This program is written in Java. A JAR package is available in the downloads section so you don't have to compile it. It runs from the command line and accepts a single argument: the file name of a Lua chunk. The decompiled code is printed to the standard output.

```
java -jar gsp.jar -a quest.luc
```

## About Release File
gsp.jar -> For SoulSaverOnline and Korean Client
th.jar  -> For GhostOnline based on Thai Language
cn.jar -> For Chinese / Taiwan Client



## How to compile `QuestDecompiler` from source

```
# when sources are in src/unluac/...
cd src
mkdir build
javac -verbose -deprecation -Werror -d build unluac/*.java
```

