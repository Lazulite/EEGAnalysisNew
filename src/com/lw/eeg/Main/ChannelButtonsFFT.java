package com.lw.eeg.Main;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.naming.InitialContext;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class ChannelButtonsFFT {
	
	public ChannelButtonsFFT(JPanel p){
		init(p);
	}

	private void init(JPanel tabPanel_1) {
		JRadioButton radioButton = new JRadioButton("AF3");
		radioButton.setBackground(Color.WHITE);
		radioButton.setBounds(63, 116, 51, 23);
		tabPanel_1.add(radioButton);
		radioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JRadioButton radioButton_1 = new JRadioButton("F7");
		radioButton_1.setBackground(Color.WHITE);
		radioButton_1.setBounds(63, 147, 51, 23);
		tabPanel_1.add(radioButton_1);
		radioButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JRadioButton radioButton_2 = new JRadioButton("F3");
		radioButton_2.setBackground(Color.WHITE);
		radioButton_2.setBounds(63, 178, 51, 23);
		tabPanel_1.add(radioButton_2);
		radioButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JRadioButton radioButton_3 = new JRadioButton("FC5");
		radioButton_3.setBackground(Color.WHITE);
		radioButton_3.setBounds(63, 209, 51, 23);
		tabPanel_1.add(radioButton_3);
		radioButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JRadioButton radioButton_4 = new JRadioButton("T7");
		radioButton_4.setBackground(Color.WHITE);
		radioButton_4.setBounds(63, 240, 51, 23);
		tabPanel_1.add(radioButton_4);
		radioButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JRadioButton radioButton_5 = new JRadioButton("P7");
		radioButton_5.setBackground(Color.WHITE);
		radioButton_5.setBounds(63, 274, 51, 23);
		tabPanel_1.add(radioButton_5);
		radioButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JRadioButton radioButton_6 = new JRadioButton("O1");
		radioButton_6.setBackground(Color.WHITE);
		radioButton_6.setBounds(63, 305, 51, 23);
		tabPanel_1.add(radioButton_6);
		radioButton_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JRadioButton radioButton_7 = new JRadioButton("O2");
		radioButton_7.setBackground(Color.WHITE);
		radioButton_7.setBounds(137, 116, 51, 23);
		tabPanel_1.add(radioButton_7);
		radioButton_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JRadioButton radioButton_8 = new JRadioButton("P8");
		radioButton_8.setBackground(Color.WHITE);
		radioButton_8.setBounds(137, 147, 51, 23);
		tabPanel_1.add(radioButton_8);
		radioButton_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JRadioButton radioButton_9 = new JRadioButton("T8");
		radioButton_9.setBackground(Color.WHITE);
		radioButton_9.setBounds(137, 178, 51, 23);
		tabPanel_1.add(radioButton_9);
		radioButton_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JRadioButton radioButton_10 = new JRadioButton("FC6");
		radioButton_10.setBackground(Color.WHITE);
		radioButton_10.setBounds(137, 209, 51, 23);
		tabPanel_1.add(radioButton_10);
		radioButton_10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JRadioButton radioButton_11 = new JRadioButton("F4");
		radioButton_11.setBackground(Color.WHITE);
		radioButton_11.setBounds(137, 240, 51, 23);
		tabPanel_1.add(radioButton_11);
		radioButton_11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JRadioButton radioButton_12 = new JRadioButton("F8");
		radioButton_12.setBackground(Color.WHITE);
		radioButton_12.setBounds(137, 271, 51, 23);
		tabPanel_1.add(radioButton_12);
		radioButton_12.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JRadioButton radioButton_13 = new JRadioButton("AF4");
		radioButton_13.setBackground(Color.WHITE);
		radioButton_13.setBounds(137, 302, 51, 23);
		tabPanel_1.add(radioButton_13);
		radioButton_13.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JRadioButton radioButton_14 = new JRadioButton("HR");
		radioButton_14.setBackground(Color.WHITE);
		radioButton_14.setBounds(63, 346, 51, 23);
		tabPanel_1.add(radioButton_14);
		radioButton_14.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
	}

}
