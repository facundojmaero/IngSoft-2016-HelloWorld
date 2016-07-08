package player;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
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
import javafx.scene.control.ProgressBar;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Slider;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import player.models.MP3Model;
import player.models.Song;
import player.states.DontRepeat;
import player.states.EmptyState;
import player.states.RepeatAll;
import player.states.RepeatOne;
import player.views.ProgressObserver;
import player.views.TrackObserver;

public class BigController implements TrackObserver, ProgressObserver {

	@FXML private ToggleButton muteToggle;
	@FXML private ToggleButton shuffleToggle;
	@FXML private Button hidePlaylistButton;
	@FXML private Button deleteButton;
	@FXML private Button previousButton;
	@FXML private ImageView albumArt;
	@FXML private Label totalTime;
	@FXML private Button addButton;
	@FXML private TableView<Song> songList;
	@FXML private TableColumn<Song, String> songNameColumn;
	@FXML private TableColumn<Song, String> durationColumn;
	@FXML private TableColumn<Song, String> albumColumn;
	@FXML private TableColumn<Song, String> artistColumn;
	@FXML private TableColumn<Song, String> yearColumn;
	@FXML private Button playButton;
	@FXML private Button repeatButton;
	@FXML private Label currentTime;
	@FXML private Button nextButton;
	@FXML private Label songLabel;
	@FXML private Slider volumeSlider;
	@FXML private Slider progressSlider;
	@FXML private Label artistLabel;
	@FXML private ImageView playIcon;
	@FXML private ImageView muteButton;
	@FXML private ImageView repeatImage;
	@FXML private ProgressBar progressBar;
	@FXML private ProgressBar volumeProgressBar;
	@FXML private Button miniPlayerButton;
	@FXML private BorderPane leftPane;
	@FXML private Button themeButton;

	private MainApp mainApp;
	private MP3Model model;
	private ObservableList<Song> playList = FXCollections.observableArrayList();

	Image pauseImg = new Image("file:src/resources/images/pauseicon.png");
	Image playImg = new Image("file:src/resources/images/playicon.png");
	Image repeatAll = new Image("file:src/resources/images/repeat.png");
	Image repeatOne = new Image("file:src/resources/images/repeatOne.png");
	Image dontRepeat = new Image("file:src/resources/images/dontRepeat.png");
	Image mute = new Image("file:src/resources/images/mute.png");
	Image volumeOn = new Image("file:src/resources/images/volume.png");
	Image noAlbumArt = new Image("file:src/resources/images/No-album-art.png");

	private double volumeAux;

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	public void setModel(MP3Model instance) {
		model = instance;
	}

	@FXML
	void switchTheme (ActionEvent event){
		mainApp.switchTheme();
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
			setPlayButton();
		} else {
			model.play();
			setPauseButton();
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
			volumeAux = volumeSlider.getValue();
			volumeSlider.setValue(0);
			muteButton.setImage(mute);
		} else {
			model.setVolumen(volumeAux / 100);
			volumeSlider.setValue(volumeAux);
			muteButton.setImage(volumeOn);
		}
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
	void handleRepeat(ActionEvent event) {
		if (model.getRepeatState() == model.getDontRepeat()) {
			repeatImage.setImage(repeatAll);
		} else if (model.getRepeatState() == model.getRepeatAll()) {
			repeatImage.setImage(repeatOne);
		} else if (model.getRepeatState() == model.getRepeatOne()) {
			repeatImage.setImage(dontRepeat);
		}
		model.toggleRepeatState();
	}

	@FXML
	void handleAdd(ActionEvent event) {
		FileChooser chooser = new FileChooser();
		chooser.setTitle("Choose MP3 Files to add:");
		List<File> selectedFiles = chooser.showOpenMultipleDialog(new Stage());
		if (selectedFiles != null) {
			for (File file : selectedFiles) {
				model.addPlayList(file.getAbsolutePath());
			}
		}
	}

