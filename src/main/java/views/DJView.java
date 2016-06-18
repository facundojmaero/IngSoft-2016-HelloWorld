package main.java.views;
    
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import main.java.models.BeatModel;
import main.java.models.BeatModelInterface;
import main.java.models.HeartAdapter;
import main.java.models.HeartModel;
import main.java.models.MP3Adapter;
import main.java.models.MP3Model;
import main.java.controllers.BeatController;
import main.java.controllers.ControllerInterface;
import main.java.controllers.HeartController;
import main.java.controllers.MP3Controller;

public class DJView implements ActionListener,  BeatObserver, BPMObserver {
	BeatModelInterface model;
	ControllerInterface controller;
    JFrame viewFrame;
    JPanel viewPanel;
	BeatBar beatBar;
	JLabel bpmOutputLabel;
    JFrame controlFrame;
    JPanel controlPanel;
    JLabel bpmLabel;
    JTextField bpmTextField;
    JButton setBPMButton;
    JButton increaseBPMButton;
    JButton decreaseBPMButton;
    JMenuBar menuBar;
    JMenu menu;
    JMenu menuStrategy;
    JMenuItem startMenuItem;
    JMenuItem stopMenuItem;
    JMenuItem djMenuItem;
    JMenuItem heartMenuItem;
    JMenuItem mp3MenuItem;

    public DJView(ControllerInterface controller, BeatModelInterface model) {	
		this.controller = controller;
		this.model = model;
		model.registerObserver((BeatObserver)this);
		model.registerObserver((BPMObserver)this);
    }
    
