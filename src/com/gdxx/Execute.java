package com.gdxx;


import java.io.File;

public class Execute implements Runnable {
	public void run() {
		for (int i = 0; i < Test.photo.length; i++) {
	//	   System.out.println(Test.photo[i]);
			Test.companynumber="";   //���������
			Test.companyname="";
			
			String photopath = Test.path + "/" + Test.photo[i];
			
			String arr[]=Test.photo[i].split("\\.");
			String format= arr[arr.length-1];
			Test.extensionname = format;
			
			 if(Test.extensionname.equals("xls")) 	   
	        	   continue;
			 
				try {	
					Test.valCode = new Recognition().recognizeText(new File(photopath), Test.extensionname);
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//ȡ������
			String[] array1 = Test.valCode.split("\\n");
			Test.companynumber = array1[0].substring(array1[0].lastIndexOf(":") + 1);
			if (array1.length > 1) {
				for (int j = 1; j < 10; j++) {
					if (array1[j].contains("���޹�˾"))
						if (array1[j].contains(":")) {
							Test.companyname = array1[j].substring(array1[j].lastIndexOf(":") + 1);
							break;
						} else {
							Test.companyname = array1[j];
							break;
						}
				}
			}
			if(Test.companyname.equals(""))
			{
				Test.companynumber="��ͼƬ����ʶ��";
				Test.companyname="��ͼƬ����ʶ��";
			}
			if(Test.companyname.equals("��ͼƬ����ʶ��"))
				Test.companynumber="��ͼƬ����ʶ��";
			
			System.out.println(Test.companynumber);
			System.out.println(Test.companyname);

			Test.dataset.add(new message(Test.companyname, Test.companynumber));
		}
	}

}