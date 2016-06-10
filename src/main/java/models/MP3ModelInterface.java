package main.java.models;

import java.io.File;

import main.java.views.BPMObserver;
import main.java.views.BeatObserver;

public interface MP3ModelInterface {
	void play();

	void pause();

	void setVolumen(double volumen);

	void addPlayList(String Path);

	void previousSong();

	void nextSong();
	
	void stop();
	
	void registerObserver(BeatObserver o);

	void removeObserver(BeatObserver o);

	void registerObserver(BPMObserver o);

	void removeObserver(BPMObserver o);
	
	double getVolumen();
	
	void setIndex(int index);
	
	int getIndex();
}