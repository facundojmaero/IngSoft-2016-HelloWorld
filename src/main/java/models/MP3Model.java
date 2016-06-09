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
	private boolean opened;
	private boolean stopped;
	
	public MP3Model(){
		player = new BasicPlayer();
		playlist = new ArrayList<String>();
		bpm_observers = new ArrayList<BPMObserver>();
		beat_observers = new ArrayList<BeatObserver>();
		index = 0;
		paused = false;
		opened = false;
		stopped = true;
	}

	@Override
	public void play() {
		
	}

	@Override
	public void pause() {
		try {
			player.pause();
		} catch (BasicPlayerException e) {
			e.printStackTrace();
		}
		paused = true;
	}

	@Override
	public void setVolumen() {
		
	}

	@Override
	public void addPlayList(File Path) {
//		if(new File(Path).isFile()){		//Si la ruta es a una sola cancion
//			playlist.add(Path);
//		}
//		else{								//Si la ruta es a una carpeta con canciones
//			
//		}
	}

	@Override
	public void previousSong() {
		if(playlist.size() == 0)
			return;
			paused = false;
			stopped = false;
			index = (index-1)%playlist.size();
			try {
				player.play();
			} catch (BasicPlayerException e) {
				e.printStackTrace();
			}
	}

	@Override
	public void nextSong() {
		if(playlist.size() == 0)
			return;
			paused = false;
			stopped = false;
			index = (index+1)%playlist.size();
			try {
				player.play();
			} catch (BasicPlayerException e) {
				e.printStackTrace();
			}
	}
}
