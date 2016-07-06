package player.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Song {

	private final StringProperty songName;
	private final StringProperty songDuration;


	public Song(String songName, String songDuration) {
		this.songName = new SimpleStringProperty(songName);
		this.songDuration = new SimpleStringProperty(songDuration);
	}

	public String getSongName() {
		return songName.get();
	}

	public void setSongName(String songName) {
		this.songName.set(songName);
	}

	public StringProperty songNameProperty() {
		return songName;
	}

	public String getSongDuration() {
		return songDuration.get();
	}

	public void setSongDuration(String songDuration) {
		this.songDuration.set(songDuration);
	}

	public StringProperty songDurationProperty() {
		return songDuration;
	}
}
