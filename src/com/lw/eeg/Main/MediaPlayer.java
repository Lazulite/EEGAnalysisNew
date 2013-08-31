package com.lw.eeg.Main;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import uk.co.caprica.vlcj.*;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.component.EmbeddedMediaListPlayerComponent;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;
public class MediaPlayer {
	
		private JFrame ourFrame = new JFrame();
		private EmbeddedMediaListPlayerComponent ourMediaPlayer;
		private String mediaPath = "";
		
		public MediaPlayer(String vlcPath, String mediaURL) {
			// TODO Auto-generated constructor stub
			NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), "D:\\Program Files\\VideoLAN\\VLC");
			Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);
			this.mediaPath = mediaURL;
			NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcCoreName(), vlcPath);
			ourMediaPlayer = new EmbeddedMediaListPlayerComponent();
			ourFrame.setContentPane(ourMediaPlayer);
			ourFrame.setSize(600,400);
			ourFrame.setVisible(true);
			//ourFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			ourFrame.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e){
					ourFrame.dispose();
					ourMediaPlayer.getMediaPlayer().stop();
				}
			});
		}
		public void run(){
			ourMediaPlayer.getMediaPlayer().playMedia(mediaPath);
		}
}
