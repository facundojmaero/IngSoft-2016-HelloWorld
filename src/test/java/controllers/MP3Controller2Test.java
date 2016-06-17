package test.java.controllers;

import main.java.models.MP3ModelInterface;
import main.java.views.MP3View;

public class MP3Controller2Test implements ControllerInterface {
	MP3ModelInterface model;
	MP3View view;

	public MP3Controller2Test(MP3ModelInterface model, MP3View view){
		
	}
	
	@Override
	public void start() {
		
	}

	@Override
	public void stop() {
		
	}

	@Override
	public void increaseBPM() {
		
	}

	@Override
	public void decreaseBPM() {
		
	}

	@Override
	public void setBPM(int bpm) {}
	
	public void setVolumen(double volumen){
	}
	
	public void increaseVolumen(){
	}
	
	public void decreaseVolumen(){
	}
	
	public void pause(){
	}
	
	public void addPlaylist(String Path){
	}

	public void removeTrack(int index) {
		
	}
}
