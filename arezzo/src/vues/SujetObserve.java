package vues;

import java.util.ArrayList;

public class SujetObserve {
    private ArrayList<Observateur> tabO;

    public SujetObserve(){
        tabO = new ArrayList<>();
    }

    public void notifierObservateur(){
        for(Observateur o : tabO){
            o.reagir();
        }
    }

    public void ajouterObservateur(Observateur o){
        tabO.add(o);
    }
}
