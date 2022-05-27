package ecouteur;

import java.util.ArrayList;

/**
 * Représente la classe SujetObserve.
 */
public class SujetObserve {
    private ArrayList<Observateur> tabObservateur;

    /**
     * Instancie une liste des observateurs.
     */
    public SujetObserve(){
        tabObservateur = new ArrayList<>();
    }

    /**
     * Ajoute un observateur à la liste.
     * @param o L'observateur à ajouter
     */
    public void ajouterObservateur(Observateur o){
        tabObservateur.add(o);
    }

    /**
     * Notifie les observateurs en appelant la fonction réagir de chacun.
     */
    public void notifierObservateurs() {
        for (Observateur o : tabObservateur) o.reagir();
    }
}

