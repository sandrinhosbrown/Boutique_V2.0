/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.ListaPrendas;
import modelo.Prenda;

/**
 *
 * @author usu21
 */
public class PrendaJDBC {
    private Connection conexion;
    
    public ListaPrendas selectAllPrendas(){
        ListaPrendas listaPrendas = new ListaPrendas();
        conectar();
        if(conexion != null){
            try {
                String query = "select * from boutique.prenda";
                Statement st = conexion.createStatement();
                ResultSet rs = st.executeQuery(query);
                while (rs.next()) {
                    Prenda prenda = new Prenda();
                    prenda.setCodigo(rs.getString("codigo")); // o bien rs.getString(1)
                    prenda.setDescripcion(rs.getString("descripcion"));
                    prenda.setTalla(rs.getString("talla"));
                    prenda.setColor(rs.getString("color"));
                    prenda.setPrecioCoste(rs.getDouble("precioCoste"));
                    prenda.setPrecioVenta(rs.getDouble("precioVenta"));
                    prenda.setStock(rs.getInt("stock"));
                    listaPrendas.altaPrenda(prenda);
                }
                // cerramos recursos
                rs.close();
                st.close();
            } catch (SQLException ex) {
                System.out.println("ERROR EN LA CONSULTA "+ex.getMessage());
            } finally {
                desconectar();
            }
        }
        return listaPrendas;
    }
    //funcion que comprueba si un codigo de pelicula ya existe en la BBDD
    // la funcion debe ser publica pq la vamos a llamar desde fuera
    public boolean existePrenda(String codigo){
        conectar();
        if(conexion != null){
            String query = "select * from boutique.prenda where codigo='" + codigo + "'";
            //Creamos Statement y el ResultSet y lo envolvemos con un Try-Catch-Finally
            try {
                Statement st = conexion.createStatement();
                ResultSet rs = st.executeQuery(query);
                //declaramos un boolean existe y comprobamos que existe la prenda
                boolean existe= false;
                if(rs.next()){
                    return true;
                }
                // cerramos Statement y ResultSet
                rs.close();
                st.close();
            } catch (SQLException ex) {
//                Logger.getLogger(PrendaJDBC.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Error al consultar: "+ex.getMessage());
                return false;
            } finally {
                desconectar();
            }
        } else {
            return false;
            }
        return false;
    }
    //Funcion para insertar una prenda en la BBDD
    public boolean insertarPrenda(Prenda prenda){
        //abrimos la conexión
        conectar();
        if(conexion != null){
            try {
                String insert = "insert into boutique.prenda values (?,?,?,?,?,?,?)";
                PreparedStatement ps = conexion.prepareStatement(insert);
                ps.setString(1, prenda.getCodigo());
                ps.setString(2, prenda.getDescripcion());
                ps.setString(3, prenda.getTalla());
                ps.setString(4, prenda.getColor());
                ps.setDouble(5, prenda.getPrecioCoste());
                ps.setDouble(6, prenda.getPrecioVenta());
                ps.setInt(7, prenda.getStock());
                ps.executeUpdate();     //Ejecuta la consulta
                ps.close();             //Liberamos recursos
                return true;
            } catch (SQLException ex) {
//                Logger.getLogger(PrendaJDBC.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Error al insertar datos: "+ex.getMessage());
            } finally {
                desconectar();
            }
        } else {
            return false;
        }
        return false;
    }
    // Funcion para borrar una prenda de la BBDD
    public boolean borrarPrenda(Prenda prenda){
        //abrimos la conexión
        conectar();
        if(conexion != null){
            try {
                String delete = "delete from prenda where codigo=?";
                PreparedStatement ps = conexion.prepareStatement(delete);
                ps.setString(1, prenda.getCodigo());
//                ps.setString(2, prenda.getDescripcion());
//                ps.setString(3, prenda.getTalla());
//                ps.setString(4, prenda.getColor());
//                ps.setDouble(5, prenda.getPrecioCoste());
//                ps.setDouble(6, prenda.getPrecioVenta());
//                ps.setInt(7, prenda.getStock());
                ps.executeUpdate();     //Ejecuta la consulta
                ps.close();             //Liberamos recursos
                return true;
            } catch (SQLException ex) {
//                Logger.getLogger(PrendaJDBC.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Error al borrar los datos: "+ex.getMessage());
            } finally {
                desconectar();
            }
        } else {
            return false;
        }
        return false;
    }

// Funcion para establecer la conexion con el servidor
    private void conectar() {
        // url de la bbdd
        try {
            String url = "jdbc:mysql://localhost:3306/boutique";
            String user = "root";
            String password = "jeveris";
            conexion = DriverManager.getConnection(url, user, password); //me obligara a capturar la excepcion
        } catch (SQLException ex) {
            // TODO: OJO CUIDADO!!: de momento hacemos souts
            System.out.println("Error al conectar " + ex.getMessage());
            conexion = null;
        }
    }

    // Funcion para cerrar la conexión
    private void desconectar() {
        try {
            conexion.close();
        } catch (SQLException ex) {
            System.out.println("Error al desconectar " + ex.getMessage());
        }
    }
}