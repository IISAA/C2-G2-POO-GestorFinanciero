package pe.edu.upeu.meta.modelo;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MetaAhorro {
    private IntegerProperty idMeta;
    private DoubleProperty valor;
    private StringProperty descripcion;
}
