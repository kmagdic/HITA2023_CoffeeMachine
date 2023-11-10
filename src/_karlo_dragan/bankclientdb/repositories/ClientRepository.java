package _karlo_dragan.bankclientdb.repositories;

import _karlo_dragan.bankclientdb.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientRepository {

    Connection conn;

    public ClientRepository(Connection conn) {
        this.conn = conn;
    }


    public void createTable() {

        try {
            String sqlCreateTable ="CREATE TABLE IF NOT EXISTS client (\n" +
                    "id integer PRIMARY KEY auto_increment, \n" +
                    "first_name text  NOT NULL,\n " +
                    "last_name text  NOT NULL,\n" +
                    "address text  NOT NULL,\n" +
                    "oib text  NOT NULL\n)";


            Statement st = conn.createStatement();
            st.execute(sqlCreateTable);

        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void insertClient(Client c){

        String insertSql = "INSERT INTO client (first_name, last_name, address, OIB) VALUES (?,?,?,?)";

        try {
            PreparedStatement ps = conn.prepareStatement(insertSql);
            ps.setString(1,c.getFirstName());
            ps.setString(2,c.getLastName());
            ps.setString(3,c.getAddress());
            ps.setString(4,c.getOib());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Client> getListOfALlClients() {
        String sqlPrint = "SELECT * FROM client";
        List<Client> resultList = new ArrayList<>();

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sqlPrint);

            while (rs.next()) {
                Client c = new Client();
                c.setId(rs.getInt("id"));
                c.setFirstName(rs.getString("first_name"));
                c.setLastName(rs.getString("last_name"));
                c.setAddress(rs.getString("address"));
                c.setOib(rs.getString("OIB"));

                resultList.add(c);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultList;
    }

    public void updateClient (Client c){
        String update = "update client set " +
                "first_name = ?, " +
                "last_name = ?, " +
                "address = ?, " +
                "oib = ? " +
                "where id = ? ";

        try {
            PreparedStatement ps = conn.prepareStatement(update);
           // ps.setInt(1,c.getId());
            ps.setString(1, c.getFirstName());
            ps.setString(2, c.getLastName());
            ps.setString(3, c.getAddress());
            ps.setString(4, c.getOib());
            ps.setInt(5, c.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(Client c) {
        String delete = "delete from client where oib = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(delete);
            ps.setString(1, c.getOib());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);


        }
    }
}



/*    ISPIS KLIJENATA DIREKTNO IZ BAZE -TO SE TAKO NE RADI - BAZA SE PRETVARA U LISTU I ONDA SE S LISTOM RADI

       public void printAllClients(){
        String sqlPrint = "SELECT * FROM client";

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sqlPrint);

            while (rs.next()) {
                System.out.println("First name:" + rs.getString("first_name"));
                System.out.println("Last name:" + rs.getString("last_name"));
                System.out.println("Address:" + rs.getString("address"));
                System.out.println("OIB:" + rs.getString("OIB"));
                System.out.println();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }*/
