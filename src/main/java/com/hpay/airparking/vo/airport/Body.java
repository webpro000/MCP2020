package com.hpay.airparking.vo.airport;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <pre>
 * Statements
 * </pre>
 *
 * @ClassName   : Body.java
 * @Description : 클래스 설명을 기술합니다.
 * @author waveM
 * @since 2019. 10. 4.
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2019. 10. 4.     waveM      최초 생성
 * </pre>
 */

@XmlRootElement(name="body")
public class Body {
    
    @XmlElement(name="items")
    private Items items;

    public Items getItems() {
        return items;
    }
}
