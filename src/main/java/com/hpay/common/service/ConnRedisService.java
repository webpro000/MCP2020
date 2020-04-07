package com.hpay.common.service;

import java.util.List;
import java.util.Map;

/**
 * <pre>
 * Statements
 * </pre>
 *
 * @ClassName   : ConnRedisService.java
 * @Description : 클래스 설명을 기술합니다.
 * @author O1484
 * @since 2019. 5. 3.
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2019. 5. 3.     O1484       최초 생성
 * </pre>
 */

public interface ConnRedisService {
    public void set(int dbNum, String key, String value)throws Exception;
    public void setHash(int dbNum, String key, List<String[]> value) throws Exception;
    public void setHash(int dbNum, String key, List<String[]> value, int expireSec) throws Exception;
    public void del(int dbNum, String key) throws Exception;
    /**
     * redis에 hash 가져오기
     * @param 
     * @throws Exception
     */
    public Map<String, String> getHash(int dbNum, String key) throws Exception;
}
