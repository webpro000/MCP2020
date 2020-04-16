package com.hpay.pharmacy.vo;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * <pre>
 * Statements
 * </pre>
 *
 * @ClassName   : PharmacyListVO.java
 * @Description : 클래스 설명을 기술합니다.
 * @author webpro000
 * @since 2020. 4. 16.
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2020. 4. 16.     webpro000     	최초 생성
 * </pre>
 */
@XmlRootElement(name="Record")
public class PharmacyListVO {
    
    private int RecordCnt = 0;
    
    private List<PharmacyVO> Record = new ArrayList<PharmacyVO>();

    /**
     * Statements
     *
     */
    public PharmacyListVO() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @return the recordCnt
     */
    public int getRecordCnt() {
        return RecordCnt;
    }

    /**
     * @param recordCnt the recordCnt to set
     */
    public void setRecordCnt(int recordCnt) {
        RecordCnt = recordCnt;
    }

    /**
     * @return the record
     */
    public List<PharmacyVO> getRecord() {
        return Record;
    }

    /**
     * @param record the record to set
     */
    public void setRecord(List<PharmacyVO> record) {
        Record = record;
    }
    
    
}
