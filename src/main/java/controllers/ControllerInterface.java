package main.java.controllers;

public interface ControllerInterface {
	void start();

	void stop();

	void increaseBPM();

	void decreaseBPM();

	void setBPM(int bpm);

	void setVolumen(double d);

	void addPlaylist(String path);

	void removeTrack(int selectedIndex);
}
