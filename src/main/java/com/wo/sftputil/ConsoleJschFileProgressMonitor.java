package com.wo.sftputil;

public class ConsoleJschFileProgressMonitor implements com.jcraft.jsch.SftpProgressMonitor {

    private long transfered;
	private long max;
	
    public long getTransfered() {
		return transfered;
	}
    
	public long getMax() {
		return max;
	}
	
	public void init(int op, String src, String dest, long max) {
		this.transfered = 0;
		this.max = max;
        System.out.println("传输开始：【" + op + "】【" + src + "】【" + dest + "】【" + max + "】");
	}

	public boolean count(long count) {
        transfered += count;
        System.out.println(String.format("已传输: %dK / %dK", transfered/1024, max/1024));
        return true;
	}

	public void end() {
        System.out.println("传输完成。");
	}

}
