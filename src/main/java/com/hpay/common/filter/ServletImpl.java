package com.hpay.common.filter;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletInputStream;

/**
 * <pre>
 * Statements
 * </pre>
 *
 * @ClassName   : ServletImpl.java
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

class ServletImpl extends ServletInputStream {

    private InputStream is;

    public ServletImpl(InputStream bis) {
     is = bis;
    }

    @Override
    public int read() throws IOException {
     return is.read();
    }

    @Override
    public int read(byte[] b) throws IOException {
     return is.read(b);
    }

   }
