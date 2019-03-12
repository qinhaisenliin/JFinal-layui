/**
 * Copyright 2019 覃海林(qinhaisenlin@163.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */ 

package com.qinhailin.common.util;

import java.net.InetAddress;

/**
 * GUID生成器，生成全球唯一id 可靠性：如果同一ip的不同进程在同一时间装载本类则会出现灾难后果 线程安全性：多线程安全
 * 
 * 使用方法：GUIDHexGenerator.getInstance().generateId();
 * 
 * 
 */
public class UUidUtiles {

	/**
	 * generateId，生成Id
	 * 
	 * @return String 返回GUID字符串
	 */
	public String createUUID() {
		String id = generateIdStringBuffer().toString();
		return id;
	}

	/**
	 * generateId，生成Id
	 * 
	 * @return StringBuffer 返回GUID字符串
	 */
	public StringBuffer generateIdStringBuffer() {
		return new StringBuffer(36).append(IPString).append(JVMString).append(
				format(getHiTime())).append(format(getLoTime())).append(
				format(getCount()));

	}

	/**
	 * 单例对象的获取函数
	 * 
	 * @return GUIDHexGenerator
	 */
	public static UUidUtiles getInstance() {
		return instance;
	}

	private static UUidUtiles instance = new UUidUtiles();

	private final int IP;
	{
		int ipadd;
		try {
			ipadd = BytesHelper.toInt(InetAddress.getLocalHost().getAddress());
		} catch (Exception e) {
			ipadd = 0;
		}
		IP = ipadd;
	}

	private final String IPString = format(IP);

	private short counter = (short) 0;

	private final int JVM = (int) (System.currentTimeMillis() >>> 8);

	private final String JVMString = format(JVM);

	/**
	 * 通过JVM使id好对于不同的JVM不重复，除非它们同时装载这个类
	 * 
	 * Unique across JVMs on this machine (unless they load this class in the
	 * same quater second - very unlikely)
	 */
	protected int getJVM() {
		return JVM;
	}

	/**
	 * Unique in a millisecond for this JVM instance (unless there are
	 * Short.MAX_VALUE instances created in a millisecond)
	 */

	protected short getCount() {
		synchronized (UUidUtiles.class) {
			counter++;
			if (counter < 0) {
				counter = 0;
			}
			return counter;
		}
	}

	/**
	 * IP地址使局域网内唯一，如果你用网卡物理号则全球唯一了：）
	 * 
	 * 
	 */

	protected int getIP() {
		return IP;
	}

	/**
	 * Unique down to millisecond
	 */
	protected short getHiTime() {
		return (short) (System.currentTimeMillis() >>> 32);
	}

	protected int getLoTime() {
		return (int) System.currentTimeMillis();
	}

	protected String format(int intval) {
		String formatted = Integer.toHexString(intval);
		StringBuffer buf = new StringBuffer("00000000");
		buf.replace(8 - formatted.length(), 8, formatted);
		return buf.toString();
	}

	protected String format(short shortval) {
		String formatted = Integer.toHexString(shortval);
		StringBuffer buf = new StringBuffer("0000");
		buf.replace(4 - formatted.length(), 4, formatted);
		return buf.toString();
	}

}

class BytesHelper {
	private BytesHelper() {
	}

	public static int toInt(byte[] bytes) {
		int result = 0;
		for (int i = 0; i < 4; i++) {
			result = (result << 8) - Byte.MIN_VALUE + (int) bytes[i];
		}
		return result;
	}

	public static short toShort(byte[] bytes) {
		return (short) (((-(short) Byte.MIN_VALUE + (short) bytes[0]) << 8)
				- (short) Byte.MIN_VALUE + (short) bytes[1]);
	}

}
