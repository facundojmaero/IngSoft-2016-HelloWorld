package main.java.testDrives;

import main.java.models.*;
import main.java.controllers.*;
import main.java.views.DJView;

public class MyMP3TestDrive4 {
	
	//Este Test Drive permite cambiar entre los 3 modelos usando solo una vista,
	//por medio del patron Strategy. La logica del mismo se encuentra en DJView,
	//quien crea y asigna los nuevos modelo y controlador a medida que elijamos cambiar.
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
