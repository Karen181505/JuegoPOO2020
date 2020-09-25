
import java.util.ArrayList;
import java.util.Random;

public class Player {

    private String name;
    private String lastName;
    private String numberId;
    private String id;
    private int victories;
    private  String usuario;
    private Personaje personaje;
    private int stamite;
    private int defense;
    public boolean defenseActive = false;
    private int turnActive;
    private int life;

    public int getStamite() {
        return stamite;
    }

    public int getLife() {
        return life;
    }

    //toca pasara a un archivo
    public Player(String name, String lastName, String numberId, String id, String usuario, int victories) {
        this.name = name;
        this.lastName = lastName;
        this.numberId = numberId;
        this.id = id;
        this.personaje = new Personaje();
        this.usuario = usuario;
        this.victories = victories;

    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCedula(String numberId) {
        this.numberId = numberId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getVictories() {
        return victories;
    }

    public void setVictories(int victories) {
        this.victories = victories;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Personaje getPersonaje() {
        return personaje;
    }
//toca corregit

    public void newCaracter(ArrayList<String> characters) {
        this.personaje.newCharacter(characters);
        this.victories = 0;
        this.stamite = this.personaje.getEstamina();
        this.defense = this.personaje.getDefense();
        this.life = this.getPersonaje().getVida();

    }

    public String getName() {
        return this.name;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getNumberId() {
        return this.numberId;
    }

    public String getId() {
        return this.id;
    }
//ataque normal

    public int atackAction() {
        int loose = (stamite * 5) / 100;
        System.out.println("Costo el ataque " + loose);
        this.personaje.looseEstamina(loose);
        return this.personaje.getAtaque();
    }
//ataque fuerte

    public int atackFinalAction() {
        int loose = (stamite * 50) / 100;
        this.personaje.looseEstamina(loose);
        return this.personaje.getAtaque();
    }
//cura

    public void Heal() {
        int loose = (stamite * 20) / 100;
        this.personaje.looseEstamina(loose);
        this.personaje.winLife();

    }
//disminuye la vida

    public void damage(int damage) {
        if (this.defenseActive) {//si esta activo la defensa solo se recibe el 75% del ataque
            damage = (damage * 75) / 100;
            this.turnActive--;
            if (this.turnActive == 0) {
                this.defenseActive = false;
            }
        }
        this.personaje.looseLife(damage);
    }
//activa la defensa

    public void defenseOn() {
        if (!defenseActive) {
            int loose = (stamite * 75) / 100;
            this.personaje.looseEstamina(loose);
            defenseActive = true;
            this.turnActive = 3;//toca ver
        }

    }
//devielve V si aun hay estamita para tal accion

    public boolean stamiteIsNotEmpty(int cost) {
        if (personaje.getEstamina() == 0) {
            return false;
        }
        int loose = (stamite * cost) / 100;
        int actual = (this.personaje.getEstamina());
        int stamiteFinish = this.personaje.getEstamina() - loose;
        System.out.println("estamita actual " + actual + " estamita q va a perder " + loose + " lo que deberia quedar " + stamiteFinish);
        return stamiteFinish >= 0;
    }

    public String nameUser() {
        return name + " " + lastName;
    }
    //Aumenta las vistorias

    public void win() {
        
        this.victories++;
    }
    //vuelven a pelear

    public void resetP() {
        this.personaje.setVida(life);
        this.personaje.setEstamina(stamite);
    }
    public void recoverStamite(){
        this.personaje.winEstamina();
    }
}
