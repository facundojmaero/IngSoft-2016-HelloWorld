package main.java.testDrives;

import main.java.controllers.BeatController;
import main.java.controllers.ControllerInterface;
import main.java.models.BeatModel;
import main.java.models.BeatModelInterface;
import main.java.views.DJView;

public class DJTestDrive {

	public static void main(String[] args) {
		BeatModelInterface model = new BeatModel();
		DJView view = new DJView(null,model);
		view.createView();
		view.createControls();
		ControllerInterface controller = new BeatController(model,view);
		view.setController(controller);
	}
}