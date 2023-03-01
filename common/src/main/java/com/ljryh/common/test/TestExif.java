package com.ljryh.common.test;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

import java.io.File;

/**
 * @author ljryh
 * @version 1.0
 * @date 2022/6/13 15:53
 */
public class TestExif {



    public static void main(String[] args) throws Exception {

        File file = new File("/Users/yibaiyi/Library/Containers/com.tencent.xinWeChat/Data/Library/Application Support/com.tencent.xinWeChat/2.0b4.0.9/363f9cbfe2b9addb1f277b74129580af/Message/MessageTemp/21dbf2b3eb60ddb819b9cb20117ef67e/Image/34221676858120_.pic_hd.jpg");
//        File file = new File("/Users/yibaiyi/Downloads/GetImage.jpeg");
//        File file = new File("/Users/yibaiyi/Downloads/GetImage.jpeg");
//        File file = new File("/Users/yibaiyi/Library/Containers/com.tencent.xinWeChat/Data/Library/Application Support/com.tencent.xinWeChat/2.0b4.0.9/363f9cbfe2b9addb1f277b74129580af/Message/MessageTemp/9818b952f4d84898c8a5335e97fa6bdf/Image/41711655276744_.pic.jpg");
//        File file = new File("/Users/yibaiyi/Library/Containers/com.tencent.xinWeChat/Data/Library/Application Support/com.tencent.xinWeChat/2.0b4.0.9/363f9cbfe2b9addb1f277b74129580af/Message/MessageTemp/8c7f53d5dc7fa11a456b1d3bc4bca874/Image/7301655276706_.pic_hd.jpg");
        System.out.println(printImageTags(file));
    }
    /**
     * 读取照片相关信息
     */
    private static String printImageTags(File file) throws ImageProcessingException, Exception {
        Metadata metadata = ImageMetadataReader.readMetadata(file);
        for (Directory directory : metadata.getDirectories()) {
            for (Tag tag : directory.getTags()) {
                String tagName = tag.getTagName();  //标签名
//                if("Software".equals(tagName)){
//                    return tag.getDescription();
//                }

                String desc = tag.getDescription(); //标签信息
                System.out.println(tagName + "   " + desc);//照片信息
            }
        }
        return null;
    }


}

