package main.java.testDrives;

import main.java.controllers.ControllerInterface;
import main.java.controllers.HeartController;
import main.java.models.HeartAdapter;
import main.java.models.HeartModel;
import main.java.views.DJView;

public class HeartTestDrive {

	public static void main(String[] args) {
		HeartModel heartModel = HeartModel.getInstance();
		DJView view = new DJView(null,new HeartAdapter(heartModel));
		view.createView();
        view.createControls();
		ControllerInterface controller = new HeartController(heartModel,view);
		view.setController(controller);
	}
}
