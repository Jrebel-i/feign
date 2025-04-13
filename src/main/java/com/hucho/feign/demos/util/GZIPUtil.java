package com.hucho.feign.demos.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @author 475636591@qq.com
 * @since 2023/10/13 10:53
 */
@Slf4j
public class GZIPUtil {

    /**
     * 压缩字符串
     *
     * @param str 待压缩的字符串
     * @return 压缩后的字节数组
     * @throws IOException
     */
    public static byte[] gzipString(String str) throws IOException {
        if (!StringUtils.hasText(str))
            throw new RuntimeException("gzip String is empty.");
        return gzip(str.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 解压缩为字符串
     *
     * @param byteArray 待解压的字节数组
     * @return 解压后的字符串
     */
    public static String unzipString(byte[] byteArray) throws IOException {
        byteArray = unzip(byteArray);
        return new String(byteArray, StandardCharsets.UTF_8);
    }

    /**
     * 压缩
     *
     * @param byteArray 待压缩的字节数组
     * @return 压缩后的字节数组
     */
    public static byte[] gzip(byte[] byteArray) throws IOException {
        if (byteArray == null || byteArray.length == 0) {
            throw new RuntimeException("gzip byteArray is empty.");
        }

        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             GZIPOutputStream gzip = new GZIPOutputStream(out)) {
            gzip.write(byteArray);
            gzip.flush();
            gzip.finish();
            byteArray = out.toByteArray();
        }
        return byteArray;
    }

    /**
     * 解压缩
     *
     * @param byteArray 待解压的字节数组
     * @return 解压后的字节数组
     */
    public static byte[] unzip(byte[] byteArray) throws IOException {
        if (byteArray == null || byteArray.length == 0) {
            throw new RuntimeException("unzip byteArray is empty.");
        }
        try (ByteArrayInputStream byteArrayInput = new ByteArrayInputStream(byteArray);
             GZIPInputStream gzipInput = new GZIPInputStream(byteArrayInput);
             ByteArrayOutputStream byteArrayOutput = new ByteArrayOutputStream();) {
            byte[] buffer = new byte[1024];
            int n;
            while ((n = gzipInput.read(buffer)) != -1) {
                byteArrayOutput.write(buffer, 0, n);
            }
            return byteArrayOutput.toByteArray();
        }
    }

}
