package com.poi;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/*
 * poi操作excel
 */
public class Test {
	
	public static void main(String[] args) throws IOException {
		
		//若使用2007版本就使用XSSFWorkbook实现类创建excel文件对象，也就是工作簿
		Workbook book = new XSSFWorkbook();//如果使用2003版本的excel就使用HSSFWorkbook实现类创建对象
		
		//创建一个表sheet，指定名称
		Sheet sheet = book.createSheet("test");
		
		//在表中创建行,参数是该行的索引，从0开始,这里表示创建第三行的对象
		Row row = sheet.createRow(2);
		//在行上创建单元格，参数是该单元格的索引，从0开始，这里表示创建第四个单元格对象
		Cell cell = row.createCell(3);
		//向第三行第四个单元格中写入值
		cell.setCellValue("学习强国");
		
		//创建单元格的样式对象，用工作簿对象来创建
		CellStyle style = book.createCellStyle();
		//设置四个边框样式
		style.setBorderTop(BorderStyle.THICK);
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.DASH_DOT);
		style.setBorderRight(BorderStyle.DASHED);
		//设置垂直居中
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		//设置水平居中
		style.setAlignment(HorizontalAlignment.CENTER);
	
		//创建字体对象
		Font font = book.createFont();
		//设置字体大小，参数是short类型的
		font.setFontHeightInPoints((short)20);
		//设置字体
		font.setFontName("华文行楷");
		//将字体对象放入样式对象中
		style.setFont(font);
		
		//将上面设置的样式设置到指定单元格中
		cell.setCellStyle(style);
		
		//设置指定行的行高
		row.setHeightInPoints(20);
		//设置指定列的宽度，第一个参数是列的索引，第二个参数是列宽，要乘以256
		sheet.setColumnWidth(3,30*256);
		
		//创建文件输出流，指定文件路径和文件名（即excel文件的路径和名字）
		FileOutputStream fos = new FileOutputStream("C:\\Users\\25269\\Desktop\\excel\\poi\\c.xlsx");
		
		//将excel的Workbook对象写入文件输出流中
		book.write(fos);
		
		//关闭文件输出流
		fos.close();
		
		
	}

}
