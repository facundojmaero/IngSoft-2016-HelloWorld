package main.java.testDrives;

import main.java.controllers.BeatController;
import main.java.controllers.ControllerInterface;
import main.java.controllers.HeartController;
import main.java.controllers.MP3Controller;
import main.java.models.BeatModel;
import main.java.models.BeatModelInterface;
import main.java.models.HeartAdapter;
import main.java.models.HeartModel;
import main.java.models.MP3Adapter;
import main.java.models.MP3Model;
import main.java.views.DJView;

public class MyMP3TestDrive3 {

	public static void main(String[] args) {
		
		MP3Model model1 = MP3Model.getInstance();									//creo mp3model
		DJView mp3View = new DJView(null,new MP3Adapter(model1));				//con su vista y 
		mp3View.createView();													//controlador
		mp3View.createControls();
		MP3Controller controller1 = new MP3Controller(model1, mp3View);
		mp3View.setController(controller1);
		
		BeatModelInterface model2 = new BeatModel();						//creo beatmodel
		DJView djview = new DJView(null,model2);
		djview.createView();													//controlador
		djview.createControls();
		ControllerInterface controller2 = new BeatController(model2,djview);		//el controlador crea
		djview.setController(controller2);																	//la vista
		
		
		HeartModel heartModel = HeartModel.getInstance();
		DJView heartView = new DJView(null,new HeartAdapter(heartModel));
		heartView.createView();													//controlador
		heartView.createControls();
		ControllerInterface controller = new HeartController(heartModel,heartView);
		heartView.setController(controller);
	}

}

