package com.hpay.common.service;

/**
 * <pre>
 * Statements
 * </pre>
 *
 * @ClassName   : UtilityService.java
 * @Description : 클래스 설명을 기술합니다.
 * @author O1484
 * @since 2019. 5. 3.
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2019. 5. 3.     O1484     	최초 생성
 * </pre>
 */

public interface UtilityService {
    public Boolean SaveFile(String physicalPath, String fileName, String value) throws Exception;
    public Boolean SaveFile(String physicalPath, String fileName, byte[] value) throws Exception ;    
    public byte[] ZipCompress(byte[] data) throws Exception;
    public byte[] ZipUncompress(byte[] data) throws Exception;
    public byte[] GzipCompress(byte[] data) throws Exception;
    public byte[] GzipUncompress(byte[] data) throws Exception;
    public String encodeBase64(byte[] data) throws Exception;
    public byte[] decodeBase64(String data) throws Exception;
    public String sendJson(String targetURI, String json) throws Exception;
    
    /**
     * Statements
     *
     * @param physicalPath
     * @param fileName
     * @param arrValue
     * @return
     * @throws Exception
     */
    Boolean SaveFileBinary(String physicalPath, String fileName, String[] arrValue) throws Exception;
}