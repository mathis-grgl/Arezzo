package ecouteur;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

import model.Arezzo;
import partition.Partition;

import java.util.ArrayList;

public class EcouteurPiano implements Observateur {
    private Arezzo arezzo;
    private Partition par;
    private ArrayList<String> listNotesABC, listNotes;

    public EcouteurPiano(Arezzo arezzo) {
        this.arezzo = arezzo;
        this.arezzo.ajouterObservateur(this);
        par = this.arezzo.getPartition();

        //Initialisation de la liste des notes en version ABC
        listNotesABC = new ArrayList<>();
        listNotesABC.add("C");
        listNotesABC.add("^C");
        listNotesABC.add("D");
        listNotesABC.add("^D");
        listNotesABC.add("E");
        listNotesABC.add("F");
        listNotesABC.add("^F");
        listNotesABC.add("G");
        listNotesABC.add("^G");
        listNotesABC.add("A");
        listNotesABC.add("^A");
        listNotesABC.add("B");

        //Initialisation de la liste des notes en version classique
        listNotes = new ArrayList<>();
        listNotes.add("Do");
        listNotes.add("DoDiese");
        listNotes.add("Re");
        listNotes.add("ReDiese");
        listNotes.add("Mi");
        listNotes.add("Fa");
        listNotes.add("FaDiese");
        listNotes.add("Sol");
        listNotes.add("SolDiese");
        listNotes.add("La");
        listNotes.add("LaDiese");
        listNotes.add("Si");
    }



    public String getNotationHauteurDuree(String lettre){
        StringBuilder concatenation = new StringBuilder();
        String hauteur = arezzo.getHauteur();
        String duree = arezzo.getDuree();
        String lettreABC = lettre;

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
        return concatenation.toString();
    }

    private String conversionNotes(String notes) {
        for (int i = 0; i < listNotes.size(); i++) {
            if(listNotes.get(i).equals(notes)){
                return listNotesABC.get(i);
            }
        }
        return null;
    }

    @FXML
    public void jouerNotes(MouseEvent event){
        String notes = event.getPickResult().getIntersectedNode().getId();
        String conversionABC = conversionNotes(notes);
        par.play(getNotationHauteurDuree(conversionABC));

        arezzo.notifierObservateur();
    }



    @FXML
    public void jouerSilence(){
        par.setMelodie(getNotationHauteurDuree("z1"));
        arezzo.notifierObservateur();
    }

    @Override
    public void reagir() {

    }
}