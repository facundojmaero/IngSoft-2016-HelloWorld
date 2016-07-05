package player.states;

import player.models.MP3Model;

public class RepeatAll implements RepeatInterface {

	MP3Model model;

	public RepeatAll(MP3Model newModel) {
		model = newModel;
	}

	@Override
	public void songFinished() {
		model.nextSong();
	}

	@Override
	public void toggleRepeatState() {
		model.setRepeatState(model.getRepeatOne());
	}

}