    public void createView() {
		// Create all Swing components here
        viewPanel = new JPanel(new GridLayout(1, 2));
        viewFrame = new JFrame("View");
        viewFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        viewFrame.setSize(new Dimension(100, 80));
        bpmOutputLabel = new JLabel("offline", SwingConstants.CENTER);
		beatBar = new BeatBar();
		beatBar.setValue(0);
        JPanel bpmPanel = new JPanel(new GridLayout(2, 1));
		bpmPanel.add(beatBar);
        bpmPanel.add(bpmOutputLabel);
        viewPanel.add(bpmPanel);
        viewFrame.getContentPane().add(viewPanel, BorderLayout.CENTER);
        viewFrame.pack();
        viewFrame.setVisible(true);
	}
  
  
    public void createControls() {
		// Create all Swing components here
        JFrame.setDefaultLookAndFeelDecorated(true);
        controlFrame = new JFrame("Control");
        controlFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        controlFrame.setSize(new Dimension(100, 80));

        controlPanel = new JPanel(new GridLayout(1, 2));

        menuBar = new JMenuBar();
        menu = new JMenu("DJ Control");
        startMenuItem = new JMenuItem("Start");
        menu.add(startMenuItem);
        startMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                controller.start();
            }
        });
        stopMenuItem = new JMenuItem("Stop");
        menu.add(stopMenuItem); 
        stopMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                controller.stop();
            }
        });
        JMenuItem exit = new JMenuItem("Quit");
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });

        menu.add(exit);
        menuBar.add(menu);
        
        //Barra dropdown con selector de modelo
        menuStrategy = new JMenu("Model");
        djMenuItem = new JMenuItem("DJ Model");
        menuStrategy.add(djMenuItem);
        heartMenuItem = new JMenuItem("Heart Model");
        menuStrategy.add(heartMenuItem);
        mp3MenuItem = new JMenuItem("MP3 Model");
        menuStrategy.add(mp3MenuItem);
        djMenuItem.addActionListener(this);
        heartMenuItem.addActionListener(this);
        mp3MenuItem.addActionListener(this);
        
        menuBar.add(menuStrategy);
        
        controlFrame.setJMenuBar(menuBar);

        bpmTextField = new JTextField(2);
        bpmLabel = new JLabel("Enter BPM:", SwingConstants.RIGHT);
        setBPMButton = new JButton("Set");
        setBPMButton.setSize(new Dimension(10,40));
        increaseBPMButton = new JButton(">>");
        decreaseBPMButton = new JButton("<<");
        setBPMButton.addActionListener(this);
        increaseBPMButton.addActionListener(this);
        decreaseBPMButton.addActionListener(this);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));

		buttonPanel.add(decreaseBPMButton);
		buttonPanel.add(increaseBPMButton);

        JPanel enterPanel = new JPanel(new GridLayout(1, 2));
        enterPanel.add(bpmLabel);
        enterPanel.add(bpmTextField);
        JPanel insideControlPanel = new JPanel(new GridLayout(3, 1));
        insideControlPanel.add(enterPanel);
        insideControlPanel.add(setBPMButton);
        insideControlPanel.add(buttonPanel);
        controlPanel.add(insideControlPanel);
        
        bpmLabel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        bpmOutputLabel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        controlFrame.getRootPane().setDefaultButton(setBPMButton);
        controlFrame.getContentPane().add(controlPanel, BorderLayout.CENTER);

        controlFrame.pack();
        controlFrame.setVisible(true);
    }
    //Habilitadores y deshabilitadores de los botones en los dropdown menus
	public void enableStopMenuItem() {stopMenuItem.setEnabled(true);}
	public void disableStopMenuItem() {stopMenuItem.setEnabled(false);}
	public void enableStartMenuItem() {startMenuItem.setEnabled(true);}
	public void disableStartMenuItem() {startMenuItem.setEnabled(false);}
	public void enableDJStartMenuItem(){djMenuItem.setEnabled(true);}
	public void disableDJStartMenuItem(){djMenuItem.setEnabled(false);}
	public void enableHeartStartMenuItem(){heartMenuItem.setEnabled(true);}
	public void disableHeartStartMenuItem(){heartMenuItem.setEnabled(false);}
	public void enableMP3StartMenuItem(){mp3MenuItem.setEnabled(true);}
	public void disableMP3StartMenuItem(){mp3MenuItem.setEnabled(false);}
	
	//ActionListener que delega al controlador las interacciones con los botones
    public void actionPerformed(ActionEvent event) {
		if (event.getSource() == setBPMButton) {
			int bpm = Integer.parseInt(bpmTextField.getText());
        	controller.setBPM(bpm);
		} else if (event.getSource() == increaseBPMButton) {
			controller.increaseBPM();
		} else if (event.getSource() == decreaseBPMButton) {
			controller.decreaseBPM();
		}
		// Todos los botones para cambiar de modelo -> Patron Strategy
		else{
			//Si presiono heartMenuItem cambio al modelo heartmodel
			if (event.getSource() == heartMenuItem) {
				controller.stop();  //Paro el modelo anterior(Si estaba reproduciendo un mp3 detengo la reproduccion)
				model.removeObserver((BPMObserver)this);
				model.removeObserver((BeatObserver)this);
				setModel(new HeartAdapter(HeartModel.getInstance()));
				model.registerObserver((BPMObserver)this);
				model.registerObserver((BeatObserver)this);
				setController(new HeartController(HeartModel.getInstance(),this));
			}
			//Si presiono djMenuItem cambio al modelo beatmodel
			if (event.getSource() == djMenuItem) {
				controller.stop();
				model.removeObserver((BPMObserver)this);
				model.removeObserver((BeatObserver)this);
				setModel(new BeatModel());
				model.registerObserver((BPMObserver)this);
				model.registerObserver((BeatObserver)this);
				setController(new BeatController(model,this));
			}
			//Si presiono mp3MenuItem cambio al modelo propio
			if (event.getSource() == mp3MenuItem) {
				controller.stop();
				model.removeObserver((BPMObserver)this);
				model.removeObserver((BeatObserver)this);
				MP3Model mp3model = MP3Model.getInstance();
				setModel(new MP3Adapter(mp3model));
				model.registerObserver((BPMObserver)this);
				model.registerObserver((BeatObserver)this);
				setController(new MP3Controller(mp3model,this));
			}
			
			//Al final siempre habilito los botones para cambiar de modelo 
			this.enableDJStartMenuItem();
			this.enableHeartStartMenuItem();
			this.enableMP3StartMenuItem();
		}
    }
    
    //Actualiza BPM usando patron observer
	public void updateBPM() {
		if (model != null) {
			int bpm = model.getBPM();
			//Reviso que modelo se usa para ver que mostrar en la BeatBar
			if (model instanceof MP3Adapter){
				bpmOutputLabel.setText("Pista numero " + (bpm+1));
			}
			else{
				if (bpm == 0) {
					if (bpmOutputLabel != null) {
	        			bpmOutputLabel.setText("offline");
					}
				} else {
					if (bpmOutputLabel != null) {
						if(model instanceof HeartAdapter)
							bpmOutputLabel.setText("Numero de intentos: " + bpm);
						else
							bpmOutputLabel.setText("Current BPM: " + model.getBPM());
					}
				}
			}
			
		}
	}
  
	public void updateBeat() {
		if (beatBar != null) {
			 beatBar.setValue(100);
		}
	}
	//Setters de controlador y modelo para implementar patron Strategy
	public void setController(ControllerInterface controller) {
		this.controller = controller;
	}
	
	public void setModel(BeatModelInterface newModel){
		this.model = newModel;
	}
}
