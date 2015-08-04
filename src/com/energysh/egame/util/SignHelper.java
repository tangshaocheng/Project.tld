package com.energysh.egame.util;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 数字验签
 * 
 * @author rocker
 * 
 */
public class SignHelper {

	private final static Log log = LogFactory.getLog(SignHelper.class);
	public final static String encoding = "UTF-8";

	public static void main(String[] args) throws Exception {
		// testVerify();
		// testKeyFile();
		// testVerifyWiipay();
		testLogNotify();
	}

	/**
	 * 测试验签
	 * 
	 * @throws Exception
	 */
	public static void testVerify() throws Exception {
		String plainText = "operatorType=CM&operatorTypeTile=移动&channelCode=02240001&appCode=02240001&payCode=0001&imsi=460002494110084&tel=+8613912498256&state=success&price=1&bookNo=A07231804572504840&date=20130723180350&devPrivate=eyJhY2NvdW50IjoiVTUwMDAxIn0=";
		PropertiesConfiguration propConfig = new PropertiesConfiguration(System.getProperty("user.dir") + "/conf/environment.properties");
		String pubKeyStr = propConfig.getString("payNotify.pubKey");
		String priKeyStr = propConfig.getString("payNotify.priKey");
		byte[] sign = getSign(plainText, priKeyStr);
		boolean verify = SignHelper.verify(plainText, sign, pubKeyStr);
		if (verify)
			System.out.println("验签成功！verify：" + verify);
		else
			System.out.println("验签失败！verify：" + verify);
	}

	/**
	 * 测试生成密钥文件
	 * 
	 * @throws Exception
	 */
	public static void testKeyFile() throws Exception {
		Map<String, Key> rmap = getKeys();
		String pubKeyStr = getKeyString(rmap.get("publicKey"));
		String priKeyStr = getKeyString(rmap.get("privateKey"));
		System.out.println("pubKeyStr: " + pubKeyStr);
		System.out.println("priKeyStr: " + priKeyStr);
		PropertiesConfiguration propConfig = new PropertiesConfiguration(System.getProperty("user.dir") + "/conf/environment.properties");
		propConfig.setProperty("payNotify.pubKey", pubKeyStr);
		propConfig.setProperty("payNotify.priKey", priKeyStr);
		propConfig.save();
	}

	/**
	 * 测试日志通知
	 * 
	 * @throws Exception
	 */
	public static void testLogNotify() throws Exception {
		Map<String, Object> para = new LinkedHashMap<String, Object>();
		String feeType = "2";
		if ("2".equals(feeType)) { // IVR
			para.put("mobile", "13850011182");
			para.put("dest_num", "10655556089");
			para.put("price", 2.0);
			para.put("btime", "20131201080000");
			para.put("etime", "20131201080131");
			para.put("fee_type", 2);
		} else {
			para.put("mobile", "13850011182");
			para.put("dest_num", "10655556089");
			para.put("send_content", "XZZL@014A10C@0062@6335");
			para.put("link_id", "d196cfd1c3fa6a9637ab17de503b0cda");
			para.put("fee_state", 1);
			para.put("send_time", "20131201080000");
			para.put("fee_type", 1);
			para.put("price", 2.0);
			para.put("operator", 1);
		}
		String plainText = JSONObject.fromObject(para).toString();
		System.out.println(plainText);
		PropertiesConfiguration propConfig = new PropertiesConfiguration(System.getProperty("user.dir") + "/conf/environment.properties");
		String pubKeyStr = propConfig.getString("payNotify.pubKey");
		String priKeyStr = propConfig.getString("payNotify.priKey");
		byte[] sign = SignHelper.getSign(plainText, priKeyStr);
		String signStr = new String(sign, encoding);
		System.out.println(signStr);
		para.put("sign", signStr);
		String body = JSONObject.fromObject(para).toString();
		System.out.println(body);
		JSONObject jsonBean = JSONObject.fromObject(body);
		@SuppressWarnings("unchecked")
		Map<String, String> rmap = (Map<String, String>) JSONObject.toBean(jsonBean, LinkedHashMap.class);
		String sig = rmap.get("sign");
		sign = sig.getBytes(encoding);
		rmap.remove("sign");
		plainText = JSONObject.fromObject(rmap).toString();
		boolean verify = SignHelper.verify(plainText, sign, pubKeyStr);
		if (verify)
			System.out.println("验签成功！verify：" + verify);
		else
			System.out.println("验签失败！verify：" + verify);
	}

