package com.hucho.feign.demos.filter;

import com.hucho.feign.demos.util.GZIPUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;

/**
 * @ClassName: UnZIPRequestWrapper
 * @Author zhangjin
 * @Date 2022/3/26 11:02
 * @Description: 解压requestBody
 */
@Slf4j
public class UnZIPRequestWrapper extends HttpServletRequestWrapper {

    private byte[] newBodyBytes;

    public UnZIPRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        unzipBodyBytes(request.getInputStream());
    }

    @Override
    public ServletInputStream getInputStream() {
        return new TempInputStream(newBodyBytes);
    }

    private void unzipBodyBytes(ServletInputStream servletInputStream) throws IOException {
        try (BufferedInputStream bis = new BufferedInputStream(servletInputStream);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = bis.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            newBodyBytes = baos.toByteArray();
            if (newBodyBytes.length == 0) {
                log.warn("body is empty, no need to decompress.");
                return;
            }
            log.info("接收数据大小："+ newBodyBytes.length);
            newBodyBytes = GZIPUtil.unzip(newBodyBytes);
        } catch (IOException ex) {
            log.info("decompress ex", ex);
            throw ex;
        }
    }

    private static class TempInputStream extends ServletInputStream {
        private final ByteArrayInputStream tempInputStream;

        public TempInputStream(byte[] bytes) {
            tempInputStream = new ByteArrayInputStream(bytes);
        }

        @Override
        public boolean isFinished() {
            return false;
        }

        @Override
        public boolean isReady() {
            return false;
        }

        @Override
        public void setReadListener(ReadListener listener) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int read() throws IOException {
            return tempInputStream.read();
        }
    }
}