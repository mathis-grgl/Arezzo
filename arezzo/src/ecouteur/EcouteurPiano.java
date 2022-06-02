package ecouteur;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import model.Arezzo;
import partition.Partition;
import java.util.List;

/**
 * L'écouteur lié à la VuePiano.
 */
public class EcouteurPiano implements Observateur {
    @FXML
    private Rectangle Do,DoDiese,Re,ReDiese,Mi,Fa,FaDiese,Sol,SolDiese,La,LaDiese,Si;
    @FXML
    private Button chut;
    private List<Rectangle> listToucheClassique,listToucheDiese;
    private Arezzo arezzo;
    private Partition par;

    /**
     * Instancie une nouvelle instance de EcouteurPiano.
     * @param arezzo Le model Arezzo
     */
    public EcouteurPiano(Arezzo arezzo) {
        //Initialise le model et ajoute l'écouteur en tant qu'Observateur
        this.arezzo = arezzo;
        this.arezzo.ajouterObservateur(this);

        //Initialise la partition
        par = this.arezzo.getPartition();
    }

    /**
     * Initialise la partie graphique de la vue gérée dans l'écouteur.
     */
    @FXML
    public void initialize(){
        //Initialise les listes des rectangles touches
        listToucheClassique = List.of(Do,Re,Mi,Fa,Sol,La,Si);
        listToucheDiese = List.of(DoDiese,ReDiese,FaDiese,SolDiese,LaDiese);

        //Modifie la couleur des touches classiques
        for(Rectangle touche : listToucheClassique)
            touche.setStyle("-fx-fill: #FAE8E0");

        //Modifie la couleur des touches dièses
        for(Rectangle touche : listToucheDiese)
            touche.setStyle("-fx-fill: #D8A7B1");

        //Modifie la couleur du bouton silence en rose chaud
        chut.setStyle("-fx-base: #EF7C8E");
    }

    /**
     * Permet de jouer et ajouter à la mélodie une note en fonction de la touche de piano appuyée.
     * @param event Les informations concernant le clic
     */
    @FXML
    public void jouerNotes(MouseEvent event){
        //Permet de récupérer la touche cliquée dans un String
        String note = event.getPickResult().getIntersectedNode().getId();

        //On convertit la note dans sa version ABC
        String noteABC = arezzo.conversionNotesVersABC(note);

        //Puis on termine par ajouter la durée et l'octave à la note (en plus de l'ajouter à la mélodie via la fonction getNotationHauteurDuree)
        String finalNote = arezzo.getNotationHauteurDuree(noteABC);

        //On joue la note
        par.play(finalNote);

        //Notifie les observateurs
        arezzo.notifierObservateurs();
    }

    /**
     * Ajoute un silence à la mélodie.
     */
    @FXML
    public void jouerSilence(){
        //On ajoute à la mélodie un silence avec la durée de celui-ci
        arezzo.getNotationHauteurDuree("z");
        arezzo.notifierObservateurs();
    }

    @Override
    public void reagir() {
        arezzo.applyCouleurListe();
    }
}