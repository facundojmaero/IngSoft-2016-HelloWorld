package main.java.testDrives;

import main.java.controllers.MP3Controller;
import main.java.models.BeatModelInterface;
import main.java.models.MP3Adapter;
import main.java.models.MP3Model;
import main.java.models.MP3ModelInterface;
import main.java.views.DJView;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MP3Model model = new MP3Model();
		MP3Controller controller = new MP3Controller(model);
		DJView view = new DJView(controller,new MP3Adapter(model));
		view.createView();
		view.createControls();
	}

}
