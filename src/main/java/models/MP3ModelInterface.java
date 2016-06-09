package main.java.models;

import java.io.File;

public interface MP3ModelInterface {
	void play();

	void pause();

	void setVolumen(double volumen);

	void addPlayList(File Path);

	void previousSong();

	void nextSong();
}
