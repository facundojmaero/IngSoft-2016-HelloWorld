package main.java.states;

import main.java.models.MP3Model;

public class EmptyState implements MP3State {
	
	MP3Model model;
	
	public EmptyState(MP3Model model){
		this.model = model;
	}
	
	@Override
	public void addPlaylist(String path) {
		model.addPlayListPath(path);	//Añado la playlist
		model.setState(model.getStoppedState()); //y paso siempre a stopped
	}
	
	//Los siguientes metodos no realizan ninguna accion en el estado actual
	@Override
	public void play() {}

	@Override
	public void paused() {}

	@Override
	public void nextSong() {}

	@Override
	public void previousSong() {}

	@Override
	public void stop() {}

}
