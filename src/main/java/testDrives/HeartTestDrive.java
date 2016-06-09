package main.java.testDrives;

import main.java.models.HeartModel;
import main.java.controllers.ControllerInterface;
import main.java.controllers.HeartController;

  
public class HeartTestDrive {

    public static void main (String[] args) {
		HeartModel heartModel = new HeartModel();
        ControllerInterface model = new HeartController(heartModel);
    }
}
