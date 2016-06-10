package main.java.testDrives;

import main.java.controllers.ControllerInterface;
import main.java.controllers.HeartController;
import main.java.models.HeartModel;

public class HeartTestDrive {

	public static void main(String[] args) {
		HeartModel heartModel = HeartModel.getInstance();
		ControllerInterface controller = new HeartController(heartModel);
	}
}
