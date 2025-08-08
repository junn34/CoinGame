package coingame.controller;

public class Timer extends Thread {
	private int limitSeconds;
	private boolean timeOver = false;
	private boolean running = true; // 타이머 동작 플래그
	
	public Timer(int seconds) {
		this.limitSeconds = seconds;
	}
	
	@Override
	public void run() {
		try {
			for(int i = limitSeconds; i > 0 && running; --i) {
//				System.out.println("/r남은 시간: " + i + "초");
				System.out.flush();
				Thread.sleep(1000);
			}
			if (running) {
				timeOver = true;
				System.out.println("TIME OVER");
			}
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public boolean isTimeOver() {
		return timeOver;
	}
	
	public void stopTimer() {
		running = false;
	}

}
