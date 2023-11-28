import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TestPersonne {


    @BeforeEach
    public void init(){
        // Création de la table Personne
        //Personne.deleteTable();
        Personne.createTable();
        Personne p;
        p=new Personne("spielberg", "steven");
        p.save();
        p=new Personne("scott", "ridley");
        p.save();
        p=new Personne("scott", "ridley");
        p.save();
    }

    @AfterEach
    public void after(){
        Personne.deleteTable();
    }

    @Test
    public void testConstructeurOK() {
        Personne p = new Personne("NomTest", "PrenomTest");
        assertEquals(p.getId(), -1);
    }

    @Test
    public void testFindByNameOK() {
        ArrayList<Personne> personnes = Personne.findByName("spielberg");
        assertEquals(1, personnes.size());
        Personne pTest = personnes.get(0);
        assertEquals(pTest.getNom(), "spielberg");
        assertEquals(pTest.getPrenom(), "steven");
    }

    @Test
    public void testFindByName2PossibleOK() {
        ArrayList<Personne> personnes = Personne.findByName("scott");

        assertEquals(2, personnes.size());
        Personne p = personnes.get(0);
        assertEquals(p.getNom(), "scott");
        p = personnes.get(1);
        assertEquals(p.getNom(), "scott");
    }

    @Test
    public void testFindByNameNonExistant()  {
        ArrayList<Personne> p = Personne.findByName("nom");
        assertEquals(0, p.size());
    }

    @Test
    public void testFindAll()  {
        ArrayList<Personne> personnes = Personne.findAll();
        assertEquals(3, personnes.size());
    }

    @Test
    public void testSaveNouveau()  {
        Personne p = new Personne("NomTest", "PrenomTest");
        p.save();


        assertEquals(4, p.getId());
        Personne pers = Personne.findById(4);
        assertEquals(pers.getNom(), "NomTest");
        assertEquals(pers.getPrenom(), "PrenomTest");
        assertEquals(pers.getId(), 4);
    }

    @Test
    public void testSaveExistant()  {
        // modifie la personne
        Personne p = Personne.findById(2);
        p.setNom("spielberg");
        p.save();
        assertEquals(2, p.getId(),"id ne devrait pas bouger");

        // on la recherche
        ArrayList<Personne> pers = Personne.findByName("spielberg");
        assertEquals(1, pers.size());
        Personne p2 = pers.get(0);
        assertEquals("spielberg", p2.getNom());
        assertEquals("steven", p2.getPrenom());
        assertEquals(p2.getId(), 1,"ide devrait etre le meme");
    }


    @Test
    public void testDelete() throws SQLException {
        Personne p = Personne.findById(2);
        p.deletePersonne();
        Personne p2 = Personne.findById(2);

        assertEquals( -1, p.getId(),"id devrait etre revenue a -1");
        assertEquals( null, p2,"le supprime n'existe plus");
    }

}