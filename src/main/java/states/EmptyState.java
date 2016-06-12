package main.java.states;

import java.io.File;

import main.java.models.MP3Model;

public class EmptyState implements MP3State {
	
	MP3Model model;
	
	public EmptyState(MP3Model model){
		this.model = model;
	}

	@Override
	public void play() {
		// No hago nada

	}

	@Override
	public void paused() {
		// No hago anda

	}

	@Override
	public void addPlaylist() {
		model.setState(model.getStoppedState());

	@Override
	public void nextSong() {
		// No hago nada

	}

	@Override
	public void previousSong() {
		// No hago nada

	}

	@Override
	public void stop() {
		// No hago nada

	}

}
