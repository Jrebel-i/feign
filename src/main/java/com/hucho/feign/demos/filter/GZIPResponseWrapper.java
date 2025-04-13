package com.hucho.feign.demos.filter;

import com.hucho.feign.demos.util.GZIPUtil;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author 475636591@qq.com
 * @since 2023/11/13 17:39
 * 压缩ResponseBody
 */
public class GZIPResponseWrapper extends HttpServletResponseWrapper {

    private final TempOutputStream tempOutputStream = new TempOutputStream();

    public GZIPResponseWrapper(HttpServletResponse response) {
        super(response);
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return tempOutputStream;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return new PrintWriter(tempOutputStream);
    }

    public void resetResponse() {
        try {
            byte[] gzipByteArray = GZIPUtil.gzip(tempOutputStream.out.toByteArray());
            getResponse().getOutputStream().write(gzipByteArray);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static class TempOutputStream extends ServletOutputStream {
        private final ByteArrayOutputStream out = new ByteArrayOutputStream();

        public boolean isReady() {
            return true;
        }

        @Override
        public void setWriteListener(WriteListener listener) {
        }

        @Override
        public void write(int b) throws IOException {
            out.write(b);
        }
    }
}
