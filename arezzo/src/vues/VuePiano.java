package vues;

import javafx.fxml.FXML;
import model.Arezzo;
import partition.Partition;

public class VuePiano implements Observateur {
    private Arezzo arezzo;
    private Partition par;

    public VuePiano(Arezzo arezzo){
            this.arezzo = arezzo;
            this.arezzo.ajouterObservateur(this);
            par = this.arezzo.getPartition();
    }

    public String getNotationHauteurDuree(String lettre){
        StringBuilder concatenation = new StringBuilder();
        String hauteur = arezzo.getHauteur();
        String lettreABC = lettre;
        switch(hauteur){
            case "aigu":
                lettreABC.toLowerCase();
                concatenation.append(lettreABC);
                break;
            case "grave":
                concatenation.append(lettreABC);
                concatenation.append(",");
                break;
            case "medium":
                concatenation.append(lettreABC);
        }

        return concatenation.toString();
    }

    @FXML
    public void jouerLa(){
        arezzo.notifierObservateur();
        par.play(getNotationHauteurDuree("B"));
    }

    @FXML
    public void jouerLaDiese(){
        arezzo.notifierObservateur();
        par.play("^C");
    }

    @FXML
    public void jouerSi(){
        arezzo.notifierObservateur();
        par.play("D");
    }

    @FXML
    public void jouerDo(){
        arezzo.notifierObservateur();
        par.play("E");
    }

    @FXML
    public void jouerDoDiese(){
        arezzo.notifierObservateur();
        par.play("^E");
    }

    @FXML
    public void jouerRe(){
        arezzo.notifierObservateur();
        par.play("F");
    }

    @FXML
    public void jouerReDiese(){
        arezzo.notifierObservateur();
        par.play("^F");
    }

    @FXML
    public void jouerMi(){
        arezzo.notifierObservateur();
        par.play("G");
    }

    @FXML
    public void jouerFa(){
        arezzo.notifierObservateur();
        par.play("A");
    }

    @FXML
    public void jouerFaDiese(){
        arezzo.notifierObservateur();
        par.play("^A");
    }

    @FXML
    public void jouerSol(){
        arezzo.notifierObservateur();
        par.play("B");
    }

    @FXML
    public void jouerSolDiese(){
        arezzo.notifierObservateur();
        par.play("^B");
    }

    @FXML
    public void jouerSilence(){
        arezzo.notifierObservateur();
        par.setMelodie("z2");
    }

    @Override
    public void reagir() {

    }
}