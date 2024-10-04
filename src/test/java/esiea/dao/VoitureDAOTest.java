package esiea.dao;

import esiea.metier.Voiture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class VoitureDAOTest {

    @Mock
    private Connection mockConnection;
    @Mock
    private PreparedStatement mockPreparedStatement;
    @InjectMocks
    private VoitureDAO voitureDAO;

    @BeforeEach
    public void setUp() throws SQLException {

        MockitoAnnotations.openMocks(this);

        when(voitureDAO.getConnexion()).thenReturn(mockConnection);

        // Simuler la méthode prepareStatement() pour qu'elle retourne le mock de PreparedStatement
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
    }

    @Test
    public void testAjouterVoiture() throws SQLException {
        // Créer un objet Voiture avec des valeurs de test
        Voiture voiture = new Voiture();
        voiture.setMarque("Toyota");
        voiture.setModele("Corolla");
        voiture.setFinition("Sport");
        voiture.setCarburant(Voiture.Carburant.valueOf("Essence"));
        voiture.setKm(50000);
        voiture.setAnnee(2015);
        voiture.setPrix(15000);

        // Appeler la méthode ajouterVoiture
        voitureDAO.ajouterVoiture(voiture);

        // Vérifier que le PreparedStatement a été configuré avec les bonnes valeurs
        verify(mockPreparedStatement).setString(1, "Toyota");
        verify(mockPreparedStatement).setString(2, "Corolla");
        verify(mockPreparedStatement).setString(3, "Sport");
        verify(mockPreparedStatement).setString(4, "Essence");
        verify(mockPreparedStatement).setInt(5, 50000);
        verify(mockPreparedStatement).setInt(6, 2015);
        verify(mockPreparedStatement).setInt(7, 15000);

        // Vérifier que la méthode executeUpdate a été appelée
        verify(mockPreparedStatement).executeUpdate();

        // Vérifier que la méthode deconnecter a été appelée
        verify(voitureDAO).deconnecter();
    }


    @Test
    public void testModifierVoiture() throws SQLException {
        // Créer un objet Voiture avec des valeurs de test
        Voiture voiture = new Voiture();
        voiture.setMarque("Toyota");
        voiture.setModele("Corolla");
        voiture.setFinition("Sport");
        voiture.setCarburant("Essence");
        voiture.setKm(60000);
        voiture.setAnnee(2018);
        voiture.setPrix(18000);


        /**
         * Méthode permettnt de tester la modification d'une voiture.
         */
        int id = 1;
        voitureDAO.modifierVoiture(id, voiture);

        // Vérifier que le PreparedStatement a été configuré avec les bonnes valeurs
        verify(mockPreparedStatement).setString(1, "Toyota");
        verify(mockPreparedStatement).setString(2, "Corolla");
        verify(mockPreparedStatement).setString(3, "Sport");
        verify(mockPreparedStatement).setString(4, "Essence");
        verify(mockPreparedStatement).setInt(5, 60000);
        verify(mockPreparedStatement).setInt(6, 2018);
        verify(mockPreparedStatement).setInt(7, 18000);
        verify(mockPreparedStatement).setInt(8, id);

        // Vérifier que la méthode executeUpdate a été appelée
        verify(mockPreparedStatement).executeUpdate();

        // Vérifier que la méthode deconnecter a été appelée
        verify(voitureDAO).deconnecter();
    }

    @Test
    public void testModifierVoitureSQLException() throws SQLException {
        // Simuler une SQLException lors de l'exécution de la requête
        when(mockPreparedStatement.executeUpdate()).thenThrow(new SQLException());

        //Créer un objet Voiture pour le test
        Voiture voiture = new Voiture();
        voiture.setMarque("Toyota");
        voiture.setModele("Corolla");
        voiture.setFinition("Sport");
        voiture.setCarburant(Voiture.Carburant.valueOf("Essence"));
        voiture.setKm(60000);
        voiture.setAnnee(2018);
        voiture.setPrix(18000);

        int idVoiture = 1;
        // Attendre une SQLException lorsqu'on essaie de modifier une voiture
        assertThrows(SQLException.class, () -> {
            voitureDAO.modifierVoiture(idVoiture, voiture);
        });

        // Vérifier que la méthode deconnecter a été appelée même en cas d'exception
        verify(voitureDAO).deconnecter();
    }






}