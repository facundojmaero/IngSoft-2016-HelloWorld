package main.java.controllers;

import java.awt.image.BufferedImage;

import javax.swing.DefaultListModel;

public interface ControllerInterface {
	void start();

	void stop();

	void increaseBPM();

	void decreaseBPM();

	void setBPM(int bpm);

	void setVolumen(double d);

	void addPlaylist(String path);

	void removeTrack(int selectedIndex);

	BufferedImage getAlbumArt();

	DefaultListModel<String> getSongInfo();
	void removeTracks(int[] selectedIndices);
}
