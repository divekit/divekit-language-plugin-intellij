# Divekit Language Plugin IntelliJ

## Beschreibung

Dieses Repository enthält den Sourcecode für das Divekit-IntelliJ Plugin. Zusammen mit den Repositorys [divekit-language-plugin-vscode](https://github.com/divekit/divekit-language-plugin-vscode)
und [divekit-language-server](https://github.com/divekit/divekit-language-server) bildet es die Codebasis für das Praxisprojekt
"**Entwicklung eines IDE Plugins zur Unterstützung bei der Erstellung individualisierter Praktika-und Klausuraufgaben**".

Eine ausführliche Dokumentation der verschiedenen Projektbestandteile befindet sich im [Wiki](https://github.com/divekit/divekit-language-server/wiki).

## Nutzung 

Das Plugin ist für die Nutzung im Projekt [automated-repo-setup](https://github.com/divekit/divekit-automated-repo-setup) entwickelt worden. 

Nach der Installation sind noch folgende Schritte erforderlich: 

* Divekit Language Server JAR muss auf dem lokalen Dateisystem vorhanden sein. Dies kann durch clonen des [Language Server Repositorys](https://github.com/divekit/divekit-language-server) und anschließendes bauen der JAR mit `mvn package` erreicht werden.

* Der absolute Pfad zur JAR muss in den Einstellungen unter Tools -> Divekit: Settings eingetragen werden.

* Optional kann noch der absolute Pfad zu den Dateien `variationsConfig.json` und `variableExtensionsConfig.json` in den Einstellungen
eingetragen werden. Sind diese Felder leer, wird automatisch vom Project-root aus nach den Files gesucht.
  Achtung: Das Plugin startet den Language Server nur, wenn die beiden Config-Dateien gefunden werden.
  
* Anschließend kann das Projekt `automated-repo-setup` gestartet werden. Wenn es vorher schon geöffnet war, muss es einmal geschlossen 
und wieder geöffnet werden. Wenn unten rechts die Meldung erscheint, dass der Language Server in diesem Projekt genutzt werden kann, hat
  alles funktioniert.
  
## Weiterentwicklung & Anpassungen

* Probleme & Fehler können auf GitHub zuerst unter der [Issues](https://github.com/divekit/divekit-language-plugin-intellij/issues) eingetragen werden
und anschließend von da abgearbeitet werden. 
  
* Vor dem Mergen empfiehlt sich in der Regel ein Pull-Request, da so die Code-Qualität hoch gehalten werden kann.

* Über den Gradle-Task **runIde** unter dem Menüpunkt **intellij** in der Gradle Sidebar lässt sich eine neue
IntelliJ Instanz mit installiertem Plugin zum schnellen Testen der Funktionalität öffnen.


## Testen

* Der [DivekitCodeCompletionTest](https://github.com/divekit/divekit-language-plugin-intellij/blob/master/src/test/java/DivekitCodeCompletionTest.java)
testet die Funktionalität des Plugins in Zusammenspiel mit dem Divekit-Language-Server. Wenn der Test erfolgreich ist, funktioniert die Code Completion
  wie erwartet. Der Test ist im Code ausführlich durch Kommentare beschrieben.
  
* Um den Test lokal laufen zu lassen muss in [Zeile 163](https://github.com/divekit/divekit-language-plugin-intellij/blob/ebe71b9a3120f6552e836e7d4c753061a6618f8d/src/test/java/DivekitCodeCompletionTest.java#L163)
  der absolute Pfad zur Divekit Language Server JAR-Datei eingetragen werden.
