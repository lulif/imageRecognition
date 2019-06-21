package com.gdxx;

import java.io.File;
import java.util.List;

public class Find {
	public static String target ;
	//���ڴ��Ŀ���ļ���·��
	public static List<String> find(String base, List<String> resultFileName) {
		
		File file = new File(base);                   
		File[] tempList = file.listFiles();  //�г���Ŀ¼�µ������ļ�
		
		if(tempList==null)          //�ж��Ƿ�Ϊ��
	    	return resultFileName;
		
		for(File f:tempList){ 
			if(f.isHidden())      //�Ƿ�Ϊ�����ļ�
				continue;
			if (f.isDirectory()) {	      //�Ƿ�ΪĿ¼
				String arr[] = f.toString().split("\\\\"); 
				if (arr[arr.length - 1].equals(target)) { 	    //�ж��Ƿ�ΪĿ���ļ�			
					 resultFileName.add(f.getAbsolutePath());
					 return resultFileName;
				} 		
				else                                                  
					 find(f.getAbsolutePath(),resultFileName);      //����Ŀ���ļ� ��ȥ�ݹ�
			}
		}
		return resultFileName;
	}
}