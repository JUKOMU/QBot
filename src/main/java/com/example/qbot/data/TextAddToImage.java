package com.example.qbot.data;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;

/**
 * 存放文本内容的类
 */
@Setter
@Getter
public class TextAddToImage {
    //文字内容
    private String text;
    //字体颜色和透明度
    private Color color;
    //字体和大小
    private Font font;
    //所在图片的x坐标
    private int x;
    //所在图片的y坐标
    private int y;

    /**
     * 创建textAddToImage, 每一个对象,代表在该图片中要插入的一段文字内容:
     * @param text  : 文本内容;
     * @param color : 字体颜色(前三位)和透明度(第4位,值越小,越透明);
     * @param font  : 字体的样式和字体大小;
     * @param x     : 当前字体在该图片位置的横坐标;
     * @param y     : 当前字体在该图片位置的纵坐标;
     * @return
     */
    public static TextAddToImage createTextAddToImage(String text, Color color, Font font, int x, int y){
        TextAddToImage textAddToImage = new TextAddToImage();
        textAddToImage.setText(text);
        textAddToImage.setColor(color);
        textAddToImage.setFont(font);
        textAddToImage.setX(x);
        textAddToImage.setY(y);
        return textAddToImage;
    }

}