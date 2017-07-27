package com.wo.sftputil;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import com.jcraft.jsch.*;
import com.jcraft.jsch.ChannelSftp.LsEntry;

public class SftpUtil {

	private String host;
	private int port;
	private String username;
	private String password;
	private String identity;
	private String passphrase;

	private JSch jsch = null;
	private Session session = null;
	private Channel channel = null;
	private ChannelSftp channelSftp = null;

	public ChannelSftp getChannelSftp() {
		return channelSftp;
	}

	/**
	 * @param filename
	 *            文件路径
	 * @param passphrase
	 *            密钥密码
	 * @param keytype
	 *            密钥类型，如com.jcraft.jsch.KeyPair.RSA
	 * @param keysize
	 *            密钥长度
	 * @param comment
	 *            公钥备注
	 */
	public static void GenerateKeyPair(String filename, String passphrase, int keytype, int keysize, String comment) {
		JSch j = new JSch();

		KeyPair kpair = null;
		try {
			kpair = KeyPair.genKeyPair(j, keytype, keysize);
			kpair.writePrivateKey(filename, passphrase.getBytes("UTF-8"));
			kpair.writePublicKey(filename + ".pub", comment);
			System.out.println("Finger print: " + kpair.getFingerPrint());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (kpair != null)
				kpair.dispose();
		}
	}

	/**
	 * @param host
	 *            主机地址
	 * @param port
	 *            ssh端口
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @throws JSchException
	 */
	public SftpUtil(String host, int port, String username, String password) {
		this.username = username;
		this.password = password;
		this.host = host;
		this.port = port;
	}

	/**
	 * @param username
	 *            用户名
	 * @param host
	 *            主机地址
	 * @param port
	 *            ssh端口
	 * @param identity
	 *            密钥路径
	 * @param passphrase
	 *            密钥密码
	 */
	public SftpUtil(String host, int port, String username, String identity, String passphrase) {
		this.username = username;
		this.host = host;
		this.port = port;
		this.identity = identity;
		this.passphrase = passphrase;
	}

	public ChannelSftp init() throws JSchException {
		this.jsch = new JSch();
		try {
			this.session = this.jsch.getSession(this.username, this.host, this.port);
			this.session.setConfig(getSshConfig());
			if (this.identity != null && !this.identity.isEmpty()) {
				this.jsch.addIdentity(this.identity, passphrase);
			} else {
				this.session.setPassword(this.password);
			}
			this.session.connect();
			this.channel = session.openChannel("sftp");
			channel.connect();
			this.channelSftp = (ChannelSftp) this.channel;
			return channelSftp;
		} catch (JSchException e) {
			System.out.println(e.getMessage());
			throw e;
		}
	}

	/**
	 * 设置日志记录器
	 * 
	 * @param logger
	 */
	public static void SetLogger(Logger logger) {
		JSch.setLogger(logger);
	}

	/**
	 * 设置服务配置
	 * 
	 * @return
	 */
	private Properties getSshConfig() {
		Properties sshConfig = null;
		sshConfig = new Properties();
		sshConfig.put("StrictHostKeyChecking", "no");
		return sshConfig;
	}

	/**
	 * 获取SFTP指定目录上的所有文件
	 * 
	 * @param source
	 *            目录
	 * @return 文件实体列表
	 * @throws SftpException
	 */
	public List<LsEntry> getAllFiles(String source) throws SftpException {
		List<LsEntry> list = null;
		Vector<?> v = null;
		v = channelSftp.ls(source);
		if (v != null && v.capacity() > 0) {
			list = new ArrayList<ChannelSftp.LsEntry>();
			for (Object item : v) {
				list.add((LsEntry) item);
			}
		}
		return list;
	}

	public void destroy() {
		if (this.session != null)
			this.session.disconnect();
	}

	protected void finalize() {
		destroy();
	}
}
