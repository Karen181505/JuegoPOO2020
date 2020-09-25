import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.animation.Animation;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.text.Font;
import javafx.util.Duration;


public class FightWindow implements SceneView{

    private ArrayList<String> register;
    private Label[] lifeCh;
    private Label[] stamiteCh;
    private Label[] imgChrPly;
    private Label registerView;
    private Button nextView;
    private VBox view;
    private boolean turn;
    private Player[] players;
    private Animation[] animation1;
    private Animation[] animation2;
    private ArrayList<Button> buttonsplayer1 = new ArrayList<>();
    private ArrayList<Button> buttonsplayer2 = new ArrayList<>();
    private FightCaracters fight;
    private int optionF;
    private  Image picBG= new Image ("hoja.gif");
    private AudioClip audio;



    public FightWindow() {
        this.players = new Player[2];
        this.register = new ArrayList<>();
        this.lifeCh = new Label[2];
        this.stamiteCh = new Label[2];
        this.imgChrPly = new Label[2];     
        this.nextView = new Button("Finalizar");
        this.nextView.setFont(Font.font("Verdana", 13));
        this.nextView.setMinSize(200, 27);
        this.nextView.setMaxSize(200, 27);
        this.optionF = 0;
        this.audio = new AudioClip("file:animals027.wav");


    }

    public void setPlayers(Player[] players) {
        
        this.view = new VBox();
        this.players= players;
        this.imgChrPly[0] = new Label();
        this.imgChrPly[0].setGraphic(this.players[0].getPersonaje().getImage());
        this.imgChrPly[1] = new Label();
        this.imgChrPly[1].setGraphic(this.players[1].getPersonaje().getImage());
        this.registerView = new Label();
        this.registerView.setStyle("-fx-border-color:black;");
        this.registerView.setAlignment(Pos.TOP_LEFT);
        setStyler(this.registerView, 10, 780, 50);
        this.optionF = 0;
        fight = new FightCaracters(players);
        turn=false;
        this.animation1=resetAnimation(animation1,(this.players[0].getPersonaje().getImage()));
        this.animation2=resetAnimation(animation2,(this.players[1].getPersonaje().getImage()));
        
    }
    private Animation[] resetAnimation(Animation[] animation, ImageView img){
        animation=new  Animation[3];
        animation[0]= new SpriteAnimation(
                img,
                Duration.millis(900),
                6, 6,
                0, 0,
                99, 90
        );
        animation[0].setCycleCount(Animation.INDEFINITE);

        animation[1]= new SpriteAnimation(
                img,
                Duration.millis(700),
                6, 6,
                0, 100,
                99, 90
        );
        animation[1].setCycleCount(1);
        
        animation[2]= new SpriteAnimation(
                img,
                Duration.millis(1500),
                6, 6,
                0, 200,
                99, 90
        );
        animation[2].setCycleCount(1);
        return  animation;
    }
    private void actionImg(int num, boolean t){
        if(t){
            animation1[num].play();
            animation2[0].stop();
        }else{
            animation2[num].play();
            animation1[0].stop();
        }
    }
    @Override
    public Scene showView() {
        view.getChildren().add(getView());
        Scene viewF = new Scene(view, 860, 550);
        return viewF;
    }

