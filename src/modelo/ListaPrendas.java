/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import org.jdesktop.observablecollections.ObservableCollections;
import org.jdesktop.observablecollections.ObservableList;


/**
 *
 * @author Sandro Gamarra
 */
public class ListaPrendas {
    
    private ObservableList<Prenda> lista; //Añadimos una lista ObservableList<Prenda>
    //Si bindeamos, nos aparecerá la opción de importar las librerias necesarias
    //sino hay que agregarlas (Beans Binding) a mano para poder usar una ObservableList

    public ListaPrendas() {
        lista = ObservableCollections.observableList(new ArrayList<Prenda>());
    }
    
    public void altaPrenda(Prenda p){
        lista.add(p);
    }
    public void bajaprenda(Prenda p){
        lista.remove(p);
    }
    
    
    
    
    public int cantidadPrendas() {
        return lista.size();
    }
    
    public boolean existePrenda(Prenda p) {
        return lista.contains(p);
    }
    
    public ArrayList<String> listadoColores() {
        ArrayList<String> tempColores = new ArrayList<>();
        
        for (Prenda p : lista)
            tempColores.add(p.getColor());
                
        ArrayList<String> colores = new ArrayList<>(new HashSet<>(tempColores));
        Collections.sort(colores);
        
        return colores;
    }
    
    public ListaPrendas prendasPorColor(String color) {        
        ListaPrendas prendasPorColor = new ListaPrendas();
        
        for (Prenda p : lista)
            if (p.getColor().equalsIgnoreCase(color)) prendasPorColor.altaPrenda(p);
                
        return prendasPorColor;
    }
    
    public int cantidadStock() {
        int cantidad = 0;
        
        for (Prenda p : lista)
            cantidad += p.getStock();
        
        return cantidad;
    }
    
    public double valoracionStock() {
        double valoracion = 0;
        
        for (Prenda p : lista)
            valoracion += p.getPrecioCoste() * p.getStock();
        
        return valoracion;
    }
    
    
    public static final String PROP_LISTA = "lista";

    public ObservableList<Prenda> getLista() {
        return lista;
    }

    public void setLista(ObservableList<Prenda> lista) {
        ObservableList<Prenda> oldLista = this.lista;
        this.lista = lista;
        propertyChangeSupport.firePropertyChange(PROP_LISTA, oldLista, lista);
    }

    private transient final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

}
