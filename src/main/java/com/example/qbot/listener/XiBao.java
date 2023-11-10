package com.example.qbot.listener;

import com.example.qbot.util.createImage;
import love.forte.simboot.annotation.Filter;
import love.forte.simboot.annotation.Listener;
import love.forte.simboot.filter.MatchType;
import love.forte.simbot.definition.Group;
import love.forte.simbot.event.GroupMessageEvent;
import love.forte.simbot.message.Image;
import love.forte.simbot.message.ResourceImage;
import love.forte.simbot.resources.ByteArrayResource;
import love.forte.simbot.resources.Resource;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import static com.example.qbot.data.TextAddToImage.createTextAddToImage;

@Component
public class XiBao {
    @Listener
    @Filter(value = "喜报", matchType = MatchType.TEXT_CONTAINS)
    public void onXiBaoEvent(GroupMessageEvent event) throws IOException {
        String message = event.getMessageContent().toString();
        int index = message.indexOf("喜报");
        int end = message.indexOf("messageSource");
        String xibaoContent = message.substring(index+2,end-2);
        int xibaoLength = xibaoContent.length();
        double lines = Math.ceil(xibaoLength/7.0);
        double Hmargin = (550 - lines * 90)/2.0;
        ArrayList<Object> list = new ArrayList<>();
        System.out.println("Loading");
        for (int i = 1; i <= lines; i++) {
            if (i < lines) {
                String str = xibaoContent.substring((int) (0+(i-1)*7), (int) (i*7));
                System.out.println(str);
                System.out.println((int) (int) (0+(i-1)*7)+" " +(int) (7+(i-1)*lines));
                list.add(createTextAddToImage(str,new Color(255, 234, 180, 255),new Font("隶书", Font.BOLD, 84), 99, (int) (Hmargin+90*i)));
                list.add(createTextAddToImage(str,new Color(255,0,0, 255),new Font("隶书", Font.PLAIN, 85), 99, (int) (Hmargin+90*i)));
            }else {
                int textRestLentgh = (int) (xibaoLength - 7 * (lines - 1));
                int Xmargin = (int) ((800 - textRestLentgh * 86)/2.0);
                String str = xibaoContent.substring(xibaoLength - textRestLentgh);
                System.out.println(str);
                System.out.println(xibaoLength - textRestLentgh);
                list.add(createTextAddToImage(str,new Color(255, 234, 180, 255),new Font("隶书", Font.BOLD, 84), Xmargin, (int) (Hmargin+90*i)));
                list.add(createTextAddToImage(str,new Color(255,0,0, 255),new Font("隶书", Font.PLAIN, 85), Xmargin, (int) (Hmargin+90*i)));
            }
        }
        //list.add(createTextAddToImage(xibaoContent,new Color(255, 234, 180, 255),new Font("隶书", Font.BOLD, 85), 400, 275));
        //list.add(createTextAddToImage(xibaoContent,new Color(255,0,0, 255),new Font("隶书", Font.PLAIN, 85), 400, 275));
        //操作图片:
        String srcImgPath="E:\\Java\\QBot\\src\\main\\resources\\images\\xibao.png";
        BufferedImage image = createImage.writeImage(srcImgPath,list);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(image, "png", out);
        byte[] bytes = out.toByteArray();
        ByteArrayResource resource = Resource.of(bytes,"xibao");
        ResourceImage imageToSend = Image.of(resource);
        Group group = event.getGroup();
        group.sendAsync(imageToSend);
    }
}
