/*
 * PrendaJDBC: Clase donde realizaremos todas las operaciones a la BBDD
 */
package dao;

import exception.miExcepcion;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import modelo.ListaPrendas;
import modelo.Prenda;

/**
 *
 * @author Sandro Gamarra
 */
public class PrendaJDBC {
    private Connection conexion;
    
    public ListaPrendas selectAllPrendas() throws miExcepcion{
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
                throw new miExcepcion("Error al consultar los datos: "+ex.getLocalizedMessage());
            } finally {
                desconectar();
            }
        }
        return listaPrendas;
    }
    //funcion que comprueba si un codigo de prenda ya existe en la BBDD
    // la funcion debe ser publica pq la vamos a llamar desde fuera
    public boolean existePrenda(String codigo) throws miExcepcion{
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
                throw new miExcepcion("Error al consultar los datos: "+ex.getLocalizedMessage());
            } finally {
                desconectar();
            }
        } else {
            return false;
            }
        return false;
    }
    //Funcion para insertar una prenda en la BBDD
    public boolean insertarPrenda(Prenda prenda) throws miExcepcion{
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
                throw new miExcepcion("Error al insertar los datos: "+ex.getLocalizedMessage());
            } finally {
                desconectar();
            }
        } else {
            return false;
        }
    }
    // TODO: Funcion para borrar una prenda de la BBDD
    public boolean borrarPrenda(Prenda prenda) throws miExcepcion{
        //abrimos la conexión
        conectar();
        if(conexion != null){
            try {
                String delete = "delete from prenda where codigo=?;";
                PreparedStatement ps = conexion.prepareStatement(delete);
                ps.setString(1, prenda.getCodigo());
                ps.executeUpdate();     
                ps.close();             
                return true;
            } catch (SQLException ex) {
                throw new miExcepcion("Error al borrar los datos: "+ex.getLocalizedMessage());
            } finally {
                desconectar();
            }
        } else {
            return false;
        }
    }
    
    // Función para modificar el stock de una prenda de la BBDD
    public boolean actualizarPrenda(Prenda prenda) throws miExcepcion{
        conectar();
        if(conexion != null){
            try {
                String update = "update prenda set stock = " + prenda.getStock() +
                        " where codigo = '" + prenda.getCodigo() + "'";
                Statement st = conexion.createStatement();
                st.executeUpdate(update);     
                st.close();                   
                return true;
            } catch (SQLException ex) {
                throw new miExcepcion("Error al insertar los datos: "+ex.getLocalizedMessage());
            } finally {
                desconectar();
            }
        } else {
            return false;
        }
    }

    //valorTotal: Precio Total Venta * Stock
    public double valorTotal() throws miExcepcion {
        double total = 0;
        conectar();
        if (conexion != null) {
            try {
                String query = "select sum(precioventa*stock) from prenda";
                Statement st = conexion.createStatement();
                ResultSet rs = st.executeQuery(query);
                if (rs.next()){
                total = rs.getInt(1);
                }
                rs.close();
                st.close();
            } catch (SQLException ex) {
                System.out.println("Error en la consulta " + ex.getMessage());
            } finally {
                desconectar();
            }
        }
        return total;
    }
    
    // Función que hace una consulta y devuelve una lista de las prendas de un color
    public ListaPrendas prendaColor(String color) throws miExcepcion{
        ListaPrendas listaPrendas = new ListaPrendas();
        conectar();
        if (conexion != null){
            try {
                String query = "select * from prenda where color='" + color + "'";
                Statement st = conexion.createStatement();
                ResultSet rs = st.executeQuery(query);
                while (rs.next()){
                Prenda prenda = new Prenda();
                prenda.setCodigo(rs.getString("codigo"));
                prenda.setDescripcion(rs.getString("descripcion"));
                prenda.setTalla(rs.getString("talla"));
                prenda.setColor(rs.getString("color"));
                prenda.setPrecioCoste(rs.getDouble("precioCoste"));
                prenda.setPrecioVenta(rs.getDouble("precioVenta"));
                prenda.setStock(rs.getInt("stock"));
                listaPrendas.altaPrenda(prenda);
                }
                rs.close();
                st.close();
                
            }catch (SQLException ex){
                System.out.println("Error en la consulta " + ex.getMessage());
            }finally{
                desconectar();
            }
        }
       return listaPrendas; 
    } 
    
    public ArrayList<String> colorDisponible() throws miExcepcion{
        ArrayList<String> colores = new ArrayList<>();
        conectar();
        if(conexion != null){
            try{
                String query = "select distinct color from prenda";
                Statement st = conexion.createStatement();
                ResultSet rs = st.executeQuery(query);
                while (rs.next()){
                    colores.add(rs.getString("color"));
                }
                rs.close();
                st.close();
                }catch (SQLException ex){
                    System.out.println("Error en la consulta " + ex.getMessage());
            } finally {
                desconectar();
            }
        }
        return colores;
    }
    
    //Funcion que nos devuelve el total de las prendas disponibles
    public int totalPrendas() throws miExcepcion {
        int total = 0;
        conectar();
        if (conexion != null) {
            try {
                String query = "select count(*) from prenda";
                Statement st = conexion.createStatement();
                ResultSet rs = st.executeQuery(query);
                if (rs.next()){
                total = rs.getInt(1);
                }
                rs.close();
                st.close();
            } catch (SQLException ex) {
                System.out.println("Error en la consulta " + ex.getMessage());
            } finally {
                desconectar();
            }
        }

        return total;
    }
    
    
// Funcion para establecer la conexion con el servidor
    private void conectar() throws miExcepcion {
        // url de la bbdd
        try {
            String url = "jdbc:mysql://localhost:3306/boutique";
            String user = "root";
            String password = "jeveris";
            conexion = DriverManager.getConnection(url, user, password); //me obligara a capturar la excepcion
        } catch (SQLException ex) {
                throw new miExcepcion("Error al conectar: "+ex.getLocalizedMessage());
        }
    }

    // Funcion para cerrar la conexión
    private void desconectar() throws miExcepcion {
        try {
            conexion.close();
        } catch (SQLException ex) {
                throw new miExcepcion("Error al conectar: "+ex.getLocalizedMessage());
        }
    }
}