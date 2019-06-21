package com.gdxx;

import java.io.BufferedReader;  
import java.io.File;  
import java.io.FileInputStream;  
import java.io.InputStreamReader;  
import java.util.ArrayList;  
import java.util.List;
import java.util.Map;

import org.jdesktop.swingx.util.OS;  
  
public class Recognition {  
	
    private String workspace;    
    private final String LANG_OPTION = "-l";   
    private final String EOL = System.getProperty("line.separator");  
    private String project="Image Recognition/tool/Tesseract-OCR";
   
    public String recognizeText(File imageFile,String extensionname)throws Exception{
    	workspace="";
    	String temp=Recognition.class.getResource("/").getPath();
		String arr[]=temp.split("/");
		for(int i=1;i<=arr.length-3;i++)
		{
			workspace+=arr[i]+"/";
		}
        File tempImage = ImageConversion.createImage(imageFile,extensionname);  
        File outputFile = new File(imageFile.getParentFile(),"output");  
        StringBuffer strB = new StringBuffer();  
        List<String> cmd = new ArrayList<String>();  
        if(OS.isWindowsXP()){  
            cmd.add(workspace+project+"/tesseract");  
        }else if(OS.isLinux()){  
            cmd.add("tesseract");  
        }else{  
            cmd.add(workspace+project+"/tesseract");  
        }  
        cmd.add("");  
        cmd.add(outputFile.getName());  
        cmd.add(LANG_OPTION);  
        cmd.add("chi_sim");  
//        cmd.add("eng");  
          
        ProcessBuilder pb = new ProcessBuilder();  
        pb.directory(imageFile.getParentFile());  
        
        //单独配置
        Map <String,String> env = pb.environment(); 
        env.put("TESSDATA_PREFIX",workspace+project+"/tessdata"); 
            
          
        cmd.set(1, tempImage.getName());  
        pb.command(cmd);  
        pb.redirectErrorStream(true);  
        Process process = pb.start();     //执行
        
        
        
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        while((bufferedReader.readLine()) != null);
        int w = process.waitFor();  
          
        //删除临时正在工作文件  
        tempImage.delete();  
          
        if(w==0){  
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(outputFile.getAbsolutePath()+".txt"),"UTF-8"));  
              
            String str;  
            while((str = in.readLine())!=null){  
                strB.append(str).append(EOL);  
            }  
            in.close();  
        }else{  
            String msg;  
            switch(w){  
                case 1:  
                    msg = "Errors accessing files.There may be spaces in your image's filename.";  
                    break;  
                case 29:  
                    msg = "Cannot recongnize the image or its selected region.";  
                    break;  
                case 31:  
                    msg = "Unsupported image format.";  
                    break;  
                default:  
                    msg = "Errors occurred.";  
            }  
            tempImage.delete();  
            throw new RuntimeException(msg);  
        }  
        new File(outputFile.getAbsolutePath()+".txt").delete();  //删除output.txt
        return strB.toString();  
    }  
}  