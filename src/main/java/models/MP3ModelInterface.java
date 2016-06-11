package main.java.models;

import java.io.File;

import main.java.views.BPMObserver;
import main.java.views.BeatObserver;
import main.java.views.TrackObserver;

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
	
	boolean setIndex(int index);
	
	int getIndex();
	
	void registerObserver(TrackObserver o);
	
	void removeObserver(TrackObserver o);
}