    public Parent getView() {
        this.animation1[0].play();
        this.animation2[0].play();
        System.out.println("Entrada");
        System.out.println(this.players[0].getName()+" *  id:"+this.players[0].getId() + " *  victorias: " + this.players[0].getVictories());
        System.out.println(this.players[1].getName()+" *  id:"+this.players[1].getId() + " *  victorias: " + this.players[1].getVictories());
        System.out.println("--------------------------------\n");
        VBox view = new VBox();//la pantalla completa
        //formato de pantalla
        view.setSpacing(5);
        view.setStyle("-fx-border-color:black;");
        view.setPadding(new Insets(10, 10, 10, 10));
        //elementos dentro de la pantalla
        
        //Parte superior
        StackPane squareAction= new StackPane();

        VBox challengersDatas = new VBox();//datos del jugadores y personaje
        BorderPane buttonsPlayersAction = new BorderPane();//botones de accion

        //elemento para dar borde 
        VBox buttonsAndRegister = new VBox();
        buttonsAndRegister.setStyle("-fx-border-color:black;");
        buttonsAndRegister.setPadding(new Insets(10, 10, 10, 10));
        buttonsAndRegister.setSpacing(10);

        //Relleno de    challengersDatas     
        VBox characterInfoPly1 = new VBox();//datos de cada jugador
        VBox characterInfoPly2 = new VBox();
        //inicializa los personajes
        initCharacterInfo(characterInfoPly1, false);
        initCharacterInfo(characterInfoPly2, true);

        //ubica
        BorderPane locateCtInfo1 = new BorderPane();
        BorderPane locateCtInfo2 = new BorderPane();

        locateCtInfo1.setLeft(characterInfoPly1);
        locateCtInfo1.setRight(imgChrPly[1]);

        locateCtInfo2.setRight(characterInfoPly2);
        locateCtInfo2.setLeft(imgChrPly[0]);
        //añade en una fila
        challengersDatas.getChildren().add(locateCtInfo1);
        challengersDatas.getChildren().add(locateCtInfo2);
        ImageView background = new ImageView();
        background.setImage(new Image ("hoja.gif"));
        background.setFitHeight(200);
        background.setPreserveRatio(true);
        background.setSmooth(true);
        background.setCache(true);
        
        squareAction.getChildren().addAll(background,challengersDatas);


//relleno de buttonsPlayersAction
        //cuadro de botones
        GridPane buttonsPlayer1Format = new GridPane();
        GridPane buttonsPlayer2Format = new GridPane();
        //agrega los botones a los cuadros

        initBattleButtons(buttonsplayer1, true);
        initBattleButtons(buttonsplayer2, false);
        buttonsPlayerDisable();
        outButtonsBattle(buttonsPlayer1Format, buttonsplayer1);
        outButtonsBattle(buttonsPlayer2Format, buttonsplayer2);

        //pone en una linea
        buttonsPlayersAction.setRight(buttonsPlayer2Format);
        buttonsPlayersAction.setLeft(buttonsPlayer1Format);

        buttonsAndRegister.getChildren().add(buttonsPlayersAction);
        buttonsAndRegister.getChildren().add(this.registerView);
        buttonsAndRegister.setStyle("-fx-background-color: radial-gradient(radius 120%, black, derive(black, -40%), derive(white, 60%));");
        this.registerView.setStyle("-fx-background-color: WHITE");

//añade a la pantalla
        view.getChildren().add(squareAction);
        view.getChildren().add(buttonsAndRegister);
        return view;

    }

    //inicializa los botones
    private void initBattleButtons(ArrayList<Button> player, boolean howPlayer) {
        this.audio.play();
        //declaracion de los botones
        player.add(new Button("Ataque"));
        player.add(new Button("Ataque Final"));
        player.add(new Button("Curar"));
        player.add(new Button("Defensa"));

        //formato
        for (Button button : player) {
            button.setFont(Font.font("Verdana", 13));
            button.setMinSize(120, 27);
            button.setMaxSize(0, 27);
            //tamaño del boton buscar
        }

        //cuando clikea
        player.get(0).setOnAction(event -> {
            actionImg(1,!turn);
            fight.atack(howPlayer);
            
            if (stamiteIsEmpty(howPlayer, 5)) {
                actuallyView(" uso Ataque");
            } else {
                actuallyView(" uso Ataque pero reduce su vida");
            }
            

        });
        player.get(1).setOnAction(event -> {
            actionImg(1,!turn);
            fight.atackFinal(howPlayer);
            actuallyView(" uso Ataque Final");

        });
        
        player.get(2).setOnAction(event -> {
            
          
            fight.heal(howPlayer);

            actuallyView(" uso Curar");

        });
        player.get(3).setOnAction(event -> {
            


            fight.protect(howPlayer);
            actuallyView(" uso Defensa");

        });
    }

    //pone los botones en el recuadro
    private void outButtonsBattle(GridPane buttonsFormat, ArrayList<Button> buttonsplayer) {
        //formato
        buttonsFormat.setVgap(5);
        buttonsFormat.setHgap(5);
        buttonsFormat.setPadding(new Insets(5, 5, 5, 5));
        //pone en tabla
        buttonsFormat.add(buttonsplayer.get(0), 0, 0);
        buttonsFormat.add(buttonsplayer.get(1), 1, 0);
        buttonsFormat.add(buttonsplayer.get(2), 0, 1);
        buttonsFormat.add(buttonsplayer.get(3), 1, 1);

    }

