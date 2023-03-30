package com.common.utils;

import com.common.utils.upload.FileUpload;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * @author Administrator
 */
public class ImageUtils {

    /**
     * 按照一定比例生成缩略图
     *
     * @param inputStream
     * @param scale
     * @return
     * @throws Exception
     */
    public static InputStream thumbImage(InputStream inputStream, int scale) throws Exception {
        if (scale <= 0) {
            throw new Exception("scale must not be less than zero");
        }
        BufferedImage srcImage = ImageIO.read(inputStream);
        int width = srcImage.getWidth();
        int height = srcImage.getHeight();
        width /= scale;
        height /= scale;
        BufferedImage targetImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        targetImage.getGraphics().drawImage(srcImage.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(targetImage, "jpg", baos);
        return new ByteArrayInputStream(baos.toByteArray());
    }

    /**
     * 通过图片的url 生成一个缩略图
     * 如果图片的长 大于 宽  则以宽为长度 截取一个正方形图片
     * 如果图片的宽 大于 长 则以长为长度 截取一个正方形图片
     * 如果长等于宽 则不处理
     *
     * @param imgUrl
     * @return
     * @throws Exception
     */
    public static String thumbImage(String imgUrl) throws Exception {
        String resultUrl = "";
        if (StringUtils.isNotBlank(imgUrl)) {
//			FileManager fileManager = FileManager.getInstance();
            String suffix = imgUrl.substring(imgUrl.lastIndexOf(".") + 1);
            System.out.println("后缀名 = = ==" + suffix);
            suffix = "jpg";
            URL url = new URL(imgUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();//利用HttpURLConnection对象,我们可以从网络中获取网页数据.
            conn.setDoInput(true);
            conn.connect();
            InputStream inputStream = conn.getInputStream(); //得到网络返回的输入流
            BufferedImage srcImage = ImageIO.read(inputStream);
            int width = srcImage.getWidth();
            int height = srcImage.getHeight();
            if (width != height) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                if (width > height) {
                    BufferedImage targetImage = new BufferedImage(height, height, BufferedImage.TYPE_INT_RGB);
                    targetImage.getGraphics().drawImage(srcImage.getScaledInstance(height, height, Image.SCALE_SMOOTH), 0, 0, height, height, null);
                    ImageIO.write(targetImage, suffix, baos);
                } else {
                    BufferedImage targetImage = new BufferedImage(width, width, BufferedImage.TYPE_INT_RGB);
                    targetImage.getGraphics().drawImage(srcImage.getScaledInstance(width, width, Image.SCALE_SMOOTH), 0, 0, width, width, null);
                    ImageIO.write(targetImage, suffix, baos);
                }
                InputStream in = new ByteArrayInputStream(baos.toByteArray());
                String fileName = System.currentTimeMillis() + "." + suffix;
                System.out.println("fileName = = =" + fileName);
//                resultUrl = FileUpload.uploadFile(in, fileName);
                System.out.println("压缩后的图片地址 = = = " + resultUrl);
            } else {
                resultUrl = imgUrl;
            }
        } else {
            throw new Exception("图片地址为null");
        }
        return resultUrl;
    }

    /**
     * 给图片添加水印文字、可设置水印文字的旋转角度
     *
     * @param logoText  要写入的文字
     * @param degree    旋转角度
     * @param color     字体颜色
     * @param formaName 图片后缀
     */
    public static String markImage(String logoText, InputStream is, Integer degree, Color color, String formaName) throws Exception {
        String resultUrl = "";
        Image src = ImageIO.read(is);
        int wideth = src.getWidth(null);
        int height = src.getHeight(null);
        BufferedImage image = new BufferedImage(wideth,height,BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        g.drawImage(src,0,0,wideth,height,null);
        g.setColor(Color.white);
        Integer fontSize = 2;
        if(wideth > 100){
            fontSize = (wideth/100) * fontSize;
            ++fontSize;
        }
        g.setFont(new Font("宋体", Font.BOLD, fontSize));
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 1f));
        Integer x = wideth - (wideth/2) -fontSize;
        Integer y = height -fontSize;

        Integer imageHeight = ImageUtils.logoHeight(height,fontSize);
        Image src_biao = ImageIO.read(new URL("http://39.97.52.148:8091/resource/icon/logo.png"));
        g.drawImage(src_biao,x-fontSize,imageHeight,fontSize,fontSize,null);


        g.setPaint(new Color(192,192,192));
        g.drawString(logoText,x,y+fontSize-20);

        g.dispose();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, formaName,baos);
        InputStream in = new ByteArrayInputStream(baos.toByteArray());
        resultUrl =FileUpload.uploadImg(in, formaName);
        return resultUrl;

    }

    private static Integer logoHeight(Integer height,Integer fontSize){
        Integer logoHeight = 0;
        logoHeight = height - fontSize -20;
        return logoHeight;
    }

    public static void main(String [] a){
        System.out.println(301/300);
    }

}
