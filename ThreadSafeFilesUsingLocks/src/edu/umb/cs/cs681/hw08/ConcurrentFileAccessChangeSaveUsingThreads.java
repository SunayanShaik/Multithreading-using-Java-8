package edu.umb.cs.cs681.hw08;

public class ConcurrentFileAccessChangeSaveUsingThreads {
	public static void main(String[] args) {
		
		File aFile = new File();
		Editor editorRunnable = new Editor(aFile);
		AutoSaver autoSaverRunnable = new AutoSaver(aFile);
		
		Thread editorThread = new Thread(editorRunnable, "Thread-Editor");
		Thread autoSaverThread = new Thread(autoSaverRunnable, "Thread-AutoSaver");
		
		editorThread.start();
		autoSaverThread.start();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		editorRunnable.setDone();
		autoSaverRunnable.setDone();
	}
}
