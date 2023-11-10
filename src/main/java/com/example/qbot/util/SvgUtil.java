package com.example.qbot.util;

import ch.qos.logback.classic.Logger;
import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.batik.util.XMLResourceDescriptor;
import org.apache.xmlgraphics.image.loader.impl.ImageBuffered;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static javax.swing.text.DefaultStyledDocument.ElementSpec.ContentType;

public class SvgUtil {

    public static Image getImage(String svgPath) throws IOException, TranscoderException {
        /*
        System.out.println("1");
        String svgString = toString(svgPath);
        System.out.println("2");
        Image image = toPng(svgString);
         */
        String svgOriginFile = svgOriginFile(svgPath);
        InputStream input = svgToPng(svgOriginFile, 10.0F);
        BufferedImage image = ImageIO.read(input);
        FileOutputStream outImgStream = new FileOutputStream("E:\\Java\\QBot\\src\\main\\resources\\images\\weatherBackground3.png");
        ImageIO.write(image, "png", outImgStream);
        return image;
    }

/*
    /**
     * 将svg转换成字符串
     * @param svgPath
     * @return
     **
    public static String toString(String svgPath) {
        File file = new File(svgPath);
        String parser = XMLResourceDescriptor.getXMLParserClassName();
        SAXSVGDocumentFactory f = new SAXSVGDocumentFactory(parser);
        try {
            Document doc = f.createDocument(file.toURI().toString());
            Element documentElement = doc.getDocumentElement();

            TransformerFactory transFactory = TransformerFactory.newInstance();
            Transformer transformer = transFactory.newTransformer();
            StringWriter buffer = new StringWriter();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.transform(new DOMSource(documentElement), new StreamResult(buffer));
            System.out.println("buffer.toString()");
            return buffer.toString();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

 */
/*
    /**
     * svg转png
     * @param svgString
     * @return
     **
    public static Image toPng(String svgString) {
        InputStream inputStream = new ByteArrayInputStream(svgString.getBytes());
        PNGTranscoder transcoder = new PNGTranscoder();
        TranscoderInput input = new TranscoderInput(inputStream);
        ByteArrayOutputStream outputStream = null;
        try {
            outputStream = new ByteArrayOutputStream();
            TranscoderOutput output = new TranscoderOutput(outputStream);
            transcoder.transcode(input, output);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        byte[] pngData = outputStream.toByteArray();
        System.out.println("PNG data");
        return toImage(pngData);
    }
    */
/*
    public static Image toImage(byte[] pngData) {
        byte[] imageData = pngData;
        String format = "png";
        try {
            BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageData));
            File pngFile = new File("output." + format);
            ImageIO.write(image, format, pngFile);
            System.out.println("图片转换成功！");
            return image;
        } catch (IOException e) {
            System.out.println("图片转换失败：" + e.getMessage());
        }
        return null;
    }

 */

    /**
     * svg 转 png
     * @param originFile svg源文件码
     * @param multiple   放大倍数
     * @return InputStream
     * @throws IOException
     * @throws TranscoderException
     */
    public static InputStream svgToPng(String originFile, Float multiple) throws IOException, TranscoderException {
        if (originFile != null && multiple != null) {
            long st = System.currentTimeMillis();
            ByteArrayOutputStream pngStream = new ByteArrayOutputStream();
            InputStream pngInput;
            byte[] bytes = originFile.getBytes(StandardCharsets.UTF_8);
            TranscoderInput input = new TranscoderInput(new ByteArrayInputStream(bytes));
            PNGTranscoder t = new PNGTranscoder();
            //获取宽高，动态放大
            Float width = svgSize(originFile, "width");
            Float height = svgSize(originFile, "height");
            t.addTranscodingHint(PNGTranscoder.KEY_WIDTH, width * multiple);
            t.addTranscodingHint(PNGTranscoder.KEY_HEIGHT, height * multiple);
            //格式转换
            t.transcode(input, new TranscoderOutput(pngStream));
            byte[] outByte = pngStream.toByteArray();
            pngInput = new ByteArrayInputStream(outByte);
            long cost = System.currentTimeMillis() - st;
            System.out.println("SvgToPng success, cost time "+ cost +" ms");
            pngStream.close();
            return pngInput;
        } else {
            return null;
        }
    }


    /**
     * 获取svg文件源码
     *
     * @param svgPath svg文件路径
     * @return
     * @throws IOException
     */
    public static String svgOriginFile(String svgPath) throws IOException {
        File svgFile = new File(svgPath);
        MultipartFile file = file2MultipartFile(svgFile);
        if (file != null) {
            InputStream fileInput = file.getInputStream();
            byte[] inputByte = new byte[fileInput.available()];
            fileInput.read(inputByte);
            String originFile = new String(inputByte, StandardCharsets.UTF_8);
            System.out.println("originFile: " + originFile);
            fileInput.close();
            return originFile;
        }
        return null;
    }

    /**
     * 获取svg文件宽高
     *
     * @param originFile
     * @param lengthName
     * @return
     * @throws IOException
     */
    public static Float svgSize(String originFile, String lengthName) throws IOException {
        if (originFile != null && lengthName != null) {
            Document doc = svgDocument(originFile);
            Element element = doc.getDocumentElement();
            String length = lengthReg(element.getAttribute(lengthName));
            if (length == "" || length == null) {
                int num = lengthName.equals("width") ? 2 : 3;
                String viewBox = element.getAttribute("viewBox");
                length = viewBox.split(" ")[num];
            }
            System.out.println("svgSize(" + lengthName + length);
            return Float.parseFloat(length);
        }
        return null;
    }


    public static Document svgDocument(String originFile) throws IOException {
        byte[] bytes = originFile.getBytes(StandardCharsets.UTF_8);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        String parser = XMLResourceDescriptor.getXMLParserClassName();
        SAXSVGDocumentFactory f = new SAXSVGDocumentFactory(parser);
        Document doc = f.createDocument(null, inputStream);
        return doc;
    }

    public static String lengthReg(String str) {
        //用正则表达式
        String reg = "[^0-9]";
        //Pattern类的作用在于编译正则表达式后创建一个匹配模式.
        Pattern p = Pattern.compile(reg);
        //Matcher类使用Pattern实例提供的模式信息对正则表达式进行匹配
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    /**
     * 获取svg文件颜色
     *
     * @param originFile
     * @return
     * @throws IOException
     */
    public String selectColorByOriginFile(String originFile) throws IOException {
        if (originFile != null) {
            Document doc = svgDocument(originFile);
            Element element = doc.getDocumentElement();
            NodeList list = element.getElementsByTagName("path");
            Element colorElement = (Element) list.item(0);
            String color = colorElement.getAttribute("fill");
            Logger log = null;
            log.info("Get svg color [{}]", color);
            return color;
        }
        return null;
    }

    public static MultipartFile file2MultipartFile (File file) {
        MultipartFile multipartFile;
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            multipartFile = new MockMultipartFile("copy"+file.getName(),file.getName(), String.valueOf(ContentType),fileInputStream);
            System.out.println(multipartFile.getName()); // 输出demo.xlsx
            fileInputStream.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return multipartFile;
    }
}

