import java.sql.*;
import java.util.ArrayList;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class Film {

    private String titre;
    private int id;
    private int id_rea;

    private static Connection connect = DBConnection.getConnection();

    public Film(String tit, Personne real){
        this.titre = tit;
        this.id = -1;
        this.id_rea = real.getId();
    }

    private Film(String tit, int i, int real){
        this.titre = tit;
        this.id = i;
        this.id_rea = real;
    }

    public void setReal(int p) throws SQLException {

        String req = "SELECT * FROM PERSONNE WHERE id = ?";

        PreparedStatement statement = connect.prepareStatement(req);

        statement.setInt(1, p);

        ResultSet rs = statement.executeQuery();

        if (rs.next()) {
            this.id_rea = p;
            System.out.println("Réalisateur actualisé avec succès");
        } else {
            System.out.println("Aucun Réalisateur trouvé avec l'ID spécifié");
        }
    }

    public Personne getRealisateur(){
        return Personne.findById(this.id_rea);
    }

    public static Film findById(int p){

        String req = "SELECT * FROM FILM WHERE id = ?";

        try {
            PreparedStatement statement = connect.prepareStatement(req);

            statement.setInt(1, p);

            ResultSet rs = statement.executeQuery();
            rs.next();

            Film film = new Film(rs.getString("titre"), rs.getInt("id"), rs.getInt("id_rea"));

            statement.close();

            return film;
        } catch (SQLException e) {
            System.out.println("Problème SQL : " + e.getMessage());
            return null;
        }
    }

    public static ArrayList<Film> findAll(){

        String req = "SELECT * FROM FILM";

        ArrayList<Film> lisfilm = new ArrayList<>();

        try {

            PreparedStatement statement = connect.prepareStatement(req);

            ResultSet rs = statement.executeQuery();

            while(rs.next()) {
                Film film = new Film(rs.getString("titre"), rs.getInt("id"), rs.getInt("id_rea"));
                lisfilm.add(film);
            }

            statement.close();

            return lisfilm;

        } catch (SQLException e) {
            System.out.println("Problème SQL : " + e.getMessage());
            return null;
        }
    }

    public static ArrayList<Film> findByTitre(String n){

        String req = "SELECT * FROM FILM WHERE TITRE = ?";

        ArrayList<Film> lisfilm = new ArrayList<>();

        try {
            PreparedStatement statement = connect.prepareStatement(req);

            statement.setString(1, n);

            ResultSet rs = statement.executeQuery();

            while(rs.next()) {
                Film film = new Film(rs.getString("titre"), rs.getInt("id"), rs.getInt("id_rea"));
                lisfilm.add(film);
            }

            statement.close();

            return lisfilm;

        } catch (SQLException e) {
            System.out.println("Problème SQL : " + e.getMessage());
            return null;
        }
    }

    public void insertFilm() throws SQLException {

        String req = "INSERT INTO FILM (titre, id_rea) VALUES (? , ?)";

        if (this.id == -1) {

            PreparedStatement statement = connect.prepareStatement(req, RETURN_GENERATED_KEYS);

            statement.setString(1, this.titre);
            statement.setInt(2, this.id_rea);

            int nbligne = statement.executeUpdate();

            if (nbligne > 0) {
                System.out.println("Film inséré avec succès");
                ResultSet rs = statement.getGeneratedKeys();
                rs.next();
                this.id = rs.getInt(1);

            } else {
                System.out.println("Erreur lors de l'insertion du Film");
            }
        } else {
            System.out.println("Ce Film est déjà dans la table");
        }
    }

    public void deletePersonne() throws SQLException {

        String req = "DELETE FROM FILM WHERE id = ?";

        if (this.id == -1) {
            System.out.println("Ce Film n'existe pas dans la table");
        } else {

            PreparedStatement statement = connect.prepareStatement(req);

            statement.setInt(1, this.id);

            int nbligne = statement.executeUpdate();

            if (nbligne > 0) {
                System.out.println("Film supprimé avec succès");
            } else {
                System.out.println("Aucun Film trouvé avec l'ID spécifié");
            }
        }
    }

    public void save() throws SQLException {

        if(this.id == -1){
            insertFilm();
        }else{
            String req = "UPDATE FILM (titre, id_rea) VALUES (?, ?) WHERE id = ?";

            PreparedStatement statement = connect.prepareStatement(req);

            statement.setString(1, this.titre);
            statement.setInt(2, this.id_rea);
            statement.setInt(3, this.id);

            int nbligne = statement.executeUpdate();

            if (nbligne > 0) {
                System.out.println("Film actualisé avec succès");
            } else {
                System.out.println("Aucun Film trouvé avec l'ID spécifié");
            }
        }
    }


    public static void createTable(){


        String req = "CREATE TABLE `Film` ("+
                      "`id` int(11) NOT NULL,"+
                      "`titre` varchar(40) NOT NULL,"+
                      "`id_rea` int(11) DEFAULT NULL"+
                      ")";
        String req2 = "ALTER TABLE `Film` ADD PRIMARY KEY (`id`) ";
        String req3 = "ALTER TABLE `Film` MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=0 ";

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
            System.out.println("Problème SQL lors de la création de la table Film : " + e.getMessage());
        }
    }


    public static void deleteTable(){
        String req = "drop table `Film`";

        try {
            PreparedStatement statement = connect.prepareStatement(req);
            statement.executeUpdate();

            statement.close();

        } catch (SQLException e) {
            System.out.println("Problème SQL lors de la suppression de la table : " + e.getMessage());
        }
    }

    public String toString(){
        return "ID :"+this.id+" Titre : "+this.titre+" ID Réalisateur: "+this.id_rea;
    }

    public String getTitre(){
        return this.titre;
    }

    public void setTitre(String n){
        this.titre = n;
    }

    public void setId(int i){
        this.id = i;
    }

    public int getId(){
        return this.id;
    }
}
