package main.java.testDrives;

import main.java.controllers.BeatController;
import main.java.controllers.ControllerInterface;
import main.java.controllers.HeartController;
import main.java.controllers.MP3Controller;
import main.java.models.BeatModel;
import main.java.models.BeatModelInterface;
import main.java.models.HeartModel;
import main.java.models.MP3Adapter;
import main.java.models.MP3Model;
import main.java.views.DJView;

public class MyMP3TestDrive3 {

	public static void main(String[] args) {
		
		MP3Model model1 = new MP3Model();									//creo mp3model
		DJView view = new DJView(null,new MP3Adapter(model1));				//con su vista y 
		view.createView();													//controlador
		view.createControls();
		MP3Controller controller1 = new MP3Controller(model1, view);
		view.setController(controller1);
		
		BeatModelInterface model2 = new BeatModel();						//creo beatmodel
		ControllerInterface controller2 = new BeatController(model2);		//el controlador crea
																			//la vista
		HeartModel heartModel = HeartModel.getInstance();					//creo heartmodel
		ControllerInterface controller = new HeartController(heartModel);	//el controlador crea
	}																		//la vista

}

