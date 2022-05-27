package ecouteur;

/**
 * Représente l'interface Observateur qui permet d'actualiser chaque sous classe de celle ci.
 */
public interface Observateur {
    /**
     * Fonction appelée à chaque fois que la fonction notifierObservateur de SujetObservé est appelée.
     */
    void reagir();
}
