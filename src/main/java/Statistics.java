
import java.util.Comparator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class Statistics implements SceneView {

    private BorderPane border;
    private GridPane gridpane;
    private TableView table;
    private Button exit;
    private Button selecCharacters;

    public Statistics() {
        this.border = new BorderPane();
        this.gridpane = new GridPane();
        this.table = new TableView();
        exit = new Button("Salir");
        selecCharacters = new Button("Ir a seleccionar personajes");
    }

    public Button getExit() {
        return exit;
    }

    public Button getSelecCharacters() {
        return selecCharacters;
    }

    public VBox table() {
        //Crear una tabla
        this.table = new TableView();
        Registry registry = new Registry();
        ObservableList<Player> payerlist = registry.getPlayersList();
        payerlist.sort(
                Comparator.comparingInt(Player::getVictories).reversed()
        );
        ObservableList<Player> player= FXCollections.observableArrayList();
        int i=0;
        while(i<7){
            
            if(payerlist.get(i).getName().isEmpty()){
                break;
            }
            player.add(payerlist.get(i));
            i++;
        }
        this.table.setEditable(true);
        //Columnas de la tabla 
        TableColumn nombreCol = new TableColumn("Nombre: ");
        nombreCol.setCellValueFactory(
                new PropertyValueFactory<Player, String>("name"));
        TableColumn apellidoCol = new TableColumn("Apellido: ");
        apellidoCol.setCellValueFactory(
                new PropertyValueFactory<Player, String>("lastName"));
        TableColumn cedulaCol = new TableColumn("Cedula: ");
        cedulaCol.setCellValueFactory(
                new PropertyValueFactory<Player, String>("numberId"));
        TableColumn usuarioCol = new TableColumn("Usuario: ");
        usuarioCol.setCellValueFactory(
                new PropertyValueFactory<Player, String>("usuario"));
        TableColumn idCol = new TableColumn("id: ");
        idCol.setCellValueFactory(
                new PropertyValueFactory<Player, String>("id"));
        TableColumn victoriasCol = new TableColumn("Victorias: ");
        victoriasCol.setCellValueFactory(
                new PropertyValueFactory<Player, String>("victories"));
        this.table.getColumns().add(idCol);
        this.table.getColumns().add(nombreCol);
        this.table.getColumns().add(apellidoCol);
        this.table.getColumns().add(cedulaCol);
        this.table.getColumns().add(usuarioCol);
        this.table.getColumns().add(victoriasCol);
        this.table.setItems(player);

        VBox vbox = new VBox();
        vbox.setSpacing(100);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.getChildren().add(table);
        vbox.maxWidth(25);
        vbox.maxHeight(25);
        return vbox;
    }

    @Override
    public Scene showView() {
        this.border = new BorderPane();
        
        Image backgroudImage = new Image("fondo.gif");
        ImageView backgroundView = new ImageView(backgroudImage);
        //backgroundView.setStyle("-fx-background-color: BLACK");
        backgroundView.setFitHeight(400);
        
        backgroundView.setPreserveRatio(true);
        backgroundView.setSmooth(true);
        backgroundView.setCache(true);
        this.border.getChildren().add(backgroundView);
        
        exit.setTranslateX(220);
        selecCharacters.setTranslateX(170);
        Label play = new Label("POO");
        Label label2 = new Label("Values");
        play.setStyle("-fx-text-fill: #F4F6F7  !important; -fx-highlight-text-fill: #F4F6F7  !important");
        play.setTranslateX(220);
        Label result = new Label("THE KILLEST");
        result.setStyle("-fx-text-fill: #F4F6F7  !important; -fx-highlight-text-fill: #F4F6F7  !important");
        result.setFont(new Font("Cambria", 32));
        result.setTranslateX(170);
        VBox hbox = new VBox();
        hbox.getChildren().addAll(play, result, table(), exit, selecCharacters);
        hbox.setStyle("-fx-border-color:white; -fx-border-width: 5;");
        this.border.setCenter(hbox);
        this.border.setStyle("-fx-border-color:white; -fx-border-width: 1;");
        Scene registro = new Scene(this.border);
        return registro;
    }
}
