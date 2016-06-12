package main.java.controllers;

import main.java.models.MP3Model;
import main.java.models.MP3ModelInterface;
import main.java.views.DJView;

public class MP3Controller implements ControllerInterface {
	MP3ModelInterface model;
	DJView view;
	
	public MP3Controller(MP3ModelInterface model, DJView view){
		this.model = model;
		this.view = view;
		view.disableStopMenuItem();
		view.enableStartMenuItem();
		model.addPlayList("src/main/resources/default songs");	//playlist por default
	}

	@Override
	public void start() {
		model.play();
		view.disableStartMenuItem();
		view.enableStopMenuItem();
	}

	@Override
	public void stop() {
		model.stop();
		view.enableStartMenuItem();
		view.disableStopMenuItem();
	}

	@Override
	public void increaseBPM() {
		model.nextSong();
		view.disableStartMenuItem();
		view.enableStopMenuItem();

	}

	@Override
	public void decreaseBPM() {
		model.previousSong();
		view.disableStartMenuItem();
		view.enableStopMenuItem();
	}

	@Override
	public void setBPM(int bpm) {
		boolean validacion = model.setIndex(bpm-1);	//boolean para saber si el indice esta dentro de los limites
		if(validacion){
			model.stop();
			model.play();
		}
		else{
			model.stop();
		}

	}
	
	public void setVolumen(double volumen){
		model.setVolumen(volumen);
	}
	
	public void increaseVolumen(){
		double volumen_actual = model.getVolumen();
		model.setVolumen(volumen_actual+0.1);
	}
	
	public void decreaseVolumen(){
		double volumen_actual = model.getVolumen();
		model.setVolumen(volumen_actual-0.1);
	}
	
	public void clearPlaylist(){
		model.clearPlaylist();
	}
}
