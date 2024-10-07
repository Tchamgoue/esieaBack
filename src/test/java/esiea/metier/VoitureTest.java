package esiea.metier;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
class VoitureTest {

    @Mock
    Voiture voiture = new Voiture();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void testGetTypeDonneeWithString(){

        // Tableau des valeurs attendues pour les strings
        String[] donneesString = {"marque", "modele", "finition"};

        // Boucle sur chaque valeur dans le tableau
        for (String donnee : donneesString) {
            String resultat = voiture.getTypeDonnee(donnee);
            assertEquals("string", resultat, "Erreur avec la donnée : " + donnee);
        }
    }


    @Test
    public void testGetTypeDonneeWithEntier() {
        // Tableau des valeurs attendues pour les entiers
        String[] donneesEntiere = {"id", "annee", "km", "prix"};

        // Boucle sur chaque valeur dans le tableau
        for (String donnee : donneesEntiere) {
            String resultat = voiture.getTypeDonnee(donnee);
            assertEquals("entier", resultat, "Erreur avec la donnée : " + donnee);
        }
    }




    @Test
    public void testCheckWithValidValues() {
        // declaration des variable de test

        int id = 1;
        int km = 50000;
        int annee =2015;
        int prix = 15000;

        // Simuler des valeurs valides pour la voiture
        Mockito.when(voiture.getId()).thenReturn(id);
        Mockito.when(voiture.getMarque()).thenReturn("Toyota");
        Mockito.when(voiture.getModele()).thenReturn("Corolla");
        Mockito.when(voiture.getFinition()).thenReturn("Sport");
        //Mockito.when(voiture.getCarburant()).thenReturn("Essence");
        Mockito.when(voiture.getKm()).thenReturn(km);
        Mockito.when(voiture.getAnnee()).thenReturn(annee);
        Mockito.when(voiture.getPrix()).thenReturn(prix);

        // Vérifier que check() renvoie true
        assertTrue(voiture.check());
    }

    @Test
    public void testCheckWithInvalidId() {
        // Simuler un id invalide
        int invalidid = -1;
        Mockito.when(voiture.getId()).thenReturn(invalidid);

        // Vérifier que check() renvoie false
        assertFalse(voiture.check());
    }

    @Test
    public void testCheckWithNullMarque() {
        // Simuler une marque nulle
        String marque= null;
        Mockito.when(voiture.getMarque()).thenReturn(marque);

        // Vérifier que check() renvoie false
        assertFalse(voiture.check());
    }

    @Test
    public void testCheckWithEmptyModele() {
        // Simuler un modèle vide
        Mockito.when(voiture.getModele()).thenReturn("");

        // Vérifier que check() renvoie false
        assertFalse(voiture.check());
    }

    @Test
    public void testCheckWithInvalidAnnee() {
        // Simuler une année invalide;
        int annee = 1899;
        Mockito.when(voiture.getAnnee()).thenReturn(annee);

        // Vérifier que check() renvoie false
        assertFalse(voiture.check());
    }

    @Test
    public void testCheckWithInvalidPrix() {
        // Simuler un prix négatif
        int price = -1000;
        Mockito.when(voiture.getPrix()).thenReturn(price);

        // Vérifier que check() renvoie false
        assertFalse(voiture.check());
    }

}