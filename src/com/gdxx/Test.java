package com.gdxx;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

public class Test {

	public static List<String> li = new ArrayList<String>();
	protected static String extensionname;
	protected static String companynumber;
	protected static String companyname;
	protected static String path = "C:\\";
	protected static String[] headers = { "��ҵע���", "��ҵ����" };
	protected static String photo[];
	protected static String valCode;
	protected static hssfexcel ex1;
	protected static Collection<message> dataset;
	private static Scanner sc;
//	public  static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// �������ڸ�ʽ


	public static void NeedTarget() {
		ex1 = new hssfexcel();
		dataset = new ArrayList<message>();
		File file = new File(path);
		photo = file.list();              //����ͼƬ·���µ�����ͼƬ��       	
	   Arrays.sort(photo, new Comparator<String>()
	   {
		@Override       //��дcompare���� ��ͼƬ����С����
		public int compare(String strA, String strB) {
		     String []arrA=strA.split("\\.");
		     String []arrB=strB.split("\\.");
		     if(arrA[0].equals("message")||arrB[0].equals("message"))
		     return 0;
		     
		     int a=Integer.valueOf(arrA[0]);
		     int b=Integer.valueOf(arrB[0]);
			return a<b?-1:1;
     }	
	   });
	}

	public static void main(String[] args) throws Exception {
	
		sc = new Scanner(System.in);  
		Find.target = sc.nextLine();   //����Ŀ���ļ�
		li.clear();
		Find.find(path, li);
		for (String s : li)
			path = s;             //·����ֵ��path
	
//		System.out.println(df.format(new Date()));
		NeedTarget();

		// ��ͨ���߳�
//		Execute Exec = new Execute();
//		Exec.run();
		
		// Fork/Join���
		 ForkJoinPool forkJoinPool = new ForkJoinPool();     //newһ���̳߳�
		 parallel task = new parallel(0, Test.photo.length);
		 Future<Void> result = forkJoinPool.submit(task);     //�ύ����
		 result.get();

//		System.out.println(df.format(new Date()));
		OutputStream out1 = new FileOutputStream(path + "/message.xls");  //д��Excel���
		ex1.exportexcel("message", headers, dataset, out1, "");
		out1.close();

	}

}
