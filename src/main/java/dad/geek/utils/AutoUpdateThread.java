package dad.geek.utils;

import dad.geek.controllers.MainController;

public class AutoUpdateThread extends Thread {
//	private MainController controller;
	
	public AutoUpdateThread(MainController controller) {
//		this.controller = controller;
		}
	
	@Override
	public synchronized void run() {
//		controller.reloadPosts();
		super.run();
	}
}
