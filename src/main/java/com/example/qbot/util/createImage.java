package com.example.qbot.util;

import com.example.qbot.data.ImageAddToImage;
import com.example.qbot.data.TextAddToImage;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * @use 利用Java代码给图片添加文字(透明图调低点,也可以当做水印)
 */
@Slf4j
public class createImage {
    public static BufferedImage writeImage(String srcImgPath,List<Object> list) {
        FileOutputStream outImgStream = null;
        try {
            //读取原图片信息
            File srcImgFile = new File(srcImgPath);//得到文件
            Image srcImg = ImageIO.read(srcImgFile);//文件转化为图片
            int srcImgWidth = srcImg.getWidth(null);//获取图片的宽
            int srcImgHeight = srcImg.getHeight(null);//获取图片的高

            //添加文字:
            BufferedImage bufImg = new BufferedImage(srcImgWidth, srcImgHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufImg.createGraphics();
            g.drawImage(srcImg, 0, 0, srcImgWidth, srcImgHeight, null);
            for (Object obj : list) {
                if (obj instanceof TextAddToImage textaddToImage) {
                    g.setColor(textaddToImage.getColor());                                  //根据图片的背景设置水印颜色
                    g.setFont(textaddToImage.getFont());                                    //设置字体
                    g.drawString(textaddToImage.getText(), textaddToImage.getX(), textaddToImage.getY());   //画出水印
                }
                if (obj instanceof ImageAddToImage imageAddToImage) {
                    g.drawImage(imageAddToImage.getImg(), imageAddToImage.getX(), imageAddToImage.getY(), imageAddToImage.getWidth(), imageAddToImage.getHeight(), null);
                }
            }
            g.dispose();

            return bufImg;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (null != outImgStream){
                    outImgStream.flush();
                    outImgStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 编辑图片,往指定位置添加文字
     * @param srcImgPath    :源图片路径
     * @param targetImgPath :保存图片路径
     * @param list          :文字集合
     */
    public static void writeImage(String srcImgPath, String targetImgPath, List<Object> list) {
        FileOutputStream outImgStream = null;
        try {
            //读取原图片信息
            File srcImgFile = new File(srcImgPath);//得到文件
            Image srcImg = ImageIO.read(srcImgFile);//文件转化为图片
            int srcImgWidth = srcImg.getWidth(null);//获取图片的宽
            int srcImgHeight = srcImg.getHeight(null);//获取图片的高

            //添加文字:
            BufferedImage bufImg = new BufferedImage(srcImgWidth, srcImgHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufImg.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g.drawImage(srcImg, 0, 0, srcImgWidth, srcImgHeight, null);
            for (Object obj : list) {
                if (obj instanceof TextAddToImage textaddToImage) {
                    g.setColor(textaddToImage.getColor());                                  //根据图片的背景设置水印颜色
                    g.setFont(textaddToImage.getFont());                                    //设置字体
                    g.drawString(textaddToImage.getText(), textaddToImage.getX(), textaddToImage.getY());   //画出水印
                }
                if (obj instanceof ImageAddToImage imageAddToImage) {
                    g.drawImage(imageAddToImage.getImg(), imageAddToImage.getX(), imageAddToImage.getY(), imageAddToImage.getWidth(), imageAddToImage.getHeight(), null);
                }
            }
            g.dispose();

            // 输出图片
            outImgStream = new FileOutputStream(targetImgPath);
            ImageIO.write(bufImg, "png", outImgStream);
        } catch (Exception e) {
            log.error("==== 系统异常::{} ====",e);
        }finally {
            try {
                if (null != outImgStream){
                    outImgStream.flush();
                    outImgStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}




