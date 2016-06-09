package testDrives;

import models.BeatModel;
import models.BeatModelInterface;
import controllers.BeatController;
import controllers.ControllerInterface;

  
public class DJTestDrive {

    public static void main (String[] args) {
        BeatModelInterface model = new BeatModel();
		ControllerInterface controller = new BeatController(model);
    }
}