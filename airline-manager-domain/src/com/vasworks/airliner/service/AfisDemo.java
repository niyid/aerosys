package com.vasworks.airliner.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import sourceafis.simple.AfisEngine;
import sourceafis.simple.Fingerprint;
import sourceafis.simple.Person;

public class AfisDemo {
	
	static final AfisEngine afisEngine = new AfisEngine();
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		AfisDemo demo = new AfisDemo();
		
		demo.enroll(args[0], args[1]);

	}

	private Person enroll(String fileName, String personName) {
		Person person = new Person();
		
		if(person.getFingerprints() == null) {
			person.setFingerprints(new ArrayList<Fingerprint>());
		}
		
		try {
			BufferedImage bufferedImage = ImageIO.read((new File(fileName)));
			
			Fingerprint fingerprint = new Fingerprint();
			
			fingerprint.setImage(fetchBytes2D(bufferedImage));
			
			person.getFingerprints().add(fingerprint);
			
			afisEngine.extract(person);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return person;
	}
	
	private byte[][] fetchBytes2D(BufferedImage bufferedImage) {
		int height = bufferedImage.getHeight();
		int width = bufferedImage.getWidth();
		
		byte[][] data = new byte[height][width];
		
		int pixel = 0;
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				pixel = bufferedImage.getRGB(x, y);
				data[y][x] = (byte) (pixel << 16 | pixel << 8 | pixel);
			}			
		}
		
		return data;
	}
}
