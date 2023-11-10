package com.example.qbot.listener;

import love.forte.simboot.annotation.ContentTrim;
import love.forte.simboot.annotation.Filter;
import love.forte.simboot.annotation.Listener;
import love.forte.simboot.filter.MatchType;
import love.forte.simbot.component.mirai.event.MiraiGroupNudgeEvent;
import love.forte.simbot.definition.Group;
import love.forte.simbot.event.GroupMessageEvent;
import love.forte.simbot.message.Image;
import love.forte.simbot.message.ResourceImage;
import love.forte.simbot.resources.ByteArrayResource;
import love.forte.simbot.resources.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class Pinch {
    @Listener
    @Filter(value = "/捏", matchType = MatchType.TEXT_EQUALS, targets = @Filter.Targets(atBot = true))
    @ContentTrim
    public void onPinch(GroupMessageEvent event) throws IOException {
        Group group = event.getGroup();
        Path path = Paths.get("E:\\Java\\QBot\\src\\main\\resources\\images\\pinch.png");
        byte[] bytes = Files.readAllBytes(path);
        ByteArrayResource resource = Resource.of(bytes, path.toString());
        ResourceImage image = Image.of(resource);
        group.sendAsync(image);
    }
}
