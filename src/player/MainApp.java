package player;

import java.io.IOException;
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
	private Stage myStage;

	@Override
	public void start(Stage stage) throws Exception {
		myStage = stage;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("views/bigPlayer.fxml"));

		Pane pane = (Pane) loader.load();
		stage.setTitle("MP3 Player - v2.0");
		Scene scene = new Scene(pane);
		stage.setScene(scene);
		stage.show();
		BigController bigController = loader.<BigController> getController();

		model = MP3Model.getInstance();
		model.addPlayList("E:/Music/Chet Faker - Thinking In Textures (EP) (2012)");
		bigController.setMainApp(this);
		bigController.setModel(model);
		bigController.registerAsObserver();

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

	public Stage getStage() {
		return myStage;
	}

	public void changeSmallView() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("views/smallPlayer.fxml"));
		Pane pane = null;
		try {
			pane = (Pane) loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		myStage.setTitle("MP3 Player - v2.0");
		Scene scene = new Scene(pane);
		myStage.setScene(scene);
		myStage.setResizable(false);
		myStage.show();
		SmallController smallController = loader.<SmallController> getController();

		model = MP3Model.getInstance();
		smallController.setMainApp(this);
		smallController.setModel(model);
		smallController.registerAsObserver();
		smallController.updateTrackInfo();

		smallController.configureOnViewChange();
	}

	public void changeBigView() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("views/bigPlayer.fxml"));
		Pane pane = null;
		try {
			pane = (Pane) loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		myStage.setTitle("MP3 Player - v2.0");
		Scene scene = new Scene(pane);
		myStage.setScene(scene);
		myStage.setResizable(false);
		myStage.show();
		BigController bigController = loader.<BigController> getController();

		model = MP3Model.getInstance();
		bigController.setMainApp(this);
		bigController.setModel(model);
		bigController.registerAsObserver();

		bigController.updateTrackInfo();
		bigController.updatePlaylistInfo();

		bigController.configureOnViewChange();
		getStage().setResizable(true);
	}
}