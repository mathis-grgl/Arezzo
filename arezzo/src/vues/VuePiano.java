package vues;

import javafx.fxml.FXML;
import melodie.Arezzo;
import partition.Partition;

public class VuePiano implements Observateur {
    private Arezzo arezzo;

    public VuePiano(Arezzo arezzo){
            this.arezzo = arezzo;
    }

    @FXML
    public void jouerLa(){
        partitio.play("C");
    }

    @FXML
    public void jouerLaDiese(){
        partitio.play("^C");
    }

    @FXML
    public void jouerSi(){
        partitio.play("D");
    }

    @FXML
    public void jouerDo(){
        partitio.play("E");

    }

    @FXML
    public void jouerDoDiese(){
        partitio.play("^E");
    }

    @FXML
    public void jouerRe(){
        partitio.play("F");
    }

    @FXML
    public void jouerReDiese(){
        partitio.play("^F");
    }

    @FXML
    public void jouerMi(){
        partitio.play("G");
    }

    @FXML
    public void jouerFa(){
        partitio.play("A");
    }

    @FXML
    public void jouerFaDiese(){
        partitio.play("^A");
    }

    @FXML
    public void jouerSol(){
        partitio.play("B");
    }

    @FXML
    public void jouerSolDiese(){
        partitio.play("^B");
    }

    @FXML
    public void jouerSilence(){
        partitio.setMelodie("z2");
        partitio.play();
    }

    @Override
    public void reagir() {

    }
}