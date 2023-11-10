package com.example.qbot.data;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.charset.StandardCharsets;

@Getter
public class SetuData {
    // 作品 pid
    private String pid;
    // 作品所在页
    private String p;
    // 作者 uid
    private String uid;
    // 作品标题
    private String title;
    // 作者名（入库时，并过滤掉 @ 及其后内容）
    private String author;
    // 是否 R18（在库中的分类，不等同于作品本身的 R18 标识）
    private String r18;
    // 原图宽度 px
    private String width;
    // 原图高度 px
    private String height;
    // 作品标签，包含标签的中文翻译（有的话）
    private String[] tags;
    // 图片扩展名
    private String ext;
    // 是否是 AI 作品，0 未知（旧画作或字段未更新），1 不是，2 是
    private String aiType;
    // 作品上传日期；时间戳，单位为毫秒
    private String uploadDate;
    // 包含了所有指定size的图片地址
    private String urls;
    // 图片资源
    private byte[] imageByteData;

    // 不带tag的请求
    public SetuData() throws MalformedURLException {
        String jsonString = getUrlJsonString("https://api.lolicon.app/setu/v2?size=regular");
        readJson(jsonString);
    }

    // 带tag的请求
    public SetuData(String[] values) throws MalformedURLException {
        StringBuilder url = new StringBuilder("https://api.lolicon.app/setu/v2?size=regular");
        for (String value : values) {
            url.append("&tag=").append(URLEncoder.encode(value, StandardCharsets.UTF_8));
        }
        String jsonString = getUrlJsonString(url.toString());
        readJson(jsonString);
    }

    private void readJson(String json) {
        // 解析JSON格式的天气信息数据
        JSONObject JSON = JSONObject.parseObject(json);
        JSONArray JSONArray = JSON.getJSONArray("data");
        JSONObject data = JSONArray.getJSONObject(0);
        pid = data.getString("pid");
        p = data.getString("p");
        uid = data.getString("uid");
        title = data.getString("title");
        author = data.getString("author");
        r18 = data.getString("r18");
        width = data.getString("width");
        height = data.getString("height");
        tags = data.getJSONArray("tags").toArray(new String[0]);
        ext = data.getString("ext");
        aiType = data.getString("aiType");
        uploadDate = data.getString("uploadDate");
        urls = data.getJSONObject("urls").getString("regular");

        try {
            //获取下载地址
            URL url = new URL(urls);
            InputStream in = null;
            ByteArrayOutputStream out = null;
            try {
                //链接网络地址，创建连接对象
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                //建立到远程对象的实际连接
                connection.connect();
                //连接成功
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    //获取链接的输入流
                    in = connection.getInputStream();
                    //写入
                    out = new ByteArrayOutputStream();
                    byte[] buf = new byte[1024];
                    int len;
                    while ((len = in.read(buf)) != -1) {
                        out.write(buf, 0, len);
                    }

                    out.flush();
                    imageByteData = out.toByteArray();
                    //断开连接
                    connection.disconnect();
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private String getUrlJsonString(String URLS) {
        StringBuffer sb = new StringBuffer();
        try {
            URL url = new URL(URLS);
            URLConnection conn = url.openConnection();
            InputStream is = conn.getInputStream();
            InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8); // 设置读取流的编码格式，自定义编码
            BufferedReader reader = new BufferedReader(isr);
            String line = null;
            while ((line = reader.readLine()) != null)
                sb.append(line + " ");
            reader.close();
        } catch (Exception e) {
            System.out.println("获取setu数据失败：" + e);
        }
        return sb.toString();
    }
}
