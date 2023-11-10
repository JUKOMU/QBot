package com.example.qbot.data;

import lombok.Getter;
import lombok.Setter;
import org.apache.batik.transcoder.TranscoderException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

import static com.example.qbot.util.SvgUtil.getImage;

/**
 * 存放图片内容的类
 */
@Setter
@Getter
public class ImageAddToImage {
    //图片资源的路径
    private String srcImgPath;
    //图片资源
    private Image img;
    //所在图片的x坐标
    private int x;
    //所在图片的y坐标
    private int y;
    //图片宽
    private int width;
    //图片长
    private int height;

    public ImageAddToImage() {
    }

    public ImageAddToImage(String srcImgPath) throws IOException, TranscoderException {
        this.srcImgPath = srcImgPath;
        this.img = getImage(srcImgPath);
    }
    /**
     * 创建imageAddToImage, 每一个对象,代表在该图片中要插入的一段图片内容:
     * @param srcImgPath    : 图片内容
     * @param x             : 当前字体在该图片位置的横坐标;
     * @param y             : 当前字体在该图片位置的纵坐标;
     * @param width         : 插入图片的宽
     * @param height        : 插入图片的高
     * @return
     * @throws IOException
     */
    public static ImageAddToImage createImageAddToImage(String srcImgPath, int x, int y, int width, int height) throws IOException, TranscoderException {
        ImageAddToImage imageAddToImage = new ImageAddToImage(srcImgPath);
        imageAddToImage.setWidth(width);
        imageAddToImage.setHeight(height);
        imageAddToImage.setX(x);
        imageAddToImage.setY(y);
        return imageAddToImage;
    }

}