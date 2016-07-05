package player;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import player.models.MP3Model;
import player.states.EmptyState;
import player.views.ProgressObserver;
import player.views.TrackObserver;

public class BigController implements TrackObserver, ProgressObserver{

	 	@FXML
	    private ToggleButton muteToggle;

	    @FXML
	    private ToggleButton shuffleToggle;

	    @FXML
	    private Button minimizeButton;

	    @FXML
	    private Button deleteButton;

	    @FXML
	    private Button previousButton;

	    @FXML
	    private ImageView albumArt;

	    @FXML
	    private Label totalTime;

	    @FXML
	    private Button addButton;

	    @FXML
	    private ListView<String> songList;
	    private ObservableList<String> playList = FXCollections.observableArrayList();

	    @FXML
	    private Button playButton;

	    @FXML
	    private Button repeatButton;

	    @FXML
	    private Label currentTime;

	    @FXML
	    private Button nextButton;

	    @FXML
	    private Label songLabel;

	    @FXML
	    private Slider volumeSlider;

	    @FXML
	    private Slider progressSlider;

	    @FXML
	    private Label artistLabel;

	    @FXML
	    private ImageView playIcon;

	    @FXML
	    private ImageView muteButton;

	    @FXML
	    private ImageView repeatImage;

    private MainApp mainApp;
	private MP3Model model;

	Image pauseImg = new Image("file:src/resources/images/pauseicon.png");
	Image playImg = new Image("file:src/resources/images/playicon.png");
	Image repeatAll = new Image ("file:src/resources/images/repeat.png");
	Image repeatOne = new Image ("file:src/resources/images/repeatOne.png");
	Image dontRepeat = new Image ("file:src/resources/images/dontRepeat.png");
	Image mute = new Image ("file:src/resources/images/mute.png");
	Image volumeOn = new Image ("file:src/resources/images/volume.png");
	Image noAlbumArt = new Image ("file:src/resources/images/No-album-art.png");

	private double volumeAux;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

	public void setModel(MP3Model instance) {
		model = instance;
	}

    @FXML
    void handlePrevious(ActionEvent event) {
    	System.out.println("Previous!");
		if(model.getPlaylistSize()==0){
			return;
		}
		model.previousSong();
		if(model.IsPlaying()){
			playIcon.setImage(pauseImg);
		}
		else{
			playIcon.setImage(playImg);
		}
    }

    @FXML
    void handlePlay(ActionEvent event) {
    	System.out.println("Play!");
    	if(model.getPlaylistSize()==0){
			return;
		}
		if(model.IsPlaying()){
			model.pause();
			playIcon.setImage(playImg);
		}
		else{
			model.play();
			playIcon.setImage(pauseImg);
		}
    }

    @FXML
    void handleNext(ActionEvent event) {
    	System.out.println("Next!");
		if(model.getPlaylistSize()==0){
			return;
		}
		model.nextSong();
		String a = "-> " + playList.get(model.getIndex());
		playList.set(model.getIndex(), a);
		// Si esta reproduciendo habilito la opcion de pausa
		if(model.IsPlaying()){
			playIcon.setImage(pauseImg);
		}
		// Si no habilito la de play
		else{
			playIcon.setImage(playImg);
		}
    }

    @FXML
    void handleMute(ActionEvent event) {
    	System.out.println("Mute!");
    	if(muteToggle.isSelected()){
    		model.setVolumen(0);
    		volumeAux = volumeSlider.getValue();
    		volumeSlider.setValue(0);
    		muteButton.setImage(mute);
    	}
    	else{
    		model.setVolumen(volumeAux/100);
    		volumeSlider.setValue(volumeAux);
    		muteButton.setImage(volumeOn);
    	}
    }

    @FXML
    void handleProgressSlide(ActionEvent event) {}

    @FXML
    void handleShuffle(ActionEvent event) {
    	if(shuffleToggle.isSelected()){
    		model.shuffle();
    	}
    	else{
    		model.unShuffle();
    	}
    }

    @FXML
    void handleRepeat(ActionEvent event) {
    	if(model.getRepeatState() == model.getDontRepeat()){
    		repeatImage.setImage(repeatAll);
    	}
    	else if (model.getRepeatState() == model.getRepeatAll()){
    		repeatImage.setImage(repeatOne);
    	}
    	else if (model.getRepeatState() == model.getRepeatOne()){
    		repeatImage.setImage(dontRepeat);
    	}
    	model.toggleRepeatState();
    }

    @FXML
    void handleAdd(ActionEvent event) {
    	FileChooser chooser = new FileChooser();
    	chooser.setTitle("Choose MP3 Files to add:");
    	List <File> selectedFiles = chooser.showOpenMultipleDialog(new Stage());
    	if(selectedFiles != null){
	    	for (File file : selectedFiles) {
				model.addPlayList(file.getAbsolutePath());
			}
    	}
    }

