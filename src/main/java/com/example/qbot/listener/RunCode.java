package com.example.qbot.listener;

import com.example.qbot.compile.Answer;
import com.example.qbot.compile.Question;
import com.example.qbot.compile.Task;
import love.forte.simboot.annotation.ContentTrim;
import love.forte.simboot.annotation.Filter;
import love.forte.simboot.annotation.FilterValue;
import love.forte.simboot.annotation.Listener;
import love.forte.simboot.filter.MatchType;
import love.forte.simbot.event.GroupMessageEvent;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RunCode {
    @Listener
    @Filter(value = "/run", matchType = MatchType.TEXT_CONTAINS)
    public void onRunCodeEvent(GroupMessageEvent event) throws IOException {
        String message = event.getMessageContent().toString();
        int index = message.indexOf("run");
        int end = message.indexOf("messageSource");
        String code = message.substring(index+3,end-2);
        Task task = new Task();
        Question question = new Question();
        question.setCode(code);
        Answer answer = task.compileAndRun(question);
        int errorCode = answer.getError();
        if (errorCode == 0){
            String result = "运行成功:\n" + answer.getStdout();
            event.replyAsync(result);
        } else if (errorCode == 1) {
            String result = "编译失败:\n" + getError(answer.getReason());
            event.replyAsync(result);
        } else if (errorCode == 2) {
            String result = "运行出错:\n" + getError(answer.getReason());
            event.replyAsync(result);
        }
        else {
            event.replyAsync("未知的错误！");
        }
    }
    public static String getError(String error) {
        int index = error.indexOf("错误");
        String result = error.substring(index);
        return result;
    }
}
