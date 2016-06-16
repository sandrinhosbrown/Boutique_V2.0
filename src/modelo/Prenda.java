/*
 *
 */
package modelo;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 *
 * @author Sandro Gamarra
 */
public class Prenda { //Declaramos las variables y hacemos BOUND
        // pq son realmente las entidades de nuestro programa
        // y es dnd se van a guardar los datos y x ello deben avisar a la vista
        // que han cambiado su estado, para q la vista se refresque
        private String codigo;
        private String descripcion;
        private String talla;
        private String color;
        private double precioCoste;
        private double precioVenta;
        private int stock;

        
        public Prenda() { //Inicializamos los Strings en un constructor vacío
        codigo = descripcion = talla = color = "";
        precioCoste = precioVenta = stock = 0;
        }
         
    public static final String PROP_STOCK = "stock";

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        int oldStock = this.stock;
        this.stock = stock;
        propertyChangeSupport.firePropertyChange(PROP_STOCK, oldStock, stock);
    }


    public static final String PROP_PRECIOVENTA = "precioVenta";

    public double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(double precioVenta) {
        double oldPrecioVenta = this.precioVenta;
        this.precioVenta = precioVenta;
        propertyChangeSupport.firePropertyChange(PROP_PRECIOVENTA, oldPrecioVenta, precioVenta);
    }


    public static final String PROP_PRECIOCOSTE = "precioCoste";

    public double getPrecioCoste() {
        return precioCoste;
    }

    public void setPrecioCoste(double precioCoste) {
        double oldPrecioCoste = this.precioCoste;
        this.precioCoste = precioCoste;
        propertyChangeSupport.firePropertyChange(PROP_PRECIOCOSTE, oldPrecioCoste, precioCoste);
    }

        
    public static final String PROP_COLOR = "color";

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        String oldColor = this.color;
        this.color = color;
        propertyChangeSupport.firePropertyChange(PROP_COLOR, oldColor, color);
    }


    public static final String PROP_TALLA = "talla";

    public String getTalla() {
        return talla;
    }

    public void setTalla(String talla) {
        String oldTalla = this.talla;
        this.talla = talla;
        propertyChangeSupport.firePropertyChange(PROP_TALLA, oldTalla, talla);
    }

        
        
    public static final String PROP_DESCRIPCION = "descripcion";

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        String oldDescripcion = this.descripcion;
        this.descripcion = descripcion;
        propertyChangeSupport.firePropertyChange(PROP_DESCRIPCION, oldDescripcion, descripcion);
    }

        
    public static final String PROP_CODIGO = "codigo";

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        String oldCodigo = this.codigo;
        this.codigo = codigo;
        propertyChangeSupport.firePropertyChange(PROP_CODIGO, oldCodigo, codigo);
    }

    private transient final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

}
