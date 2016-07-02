package main.java.controllers;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;

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
		if(model.getPlaylistSize()==0){
			return;
		}
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
		if(model.getPlaylistSize()==0){
			return;
		}
		model.stop();
		view.MakePlayIcon();
	}

	@Override
	public void increaseBPM() {
		if(model.getPlaylistSize()==0){
			return;
		}
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
		if(model.getPlaylistSize()==0){
			return;
		}
		model.previousSong();
		if(model.IsPlaying()){
			view.MakePauseIcon();
		}
		else{
			view.MakePlayIcon();
		}
	}

	public void setBPM(int bpm) {
		if(model.getPlaylistSize()==0){
			return;
		}
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
		if(model.getPlaylistSize()==0){
			return;
		}
		model.pause();
		view.MakePlayIcon();
	}

	public void addPlaylist(String Path){
		model.addPlayList(Path);
	}

	public void removeTrack(int index) {}

	public void removeTracks(int[] indexes) {
		for (int i = indexes.length-1; i >= 0; i--) {
			model.removePlayList(indexes[i]);
		}
		if (model.IsPlaying()){
			view.MakePauseIcon(); //si esta reproduciendo actualizo la vista p/ que muestre el icono pausa
		}
		else{
			view.MakePlayIcon(); //si no muestro la opcion play
		}
	}

	public void toggleRepeatBehavior(){
		model.toggleRepeatState();
	}

	@Override
	public BufferedImage getAlbumArt() {
		byte[] imageData = model.getAlbumArt();
		BufferedImage img = null;
		try {
			img = ImageIO.read(new ByteArrayInputStream(imageData));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}

	@Override
	public DefaultListModel<String> getSongInfo() {
		return model.getSongInfo();
	}
}
