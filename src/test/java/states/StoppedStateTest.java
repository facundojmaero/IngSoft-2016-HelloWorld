package test.java.states;

import static org.junit.Assert.*;

import java.lang.reflect.Field;

import javazoom.jlgui.basicplayer.BasicPlayer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.java.models.MP3Model;
import main.java.states.MP3State;

public class StoppedStateTest {
	private MP3Model mp3Model = null;
	private MP3State stoppedState = null;
	private String playListPath = "src/main/resources/default songs/";
	private String pinkPantherSongName = "Pink Panther Theme.mp3";
	private String missionImpossibleSongName = "Mission Impossible Theme.mp3";
	
	private String[] currentPlaylist = null;
	private int index = 0;
	private BasicPlayer player = null;
	private int playListSize = 0;
	private double volumen = 1.0;
	private boolean isPlaying = false;
	
	
	@Before
	public void setUp() throws Exception {
		System.out.println("Create Singleton");
		mp3Model = MP3Model.getInstance();
		mp3Model.addPlayList(playListPath);
		mp3Model.stop();
		stoppedState = mp3Model.getStoppedState();
		
		this.currentPlaylist = mp3Model.getCurrentPlaylist().clone();
		this.index = mp3Model.getIndex();
		this.player = mp3Model.getPlayer();
		this.playListSize = mp3Model.getPlaylistSize();
		this.volumen = mp3Model.getVolumen();
		this.isPlaying = mp3Model.IsPlaying();
	}
	
	@After
	public void tearDown() throws Exception {
		System.out.println("Delete Singleton");
		mp3Model.stop();
		mp3Model.getPlayer().stop();
		mp3Model.clearPlaylist();

		Field uniqueMP3 = MP3Model.class.getDeclaredField("uniqueMP3");
		uniqueMP3.setAccessible(true);
		uniqueMP3.set(null, null);
		
		this.currentPlaylist = null;
		this.index = 0;
		this.player = null;
		this.playListSize = 0;
		this.volumen = 1.0;
		this.isPlaying = false;
	}

	@Test
	public void testValues(){
		assertEquals(4, this.playListSize);
		assertEquals(0, this.index);
		assertTrue(Double.valueOf(1.0).equals(this.volumen));
		assertFalse(this.isPlaying);
	}

	@Test
	public void testPlay(){		
		stoppedState.play();
		
		assertEquals(this.currentPlaylist.length, mp3Model.getCurrentPlaylist().length);
		if(this.currentPlaylist.length > 0){
			for(int i = 0; i < this.currentPlaylist.length; i++){
				assertEquals(this.currentPlaylist[i], mp3Model.getCurrentPlaylist()[i]);
			}
		}
		assertEquals(this.index, mp3Model.getIndex());
		assertEquals(this.player, mp3Model.getPlayer());
		assertEquals(this.playListSize, mp3Model.getPlaylistSize());
		assertTrue(Double.valueOf(this.volumen).equals(mp3Model.getVolumen()));
		assertTrue(mp3Model.IsPlaying());
	}

	@Test
	public void testPaused(){
		stoppedState.paused();
		
		assertEquals(this.currentPlaylist.length, mp3Model.getCurrentPlaylist().length);
		if(this.currentPlaylist.length > 0){
			for(int i = 0; i < this.currentPlaylist.length; i++){
				assertEquals(this.currentPlaylist[i], mp3Model.getCurrentPlaylist()[i]);
			}
		}
		assertEquals(this.index, mp3Model.getIndex());
		assertEquals(this.player, mp3Model.getPlayer());
		assertEquals(this.playListSize, mp3Model.getPlaylistSize());
		assertTrue(Double.valueOf(this.volumen).equals(mp3Model.getVolumen()));
		assertEquals(this.isPlaying, mp3Model.IsPlaying());
	}

	@Test
	public void testNextSong(){
		assertFalse(mp3Model.IsPlaying());
		assertEquals(0, mp3Model.getIndex());
		stoppedState.nextSong();
		
		assertEquals(this.currentPlaylist.length, mp3Model.getCurrentPlaylist().length);
		if(this.currentPlaylist.length > 0){
			for(int i = 0; i < this.currentPlaylist.length; i++){
				assertEquals(this.currentPlaylist[i], mp3Model.getCurrentPlaylist()[i]);
			}
		}
		assertEquals(1, mp3Model.getIndex());
		assertEquals(this.player, mp3Model.getPlayer());
		assertEquals(this.playListSize, mp3Model.getPlaylistSize());
		assertTrue(Double.valueOf(this.volumen).equals(mp3Model.getVolumen()));
		assertTrue(mp3Model.IsPlaying());
	}

	@Test
	public void testPreviousSong(){
		assertFalse(mp3Model.IsPlaying());
		assertEquals(0, mp3Model.getIndex());
		stoppedState.previousSong();
		
		assertEquals(this.currentPlaylist.length, mp3Model.getCurrentPlaylist().length);
		if(this.currentPlaylist.length > 0){
			for(int i = 0; i < this.currentPlaylist.length; i++){
				assertEquals(this.currentPlaylist[i], mp3Model.getCurrentPlaylist()[i]);
			}
		}
		assertEquals(3, mp3Model.getIndex());
		assertEquals(this.player, mp3Model.getPlayer());
		assertEquals(this.playListSize, mp3Model.getPlaylistSize());
		assertTrue(Double.valueOf(this.volumen).equals(mp3Model.getVolumen()));
		assertTrue(mp3Model.IsPlaying());
	}

	@Test
	public void testStop(){
		stoppedState.stop();
		
		assertEquals(this.currentPlaylist.length, mp3Model.getCurrentPlaylist().length);
		if(this.currentPlaylist.length > 0){
			for(int i = 0; i < this.currentPlaylist.length; i++){
				assertEquals(this.currentPlaylist[i], mp3Model.getCurrentPlaylist()[i]);
			}
		}
		assertEquals(this.index, mp3Model.getIndex());
		assertEquals(this.player, mp3Model.getPlayer());
		assertEquals(this.playListSize, mp3Model.getPlaylistSize());
		assertTrue(Double.valueOf(this.volumen).equals(mp3Model.getVolumen()));
		assertEquals(this.isPlaying, mp3Model.IsPlaying());
	}

	@Test
	public void testAddPlayList(){
		mp3Model.clearPlaylist();
		assertEquals(0, mp3Model.getPlaylistSize());
		
		stoppedState.addPlaylist(playListPath + pinkPantherSongName);
		assertEquals(1, mp3Model.getPlaylistSize());
		
		mp3Model.stop();
		
		assertNotEquals(this.currentPlaylist.length, mp3Model.getCurrentPlaylist().length);
		assertEquals(this.index, mp3Model.getIndex());
		assertEquals(this.player, mp3Model.getPlayer());
		assertEquals(1, mp3Model.getPlaylistSize());
		assertTrue(Double.valueOf(this.volumen).equals(mp3Model.getVolumen()));
		assertFalse(mp3Model.IsPlaying());
		
		stoppedState.addPlaylist(playListPath + missionImpossibleSongName);
		assertEquals(2, mp3Model.getPlaylistSize());
		assertTrue(mp3Model.IsPlaying());
	}
}
