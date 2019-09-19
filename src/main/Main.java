/**   
 * Description: TODO(描述)
 * @FileName: Main.java
 * @Package: main
 * @author: 杨哲
 * @date: 2017年9月27日 上午11:07:01
 */

package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

/**
 * Description: TODO(描述)
 * 
 * @ClassName: Main
 * @author: 杨哲
 * @date: 2017年9月27日 上午11:07:01
 */

public class Main {
	private static Properties pro = new Properties();
	static {
		InputStream is = Main.class.getResourceAsStream("/lib/config.properties");
		try {
			pro.load(new InputStreamReader(is, "UTF-8"));
			is.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private static final boolean isDebug = true;

	public static void main(String[] args) {
		if (isDebug) {
			String filePathName = pro.getProperty("mergeFile");
			OptExcel.writeExcel(filePathName, Main.callProcess(OptExcel.readExcel(filePathName)));
		} else {
			System.out.println("输入START开始执行；Q或EXIT退出！");
			Scanner scan = new Scanner(System.in);
			while (true) {
				String read = scan.nextLine();
				if (read.toLowerCase().equals("exit") || read.toLowerCase().equals("q")) {
					break;
				}
				if (read.toLowerCase().equals("start")) {
					String filePathName = pro.getProperty("mergeFile");
					OptExcel.writeExcel(filePathName, Main.callProcess(OptExcel.readExcel(filePathName)));
					System.out.println("执行完毕，输入START继续执行；Q或EXIT退出！");
				} else {
					System.out.println("输入START开始执行；Q或EXIT退出！");
				}
			}
			scan.close();
		}
	}

	public static List<String> callProcess(List<Map<String, String>> dataList) {
		try {
			Iterator<Map<String, String>> it = dataList.iterator();
			List<String> list = new ArrayList<String>();
			int count = dataList.size();
			while (it.hasNext()) {
				System.out.println("还有：" + count-- + "个文件");
				Map<String, String> map = it.next();
				String file = map.get("file").substring(map.get("file").indexOf("/", 1));
				boolean isDir = file.endsWith("/");
				String readLine = "";
				// 获取版本号
				String commandInfo = "cmd.exe /c \"" + pro.getProperty("svnPath") + "\" info";
				commandInfo += " " + pro.getProperty("trunkPath") + file;
				Process processInfo = Runtime.getRuntime().exec(commandInfo);
				BufferedReader bufferedReaderInfo = new BufferedReader(new InputStreamReader(
						processInfo.getInputStream(), "GBK"));
				String versionNum = "";
				while ((readLine = bufferedReaderInfo.readLine()) != null) {
					if (readLine.indexOf("Last Changed Rev: ") != -1) {
						versionNum = readLine.replaceAll("Last Changed Rev: ", "");
					}
				}
				bufferedReaderInfo.close();
				processInfo.waitFor();
				if (!versionNum.equals("")) {
					// 如果合并文件为目录则更新跟目录
					boolean update = false;
					if(isDir){
						String commandUpdate = "cmd.exe /c \"" + pro.getProperty("svnPath") + "\" update --ignore-externals";
						commandUpdate += " " + pro.getProperty("trunkPath");
						Process processUpdate = Runtime.getRuntime().exec(commandUpdate);
						BufferedReader bufferedReaderUpdate = new BufferedReader(new InputStreamReader(
								processUpdate.getInputStream(), "GBK"));
						while (bufferedReaderUpdate.readLine() != null) {
							update = true;
						}
						bufferedReaderUpdate.close();
						processUpdate.waitFor();
					}else{
						update = true;
					}
					// 获取更新文件
					boolean merge = false;
					if(update){
						String commandMerge = "cmd.exe /c \"" + pro.getProperty("svnPath") + "\" merge" + (isDir ? " --accept mc" : "");
						commandMerge += " " + pro.getProperty("branchURL") + file + "@" + versionNum;
						commandMerge += " " + pro.getProperty("branchURL") + file;
						commandMerge += " " + pro.getProperty("trunkPath") + file;
						Process processMerge = Runtime.getRuntime().exec(commandMerge);
						BufferedReader bufferedReaderMerge = new BufferedReader(new InputStreamReader(
								processMerge.getInputStream(), "GBK"));
						while (bufferedReaderMerge.readLine() != null) {
							merge = true;
						}
						bufferedReaderMerge.close();
						processMerge.waitFor();
					}
					// 提交文件-文件夹不提交
					boolean commit = false;
					if(isDir){
						if(merge){
							commit = true;
						}
					}else{
						String commandCommit = "cmd.exe /c \"" + pro.getProperty("svnPath") + "\" commit  -m "
								+ map.get("interfaceCode") + "-" + map.get("information") + "-" + map.get("author");
						commandCommit += " " + pro.getProperty("trunkPath") + file;
						Process processCommit = Runtime.getRuntime().exec(commandCommit);
						BufferedReader bufferedReaderCommit = new BufferedReader(new InputStreamReader(
								processCommit.getInputStream(), "GBK"));
						while (bufferedReaderCommit.readLine() != null) {
							commit = true;
						}
						bufferedReaderCommit.close();
						processCommit.waitFor();
					}
					//写入完成标识
					if (commit) {
						list.add(map.get("file"));
					}
				}
			}
			return list;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}
}
