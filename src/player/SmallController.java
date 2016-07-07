package player;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import player.models.MP3Model;
import player.states.EmptyState;
import player.views.ProgressObserver;
import player.views.TrackObserver;

public class SmallController implements TrackObserver, ProgressObserver {

	@FXML
	private ToggleButton muteToggle;

	@FXML
	private ToggleButton shuffleToggle;

	@FXML
	private Button minimizeButton;

//	@FXML
	private Button previousButton;

	@FXML
	private BorderPane leftPane;

	@FXML
	private ImageView albumArt;

	@FXML
	private Label totalTime;

	@FXML
	private Button playButton;

	@FXML
	private Label currentTime;

	@FXML
	private Button nextButton;

	@FXML
	private Label songLabel;

	@FXML
	private Slider progressSlider;

	@FXML
	private Label artistLabel;

	@FXML
	private ImageView playIcon;

	@FXML
	private ImageView muteButton;

	private MainApp mainApp;
	private MP3Model model;

	Image pauseImg = new Image("file:src/resources/images/pauseicon.png");
	Image playImg = new Image("file:src/resources/images/playicon.png");
	Image repeatAll = new Image("file:src/resources/images/repeat.png");
	Image repeatOne = new Image("file:src/resources/images/repeatOne.png");
	Image dontRepeat = new Image("file:src/resources/images/dontRepeat.png");
	Image mute = new Image("file:src/resources/images/mute.png");
	Image volumeOn = new Image("file:src/resources/images/volume.png");
	Image noAlbumArt = new Image("file:src/resources/images/No-album-art.png");

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	public void setModel(MP3Model instance) {
		model = instance;
	}

	@FXML
	void handleProgressSlide(ActionEvent event) {

	}

	@FXML
	void handleShuffle(ActionEvent event) {
		if (shuffleToggle.isSelected()) {
			model.shuffle();
		} else {
			model.unShuffle();
		}
	}

	@FXML
	void handlePrevious(ActionEvent event) {
		System.out.println("Previous!");
		if (model.getPlaylistSize() == 0) {
			return;
		}
		model.previousSong();
		if (model.IsPlaying()) {
			playIcon.setImage(pauseImg);
		} else {
			playIcon.setImage(playImg);
		}
	}

	@FXML
	void handlePlay(ActionEvent event) {
		System.out.println("Play!");
		if (model.getPlaylistSize() == 0) {
			return;
		}
		if (model.IsPlaying()) {
			model.pause();
			playIcon.setImage(playImg);
		} else {
			model.play();
			playIcon.setImage(pauseImg);
		}
	}

	@FXML
	void handleNext(ActionEvent event) {
		System.out.println("Next!");
		if (model.getPlaylistSize() == 0) {
			return;
		}
		model.nextSong();
		// Si esta reproduciendo habilito la opcion de pausa
		if (model.IsPlaying()) {
			playIcon.setImage(pauseImg);
		}
		// Si no habilito la de play
		else {
			playIcon.setImage(playImg);
		}
	}

	@FXML
	void handleMute(ActionEvent event) {
		System.out.println("Mute!");
		if (muteToggle.isSelected()) {
			model.setVolumen(0);
			muteButton.setImage(mute);
		} else {
			model.setVolumen(1);
			muteButton.setImage(volumeOn);
		}
	}

	@FXML
	void handleMinimize(ActionEvent event) {
		model.removeObserver((ProgressObserver) this);
		model.removeObserver((TrackObserver) this);
		mainApp.changeBigView();
	}

	@FXML
	void initialize() {
		resetView();

		albumArt.fitWidthProperty().bind(leftPane.heightProperty());
		albumArt.fitHeightProperty().bind(leftPane.widthProperty());

		// Escucho cambios en el slider de progreso
		progressSlider.setOnMouseReleased((MouseEvent event) -> {
			model.seek((int) progressSlider.getValue());
		});

		progressSlider.setOnScroll(new EventHandler<ScrollEvent>() {
			@Override
			public void handle(ScrollEvent event) {
				if (event.getDeltaY() > 0) {
					progressSlider.setValue(progressSlider.getValue() + 20);
				} else {
					progressSlider.setValue(progressSlider.getValue() - 20);
				}
				model.seek((int) progressSlider.getValue());
			}
		});
	}

	@Override
	public void updateTrackInfo() {
		songLabel.setText(model.getCurrentTrackName());
		artistLabel.setText(model.getArtist() + " - " + model.getAlbum());
		totalTime.setText(model.getCurrentSongDuration());

		if (model.getState() instanceof EmptyState) {
			return;
		}
		byte[] imageData = model.getAlbumArt();
		if (imageData == null) {
			albumArt.setImage(noAlbumArt);
			return;
		}
		BufferedImage art = null;
		try {
			art = ImageIO.read(new ByteArrayInputStream(imageData));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Image album = SwingFXUtils.toFXImage(art, null);
		albumArt.setImage(album);

	}

	@Override
	public void updatePlaylistInfo() {
	}

	public void registerAsObserver() {
		model.registerObserver((TrackObserver) this);
		model.registerObserver((ProgressObserver) this);
	}

	@Override
	public void updateTrackProgress(int progress, int size) {
		progressSlider.setValue(progress);
		progressSlider.setMax(size);
		int minutes = progress / 60;
		int seconds = progress % 60;
		String a = String.format("%02d:%02d", minutes, seconds);
		currentTime.setText(a);
	}

	public void setPauseButton() {
		playIcon.setImage(pauseImg);
	}

	public void setPlayButton() {
		playIcon.setImage(playImg);
	}

	public void resetView() {
		albumArt.setImage(noAlbumArt);
		artistLabel.setText("Artist");
		songLabel.setText("Song");
		currentTime.setText("00:00");
		totalTime.setText("00:00");
		playIcon.setImage(playImg);
		muteButton.setImage(volumeOn);
		muteToggle.setSelected(false);
		shuffleToggle.setSelected(false);
	}

	public void configureOnViewChange() {
		if(model.IsPlaying()){
			playIcon.setImage(pauseImg);
		}

		if(model.getVolumen()==0){
			muteButton.setImage(mute);
			muteToggle.setSelected(true);
		}

		if(model.isShuffled()){
			shuffleToggle.setSelected(true);
		}
	}
}
