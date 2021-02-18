package bnn.interfaces;


import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class ImageReader {
	
	public ImageReader() {}
	
	public ArrayList<String> read(String imageUrl) throws IOException{
		ArrayList<String> chromosomes = new ArrayList<String>();
		try {
			File input = new File(imageUrl);
			BufferedImage image = ImageIO.read(input);
			Image img = image.getScaledInstance(16, 16, Image.SCALE_DEFAULT);
			BufferedImage bufferedImage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_BYTE_BINARY);
			bufferedImage.getGraphics().drawImage(img, 0, 0, null);
			WritableRaster raster = (WritableRaster) bufferedImage.getRaster();
			DataBufferByte data   = (DataBufferByte) raster.getDataBuffer();
			byte iba[] =data.getData();
			for (int i=0; i< iba.length;i++) {
				String bi = String.format("%8s", Integer.toBinaryString(iba[i] & 0xFF)).replace(' ', '0');
				bi = bi.replace("0", "A");
				bi = bi.replace("1", "B");
				System.out.println("["+i+"]\t"+iba[i]+" \t"+bi);
				chromosomes.add(bi);
			}
		} catch (IOException e) {
			System.out.println(e);
		}			
		return chromosomes;
		
	}
	
	public static void training() {

		try {
			
			for (int n=1;n<10;n++) {
				System.out.println("No: "+n);
				
				File input = new File("resources\\image"+n+".bmp");
				File input2 = new File("resources\\imageM"+n+".bmp");
				
				BufferedImage image = ImageIO.read(input);
				BufferedImage image2 = ImageIO.read(input2);

				Image img = image.getScaledInstance(16, 16, Image.SCALE_DEFAULT);
				BufferedImage bufferedImage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_BYTE_BINARY);
				bufferedImage.getGraphics().drawImage(img, 0, 0, null);
				WritableRaster raster = (WritableRaster) bufferedImage.getRaster();
				DataBufferByte data   = (DataBufferByte) raster.getDataBuffer();

				Image img2 = image2.getScaledInstance(16, 16, Image.SCALE_DEFAULT);
				BufferedImage bufferedImage2 = new BufferedImage(img2.getWidth(null), img2.getHeight(null), BufferedImage.TYPE_BYTE_BINARY);
				bufferedImage2.getGraphics().drawImage(img2, 0, 0, null);
				WritableRaster raster2 = (WritableRaster) bufferedImage2.getRaster();
				DataBufferByte data2   = (DataBufferByte) raster2.getDataBuffer();
			

				byte iba[] =data.getData();
				byte iba2[] =data2.getData();
	    		for (int i=0; i< iba.length;i++) {
	    			

	    			
	    			String bi = String.format("%8s", Integer.toBinaryString(iba[i] & 0xFF)).replace(' ', '0');
	    			bi = bi.replace("0", "A");
	    			bi = bi.replace("1", "B");
	    			
	    			String bi2 = String.format("%8s", Integer.toBinaryString(iba2[i] & 0xFF)).replace(' ', '0');
	    			bi2 = bi2.replace("0", "A");
	    			bi2 = bi2.replace("1", "B"); 
	    			
	    			System.out.println("["+i+"]\t"+iba[i]+"\t"+iba2[i]+" \t"+bi+"\t"+bi2);
	    		}
			}
       

		} catch (IOException e) {
			System.out.println(e);
		}
	}
	
}
	
