package main.java.models;

import java.io.File;
import java.util.ArrayList;

import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;
import main.java.views.BPMObserver;
import main.java.views.BeatObserver;

public class MP3Model implements MP3ModelInterface {
	private BasicPlayer player;
	private ArrayList<String> playlist;
	private ArrayList<BPMObserver> bpm_observers;
	private ArrayList<BeatObserver> beat_observers;
	private int index;
	private boolean paused;
	private boolean stoped;
	private boolean opened;
	
	public MP3Model(){
		player = new BasicPlayer();
		playlist = new ArrayList<String>();
		bpm_observers = new ArrayList<BPMObserver>();
		beat_observers = new ArrayList<BeatObserver>();
		index = 0;	
		stoped = true;		//stop comienza true
		paused = false;		//pausado comienza como false
		opened = false;		//ningun archivo abierto
	}

	@Override
	public void play() {
		if(playlist.size() == 0){
			return;
		}
		
		if(stoped){
			File f = new File(playlist.get(index));
			try {
				player.open(f);
			} catch (BasicPlayerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			opened = true;
			try {
				player.play();
			} catch (BasicPlayerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(paused && opened){
			try {
				player.resume();
			} catch (BasicPlayerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			paused = false;

		}
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
