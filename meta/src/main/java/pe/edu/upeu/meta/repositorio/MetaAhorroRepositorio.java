package pe.edu.upeu.meta.repositorio;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import pe.edu.upeu.meta.modelo.MetaAhorro;

import java.util.ArrayList;
import java.util.List;

public abstract class   MetaAhorroRepositorio {
    public List<MetaAhorro> listaMetas = new ArrayList<>();

    public List<MetaAhorro> findAll() {
        listaMetas.add(new MetaAhorro(
                new SimpleIntegerProperty(1),
                new SimpleDoubleProperty(100.00),
                new SimpleStringProperty("Para comprarme una bicicleta")
        ));
        listaMetas.add(new MetaAhorro(
                new SimpleIntegerProperty(2),
                new SimpleDoubleProperty(50.00),
                new SimpleStringProperty("Para mi libro de álgebra")
        ));
        return listaMetas;
    }
}
