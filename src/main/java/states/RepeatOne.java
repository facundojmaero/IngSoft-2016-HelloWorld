package main.java.states;

import main.java.models.MP3Model;

public class RepeatOne implements RepeatInterface{

	MP3Model model;

	public RepeatOne(MP3Model newModel) {
		model = newModel;
	}

	@Override
	public void songFinished() {
		model.restartCurrentSong();
	}

	@Override
	public void toggleRepeatState() {
		model.setRepeatState(model.getDontRepeat());
	}

}
