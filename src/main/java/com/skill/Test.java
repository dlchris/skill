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
		System.out.println((time2-time1)+"����");
		System.out.println(new Date(time2));
	}
//	public static String decodeSpecialChars(String content) {
//		// ��������oracle�ַ����ı߽�,oralce����2�������Ŵ���1��������
//		String afterDecode = content.replaceAll("'", "''");
//		// ����ʹ����/��ΪESCAPE��ת�������ַ�,������Ҫ�Ը��ַ�����ת��
//		// ����������ǽ�"a\a"ת��"a\\a"
//		afterDecode = afterDecode.replaceAll("\\\\", "\\\\\\\\");
//		// ����������ǽ�"a/a"ת��"a//a"
//		afterDecode = afterDecode.replaceAll("/", "//");
//		// ʹ��ת���ַ� /,��oracle�����ַ�% ����ת��,ֻ��Ϊ��ͨ��ѯ�ַ�������ģ��ƥ��
//		afterDecode = afterDecode.replaceAll("%", "/%");
//		// ʹ��ת���ַ� /,��oracle�����ַ�_ ����ת��,ֻ��Ϊ��ͨ��ѯ�ַ�������ģ��ƥ��
//		afterDecode = afterDecode.replaceAll("_", "/_");
//		return afterDecode;
//	}
}
