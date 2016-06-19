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
		model.play();
//		model.playNow(model.getIndex());
//		model.setState(model.getPlayingState());
	}

	@Override
	public void previousSong() {
		int index = (model.getIndex()-1)%model.getPlaylistSize();
		if(index < 0){
			index = model.getPlaylistSize()-1;
		}
		model.setIndex(index);
		model.play();
//		model.playNow(model.getIndex());
//		model.setState(model.getPlayingState());
	}

	@Override
	public void addPlaylist(String path) {
		model.addPlayListPath(path);
		if(model.getPlaylistSize() > 0){
			if(path.endsWith(".mp3")){
				model.setIndex(model.getPlaylist().indexOf(path));
			}
			model.play();
		}
	}
	
	//Los siguientes metodos no realizan ninguna accion en el estadoa actual
	@Override
	public void stop() {}
	
	@Override
	public void paused() {}

}
