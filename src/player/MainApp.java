package player;

import java.io.IOException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import player.models.MP3Model;

public class MainApp extends Application {

	private MP3Model model;
	private Stage myStage;
	private String darkThemeURL = getClass().getResource("views/DarkTheme.css").toExternalForm();
    private String lightThemeURL = getClass().getResource("views/LightTheme.css").toExternalForm();
    private String currentThemeURL = darkThemeURL;
    private Scene scene;

	@Override
	public void start(Stage stage) throws Exception {
		myStage = stage;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("views/bigPlayer.fxml"));

		Pane pane = (Pane) loader.load();
		stage.setTitle("MP3 Player - v2.0");
		stage.initStyle(StageStyle.UTILITY);
		scene = new Scene(pane);
		scene.getStylesheets().add(darkThemeURL);
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
		return myStage;
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
		scene = new Scene(pane);
		scene.getStylesheets().add(currentThemeURL);
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
		scene = new Scene(pane);
		scene.getStylesheets().add(currentThemeURL);
		myStage.setScene(scene);
		myStage.setResizable(true);
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

	public void changeMiniView() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("views/miniPlayer.fxml"));
		Pane pane = null;
		try {
			pane = (Pane) loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		myStage.setTitle("MP3 Player - v2.0");
		scene = new Scene(pane);
		scene.getStylesheets().add(currentThemeURL);
		myStage.setScene(scene);
		myStage.setResizable(false);
		myStage.show();
		MiniController miniController = loader.<MiniController> getController();

		model = MP3Model.getInstance();
		miniController.setMainApp(this);
		miniController.setModel(model);
		miniController.registerAsObserver();
		miniController.updateTrackInfo();

		miniController.configureOnViewChange();
	}

	public void switchTheme(){
		scene.getStylesheets().remove(currentThemeURL);
		if(currentThemeURL.matches(darkThemeURL)){
			currentThemeURL = lightThemeURL;
			scene.getStylesheets().add(currentThemeURL);
		}
		else{
			currentThemeURL = darkThemeURL;
			scene.getStylesheets().add(currentThemeURL);
		}
	}
}