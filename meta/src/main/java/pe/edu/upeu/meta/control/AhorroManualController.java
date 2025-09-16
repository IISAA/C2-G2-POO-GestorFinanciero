package pe.edu.upeu.meta.control;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pe.edu.upeu.meta.modelo.MetaAhorro;
import pe.edu.upeu.meta.servicio.MetaAhorroServicioI;

@Controller
public class AhorroManualController {
    @Autowired
    MetaAhorroServicioI metaAhorroServicioI;
    int indexE = -1;

    @FXML
    TextField txtValor, txtAgregar;
    @FXML
    TextArea txtarDescripcion;

    @FXML
    private TableView<MetaAhorro> tableView;
    ObservableList<MetaAhorro> listaMetas;
    @FXML
    private TableColumn<MetaAhorro, String> idMetaColumn, valorColumn, descripcionColumn;
    private TableColumn<MetaAhorro, Void> opcColumn;

    @FXML
    public void initialize() {
        definirColumnas();
        listarMetas();
    }

    public void limpiarFormulario() {
        txtValor.setText("");
        txtAgregar.setText("");
    }

    @FXML
    public void asignarAhorroManual() {
        Double suma, pull, asign;
        pull = Double.parseDouble(txtValor.getText());
        asign = Double.parseDouble(txtAgregar.getText());
        suma = pull + asign;

        MetaAhorro ma = new MetaAhorro();
        ma.setValor(new SimpleDoubleProperty(suma));
        ma.setDescripcion(new SimpleStringProperty(txtarDescripcion.getText()));

        if (indexE == -1) {
            metaAhorroServicioI.save(ma);
        } else {
            metaAhorroServicioI.update(ma, indexE);
            indexE = -1;
        }
        listarMetas();

        limpiarFormulario();
    }

    public void definirColumnas() { //método para establecer columnas
        idMetaColumn = new TableColumn("ID");
        valorColumn = new TableColumn("Valor");
        descripcionColumn = new TableColumn("Descripción");
        opcColumn = new TableColumn("Opciones");
        //opcColumn.setPrefWidth(200);

        tableView.getColumns().addAll(idMetaColumn, valorColumn, descripcionColumn, opcColumn);
    }


    public void agregarAccionBotones() {
        Callback<TableColumn<MetaAhorro, Void>, TableCell<MetaAhorro, Void>> cellFactory = param -> new TableCell<>() {

            private final Button asignarBtn = new Button("Asignar");

            {
                asignarBtn.setOnAction((event) -> {
                    MetaAhorro ma = getTableView().getItems().get(getIndex());
                    editarDatos(ma, getIndex());
                });
            }

            @Override
            public void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox hbox = new HBox(asignarBtn);
                    hbox.setSpacing(10);
                    setGraphic(hbox);
                }
            }
        };
        opcColumn.setCellFactory(cellFactory);
    }

    public void listarMetas() {
        //idMetaColumn.setCellValueFactory(cellData -> cellData.getValue().getIdMeta().asString());
        valorColumn.setCellValueFactory(cellData -> cellData.getValue().getValor().asString());
        descripcionColumn.setCellValueFactory(cellData -> cellData.getValue().getDescripcion());

        agregarAccionBotones();
        listaMetas = FXCollections.observableArrayList(metaAhorroServicioI.findAll());
        tableView.setItems(listaMetas);
    }

    public void editarDatos(MetaAhorro ma, int index) {
        txtValor.setText(ma.getValor().getValue().toString());
        txtarDescripcion.setText(ma.getDescripcion().getValue().toString());
        indexE = index;
    }

}
