package com.hpay.common.filter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.io.IOUtils;

/**
 * <pre>
 * Statements
 * </pre>
 *
 * @ClassName   : HttpRequestWrapper.java
 * @Description : 클래스 설명을 기술합니다.
 * @author O1484
 * @since 2019. 6. 18.
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2019. 6. 18.     O1484     	최초 생성
 * </pre>
 */

public class HttpRequestWrapper extends HttpServletRequestWrapper {

    /** HTTP request body data */
    private byte[] bodyData;

    /**
     * @param request
     * @throws IOException
     */
    public HttpRequestWrapper(HttpServletRequest request) throws IOException {
     super(request);
     InputStream is = super.getInputStream();
     bodyData = IOUtils.toByteArray(is);
    }

    /**
     * <pre>
     * getInputStream
     *
     * <pre>
     * @return
     * @throws IOException
     */
    @Override
    public ServletInputStream getInputStream() throws IOException {
     final ByteArrayInputStream bis = new ByteArrayInputStream(bodyData);
     return new ServletImpl(bis);
    }
   }
