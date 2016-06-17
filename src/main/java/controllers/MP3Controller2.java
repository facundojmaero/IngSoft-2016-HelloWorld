package main.java.controllers;

import main.java.models.MP3ModelInterface;
import main.java.views.MP3View;

public class MP3Controller2 implements ControllerInterface {
	MP3ModelInterface model;
	MP3View view;

	public MP3Controller2(MP3ModelInterface model, MP3View view){
		this.model = model;
		this.view = view;
		view.setVisible(true);
	}
	
	@Override
	public void start() {
		model.play();
		view.MakePauseIcon();
	}

	@Override
	public void stop() {
		model.stop();
		view.MakePlayIcon();
	}

	@Override
	public void increaseBPM() {
		model.nextSong();
		view.MakePauseIcon();
	}

	@Override
	public void decreaseBPM() {
		model.previousSong();
		view.MakePauseIcon();
	}

	@Override
	public void setBPM(int bpm) {}
	
	public void setVolumen(double volumen){
		model.setVolumen(volumen);
	}
	
	public void setTime(long time){
		model.setTime(time);
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
	}
}
