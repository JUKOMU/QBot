package com.example.qbot.listener;

import love.forte.simboot.annotation.Listener;
import love.forte.simbot.component.mirai.event.MiraiMemberJoinEvent;
import love.forte.simbot.definition.Group;
import love.forte.simbot.message.Message;
import love.forte.simbot.message.MessagesBuilder;
import org.springframework.stereotype.Component;

@Component
public class WelcomeNewMember {
    @Listener
    public void onEvent(MiraiMemberJoinEvent event) {
        Message message = new MessagesBuilder().text("欢迎新人!").build();
        Group group = event.getGroup();
        group.sendAsync(message);
    }
}
