package main.java.models;

public class ProgressThread implements Runnable{
	Thread thread;
	int maximum;
	int value;
	boolean playing;
	MP3Model model;

	public ProgressThread() {
		thread = new Thread(this);
		thread.start();
		playing = false;
	}
	public void run() {
		while(true) {
			if(playing){
				value++;
				if(value==maximum+1){
					model.nextSong();
					value = 0;
				}
				model.notifyProgressObservers(value);
				try {
					Thread.sleep(1000);
				} catch (Exception e) {};	
			}
			else {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void setMax(int i){
		maximum = i;
	}
	public void start(){
		playing = true;
	}
	public void stop(){
		playing = false;
	}
	public void reset(){
		value = 0;
		model.notifyProgressObservers(value);
	}
	public void setModel(MP3Model newModel){
		model = newModel;
	}
}
