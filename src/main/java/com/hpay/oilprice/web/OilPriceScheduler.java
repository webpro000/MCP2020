package com.hpay.oilprice.web;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * <pre>
 * Statements
 * </pre>
 *
 * @ClassName   : OilPriceScheduler.java
 * @Description : 클래스 설명을 기술합니다.
 * @author webpro000
 * @since 2020. 4. 23.
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2020. 4. 23.     webpro000     	최초 생성
 * </pre>
 */
@EnableScheduling
@Component
public class OilPriceScheduler {
//  @Scheduled(cron="0 0 02 * * ?")       = 매일 새벽2시에 실행
//  @Scheduled(cron="0 0 02 2,20 * ?")    = 매월 2일,20일 새벽2시에 실행
//  @Scheduled(cron="0 10 * * * ?")       = 매 10분 마다 실행.
//  @Scheduled(fixedRate = 10 * 1000L)    = 1초 간격으로 실행
//  @Scheduled(cron="*/10 * * * * ?")       //= 매 10초 마다 실행.
    
    //모젠센터OracleDB에서 유가정보 일 6회 수집 및 저장(1:40, 2:40, 9:40, 12:40, 16:40, 19:40)
    //@Scheduled(cron="*/15 * * * * ?")       //= 매 15초 마다 실행.
    @Scheduled(cron="0 40 1 * * ?")
    @Scheduled(cron="0 40 2 * * ?")
    @Scheduled(cron="0 40 9 * * ?")
    @Scheduled(cron="0 40 12 * * ?")
    @Scheduled(cron="0 40 16 * * ?")
    @Scheduled(cron="0 40 19 * * ?")
    public void executeGetOilPriceTasks() {
        SimpleDateFormat  formatter01 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String dt = formatter01.format(new Date());
        System.out.println("executeGetOilPriceTasks 실행:"+dt);
    }
}
