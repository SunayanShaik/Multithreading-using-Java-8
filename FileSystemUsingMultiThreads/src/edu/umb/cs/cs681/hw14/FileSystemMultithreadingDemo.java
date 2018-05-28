package edu.umb.cs.cs681.hw14;

import java.util.ArrayList;
import java.util.Date;

public class FileSystemMultithreadingDemo {

	public static void main(String[] args) {
		
		// Building a tree structure
		Directory root, cDrive, dDrive, eDrive;
		File cFile1, cFile2, cFile3, dFile1, dFile2, dFile3, eFile1, eFile2, eFile3;
		Link x, y;
		int size = 0;
		FileSystem fileSystem;
		ArrayList<Directory> drives = new ArrayList<>();
		FileCrawler crawlerRunnable;
		FileIndexer indexerRunnable;
		
		Date created = new Date(System.currentTimeMillis() - 3600 * 1000);
		Date modified = new java.util.Date();
		root = new Directory(null, "root", "sunayan", created, modified, 0, false);
		cDrive = new Directory(root, "cDrive", "sunayan", created, modified, 0, false);
		dDrive = new Directory(root, "dDrive", "sunayan", created, modified, 0, false);
		eDrive = new Directory(root, "eDrive", "sunayan", created, modified, 0, false);
		root.appendChild(cDrive);
		root.appendChild(dDrive);
		root.appendChild(eDrive);
		
		drives.add(cDrive);
		drives.add(dDrive);
		drives.add(eDrive);
		
		cFile1 = new File(cDrive, "cFile1.txt", "sunayan", created, modified, 10, true);
		cFile2 = new File(cDrive, "cFile2.txt", "sunayan", created, modified, 20, true);
		cFile3 = new File(cDrive, "cFile3.png", "sunayan", created, modified, 30, true);
		
		cDrive.appendChild(cFile1);
		cDrive.appendChild(cFile2);
		cDrive.appendChild(cFile3);
		
		dFile1 = new File(dDrive, "dFile1.png", "sunayan", created, modified, 10, true);
		dFile2 = new File(dDrive, "dFile2.txt", "sunayan", created, modified, 20, true);
		dFile3 = new File(dDrive, "dFile3.png", "sunayan", created, modified, 30, true);		
		
		dDrive.appendChild(dFile1);
		dDrive.appendChild(dFile2);
		dDrive.appendChild(dFile3);
		
		eFile1 = new File(eDrive, "eFile1.png", "sunayan", created, modified, 10, true);
		eFile2 = new File(eDrive, "eFile2.txt", "sunayan", created, modified, 20, true);
		eFile3 = new File(eDrive, "eFile3.png", "sunayan", created, modified, 30, true);	
		
		eDrive.appendChild(eFile1);
		eDrive.appendChild(eFile2);
		eDrive.appendChild(eFile3);
		
		fileSystem = FileSystem.getFileSystem(root);
		System.out.println("Tree structure is: ");
		fileSystem.showAllElements();

		FileQueue fileQueue = new FileQueue();
		ArrayList<Thread> listThreads = new ArrayList<>();
		ArrayList<FileCrawler> listCrawlers = new ArrayList<>();
		ArrayList<FileIndexer> listIndexers = new ArrayList<>();
		
		//Creating threads for crawler and indexer
		for(Directory drive : drives) {
			crawlerRunnable = new FileCrawler(fileQueue, drive);
			listCrawlers.add(crawlerRunnable);
			Thread crawlerThread = new Thread(crawlerRunnable);
			listThreads.add(crawlerThread);
			
			indexerRunnable = new FileIndexer(fileQueue);
			listIndexers.add(indexerRunnable);
			Thread indexerThread = new Thread(indexerRunnable);
			listThreads.add(indexerThread);
	
			crawlerThread.start();	
			indexerThread.start();
		}
		
		try {
			Thread.sleep(100);
		} catch (InterruptedException ex) {
			System.out.println("[" + Thread.currentThread().getName() + "] " + ex.getMessage());
		}
		
		for(Thread thread : listThreads) {
			thread.interrupt();
		}
		
		for(FileCrawler crawlRunnable : listCrawlers) {
			crawlRunnable.setDone();
		}
		
		for(FileIndexer indexRunnable : listIndexers) {
			indexRunnable.setDone();
		}
		
	}

}