	@FXML
	void handleDelete(ActionEvent event) {
		Object[] indexes = songList.getSelectionModel().getSelectedIndices().toArray();

		if (indexes.length == 0) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("No Song Selected");
			alert.setContentText("Please select a song in the playlist.");
			alert.showAndWait();
		} else {
			for (int i = indexes.length - 1; i >= 0; i--) {
				model.removePlayList((int) indexes[i]);
			}

			if (model.IsPlaying()) {
				setPauseButton(); // si esta reproduciendo actualizo la vista p/
									// que muestre el icono pausa
			} else {
				setPlayButton(); // si no muestro la opcion play
			}
		}
	}

	@FXML
	void handleMinimize(ActionEvent event) {
		model.removeObserver((ProgressObserver) this);
		model.removeObserver((TrackObserver) this);
		mainApp.changeSmallView();
	}

	@FXML
	void handleMiniPlayer(ActionEvent event) {
		model.removeObserver((ProgressObserver) this);
		model.removeObserver((TrackObserver) this);
		mainApp.changeMiniView();
	}

	@FXML
	void handleVolumeSlide(ActionEvent event) {}

	@FXML
	void handleVolumeScroll(ActionEvent event) {}

	@FXML
	void initialize() {
		resetView();
		songList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		volumeProgressBar.setProgress(1);
		themeButton.getStyleClass().add("topButton");
		miniPlayerButton.getStyleClass().add("topButton");
		hidePlaylistButton.getStyleClass().add("topButton");

		progressSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                    Number old_val, Number new_val) {
                progressBar.setProgress(new_val.doubleValue() / model.getCurrentSongDurationSec());
            }
        });

		volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                    Number old_val, Number new_val) {
                volumeProgressBar.setProgress(new_val.doubleValue() / 100);
            }
        });

		// Initialize the columns.
		songNameColumn.setCellValueFactory(cellData -> cellData.getValue().songNameProperty());
		durationColumn.setCellValueFactory(cellData -> cellData.getValue().songDurationProperty());
		albumColumn.setCellValueFactory(cellData -> cellData.getValue().songAlbumProperty());
		artistColumn.setCellValueFactory(cellData -> cellData.getValue().songArtistProperty());
		yearColumn.setCellValueFactory(cellData -> cellData.getValue().songYearProperty());

		albumArt.fitWidthProperty().bind(leftPane.heightProperty());
		albumArt.fitHeightProperty().bind(leftPane.widthProperty());

		// Escucho cambios en el slider de volumen
		volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				double volumen = newValue.doubleValue() / 100;
				if (volumen <= 100 || volumen >= 0) {
					model.setVolumen(volumen);
					if (volumen == 0) {
						muteButton.setImage(mute);
						muteToggle.setSelected(true);
					} else {
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
				if (event.getDeltaY() > 0) {
					volumeSlider.setValue(volumeSlider.getValue() + 5);
				} else {
					volumeSlider.setValue(volumeSlider.getValue() - 5);
				}
			}
		});

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

		// Seteo playlist a songlist para que se actualice sola
		songList.setItems(playList);

		// Detecto doble click en playlist
		songList.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent click) {
				if (click.getClickCount() == 2) {
					model.stop();
					model.setIndex(songList.getSelectionModel().getSelectedIndex());
					handlePlay(new ActionEvent());
				}
			}
		});

		// Custom rendering of the table cell.
		songNameColumn.setCellFactory(column -> {
			return new TableCell<Song, String>() {
				@Override
				protected void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);

					if (item == null || empty) {
						setText(null);
						setStyle("");
					} else {
						getStyleClass().clear();
						if (item.endsWith("<-")) {
							setText(item.substring(0,item.length()-2));
							getStyleClass().add("currentSong");
						} else {
							getStyleClass().add("playlist");
							setStyle("");
							setText(item);
						}
					}
				}
			};
		});

		durationColumn.setCellFactory(column -> {
			return new TableCell<Song, String>() {
				@Override
				protected void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);

					if (item == null || empty) {
						setText(null);
						setStyle("");
					} else {
						getStyleClass().clear();
						if (item.endsWith("<-")) {
							setText(item.substring(0,item.length()-2));
							getStyleClass().add("currentSong");
						} else {
							getStyleClass().add("playlist");
							setStyle("");
							setText(item);
						}
					}
				}
			};
		});

		albumColumn.setCellFactory(column -> {
			return new TableCell<Song, String>() {
				@Override
				protected void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);

					if (item == null || empty) {
						setText(null);
						setStyle("");
					} else {
						getStyleClass().clear();
						if (item.endsWith("<-")) {
							setText(item.substring(0,item.length()-2));
							getStyleClass().add("currentSong");
						} else {
							getStyleClass().add("playlist");
							setStyle("");
							setText(item);
						}
					}
				}
			};
		});

		artistColumn.setCellFactory(column -> {
			return new TableCell<Song, String>() {
				@Override
				protected void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);

					if (item == null || empty) {
						setText(null);
						setStyle("");
					} else {
						getStyleClass().clear();
						if (item.endsWith("<-")) {
							setText(item.substring(0,item.length()-2));
							getStyleClass().add("currentSong");
						} else {
							getStyleClass().add("playlist");
							setStyle("");
							setText(item);
						}
					}
				}
			};
		});

		yearColumn.setCellFactory(column -> {
			return new TableCell<Song, String>() {
				@Override
				protected void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);

					if (item == null || empty) {
						setText(null);
						setStyle("");
					} else {
						getStyleClass().clear();
						if (item.endsWith("<-")) {
							setText(item.substring(0,item.length()-2));
							getStyleClass().add("currentSong");
						} else {
							getStyleClass().add("playlist");
							setStyle("");
							setText(item);
						}
					}
				}
			};
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
		ArrayList<Song> lista = model.getCurrentSongPlaylist();
		playList.clear();
		if (lista.size() == 0 || lista == null) {
			resetView();
		}
		for (int i = 0; i < lista.size(); i++) {
			playList.add(lista.get(i));
		}
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
		repeatImage.setImage(dontRepeat);
		playList.clear();
	}

	public void configureOnViewChange() {
		// si estaba en pausa, seteo esa imagen. sino no hago nada
		if (model.IsPlaying()) {
			playIcon.setImage(pauseImg);
		}
		if (model.getVolumen() == 0) {
			muteButton.setImage(mute);
			muteToggle.setSelected(true);
		}
		if (model.isShuffled()) {
			shuffleToggle.setSelected(true);
		}

		volumeSlider.setValue(model.getVolumen() * 100);

		if (model.getRepeatState() instanceof DontRepeat) {
			repeatImage.setImage(dontRepeat);
		} else if (model.getRepeatState() instanceof RepeatAll) {
			repeatImage.setImage(repeatAll);
		} else if (model.getRepeatState() instanceof RepeatOne) {
			repeatImage.setImage(repeatOne);
		}
	}
}