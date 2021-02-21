package com.api.gimart.util;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

public class FileUtils {

	// save uploaded file to a defined location on the server
	public static boolean SaveFile(String UPLOAD_PATH ,InputStream inputStream, FormDataContentDisposition fileMetaData) {

		//boolean isSaved=false;
		try {
			//System.out.println("Called"+UPLOAD_PATH);
			int read = 0;
			byte[] bytes = new byte[1024];

			OutputStream out = new FileOutputStream(new File(UPLOAD_PATH));
			while ((read = inputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			out.flush();
			out.close();
			return true;
		} catch (IOException e) {
			//System.out.println("Called"+e.toString());
			e.printStackTrace();
			return false;
		}

	}
	
	public static boolean SaveCompressedFile() {

		//boolean isSaved=false;
		try {
			File imageFile = new File("C:\\Users\\Ajaz PC\\Desktop\\Digital Learning Files\\jpeg-featured-image.jpg");
	        File compressedImageFile = new File("C:\\Users\\Ajaz PC\\Desktop\\Digital Learning Files\\compressedImageFile.jpg");
	 
	        InputStream is = new FileInputStream(imageFile);
	        OutputStream os = new FileOutputStream(compressedImageFile);
	 
	        float quality = 0.5f;
	 
	        // create a BufferedImage as the result of decoding the supplied InputStream
	        BufferedImage image = ImageIO.read(is);
	 
	        // get all image writers for JPG format
	        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
	 
	        if (!writers.hasNext())
	            throw new IllegalStateException("No writers found");
	 
	        ImageWriter writer = (ImageWriter) writers.next();
	        ImageOutputStream ios = ImageIO.createImageOutputStream(os);
	        writer.setOutput(ios);
	 
	        ImageWriteParam param = writer.getDefaultWriteParam();
	 
	        // compress to a given quality
	        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
	        param.setCompressionQuality(quality);
	 
	        // appends a complete image stream containing a single image and
	        //associated stream and image metadata and thumbnails to the output
	        writer.write(null, new IIOImage(image, null, null), param);
	 
	        // close all streams
	        is.close();
	        os.close();
	        ios.close();
	        writer.dispose();
			return true;
		} catch (IOException e) {
			//System.out.println("Called"+e.toString());
			e.printStackTrace();
			return false;
		}

	}

	
	public static boolean SaveResizeFile() {

		//boolean isSaved=false;
		try {
			File input = new File("C:\\Users\\Ajaz PC\\Desktop\\Digital Learning Files\\background.png");
	        BufferedImage image = ImageIO.read(input);

	        BufferedImage resized = resize(image, 500, 500);

	        File output = new File("C:\\Users\\Ajaz PC\\Desktop\\Digital Learning Files\\resized1500x500.png");
	        ImageIO.write(resized, "png", output);
	 
	        // close all streams
	       
			return true;
		} catch (IOException e) {
			//System.out.println("Called"+e.toString());
			e.printStackTrace();
			return false;
		}

	}
	
	 private static BufferedImage resize(BufferedImage img, int height, int width) {
	        Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
	        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	        Graphics2D g2d = resized.createGraphics();
	        g2d.drawImage(tmp, 0, 0, null);
	        g2d.dispose();
	        return resized;
	    }

	
	
	private static void createDirectoryInProject(String foldername) {
		String sRootPath = new File("").getAbsolutePath();
		// Utility.showMessage(TAG, sRootPath);

		File file = new File(sRootPath + "\\attachments\\" + foldername);
		if (!file.exists()) {
			if (file.mkdir()) {
				System.out.println("Directory is created!");
			} else {
				System.out.println("Failed to create directory!");
			}
		}
	}

	private static String GetFolderPath(String foldername) {
		String FolderPath = "";
		String sRootPath = new File("").getAbsolutePath();
		File file = new File(sRootPath + "\\attachments\\" + foldername);
		if (!file.exists()) {
			FolderPath = new File(sRootPath + "\\attachments\\" + foldername).getAbsolutePath();
		}
		return FolderPath;
	}

	
	public static String getFileExtension(File file) {
        String extension = "";
 
        try {
            if (file != null && file.exists()) {
                String name = file.getName();
                extension = name.substring(name.lastIndexOf("."));
            }
        } catch (Exception e) {
            extension = "";
        }
 
        return extension;
 
    }
	
	public static String getFileExtension(String filename) {
        String extension = "";
 
        try {
            if (filename != null && !filename.isEmpty()) {
                
                extension = filename.substring(filename.lastIndexOf("."));
            }
        } catch (Exception e) {
            extension = "";
        }
 
        return extension;
 
    }
	
	public static void main(String[] args) {
		//System.out.println(""+SaveCompressedFile());
		//System.out.println(""+SaveResizeFile());
	}
	

}
