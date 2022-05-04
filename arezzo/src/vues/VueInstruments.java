package vues;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Arezzo;
import partition.Partition;

public class VueInstruments implements Observateur{
    @FXML
    private ChoiceBox<String> boxInstruments;
    @FXML
    private ObservableList<String> listInstruments = FXCollections.observableArrayList("Piano","Guitare","Saxophone","Trompette");
    @FXML
    private RadioButton croche,noire,blanche, ronde,aigu,medium,grave;
    private ToggleGroup groupeCouleurs,groupeHauteurs;

    private Arezzo arezzo;
    private Partition par;

    public VueInstruments(Arezzo arezzo){
        this.arezzo = arezzo;
        this.arezzo.ajouterObservateur(this);
        par = this.arezzo.getPartition();
        groupeCouleurs = new ToggleGroup();
        groupeHauteurs = new ToggleGroup();
    }

    @FXML
    public void initialize(){
        //Liste instruments
        boxInstruments.setItems(listInstruments);
        boxInstruments.setValue("Piano");

        //Liste hauteurs
        aigu.setToggleGroup(groupeHauteurs);
        medium.setToggleGroup(groupeHauteurs);
        grave.setToggleGroup(groupeHauteurs);


        //Liste couleurs
        croche.setToggleGroup(groupeCouleurs);
        noire.setToggleGroup(groupeCouleurs);
        blanche.setToggleGroup(groupeCouleurs);
        ronde.setToggleGroup(groupeCouleurs);

        //Image croche
        ImageView crocheIMG = new ImageView(new Image("images/croche.png"));
        crocheIMG.setFitHeight(30);
        crocheIMG.setPreserveRatio(true);
        croche.setGraphic(crocheIMG);

        //Image blanche
        ImageView blancheIMG = new ImageView(new Image("images/blanche.png"));
        blancheIMG.setFitHeight(30);
        blancheIMG.setPreserveRatio(true);
        blanche.setGraphic(blancheIMG);

        //Image noire
        ImageView noireIMG = new ImageView(new Image("images/noire.png"));
        noireIMG.setFitHeight(30);
        noireIMG.setPreserveRatio(true);
        noire.setGraphic(noireIMG);

        //Image ronde
        ImageView rondeIMG = new ImageView(new Image("images/ronde.png"));
        rondeIMG.setFitHeight(15);
        rondeIMG.setPreserveRatio(true);
        ronde.setGraphic(rondeIMG);
    }

    @FXML
    public void setForme(){
        
    }

    @Override
    public void reagir() {
        par.setInstrument(boxInstruments.getValue());
        if(arezzo.getNouveauProjet()){
            croche.setSelected(true);
            medium.setSelected(true);
            boxInstruments.setValue("Piano");
        }
    }
}
