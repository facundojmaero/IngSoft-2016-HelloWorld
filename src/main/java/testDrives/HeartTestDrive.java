package main.java.testDrives;

import main.java.controllers.ControllerInterface;
import main.java.controllers.HeartController;
import main.java.models.HeartModel;

public class HeartTestDrive {

	public static void main(String[] args) {
		HeartModel heartModel = new HeartModel();
		ControllerInterface model = new HeartController(heartModel);
	}
}
