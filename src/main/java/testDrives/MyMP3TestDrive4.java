package main.java.testDrives;

import main.java.models.*;
import main.java.controllers.*;
import main.java.views.DJView;

public class MyMP3TestDrive4 {

	public static void main(String[] args) {
		BeatModelInterface beatModel = new BeatModel();
		HeartModel heartModel = HeartModel.getInstance();
		MP3Model mp3Model = MP3Model.getInstance();
		
		DJView view = new DJView(null,beatModel);
		view.createView();
		view.createControls();
		
		ControllerInterface djController = new BeatController(beatModel,view);
		view.setController(djController);
		view.enableDJStartMenuItem();
		view.enableHeartStartMenuItem();
		view.enableMP3StartMenuItem();
		
	}

}
