package com.example.qbot.listener;

import com.example.qbot.data.SetuData;
import love.forte.simboot.annotation.Filter;
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
public class Setu1 {
    @Listener
    @Filter(value = "/来一份涩图", matchType = MatchType.TEXT_EQUALS)
    @Filter(value = "/来一份瑟图", matchType = MatchType.TEXT_EQUALS)
    @Filter(value = "/来一张涩图", matchType = MatchType.TEXT_EQUALS)
    @Filter(value = "/来一张瑟图", matchType = MatchType.TEXT_EQUALS)
    @Filter(value = "setu", matchType = MatchType.TEXT_EQUALS_IGNORE_CASE)
    public void onSetuEvent(GroupMessageEvent event) throws MalformedURLException {
        event.replyAsync("查询中");
        SetuData setu = new SetuData();
        try {
            ByteArrayResource resource = Resource.of(setu.getImageByteData(), "setu");
            ResourceImage imageRes = Image.of(resource);
            event.replyAsync(imageRes);
        } catch (Exception e) {
            event.replyAsync("图片不存在或无法连接，请重试");
        }
    }
}
