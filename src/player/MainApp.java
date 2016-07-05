package player;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import player.models.MP3Model;

public class MainApp extends Application {

	private Stage primaryStage;
	private MP3Model model;

	@Override
	public void start(Stage stage) throws Exception {

		FXMLLoader loader = new FXMLLoader(getClass().getResource("views/bigPlayer.fxml"));
		Pane pane = (Pane) loader.load();
		stage.setTitle("MP3 Player - v2.0");
		Scene scene = new Scene(pane);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
		BigController controller = loader.<BigController> getController();

		model = MP3Model.getInstance();
//		model.addPlayList("E:/Music/Avicii/Avicii - Stories [2015]");
		model.addPlayList("E:/Music/Chet Faker - Thinking In Textures (EP) (2012)");
//		model.addPlayList("E:/Music/Avicii/Avicii - Stories [2015]/01. Waiting For Love.mp3");
		controller.setMainApp(this);
		controller.setModel(model);
		controller.registerAsObserver();

		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
				Platform.exit();
				System.exit(0);
			}
		});
	}


	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void main(String[] args) {
		launch(args);
	}
}