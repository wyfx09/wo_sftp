package com.wo.sftp;

import com.jcraft.jsch.KeyPair;
import com.wo.sftputil.SftpUtil;
 
//生产秘钥
public class KeyGen {
	public static void main(String[] arg) {

		String filename = "F:\\SFTP\\KeyGen\\id_rsa";
		int keytype = KeyPair.RSA;
		int keysize = 2048;
		String passphrase = "123456";
		String comment = "备注";
		
		SftpUtil.GenerateKeyPair(filename, passphrase, keytype, keysize, comment);
		System.exit(0);
	}
}
