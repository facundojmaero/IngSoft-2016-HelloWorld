package main.java.states;

import main.java.models.MP3Model;

public class StoppedState implements MP3State {
	
	MP3Model model;
	
	public StoppedState(MP3Model model){
		this.model = model;
	}

	@Override
	public void play() {
		model.playNow(model.getIndex());
		model.setState(model.getPlayingState());
	}

	@Override
	public void nextSong() {
		model.setIndex((model.getIndex()+1)%model.getPlaylistSize());
		model.playNow(model.getIndex());
		model.setState(model.getPlayingState());
	}

	@Override
	public void previousSong() {
		model.setIndex((model.getIndex()-1)%model.getPlaylistSize());
		if(model.getIndex()<0){
			model.setIndex(model.getPlaylistSize()-1);
		}
		model.playNow(model.getIndex());
		model.setState(model.getPlayingState());
	}
	
	//Los siguientes metodos no realizan ninguna accion en el estadoa actual
	@Override
	public void stop() {}
	
	@Override
	public void paused() {}

	@Override
	public void addPlaylist() {}

}
