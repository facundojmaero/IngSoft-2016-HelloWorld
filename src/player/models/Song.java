package player.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Song {

	private final StringProperty songName;
	private final StringProperty songDuration;
	private final StringProperty songArtist;
	private final StringProperty songAlbum;
	private final StringProperty songYear;


	public Song(String songName, String songDuration, String songArtist, String songAlbum, String songYear) {
		this.songName = new SimpleStringProperty(songName);
		this.songDuration = new SimpleStringProperty(songDuration);
		this.songArtist = new SimpleStringProperty(songArtist);
		this.songAlbum = new SimpleStringProperty(songAlbum);
		this.songYear = new SimpleStringProperty(songYear);
	}

	public String getSongName() {return songName.get();}
	public String getSongDuration() {return songDuration.get();}
	public String getSongArtist(){return songArtist.get();}
	public String getSongAlbum(){return songAlbum.get();}
	public String getSongYear(){return songYear.get();}

	public void setSongName(String songName) {this.songName.set(songName);}
	public void setSongDuration(String songDuration) {this.songDuration.set(songDuration);}
	public void setSongArtist(String songArtist){this.songArtist.set(songArtist);}
	public void setSongAlbum(String songAlbum){this.songAlbum.set(songAlbum);}
	public void setSongYear(String songYear){this.songYear.set(songYear);}

	public StringProperty songNameProperty() {return songName;}
	public StringProperty songDurationProperty() {return songDuration;}
	public StringProperty songAlbumProperty() {return songAlbum;}
	public StringProperty songArtistProperty() {return songArtist;}
	public StringProperty songYearProperty() {return songYear;}

}