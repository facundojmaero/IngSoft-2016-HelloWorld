package main.java.testDrives;

import main.java.models.BeatModel;
import main.java.models.BeatModelInterface;
import main.java.controllers.BeatController;
import main.java.controllers.ControllerInterface;

  
public class DJTestDrive {

    public static void main (String[] args) {
        BeatModelInterface model = new BeatModel();
		ControllerInterface controller = new BeatController(model);
    }
}