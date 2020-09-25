
import java.util.ArrayList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Personaje {

    private String name;
    private int vida;
    private int life;
    private int estamina;
    private int stamite;
    private int ataque;
    private int ataqueFinal;
    private int defense;
    private ImageView image;

    public Personaje() {

    }

    public void newCharacter(ArrayList<String> characters) {
        this.name = characters.get(0);
        this.vida = Integer.valueOf(characters.get(1));
        this.estamina = Integer.valueOf(characters.get(2));
        this.ataque = Integer.valueOf(characters.get(3));
        this.ataqueFinal = Integer.valueOf(characters.get(4));
        this.defense = Integer.valueOf(characters.get(5));
        this.image =  new ImageView(new Image(characters.get(6)));
        this.image.setFitHeight(150);
        this.image.setFitWidth(200);
        this.life = vida;
        this.stamite= this.estamina;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public void setEstamina(int estamina) {
        this.estamina = estamina;
    }
//pierde vida

    public void looseLife(int atack) {
        int atackD = (atack - defense);
        if (atackD < 0) {
            atackD = 0;
        }
        this.vida -= atackD;
        if (this.vida < 0) {
            this.vida = 0;
        }
    }
//gana vida

    public void winLife() {
        int plusLife = (life * 20) / 100;
        this.vida += plusLife;
        if (this.vida > this.life) {
            this.vida = this.life;
        }
    }
//pierde estamita

    public void looseEstamina(int atackCost) {
        System.out.println("deberia perder " + atackCost);
        this.estamina -= atackCost;
    }
    
    public void winEstamina(){
        int plusEstamita= (stamite*10)/100;
        this.estamina+=plusEstamita;
        if (this.estamina > this.stamite) {
            this.estamina = this.stamite;
        }
    }
    
//devuelve 0

    public void stamiteStatic() {
        this.estamina = 0;
    }

    public String getName() {
        return name;
    }

    public int getVida() {
        return vida;
    }

    public int getEstamina() {
        return estamina;
    }

    public int getAtaque() {
        return ataque;
    }

    public int getAtaqueFinal() {
        return ataqueFinal;
    }

    public int getDefense() {
        return defense;
    }

    public ImageView getImage() {
        return image;
    }
}
