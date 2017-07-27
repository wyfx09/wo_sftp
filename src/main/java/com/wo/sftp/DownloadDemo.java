package com.wo.sftp;
 
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException; 
import com.wo.sftputil.ConsoleJschFileProgressMonitor;
import com.wo.sftputil.ConsoleJschLogger;
import com.wo.sftputil.SftpUtil;
 

public class DownloadDemo {
	public static void main(String[] args) {
       	SftpUtil.SetLogger(new ConsoleJschLogger());
		SftpUtil sftp = new SftpUtil("127.0.0.1", 22, "ucs", "123456");
        try {
        	ChannelSftp cs = sftp.init(); 
			cs.get("/out/nvidia.zip", "F:\\SFTP\\local\\");  
		} catch (JSchException e) {
			e.printStackTrace();
		} catch (SftpException e) {
			e.printStackTrace();
		} finally {
			sftp.destroy();
		}
	}
}
