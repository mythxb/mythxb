package com.common.utils.orcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.awt.image.BufferedImage;
import java.util.HashMap;

/**
 * 生成二维码
 */
public class QRCodeGenerator {

    private static final int IMAGE_WIDTH = 45;
    private static final int IMAGE_HEIGHT = 45;
    private static final int IMAGE_HALF_WIDTH = IMAGE_WIDTH / 2;
    private static final int FRAME_WIDTH = 2;



    public static BufferedImage orCode(String content) throws Exception{
        /*
         * 图片的宽度和高度
         */
        int width = 300;
        int height = 300;
        // 图片的格式
        String format = "png";
        // 二维码内容
        // String content = "hello,word";

        // 定义二维码的参数
        HashMap hints = new HashMap();
        // 定义字符集编码格式
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        // 纠错的等级 L > M > Q > H 纠错的能力越高可存储的越少，一般使用M
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        // 设置图片边距
        hints.put(EncodeHintType.MARGIN, 2);

            // 最终生成 参数列表 （1.内容 2.格式 3.宽度 4.高度 5.二维码参数）
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
            // 写入到本地
            int halfW = bitMatrix.getWidth() / 2;
            int halfH = bitMatrix.getHeight() / 2;
            int[] pixels = new int[width * height];

            // System.out.println(matrix.getHeight());
            for (int y = 0; y < bitMatrix.getHeight(); y++) {
                for (int x = 0; x < bitMatrix.getWidth(); x++) {
                    // 读取图片
                    if (x > halfW - IMAGE_HALF_WIDTH
                            && x < halfW + IMAGE_HALF_WIDTH
                            && y > halfH - IMAGE_HALF_WIDTH
                            && y < halfH + IMAGE_HALF_WIDTH) {
//						pixels[y * width + x] = srcPixels[x - halfW
//								+ IMAGE_HALF_WIDTH][y - halfH + IMAGE_HALF_WIDTH];
                    }
                    // 在图片四周形成边框
                    else if ((x > halfW - IMAGE_HALF_WIDTH - FRAME_WIDTH
                            && x < halfW - IMAGE_HALF_WIDTH + FRAME_WIDTH
                            && y > halfH - IMAGE_HALF_WIDTH - FRAME_WIDTH && y < halfH
                            + IMAGE_HALF_WIDTH + FRAME_WIDTH)
                            || (x > halfW + IMAGE_HALF_WIDTH - FRAME_WIDTH
                            && x < halfW + IMAGE_HALF_WIDTH + FRAME_WIDTH
                            && y > halfH - IMAGE_HALF_WIDTH - FRAME_WIDTH && y < halfH
                            + IMAGE_HALF_WIDTH + FRAME_WIDTH)
                            || (x > halfW - IMAGE_HALF_WIDTH - FRAME_WIDTH
                            && x < halfW + IMAGE_HALF_WIDTH + FRAME_WIDTH
                            && y > halfH - IMAGE_HALF_WIDTH - FRAME_WIDTH && y < halfH
                            - IMAGE_HALF_WIDTH + FRAME_WIDTH)
                            || (x > halfW - IMAGE_HALF_WIDTH - FRAME_WIDTH
                            && x < halfW + IMAGE_HALF_WIDTH + FRAME_WIDTH
                            && y > halfH + IMAGE_HALF_WIDTH - FRAME_WIDTH && y < halfH
                            + IMAGE_HALF_WIDTH + FRAME_WIDTH)) {
                        pixels[y * width + x] = 0xfffffff;
                    } else {
                        // 此处可以修改二维码的颜色，可以分别制定二维码和背景的颜色；
                        pixels[y * width + x] = bitMatrix.get(x, y) ? 0xff000000
                                : 0xfffffff;
                    }
                }
            }
            BufferedImage image = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);
            image.getRaster().setDataElements(0, 0, width, height, pixels);
            return image;

    }
}
