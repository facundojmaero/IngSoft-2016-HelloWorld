package main.java.controllers;

import main.java.models.HeartAdapter;
import main.java.models.HeartModelInterface;
import main.java.views.DJView;

public class HeartController implements ControllerInterface {
	HeartModelInterface model;
	DJView view;

	public HeartController(HeartModelInterface model) {
		this.model = model;
		view = new DJView(this, new HeartAdapter(model));
		view.createView();
		view.createControls();
		view.disableStopMenuItem();
		view.disableStartMenuItem();
	}

	public void start() {
	}

	public void stop() {
	}

	public void increaseBPM() {
	}

	public void decreaseBPM() {
	}

	public void setBPM(int bpm) {
	}
}
