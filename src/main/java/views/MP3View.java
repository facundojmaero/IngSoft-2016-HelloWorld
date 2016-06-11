package main.java.views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

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

public class MP3View extends JFrame implements ActionListener {
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
	JButton btnShSt = new JButton();
	JButton btnShWf = new JButton();
	JButton btnShDi = new JButton();
	JButton btnDel = new JButton();
	JButton btnDelAll = new JButton();
	JMenuBar topMenu = new JMenuBar();
	JList<String> jSongList = new JList<String>(songList);
	JLabel lblplaying = new JLabel();
	JLabel lblst = new JLabel();
	JLabel lblet = new JLabel();
	//SeekBar seekbar = new SeekBar();
	JFileChooser fc = new JFileChooser();
	//Frames
	//WaveformParallelFrame wff = null;
	//FFTParallelFrame fdf = null;
	//public static StatusFrame stf = new StatusFrame();
	//Icons
	ImageIcon frameIcon = new ImageIcon("images/frameicon.png");
	ImageIcon playIcon = new ImageIcon("images/playicon.png");
	ImageIcon pauseIcon = new ImageIcon("images/pauseicon.png");
	
	/**
	 * Class/Frame constructor
	 */
	public MP3View()
	{
		this.init();	//Inicializa la vista
		this.addListeners();	//Añade EventListener a los botones
	}
	
	/**
	 * Init Swing graphics UI 
	 */
	private void init()
	{
		//MainView
		setIconImage(frameIcon.getImage());
		setTitle("Music Player - Java - 1.0");
		int _H = 300;
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
		int line1 = 80;		//posicion y
		JPanel contBtns = new JPanel();
		contBtns.setBounds(0, line1, 320, btn_h);
		btnPrev.setText("<<");
		btnPrev.setSize(btn_w,btn_h);
		btnPlay.setIcon(playIcon);
		btnPlay.setMnemonic(KeyEvent.VK_SPACE);
		btnPlay.setSize(btn_w,btn_h);
		btnNext.setText(">>");
		btnNext.setSize(btn_w,btn_h);
		int line2 = 125;
		btnAdd.setText("Add Song");
		btnAdd.setBounds(_W/2-35,line2,70,btn_h);
		contBtns.add(btnPrev);
		contBtns.add(btnPlay);
		contBtns.add(btnNext);
		container.add(contBtns);
		container.add(btnAdd);
		//Now Playing Panel
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
		int h_list = 100;
		int line3 = 175;
		//jSongList.setBounds(0, line1+50, _W, h_list);
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
	}
	
	//Metodo para manejar los eventos debependiendo que boton se toco
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == btnPlay) {
			System.out.println("Click en play");
			btnPlay.setIcon(pauseIcon);
		}
		else if(event.getSource() == btnPrev){
			System.out.println("Click en anterior");
		}
		else if(event.getSource() == btnNext){
			System.out.println("Click en siguiente");
		}
		else if(event.getSource() == btnAdd){
			System.out.println("Click en add");
		}
	}
}
