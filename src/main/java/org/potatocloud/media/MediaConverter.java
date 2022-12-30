package org.potatocloud.media;

import org.potatocloud.media.model.Media;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MediaConverter {

	public static Media converter(String originalBase64) {

		return null;
	}

	private static String thumbnailGenerator(String original) {
		return "";
	}

	private BufferedImage resizeImage(File img, int targetWidth, int targetHeight) throws IOException {
		BufferedImage in = null;
		in = ImageIO.read(img);
		BufferedImage originalImage = new BufferedImage(
				in.getWidth(), in.getHeight(), BufferedImage.TYPE_INT_ARGB);
		BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics2D = resizedImage.createGraphics();
		graphics2D.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
		graphics2D.dispose();
		return resizedImage;
	}

}
