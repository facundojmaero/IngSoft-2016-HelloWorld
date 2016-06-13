	package main.java.models;

	import main.java.views.BPMObserver;
	import main.java.views.BeatObserver;

	public class MP3Adapter implements BeatModelInterface{
		MP3ModelInterface player;
		
		public MP3Adapter (MP3ModelInterface player){
			this.player = player;
		}
		
		@Override
		public void initialize() {}

		@Override
		public void on() {}

		@Override
		public void off() {}

		@Override
		public void setBPM(int bpm) {}

		@Override
		public int getBPM() {
			return player.getIndex();
		}

		@Override
		public void registerObserver(BeatObserver o) {
			player.registerObserver(o);
		}

		@Override
		public void removeObserver(BeatObserver o) {
			player.removeObserver(o);
		}

		@Override
		public void registerObserver(BPMObserver o) {
			player.registerObserver(o);
		}

		@Override
		public void removeObserver(BPMObserver o) {
			player.removeObserver(o);
		}

	}