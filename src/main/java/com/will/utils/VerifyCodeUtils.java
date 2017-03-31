package com.will.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Random;

/**
 * The type VerifyCodeUtils. 验证码工具类
 *
 * @Auth Will
 * @Date 2016 -08-19 09:48:19
 */
public class VerifyCodeUtils{
	
	private static final String VERIFY_CODES = "123456789abcdefghijkmnopqrstuvwxyzABCDEFGHJKLMNOPQRSTUVWXYZ";//剔除0o Il易混淆字符
//	public static final String VERIFY_CODES = "1234567890";


	/**
	 * 使用系统默认字符源生成验证码
	 * @param verifySize	验证码长度
	 * @return
	 */
	private static String generateVerifyCode(int verifySize){
		return generateVerifyCode(verifySize, VERIFY_CODES);
	}
	/**
	 * 使用指定源生成验证码
	 * @param verifySize	验证码长度
	 * @param sources	验证码字符源
	 * @return
	 */
	private static String generateVerifyCode(int verifySize, String sources){
		if(sources == null || sources.length() == 0){
			sources = VERIFY_CODES;
		}
		int codesLen = sources.length();
		Random rand = new Random(System.currentTimeMillis());
		StringBuilder verifyCode = new StringBuilder(verifySize);
		for(int i = 0; i < verifySize; i++){
			verifyCode.append(sources.charAt(rand.nextInt(codesLen-1)));
		}
		return verifyCode.toString();
	}
	
	/**
	 * 生成随机验证码文件,并返回验证码值
	 * @param width 图片宽
	 * @param height 图片高
	 * @param outputFile 文件
	 * @param verifySize 验证码长度
	 * @return
	 * @throws IOException
	 */
	public static String outputVerifyImage(int width, int height, File outputFile, int verifySize) throws IOException{
		String verifyCode = generateVerifyCode(verifySize);
		outputImage(width, height, outputFile, verifyCode);
		return verifyCode;
	}
	
	/**
	 * 输出随机验证码图片流,并返回验证码值
	 * @param width 图片宽
	 * @param height 图片高
	 * @param os 输出流
	 * @param verifySize 验证码长度
	 * @return
	 * @throws IOException
	 */
	public static String outputVerifyImage(int width, int height, OutputStream os, int verifySize) throws IOException{
		String verifyCode = generateVerifyCode(verifySize);
		outputImage(width, height, os, verifyCode);
		return verifyCode;
	}
	
	/**
	 * 生成指定验证码图像文件
	 * @param width 图片宽
	 * @param height 图片高
	 * @param outputFile 输出文件
	 * @param code 验证码值
	 * @throws IOException
	 */
	private static void outputImage(int width, int height, File outputFile, String code) throws IOException{
		if(outputFile == null){
			return;
		}
		File dir = outputFile.getParentFile();
		if(!dir.exists()){
			dir.mkdirs();
		}
		try{
			outputFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(outputFile);
			outputImage(width, height, fos, code);
			fos.close();
		} catch(IOException e){
			throw e;
		}
	}
	
	/**
	 * 输出指定验证码图片流
	 * @param width 图片宽
	 * @param height 图片高
	 * @param os 输出流
	 * @param code 验证码值
	 * @throws IOException
	 */
	private static void outputImage(int width,
							int height,
							OutputStream os,
							String code) throws IOException{
		int verifySize = code.length();
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Random rand = new Random();
		Graphics2D g2 = image.createGraphics();
		Color[] colors = new Color[5];
		Color[] colorSpaces = new Color[] { Color.WHITE, Color.CYAN,
				Color.GRAY, Color.LIGHT_GRAY, Color.MAGENTA, Color.ORANGE,
				Color.PINK, Color.YELLOW };
		float[] fractions = new float[colors.length];
		for(int i = 0; i < colors.length; i++){
			colors[i] = colorSpaces[rand.nextInt(colorSpaces.length)];
			fractions[i] = rand.nextFloat();
		}
		Arrays.sort(fractions);
		Paint linearPaint = new LinearGradientPaint(0, 0, width, height, fractions, colors);
		Paint linearPaint2 = new LinearGradientPaint(0, 0, width, height, new float[]{0.3f, .6f, .8f, .9f}, new Color[]{Color.BLUE, Color.BLACK, Color.GREEN, Color.BLUE});
		//设置图片背景为白色
//		g2.setPaint(Color.WHITE);
//		g2.fillRect(0, 0, w, h);
		//设置图片渐变背景
		g2.setPaint(linearPaint);
		g2.fillRoundRect(0, 0, width, height, 5, 5);
		
		g2.setPaint(linearPaint2);
		int fontSize = (int) (Math.min(width/verifySize, height))+2;
		Font font = new Font("微软雅黑", Font.BOLD, fontSize);
		g2.setFont(font);
		char[] chars = code.toCharArray();
		for(int i = 0; i < verifySize; i++){
			AffineTransform affine = new AffineTransform();
			affine.setToRotation(Math.PI / 4 * rand.nextDouble() * (rand.nextBoolean() ? 1 : -1), (width / verifySize) * i + fontSize/2, height/2);
			g2.setTransform(affine);
			g2.drawChars(chars, i, 1, (width / verifySize) * i, height/2 + fontSize /2);
		}
		g2.dispose();
		ImageIO.write(image, "jpg", os);
	}

	public static void main(String[] args) throws IOException{
		File dir = new File("E:/verifies");
		int w = 200, h = 80;
		for(int i = 0; i < 1; i++){
			String verifyCode = generateVerifyCode(4);
			File file = new File(dir, verifyCode + ".jpg");
			outputImage(w, h, file, verifyCode);
		}
	}
}
