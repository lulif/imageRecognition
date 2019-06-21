package com.gdxx;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.Collection;

import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFComment;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;


public class hssfexcel {
	private HSSFWorkbook workbook;
	private String textValue;

	
	public void exportexcel(String title, String[] headers, Collection<message> dataset,
			OutputStream out, String pattern) 
					throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException, NoSuchMethodException, SecurityException {

	
		workbook = new HSSFWorkbook();  
		HSSFSheet sheet = workbook.createSheet(title);
	/*****************************************************************/	
		sheet.setDefaultColumnWidth(25);
		HSSFCellStyle style = (HSSFCellStyle) workbook.createCellStyle();
		//style.setFillBackgroundColor(HSSFColor.SKY_BLUE.index);
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		style.setAlignment(HorizontalAlignment.CENTER);
		HSSFFont font =  workbook.createFont();
		//font.setColor(HSSFColor.VIOLET.index);
		font.setBold(true);
		// 把字体应用到当前样式
		style.setFont(font);
		// 生成并设置另一个样式
		HSSFCellStyle style2 = workbook.createCellStyle();
		// style2 .setFillForegroundColor(HSSFColor LIGHT_ BLUE.index);
		style2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style2.setBorderBottom(BorderStyle.THIN);
		style2.setBorderLeft(BorderStyle.THIN);
		style2.setBorderRight(BorderStyle.THIN);
		style2.setBorderTop(BorderStyle.THIN);
		style2.setAlignment(HorizontalAlignment.CENTER);
/************************************************************************/
		
		

		
		
		
	/*******************************************************************************/
		// 生成另一个字体
		HSSFFont font2 =  workbook.createFont();
		font2.setBold(true);
		// 把字体应用到当前样式
		style.setFont(font2);
		// 声明一个画图的顶级管理器
		HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
		// 定义注释的大小和位置，详见文档
		HSSFComment comment = patriarch.createCellComment(new HSSFClientAnchor(0, 0, 0, 0, (short) 4, 2, (short) 6, 5));
		// 设置注释内容
		comment.setString(new HSSFRichTextString("长春理工大学光电信息学院"));
		// 设置注释作者，当鼠标移动到单元格上可以在状态栏中看到该内容 jingyenJbaidu.com
		comment.setAuthor(" o(*￣▽￣*)o");
		HSSFRow row = sheet.createRow(0);
/***********************************************************/
		
		
		
		
		//设置两个标题的
		for (short i = 0; i < headers.length; i++) {
			HSSFCell cell = row.createCell(i);
			//cell.setCellStyle(style);
			HSSFRichTextString text = new HSSFRichTextString(headers[i]);
			cell.setCellValue(text);
		}

		
		
		//传入的
		Iterator<message> it = dataset.iterator();
		
		int index = 0;
		while (it.hasNext()) {
			index++;
			row = sheet.createRow(index);
			message t =  it.next();
			Field[] fields = t.getClass().getDeclaredFields();
			//System.out.println(fields.length);
			for (int i = 0; i < fields.length; i++) {
				HSSFCell cell = row.createCell(i);
				//cell.setCellStyle(style2);
				//利用反射机制获取方法名
				Field field = fields[i];
				String fieldName = field.getName();
				String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
				textValue = null;
//				try {
					Class<? extends message> tCls = t.getClass();
					Method getMethod = tCls.getDeclaredMethod(getMethodName, new Class[] {});
					Object value = getMethod.invoke(t, new Object[] {});
					textValue = value.toString();
					cell.setCellValue(textValue);
					
//				} catch (Exception e) {
//					e.printStackTrace();
//				} finally {  }
				
			}
			
		}
		workbook.write(out);
	}

	
}


