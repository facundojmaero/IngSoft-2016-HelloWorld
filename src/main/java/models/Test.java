package main.java.models;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MP3Model model = new MP3Model();
		model.setVolumen(0.1);
		model.addPlayList("/Users/holawe/Desktop/songs");
		model.play();
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.stop();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.play();
		model.setVolumen(0.9);
		
	}

}
