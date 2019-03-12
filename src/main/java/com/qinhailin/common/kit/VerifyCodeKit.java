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

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

/**
 * 验证码
 * @author Administrator
 *
 */
public class VerifyCodeKit {

	/**
	 * 保存认证码
	 */
	private static String verityCode = "";
	/**
	 * 随机函数
	 */
	private static Random random = new Random();
	
	/**
	 * 输出验证码图片到页面	 * 
	 * @param response
	 * @param type 1:数字验证码；2：算式验证码
	 * @author QinHaiLin
	 * @date 2018年2月5日
	 */
	public static void createImage(HttpServletResponse response,int type) {
		BufferedImage image=createImage(80,30,type);
		ServletOutputStream out;
		try {
			out = response.getOutputStream();
			ImageIO.write(image, "jpeg", out);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取createImage认证码
	 * @param type 1:数字验证码；2：算式验证码
	 * @return
	 * @author QinHaiLin
	 * @date 2018年2月2日下午1:16:36
	 */
	public static String getVerityCode(int type) {
		if (verityCode.equals("")) {
			createImage(80, 30,type);
		}
		return verityCode;
	}

	/**
	 * 给定范围获得随机颜色
	 *
	 * @param fc
	 * @param bc
	 * @return
	 * @author QinHaiLin
	 * @date 2018年2月1日下午2:12:52
	 */
	public static Color createColor(int fc, int bc) {
		if (fc > 255) {
			fc = 255;
		}
		if (bc > 255) {
			bc = 255;
		}
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

	/**
	 * 创建4位数的验证码图片
	 *
	 * @param width
	 *            图片宽度
	 * @param height
	 *            图片高度
	 * @param type 1:数字验证码，2：算式验证码     
	 * @return
	 * @author QinHaiLin
	 * @date 2018年2月2日下午1:01:19
	 */
	public static BufferedImage createImage(int width, int height,int type) {
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		// 获取图形上下文
		Graphics g = image.getGraphics();

		// 设定背景色
		g.setColor(createColor(220, 250));
		g.fillRect(0, 0, width, height);
		// 设定字体
		g.setFont(new Font("Times New Roman", Font.PLAIN, 24));

		// 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到
		g.setColor(createColor(160, 200));
		for (int i = 0; i < 155; i++) {
			drawLine(width, height, g);
		}

		// 取随机产生的认证码(4位验证码)
		String vCode="";
		char[] code = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
				'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };		
		if(type==1) {
			for (int i = 0; i < 4; i++) {
				vCode+=drawString(g,i,code);
			}			
		}else {
			//算式验证码
			vCode=drawString(g);
		}
		verityCode=vCode;
		return image;
	}
		
	/**
	 * 画干扰线
	 *
	 * @param width
	 * @param height
	 * @param g
	 * @author QinHaiLin
	 * @date 2018年2月2日下午1:19:36
	 */
	private static void drawLine(int width, int height, Graphics g) {
		int x = random.nextInt(width);
		int y = random.nextInt(height);
		int xl = random.nextInt(12);
		int yl = random.nextInt(12);
		g.drawLine(x, y, x + xl, y + yl);
	}

	/**
	 * 画验证码
	 *
	 * @param g
	 * @param i
	 * @param code
	 * @author QinHaiLin
	 * @date 2018年2月2日下午2:06:14
	 */
	private static String drawString(Graphics g,int i, char[] code) {
		g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110),
				20 + random.nextInt(110)));
		if (i < 2) {// 字母
			String a = code[random.nextInt(26)] + "";
			// 将认证码显示到图象中
			g.drawString(a, (18 * i) + 9, 26);
			return a;
		} else {// 数字
			String rand = String.valueOf(random.nextInt(10));
			// 将认证码显示到图象中
			g.drawString(rand, (18 * i) + 9, 26);
			return rand;
		}
	}
	
	/**
	 * 算式验证码
	 * @param g
	 * @return
	 * @author QinHaiLin
	 * @date 2018年3月30日
	 */
	private static String drawString(Graphics g) {
		int sRand=0;//算法结果
		int num1=0;
		int num2=0;
		String s="+";
		String[] code={"+","-"};
		for (int i=0;i<4;i++){
			g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110),
					20 + random.nextInt(110)));
			if(i==0){
		    	num1=random.nextInt(10);
		    	g.drawString(num1+"",18*i+9,26);
		    }else if(i==1){// + -
		    	s=code[random.nextInt(2)];
		        g.drawString(s,18*i+9,26);
		    }else if(i==2){
		    	num2=random.nextInt(10); 
		    	g.drawString(num2+"",18*i+9,26);
		    }else{
		    	g.drawString("=", 18*i+9, 26);
		    }
		}
		
		//计算结果
		if("+".equals(s)){
			sRand=num1+num2;
		}else{
			sRand=num1-num2;
		}
		
		return sRand+"";
	}

}