    //inicializa los recuadros de los personajes
    private void initCharacterInfo(VBox boxCharacter, boolean pos) {
        Label nameUser = new Label("");
        Label eNickName = new Label("");
        int Hply = 0;
        if (pos) {
            nameUser.setAlignment(Pos.CENTER_RIGHT);
            eNickName.setAlignment(Pos.CENTER_RIGHT);
            boxCharacter.setAlignment(Pos.CENTER_RIGHT);
            Hply = 1;
        }
        boxCharacter.setPadding(new Insets(10, 10, 10, 10));
        boxCharacter.setSpacing(1);
        boxCharacter.setStyle("-fx-border-color:black; -fx-border-width:3;");
        boxCharacter.setMaxSize(300, 120);
        boxCharacter.setMinSize(300, 120);

        GridPane panel = new GridPane();
        panel.setVgap(2);
        //etiquetas estaticas
        eNickName = new Label(this.players[Hply].getPersonaje().getName());
        Label eLife = new Label("Vida:");
        Label eStamite = new Label("Estamita:");
        nameUser = new Label(this.players[Hply].nameUser());
        //ubicacion y control de vida y estamita

        BorderPane position = new BorderPane();

        resetLabels(Hply);
        this.stamiteCh[Hply].setTextFill(javafx.scene.paint.Color.GREEN);
        this.lifeCh[Hply].setTextFill(javafx.scene.paint.Color.GREEN);
        this.lifeCh[Hply].setStyle("-fx-border-color:black;");
        this.stamiteCh[Hply].setStyle("-fx-border-color:black;");
        //Cambia el formato de los label
        setStyler(nameUser, 20, 150, 25);
        setStyler(eNickName, 20, 150, 25);
        setStyler(eLife, 15, 80, 15);
        setStyler(eStamite, 15, 80, 15);
        setStyler(this.stamiteCh[Hply], 15, 120, 20);
        setStyler(this.lifeCh[Hply], 15, 120, 20);
        //añade en su posicion
        panel.add(this.stamiteCh[Hply], 1, 1);
        panel.add(this.lifeCh[Hply], 1, 0);
        panel.add(eLife, 0, 0);
        panel.add(eStamite, 0, 1);
        if (pos) {
            position.setRight(panel);
        } else {
            position.setLeft(panel);
        }
        boxCharacter.getChildren().add(nameUser);
        boxCharacter.getChildren().add(eNickName);
        boxCharacter.getChildren().add(position);

    }

    private void setStyler(Label label, int sizeLetter, int weight, int tall) {
        //da formato a los label 
        label.setFont(Font.font("Verdana", sizeLetter));
        label.setMaxSize(weight, tall);
        label.setMinSize(weight, tall);
    }

    //actualizacion del registro de batalla
    private void registerView() {
        int sizeR = this.register.size();
        String outView = "";
        if (sizeR > 4) {
            for (int i = sizeR - 4; i < (sizeR); i++) {
                outView += this.register.get(i) + "\n";
            }
        } else {
            for (int i = 0; i < (sizeR); i++) {
                outView += this.register.get(i) + "\n";
            }
        }
        this.registerView.setText(outView);
    }

    //inicializa los labels de las caracteristicas de los jugadores
    private void resetLabels(int Hply) {
        this.lifeCh[Hply] = new Label(this.players[Hply].getPersonaje().getVida() + "/" + this.players[Hply].getLife());

        this.stamiteCh[Hply] = new Label(this.players[Hply].getPersonaje().getEstamina() + "/" + this.players[Hply].getStamite());
    }

    //devuelve que jugador esta ejecutando la accion
    private String howPlayer(boolean how) {
        String hplay = "Jugador ";
        if (!how) {
            hplay += "1:";
        } else {
            hplay += "2:";
        }
        return hplay;
    }
    //devuelve si aun le queda estamita al jugador para la accion

    private boolean stamiteIsEmpty(boolean howPlayer, int cost) {
        int t = 0;
        if (!howPlayer) {
            t = 1;
        }
        return (this.players[t].stamiteIsNotEmpty(cost));
    }
    //desactiva los botones si no hay estamita

    private void buttonActionDisable(ArrayList<Button> player, boolean howPlayer) {
        if (!stamiteIsEmpty(!howPlayer, 50)) {
            player.get(1).setDisable(true);
        }
        if (!stamiteIsEmpty(!howPlayer, 20)) {
            player.get(2).setDisable(true);
        }
        if (!stamiteIsEmpty(!howPlayer, 75)) {
            player.get(3).setDisable(true);
        }
    }
//devuelve los botones de un jugador

    private ArrayList<Button> buttonsPlayer(boolean howPlayer) {
        if (howPlayer) {
            return this.buttonsplayer2;
        } else {
            return this.buttonsplayer1;
        }
    }
//desactiva o activa segun corresponda el caso

