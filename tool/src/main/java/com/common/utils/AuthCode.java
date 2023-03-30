package com.common.utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class AuthCode {
	
	/**
	 * 生成图片验证码
	 * @param image
	 * @param width
	 * @param height
	 * @return
	 * @throws Exception
	 */
	public static String getAuthCode(BufferedImage image,Integer width,Integer height)throws Exception{
		String authCode = "";
		Random random = new Random();
		//产生image类的Graphics用于绘制操作
        Graphics g = image.getGraphics();
        //Graphics类的样式
        g.setColor(getRandColor(200, 250));
        g.setFont(new Font("Times New Roman",0,28));
        g.fillRect(0, 0, width, height);
        //绘制干扰线
        for(int i=0;i<40;i++){
            g.setColor(getRandColor(130, 200));
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int x1 = random.nextInt(12);
            int y1 = random.nextInt(12);
            g.drawLine(x, y, x + x1, y + y1);
        }

        //绘制字符
        String strCode = "";
        for(int i=0;i<4;i++){
            String rand = getCode();
            strCode = strCode + rand;
            g.setColor(new Color(20+random.nextInt(110),20+random.nextInt(110),20+random.nextInt(110)));
            g.drawString(rand, 13*i+6, 28);
        }
        //将字符保存到session中用于前端的验证
        g.dispose();
        authCode = strCode;
		return authCode;
	}
	
	/**
	 * 从 数字 大小写字母中随机取一个
	 * @return
	 * @throws Exception
	 */
	private static String getCode()throws Exception{
		String [] codes = {"0","1","2","3","4","5","6","7","8","9","q",
				"w","e","r","t","y","u","i","o","p",
				"a","s","d","f","g","h","j","k","l",
				"z","x","c","v","b","n","m","Q","W",
				"E","R","T","Y","U","I","O","P","A",
				"S","D","F","G","H","J","K","L","Z",
				"X","C","V","B","N","M"};
		Random random = new Random();
		String rand = codes[random.nextInt(codes.length)];
		System.out.println(rand);
		return rand;
	}
	
	//随机生成颜色
	private static Color getRandColor(int fc,int bc){
        Random random = new Random();
        if(fc>255)
            fc = 255;
        if(bc>255)
            bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r,g,b);
    }

}
