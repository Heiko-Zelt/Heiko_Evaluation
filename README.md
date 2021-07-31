# Heiko_Evaluation
Vergleicht Ergebnis der Objekt-Erkennung mit Annotationen.

## Installations-Vorausetzungen
- Internet-Verbindung (nur während der Installation)
- Ubuntu 20.04.1 oder Windows 10
- git 2.25.1 oder Git for Windows v2.31.1
- Apache Maven 3.6.3
- OpenJDK 14 oder 15
- Eclipse 2021-03 (4.19.0)

Andere Versionen funktionieren vermutlich auch. Ich habe aber nur mit diesen getestet.

## Installation

In der Bash oder Git-Bash:

    cd Installations-Verzeichnis
    git clone https://github.com/Heiko-Zelt/Heiko_Evaluation

in Eclipse
1. Menüleiste > File > Open Projects from File System...
1. Verzeichnis "Evaluation_Project" in "Heiko_Evaluation" auswählen.
1. Warten bis Eclipse alle Dateien geparst hat. Im Project Explorer verschwinden rote (X)-Symbole.

## Starten

1. Im Project Explorer "src/main/java" > "de.heikozelt.objectdetection" > "Eval.java", Klick mit rechter Maustaste > "Run As" > "Java Application"
1. Auf der Console erscheint folgende Meldung: "SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder". Diese kann ignoriert werden.
1. Es können 2 Kommandozeilenparameter angeben werden:
   1. Pfad der Datei result.xml
   2. Pfad der Datei annotations.csv
