package main.java.controllers;

import java.awt.image.BufferedImage;

import javax.swing.DefaultListModel;

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
		view.disableDJStartMenuItem();
		view.disableHeartStartMenuItem();
		view.disableMP3StartMenuItem();
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

	@Override
	public void addPlaylist(String path) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeTrack(int selectedIndex) {
		// TODO Auto-generated method stub

	}

	@Override
	public BufferedImage getAlbumArt() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DefaultListModel<String> getSongInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeTracks(int[] selectedIndices) {
		// TODO Auto-generated method stub

	}
}