    private void buttonsPlayerDisable() {
        int turnH = 0, other = 1;
        if (turn) {
            turnH = 1;
            other = 0;
        }
        if (this.players[turnH].getPersonaje().getVida() == 0 && this.players[other].getPersonaje().getVida() == 0) {
            this.register.add("empate");
            this.optionF = 2;
            for (Button button : buttonsPlayer(!turn)) {
                button.setDisable(true);
            }
            for (Button button : buttonsPlayer(turn)) {
                button.setDisable(true);
            }
        }
        if (this.players[turnH].getPersonaje().getVida() == 0) {
            this.optionF = 1;
            this.players[other].win();
            this.register.add(howPlayer(!turn) + " gano");
            actionImg(2,turn);

            for (Button button : buttonsPlayer(!turn)) {
                button.setDisable(true);
            }
            for (Button button : buttonsPlayer(turn)) {
                button.setDisable(true);
            }
        } else {
            for (Button button : buttonsPlayer(!turn)) {
                button.setDisable(true);
            }
            for (Button button : buttonsPlayer(turn)) {
                button.setDisable(false);
            }
            buttonActionDisable(buttonsPlayer(turn), turn);
        }

    }
//actualiza la ventana de estados

    private void actuallyView(String dialog) {
        this.lifeCh = fight.resetLabelLife(lifeCh);
        this.fight.recoverStamite(!turn);
        this.stamiteCh = fight.resetLabelStamite(stamiteCh);
        this.register.add(howPlayer(turn) + dialog);
        this.turn = !this.turn;
        buttonsPlayerDisable();
        registerView();
        finalation();

    }
//finaliza el juego

    private void finalation() {
        switch (this.optionF) {
            case 1:
                //finalizo con ganador
                BorderPane locateButton = new BorderPane();
                locateButton.setCenter(this.nextView);
                this.view.getChildren().add(locateButton);
                System.out.println("jugador 1 "+this.players[0].getVictories());
                System.out.println("jugador 2 "+this.players[1].getVictories());
                saveInfo();
                break;
        }
    }

    public Button getNextView() {
        return nextView;
    }

    public void resetViewP() {
        this.view = new VBox();
        this.players[0].resetP();
        this.players[1].resetP();
        this.optionF = 0;
        this.registerView.setText("");
        this.register.clear();
        turn = false;
    }
    private void saveInfo() {
        ArrayList<Player> pls= new ArrayList<>();

        try (Scanner scan = new Scanner(Paths.get("usuarios.txt"))) {
            while (scan.hasNextLine()) {
                String string = scan.nextLine();
                if (string.equals(" ")) {
                    break;
                }
               String[] parts = string.split(" ");
                String id = parts[0];
                String name = parts[1];
                String lastName = parts[2];
                String ced = parts[3];
                String usuario = parts[4];
                int wins = Integer.valueOf(parts[5]);
                pls.add(new Player(name, lastName, id, ced, usuario, wins));
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        for (Player play : pls) {
            System.out.println(play.getName() + " *  id:" + play.getId()  +" *  victorias" + play.getVictories()+ " " );
        }
        System.out.println("---------------------------");
        System.out.println("Salida");
        System.out.println(this.players[0].getName()+" *  id:"+this.players[0].getId() + " *  victorias: " + this.players[0].getVictories());
        System.out.println(this.players[1].getName()+" *  id:"+this.players[1].getId() + " *  victorias: " + this.players[1].getVictories());
        System.out.println("Archivo-----------------");
        for(Player plas:pls){
            System.out.println("jugador: " + this.players[0].getName() + "lista player:" + plas.getName());
            if(plas.getName().equals(this.players[0].getName())){
                System.out.println("aqui entra 1");
                if(this.players[0].getVictories()==1){
                    System.out.println("aqui entra2");
                    plas.win();
                }
            }
            System.out.println("jugador: " + this.players[1].getName() + "lista player:" + plas.getName());
            if(plas.getName().equals(this.players[1].getName())){
                System.out.println("aqui entra 3");
                if (this.players[1].getVictories() == 1) {
                    System.out.println("aqui entra 4");
                    plas.win();
                }
            }
            System.out.println(plas.getName() + " *  id:" + plas.getId()  +"*  victorias" + plas.getVictories()+ " " );
        }
        
  
        File f= new File("usuarios.txt");

        try {
            FileWriter w = new FileWriter(f);
            BufferedWriter bw = new BufferedWriter(w);
            PrintWriter wr = new PrintWriter(bw);
            for (Player play : pls) {
                wr.write(play.getId() + " " + play.getName() + " " + play.getLastName() + " " + play.getNumberId() + " " + play.getUsuario() + " " + play.getVictories() + "\n");
                System.out.println(play.getName()+" "+play.getVictories());
            }
            wr.close();
            bw.close();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    private int found(Player pH, ArrayList<String> ids){

        for (int i=0; i<ids.size();i++){
            System.out.println("ids Buscados " + ids.get(i));
            
            if(ids.get(i).equals(pH.getId())){
                return i;
            }
        }
        return -1;
    }
}