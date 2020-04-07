package com.hpay.common.filter;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.compress.utils.IOUtils;

import com.hpay.common.service.UtilityService;
import com.hpay.common.service.impl.UtilityServiceImpl;

/**
 * <pre>
 * Statements
 * </pre>
 *
 * @ClassName   : SaveBody.java
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

public class SaveDump implements Filter {
    //private static Logger logger= Logger.getLogger(SaveDump.class);
    private String physicalPath;
    private String filenamePrefix;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException { // 모든 요청(Request)에 대해서 이 부분이 실행된다.
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpRequestWrapper requestWrapper = new HttpRequestWrapper(request);

        InputStream in =requestWrapper.getInputStream();
        byte[] bytes = IOUtils.toByteArray(in);
        String requestBody = new String(bytes);
        //logger.info(requestBody);
        Date now=new Date();
        DateFormat format_date= new SimpleDateFormat("yyyyMMdd");
        
        String fileName=filenamePrefix+"_"+format_date.format(now)+"_"+new Date().getTime();
        UtilityService utility=new UtilityServiceImpl();
        try {
            utility.SaveFile(physicalPath, fileName, requestBody);
        } catch (Exception e) {
        }
        chain.doFilter(requestWrapper, response);
    }

    /*
     * @see javax.servlet.Filter#destroy()
     */
    @Override
    public void destroy() {
        // TODO Auto-generated method stub
        
    }

    /*
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    @Override
    public void init(FilterConfig config) throws ServletException {
        physicalPath=config.getInitParameter("physicalPath");        
        filenamePrefix=config.getInitParameter("filenamePrefix");        
    }
}
