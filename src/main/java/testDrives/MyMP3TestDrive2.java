package main.java.testDrives;

import main.java.controllers.MP3Controller2;
import main.java.models.MP3Model;
import main.java.views.MP3View;

public class MyMP3TestDrive2 {

	public static void main(String[] args) {
		MP3Model model = new MP3Model();
		MP3View view = new MP3View(model);
		MP3Controller2 controller = new MP3Controller2(model,view);
		view.setController(controller);
	}
}
