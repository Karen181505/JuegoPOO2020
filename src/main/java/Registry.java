
import java.awt.Color;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Registry implements SceneView {

    private Button seleccionarPersonajes;

    private ObservableList<Player> playersList;
    private ObservableList<String> ids;
    private ArrayList<String> names;
    private ArrayList<String> lastNames;
    private ArrayList<String> numberId;
    private ArrayList<String> usuarios;
    private Label error;

    public Registry() {
        this.playersList = FXCollections.observableArrayList();
        this.ids = FXCollections.observableArrayList();
        this.names = new ArrayList<>();
        this.numberId = new ArrayList<>();
        this.lastNames = new ArrayList<>();
        this.usuarios = new ArrayList<>();
        this.error = new Label("");
        try ( Scanner scan = new Scanner(Paths.get("usuarios.txt"))) {
            while (scan.hasNextLine()) {
                String string = scan.nextLine();
                if (string.equals(" ")) {
                    break;
                }
                String[] parts = string.split(" ");
                String id = parts[0];
                this.ids.add(id);
                String name = parts[1];
                this.names.add(name);
                String lastName = parts[2];
                this.lastNames.add(lastName);
                String ced = parts[3];
                this.numberId.add(ced);
                String usuario = parts[4];
                this.usuarios.add(usuario);
                int wins = Integer.valueOf(parts[5]);
                this.playersList.add(new Player(name, lastName, id, ced, usuario, wins));

            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        this.seleccionarPersonajes = new Button("Ir a seleccionar personajes");
    }

    @Override
    public Scene showView() {

        BorderPane border = new BorderPane();
        GridPane gridpane = new GridPane();
        TableView table = new TableView();
        ChoiceBox id = new ChoiceBox(this.ids);

        Button newPlayerButton = new Button("Nuevo Player");
        Button cancelButton = new Button("Cancelar");
        Button crear = new Button("Crear");
        // Button seleccionarPersonajes = new Button("Ir a seleccionar personajes");

        Label name = new Label("Nombre: ");
        Label apellido = new Label("Apellido: ");
        Label cedula = new Label("Cedula: ");
        Label usuario = new Label("Usuario: ");
        Label newId = new Label("Nuevo Id: ");

        
       name.setStyle("-fx-text-fill: #F4F6F7  !important; -fx-highlight-text-fill: #F4F6F7  !important; -fx-font-family: Arial");
        
        apellido.setStyle("-fx-text-fill: #F4F6F7  !important; -fx-highlight-text-fill: #F4F6F7  !important; -fx-font-family: Arial");
        cedula.setStyle("-fx-text-fill: #F4F6F7  !important; -fx-highlight-text-fill: #F4F6F7  !important; -fx-font-family: Arial");
        usuario.setStyle("-fx-text-fill: #F4F6F7  !important; -fx-highlight-text-fill: #F4F6F7  !important; -fx-font-family: Arial");
        newId.setStyle("-fx-text-fill: #F4F6F7  !important; -fx-highlight-text-fill: #F4F6F7  !important; -fx-font-family: Arial");
        this.error.setStyle("-fx-text-fill: #F4F6F7  !important; -fx-highlight-text-fill: #F4F6F7  !important; -fx-font-family: Arial");

        Image backgroudImage = new Image("fondo.gif");
        ImageView backgroundView = new ImageView(backgroudImage);
        backgroundView.setStyle("-fx-background-color: BLACK");
        backgroundView.setFitHeight(450);
        backgroundView.setPreserveRatio(true);
        backgroundView.setSmooth(true);
        backgroundView.setCache(true);
        border.getChildren().add(backgroundView);

        TextField newIdText = new TextField();
        TextField nombreText = new TextField();
        TextField apellidoText = new TextField();
        TextField cedulaText = new TextField();
        TextField usuarioText = new TextField();
        ArrayList<TextField> infoFields = new ArrayList<>();
        infoFields.add(newIdText);
        infoFields.add(nombreText);
        infoFields.add(apellidoText);
        infoFields.add(cedulaText);
        infoFields.add(usuarioText);

        ArrayList<String> na = this.names;
        ArrayList<String> lastna = this.lastNames;
        ArrayList<String> cedu = this.numberId;
        ArrayList<String> usua = this.usuarios;

        id.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue ov,
                    Number value, Number newValue) {
                nombreText.setText(na.get(newValue.intValue()));
                apellidoText.setText(lastna.get(newValue.intValue()));
                cedulaText.setText(cedu.get(newValue.intValue()));
                usuarioText.setText(usua.get(newValue.intValue()));
            }
        }
        );
        gridpane.add(id, 2, 0);
        gridpane.add(name, 1, 2);
        gridpane.add(nombreText, 2, 2);
        gridpane.add(newPlayerButton, 3, 2);
        gridpane.add(apellido, 1, 3);
        gridpane.add(apellidoText, 2, 3);
        gridpane.add(cancelButton, 3, 3);
        gridpane.add(cedula, 1, 4);
        gridpane.add(cedulaText, 2, 4);
        gridpane.add(usuario, 1, 5);
        gridpane.add(usuarioText, 2, 5);
        gridpane.add(this.error, 2, 6);
        gridpane.add(crear, 3, 5);
        gridpane.add(newId, 1, 1);
        gridpane.add(newIdText, 2, 1);
        gridpane.setStyle("-fx-border-color:black; -fx-border-width: 1;");
        border.setTop(gridpane);

        table.setEditable(true);
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
        table.getColumns().add(idCol);
        table.getColumns().add(nombreCol);
        table.getColumns().add(apellidoCol);
        table.getColumns().add(cedulaCol);
        table.getColumns().add(usuarioCol);
        table.getColumns().add(victoriasCol);
        table.setItems(this.playersList);

        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.getChildren().add(table);
        vbox.maxWidth(100);
        vbox.maxHeight(100);
        border.setCenter(vbox);
        border.setBottom(seleccionarPersonajes);
        border.setStyle("-fx-border-color:black; -fx-border-width: 1;");

        crear.setDisable(true);
        cancelButton.setDisable(true);
        textFieldDisable(infoFields, true);

        newPlayerButton.setOnAction((event) -> {
            textFieldDisable(infoFields, false);
            setToBlank(infoFields);
            crear.setDisable(false);
            cancelButton.setDisable(false);
            id.setDisable(true);
        });

        crear.setOnAction((event) -> {
            if (isAllWrite(infoFields)) {
                addPlayer(String.valueOf(nombreText.getText()), String.valueOf(apellidoText.getText()), 
                        String.valueOf(newIdText.getText()), String.valueOf(cedulaText.getText()), 
                        String.valueOf(usuarioText.getText()));
                safeInformation();
                setToBlank(infoFields);
                textFieldDisable(infoFields, true);
                safeInformation();
            }
        });

        cancelButton.setOnAction((event) -> {
            textFieldDisable(infoFields, true);
            setToBlank(infoFields);
        });

        Scene registro = new Scene(border);
        return registro;
    }

    public ArrayList<String> getUsuarios() {
        ArrayList<String> newUsuarios = new ArrayList<>();
        for (Player person : this.playersList) {
            newUsuarios.add(person.getUsuario());
        }
        return newUsuarios;
    }

    public void setPlayersList(String name, String lastName, String id, String ced, String usuario) {
        for (Player play : this.playersList) {
            if (play.getId().equals(id)) {
                play.setCedula(ced);
                play.setName(name);
                play.setLastName(lastName);
                play.setUsuario(usuario);
            }
        }
        this.playersList = playersList;
    }

    public Button backStartScreenButton() {
        return seleccionarPersonajes;
    }

    public ObservableList<Player> getPlayersList() {
        return playersList;
    }

  

    public void addPlayer(String name, String lastName, String id, String ced, String usuario) {

        if (this.ids.contains(id) || this.usuarios.contains(usuario) ||this.numberId.contains(ced)) {
            this.error.setText("Usuario ya exite ingrese otro dato");

        } else {
            this.playersList.add(new Player(name, lastName, id, ced, usuario, 0));
            this.ids.add(id);
            this.usuarios.add(usuario);
            this.numberId.add(ced);
        }
    }

    public void safeInformation() {
        File f;
        f = new File("usuarios.txt");

        try {
            FileWriter w = new FileWriter(f);
            BufferedWriter bw = new BufferedWriter(w);
            PrintWriter wr = new PrintWriter(bw);
            for (Player play : this.playersList) {
                wr.write(play.getId() + " " + play.getName() + " " + play.getLastName() + " " + play.getNumberId() + " " + play.getUsuario() + " " + play.getVictories() + "\n");
            }
            wr.close();
            bw.close();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

  

    public void setToBlank(ArrayList<TextField> fields) {
        for (TextField textfield : fields) {
            textfield.setText("");
        }
    }

    public boolean isAllWrite(ArrayList<TextField> fields) {
        boolean isWrite = true;
        for (TextField textfield : fields) {
            if (textfield.getText().equals("")) {
                isWrite = false;
            }
        }
        return isWrite;
    }

    private void textFieldDisable(ArrayList<TextField> player, boolean active) {
        for (TextField button : player) {
            button.setDisable(active);
        }
    }
}
