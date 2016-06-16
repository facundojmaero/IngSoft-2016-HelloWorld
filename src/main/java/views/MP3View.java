package main.java.views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Map;

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
import javax.swing.JSlider;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import javazoom.jlgui.basicplayer.BasicController;
import javazoom.jlgui.basicplayer.BasicPlayerEvent;
import javazoom.jlgui.basicplayer.BasicPlayerListener;

import com.mpatric.mp3agic.ID3v2;

import main.java.controllers.MP3Controller2;
import main.java.models.MP3ModelInterface;

public class MP3View extends JFrame implements ActionListener, TrackObserver {

	public static class PlayerListener implements BasicPlayerListener {
		private static class PlayerListenerHolder {
			public static PlayerListener uniquePlayerListener = new PlayerListener();
		}
		
		private static long time = -1;
		private static long songLenght = -1;
		private static MP3View view = null;

		private PlayerListener(){}
		private void setSongLength(long t){
			songLenght = t;
		}
		private void setView(MP3View mp3View){
			view = mp3View;
		}
		
		public static PlayerListener getInstance(MP3View mp3View, long t){
			PlayerListenerHolder.uniquePlayerListener.setSongLength(t);
			PlayerListenerHolder.uniquePlayerListener.setView(mp3View);
			return PlayerListenerHolder.uniquePlayerListener;
		}
		
		@Override
		public void opened(Object arg0, @SuppressWarnings("rawtypes") Map arg1) {}

		@Override
		public void progress(int arg0, long arg1, byte[] arg2, @SuppressWarnings("rawtypes") Map arg3) {
			if(arg1-time < 500000){
				time = arg1;
				if(!view.seeking){
					view.updateProgressBar(Double.valueOf(time/(songLenght*1000.0)));
					System.out.println("arg1="+arg1+" - "+songLenght);
				}
			}
//			if(arg1%1000000 < 100000){
//				System.out.println("arg0="+arg0+" - arg1="+arg1+" - arg2="+arg2+" - arg3+");
//			}
		}

		@Override
		public void setController(BasicController arg0) {}

		@Override
		public void stateUpdated(BasicPlayerEvent arg0) {}
		
	}
	

	public class TimeSliderListener implements ChangeListener {
		private MP3View view = null;
		
		public TimeSliderListener(MP3View mp3View){
			view = mp3View;
		}
		
		@Override
		public void stateChanged(ChangeEvent e) {
			if(e != null && e.getSource() != null){
				int newValue = ((JSlider) e.getSource()).getValue();
				if(view.seeking){
					System.out.println("stateChanged");
					seekingTime = newValue * model.getCurrentSongDurationMil();
//					controller.setTime(newValue * model.getCurrentSongDurationMil());
				}
			}
		}
		
	}

	
	public class TimeSliderMouseListener implements MouseListener {
		private MP3View view = null;
		
		public TimeSliderMouseListener(MP3View mp3View){
			view = mp3View;
		}

		@Override
		public void mouseClicked(MouseEvent e) {}

		@Override
		public void mouseEntered(MouseEvent e) {}

		@Override
		public void mouseExited(MouseEvent e) {}

		@Override
		public void mousePressed(MouseEvent e) {
			view.seeking = true;
			System.out.println(view.seeking+" - "+seekingTime);
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			controller.setTime(seekingTime);
			view.seeking = false;
			System.out.println(view.seeking+" - "+seekingTime);
		}
	}
	
	public void updateProgressBar(double progress){
		timeSlider.setValue((int) (progress*1000));
		System.out.println("progress = "+progress);
	}
	
