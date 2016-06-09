package main.java.models;

import main.java.views.BPMObserver;
import main.java.views.BeatObserver;

public interface HeartModelInterface {
	int getHeartRate();

	void registerObserver(BeatObserver o);

	void removeObserver(BeatObserver o);

	void registerObserver(BPMObserver o);

	void removeObserver(BPMObserver o);
}
