package com.gdxx;

import java.io.File;
import java.util.List;

public class Find {
	public static String target ;
	//用于存放目标文件的路径
	public static List<String> find(String base, List<String> resultFileName) {
		
		File file = new File(base);                   
		File[] tempList = file.listFiles();  //列出此目录下的所有文件
		
		if(tempList==null)          //判断是否为空
	    	return resultFileName;
		
		for(File f:tempList){ 
			if(f.isHidden())      //是否为隐藏文件
				continue;
			if (f.isDirectory()) {	      //是否为目录
				String arr[] = f.toString().split("\\\\"); 
				if (arr[arr.length - 1].equals(target)) { 	    //判断是否为目标文件			
					 resultFileName.add(f.getAbsolutePath());
					 return resultFileName;
				} 		
				else                                                  
					 find(f.getAbsolutePath(),resultFileName);      //不是目标文件 就去递归
			}
		}
		return resultFileName;
	}
}