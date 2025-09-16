package pe.edu.upeu.meta.servicio;

import pe.edu.upeu.meta.modelo.MetaAhorro;

import java.util.List;

public interface MetaAhorroServicioI {
    //método CRUD
    void save(MetaAhorro ahorro); //C
    List<MetaAhorro> findAll(); //R
    void update(MetaAhorro ahorro, int index); //U
    void delete(int index); //D
    MetaAhorro findById(int index); // SEARCH
}
