package com.test;

public final class HardToMock {

    private final String s;

    public HardToMock(String s) {
        this.s = s;
    }

    public final String finalMethod() {
		return "HardToMock value";
	}
	public final native String nativeMethod();
}
