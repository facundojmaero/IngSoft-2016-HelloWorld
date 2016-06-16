package test.java.models;

import java.lang.reflect.Field;

import main.java.models.MP3Model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MP3ModelTest {
	MP3Model mp3Model = null;
	
	@Before
	public void setUp() throws Exception {
		System.out.println("Create Singleton");
		mp3Model = MP3Model.getInstance();
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
	}
	
	@Test
	public void testGetCurrentPlaylist(){
		String[] playList = mp3Model.getCurrentPlaylist();
		
		assertEquals(0, playList.length); 
	}
	
	@Test
	public void testAddPlayList(){
		String[] playList = mp3Model.getCurrentPlaylist();
		
		assertEquals(0, playList.length); 
		
		mp3Model.addPlayList("./src/main/resources/default songs");
		
		assertEquals(4, mp3Model.getCurrentPlaylist().length); 
	}
	
	@Test
	public void testClearPlayList(){
		mp3Model.addPlayList("./src/main/resources/default songs");
		
		assertEquals(4, mp3Model.getCurrentPlaylist().length); 
		
		mp3Model.clearPlaylist();

		String[] playList = mp3Model.getCurrentPlaylist();
		
		assertEquals(0, playList.length); 
	}
	
	@Test
	public void testGetPlayListSize(){
		assertEquals(0, mp3Model.getPlaylistSize()); 
		
		mp3Model.addPlayList("./src/main/resources/default songs");
		
		assertEquals(4, mp3Model.getPlaylistSize());
	}
	
	@Test
	public void testGetIndex(){
		assertEquals(0, mp3Model.getPlaylistSize());
		
		assertEquals(0, mp3Model.getIndex());
		
		mp3Model.addPlayList("./src/main/resources/default songs");

		assertEquals(4, mp3Model.getPlaylistSize());
		
		assertEquals(0, mp3Model.getIndex());
	}
	
	@Test
	public void testSetIndex(){
		assertEquals(0, mp3Model.getPlaylistSize());
		
		int indexValue = mp3Model.getIndex();
		
		assertFalse(mp3Model.setIndex(2));
		
		assertEquals(indexValue, mp3Model.getIndex());
		
		mp3Model.addPlayList("./src/main/resources/default songs");

		assertEquals(4, mp3Model.getPlaylistSize());
		
		assertTrue(mp3Model.setIndex(2));
		
		assertEquals(2, mp3Model.getIndex());
	}
	
	@Test
	public void testPlayNow(){
		assertEquals(0, mp3Model.getPlaylistSize()); 
		
		mp3Model.addPlayList("./src/main/resources/default songs");

		assertEquals(4, mp3Model.getPlaylistSize());
		
		mp3Model.playNow(2);
		
		assertEquals(2, mp3Model.getIndex());
	}
	
	@Test
	public void testIsPlaying(){
		assertEquals(0, mp3Model.getPlaylistSize());
		
		assertFalse(mp3Model.IsPlaying());
		
		mp3Model.addPlayList("./src/main/resources/default songs");

		assertEquals(4, mp3Model.getPlaylistSize());

		mp3Model.playNow(2);
		
		assertTrue(mp3Model.IsPlaying());
	}
	
	

}