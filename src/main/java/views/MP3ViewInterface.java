package main.java.views;

import main.java.controllers.MP3Controller2;

public interface MP3ViewInterface {

	void setController(MP3Controller2 controller);

	void MakePlayIcon();

	void MakePauseIcon();

	void MakeVolSliderMute();

	void setVisible(boolean b);

}