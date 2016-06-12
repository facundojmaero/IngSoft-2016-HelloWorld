package main.java.states;

import main.java.models.MP3Model;

public class EmptyState implements MP3State {
	
	MP3Model model;
	
	public EmptyState(MP3Model model){
		this.model = model;
	}

	@Override
	public void play() {
		// TODO Auto-generated method stub

	}

	@Override
	public void paused() {
		// TODO Auto-generated method stub

	}

	@Override
	public void addPlaylist(String path) {
		// TODO Auto-generated method stub

	}

	@Override
	public void nextSong() {
		// TODO Auto-generated method stub

	}

	@Override
	public void previousSong() {
		// TODO Auto-generated method stub

	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}

}
