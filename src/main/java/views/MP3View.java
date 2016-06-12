package main.java.views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;

import main.java.controllers.ControllerInterface;
import main.java.controllers.MP3Controller2;
import main.java.models.MP3Model;
import main.java.models.MP3ModelInterface;

public class MP3View extends JFrame implements ActionListener, TrackObserver {
	MP3ModelInterface model;
	MP3Controller2 controller = null;
	//Other
	DefaultListModel<String> songList = new DefaultListModel<String>();
	ScheduledExecutorService timersExec = Executors.newSingleThreadScheduledExecutor();	
	ScheduledExecutorService titleExec = Executors.newSingleThreadScheduledExecutor();
	float currentAudioDurationSec = 0; 
	//Components
	JPanel container = new JPanel();
	JButton btnPlay = new JButton();
	JButton btnAdd = new JButton();
	JButton btnNext = new JButton();
	JButton btnPrev = new JButton();
	JButton btnVolUp = new JButton();
	JButton btnVolDown = new JButton();
	JButton btnMute = new JButton();
	JButton btnCover = new JButton();
	JButton btnShSt = new JButton();
	JButton btnShWf = new JButton();
	JButton btnShDi = new JButton();
	JButton btnStop = new JButton();
	
	JButton btnDelete = new JButton();
	JButton btnInfo = new JButton();
	JButton btnArt = new JButton();
	
	JMenuBar topMenu = new JMenuBar();
	JList<String> jSongList = new JList<String>(songList);
	JLabel lblplaying = new JLabel();
	JLabel lblst = new JLabel();
	JLabel lblet = new JLabel();
	//SeekBar seekbar = new SeekBar();
	JFileChooser chooser = new JFileChooser();
	//Frames
	//WaveformParallelFrame wff = null;
	//FFTParallelFrame fdf = null;
	//public static StatusFrame stf = new StatusFrame();
	//Icons
	ImageIcon frameIcon = new ImageIcon("src/main/resources/images/frameicon.png");
	ImageIcon playIcon = new ImageIcon("src/main/resources/images/playicon.png");
	ImageIcon pauseIcon = new ImageIcon("src/main/resources/images/pauseicon.png");
	ImageIcon volUpIcon = new ImageIcon("src/main/resources/images/up.png");
	ImageIcon volDownIcon = new ImageIcon("src/main/resources/images/down.png");
	ImageIcon muteIcon = new ImageIcon("src/main/resources/images/off.png");
	ImageIcon prevIcon = new ImageIcon("src/main/resources/images/prev.png");
	ImageIcon nextIcon = new ImageIcon("src/main/resources/images/next.png");
	ImageIcon addIcon = new ImageIcon("src/main/resources/images/add.png");
	ImageIcon stopIcon = new ImageIcon("src/main/resources/images/stop.png");
	ImageIcon deleteIcon = new ImageIcon("src/main/resources/images/delete.png");
	ImageIcon infoIcon = new ImageIcon("src/main/resources/images/songinfo.png");
	ImageIcon artIcon = new ImageIcon("src/main/resources/images/songart.png");
	
	/**
	 * Class/Frame constructor
	 */
	public MP3View(MP3ModelInterface model)
	{
		this.model = model;
		this.init();										//Inicializa la vista
		this.updatePlaylistInfo();							//Muestra la playlist añadida por defecto en el JScrollPanel
		this.addListeners();								//Añade EventListener a los botones
		model.registerObserver((TrackObserver)this);
	}
	
