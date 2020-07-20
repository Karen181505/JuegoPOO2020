import java.util.ArrayList;
public class Retos {
    private int numeroReto;
    public Retos(int numeroRetos){
        this.numeroReto=numeroRetos;
    }
    
    public String retosGuardados(){
       ArrayList<String> retos = new ArrayList<>();
       retos.add("5 Sapitos");
       retos.add("15 Abdominales");
       retos.add("25 Sentadillas");
       retos.add("5 Flexiones de pecho");
       retos.add("20 Sapitos");
       retos.add("15 Abdominales");
       retos.add("10 Fexiones de pecho");
       retos.add("30 Sapitos");
       if(this.numeroReto > retos.size()){
           return "No existe el numero de reto";
       }else{
           return retos.get(numeroReto);
       }
    }
     
}
