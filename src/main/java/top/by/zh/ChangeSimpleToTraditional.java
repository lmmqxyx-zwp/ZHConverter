package top.by.zh;

import com.spreada.utils.chinese.ZHConverter;

import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>Title: ChangeSimpleToTraditional.java</p>
 * <p>Description: 转换逻辑</p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: www.iceos.top</p>
 *
 * ----------------------------------------
 * ----------------------------------------
 * @author zwp
 * @date 2018年10月26日
 *
 * @version 1.0
 */
public class ChangeSimpleToTraditional {

	public static void changeFileFromSimpleChineseToTradionalWithRootPath(String path) {
		ArrayList<String> tempArray = new ArrayList<String>();
		ArrayList<String> fileList = traverseFolder2(path, tempArray);
		System.out.println("文件数组" + fileList);

		if (fileList.size() == 0) {
			return;
		}
		
		for (int i = 0; i < fileList.size(); i++) {
			readOldFileAndWriteNewFileWithFilePath(fileList.get(i));
		}
	}

	public static void readOldFileAndWriteNewFileWithFilePath(String filePath) {
		//  简体转繁体 
		try {
			BufferedReader bufRead = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filePath))));
			StringBuffer strBuffer = new StringBuffer();

			for (String temp = null; (temp = bufRead.readLine()) != null; temp = null) {
				Pattern pattern = Pattern.compile("[\u4e00-\u9fcc]+");
				if (pattern.matcher(temp).find()) {
					temp = getChinese(temp);
				}
				strBuffer.append(temp);
				strBuffer.append(System.getProperty("line.separator"));
			}
			System.out.println(strBuffer.toString());
			bufRead.close();
			PrintWriter printWriter = new PrintWriter(filePath);
			printWriter.write(strBuffer.toString().toCharArray());
			printWriter.flush();
			printWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 把读取的文件的每一行字符串进行正则匹配简体中文 并且把匹配到的简体中文替换为繁体 并返回替换后的字符串
	 * paramValue：读文件时候，读取到的每一行字符串
	 */
	public static String getChinese(String paramValue) {
		String regex = "([\u4e00-\u9fa5]+)";
		String replacedStr = paramValue;
		Matcher matcher = Pattern.compile(regex).matcher(paramValue);
		while (matcher.find()) {
			System.out.println("----------" + matcher.group(0));
			ZHConverter converter2 = ZHConverter.getInstance(ZHConverter.TRADITIONAL);
			String traditionalStr = converter2.convert(matcher.group(0));

			replacedStr = replacedStr.replace(matcher.group(0), traditionalStr);

			System.out.println("zyf" + traditionalStr + replacedStr);
		}
		return replacedStr;
	}

	/**
	 * 迭代遍历传入的根文件夹，获取每一级文件夹的每个文件 并把文件名称以字符串形式装在数组返回 path：根文件夹路径
	 * listFileName：用于返回文件路径的数组，由于这个是迭代方法采用外部传入该数组
	 */
	public static ArrayList<String> traverseFolder2(String path, ArrayList<String> listFileName) {
		File file = new File(path);
		if (file.exists()) {
			File[] files = file.listFiles();
			if (files.length == 0) {
				System.out.println("文件夹是空的!");
				return null;
			} else {
				for (File file2 : files) {
					if (file2.isDirectory()) {
						System.out.println("文件夹:" + file2.getAbsolutePath());
						traverseFolder2(file2.getAbsolutePath(), listFileName);
					} else {
						String sbsolutePath = file2.getAbsolutePath();
						if (sbsolutePath.endsWith(".jsp")
								|| sbsolutePath.endsWith(".js")
								|| sbsolutePath.endsWith(".html")
								|| sbsolutePath.endsWith(".java")
								|| sbsolutePath.endsWith(".inc")
								|| sbsolutePath.endsWith(".ftl")
								|| sbsolutePath.endsWith(".sql")) {
							listFileName.add(file2.getAbsolutePath());
						}

						System.out.println("文件:" + file2.getAbsolutePath());
					}
				}
			}
		} else {
			System.out.println("文件不存在!");
		}

		return listFileName;
	}

}
