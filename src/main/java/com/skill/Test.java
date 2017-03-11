package com.skill;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		System.out.println(decodeSpecialChars("'0001"));
		Map<Integer,String> map = new HashMap<Integer,String>();
		//for()
		long time1 = System.currentTimeMillis();
		System.out.println(new Date(time1));
		for(int i=0;i<3000000;i++){
			map.put(i, "aaaaaaaa");
		}
		long time2 = System.currentTimeMillis();
		System.out.println((time2-time1)+"毫秒");
		System.out.println(new Date(time2));
	}
//	public static String decodeSpecialChars(String content) {
//		// 单引号是oracle字符串的边界,oralce中用2个单引号代表1个单引号
//		String afterDecode = content.replaceAll("'", "''");
//		// 由于使用了/作为ESCAPE的转义特殊字符,所以需要对该字符进行转义
//		// 这里的作用是将"a\a"转成"a\\a"
//		afterDecode = afterDecode.replaceAll("\\\\", "\\\\\\\\");
//		// 这里的作用是将"a/a"转成"a//a"
//		afterDecode = afterDecode.replaceAll("/", "//");
//		// 使用转义字符 /,对oracle特殊字符% 进行转义,只作为普通查询字符，不是模糊匹配
//		afterDecode = afterDecode.replaceAll("%", "/%");
//		// 使用转义字符 /,对oracle特殊字符_ 进行转义,只作为普通查询字符，不是模糊匹配
//		afterDecode = afterDecode.replaceAll("_", "/_");
//		return afterDecode;
//	}
}