    @FXML
    void handleDelete(ActionEvent event) {
    	Object[] indexes = songList.getSelectionModel().getSelectedIndices().toArray();;

    	if(indexes.length == 0){
	    	Alert alert = new Alert(AlertType.WARNING);
	        alert.initOwner(mainApp.getPrimaryStage());
	        alert.setTitle("No Selection");
	        alert.setHeaderText("No Song Selected");
	        alert.setContentText("Please select a song in the playlist.");
	        alert.showAndWait();
    	}
    	else {
			for (int i = indexes.length-1; i >= 0; i--) {
				System.out.println(indexes.length-1);
	    		System.out.println("i "+i);
				model.removePlayList((int)indexes[i]);
			}

			if (model.IsPlaying()){
				setPauseButton(); //si esta reproduciendo actualizo la vista p/ que muestre el icono pausa
			}
			else{
				setPlayButton(); //si no muestro la opcion play
			}
    	}
    }

    @FXML
    void handleMinimize(ActionEvent event) {
    	resetView();
    }

    @FXML
    void handleVolumeSlide(ActionEvent event) {}

    @FXML
    void handleVolumeScroll(ActionEvent event) {
    }

    @FXML
    private BorderPane leftPane;

	@FXML
	void initialize() {
		resetView();
		songList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

	    albumArt.fitWidthProperty().bind(leftPane.heightProperty());
	    albumArt.fitHeightProperty().bind(leftPane.widthProperty());

		// Escucho cambios en el slider de volumen
		volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				double volumen = newValue.doubleValue()/100;
				if (volumen <= 100 || volumen >= 0) {
					model.setVolumen(volumen);
					if(volumen == 0){
						muteButton.setImage(mute);
						muteToggle.setSelected(true);
					}
					else{
						muteButton.setImage(volumeOn);
						muteToggle.setSelected(false);
					}
				} else
					return;
			}
		});

		// Escucho mouse wheel en volume slider
		volumeSlider.setOnScroll(new EventHandler<ScrollEvent>() {
			@Override
			public void handle(ScrollEvent event) {
				if(event.getDeltaY()>0){
					volumeSlider.setValue(volumeSlider.getValue()+ 5);
				}
				else{
					volumeSlider.setValue(volumeSlider.getValue()- 5);
				}
			}
		});

		// Escucho cambios en el slider de progreso
		progressSlider.setOnMouseReleased((MouseEvent event) -> {
			model.seek((int)progressSlider.getValue());
		});

		progressSlider.setOnScroll(new EventHandler<ScrollEvent>() {
			@Override
			public void handle(ScrollEvent event) {
				if(event.getDeltaY()>0){
					progressSlider.setValue(progressSlider.getValue() + 20);
				}
				else{
					progressSlider.setValue(progressSlider.getValue() - 20);
				}
				model.seek((int)progressSlider.getValue());
			}
		});


		// Seteo playlist a songlist para que se actualice sola
		songList.setItems(playList);

		// Detecto doble click en playlist
		songList.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent click) {
				if(click.getClickCount()==2){
					model.stop();
					model.setIndex(songList.getSelectionModel().getSelectedIndex());
					handlePlay(new ActionEvent());
				}
			}
		});
	}

	@Override
	public void updateTrackInfo() {
		songLabel.setText(model.getCurrentTrackName());
		artistLabel.setText(model.getArtist());
		totalTime.setText(model.getCurrentSongDuration());

		if(model.getState() instanceof EmptyState){
			return;
		}
		byte[] imageData = model.getAlbumArt();
		if(imageData == null){
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
		String[] lista = model.getCurrentPlaylist();
		playList.clear();
		if(lista.length == 0 || lista == null){
			resetView();
		}
		for (int i = 0; i < lista.length; i++) {
			playList.add(lista[i]);
		}

		Platform.runLater(new Runnable() {
		    @Override
		    public void run() {
//		    	songList.scrollTo(model.getIndex());
//		        songList.getSelectionModel().select(model.getIndex());
		    }
		});
	}

	public void registerAsObserver() {
        model.registerObserver((TrackObserver)this);
        model.registerObserver((ProgressObserver)this);
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

	public void setPauseButton(){
		playIcon.setImage(pauseImg);
	}

	public void setPlayButton(){
		playIcon.setImage(playImg);
	}

	public void resetView(){
		albumArt.setImage(noAlbumArt);
		artistLabel.setText("Artist");
		songLabel.setText("Song");
		currentTime.setText("00:00");
		totalTime.setText("00:00");
		playIcon.setImage(playImg);
		muteButton.setImage(volumeOn);
		muteToggle.setSelected(false);
		shuffleToggle.setSelected(false);
		repeatImage.setImage(dontRepeat);
		playList.clear();
	}
}