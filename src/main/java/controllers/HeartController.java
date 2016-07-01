package main.java.controllers;

import main.java.views.DJView;
import main.java.models.HeartModel;
import main.java.models.HeartModelInterface;


public class HeartController implements ControllerInterface {
	HeartModelInterface model;
	DJView view;

	public HeartController(HeartModelInterface model,DJView view) {
		this.model = model;
		this.view = view;
		view.disableStopMenuItem();
		view.disableStartMenuItem();
		view.disableDJStartMenuItem();
		view.disableHeartStartMenuItem();
		view.disableMP3StartMenuItem();
	}

	public void start() {}

	public void stop() {}

	public void increaseBPM() {
		HeartModel.getInstance();
	}

	public void decreaseBPM() {}

 	public void setBPM(int bpm) {}

	@Override
	public void setVolumen(double d) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addPlaylist(String path) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeTrack(int selectedIndex) {
		// TODO Auto-generated method stub

	}
}



