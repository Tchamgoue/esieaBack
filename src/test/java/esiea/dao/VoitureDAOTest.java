package esiea.dao;

import esiea.dao.ReponseVoiture;
import esiea.dao.VoitureDAO;
import esiea.metier.Voiture;
import esiea.metier.Voiture.Carburant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockitoAnnotations;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class VoitureDAOTest {

    private VoitureDAO voitureDAO;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    @BeforeEach
    public void setUp() throws SQLException {
        voitureDAO = new VoitureDAO();
        connection = mock(Connection.class);
        preparedStatement = mock(PreparedStatement.class);
        resultSet = mock(ResultSet.class);

        // Mock le comportement de getConnexion
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
        voitureDAO.connection = connection;
    }
/*
    @BeforeEach
    public void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);

        // Simuler le comportement de la connexion
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        // Injecter la connexion simulée dans le DAO
        voitureDAO.connection = connection;

    } */

    @Test
    public void testAjouterVoiture() throws SQLException {
        Voiture voiture = new Voiture();
        voiture.setMarque("Renault");
        voiture.setModele("Clio");
        voiture.setFinition("Initiale");
        voiture.setCarburant(Carburant.ESSENCE);
        voiture.setKm(50000);
        voiture.setAnnee(2019);
        voiture.setPrix(15000);

        // Appeler la méthode à tester
        voitureDAO.ajouterVoiture(voiture);

        // Vérifier que la méthode executeUpdate a été appelée
        verify(preparedStatement).executeUpdate();
    }

    @Test
    public void testModifierVoiture() throws SQLException {
        Voiture nouvelleVoiture = new Voiture();
        nouvelleVoiture.setMarque("Peugeot");
        nouvelleVoiture.setModele("208");
        nouvelleVoiture.setFinition("Active");
        nouvelleVoiture.setCarburant(Carburant.DIESEL);
        nouvelleVoiture.setKm(30000);
        nouvelleVoiture.setAnnee(2020);
        nouvelleVoiture.setPrix(20000);

        // Appeler la méthode à tester
        voitureDAO.modifierVoiture(1, nouvelleVoiture);

        // Vérifier que la méthode executeUpdate a été appelée
        verify(preparedStatement).executeUpdate();
    }

    @Test
    public void testSupprimerVoiture() throws SQLException {
        // Appeler la méthode à tester
        voitureDAO.supprimerVoiture("1");

        // Vérifier que la méthode executeUpdate a été appelée
        verify(preparedStatement).executeUpdate();
    }

    @Test
    public void testRechercherVoitures() throws SQLException {
        // Configuration du mock pour le résultat
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("marque")).thenReturn("Renault");
        when(resultSet.getString("modele")).thenReturn("Clio");
        when(resultSet.getString("finition")).thenReturn("Initiale");
        when(resultSet.getString("carburant")).thenReturn("ESSENCE");
        when(resultSet.getInt("km")).thenReturn(50000);
        when(resultSet.getInt("annee")).thenReturn(2019);
        when(resultSet.getInt("prix")).thenReturn(15000);

        // Simuler le comportement de getVoitures
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        // Appeler la méthode à tester
        ReponseVoiture response = voitureDAO.rechercherVoitures("1", 0, 10);

        // Vérifier les résultats
        assertNotNull(response);
        assertEquals(1, response.getData().length);
        assertEquals("Renault", response.getData()[0].getMarque());
    }

}
