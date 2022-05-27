package model;

import javafx.collections.FXCollections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import partition.Partition;

import java.util.List;

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
        arezzo.setTempo(220.0);
        arezzo.setNouveauProjet(false);
        arezzo.resetAll();
        assertTrue(arezzo.getNouveauProjet(),"Bug dans resetAll");
        assertEquals("",arezzo.getMelodie(),"Bug dans resetAll");
        assertEquals("Nouveau projet",arezzo.getTitre(),"Bug dans resetAll");
        assertEquals(0,arezzo.getListMelodie().size(),"Bug dans resetAll");
        assertEquals(180,arezzo.getTempo(),"Bug dans resetAll");
    }

    @Test
    void addMelodie() {
        assertEquals("",arezzo.getMelodie(),"bug dans addMelodie");
        arezzo.addMelodie("C");
        assertEquals("C ",arezzo.getMelodie(),"bug dans addMelodie");
        arezzo.addMelodie("B");
        assertEquals("C B ",arezzo.getMelodie(),"bug dans addMelodie");
        arezzo.addMelodie("D");
        assertEquals("C B D ",arezzo.getMelodie(),"bug dans addMelodie");
        arezzo.addMelodie("G");
        assertEquals("C B D G ",arezzo.getMelodie(),"bug dans addMelodie");
        arezzo.addMelodie("F");
        assertEquals("C B D G F ",arezzo.getMelodie(),"bug dans addMelodie");
    }

    @Test
    void getNotationHauteurDuree() {
        arezzo.setDuree("noire");
        assertEquals("C1",arezzo.getNotationHauteurDuree("C"),"bug dans getNotationHauteurDuree");
        arezzo.setHauteur("aigu");
        assertEquals("c1",arezzo.getNotationHauteurDuree("C"),"bug dans getNotationHauteurDuree");
        arezzo.setHauteur("grave");
        assertEquals("C,1",arezzo.getNotationHauteurDuree("C"),"bug dans getNotationHauteurDuree");
        arezzo.setDuree("blanche");
        assertEquals("C,2",arezzo.getNotationHauteurDuree("C"),"bug dans getNotationHauteurDuree");
        arezzo.setDuree("ronde");
        assertEquals("C,4",arezzo.getNotationHauteurDuree("C"),"bug dans getNotationHauteurDuree");
        arezzo.setDuree("croche");
        assertEquals("C,/",arezzo.getNotationHauteurDuree("C"),"bug dans getNotationHauteurDuree");

    }

    @Test
    void conversionNotes() {
        assertEquals("C",arezzo.conversionNotesVersABC("Do"),"Bug dans conversionNotes");
        assertEquals("^C",arezzo.conversionNotesVersABC("DoDiese"),"Bug dans conversionNotes");
        assertEquals("D",arezzo.conversionNotesVersABC("Re"),"Bug dans conversionNotes");
        assertEquals("^D",arezzo.conversionNotesVersABC("ReDiese"),"Bug dans conversionNotes");
        assertEquals("E",arezzo.conversionNotesVersABC("Mi"),"Bug dans conversionNotes");
        assertEquals("F",arezzo.conversionNotesVersABC("Fa"),"Bug dans conversionNotes");
        assertEquals("^F",arezzo.conversionNotesVersABC("FaDiese"),"Bug dans conversionNotes");
        assertEquals("G",arezzo.conversionNotesVersABC("Sol"),"Bug dans conversionNotes");
        assertEquals("^G",arezzo.conversionNotesVersABC("SolDiese"),"Bug dans conversionNotes");
        assertEquals("A",arezzo.conversionNotesVersABC("La"),"Bug dans conversionNotes");
        assertEquals("^A",arezzo.conversionNotesVersABC("LaDiese"),"Bug dans conversionNotes");
        assertEquals("B",arezzo.conversionNotesVersABC("Si"),"Bug dans conversionNotes");
    }

    @Test
    void deleteMelodie() {
        assertEquals(arezzo.getListMelodie().size(),0,"Bug dans deleteMelodie");
        arezzo.addMelodie("C");
        assertNotEquals(arezzo.getListMelodie().size(),0,"Bug dans deleteMelodie");
        arezzo.addMelodie("C");
        assertNotEquals(arezzo.getListMelodie().size(),0,"Bug dans deleteMelodie");
        arezzo.addMelodie("C");
        assertNotEquals(arezzo.getListMelodie().size(),0,"Bug dans deleteMelodie");
        arezzo.addMelodie("C");
        assertNotEquals(arezzo.getListMelodie().size(),0,"Bug dans deleteMelodie");
        arezzo.addMelodie("C");
        assertNotEquals(arezzo.getListMelodie().size(),0,"Bug dans deleteMelodie");
        arezzo.deleteMelodie();
        assertEquals(arezzo.getListMelodie().size(),0,"Bug dans deleteMelodie");
    }

    @Test
    void convertirListEnMelodie(){
        arezzo.addMelodie("C");
        arezzo.addMelodie("C/");
        arezzo.addMelodie("D");
        arezzo.addMelodie("A");
        arezzo.addMelodie("A4");
        arezzo.convertirListEnMelodie();
        assertEquals(arezzo.getMelodie().length(),12,"Bug dans convertirListEnMelodie");
    }

    @Test
    void convertirMelodieEnList() {
        arezzo.addMelodie("C");
        arezzo.addMelodie("C/");
        arezzo.addMelodie("D");
        arezzo.addMelodie("A");
        arezzo.addMelodie("A4");
        arezzo.convertirListEnMelodie();
        arezzo.getListMelodie().clear();
        assertEquals(arezzo.getListMelodie().size(),0,"Bug dans convertirMelodieEnList");
        arezzo.convertirMelodieEnList();
        System.out.println(arezzo.getMelodie());
        assertEquals(arezzo.getListMelodie().size(),5,"Bug dans convertirMelodieEnList");
    }

    @Test
    void transposerNotesArezzo() {
        arezzo.addMelodie("C1");
        arezzo.addMelodie("C/");
        arezzo.addMelodie("D1");
        arezzo.addMelodie("A1");
        arezzo.addMelodie("A4");
        arezzo.transposerNotesArezzo(1);
        assertEquals("^C1",arezzo.getNoteMelodie(0),"Bug dans transposerNotesArezzo");
        assertEquals("^C/",arezzo.getNoteMelodie(1),"Bug dans transposerNotesArezzo");
        assertEquals("^D1",arezzo.getNoteMelodie(2),"Bug dans transposerNotesArezzo");
        assertEquals("^A1",arezzo.getNoteMelodie(3),"Bug dans transposerNotesArezzo");
        assertEquals("^A4",arezzo.getNoteMelodie(4),"Bug dans transposerNotesArezzo");
        arezzo.addMelodie("C1");
        arezzo.addMelodie("C/");
        arezzo.addMelodie("D1");
        arezzo.addMelodie("A1");
        arezzo.addMelodie("A4");
        arezzo.transposerNotesArezzo(1);
        assertEquals("D1",arezzo.getNoteMelodie(0),"Bug dans transposerNotesArezzo");
        assertEquals("D/",arezzo.getNoteMelodie(1),"Bug dans transposerNotesArezzo");
        assertEquals("E1",arezzo.getNoteMelodie(2),"Bug dans transposerNotesArezzo");
        assertEquals("B1",arezzo.getNoteMelodie(3),"Bug dans transposerNotesArezzo");
        assertEquals("B4",arezzo.getNoteMelodie(4),"Bug dans transposerNotesArezzo");
        assertEquals("^C1",arezzo.getNoteMelodie(5),"Bug dans transposerNotesArezzo");
        assertEquals("^C/",arezzo.getNoteMelodie(6),"Bug dans transposerNotesArezzo");
        assertEquals("^D1",arezzo.getNoteMelodie(7),"Bug dans transposerNotesArezzo");
        assertEquals("^A1",arezzo.getNoteMelodie(8),"Bug dans transposerNotesArezzo");
        assertEquals("^A4",arezzo.getNoteMelodie(9),"Bug dans transposerNotesArezzo");
    }

    @Test
    void noteSansSurPlusMajuscule() {
        assertEquals("D",arezzo.noteSansSurPlusMajuscule("D1"),"Bug dans noteSansSurPlusMajuscule");
        assertEquals("D",arezzo.noteSansSurPlusMajuscule("D/"),"Bug dans noteSansSurPlusMajuscule");
        assertEquals("E",arezzo.noteSansSurPlusMajuscule("E1"),"Bug dans noteSansSurPlusMajuscule");
        assertEquals("B",arezzo.noteSansSurPlusMajuscule("B1"),"Bug dans noteSansSurPlusMajuscule");
        assertEquals("B",arezzo.noteSansSurPlusMajuscule("B4"),"Bug dans noteSansSurPlusMajuscule");
        assertEquals("^C",arezzo.noteSansSurPlusMajuscule("^C1"),"Bug dans noteSansSurPlusMajuscule");
        assertEquals("^C",arezzo.noteSansSurPlusMajuscule("^C/"),"Bug dans noteSansSurPlusMajuscule");
        assertEquals("^D",arezzo.noteSansSurPlusMajuscule("^D1"),"Bug dans noteSansSurPlusMajuscule");
        assertEquals("^A",arezzo.noteSansSurPlusMajuscule("^A1"),"Bug dans noteSansSurPlusMajuscule");
        assertEquals("^A",arezzo.noteSansSurPlusMajuscule("^A4"),"Bug dans noteSansSurPlusMajuscule");
    }

    @Test
    void conversionNotesVersABC() {
        assertEquals("^C",arezzo.conversionNotesVersABC("DoDiese"),"Bug dans conversionNotesVersABC");
        assertEquals("C",arezzo.conversionNotesVersABC("Do"),"Bug dans conversionNotesVersABC");
        assertEquals("D",arezzo.conversionNotesVersABC("Re"),"Bug dans conversionNotesVersABC");
        assertEquals("^D",arezzo.conversionNotesVersABC("ReDiese"),"Bug dans conversionNotesVersABC");
        assertEquals("E",arezzo.conversionNotesVersABC("Mi"),"Bug dans conversionNotesVersABC");
        assertEquals("F",arezzo.conversionNotesVersABC("Fa"),"Bug dans conversionNotesVersABC");
        assertEquals("^F",arezzo.conversionNotesVersABC("FaDiese"),"Bug dans conversionNotesVersABC");
        assertEquals("G",arezzo.conversionNotesVersABC("Sol"),"Bug dans conversionNotesVersABC");
        assertEquals("^G",arezzo.conversionNotesVersABC("SolDiese"),"Bug dans conversionNotesVersABC");
        assertEquals("A",arezzo.conversionNotesVersABC("La"),"Bug dans conversionNotesVersABC");
        assertEquals("^A",arezzo.conversionNotesVersABC("LaDiese"),"Bug dans conversionNotesVersABC");
        assertEquals("B",arezzo.conversionNotesVersABC("Si"),"Bug dans conversionNotesVersABC");
    }

    @Test
    void conversionNotesVersClassique() {
        assertEquals("DoDiese",arezzo.conversionNotesVersClassique("^C"),"Bug dans conversionNotesVersClassique");
        assertEquals("Do",arezzo.conversionNotesVersClassique("C"),"Bug dans conversionNotesVersClassique");
        assertEquals("Re",arezzo.conversionNotesVersClassique("D"),"Bug dans conversionNotesVersClassique");
        assertEquals("ReDiese",arezzo.conversionNotesVersClassique("^D"),"Bug dans conversionNotesVersClassique");
        assertEquals("Mi",arezzo.conversionNotesVersClassique("E"),"Bug dans conversionNotesVersClassique");
        assertEquals("Fa",arezzo.conversionNotesVersClassique("F"),"Bug dans conversionNotesVersClassique");
        assertEquals("FaDiese",arezzo.conversionNotesVersClassique("^F"),"Bug dans conversionNotesVersClassique");
        assertEquals("Sol",arezzo.conversionNotesVersClassique("G"),"Bug dans conversionNotesVersClassique");
        assertEquals("SolDiese",arezzo.conversionNotesVersClassique("^G"),"Bug dans conversionNotesVersClassique");
        assertEquals("La",arezzo.conversionNotesVersClassique("A"),"Bug dans conversionNotesVersClassique");
        assertEquals("LaDiese",arezzo.conversionNotesVersClassique("^A"),"Bug dans conversionNotesVersClassique");
        assertEquals("Si",arezzo.conversionNotesVersClassique("B"),"Bug dans conversionNotesVersClassique");
    }

    @Test
    void supprimerNote() {
        arezzo.addMelodie("C1");
        arezzo.addMelodie("C/");
        arezzo.addMelodie("D1");
        arezzo.addMelodie("A1");
        arezzo.addMelodie("A4");
        arezzo.addMelodie("C1");
        arezzo.addMelodie("C/");
        arezzo.addMelodie("D1");
        arezzo.addMelodie("A1");
        arezzo.addMelodie("A4");
        arezzo.addMelodie("C1");
        arezzo.addMelodie("C/");
        arezzo.addMelodie("D1");
        arezzo.addMelodie("A1");
        arezzo.addMelodie("A4");
        assertEquals("C1",arezzo.getNoteMelodie(0),"Bug dans supprimerNote");
        assertEquals("D1",arezzo.getNoteMelodie(2),"Bug dans supprimerNote");
        assertEquals("C/",arezzo.getNoteMelodie(6),"Bug dans supprimerNote");
        assertEquals("A1",arezzo.getNoteMelodie(8),"Bug dans supprimerNote");
        assertEquals("D1",arezzo.getNoteMelodie(7),"Bug dans supprimerNote");
        assertEquals("C1",arezzo.getNoteMelodie(5),"Bug dans supprimerNote");
        arezzo.supprimerNote(FXCollections.observableArrayList(List.of(0,2,6,8,7,5)));
        assertEquals("C/",arezzo.getNoteMelodie(0),"Bug dans supprimerNote");
        assertEquals("A4",arezzo.getNoteMelodie(2),"Bug dans supprimerNote");
        assertEquals("D1",arezzo.getNoteMelodie(6),"Bug dans supprimerNote");
        assertEquals("A4",arezzo.getNoteMelodie(8),"Bug dans supprimerNote");
        assertEquals("A1",arezzo.getNoteMelodie(7),"Bug dans supprimerNote");
        assertEquals("A4",arezzo.getNoteMelodie(5),"Bug dans supprimerNote");
        assertEquals(9,arezzo.getListMelodie().size(),"Bug dans supprimerNote");
    }

    @Test
    void transposerNoteComposition() {
        arezzo.addMelodie("D1");
        arezzo.addMelodie("D/");
        arezzo.addMelodie("E1");
        arezzo.addMelodie("B1");
        arezzo.addMelodie("B4");
        arezzo.addMelodie("^C1");
        arezzo.addMelodie("^C/");
        arezzo.addMelodie("^D1");
        arezzo.addMelodie("^A1");
        arezzo.addMelodie("^A4");
        assertEquals("D1",arezzo.getNoteMelodie(0),"Bug dans transposerNoteComposition");
        assertEquals("D/",arezzo.getNoteMelodie(1),"Bug dans transposerNoteComposition");
        assertEquals("E1",arezzo.getNoteMelodie(2),"Bug dans transposerNoteComposition");
        assertEquals("B1",arezzo.getNoteMelodie(3),"Bug dans transposerNoteComposition");
        assertEquals("B4",arezzo.getNoteMelodie(4),"Bug dans transposerNoteComposition");
        assertEquals("^C1",arezzo.getNoteMelodie(5),"Bug dans transposerNoteComposition");
        assertEquals("^C/",arezzo.getNoteMelodie(6),"Bug dans transposerNoteComposition");
        assertEquals("^D1",arezzo.getNoteMelodie(7),"Bug dans transposerNoteComposition");
        assertEquals("^A1",arezzo.getNoteMelodie(8),"Bug dans transposerNoteComposition");
        assertEquals("^A4",arezzo.getNoteMelodie(9),"Bug dans transposerNoteComposition");

        arezzo.transposerNoteComposition(1, FXCollections.observableArrayList(List.of(0,2,4,8,9)));

        assertEquals("^D1",arezzo.getNoteMelodie(0),"Bug dans transposerNoteComposition");
        assertEquals("D/",arezzo.getNoteMelodie(1),"Bug dans transposerNoteComposition");
        assertEquals("F1",arezzo.getNoteMelodie(2),"Bug dans transposerNoteComposition");
        assertEquals("B1",arezzo.getNoteMelodie(3),"Bug dans transposerNoteComposition");
        assertEquals("c4",arezzo.getNoteMelodie(4),"Bug dans transposerNoteComposition");
        assertEquals("^C1",arezzo.getNoteMelodie(5),"Bug dans transposerNoteComposition");
        assertEquals("^C/",arezzo.getNoteMelodie(6),"Bug dans transposerNoteComposition");
        assertEquals("^D1",arezzo.getNoteMelodie(7),"Bug dans transposerNoteComposition");
        assertEquals("B1",arezzo.getNoteMelodie(8),"Bug dans transposerNoteComposition");
        assertEquals("B4",arezzo.getNoteMelodie(9),"Bug dans transposerNoteComposition");
    }

    @Test
    void noteEstUnSilence() {
        assertFalse(arezzo.noteEstUnSilence("C"),"Bug dans noteEstUnSilence");
        assertFalse(arezzo.noteEstUnSilence("^C"),"Bug dans noteEstUnSilence");
        assertFalse(arezzo.noteEstUnSilence("D"),"Bug dans noteEstUnSilence");
        assertFalse(arezzo.noteEstUnSilence("^D"),"Bug dans noteEstUnSilence");
        assertFalse(arezzo.noteEstUnSilence("E"),"Bug dans noteEstUnSilence");
        assertFalse(arezzo.noteEstUnSilence("F"),"Bug dans noteEstUnSilence");
        assertFalse(arezzo.noteEstUnSilence("^F"),"Bug dans noteEstUnSilence");
        assertFalse(arezzo.noteEstUnSilence("G"),"Bug dans noteEstUnSilence");
        assertFalse(arezzo.noteEstUnSilence("^G"),"Bug dans noteEstUnSilence");
        assertFalse(arezzo.noteEstUnSilence("A"),"Bug dans noteEstUnSilence");
        assertFalse(arezzo.noteEstUnSilence("^A"),"Bug dans noteEstUnSilence");
        assertFalse(arezzo.noteEstUnSilence("B"),"Bug dans noteEstUnSilence");
        assertTrue(arezzo.noteEstUnSilence("Z"),"Bug dans noteEstUnSilence");
    }

    @Test
    void getOctaveNote() {
        assertEquals("grave",arezzo.getOctaveNote("C,1"),"Bug dans getOctaveNote");
        assertEquals("aigu",arezzo.getOctaveNote("^c1"),"Bug dans getOctaveNote");
        assertEquals("medium",arezzo.getOctaveNote("D2"),"Bug dans getOctaveNote");
    }

    @Test
    void getDureeNote() {
        assertEquals("croche",arezzo.getDureeNote("C,/"),"Bug dans getDureeNote");
        assertEquals("noire",arezzo.getDureeNote("^c1"),"Bug dans getDureeNote");
        assertEquals("blanche",arezzo.getDureeNote("D2"),"Bug dans getDureeNote");
        assertEquals("ronde",arezzo.getDureeNote("D4"),"Bug dans getDureeNote");
    }

    @Test
    void getDureeSilence() {
        assertEquals("demiSoupir",arezzo.getDureeSilence("C,/"),"Bug dans getDureeSilence");
        assertEquals("soupir",arezzo.getDureeSilence("^c1"),"Bug dans getDureeSilence");
        assertEquals("demiPause",arezzo.getDureeSilence("D2"),"Bug dans getDureeSilence");
        assertEquals("pause",arezzo.getDureeSilence("D4"),"Bug dans getDureeSilence");
    }

    @Test
    void nePeutPlusEtreSupprimer() {
        assertFalse(arezzo.nePeutPlusEtreSupprimer(),"Bug dans nePeutPlusEtreSupprimer");
        arezzo.addMelodie("C");
        assertTrue(arezzo.nePeutPlusEtreSupprimer(),"Bug dans nePeutPlusEtreSupprimer");
        arezzo.addMelodie("C");
        assertFalse(arezzo.nePeutPlusEtreSupprimer(),"Bug dans nePeutPlusEtreSupprimer");
        arezzo.supprimerNote(FXCollections.observableArrayList(List.of(0)));
        assertTrue(arezzo.nePeutPlusEtreSupprimer(),"Bug dans nePeutPlusEtreSupprimer");
    }
}