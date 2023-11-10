package com.example.qbot.listener;

import com.example.qbot.util.createImage;
import love.forte.simboot.annotation.Filter;
import love.forte.simboot.annotation.FilterValue;
import love.forte.simboot.annotation.Listener;
import love.forte.simbot.event.GroupMessageEvent;
import love.forte.simbot.message.Image;
import love.forte.simbot.message.Message;
import love.forte.simbot.message.MessagesBuilder;
import love.forte.simbot.message.ResourceImage;
import love.forte.simbot.resources.ByteArrayResource;
import love.forte.simbot.resources.Resource;
import org.apache.batik.transcoder.TranscoderException;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Map;

import static com.example.qbot.data.ImageAddToImage.createImageAddToImage;
import static com.example.qbot.data.TextAddToImage.createTextAddToImage;
import static com.example.qbot.util.GetWeather.getWeather;


@Component
public class Weather {
    @Listener
    @Filter(value = "/天气.(?<city>\\S+)")
    public void onEvent(GroupMessageEvent event, @FilterValue("city") String city) throws IOException, TranscoderException {
        Map<String, String> map = getWeather(city);
        if (map == null) {
            event.replyAsync("请求的地区不存在！");
        } else {
            /*
            String weather = map.get("adm1") + "/" + city + "：" + map.get("text") + "\n" +
                    "温度：" + map.get("temp") + "℃\n" +
                    "体感温度：" + map.get("feelsLike") + "℃\n" +
                    "体感等级：" + map.get("feelsLikeLevel") + "\n" +
                    "湿度：" + map.get("humidity") + "%\n" +
                    "风向：" + map.get("windDir") + "\n" +
                    "风速：" + map.get("windSpeed") + "公里/小时\n" +
                    "风力：" + map.get("windScale") + "级\n" +
                    "气压：" + map.get("pressure") + "百帕\n" +
                    "数据截止时间：" + map.get("obsTime") + "\n" +
                    "数据仅为预测，不保证其准确性。";
             */
            String srcImgPath="E:\\Java\\QBot\\src\\main\\resources\\images\\weatherBackground.png";
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


            //操作图片:
            BufferedImage image = createImage.writeImage(srcImgPath,list);
            ByteArrayOutputStream byteImage = new ByteArrayOutputStream();
            ImageIO.write(image,"png",byteImage);
            byte[] byteImageArray = byteImage.toByteArray();
            ByteArrayResource resource = Resource.of(byteImageArray, "weather");
            ResourceImage imageRes = Image.of(resource);
            event.replyAsync(imageRes);

        }
    }
}


