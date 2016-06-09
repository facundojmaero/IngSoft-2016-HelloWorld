package models;

import java.io.File;

public interface MP3ModelInterface {
	void play();
	void pause();
	void setVolumen();
	void addPlayList(File Path);
	void previousSong();
	void nextSong();
}
