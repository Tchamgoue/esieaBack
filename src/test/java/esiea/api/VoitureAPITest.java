package esiea.api;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import esiea.dao.VoitureDAO;
import esiea.metier.Voiture;
import esiea.dao.ReponseVoiture;
import esiea.metier.Voiture.Carburant;

import java.sql.SQLException;

public class VoitureAPITest {

    @Mock
    private VoitureDAO voitureDAO; // Crée un mock de VoitureDAO

    @InjectMocks
    private VoitureAPI voitureAPI; // Injecte le mock dans VoitureAPI

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Initialise les mocks
    }

    @Test
    public void testGetVoituresJson_All() throws Exception {
        // Préparer les données de test
        Voiture voiture1 = new Voiture();
        voiture1.setId(1);
        voiture1.setMarque("Renault");
        voiture1.setModele("Clio");
        voiture1.setFinition("Initiale"); // Ajoutez la finition
        voiture1.setCarburant(Carburant.ESSENCE); // Utiliser l'énumération Carburant
        voiture1.setKm(50000); // Ajoutez le kilométrage
        voiture1.setAnnee(2019); // Ajoutez l'année
        voiture1.setPrix(15000);

        Voiture voiture2 = new Voiture();
        voiture2.setId(2);
        voiture2.setMarque("Peugeot");
        voiture2.setModele("208");
        voiture2.setFinition("Allure"); // Ajoutez la finition
        voiture2.setCarburant(Carburant.DIESEL); // Utiliser l'énumération Carburant
        voiture2.setKm(30000); // Ajoutez le kilométrage
        voiture2.setAnnee(2020); // Ajoutez l'année
        voiture2.setPrix(18000);

        ReponseVoiture reponseVoiture = new ReponseVoiture();
        reponseVoiture.setData(new Voiture[]{voiture1, voiture2});
        reponseVoiture.setVolume(2);

        // Configurer le comportement du mock
        when(voitureDAO.getVoitures(null, -1, -1)).thenReturn(reponseVoiture);

        // Appeler la méthode à tester
        String jsonResponse = voitureAPI.getVoituresJson("all");

        // Vérifier les résultats
        JSONObject response = new JSONObject(jsonResponse);
        assertTrue(response.has("voitures"));
        assertEquals(2, response.getJSONArray("voitures").length());
        assertEquals("Renault", response.getJSONArray("voitures").getJSONObject(0).getString("marque"));
        assertEquals("Peugeot", response.getJSONArray("voitures").getJSONObject(1).getString("marque"));
    }

    @Test
    public void testGetVoituresJson_WithId() throws Exception {
        // Préparer les données de test pour un ID spécifique
        Voiture voiture = new Voiture();
        voiture.setId(2);
        voiture.setMarque("Peugeot");
        voiture.setModele("208");
        voiture.setPrix(18000);

        ReponseVoiture reponseVoiture = new ReponseVoiture();
        reponseVoiture.setData(new Voiture[]{voiture});
        reponseVoiture.setVolume(1);

        // Configurer le comportement du mock
        when(voitureDAO.rechercherVoitures("2", -1, -1)).thenReturn(reponseVoiture);

        // Appeler la méthode à tester
        String jsonResponse = voitureAPI.getVoituresJson("2");

        // Vérifier les résultats
        JSONObject response = new JSONObject(jsonResponse);
        assertTrue(response.has("voiture"));
        assertEquals("Peugeot", response.getJSONObject("voiture").getString("marque"));
    }

    @Test
    public void testGetVoituresJson_WithParam() throws Exception {
        // Préparer les données de test
        Voiture voiture = new Voiture();
        voiture.setId(1);
        voiture.setMarque("Renault");
        voiture.setModele("Clio");
        voiture.setFinition("Initiale");
        voiture.setCarburant(Carburant.ESSENCE);
        voiture.setKm(50000);
        voiture.setAnnee(2019);
        voiture.setPrix(15000);

        ReponseVoiture reponseVoiture = new ReponseVoiture();
        reponseVoiture.setData(new Voiture[]{voiture});
        reponseVoiture.setVolume(1);

        // Configurer le comportement du mock pour un ID existant
        when(voitureDAO.rechercherVoitures("1", -1, -1)).thenReturn(reponseVoiture);

        // Appeler la méthode à tester
        String jsonResponse = voitureAPI.getVoituresJson("1", "-1", "-1");

        // Vérifier les résultats
        JSONObject response = new JSONObject(jsonResponse);
        assertTrue(response.has("voiture"));
        assertEquals("Renault", response.getJSONObject("voiture").getString("marque"));
        assertEquals(1, response.getInt("volume"));
    }

    @Test
    public void testGetVoituresJson_WithInvalidParam() throws Exception {
        // Préparer les données de test
        Voiture voiture1 = new Voiture();
        voiture1.setId(1);
        voiture1.setMarque("Renault");
        voiture1.setModele("Clio");
        voiture1.setFinition("Initiale");
        voiture1.setCarburant(Carburant.ESSENCE);
        voiture1.setKm(50000);
        voiture1.setAnnee(2019);
        voiture1.setPrix(15000);

        ReponseVoiture reponseVoiture = new ReponseVoiture();
        reponseVoiture.setData(new Voiture[]{voiture1});
        reponseVoiture.setVolume(1);

        // Configurer le comportement du mock pour un paramètre non entier
        when(voitureDAO.rechercherVoitures("invalid", -1, -1)).thenReturn(reponseVoiture);

        // Appeler la méthode à tester
        String jsonResponse = voitureAPI.getVoituresJson("invalid", "-1", "-1");

        // Vérifier les résultats
        JSONObject response = new JSONObject(jsonResponse);
        assertTrue(response.has("voitures"));
        assertEquals(1, response.getJSONArray("voitures").length());
        assertEquals("Renault", response.getJSONArray("voitures").getJSONObject(0).getString("marque"));
        assertEquals(1, response.getInt("volume"));
    }

    @Test
    public void testAjouterVoiture_Succes() throws Exception {
        // Préparer les données d'entrée
        String jsonInput = "{ \"marque\": \"Renault\", \"modele\": \"Clio\", \"carburant\": \"ESSENCE\", \"prix\": 15000 }";

        Voiture voiture = new Voiture();
        voiture.setMarque("Renault");
        voiture.setModele("Clio");
        voiture.setCarburant(Carburant.ESSENCE);
        voiture.setPrix(15000);

        // Configurer le comportement du mock
        doNothing().when(voitureDAO).ajouterVoiture(any(Voiture.class));

        // Appeler la méthode à tester
        String jsonResponse = voitureAPI.ajouterVoiture(jsonInput);

        // Vérifier les résultats
        JSONObject response = new JSONObject(jsonResponse);
        assertTrue(response.getBoolean("succes"));
    }

    @Test
    public void testAjouterVoiture_Echec() throws Exception {
        // Préparer les données d'entrée
        String jsonInput = "{ \"marque\": \"Renault\", \"modele\": \"Clio\", \"carburant\": \"ESSENCE\", \"prix\": 15000 }";

        // Configurer le comportement du mock
        doThrow(new SQLException()).when(voitureDAO).ajouterVoiture(any(Voiture.class));

        // Appeler la méthode à tester
        String jsonResponse = voitureAPI.ajouterVoiture(jsonInput);

        // Vérifier les résultats
        JSONObject response = new JSONObject(jsonResponse);
        assertFalse(response.getBoolean("succes"));
    }

    @Test
    public void testSupprimerVoiture_Succes() throws Exception {
        // Configurer le comportement du mock
        doNothing().when(voitureDAO).supprimerVoiture("1");

        // Appeler la méthode à tester
        String jsonResponse = voitureAPI.supprimerVoiture("1");

        // Vérifier les résultats
        JSONObject response = new JSONObject(jsonResponse);
        assertTrue(response.getBoolean("succes"));
    }


}
