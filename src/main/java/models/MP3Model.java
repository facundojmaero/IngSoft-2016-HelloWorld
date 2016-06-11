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
	private ArrayList<BPMObserver> bpmObservers;
	private ArrayList<BeatObserver> beatObservers;
	private int index;
	private double volumen;
	private boolean paused;
	private boolean opened;
	private boolean stopped;
	
	
	public MP3Model(){
		this.player = new BasicPlayer();
		this.bpmObservers = new ArrayList<BPMObserver>();
		this.beatObservers = new ArrayList<BeatObserver>();
		this.index = 0;	
		this.volumen = 1;			//maximo volumen
		this.stopped = true;		    //stop comienza true
		this.paused = false;		//pausado comienza como false
		this.opened = false;		//ningun archivo abierto
		this.playlist = new ArrayList<String>();
		//playlist por default
//		this.addPlayList("default songs");
		this.addPlayList("src\\main\\resources");
	}

	@Override
	public void play() {
		if(playlist.size() == 0){
			return;
		}
		
		if(stopped){
			File f = new File(playlist.get(index));
			try {
				player.open(f);
			} catch (BasicPlayerException e) {
				e.printStackTrace();
			}
			try {
				player.play();
			} catch (BasicPlayerException e) {
				e.printStackTrace();
			}
			opened = true; //el archivo fue abierto
			stopped = false; //salimos del estado stoped
			this.notifyBPMObservers();
		}
		if(paused && opened){
			try {
				player.resume();
			} catch (BasicPlayerException e) {
				e.printStackTrace();
			}
			paused = false;

		}
		//Seteo la ganancia aca para que se pueda subir el volumen antes de dar play y el
		//reproductor lo entienda
		try {
			player.setGain(volumen);
		} catch (BasicPlayerException e) {
			e.printStackTrace();
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
	public void addPlayList(String Path) {
		File file = new File(Path);				// Uso la ruta para crear un nuevo File
		if (file.isFile()) { 					// Si la ruta es una sola cancion
			playlist.add(Path);
		} else { 								// Si la ruta es una carpeta con canciones
			File list[] = file.listFiles();
			if (list != null) {
				for (int i = 0; i < list.length; i++) {
					playlist.add(list[i].getAbsolutePath());
				}
			}
		}
	}

	@Override
	public void previousSong() {
		if(playlist.size() == 0)
			return;
			paused = false;
			stopped = true;
			index = (index-1)%playlist.size();
			if(index<0){
				index = playlist.size()-1;
			}
			this.play();
	}

	@Override
	public void nextSong() {
		if(playlist.size() == 0)
			return;
			paused = false;
			stopped = true;
			index = (index+1)%playlist.size();
			this.play();
	}

	@Override
	public void stop() {
		if(opened){
			try {
				player.stop();
			} catch (BasicPlayerException e) {
				e.printStackTrace();
			}
			stopped = true;
			paused = false;
		}
	}

	@Override
	public void setVolumen(double volumen) {
		if(volumen<0 || volumen>1){
			return;
		}
		else{
			this.volumen = volumen;
		}

	}
	
	public double getVolumen(){
		return volumen;
	}
	
	public void registerObserver(BPMObserver o) {
		bpmObservers.add(o);
	}

	public void notifyBPMObservers() {
		for (int i = 0; i < bpmObservers.size(); i++) {
			BPMObserver observer = (BPMObserver) bpmObservers.get(i);
			observer.updateBPM();
		}
	}

	public void removeObserver(BeatObserver o) {
		int i = beatObservers.indexOf(o);
		if (i >= 0) {
			beatObservers.remove(i);
		}
	}

	public void removeObserver(BPMObserver o) {
		int i = bpmObservers.indexOf(o);
		if (i >= 0) {
			bpmObservers.remove(i);
		}
	}

	public void registerObserver(BeatObserver o) {
		beatObservers.add(o);	
	}
	
	public boolean setIndex (int index){
		if ( index > (playlist.size()-1) || index < 0)
			return false;
		this.index = index;
		return true;
	}

	@Override
	public int getIndex() {
		return index;
	}
}
