package esiea.metier;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class VoitureTest {

    Voiture voiture = new Voiture();

    @Test
    public void testGetTypeDonneeWithString() {
        // Tableau des valeurs attendues pour les strings
        String[] donneesString = {"marque", "modele", "finition"};

        // Boucle sur chaque valeur dans le tableau
        for (String donnee : donneesString) {
            String resultat = Voiture.getTypeDonnee(donnee);
            assertEquals("string", resultat, "Erreur avec la donnée : " + donnee);
        }
    }

    @Test
    public void testGetTypeDonneeWithEntier() {
        // Tableau des valeurs attendues pour les entiers
        String[] donneesEntiere = {"id", "annee", "km", "prix"};

        // Boucle sur chaque valeur dans le tableau
        for (String donnee : donneesEntiere) {
            String resultat = Voiture.getTypeDonnee(donnee);
            assertEquals("entier", resultat, "Erreur avec la donnée : " + donnee);
        }
    }

    @Test
    public void testCheckWithValidValues() {
        // Déclaration des valeurs valides
        voiture.setId(1);
        voiture.setMarque("Toyota");
        voiture.setModele("Corolla");
        voiture.setFinition("Sport");
        voiture.setCarburant(Voiture.Carburant.valueOf("ESSENCE"));
        voiture.setKm(50000);
        voiture.setAnnee(2015);
        voiture.setPrix(15000);

        // Vérifier que check() renvoie true
        assertTrue(voiture.check());
    }

    @Test
    public void testCheckWithInvalidId() {
        // Déclaration d'un ID invalide
        voiture.setId(-1); // ID négatif
        voiture.setMarque("Toyota");
        voiture.setModele("Corolla");
        voiture.setFinition("Sport");
        voiture.setCarburant(Voiture.Carburant.valueOf("ESSENCE"));
        voiture.setKm(50000);
        voiture.setAnnee(2015);
        voiture.setPrix(15000);

        // Vérifier que check() renvoie false
        assertFalse(voiture.check());
    }

    @Test
    public void testCheckWithNullMarque() {
        // Déclaration d'une marque nulle
        voiture.setId(1);
        voiture.setMarque(null); // Marque nulle
        voiture.setModele("Corolla");
        voiture.setFinition("Sport");
        voiture.setCarburant(Voiture.Carburant.valueOf("ESSENCE"));
        voiture.setKm(50000);
        voiture.setAnnee(2015);
        voiture.setPrix(15000);

        // Vérifier que check() renvoie false
        assertFalse(voiture.check());
    }

    @Test
    public void testCheckWithEmptyModele() {
        // Déclaration d'un modèle vide
        voiture.setId(1);
        voiture.setMarque("Toyota");
        voiture.setModele(""); // Modèle vide
        voiture.setFinition("Sport");
        voiture.setCarburant(Voiture.Carburant.valueOf("ESSENCE"));
        voiture.setKm(50000);
        voiture.setAnnee(2015);
        voiture.setPrix(15000);

        // Vérifier que check() renvoie false
        assertFalse(voiture.check());
    }

    @Test
    public void testCheckWithInvalidAnnee() {
        // Déclaration d'une année invalide
        voiture.setId(1);
        voiture.setMarque("Toyota");
        voiture.setModele("Corolla");
        voiture.setFinition("Sport");
        voiture.setCarburant(Voiture.Carburant.valueOf("ESSENCE"));
        voiture.setKm(50000);
        voiture.setAnnee(1899); // Année invalide
        voiture.setPrix(15000);

        // Vérifier que check() renvoie false
        assertFalse(voiture.check());
    }

    @Test
    public void testCheckWithInvalidPrix() {
        // Déclaration d'un prix négatif
        voiture.setId(1);
        voiture.setMarque("Toyota");
        voiture.setModele("Corolla");
        voiture.setFinition("Sport");
        voiture.setCarburant(Voiture.Carburant.valueOf("ESSENCE"));
        voiture.setKm(50000);
        voiture.setAnnee(2015);
        voiture.setPrix(-1000);


        assertFalse(voiture.check());
    }
}
