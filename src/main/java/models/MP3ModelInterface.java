package main.java.models;

import java.io.File;

public interface MP3ModelInterface {
	void play();

	void pause();

	void setVolumen();

	void addPlayList(String Path);

	void previousSong();

	void nextSong();
	
	void stop();
}
