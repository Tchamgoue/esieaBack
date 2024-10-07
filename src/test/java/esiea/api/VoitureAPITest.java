package esiea.api;

import esiea.dao.VoitureDAO;
import esiea.metier.Voiture;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class VoitureAPITest {
    @Mock
    private VoitureDAO vDao;
    @InjectMocks
    private VoitureResource voitureResource;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testAjouterVoitureSuccess() throws Exception {

        JSONObject voitureJson = new JSONObject();
        voitureJson.put("marque", "Toyota");
        voitureJson.put("modele", "Corolla");
        voitureJson.put("annee", 2020);

        Voiture voiture = new Voiture("Toyota", "Corolla", 2020);

        when(vDao.ajouterVoiture(any(Voiture.class))).thenReturn(true);
        when(voiture.check()).thenReturn(true);

        // Appel de la méthode à tester
        String response = vDao.ajouterVoiture(voitureJson.toString());

        // On s'attend à ce que le retour soit "succes": true
        JSONObject expectedResponse = new JSONObject();
        expectedResponse.put("succes", true);
        JSONAssert.assertEquals(expectedResponse.toString(), response, true);
    }

    @Test
    public void testAjouterVoitureCheckFail() throws Exception {
        // Préparation des données
        JSONObject voitureJson = new JSONObject();
        voitureJson.put("marque", "Toyota");
        voitureJson.put("modele", "Corolla");
        voitureJson.put("annee", 2020);

        Voiture voiture = new Voiture("Toyota", "Corolla", 2020);

        // Simuler que le check échoue
        when(voiture.check()).thenReturn(false);

        // Appel de la méthode à tester
        String response = voitureResource.ajouterVoiture(voitureJson.toString());

        // On s'attend à ce que le retour soit "succes": false
        JSONObject expectedResponse = new JSONObject();
        expectedResponse.put("succes", false);
        JSONAssert.assertEquals(expectedResponse.toString(), response, true);

        // Vérification que l'ajout dans le DAO n'a pas été appelé
        verify(vDao, never()).ajouterVoiture(any(Voiture.class));
    }


    @Test
    public void testAjouterVoitureSQLException() throws Exception {
        // Préparation des données
        JSONObject voitureJson = new JSONObject();
        voitureJson.put("marque", "Toyota");
        voitureJson.put("modele", "Corolla");
        voitureJson.put("annee", 2020);

        Voiture voiture = new Voiture("Toyota", "Corolla", 2020);

        // Simuler que le check passe, mais que la DAO lève une SQLException
        when(voiture.check()).thenReturn(true);
        doThrow(new SQLException()).when(vDao).ajouterVoiture(any(Voiture.class));

        // Appel de la méthode à tester
        String response = voitureResource.ajouterVoiture(voitureJson.toString());

        // On s'attend à ce que le retour soit "succes": false
        JSONObject expectedResponse = new JSONObject();
        expectedResponse.put("succes", false);
        JSONAssert.assertEquals(expectedResponse.toString(), response, true);
    }




}