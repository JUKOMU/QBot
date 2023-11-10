package com.example.qbot.listener;

import com.example.qbot.data.SetuData;
import love.forte.simboot.annotation.Filter;
import love.forte.simboot.annotation.FilterValue;
import love.forte.simboot.annotation.Listener;
import love.forte.simboot.filter.MatchType;
import love.forte.simbot.event.GroupMessageEvent;
import love.forte.simbot.message.Image;
import love.forte.simbot.message.ResourceImage;
import love.forte.simbot.resources.ByteArrayResource;
import love.forte.simbot.resources.Resource;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;

@Component
public class Setu2 {
    @Listener
    @Filter(value = "/来一份(?<tags>.+)涩图")
    @Filter(value = "/来一份(?<tags>.+)瑟图")
    public void onSetuEvent(GroupMessageEvent event, @FilterValue("tags") String tags) throws MalformedURLException {
        event.replyAsync("查询中");
        String[] tag;
        if (tags.equals(",")) {
            tag = tags.split(",");
        } else if (tags.contains("，")) {
            tag = tags.split("，");
        } else {
            tag = new String[1];
            tag[0] = tags;
        }
        SetuData setu = new SetuData(tag);
        try {
            ByteArrayResource resource = Resource.of(setu.getImageByteData(), "setu");
            ResourceImage imageRes = Image.of(resource);
            event.replyAsync(imageRes);
        } catch (Exception e) {
            event.replyAsync("图片不存在或无法连接，请重试");
        }
    }
}