	/**
	 * Init Swing graphics UI 
	 */
	private void init()
	{
		//MainView
		setIconImage(frameIcon.getImage());
		setTitle("Music Player - Java - 1.0");
		int _H = 400;
		int _W = 330;
		setSize(_W,_H);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		//Container
		container.setLayout(null);
		getContentPane().add(container);
		//Buttons
		int btn_h = 35;		//altura de los botones
		int btn_w = 50;		//ancho de los botones
		int line1 = 80;		//posicion "y"
		//Panel para los botones prev,play,stop y next
		JPanel contBtns = new JPanel();
		contBtns.setBounds(0, line1, 320, btn_h);		
		btnPrev.setIcon(prevIcon);
		btnPrev.setSize(btn_w,btn_h);
		btnPlay.setIcon(playIcon);
		btnPlay.setMnemonic(KeyEvent.VK_SPACE);
		btnPlay.setSize(btn_w,btn_h);
		btnNext.setIcon(nextIcon);
		btnNext.setSize(btn_w,btn_h);
		btnStop.setIcon(stopIcon);
		btnStop.setSize(btn_w,btn_h);
		contBtns.add(btnPrev);
		contBtns.add(btnPlay);
		contBtns.add(btnStop);
		contBtns.add(btnNext);
		container.add(contBtns);
		
		//Panel para botones de volumen
		int line2 = 125;
		btn_w = 30; //hago los siguientes botones mas chicos
		JPanel volBtns = new JPanel();
		volBtns.setBounds(0, line2, 320, btn_h);
		btnVolUp.setIcon(volUpIcon);
		btnVolUp.setSize(btn_w,btn_h);
		btnVolDown.setIcon(volDownIcon);
		btnVolDown.setSize(btn_w,btn_h);
		btnMute.setIcon(muteIcon);
		btnMute.setSize(btn_w,btn_h);
		volBtns.add(btnMute);
		volBtns.add(btnVolDown);
		volBtns.add(btnVolUp);
		container.add(volBtns);
		
		//Panel para botones de ver info, agregar y borrar playlist
		int line4 = 170;
		JPanel configBtns = new JPanel();
		configBtns.setBounds(0, line4, 320, btn_h);
		btnAdd.setIcon(addIcon);
		btnAdd.setSize(btn_w,btn_h);
		btnDelete.setIcon(deleteIcon);
		btnDelete.setSize(btn_w,btn_h);
		btnInfo.setIcon(infoIcon);
		btnInfo.setSize(btn_w,btn_h);
		btnArt.setIcon(artIcon);
		btnArt.setSize(70,btn_h);
		configBtns.add(btnAdd);
		configBtns.add(btnDelete);
		configBtns.add(btnInfo);
		configBtns.add(btnArt);
		container.add(configBtns);
		
		//Panel de nowPlaying(el que va arriba)
		JPanel panelNP = new JPanel();
		panelNP.setLayout(new BoxLayout(panelNP, BoxLayout.PAGE_AXIS));
		panelNP.setToolTipText("Now Playing");
		panelNP.setBorder(BorderFactory.createMatteBorder(1, 0, 2, 0, Color.gray));
		panelNP.setBounds(5, line1-25, _W-15, 20);
		//JLabel lblnp = new JLabel("Now Playing:");
		lblplaying.setText("Now Playing: ");
		lblplaying.setBounds(5, 0, 100, 40);
		//panelNP.add(lblnp);
		panelNP.add(lblplaying);
		container.add(panelNP);
		
		//SongList
		int h_list = 150;
		int line3 = 215;
		//Panel para la playilist
		JScrollPane listScroller = new JScrollPane(jSongList);
		listScroller.setPreferredSize(new Dimension(_W-10,h_list));
		listScroller.setBounds(0, line3, _W-10, h_list);
		container.add(listScroller);
		//container.add(jSongList);
		//SeekBar
		//seekbar.setBounds(5, 10, _W-15, 10);
		//container.add(seekbar);
		//Labels song time
		JPanel contSlbl = new JPanel();
		contSlbl.setBounds(10, 15, _W-20, 20);
		contSlbl.add(lblst);
		contSlbl.add(lblet);
		lblst.setText("00:00");
		lblst.setBorder(new EmptyBorder(0, 0, 0, 200));
		lblet.setText("00:00");
		container.add(contSlbl);
	}
	