	/**
	 * 测试微派
	 * 
	 * @throws Exception
	 */
	public static void testVerifyWiipay() throws Exception {
		String plainText = "operatorType=CM&operatorTypeTile=移动&channelCode=200000&appCode=01360001&payCode=0002&imsi=460023212405022&tel=15021213653&state=success&price=2&bookNo=A11212014457397062&date=20131121201456&devPrivate=eyJsaWJfcGFyYW0iOiIxMDAxMzExMjEyMDE0NDI3NjIwNjg3MTVfMTYxXzJfMSJ9&synType=wiipay";
		String sig = "dwut1YuCVEQFqnSwk+NFmEfZkaXi58ghpbeXwdFzXnxID2xvEqbpfe4ECWRbXLx5vZKVEA0JpZ1z4+4hYJuFnHFh7tDzwYLtV0c9eFksQdcQSsttAqmRnrTIh233Ea8I/DGaXPU+Yu9j+WZxzWDI8i55HQPyb8giQaQWmSUIeeHiA0giz307e6yP8XZlKwfFbbE8ZbMHyJNAezmrLK89FuKv6KCAEqZC8R2YL8dBhsaCTHFn/q7l39owuKnx5+bvJTDGvrLlb202C8R9S/0rJXs9RxI+vJOn6jF3rFhWhUMsOMIzSRZV8X23vKLen6LC7e16yITVMCSk7sltanyJ3Q==";
		PropertiesConfiguration propConfig = new PropertiesConfiguration(System.getProperty("user.dir") + "/conf/environment.properties");
		String pubKeyStr = propConfig.getString("WIIPAY.pubKey");
		boolean verify = SignHelper.verify(plainText, sig.getBytes(encoding), pubKeyStr);
		if (verify)
			System.out.println("验签成功！verify：" + verify);
		else
			System.out.println("验签失败！verify：" + verify);
	}

	/**
	 * 生成密钥对
	 * 
	 * @return
	 */
	public static Map<String, Key> getKeys() throws Exception {
		Map<String, Key> rmap = new HashMap<String, Key>();
		KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
		generator.initialize(2048);
		/** 生成一个密钥对 **/
		KeyPair keyPair = generator.generateKeyPair();
		/** 把公钥公开 **/
		PublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		/** 自己保存好私钥 **/
		PrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		rmap.put("publicKey", publicKey);
		rmap.put("privateKey", privateKey);
		return rmap;
	}

	/**
	 * 得到密钥字符串(经过base64编码)
	 * 
	 * @return
	 */
	public static String getKeyString(Key key) throws Exception {
		byte[] keyBytes = key.getEncoded();
		String keyStr = new String(Base64.encodeBase64(keyBytes), encoding);
		return keyStr;
	}

	/**
	 * 获得公钥
	 * 
	 * @param key
	 *            : 密钥字符串(经过base64编码)
	 */
	public static PublicKey getPublicKey(String key) throws Exception {
		byte[] keyBytes = Base64.decodeBase64(key.getBytes(encoding));
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey publicKey = keyFactory.generatePublic(keySpec);
		return publicKey;
	}

	/**
	 * 获得私钥
	 * 
	 * @param key
	 *            : 密钥字符串(经过base64编码)
	 */
	public static PrivateKey getPrivateKey(String key) throws Exception {
		byte[] keyBytes = Base64.decodeBase64(key.getBytes(encoding));
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
		return privateKey;
	}

	/**
	 * 获得签名数据
	 * 
	 * @param plainText
	 *            : 原文
	 * @param priKeyStr
	 *            : 私钥
	 * @return
	 */
	public static byte[] getSign(String plainText, String priKeyStr) throws Exception {
		PrivateKey privateKey = getPrivateKey(priKeyStr);
		/** 数字签名 **/
		Signature signature = Signature.getInstance("SHA1withRSA");
		/** 用自己的私钥对数据签名 **/
		signature.initSign(privateKey);
		signature.update(plainText.getBytes(encoding));
		byte[] signByte = signature.sign();
		return Base64.encodeBase64(signByte);
	}

	/**
	 * 验签
	 * 
	 * @param plainText
	 *            : 原文
	 * @param sign
	 *            : 签名数据
	 * @param pubKeyStr
	 *            : 公钥
	 * @return
	 */
	public static boolean verify(String plainText, byte[] sign, String pubKeyStr) {
		boolean verify = false;
		try {
			PublicKey publicKey = getPublicKey(pubKeyStr);
			Signature signature = Signature.getInstance("SHA1withRSA");
			/** 比较两次数据结果是否一致 **/
			signature.initVerify(publicKey);
			signature.update(plainText.getBytes(encoding));
			verify = signature.verify(Base64.decodeBase64(sign));
		} catch (Exception e) {
			log.error("sign verify error.", e);
		}
		return verify;
	}
}
