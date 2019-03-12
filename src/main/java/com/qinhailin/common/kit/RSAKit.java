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

package com.qinhailin.common.kit;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.math.BigInteger;

import java.net.URLDecoder;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.Cipher;

/**
 * RSA加密算法
 *
 * @author QinHaiLin
 * @date 2017年9月27日
 *
 */
public class RSAKit {

	private static String filename = "RSAKey.txt";

	/**
	 * 密文解密
	 *
	 * @param cipher
	 *            密文
	 * @return cipher 明文
	 * @throws Exception
	 * @author QinHaiLin
	 * @date 2018年3月13日
	 */
	public static String decryptionToString(String cipher) throws Exception {
		String result = "";

		byte[] enResultUserCode = hexStringToBytes(cipher);
		byte[] deResultUserCode = decrypt(getKeyPair().getPrivate(), enResultUserCode);

		StringBuffer sbUserCode = new StringBuffer();
		sbUserCode.append(new String(deResultUserCode));
		result = sbUserCode.reverse().toString();
		// 转译特殊字符	
		result =URLDecoder.decode(result, "UTF-8");
		
		return result.equals("") ? cipher : result;
	}

	/**
	 * Convert char to byte
	 *
	 * @param c
	 *            char
	 * @return byte
	 */
	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}

	/**
	 * * 解密 *
	 *
	 * @param pk
	 *            解密的密钥 *
	 * @param raw
	 *            已经加密的数据 *
	 * @return 解密后的明文 *
	 * @throws Exception
	 * @author QinHaiLin
	 * @date 2017年9月27日
	 */
	public static byte[] decrypt(PrivateKey pk, byte[] raw) throws Exception {
		try {
			Cipher cipher = Cipher.getInstance("RSA",
					new org.bouncycastle.jce.provider.BouncyCastleProvider());
			cipher.init(Cipher.DECRYPT_MODE, pk);
			int blockSize = cipher.getBlockSize();
			ByteArrayOutputStream bout = new ByteArrayOutputStream(64);
			int j = 0;

			while ((raw.length - (j * blockSize)) > 0) {
				bout.write(cipher.doFinal(raw, j * blockSize, blockSize));
				j++;
			}
			return bout.toByteArray();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * * 加密 *
	 *
	 * @param pk
	 *            加密的密钥 *
	 * @param data
	 *            待加密的明文数据 *
	 * @return 加密后的数据 *
	 * @throws Exception
	 * @author QinHaiLin
	 * @date 2017年9月27日
	 */
	public static byte[] encrypt(PublicKey pk, byte[] data) throws Exception {
		try {
			Cipher cipher = Cipher.getInstance("RSA",
					new org.bouncycastle.jce.provider.BouncyCastleProvider());
			cipher.init(Cipher.ENCRYPT_MODE, pk);
			int blockSize = cipher.getBlockSize();// 获得加密块大小，如：加密前数据为128个byte，而key_size=1024
			// 加密块大小为127
			// byte,加密后为128个byte;因此共有2个加密块，第一个127
			// byte第二个为1个byte
			int outputSize = cipher.getOutputSize(data.length);// 获得加密块加密后块大小
			int leavedSize = data.length % blockSize;
			int blocksSize = leavedSize != 0 ? (data.length / blockSize) + 1
					: data.length / blockSize;
			byte[] raw = new byte[outputSize * blocksSize];
			int i = 0;
			while ((data.length - (i * blockSize)) > 0) {
				if ((data.length - (i * blockSize)) > blockSize) {
					cipher.doFinal(data, i * blockSize, blockSize, raw, i * outputSize);
				} else {
					cipher.doFinal(data, i * blockSize, data.length - (i * blockSize),
							raw, i * outputSize);
				}
				// 这里面doUpdate方法不可用，查看源代码后发现每次doUpdate后并没有什么实际动作除了把byte[]放到
				// ByteArrayOutputStream中，而最后doFinal的时候才将所有的byte[]进行加密，可是到了此时加密块大小很可能已经超出了
				// OutputSize所以只好用dofinal方法。

				i++;
			}
			return raw;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * * 生成密钥对 *
	 *
	 * @return KeyPair *
	 * @throws EncryptException
	 * @author QinHaiLin
	 * @date 2017年9月27日
	 */
	public static KeyPair generateKeyPair() throws Exception {
		try {
			KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA",
					new org.bouncycastle.jce.provider.BouncyCastleProvider());
			final int keySize = 1024;// 这个值关系到块加密的大小，可以更改，但是不要太大，否则效率会低
			keyPairGen.initialize(keySize, new SecureRandom());
			KeyPair keyPair = keyPairGen.generateKeyPair();

			System.out.println(keyPair.getPrivate());
			System.out.println(keyPair.getPublic());

			saveKeyPair(keyPair);
			return keyPair;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * * 生成私钥 *
	 *
	 * @param modulus
	 *            *
	 * @param privateExponent
	 *            *
	 * @return RSAPrivateKey *
	 * @throws Exception
	 * @author QinHaiLin
	 */
	public static RSAPrivateKey generateRSAPrivateKey(byte[] modulus,
			byte[] privateExponent) throws Exception {
		KeyFactory keyFac = null;
		try {
			keyFac = KeyFactory.getInstance("RSA",
					new org.bouncycastle.jce.provider.BouncyCastleProvider());
		} catch (NoSuchAlgorithmException ex) {
			throw new Exception(ex.getMessage());
		}

		RSAPrivateKeySpec priKeySpec = new RSAPrivateKeySpec(new BigInteger(modulus),
				new BigInteger(privateExponent));
		try {
			return (RSAPrivateKey) keyFac.generatePrivate(priKeySpec);
		} catch (InvalidKeySpecException ex) {
			throw new Exception(ex.getMessage());
		}
	}

	/**
	 * * 生成公钥 *
	 *
	 * @param modulus
	 *            *
	 * @param publicExponent
	 *            *
	 * @return RSAPublicKey *
	 * @throws Exception
	 * @author QinHaiLin
	 */
	public static RSAPublicKey generateRSAPublicKey(byte[] modulus, byte[] publicExponent)
			throws Exception {
		KeyFactory keyFac = null;
		try {
			keyFac = KeyFactory.getInstance("RSA",
					new org.bouncycastle.jce.provider.BouncyCastleProvider());
		} catch (NoSuchAlgorithmException ex) {
			throw new Exception(ex.getMessage());
		}

		RSAPublicKeySpec pubKeySpec = new RSAPublicKeySpec(new BigInteger(modulus),
				new BigInteger(publicExponent));
		try {
			return (RSAPublicKey) keyFac.generatePublic(pubKeySpec);
		} catch (InvalidKeySpecException ex) {
			throw new Exception(ex.getMessage());
		}
	}

	/**
	 * 获取密钥对
	 *
	 * @return
	 * @throws Exception
	 * @author QinHaiLin
	 * @date 2017年12月15日下午4:50:43
	 */
	public static KeyPair getKeyPair() throws Exception {
		// String path=ClassLoader.getSystemResource(filename).toString();
		// path=path.replace("file:/","");
		// FileInputStream fis=new FileInputStream(path);
		InputStream is = RSAKit.class.getClassLoader().getResourceAsStream(filename);
		ObjectInputStream oos = new ObjectInputStream(is);
		KeyPair kp = (KeyPair) oos.readObject();
		oos.close();
		is.close();
		return kp;
	}

	/**
	 * 16进制 To byte[]
	 *
	 * @param hexString
	 * @return byte[]
	 * @author QinHaiLin
	 * @date 2017年9月27日
	 */
	public static byte[] hexStringToBytes(String hexString) {
		if ((hexString == null) || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) ((charToByte(hexChars[pos]) << 4)
					| charToByte(hexChars[pos + 1]));
		}
		return d;
	}

	/**
	 * 执行main生成密钥
	 *
	 * @param args
	 *            *
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
//		RSAPublicKey rsap = (RSAPublicKey) RSAUtil.generateKeyPair().getPublic();
//		String test = "qinhailin";
//		byte[] enTest = encrypt(getKeyPair().getPublic(), test.getBytes());
//		byte[] deTest = decrypt(getKeyPair().getPrivate(), enTest);
//		System.out.println(new String(deTest));
	}

	/**
	 * 保存密钥对
	 *
	 * @param kp
	 * @throws Exception
	 * @author QinHaiLin
	 * @date 2017年12月15日下午4:53:00
	 */
	public static void saveKeyPair(KeyPair kp) throws Exception {
		String path = ClassLoader.getSystemResource(filename).toString();
		path = path.replace("file:/", "");
		FileOutputStream fos = new FileOutputStream(path);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		// 生成密钥对
		oos.writeObject(kp);
		oos.close();
		fos.close();
	}

}