	private void addListeners(){
		 btnPlay.addActionListener(this);
		 btnPrev.addActionListener(this);
		 btnNext.addActionListener(this);
		 btnAdd.addActionListener(this);
		 btnMute.addActionListener(this);
		 btnVolUp.addActionListener(this);
		 btnVolDown.addActionListener(this);
		 btnStop.addActionListener(this);
		 btnArt.addActionListener(this);
		 btnInfo.addActionListener(this);
		 btnDelete.addActionListener(this);
	}
	
	//Metodo para manejar los eventos dependiendo que boton se toco
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == btnPlay) {
			if(model.IsPlaying()){
				controller.pause();
			}
			else{
				controller.start();
			}
		}
		else if(event.getSource() == btnPrev){
			controller.decreaseBPM();
		}
		else if(event.getSource() == btnNext){
			controller.increaseBPM();
		}
		else if(event.getSource() == btnAdd){
		    FileNameExtensionFilter filter = new FileNameExtensionFilter(
		        "mp3 Files", "mp3");
		    chooser.setFileFilter(filter);
		    int returnVal = chooser.showOpenDialog(btnAdd);
		    if(returnVal == JFileChooser.APPROVE_OPTION) {
		    	File file = chooser.getSelectedFile();
		    	String path = file.getAbsolutePath();
		    	controller.addPlaylist(path);
		    }
		}
		else if(event.getSource() == btnMute){
			controller.setVolumen(0);
		}
		else if(event.getSource() == btnVolUp){
			controller.increaseVolumen();
		}
		else if(event.getSource() == btnVolDown){
			controller.decreaseVolumen();
		}
		else if(event.getSource() == btnStop){
			controller.stop();
		}
		else if(event.getSource() == btnArt){
			ID3v2 songTag = model.getAlbumArt();
			byte[] imageData = songTag.getAlbumImage();
            BufferedImage img = null;
			try {
				img = ImageIO.read(new ByteArrayInputStream(imageData));
			} catch (IOException e) {
				e.printStackTrace();
			}
            JFrame frame = new JFrame();
	        frame.getContentPane().setLayout(new FlowLayout());
	        frame.getContentPane().add(new JLabel(new ImageIcon(img)));
	        frame.pack();
	        frame.setTitle("Album Art");
	        frame.setVisible(true);
		}
		else if(event.getSource() == btnInfo){
			ID3v2 songTag = model.getSongInfo();
			JFrame container = new JFrame();
	    	DefaultListModel<String> songinfo = new DefaultListModel<String>();
	        songinfo.addElement("Track: " + songTag.getTrack());
	        songinfo.addElement("Artist: " + songTag.getArtist());
	        songinfo.addElement("Title: " + songTag.getTitle());
	        songinfo.addElement("Album: " + songTag.getAlbum());
	        songinfo.addElement("Year: " + songTag.getYear());
	        songinfo.addElement("Genre: " + songTag.getGenreDescription());
	
	    	JList<String> jSongList = new JList<String>(songinfo);
	    	container.add(jSongList);
	    	container.setTitle("Song Info");       
	    	container.setSize(240, 160);
	    	container.setLocationRelativeTo(null);
	    	container.setVisible(true);
		}
		else if(event.getSource() == btnDelete){
			controller.clearPlaylist();
		}
	}
	
	public void setController(MP3Controller2 controller){
		this.controller = controller;
	}
	
	public void MakePlayIcon(){
		btnPlay.setIcon(playIcon);
	}
	
	public void MakePauseIcon(){
		btnPlay.setIcon(pauseIcon);
	}

	@Override
	public void updateTrackInfo() {
			lblplaying.setText("Now Playing: " + ((MP3Model) model).getCurrentTrackName());
			lblet.setText(((MP3Model) model).getCurrentSongDuration());
			
	}

	@Override
	public void updatePlaylistInfo() {
		String[] playlist = model.getCurrentPlaylist();
		songList.clear(); //Borro la songList que habia y le pido al modelo que me de la nueva(el clear es necesario para evitar duplicados)
		for(int i=0;i<playlist.length;i++){
			songList.addElement(playlist[i]);
		}
	}
}
