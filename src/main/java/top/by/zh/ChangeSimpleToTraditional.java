package top.by.zh;

import com.spreada.utils.chinese.ZHConverter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
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

	public static void changeFileFromSimpleChineseToTradionalWithRootPath(String path, List<String> suffixNames, boolean debug) {
		ArrayList<String> tempArray = new ArrayList<String>();
		ArrayList<String> fileList = traverseFolder2(path, tempArray, suffixNames, debug);
		if (debug) {
			System.out.println("INFO：文件数组" + fileList);
		}

		if (fileList.size() == 0) {
			return;
		}
		
		for (int i = 0; i < fileList.size(); i++) {
			readOldFileAndWriteNewFileWithFilePath(i + 1, fileList.get(i), debug);
		}
	}

	public static void readOldFileAndWriteNewFileWithFilePath(int sno, String filePath, boolean debug) {
		//  简体转繁体 
		try {
			BufferedReader bufRead = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filePath))));
			StringBuffer strBuffer = new StringBuffer();

			StringBuffer before = new StringBuffer();

			for (String temp = null; (temp = bufRead.readLine()) != null; temp = null) {
				Pattern pattern = Pattern.compile("[\u4e00-\u9fcc]+");
				before.append(temp + "\n");
				if (pattern.matcher(temp).find()) {
					temp = getChinese(temp);
				}
				strBuffer.append(temp);
				strBuffer.append(System.getProperty("line.separator"));
			}
			if (debug) {
				System.out.println("---------------------------------------------------------------------------------");
				System.out.println("INFO：(" + sno + ")转换前内容(" + filePath + ")：\n" + before);
				System.out.println("---------------------------------------------------------------------------------");
				System.out.println("INFO：(" + sno + ")转换后内容(" + filePath + ")：\n" + strBuffer);
				System.out.println("---------------------------------------------------------------------------------");
			}
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
			ZHConverter converter2 = ZHConverter.getInstance(ZHConverter.TRADITIONAL);
			String traditionalStr = converter2.convert(matcher.group(0));

			replacedStr = replacedStr.replace(matcher.group(0), traditionalStr);
		}
		return replacedStr;
	}

	/**
	 * 迭代遍历传入的根文件夹，获取每一级文件夹的每个文件 并把文件名称以字符串形式装在数组返回 path：根文件夹路径
	 * listFileName：用于返回文件路径的数组，由于这个是迭代方法采用外部传入该数组
	 */
	public static ArrayList<String> traverseFolder2(String path, ArrayList<String> listFileName, List<String> suffixNames, boolean debug) {
		File file = new File(path);
		if (file.exists()) {
			File[] files = file.listFiles();
			if (files.length == 0) {
				if (debug) {
					System.out.println("WARN：[ " + path + " ] 文件夹是空的!");
				}
				// 文件夹为空
				return null;
			} else {
				for (File file2 : files) {
					if (file2.isDirectory()) {
						traverseFolder2(file2.getAbsolutePath(), listFileName, suffixNames, debug);
					} else {
						String sbsolutePath = file2.getAbsolutePath();
						if (isSuffixName(sbsolutePath, suffixNames)) {
							listFileName.add(file2.getAbsolutePath());
						}
					}
					if (debug) {
						System.out.println("INFO：[ " + file2.getAbsolutePath() + " ]");
					}
				}
			}
		} else {
			System.err.println("WARN：文件不存在!");
		}

		return listFileName;
	}

	/**
	 * 判断文件后缀名
	 *
	 * @param fileName    文件全名
	 * @param suffixNames 后缀名
	 * @return
	 */
	private static boolean isSuffixName(String fileName, List<String> suffixNames) {
		boolean is = false;

		for (String suffixName : suffixNames) {
			if (fileName.endsWith(suffixName)) {
				is = true;
				break;
			}
		}

		return is;
	}

}
