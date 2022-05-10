package ecouteur;

import javafx.fxml.FXML;
import model.Arezzo;
import partition.Partition;

public class EcouteurPiano implements Observateur {
    private Arezzo arezzo;
    private Partition par;

    public EcouteurPiano(Arezzo arezzo){
            this.arezzo = arezzo;
            this.arezzo.ajouterObservateur(this);
            par = this.arezzo.getPartition();
    }

    public String getNotationHauteurDuree(String lettre){
        StringBuilder concatenation = new StringBuilder();
        String hauteur = arezzo.getHauteur();
        String duree = arezzo.getDuree();
        String lettreABC = lettre;
        System.out.println(lettreABC);
        System.out.println(hauteur);

        switch(hauteur){
            case "aigu":
                lettreABC = lettreABC.toLowerCase();
                concatenation.append(lettreABC);
                break;
            case "grave":
                concatenation.append(lettreABC);
                concatenation.append(",");
                break;
            case "medium":
                concatenation.append(lettreABC);
        }

        switch(duree){
            case "croche":
                concatenation.append("/");
                break;
            case "ronde":
                concatenation.append("4");
                break;
            case "blanche":
                concatenation.append("2");
                break;
        }
        System.out.println(concatenation);
        return concatenation.toString();
    }

    @FXML
    public void jouerLa(){
        arezzo.notifierObservateur();
        par.play(getNotationHauteurDuree("C"));
    }

    @FXML
    public void jouerLaDiese(){
        arezzo.notifierObservateur();
        par.play(getNotationHauteurDuree("^C"));
    }

    @FXML
    public void jouerSi(){
        arezzo.notifierObservateur();
        par.play(getNotationHauteurDuree("D"));
    }

    @FXML
    public void jouerDo(){
        arezzo.notifierObservateur();
        par.play(getNotationHauteurDuree("E"));
    }

    @FXML
    public void jouerDoDiese(){
        arezzo.notifierObservateur();
        par.play(getNotationHauteurDuree("^E"));
    }

    @FXML
    public void jouerRe(){
        arezzo.notifierObservateur();
        par.play(getNotationHauteurDuree("F"));
    }

    @FXML
    public void jouerReDiese(){
        arezzo.notifierObservateur();
        par.play(getNotationHauteurDuree("^F"));
    }

    @FXML
    public void jouerMi(){
        arezzo.notifierObservateur();
        par.play(getNotationHauteurDuree("G"));
    }

    @FXML
    public void jouerFa(){
        arezzo.notifierObservateur();
        par.play(getNotationHauteurDuree("A"));
    }

    @FXML
    public void jouerFaDiese(){
        arezzo.notifierObservateur();
        par.play(getNotationHauteurDuree("^A"));
    }

    @FXML
    public void jouerSol(){
        arezzo.notifierObservateur();
        par.play(getNotationHauteurDuree("B"));
    }

    @FXML
    public void jouerSolDiese(){
        arezzo.notifierObservateur();
        par.play(getNotationHauteurDuree("^B"));
    }

    @FXML
    public void jouerSilence(){
        arezzo.notifierObservateur();
        par.setMelodie(getNotationHauteurDuree("z1"));
    }

    @Override
    public void reagir() {

    }
}