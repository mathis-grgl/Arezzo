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
        this.arezzo = arezzo;
        this.arezzo.ajouterObservateur(this);

        par = this.arezzo.getPartition();
    }

    /**
     * Initialise la partie graphique de la vue gérée dans l'écouteur.
     */
    @FXML
    public void initialize(){
        listToucheClassique = List.of(Do,Re,Mi,Fa,Sol,La,Si);
        listToucheDiese = List.of(DoDiese,ReDiese,FaDiese,SolDiese,LaDiese);

        for(Rectangle touche : listToucheClassique)
            touche.setStyle("-fx-fill: #FAE8E0");

        for(Rectangle touche : listToucheDiese)
            touche.setStyle("-fx-fill: #D8A7B1");
    }

    /**
     * Permet de jouer et ajouter à la mélodie une note en fonction de la touche de piano appuyée.
     * @param event Les informations concernant le clic
     */
    @FXML
    public void jouerNotes(MouseEvent event){
        String note = event.getPickResult().getIntersectedNode().getId();
        String noteABC = arezzo.conversionNotesVersABC(note);
        String finalNote = arezzo.getNotationHauteurDuree(noteABC);

        par.play(finalNote);

        arezzo.notifierObservateurs();
    }

    /**
     * Ajoute un silence à la mélodie.
     */
    @FXML
    public void jouerSilence(){
        arezzo.getNotationHauteurDuree("z");
        arezzo.notifierObservateurs();
    }

    @Override
    public void reagir() {}
}