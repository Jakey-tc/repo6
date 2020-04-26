package com.mysql;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.util.ExcelUtil;

/**
 * 
 * Excel导入Mysql
 * 会找到目录下最新被修改的文件，将其数据导入mysql中，且每次导入会删除原来的数据，
 * 
 * @version 1.0
 * @author jakey
 * @date 2019年6月26日 下午4:24:07
 */
public class ExcelToMysql {

	static final String DB_URL = "jdbc:mysql://localhost:3306/test?serverTimezone=UTC"; // 数据库 URL
	static final String USER = "root"; // 数据库的用户名
	static final String PASS = "351997"; // 数据库的密码
	private static String PATH = null; // Excel文件所在的路径
	private static final String TABLE = "sheet1"; // 数据库表名
	private static List<String> FIELDLIST = Arrays.asList("价格", "年龄", "年级"); // 数据库字段



	public static void main(String[] args) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		//获取最新修改的excel文件目录和文件名
		String path = "C:\\Users\\25269\\Desktop\\excel\\";
		File file = new File(path);
		File[] files = file.listFiles();
		Map<String,String> map1 = new HashMap();
		//创建一个变量存储每一个文件的修改时间（从1970到现在的毫秒数），用于比较获得最大的一个即最新的excel文件
		Long beforeFileTime = 0L;
		//遍历当前目录下的所有文件，将修改时间最新的文件名存储到Map集合中，且key值是newFile
		for(File fil:files) {
			
			if(fil.lastModified() > beforeFileTime) {
				 beforeFileTime = fil.lastModified();
				 map1.put("newFile",fil.getName());
			}
		}
		//获取最新的文件名
		String newFile = map1.get("newFile");
		//拼接目录和excel文件名，获取绝对路径
		PATH = "C:\\Users\\25269\\Desktop\\excel\\" + newFile;

		try {
			// 打开链接
			System.out.println("连接数据库...");
			connection = DriverManager.getConnection(DB_URL, USER, PASS);
			pstmt = connection.prepareStatement("truncate table sheet1");
			pstmt.execute();
			StringBuffer sql = new StringBuffer("insert into ");
			sql.append(TABLE);
			sql.append("(");
			for (int i = 0; i < FIELDLIST.size(); i++) {
				sql.append(FIELDLIST.get(i) + ",");
			}
			sql.deleteCharAt(sql.length() - 1);
			sql.append(") values ");
			// 拼接数据
			List<Map<String, Object>> dataList = ExcelUtil.getExcelData(PATH);
			if (dataList != null && dataList.size() != 0) {
				for (Map<String, Object> map : dataList) {
					sql.append("(");
					for (int i = 0; i < FIELDLIST.size(); i++) {
						sql.append("'" + map.get(FIELDLIST.get(i)) + "',");
					}
					sql.deleteCharAt(sql.length() - 1);
					sql.append("),");
				}
			}
			sql.deleteCharAt(sql.length() - 1);
			pstmt = (PreparedStatement) connection.prepareStatement(sql.toString());
			pstmt.execute();
			System.out.println("导入成功");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
