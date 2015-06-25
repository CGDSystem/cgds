package com.cgds.postecontrol.ihm;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DirectColorModel;
import java.awt.image.IndexColorModel;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

public class Visualisation {

	protected Object result;
	protected Shell shell;
	private String nomDrone;

	private Composite composite;
	
	public Visualisation(Display display, String nomDrone) {
		this.nomDrone = nomDrone;
		shell = new Shell(display, SWT.CLOSE);
		createContents();
		shell.open();
        shell.addListener(SWT.Close, new Listener()
        {
           @Override
           public void handleEvent(Event arg0)
           {
              System.out.println("Child Shell handling Close event, about to dispose this Shell");
              shell.dispose();
           }

 
        });
	}
	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shell.setSize(400, 300);
		shell.setText(nomDrone + " - Visualisation");
		composite = new Composite(shell, SWT.NONE);
		composite.setBounds(0, 0, 400, 300);
	
	}

	public void update(byte[] image){
		if (!shell.getDisplay().isDisposed()) {
			UpdateAsyncrone update = new UpdateAsyncrone(image);
			Display.getDefault().asyncExec(update);
		}
	}
	
	public class UpdateAsyncrone implements Runnable {

		byte[] image = new byte[1];
		
		
		public UpdateAsyncrone(byte[] bytes) {
			this.image = bytes;
		}
		
		@Override
		public void run() {

			try {
				ByteArrayInputStream in = new ByteArrayInputStream(image);
		        Iterator<?> readers = ImageIO.getImageReadersByFormatName("png");
		        
		        //ImageIO is a class containing static methods for locating ImageReaders
		        //and ImageWriters, and performing simple encoding and decoding. 
		 
		        ImageReader reader = (ImageReader) readers.next();
		        Object source = in; 
		        ImageInputStream iis = ImageIO.createImageInputStream(source); 
		        reader.setInput(iis, true);
		        ImageReadParam param = reader.getDefaultReadParam();
		 
		        java.awt.Image image = reader.read(0, param);
		 
		        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
			    Graphics2D bGr = bufferedImage.createGraphics();
			    bGr.drawImage(image, 0, 0, null);
			    bGr.dispose();
				ImageData imageData = convertToSWT(bufferedImage);
				Image byteImage = new Image(composite.getDisplay(), imageData );
				composite.setBackgroundImage(byteImage);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
		public ImageData convertToSWT(BufferedImage bufferedImage) {
			if (bufferedImage.getColorModel() instanceof DirectColorModel) {
				DirectColorModel colorModel = (DirectColorModel) bufferedImage.getColorModel();
				PaletteData palette = new PaletteData(
					colorModel.getRedMask(),
					colorModel.getGreenMask(),
					colorModel.getBlueMask()
				);
				ImageData data = new ImageData(
					bufferedImage.getWidth(),
					bufferedImage.getHeight(), colorModel.getPixelSize(),
					palette
				);
				WritableRaster raster = bufferedImage.getRaster();
				int[] pixelArray = new int[3];
				for (int y = 0; y < data.height; y++) {
					for (int x = 0; x < data.width; x++) {
						raster.getPixel(x, y, pixelArray);
						int pixel = palette.getPixel(
							new RGB(pixelArray[0], pixelArray[1], pixelArray[2])
						);
						data.setPixel(x, y, pixel);
					}
				}
				return data;
			} else if (bufferedImage.getColorModel() instanceof IndexColorModel) {
				IndexColorModel colorModel = (IndexColorModel) bufferedImage.getColorModel();
				int size = colorModel.getMapSize();
				byte[] reds = new byte[size];
				byte[] greens = new byte[size];
				byte[] blues = new byte[size];
				colorModel.getReds(reds);
				colorModel.getGreens(greens);
				colorModel.getBlues(blues);
				RGB[] rgbs = new RGB[size];
				for (int i = 0; i < rgbs.length; i++) {
					rgbs[i] = new RGB(reds[i] & 0xFF, greens[i] & 0xFF, blues[i] & 0xFF);
				}
				PaletteData palette = new PaletteData(rgbs);
				ImageData data = new ImageData(
					bufferedImage.getWidth(),
					bufferedImage.getHeight(),
					colorModel.getPixelSize(),
					palette
				);
				data.transparentPixel = colorModel.getTransparentPixel();
				WritableRaster raster = bufferedImage.getRaster();
				int[] pixelArray = new int[1];
				for (int y = 0; y < data.height; y++) {
					for (int x = 0; x < data.width; x++) {
						raster.getPixel(x, y, pixelArray);
						data.setPixel(x, y, pixelArray[0]);
					}
				}
				return data;
			}
			return null;
		}
		
	}
}
