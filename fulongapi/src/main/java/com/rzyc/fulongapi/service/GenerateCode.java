package com.rzyc.fulongapi.service;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class GenerateCode {

    private static char[] OPS = new char[]{'+'};
    /**
     * 验证码样式
     *
     * @return
     */
    public static BufferedImage getVerify(HttpServletRequest request) throws IOException {
        //设置宽高
        int width = 160;
        int height = 42;
        //声明RGB格式图片
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        //创建画笔
        Graphics g = image.getGraphics();

        //设置图片颜色
        g.setColor(new Color(244, 240, 255));
        //画一个矩形
        g.fillRect(0, 0, width, height);
        g.setColor(new Color(236, 231, 255));
        g.drawRect(0, 0, width - 1, height - 1);

        //创建随机数
        Random rdm = new Random();
        //生成随机点
        for (int i = 0; i < 50; i++) {
            //每个点不一样的颜色
            g.setColor(new Color(rdm.nextInt(256), rdm.nextInt(256), rdm.nextInt(256)));
            int x = rdm.nextInt(width);
            int y = rdm.nextInt(height);
            g.drawOval(x, y, 0, 0);
        }
        //生成随机验证码
        String verifyCode;
        int num1 = rdm.nextInt(10);
        int num2 = rdm.nextInt(10);
        int num3 = rdm.nextInt(10);
        g.setColor(Color.orange);
        char op1 = OPS[rdm.nextInt(1)];
        char op2 = OPS[rdm.nextInt(1)];

        //给每个不同数字设置不用颜色
        g.setColor(new Color(83, 141, 244));
        g.setFont(new Font("DejaVu Sans", Font.BOLD, 26));
        g.drawString(num1  + "", 45, 24);
        //  g.drawString( op1+"", 30, 23);
        g.setColor(Color.RED);
        //g.drawString(num2 +"", 52, 26);
        g.drawString( op2 +"", 70, 26);
        g.setColor(new Color(244,159,47));
        g.drawString(num3 + "= ?", 90, 26);
        g.dispose();
        //将验证码计算结果存入session
        verifyCode = "" + num1  + op2 + num3;
        int result = calc(verifyCode);
        request.getSession().setAttribute("VERIFY_CODE_RESULT", result);
        String sessionId = request.getSession().getId();
        System.out.println("sessionId:"+sessionId);
        System.out.println("验证码结果：" + result);
        //输出图片
        return image;
    }

    /**
     * 计算验证码结果
     * @param exp
     * @return
     */
    private static int calc(String exp) {
        try {
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("JavaScript");
            return (Integer) engine.eval(exp);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
