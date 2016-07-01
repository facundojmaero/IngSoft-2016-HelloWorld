package main.java.controllers;

import main.java.models.MP3ModelInterface;
import main.java.views.MP3View;
import main.java.views.MP3ViewInterface;

public class MP3Controller2 implements ControllerInterface {
	MP3ModelInterface model;
	MP3ViewInterface view;

	public MP3Controller2(MP3ModelInterface model, MP3View view){
		this.model = model;
		this.view = view;
		view.setVisible(true);
	}

	@Override
	public void start() {
		if(model.IsPlaying()){
			model.pause();
			view.MakePlayIcon();
		}
		else{
			model.play();
			view.MakePauseIcon();
		}
	}

	@Override
	public void stop() {
		model.stop();
		view.MakePlayIcon();
	}

	@Override
	public void increaseBPM() {
		model.nextSong();
		// Si esta reproduciendo habilito la opcion de pausa
		if(model.IsPlaying()){
			view.MakePauseIcon();
		}
		// Si no habilito la de play
		else{
			view.MakePlayIcon();
		}
	}

	@Override
	public void decreaseBPM() {
		model.previousSong();
		if(model.IsPlaying()){
			view.MakePauseIcon();
		}
		else{
			view.MakePlayIcon();
		}
	}

	public void setBPM(int bpm) {
		if(bpm<0){
			return;
		}
		model.stop();
		model.setIndex(bpm);
		start();
	}

	public void setVolumen(double volumen){
		model.setVolumen(volumen);
		if(volumen == 0){
			view.MakeVolSliderMute();  //Si se apreto el boton mute(vol=0) actualizo la barra
		}
	}


	public void pause(){
		model.pause();
		view.MakePlayIcon();
	}

	public void addPlaylist(String Path){
		model.addPlayList(Path);
	}

	public void removeTrack(int index) {
		model.removePlayList(index);
		if (model.IsPlaying()){
			view.MakePauseIcon(); //si esta reproduciendo actualizo la vista p/ que muestre el icono pausa
		}
		else{
			view.MakePlayIcon(); //si no muestro la opcion play
		}
	}
}
