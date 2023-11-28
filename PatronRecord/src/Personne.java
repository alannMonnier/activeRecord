import java.sql.*;
import java.util.ArrayList;

import static java.sql.Statement.RETURN_GENERATED_KEYS;


public class Personne {

    public int id;
    public String nom;
    public String prenom;

    private static Connection connect=DBConnection.getConnection();

    public Personne(String n, String p){
        this.nom = n;
        this.prenom = p;
        this.id = -1;
    }

    public static Personne findById(int p){
        //Connection connect = DBConnection.getConnection();

        String req = "SELECT * FROM PERSONNE WHERE id = ?";

        try {
            PreparedStatement statement = connect.prepareStatement(req);

            statement.setInt(1, p);

            ResultSet rs = statement.executeQuery();

            Personne perso = new Personne(rs.getString("nom"), rs.getString("prenom"));
            perso.setId(p);

            statement.close();


            return perso;
        } catch (SQLException e) {
            System.out.println("Problème SQL : " + e.getMessage());
            return null;
        }
    }

    public static ArrayList<Personne> findByName(String n){
        //Connection connect = DBConnection.getConnection();

        String req = "SELECT * FROM PERSONNE WHERE nom = ?";

        ArrayList<Personne> lisperso = new ArrayList<>();

        try {
            PreparedStatement statement = connect.prepareStatement(req);

            statement.setString(1, n);

            ResultSet rs = statement.executeQuery();

            while(rs.next()) {
                Personne perso = new Personne(rs.getString("nom"), rs.getString("prenom"));
                perso.setId(rs.getInt("id"));
                lisperso.add(perso);
            }

            statement.close();


            return lisperso;

        } catch (SQLException e) {
            System.out.println("Problème SQL : " + e.getMessage());
            return null;
        }
    }

    public static ArrayList<Personne> findAll(){

        //Connection connect = DBConnection.getConnection();

        String req = "SELECT * FROM PERSONNE";

        ArrayList<Personne> lisperso = new ArrayList<>();

        try {
            PreparedStatement statement = connect.prepareStatement(req);

            ResultSet rs = statement.executeQuery();

            while(rs.next()) {
                Personne perso = new Personne(rs.getString("nom"), rs.getString("prenom"));
                perso.setId(rs.getInt("id"));
                lisperso.add(perso);
            }

            statement.close();


            return lisperso;

        } catch (SQLException e) {
            System.out.println("Problème SQL : " + e.getMessage());
            return null;
        }
    }

    public static void createTable(){

        String req = "CREATE TABLE `Personne` (" +
                "  `id` int(11) NOT NULL, " +
                "  `nom` varchar(40) NOT NULL," +
                "  `prenom` varchar(40) NOT NULL" +
                ")";
        String req2 = "ALTER TABLE `Personne` ADD PRIMARY KEY (`id`) ";
        String req3 = "ALTER TABLE `Personne` MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5 ";

        try {
            PreparedStatement statement = connect.prepareStatement(req);
            statement.executeUpdate();
            statement.close();

            statement = connect.prepareStatement(req2);
            statement.executeUpdate();
            statement.close();

            statement = connect.prepareStatement(req3);
            statement.executeUpdate();
            statement.close();

        } catch (SQLException e) {
            System.out.println("Problème SQL lors de la création de la table personne : " + e.getMessage());
        }
    }


    public static void deleteTable(){
        String req = "drop table `Personne`";

        try {
            PreparedStatement statement = connect.prepareStatement(req);
            statement.executeUpdate();

            statement.close();

        } catch (SQLException e) {
            System.out.println("Problème SQL lors de la suppression de la table : " + e.getMessage());
        }
    }

    /**
    public void insertPersonne() throws SQLException {

        String req = "INSERT INTO PERSONNE (nom, prenom) VALUES (? , ?)";

        if (this.id == -1) {

            PreparedStatement statement = connect.prepareStatement(req, RETURN_GENERATED_KEYS);

            statement.setString(1, this.nom);
            statement.setString(2, this.prenom);

            int nbligne = statement.executeUpdate();

            if (nbligne > 0) {
                System.out.println("Personne insérée avec succès");
                ResultSet rs = statement.getGeneratedKeys();
                this.id = rs.getInt(1);
            } else {
                System.out.println("Erreur lors de l'insertion de la personne");
            }
        } else {
            System.out.println("Cette Personne est déjà dans la table");
        }
    }*/

    public void insertPersonne() throws SQLException {

        String req = "INSERT INTO PERSONNE (nom, prenom) VALUES (? , ?)";

        if (this.id == -1) {

            PreparedStatement statement = connect.prepareStatement(req);

                statement.setString(1, this.nom);
                statement.setString(2, this.prenom);

                int nbligne = statement.executeUpdate();

                if (nbligne > 0) {
                    System.out.println("Personne insérée avec succès");
                } else {
                    System.out.println("Erreur lors de l'insertion de la personne");
                }

        } else {
            System.out.println("Cette Personne est déjà dans la table");
        }
    }

    public void deletePersonne() throws SQLException {
        String req = "DELETE FROM PERSONNE WHERE id = ?";

        if (this.id == -1) {
            System.out.println("Cette personne n'existe pas dans la table");
        } else {

            PreparedStatement statement = connect.prepareStatement(req);

            statement.setInt(1, this.id);

            int nbligne = statement.executeUpdate();

            if (nbligne > 0) {
                System.out.println("Personne supprimée avec succès");
            } else {
                System.out.println("Aucune personne trouvée avec l'ID spécifié");
            }
        }
    }


    public void save() {
        if(this.id == -1){
            try {
                insertPersonne();
            }
            catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        else{
            String req = "UPDATE FROM PERSONNE (nom, prenom) VALUES (?, ?) WHERE id = ?";
            try{
                PreparedStatement statement = connect.prepareStatement(req);

                statement.setString(1, this.nom);
                statement.setString(2, this.prenom);
                statement.setInt(3, this.id);

                int nbligne = statement.executeUpdate();

                if (nbligne > 0) {
                    System.out.println("Personne actualisée avec succès");
                } else {
                    System.out.println("Aucune personne trouvée avec l'ID spécifié");
                }
            }
            catch (SQLException e){
                e.printStackTrace();
            }

        }
    }

    public String toString(){
        return "ID :"+this.id+" Nom : "+this.nom+" Prenom: "+this.prenom;
    }

    public String getNom(){
        return this.nom;
    }

    public void setNom(String n){
        this.nom = n;
    }

    public String getPrenom(){
        return this.prenom;
    }

    public void setPrenom(String p){
        this.prenom = p;
    }

    public void setId(int i){
        this.id = i;
    }

    public int getId() {
        return id;
    }
}
