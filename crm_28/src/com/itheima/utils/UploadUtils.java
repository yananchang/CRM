package com.itheima.utils;

import java.util.UUID;

/**
 * 文件上传的工具类
 * @author Yanan Chang
 *
 */
public class UploadUtils {

	/**
	 * 传入文件的名称,返回的唯一的名称
	 * 例如: girl.jpg 返回sdfsdfdf.jpg
	 * @return
	 */
	public static String getUUIDName(String filename){
		//先查找
		int index = filename.lastIndexOf(".");
		// 截取后缀名
		String lastname = filename.substring(index, filename.length());
		
		//唯一字符串
		String uuid = UUID.randomUUID().toString().replace("-", "");
		
		return uuid+lastname;
	}
}
