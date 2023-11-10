package com.example.qbot;

import com.example.qbot.data.SetuData;
import com.example.qbot.util.createImage;
import love.forte.simbot.definition.Group;
import love.forte.simbot.message.Image;
import love.forte.simbot.message.ResourceImage;
import love.forte.simbot.resources.ByteArrayResource;
import love.forte.simbot.resources.Resource;
import org.apache.batik.transcoder.TranscoderException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import static com.example.qbot.data.TextAddToImage.createTextAddToImage;
import static com.example.qbot.data.ImageAddToImage.createImageAddToImage;
import static com.example.qbot.util.GetWeather.getWeather;

public class test {

    /**
     * main方法:
     * @param args
     */
    public static void main(String[] args) throws IOException, TranscoderException {
/*
        //=========================================自行发挥================================
        //todo 自己真实的地址:(如果项目中使用的阿里云,则自行改造'writeImage'方法,接受流对象就好了);
        String srcImgPath="E:\\Java\\QBot\\src\\main\\resources\\images\\xibao.png";    //源图片地址
        String tarImgPath="E:\\Java\\QBot\\src\\main\\resources\\images\\xibao2.png";   //目标图片的地址
        //==============================================================================

        //获取数据集合；
        ArrayList<Object> list = new ArrayList<>();
        list.add(createTextAddToImage("喜报",new Color(255, 234, 180, 255),new Font("隶书", Font.BOLD, 85), 400, 275));
        list.add(createTextAddToImage("喜报",new Color(255,0,0, 255),new Font("隶书", Font.PLAIN, 85), 400, 275));

        //list.add(createImageAddToImage("E:\\Java\\QBot\\src\\main\\resources\\QWeather-Icons\\icons\\100.svg", 200, 200, 200, 200));

        //操作图片:
        createImage.writeImage(srcImgPath, tarImgPath, list);

        //这句代码,自己项目中可以不用加,在这里防止main方法报错的;
        System.exit(0);

 */
        /*
        String xibaoContent = "死🐎腾讯";
        int xibaoLength = xibaoContent.length();
        double lines = Math.ceil(xibaoLength/7.0);
        double Hmargin = (550 - lines * 90)/2.0;
        ArrayList<Object> list = new ArrayList<>();
        for (int i = 1; i <= lines; i++) {
            if (i < lines) {
                list.add(createTextAddToImage(xibaoContent,new Color(255, 234, 180, 255),new Font("隶书", Font.BOLD, 84), 99, (int) (Hmargin+90*i)));
                list.add(createTextAddToImage(xibaoContent,new Color(255,0,0, 255),new Font("隶书", Font.PLAIN, 85), 99, (int) (Hmargin+90*i)));
            }else {
                int textRestLentgh = (int) (xibaoLength - 7 * (lines - 1));
                int Xmargin = (int) ((800 - textRestLentgh * 86)/2.0);
                list.add(createTextAddToImage(xibaoContent,new Color(255, 234, 180, 255),new Font("隶书", Font.BOLD, 85), Xmargin, (int) (Hmargin+90*i)));
                list.add(createTextAddToImage(xibaoContent,new Color(255,0,0, 255),new Font("隶书", Font.PLAIN, 85), Xmargin, (int) (Hmargin+90*i)));
            }
        }
        //list.add(createTextAddToImage(xibaoContent,new Color(255, 234, 180, 255),new Font("隶书", Font.BOLD, 85), 400, 275));
        //list.add(createTextAddToImage(xibaoContent,new Color(255,0,0, 255),new Font("隶书", Font.PLAIN, 85), 400, 275));
        //操作图片:
        String srcImgPath="E:\\Java\\QBot\\src\\main\\resources\\images\\xibao.png";
        String tarImgPath="E:\\Java\\QBot\\src\\main\\resources\\images\\xibao2.png";
        BufferedImage image = createImage.writeImage(srcImgPath,list);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(image, "png", out);
        createImage.writeImage(srcImgPath, tarImgPath, list);
        String city = "北京";
        Map<String, String> map = getWeather(city);
        ArrayList<Object> list = new ArrayList<>();
        String cityInfo = map.get("adm1") + city;
        int strLength = cityInfo.length();
        int cityInfoX = (1178 - strLength * 86 - 36)/2;
        list.add(createTextAddToImage(map.get("adm1") + "/" + city,new Color(0, 0, 0, 255),new Font("微软雅黑", Font.PLAIN, 85), cityInfoX, 188));
        list.add(createTextAddToImage(map.get("temp") + "℃",new Color(0, 0, 0, 255),new Font("微软雅黑", Font.PLAIN, 200), 530, 476));
        list.add(createImageAddToImage("E:\\Java\\QBot\\src\\main\\resources\\QWeather-Icons\\icons\\"+map.get("icon")+".svg", 255, 300, 160, 160));
        String text = map.get("text");
        int textLength = text.length();
        int textX = 330 - (textLength * 60)/2;
        list.add(createTextAddToImage(text,new Color(0, 0, 0, 255),new Font("微软雅黑", Font.PLAIN, 60), textX, 550));
        list.add(createTextAddToImage(map.get("feelsLike")+"℃/"+map.get("feelsLikeLevel"), new Color(0, 0, 0, 255),new Font("微软雅黑", Font.PLAIN, 50), 625, 798));
        list.add(createTextAddToImage(map.get("humidity")+"%",new Color(0, 0, 0, 255),new Font("微软雅黑", Font.PLAIN, 50), 691, 910));
        list.add(createTextAddToImage(map.get("windDir"),new Color(0, 0, 0, 255),new Font("微软雅黑", Font.PLAIN, 50), 664, 1037));
        list.add(createTextAddToImage(map.get("windSpeed") + "公里/小时",new Color(0, 0, 0, 255),new Font("微软雅黑", Font.PLAIN, 50), 617, 1155));
        list.add(createTextAddToImage(map.get("windScale") + "级",new Color(0, 0, 0, 255),new Font("微软雅黑", Font.PLAIN, 50), 708, 1289));
        list.add(createTextAddToImage(map.get("pressure") + "百帕",new Color(0, 0, 0, 255),new Font("微软雅黑", Font.PLAIN, 50), 646, 1408));

        String srcImgPath="E:\\Java\\QBot\\src\\main\\resources\\images\\weatherBackground.png";
        String tarImgPath="E:\\Java\\QBot\\src\\main\\resources\\images\\Weather.png";
        createImage.writeImage(srcImgPath, tarImgPath, list);
         */
        SetuData data = new SetuData();

    }
}
