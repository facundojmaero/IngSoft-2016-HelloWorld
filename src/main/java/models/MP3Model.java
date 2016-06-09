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
	private boolean stopped;
	
	public MP3Model(){
		this.player = new BasicPlayer();
		this.bpm_observers = new ArrayList<BPMObserver>();
		this.beat_observers = new ArrayList<BeatObserver>();
		this.index = 0;	
		this.stoped = true;		    //stop comienza true
		this.paused = false;		//pausado comienza como false
		this.opened = false;		//ningun archivo abierto
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
		try {
			player.pause();
		} catch (BasicPlayerException e) {
			e.printStackTrace();
		}
		paused = true;
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
			stopped = true;
			index = (index+1)%playlist.size();
			try {
				player.play();
			} catch (BasicPlayerException e) {
				e.printStackTrace();
			}
	}

	@Override
	public void setVolumen(double volumen) {
		if(volumen<0 || volumen>1){
			return;
		}
		else{
			try {
				player.setGain(volumen);
			} catch (BasicPlayerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
