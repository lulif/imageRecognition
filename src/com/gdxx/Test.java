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
	protected static String[] headers = { "企业注册号", "企业名称" };
	protected static String photo[];
	protected static String valCode;
	protected static hssfexcel ex1;
	protected static Collection<message> dataset;
	private static Scanner sc;
//	public  static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式


	public static void NeedTarget() {
		ex1 = new hssfexcel();
		dataset = new ArrayList<message>();
		File file = new File(path);
		photo = file.list();              //测试图片路径下的所有图片名       	
	   Arrays.sort(photo, new Comparator<String>()
	   {
		@Override       //重写compare函数 按图片名大小排序
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
		Find.target = sc.nextLine();   //输入目标文件
		li.clear();
		Find.find(path, li);
		for (String s : li)
			path = s;             //路径赋值给path
	
//		System.out.println(df.format(new Date()));
		NeedTarget();

		// 普通单线程
//		Execute Exec = new Execute();
//		Exec.run();
		
		// Fork/Join框架
		 ForkJoinPool forkJoinPool = new ForkJoinPool();     //new一个线程池
		 parallel task = new parallel(0, Test.photo.length);
		 Future<Void> result = forkJoinPool.submit(task);     //提交任务
		 result.get();

//		System.out.println(df.format(new Date()));
		OutputStream out1 = new FileOutputStream(path + "/message.xls");  //写入Excel表格
		ex1.exportexcel("message", headers, dataset, out1, "");
		out1.close();

	}

}
