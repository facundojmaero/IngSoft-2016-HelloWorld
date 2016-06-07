package testDrives;

import models.HeartModel;
import controllers.ControllerInterface;
import controllers.HeartController;

  
public class HeartTestDrive {

    public static void main (String[] args) {
		HeartModel heartModel = new HeartModel();
        ControllerInterface model = new HeartController(heartModel);
    }
}
