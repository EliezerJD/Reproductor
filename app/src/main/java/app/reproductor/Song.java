package app.reproductor;

public class Song {
    String name;
    String artist;
    int image;
    public Song(String name, String artist, int image){
        this.name = name;
        this.artist = artist;
        this.image = image;
    }


    public String getName(){
        return name;
    }
}
