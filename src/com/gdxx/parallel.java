package com.gdxx;

import java.io.File;
import java.util.concurrent.RecursiveAction;

public class parallel extends RecursiveAction {

	private static final long serialVersionUID = 1L;
	private static final int THRESHOLD = 8;
	private int start;
	private int end;
	protected Object leftleftResult;
	protected Object rightrightResult;
	protected Object leftResult;
	protected Object rightResult;

	public parallel(int start, int end) {
		this.start = start;
		this.end = end;
	}

	@Override
	protected void compute() {
		boolean canCompute = (end - start) <= THRESHOLD;  //�ж�����ķ�Χ��С  �Ƿ�Ϸ�
		if (canCompute) {        //���Ϸ���ִ��
			for (int i = start; i < end; i++) {
//				System.out.println(Test.photo[i]);
				Test.companynumber = "";
				Test.companyname = "";
				//System.out.println(Test.photo[i]);
				String photopath = Test.path + "/" + Test.photo[i]; 
				String arr[] = Test.photo[i].split("\\.");
				String format = arr[arr.length - 1];   //�ָ��ַ���ȡ��ʽ
				Test.extensionname = format;

				if (Test.extensionname.equals("xls"))
					continue;

				try {                           
					Test.valCode = new Recognition().recognizeText(new File(photopath), Test.extensionname);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String[] array1 = Test.valCode.split("\\n");
//                  for(int k=0;k<array1.length;k++)
//                  {
//                	  System.out.println(k+" : "+array1[k]);           	 
//                  }
				//ȡ������
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
				Test.dataset.add(new message(Test.companyname, Test.companynumber));//new message������뼯����
			}
		} else {      //���Ϸ������ָ����������
			int middle = (start + end) / 2;
			parallel childTask1 = new parallel(start, middle);
			parallel childTask2 = new parallel(middle, end);
			invokeAll(childTask1, childTask2);  //�ϲ��߳�
			leftResult = childTask1.join();
			rightResult = childTask2.join();
		}
		return;
	}

}