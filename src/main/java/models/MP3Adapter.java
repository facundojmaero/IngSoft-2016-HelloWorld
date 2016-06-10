	package main.java.models;

	import main.java.views.BPMObserver;
	import main.java.views.BeatObserver;

	public class MP3Adapter implements BeatModelInterface{
		MP3ModelInterface player;
		
		public MP3Adapter (MP3ModelInterface player){
			this.player = player;
		}
		
		@Override
		public void initialize() {
			// TODO Auto-generated method stub
		}

		@Override
		public void on() {
			// TODO Auto-generated method stub
		}

		@Override
		public void off() {
			// TODO Auto-generated method stub
		}

		@Override
		public void setBPM(int bpm) {
			// TODO Auto-generated method stub
		}

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