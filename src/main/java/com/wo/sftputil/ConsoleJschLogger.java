package com.wo.sftputil;

import com.jcraft.jsch.Logger;

public class ConsoleJschLogger implements Logger {

	public boolean isEnabled(int arg0) {
		return true;
	}

	public void log(int level, String message) {
		System.out.println(String.format("[JSCH.Level%d: %s]", level, message));
	}

}
