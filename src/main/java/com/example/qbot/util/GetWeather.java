package com.example.qbot.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

@Slf4j
public class GetWeather {
    public static final String key = "6cbc4992b45e4fb7b6dcd6387ece35c0";
    public static Map<String,String> getWeather(String city){
        ArrayList<String> cityLocation = getLocation(city);
        if (!cityLocation.get(0).equals("200")){
            return null;
        }
        Map<String,String> map = getWeatherByHFTQ(cityLocation.get(1));
        map.put("adm1", cityLocation.get(2));
        return map;
    }


    /**
     * 从和风天气获取天气信息
     *
     * @param location 城市编码
     * @return
     */
    public static Map<String,String> getWeatherByHFTQ(String location) {
        Map<String,String> map = new HashMap<String,String>();

        StringBuffer sb = new StringBuffer();
        try {
            String weather_url =
                    "https://devapi.qweather.com/v7/weather/now?location=" + location + "&key=" + key;
            URL url = new URL(weather_url);
            URLConnection conn = url.openConnection();
            InputStream is = conn.getInputStream();
            GZIPInputStream gzin = new GZIPInputStream(is);
            InputStreamReader isr = new InputStreamReader(gzin, StandardCharsets.UTF_8); // 设置读取流的编码格式，自定义编码
            BufferedReader reader = new BufferedReader(isr);
            String line = null;
            while ((line = reader.readLine()) != null)
                sb.append(line + " ");
            reader.close();

            // 解析JSON格式的天气信息数据
            JSONObject json = JSONObject.parseObject(sb.toString());
            String code = json.getString("code");
            map.put("code", code);
            JSONObject now = json.getJSONObject("now");
            String obsTime = now.getString("obsTime");//数据观测时间
            map.put("obsTime",obsTime);
            String temp = now.getString("temp"); // 获取温度
            map.put("temp",temp);
            String feelsLike = now.getString("feelsLike"); //体感温度
            map.put("feelsLike",feelsLike);
            String feelsLikeLevel = getFeelsLikeLevel(Double.parseDouble(feelsLike));
            map.put("feelsLikeLevel",feelsLikeLevel);
            String icon = now.getString("icon"); //天气状况的天气状况的图标代码
            map.put("icon",icon);
            String text = now.getString("text"); //天气状况的文字描述，包括阴晴雨雪等天气状态的描述
            map.put("text",text);
            String windDir = now.getString("windDir"); // 获取风向
            map.put("windDir",windDir);
            String windSpeed = now.getString("windSpeed"); // 获取风速，公里/小时
            map.put("windSpeed",windSpeed);
            String windScale = now.getString("windScale"); // 获取风力
            map.put("windScale",windScale);
            String humidity = now.getString("humidity"); // 获取湿度
            map.put("humidity",humidity);
            String pressure = now.getString("pressure"); // 大气压强，默认单位：百帕
            map.put("pressure",pressure);
            // 输出获取到的天气信息
            System.out.println("状态码：" + code);
            System.out.println("城市编号：" + location);
            System.out.println("数据观测时间：" + obsTime);
            System.out.println("天气状况的文字描述：" + text);
            System.out.println("温度：" + temp + "℃");
            System.out.println("体感温度：" + feelsLike + "℃");
            System.out.println("体感等级：" + feelsLikeLevel);
            System.out.println("湿度：" + humidity + "%");
            System.out.println("风向：" + windDir);
            System.out.println("风速：" + windSpeed + "公里/小时");
            System.out.println("风力：" + windScale + "级");
            System.out.println("气压：" + pressure + "百帕");

        } catch (Exception e) {
            System.out.println("获取天气信息失败：" + e);
        }

        return map;
    }

    /**
     * 根据体感温度获取体感等级
     *
     * @param temp 体感温度
     * @return 体感等级
     */
    public static String getFeelsLikeLevel(double temp) {
        String level;
        if (temp > 40) {
            level = "极热";
        } else if (temp > 35) {
            level = "酷热";
        } else if (temp > 30) {
            level = "炎热";
        } else if (temp > 27) {
            level = "温暖";
        } else if (temp > 20) {
            level = "适宜";
        } else if (temp > 15) {
            level = "有点凉";
        } else if (temp > 10) {
            level = "凉爽";
        } else if (temp > 5) {
            level = "微冷";
        } else {
            level = "寒冷";
        }
        return level;
    }

    public static ArrayList<String> getLocation(String city){
        String id = "";
        String adm1 = "";
        ArrayList<String> list = new ArrayList<String>();
        list.add("999");
        StringBuffer sb = new StringBuffer();
        try {
            String weather_url =
                    "https://geoapi.qweather.com/v2/city/lookup?location=" + URLEncoder.encode(city, StandardCharsets.UTF_8) + "&key=" + key;
            URL url = new URL(weather_url);
            URLConnection conn = url.openConnection();
            InputStream is = conn.getInputStream();
            GZIPInputStream gzin = new GZIPInputStream(is);
            InputStreamReader isr = new InputStreamReader(gzin, StandardCharsets.UTF_8); // 设置读取流的编码格式，自定义编码
            BufferedReader reader = new BufferedReader(isr);
            String line = null;
            while ((line = reader.readLine()) != null)
                sb.append(line + " ");
            reader.close();

            // 解析JSON格式的天气信息数据
            JSONObject json = JSONObject.parseObject(sb.toString());
            String code = json.getString("code");
            JSONArray location = json.getJSONArray("location");
            JSONObject Fir = location.getJSONObject(0);
            id = Fir.getString("id"); //获取城市id
            adm1 = Fir.getString("adm1"); //获取城市所属一级行政区域
            list.set(0,code);
            list.add(id);
            list.add(adm1);
            // 输出获取到的天气信息
            System.out.println("code："+code);
            System.out.println("城市id："+id);
            System.out.println("城市所属一级行政区域："+adm1);

        } catch (Exception e) {
            System.out.println("获取天气信息失败：" + e);
            return list;
        }

        return list;
    }
/*
    public static void main(String[] args) {
        String city = "北京";
        String cityId = getLocationID(city);
        Map<String,String> map = getWeather(cityId);
        for (String key : map.keySet()) {
            System.out.println(key+":" + map.get(key));
        }
    }
 */

}