	private static final long serialVersionUID = 1L;
	MP3ModelInterface model;
	MP3Controller2 controller = null;
	Thread progressBarThread = null;
//	MP3Model.ProgressBarListener progressBarListener = null;
	//Other
	DefaultListModel<String> songList = new DefaultListModel<String>();
//	ScheduledExecutorService timersExec = Executors.newSingleThreadScheduledExecutor();	
//	ScheduledExecutorService titleExec = Executors.newSingleThreadScheduledExecutor();
	float currentAudioDurationSec = 0;
	boolean seeking = false;
	long seekingTime = -1;
	//Components
	JPanel container = new JPanel();
	JFrame songArt = null;
	JFrame songInfo = null;
	JSlider volSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
	JSlider timeSlider = new JSlider(JSlider.HORIZONTAL, 0, 1000, 0);
	JButton btnPlay = new JButton();
	JButton btnAdd = new JButton();
	JButton btnNext = new JButton();
	JButton btnPrev = new JButton();
//	JButton btnVolUp = new JButton();
//	JButton btnVolDown = new JButton();
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
	String frameIconPath = "main/resources/images/frameicon.png";
	ImageIcon frameIcon = new ImageIcon(getClass().getClassLoader().getResource(frameIconPath));
	String playIconPath = "main/resources/images/playicon.png";
	ImageIcon playIcon = new ImageIcon(getClass().getClassLoader().getResource(playIconPath));
	String pauseIconPath = "main/resources/images/pauseicon.png";
	ImageIcon pauseIcon = new ImageIcon(getClass().getClassLoader().getResource(pauseIconPath));
	String volUpIconPath = "main/resources/images/up.png";
	ImageIcon volUpIcon = new ImageIcon(getClass().getClassLoader().getResource(volUpIconPath));
	String volDownIconPath = "main/resources/images/down.png";
	ImageIcon volDownIcon = new ImageIcon(getClass().getClassLoader().getResource(volDownIconPath));
	String muteIconPath = "main/resources/images/off.png";
	ImageIcon muteIcon = new ImageIcon(getClass().getClassLoader().getResource(muteIconPath));
	String prevIconPath = "main/resources/images/prev.png";
	ImageIcon prevIcon = new ImageIcon(getClass().getClassLoader().getResource(prevIconPath));
	String nextIconPath = "main/resources/images/next.png";
	ImageIcon nextIcon = new ImageIcon(getClass().getClassLoader().getResource(nextIconPath));
	String addIconPath = "main/resources/images/add.png";
	ImageIcon addIcon = new ImageIcon(getClass().getClassLoader().getResource(addIconPath));
	String stopIconPath = "main/resources/images/stop.png";
	ImageIcon stopIcon = new ImageIcon(getClass().getClassLoader().getResource(stopIconPath));
	String deleteIconPath = "main/resources/images/delete.png";
	ImageIcon deleteIcon = new ImageIcon(getClass().getClassLoader().getResource(deleteIconPath));
	String infoIconPath = "main/resources/images/songinfo.png";
	ImageIcon infoIcon = new ImageIcon(getClass().getClassLoader().getResource(infoIconPath));
	String artIconPath = "main/resources/images/songart.png";
	ImageIcon artIcon = new ImageIcon(getClass().getClassLoader().getResource(artIconPath));

	
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
		int _W = 400;
		setSize(_W,_H);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		//Container
		container.setLayout(null);
		getContentPane().add(container);
		

		//Panel de nowPlaying(el que va arriba)
		JPanel panelNP = new JPanel();
		panelNP.setLayout(new BoxLayout(panelNP, BoxLayout.PAGE_AXIS));
		panelNP.setToolTipText("Now Playing");
		panelNP.setBorder(BorderFactory.createMatteBorder(1, 0, 2, 0, Color.gray));
		panelNP.setBounds(5, 0, _W-15, 20);
		//JLabel lblnp = new JLabel("Now Playing:");
		lblplaying.setText("Now Playing: ");
		lblplaying.setBounds(5, 0, 100, 40);
		//panelNP.add(lblnp);
		panelNP.add(lblplaying);
		container.add(panelNP);
		
		//Labels song time
		JPanel contSlbl = new JPanel();
		contSlbl.setBounds(10, 15, _W-20, 20);
		contSlbl.add(lblst);
		contSlbl.add(lblet);
		lblst.setText("00:00");
		lblst.setBorder(new EmptyBorder(0, 0, 0, 300));
		lblet.setText("00:00");
		container.add(contSlbl);
		
		
		//Panel para los botones prev,play,stop y next
		JPanel contTime = new JPanel();
		contTime.setBounds(0, 40, 400, 40);
		contTime.setLayout(null);

		timeSlider.setBounds(0, 0, 400, 40);
		timeSlider.setMinimumSize(new Dimension(400, 40));
		timeSlider.setMinorTickSpacing(10);
		timeSlider.setMajorTickSpacing(100);
		timeSlider.setPaintTicks(true);
		timeSlider.setPaintLabels(false);
		timeSlider.setVisible(true);
		
		contTime.add(timeSlider);
		container.add(contTime);
		
		//Buttons
		int btn_h = 35;		//altura de los botones
		int btn_w = 50;		//ancho de los botones
		int lineW = _W - 10;//ancho de los contenedores
		int line1 = 80;		//posicion "y"

		//Panel para los botones prev,play,stop y next
		JPanel contBtns = new JPanel();
		contBtns.setBounds(0, line1, lineW, btn_h);		
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
		volBtns.setBounds(0, line2, lineW, btn_h);
		
		volSlider.setSize(100, 20);
		volSlider.setMinorTickSpacing(2);
		volSlider.setMajorTickSpacing(10);
		volSlider.setPaintTicks(true);
		volSlider.setPaintLabels(false);
		volSlider.setVisible(true);
		btnMute.setIcon(muteIcon);
		btnMute.setSize(btn_w,btn_h);
		volBtns.add(btnMute);
		volBtns.add(volSlider);
		/*
		btnVolDown.setIcon(volDownIcon);
		btnVolDown.setSize(btn_w,btn_h);
		volBtns.add(btnVolDown);
		btnVolUp.setIcon(volUpIcon);
		btnVolUp.setSize(btn_w,btn_h);
		volBtns.add(btnVolUp);
		*/
		container.add(volBtns);
		
