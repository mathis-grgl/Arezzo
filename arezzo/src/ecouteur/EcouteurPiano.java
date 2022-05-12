package ecouteur;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

import model.Arezzo;
import partition.Partition;

import java.util.ArrayList;
import java.util.List;

public class EcouteurPiano implements Observateur {
    private Arezzo arezzo;
    private Partition par;
    private List<String> listNotesABC, listNotes;

    public EcouteurPiano(Arezzo arezzo) {
        this.arezzo = arezzo;
        this.arezzo.ajouterObservateur(this);
        par = this.arezzo.getPartition();

        //Initialisation de la liste des notes en version ABC
        listNotesABC = new ArrayList<>();
        listNotesABC = List.of("C","^C","D","^D","E","F","^F","G","^G","A","^A","B");

        //Initialisation de la liste des notes en version classique
        listNotes = new ArrayList<>();
        listNotes = List.of("Do","DoDiese","Re","ReDiese","Mi","Fa","FaDiese","Sol","SolDiese","La","LaDiese","Si");
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

    private String conversionNotes(String note) {
        for (int i = 0; i < listNotes.size(); i++) {
            if(listNotes.get(i).equals(note)){
                return listNotesABC.get(i);
            }
        }
        return null;
    }

    @FXML
    public void jouerNotes(MouseEvent event){
        String note = event.getPickResult().getIntersectedNode().getId();
        String noteABC = conversionNotes(note);
        par.play(getNotationHauteurDuree(noteABC));
        arezzo.addMelodie(noteABC);
        arezzo.notifierObservateur();
    }



    @FXML
    public void jouerSilence(){
        arezzo.addMelodie("z1");
        arezzo.notifierObservateur();
    }

    @Override
    public void reagir() {

    }
}