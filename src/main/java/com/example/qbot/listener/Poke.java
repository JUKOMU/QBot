package com.example.qbot.listener;

import love.forte.simboot.annotation.ContentTrim;
import love.forte.simboot.annotation.Filter;
import love.forte.simboot.annotation.Listener;
import love.forte.simboot.filter.MatchType;
import love.forte.simbot.component.mirai.event.MiraiMemberNudgeEvent;
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
public class Poke {
    @Listener
    @Filter(value = "/Poke", matchType = MatchType.TEXT_EQUALS_IGNORE_CASE)
    @ContentTrim
    public void onPoke(GroupMessageEvent event) throws IOException {
        System.out.println("?");
        Group group = event.getGroup();
        Path path = Paths.get("E:\\Java\\QBot\\src\\main\\resources\\images\\poke.jpg");
        byte[] bytes = Files.readAllBytes(path);
        ByteArrayResource resource = Resource.of(bytes, path.toString());
        ResourceImage image = Image.of(resource);
        group.sendBlocking(image);
    }
}
