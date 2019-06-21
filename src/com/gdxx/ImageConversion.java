package com.gdxx;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Locale;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;

import com.sun.media.imageio.plugins.tiff.TIFFImageWriteParam;  
  
public class ImageConversion {  

    public static File createImage(File imageFile, String extensionname) {  
        File tempFile = null;  
        try {  
           // Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName(extensionname.toString());  
        	//��ȡ���е�ǰ��ע���ʶ��ͼ���ʽ�Ķ�ȡ��
        	Iterator<ImageReader> readers = ImageIO.getImageReaders(new FileImageInputStream(new File(imageFile.toString())));

        	ImageReader reader = readers.next();  
          
        	//��ָ�����ļ�����ͼ��������
            ImageInputStream iis = ImageIO.createImageInputStream(imageFile);  
            reader.setInput(iis);  
            //��ȡ��Ԫ���� 
            IIOMetadata streamMetadata = reader.getStreamMetadata();  
              
            //����д����� 
            TIFFImageWriteParam tiffWriteParam = new TIFFImageWriteParam(Locale.CHINESE);  
            tiffWriteParam.setCompressionMode(ImageWriteParam.MODE_DISABLED);  
              
            //��ȡTIFд���������������Ϊ�ļ�
            Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("tiff");  
            ImageWriter writer = writers.next();  
              
            BufferedImage bi = reader.read(0);  
            IIOImage image = new IIOImage(bi,null,reader.getImageMetadata(0));  
            tempFile = tempImageFile(imageFile);  
            ImageOutputStream ios = ImageIO.createImageOutputStream(tempFile);  
            writer.setOutput(ios);  
            writer.write(streamMetadata, image, tiffWriteParam);  
            ios.close();  
              
            writer.dispose();  
            reader.dispose();  
              
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return tempFile;  
    }  
 
    private static File tempImageFile(File imageFile) {  
        String path = imageFile.getPath();  
        StringBuffer strB = new StringBuffer(path);  
        strB.insert(path.lastIndexOf('.'),'#');  
        return new File(strB.toString().replaceFirst("(?<=//.)(//w+)$", "tif"));  
    }  
  
}  