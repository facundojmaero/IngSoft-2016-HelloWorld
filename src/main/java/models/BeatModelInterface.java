package main.java.models;

import main.java.views.BPMObserver;
import main.java.views.BeatObserver;

public interface BeatModelInterface {
	void initialize();

	void on();

	void off();

	void setBPM(int bpm);

	int getBPM();

	void registerObserver(BeatObserver o);

	void removeObserver(BeatObserver o);

	void registerObserver(BPMObserver o);

	void removeObserver(BPMObserver o);
}
