package com.ljryh.common.utils;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

import java.io.File;

/**
 * @author ljryh
 * @version 1.0
 * @date 2022/6/20 09:14
 */
public class ImageTagsUtils {

    private static String printImageTags(String s, File file) throws ImageProcessingException, Exception {
        Metadata metadata = ImageMetadataReader.readMetadata(file);
        for (Directory directory : metadata.getDirectories()) {
            for (Tag tag : directory.getTags()) {
                String tagName = tag.getTagName();  //标签名
                if (s.equals(tagName)) {
                    return tag.getDescription();
                }

                String desc = tag.getDescription(); //标签信息
                System.out.println(tagName + "   " + desc);//照片信息
            }
        }
        return null;
    }

}
