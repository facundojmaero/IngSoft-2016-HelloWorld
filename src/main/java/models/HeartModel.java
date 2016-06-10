package main.java.models;

import java.util.ArrayList;
import java.util.Random;

import main.java.views.BPMObserver;
import main.java.views.BeatObserver;

import main.java.views.BPMObserver;
import main.java.views.BeatObserver;

public class HeartModel implements HeartModelInterface, Runnable {
	private static HeartModel unique_model;
	ArrayList beatObservers = new ArrayList();
	ArrayList bpmObservers = new ArrayList();
	int time = 1000;
	int bpm = 90;
	static int intentos = 0; // Numero de intentos de crear el modelo
	Random random = new Random(System.currentTimeMillis());
	Thread thread;

	private HeartModel() {
		thread = new Thread(this);
		thread.start();
	}

	public static HeartModel getInstance() {
		if (unique_model == null) {
			unique_model = new HeartModel();
		}
		intentos++;
		return unique_model;
	}

	public void run() {
		int lastrate = -1;

		for (;;) {
			int change = random.nextInt(10);
			if (random.nextInt(2) == 0) {
				change = 0 - change;
			}
			int rate = 60000 / (time + change);
			if (rate < 120 && rate > 50) {
				time += change;
				notifyBeatObservers();
				if (rate != lastrate) {
					lastrate = rate;
					notifyBPMObservers();
				}
			}
			try {
				Thread.sleep(time);
			} catch (Exception e) {
			}
		}
	}

	public int getHeartRate() {
		return 60000 / time;
	}

	public void registerObserver(BeatObserver o) {
		beatObservers.add(o);
	}

	public void removeObserver(BeatObserver o) {
		int i = beatObservers.indexOf(o);
		if (i >= 0) {
			beatObservers.remove(i);
		}
	}

	public void notifyBeatObservers() {
		for (int i = 0; i < beatObservers.size(); i++) {
			BeatObserver observer = (BeatObserver) beatObservers.get(i);
			observer.updateBeat();
		}
	}

	public void registerObserver(BPMObserver o) {
		bpmObservers.add(o);
	}

	public void removeObserver(BPMObserver o) {
		int i = bpmObservers.indexOf(o);
		if (i >= 0) {
			bpmObservers.remove(i);
		}
	}

	public void notifyBPMObservers() {
		for (int i = 0; i < bpmObservers.size(); i++) {
			BPMObserver observer = (BPMObserver) bpmObservers.get(i);
			observer.updateBPM();
		}
	}

	public int getIntentos() {
		// TODO Auto-generated method stub
		return intentos;
	}

}
