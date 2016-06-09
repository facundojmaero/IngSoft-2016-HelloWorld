package main.java.models;

import java.io.File;
import java.util.ArrayList;

import javazoom.jlgui.basicplayer.BasicPlayer;
import main.java.views.BPMObserver;
import main.java.views.BeatObserver;

public class MP3Model implements MP3ModelInterface {
	private BasicPlayer player;
	private ArrayList<String> playlist;
	private ArrayList<BPMObserver> bpm_observers;
	private ArrayList<BeatObserver> beat_observers;
	private int index;
	private boolean paused;
	private boolean opened;
	
	public MP3Model(){
		player = new BasicPlayer();
		playlist = new ArrayList<String>();
		bpm_observers = new ArrayList<BPMObserver>();
		beat_observers = new ArrayList<BeatObserver>();
		index = 0;
		paused = true;
		opened = false;
	}

	@Override
	public void play() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setVolumen() {
		// TODO Auto-generated method stub

	}

	@Override
	public void addPlayList(File Path) {
		// TODO Auto-generated method stub

	}

	@Override
	public void previousSong() {
		// TODO Auto-generated method stub

	}

	@Override
	public void nextSong() {
		// TODO Auto-generated method stub

	}

}
