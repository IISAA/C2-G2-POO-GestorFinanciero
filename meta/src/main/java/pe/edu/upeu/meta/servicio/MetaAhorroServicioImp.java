package pe.edu.upeu.meta.servicio;

import org.springframework.stereotype.Service;
import pe.edu.upeu.meta.modelo.MetaAhorro;
import pe.edu.upeu.meta.repositorio.MetaAhorroRepositorio;

import java.util.List;

@Service //para inyección de dependencias
public class MetaAhorroServicioImp extends MetaAhorroRepositorio implements MetaAhorroServicioI { //herencia e implementación

    //implementación de los métodos CRUD
    @Override
    public void save(MetaAhorro ahorro) {
        listaMetas.add(ahorro);
    }

    @Override
    public List<MetaAhorro> findAll() {
        if (listaMetas.isEmpty()) {
            return super.findAll();
        }
        return listaMetas;
    }

    @Override
    public void update(MetaAhorro ahorro, int index) {
        listaMetas.set(index, ahorro);
    }

    @Override
    public void delete(int index) {
        listaMetas.remove(index);
    }

    @Override
    public MetaAhorro findById(int index) {
        return listaMetas.get(index);
    }

}
