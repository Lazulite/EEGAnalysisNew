package com.lw.eeg.Main;
import java.io.File;

import javax.swing.JFileChooser;


public class VLCPlayer {
	public static JFileChooser ourFileSelector = new JFileChooser();
	public VLCPlayer(){
		
	}
	public void play(){
		String vlcPath="", mediaPath="";
		File ourFile;
		ourFileSelector.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		ourFileSelector.showSaveDialog(null);
		ourFile = ourFileSelector.getSelectedFile();
		vlcPath = ourFile.getAbsolutePath();
		
		ourFileSelector.setFileSelectionMode(JFileChooser.FILES_ONLY);
		ourFileSelector.showSaveDialog(null);
		ourFile = ourFileSelector.getSelectedFile();
		mediaPath=ourFile.getAbsolutePath();
		new MediaPlayer(vlcPath, mediaPath).run();
		
	}
}
