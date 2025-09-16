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
public class VerMetaController {
    @FXML
    private TextField txtValor;
    @FXML
    private TextArea txtarDescripcion;
    @FXML
    private TableView<MetaAhorro> tableView;
    ObservableList<MetaAhorro> listaMetas;

    @FXML
    private TableColumn<MetaAhorro, String> idMetaColumn, valorColumn, descripcionColumn;
    private TableColumn<MetaAhorro, Void> opcColumn;

    @Autowired
    MetaAhorroServicioI mas;
    int indexE = -1;

    @FXML
    public void initialize() {
        definirColumnas();
        listarMetas();
    }

    public void limpiarFormulario() {
        txtarDescripcion.setText("");
        txtValor.setText("");
    }

    @FXML
    public void registrarMetaAhorro() {
        MetaAhorro ma = new MetaAhorro();
        ma.setValor(new SimpleDoubleProperty(Double.parseDouble(txtValor.getText())));
        ma.setDescripcion(new SimpleStringProperty(txtarDescripcion.getText()));

        if (indexE == -1) { //si se edita se guarda
            mas.save(ma);
        } else {
            mas.update(ma, indexE);
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
        opcColumn.setPrefWidth(200);

        tableView.getColumns().addAll(idMetaColumn, valorColumn, descripcionColumn, opcColumn);
    }


    public void agregarAccionBotones() {
        Callback<TableColumn<MetaAhorro, Void>, TableCell<MetaAhorro, Void>> cellFactory = param -> new TableCell<>() {

            private final Button editarBtn = new Button("Editar");
            private final Button eliminarBtn = new Button("Eliminar");

            {
                editarBtn.setOnAction((event) -> {
                    MetaAhorro ma = getTableView().getItems().get(getIndex());
                    editarDatos(ma, getIndex());
                });
                eliminarBtn.setOnAction((event) -> {
                    eliminarMeta(getIndex());
                });
            }

            @Override
            public void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox hbox = new HBox(editarBtn, eliminarBtn);
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
        listaMetas = FXCollections.observableArrayList(mas.findAll());
        tableView.setItems(listaMetas);
    }

    public void editarDatos(MetaAhorro ma, int index) {
        txtarDescripcion.setText(ma.getDescripcion().getValue());
        txtValor.setText(ma.getValor().getValue().toString());
        indexE = index;
    }

    public void eliminarMeta(int index) {
        mas.delete(index);
        listarMetas();
    }
}
