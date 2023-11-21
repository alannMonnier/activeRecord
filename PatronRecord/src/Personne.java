import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Personne {

    public int id;
    public String nom;
    public String prenom;

    public Personne(String n, String p){
        this.nom = n;
        this.prenom = p;
        this.id = -1;
    }

    public Personne findById(int p){
        Connection connect = DBConnection.getConnection();

        String req = "SELECT * FROM PERSONNE WHERE id = ?";

        try {
            PreparedStatement statement = connect.prepareStatement(req);

            statement.setInt(1, p);

            ResultSet rs = statement.executeQuery();

            Personne perso = new Personne(rs.getString("nom"), rs.getString("prenom"));
            perso.setId(p);

            statement.close();
            connect.close();

            return perso;
        } catch (SQLException e) {
            System.out.println("Problème SQL : " + e.getMessage());
            return null;
        }
    }

    public ArrayList<Personne> findByName(String n){
        Connection connect = DBConnection.getConnection();

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
            connect.close();

            return lisperso;

        } catch (SQLException e) {
            System.out.println("Problème SQL : " + e.getMessage());
            return null;
        }
    }

    public ArrayList<Personne> findAll(){

        Connection connect = DBConnection.getConnection();

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
            connect.close();

            return lisperso;

        } catch (SQLException e) {
            System.out.println("Problème SQL : " + e.getMessage());
            return null;
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


}
