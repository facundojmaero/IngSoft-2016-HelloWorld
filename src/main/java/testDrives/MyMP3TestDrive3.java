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

	//Este Test Drive muestra los 3 modelos funcionando al mismo tiempo, en 3 vistas distintas,
	//independientes entre si.
	public static void main(String[] args) {
		
		//Creo el MP3Model con su vista y controlador especifico
		MP3Model model1 = MP3Model.getInstance();
		DJView mp3View = new DJView(null,new MP3Adapter(model1)); 
		mp3View.createView();
		mp3View.createControls();
		MP3Controller controller1 = new MP3Controller(model1, mp3View);
		mp3View.setController(controller1);
		
		//Creo el BeatModel con su vista y controlador especifico
		BeatModelInterface model2 = new BeatModel();
		DJView djview = new DJView(null,model2);
		djview.createView();
		djview.createControls();
		ControllerInterface controller2 = new BeatController(model2,djview);
		djview.setController(controller2);
		
		//Creo el HeartModel con su vista y controlador especifico
		HeartModel heartModel = HeartModel.getInstance();
		DJView heartView = new DJView(null,new HeartAdapter(heartModel));
		heartView.createView();	
		heartView.createControls();
		ControllerInterface controller = new HeartController(heartModel,heartView);
		heartView.setController(controller);
	}

}

