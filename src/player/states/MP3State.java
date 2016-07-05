package player.states;

public interface MP3State {
	void play();
	void paused();
	void addPlaylist();
	void nextSong();
	void previousSong();
	void stop();

}
