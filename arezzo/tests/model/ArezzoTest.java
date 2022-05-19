package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import partition.Partition;

import static org.junit.jupiter.api.Assertions.*;

class ArezzoTest {
    private Arezzo arezzo;
    private Partition par;

    @BeforeEach
    void setUp(){
        arezzo = new Arezzo();
        par = arezzo.getPartition();
    }

    @Test
    void resetAll() {
        par.setMelodie("A B C C C C");
        arezzo.setTempo(220);
        arezzo.setNouveauProjet(false);
        arezzo.resetAll();
        assertTrue(arezzo.getNouveauProjet(),"Bug dans resetAll");
        assertEquals(arezzo.getMelodie(),"","Bug dans resetAll");
        assertEquals(arezzo.getTempo(),180,"Bug dans resetAll");
    }

    @Test
    void addMelodie() {
        assertEquals(arezzo.getMelodie(),"","bug dans addMelodie");
        arezzo.addMelodie("C");
        assertEquals(arezzo.getMelodie()," C","bug dans addMelodie");
        arezzo.addMelodie("B");
        assertEquals(arezzo.getMelodie()," C B","bug dans addMelodie");
        arezzo.addMelodie("D");
        assertEquals(arezzo.getMelodie()," C B D","bug dans addMelodie");
        arezzo.addMelodie("G");
        assertEquals(arezzo.getMelodie()," C B D G","bug dans addMelodie");
        arezzo.addMelodie("F");
        assertEquals(arezzo.getMelodie()," C B D G F","bug dans addMelodie");
    }

    @Test
    void getNotationHauteurDuree() {
        arezzo.setDuree("noire");
        assertEquals(arezzo.getNotationHauteurDuree("C"),"C","bug dans getNotationHauteurDuree");
        arezzo.setHauteur("aigu");
        assertEquals(arezzo.getNotationHauteurDuree("C"),"c","bug dans getNotationHauteurDuree");
        arezzo.setHauteur("grave");
        assertEquals(arezzo.getNotationHauteurDuree("C"),"C,","bug dans getNotationHauteurDuree");
        arezzo.setDuree("blanche");
        assertEquals(arezzo.getNotationHauteurDuree("C"),"C,2","bug dans getNotationHauteurDuree");
        arezzo.setDuree("ronde");
        assertEquals(arezzo.getNotationHauteurDuree("C"),"C,4","bug dans getNotationHauteurDuree");
        arezzo.setDuree("croche");
        assertEquals(arezzo.getNotationHauteurDuree("C"),"C,/","bug dans getNotationHauteurDuree");

    }

    @Test
    void conversionNotes() {
        assertEquals(arezzo.conversionNotes("Do"),"C");
        assertEquals(arezzo.conversionNotes("DoDiese"),"^C");
        assertEquals(arezzo.conversionNotes("Re"),"D");
        assertEquals(arezzo.conversionNotes("ReDiese"),"^D");
        assertEquals(arezzo.conversionNotes("Mi"),"E");
        assertEquals(arezzo.conversionNotes("Fa"),"F");
        assertEquals(arezzo.conversionNotes("FaDiese"),"^F");
        assertEquals(arezzo.conversionNotes("Sol"),"G");
        assertEquals(arezzo.conversionNotes("SolDiese"),"^G");
        assertEquals(arezzo.conversionNotes("La"),"A");
        assertEquals(arezzo.conversionNotes("LaDiese"),"^A");
        assertEquals(arezzo.conversionNotes("Si"),"B");
    }
}