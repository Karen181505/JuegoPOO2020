
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CharacterInfo {

    private ArrayList<String> characters;
    private ArrayList<String> characterActual;

    public CharacterInfo() {
        this.characters = new ArrayList<>();
        try (Scanner scan = new Scanner(Paths.get("Characters_Information.txt"))) {
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                this.characters.add(line);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        this.characterActual= new ArrayList<>();
        String[] parts = this.characters.get(0).split(";");
        for (String part : parts) {
            this.characterActual.add(part);
        }
    }

    public void resetCharacter(int pos) {
      this.characterActual.clear();
        if (pos > (this.characters.size() - 1)) {
            pos = this.characters.size() - 1;
        }
        String[] parts = this.characters.get(pos).split(";");
        for (String part : parts) {
            this.characterActual.add(part);
        }
    }
    public ImageView giveImg(){
        return new ImageView(new Image(this.characterActual.get(this.characterActual.size()-1)));
    }

    public ArrayList<String> giveNames() {
        ArrayList<String> names = new ArrayList<>();
        for(int i=1; i<this.characters.size();i++){
            String[] words = this.characters.get(i).split(";");
            names.add(words[0]);
        }

        return names;
    }

    public ArrayList<ImageView> giveImage() {
        ArrayList<String> images = new ArrayList<>();
        for (String line : this.characters) {
            String[] words = line.split(";");
            images.add(words[words.length - 1]);
        }
        ArrayList<ImageView> imagesV = new ArrayList<>();
        for (String img : images) {
            ImageView ing = new ImageView(new Image(img));
            imagesV.add(ing);
        }
        return imagesV;
    }
    public ArrayList<String> getCharacter(){
        return this.characterActual;
    }

}
