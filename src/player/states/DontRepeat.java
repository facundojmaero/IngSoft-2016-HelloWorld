package player.states;

import player.models.MP3Model;

public class DontRepeat implements RepeatInterface {

	MP3Model model;

	public DontRepeat (MP3Model newModel){
		model = newModel;
	}

	@Override
	public void songFinished() {
		if(model.getIndex()==model.getPlaylistSize()-1){
			model.setIndex(0);
			model.stop();
			model.notifyTrackObservers();
		}
		else{
			model.nextSong();
		}
	}

	@Override
	public void toggleRepeatState() {
		model.setRepeatState(model.getRepeatAll());
	}

}
