- VM Options : --module-path /usr/share/openjfx/lib/ --add-modules javafx.controls,javafx.fxml -Djdk.gtk.version=2 -Dprism.forceGPU=true

- Si l'on veut que le volume soit définit dès le départ et réinitialiser quand on crée un nouveau projet, il faut décommenter les lignes correspondantes dans le code (l.69 et l.110 dans Arezzo.java)

- Le titre change de police quand on clone le projet, pas quand on lance depuis le jar.