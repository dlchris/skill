package com.skill.enums;

public enum TestEnum {
	RED(1, "ºìÉ«"), BLUE(2, "À¶É«");

	private int id;
	private String name;

	TestEnum(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public static void main(String[] args) {
		System.out.println(TestEnum.values());
		System.out.println(TestEnum.BLUE.name());
	}
}
