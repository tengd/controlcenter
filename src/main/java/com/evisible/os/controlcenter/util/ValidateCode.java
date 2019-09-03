package com.evisible.os.controlcenter.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;

/**
 * @author TengD
 * @date 2014/11/16 自动生成界面验证码,取四位验证码
 */
public class ValidateCode {
	private int width = 160, height = 40; // 图片宽、高
	private int line = 100; // 干扰线数量
	// 验证码
	private String code = null;
	private BufferedImage buffImg = null;

	/**
	 * @param width
	 * @param height
	 * @param codeCount
	 * @param lineCount
	 */
	public ValidateCode(int width, int height, int line) {
		this.width = width;
		this.height = height;
		this.line = line;
		this.createCode();
	}

	/**
	 * @param width
	 * @param height
	 * @param line
	 * 创建验证码
	 */
	public void createCode() {
		int x = 0, fontHeight = 0, codeY = 0;
		int red = 0, green = 0, blue = 0;
		x = width / (4 + 2); // 每个字符的宽度
		fontHeight = height - 1; // 字体的高度
		codeY = height - 8;
		// 图像buffer
		buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = buffImg.createGraphics();
		// 生成随机数
		Random random = new Random();
		// 将图像填充为白色
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
		// 创建字体
		Font font = this.getFont(fontHeight);
		g.setFont(font);
		// 划干扰线
		for (int i = 0; i < line; i++) {
			int xs = random.nextInt(width);
			int ys = random.nextInt(height);
			int xe = xs + random.nextInt(width / 8);
			int ye = ys + random.nextInt(height / 8);
			red = random.nextInt(255);
			green = random.nextInt(255);
			blue = random.nextInt(255);
			g.setColor(new Color(red, green, blue));
			g.drawLine(xs, ys, xe, ye);
		}
		// randomCode记录随机产生的验证码
		StringBuffer randomCode = new StringBuffer();
		for (int i = 0; i < EncodedRuleUtil.getEnv().getValidateCodeUtil()
				.toString().length() - 2; i++) {
			char[] strRand = EncodedRuleUtil.getEnv().getValidateCodeUtil()
					.toString().toCharArray();
			// 产生随机的颜色值，让输出的每个字符的颜色值都将不同。
			red = random.nextInt(255);
			green = random.nextInt(255);
			blue = random.nextInt(255);
			g.setColor(new Color(red, green, blue));
			g.drawString(String.valueOf(strRand[i]), (i + 1) * x, codeY);
			// 将产生的四个随机数组合在一起。
			randomCode.append(strRand[i]);
		}
		// 将四位数字的验证码保存到Session中
		code = randomCode.toString();
		System.out.println("当前验证码："+ code);
	}

	/**
	 * @param fontHeight
	 * @return 字体
	 */
	public Font getFont(int fontHeight) {
		try {
			Font baseFont = Font.createFont(Font.TRUETYPE_FONT,
					new ByteArrayInputStream(hex2byte(getFontByteStr())));
			return baseFont.deriveFont(Font.PLAIN, fontHeight);
		} catch (Exception e) {
			return new Font("Arial", Font.PLAIN, fontHeight);
		}
	}

	/**
	 * @param str
	 * @return 字体文件
	 */
	private byte[] hex2byte(String str) {
		if (str == null)
			return null;
		str = str.trim();
		int len = str.length();
		if (len == 0 || len % 2 == 1)
			return null;
		byte[] b = new byte[len / 2];
		try {
			for (int i = 0; i < str.length(); i += 2) {
				b[i / 2] = (byte) Integer
						.decode("0x" + str.substring(i, i + 2)).intValue();
			}
			return b;
		} catch (Exception e) {
			return null;
		}

	}

	/**
	 * @return
	 */
	private String getFontByteStr() {
		return null;
		// return str;//字符串太长 在附件中找
	}

	public void write(String path) throws IOException {
		OutputStream sos = new FileOutputStream(path);
		this.write(sos);
	}

	public void write(OutputStream sos) throws IOException {
		ImageIO.write(buffImg, "png", sos);
		sos.close();
	}

	public BufferedImage getBuffImg() {
		return buffImg;
	}

	public String getCode() {
		return code;
	}

	public static void main(String[] args) {
		ValidateCode vCode = new ValidateCode(140, 40, 100);
		String path="D:/t/";
		try {
			if(!path.equals(null)){
				java.io.File file=new java.io.File(path);
				file.mkdirs();
		    }
			path +=   new Date().getTime() + ".png";
			System.out.println(vCode.getCode() + ">" + path);
			vCode.write(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