		//Panel para botones de ver info, agregar y borrar playlist
		int line4 = 170;
		JPanel configBtns = new JPanel();
		configBtns.setBounds(0, line4, lineW, btn_h);
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
		
		//Panel para botones de volumen
		/*
		JPanel volBar = new JPanel();
		volBar.setBounds(0, 200, 320, 30);
		volSlider.setSize(new Dimension(100,20));
		volSlider.setMinorTickSpacing(2);
		volSlider.setMajorTickSpacing(10);
		volSlider.setPaintTicks(true);
		volSlider.setPaintLabels(true);
		volSlider.setVisible(true);		
		volBar.add(volSlider);
		container.add(volBar);
		*/
		
		
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
	}
	
	private void addListeners(){
		btnPlay.addActionListener(this);
		btnPrev.addActionListener(this);
		btnNext.addActionListener(this);
		btnAdd.addActionListener(this);
		btnMute.addActionListener(this);
		btnStop.addActionListener(this);
		btnArt.addActionListener(this);
		btnInfo.addActionListener(this);
		btnDelete.addActionListener(this);
//		Se cambio por volSlider
//		btnVolUp.addActionListener(this);
//		btnVolDown.addActionListener(this);
		volSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				controller.setVolumen(Double.valueOf(((JSlider) e.getSource()).getValue()) / 100.0);
			}
		});
		timeSlider.addChangeListener(new TimeSliderListener(this));
		timeSlider.addMouseListener(new TimeSliderMouseListener(this));
		model.addPlayerListener(this, -1);
//		progressBarListener = MP3Model.ProgressBarListener.getInstance(this);
//		progressBarThread = new Thread(progressBarListener);
//		progressBarThread.start();
	}
	
	//Metodo para manejar los eventos dependiendo que boton se toco
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == btnPlay) {
			if(model.IsPlaying()){
				controller.pause();
			}
			else{
				controller.start();
				model.addPlayerListener(this, model.getCurrentSongDurationMil());
			}
		}
		else if(event.getSource() == btnPrev){
			controller.decreaseBPM();
			model.addPlayerListener(this, model.getCurrentSongDurationMil());
		}
		else if(event.getSource() == btnNext){
			controller.increaseBPM();
			model.addPlayerListener(this, model.getCurrentSongDurationMil());
		}
		else if(event.getSource() == btnAdd){
		    FileNameExtensionFilter filter = new FileNameExtensionFilter("mp3 Files", "mp3");
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
			if(songArt != null){
				songArt.setVisible(false);
				songArt.removeAll();
				songArt = null;
			} else {
				songArt = new JFrame();
				songArt.getContentPane().setLayout(new FlowLayout());
				songArt.getContentPane().add(new JLabel(new ImageIcon(img)));
				songArt.pack();
				songArt.setTitle("Album Art");
				songArt.setVisible(true);
			}
		}
		else if(event.getSource() == btnInfo){
			if(songInfo != null){
				songInfo.setVisible(false);
				songInfo.removeAll();
				songInfo = null;
			} else {
				ID3v2 songTag = model.getSongInfo();
				songInfo = new JFrame();
		    	DefaultListModel<String> songDetails = new DefaultListModel<String>();
		    	songDetails.addElement("Track: " + songTag.getTrack());
		    	songDetails.addElement("Artist: " + songTag.getArtist());
		    	songDetails.addElement("Title: " + songTag.getTitle());
		    	songDetails.addElement("Album: " + songTag.getAlbum());
		    	songDetails.addElement("Year: " + songTag.getYear());
		    	songDetails.addElement("Genre: " + songTag.getGenreDescription());
		
		    	JList<String> jSongList = new JList<String>(songDetails);
		    	songInfo.add(jSongList);
		    	songInfo.setTitle("Song Info");
		    	songInfo.setSize(240, 160);
		    	songInfo.setLocationRelativeTo(null);
		    	songInfo.setVisible(true);
			}
		}
		else if(event.getSource() == btnDelete){
			controller.removeTrack(jSongList.getSelectedIndex());
		}
		/*
		else if(event.getSource() == btnVolUp){
			controller.increaseVolumen();
		}
		else if(event.getSource() == btnVolDown){
			controller.decreaseVolumen();
		}
		*/
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
		lblplaying.setText("Now Playing: " + model.getCurrentTrackName());
		lblet.setText(model.getCurrentSongDuration());
	}

	@Override
	public void updatePlaylistInfo() {
		String[] playlist = model.getCurrentPlaylist();
		songList.clear(); //Borro la songList que habia y le pido al modelo que me de la nueva(el clear es necesario para evitar duplicados)
		for(int i=0;i<playlist.length;i++){
			songList.addElement(playlist[i]);
		}
		jSongList.setSelectedIndex(model.getIndex());
	}
}
