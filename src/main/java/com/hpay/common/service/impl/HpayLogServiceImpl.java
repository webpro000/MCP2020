package com.hpay.common.service.impl;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.annotation.Resource;

import able.com.service.HService;

import org.springframework.stereotype.Service;

import com.hpay.common.service.HpayLogService;
import com.hpay.common.service.dao.HpayLogMDAO;
import com.hpay.common.vo.HpayLogVO;

/**
 * <pre>
 * Statements
 * </pre>
 *
 * @ClassName   : HpayLogServiceImpl.java
 * @Description : 서비스 로그 비즈니스로직 구현 클래스
 * @author O1484
 * @since 2019. 6. 19.
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 *     since             author      description
 *  =========    =======    ==============
 *  2019. 6. 19.     O1484      최초 생성
 *  2019. 6. 25.     O1484       UUID 추가
 *  2019. 7. 26.     O1484       기능수정
 * </pre>
 */
@Service("hpayLogService")
public class HpayLogServiceImpl extends HService implements HpayLogService {
    @Resource(name = "hpayLogMDAO")
    private HpayLogMDAO hpayLogMDAO;

    /**
     * 로그서비스 시작
     *
     * @param interfaceCode
     * @return
     */
    @Override
    public HpayLogVO init(String interfaceCode)  {
        String hostIp="";
        try {
            hostIp = Inet4Address.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        HpayLogVO voHpayLog=new HpayLogVO();
        
        voHpayLog.setInterface_code(interfaceCode);
        Date now =new Date();
        voHpayLog.setStart_date(now);
        voHpayLog.setHost_type(HpayLogService.typeIp);
        voHpayLog.setHost(hostIp);
        voHpayLog.setStatus_code(HpayLogService.statusActive);
        voHpayLog.setTask_seq(0);
        voHpayLog.setHpay_log_seq(0);
        return voHpayLog;
    }

    /**
     * 로그서비스 시작
     *
     * @param interfaceCode
     * @param targetType
     * @param target
     * @return
     */
    @Override
    public HpayLogVO init(String interfaceCode, String targetType, String target)  {
        String hostIp="";
        try {
            hostIp = Inet4Address.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        HpayLogVO voHpayLog=new HpayLogVO();
        voHpayLog.setInterface_code(interfaceCode);
        Date now =new Date();
        voHpayLog.setStart_date(now);
        voHpayLog.setHost_type(HpayLogService.typeIp);
        voHpayLog.setHost(hostIp);
        voHpayLog.setTarget_type(targetType);
        voHpayLog.setTarget(target);
        voHpayLog.setStatus_code(HpayLogService.statusActive);
        voHpayLog.setTask_seq(0);
        voHpayLog.setHpay_log_seq(0);
        return voHpayLog;
    }

    /**
     * 로그서비스 시작 (Async)
     *
     * @param interfaceCode
     * @param targetType
     * @param target
     * @param method
     * @return
     */
    @Override
    public HpayLogVO init(String interfaceCode, String targetType, String target, String method) {
        String hostIp="";
        try {
            hostIp = Inet4Address.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        HpayLogVO voHpayLog=new HpayLogVO();
        voHpayLog.setInterface_code(interfaceCode);
        Date now =new Date();
        voHpayLog.setStart_date(now);
        voHpayLog.setHost_type(HpayLogService.typeIp);
        voHpayLog.setHost(hostIp);
        voHpayLog.setTarget_type(targetType);
        voHpayLog.setTarget(target);
        voHpayLog.setStatus_code(HpayLogService.statusActive);
        voHpayLog.setTask_seq(0);
        voHpayLog.setMethod(method);
        voHpayLog.setHpay_log_seq(0);
        update(voHpayLog);

        return voHpayLog;
    }

    /**
     * 로그서비스 시작 (서버 경합)
     *
     * @param interfaceCode
     * @param targetType
     * @param target
     * @return
     */
   /* @Override
    public HpayLogVO initTask(String interfaceCode, String targetType, String target)  {
        String hostIp="";
        try {
            hostIp = Inet4Address.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            logger.info("UnknownHostException:Inet4Address.getLocalHost().getHostAddress()");
            e.printStackTrace();
        }
        logger.info("interfacecodes"+interfaceCode);

        HpayLogVO voHpayLog=new HpayLogVO();
        voHpayLog.setInterface_code(interfaceCode);
        Date now =new Date();
        voHpayLog.setStart_date(now);
        voHpayLog.setHost_type(HpayLogService.typeIp);
        voHpayLog.setHost(hostIp);
        voHpayLog.setTarget_type(targetType);
        voHpayLog.setTarget(target);
        voHpayLog.setStatus_code(HpayLogService.statusStandby);

        //인터벌 내로 완료가 있는지 체크 
        int todaySeq;
        try {
            todaySeq = hpayLogMDAO.selectMaxTodayWorkSeq(voHpayLog);
            todaySeq++;
        } catch (Exception e) {
            e.printStackTrace();
            todaySeq=0;
        }
        voHpayLog.setTask_seq(todaySeq);
        voHpayLog.setTask_uuid(UUID.randomUUID().toString());
        voHpayLog.setHpay_log_seq(0);
        update(voHpayLog);
        return voHpayLog;
    }*/
    
    @Override
    public HpayLogVO initTask(String interfaceCode, String targetType, String target)  {
        String hostIp="";
        try {
            hostIp = Inet4Address.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            logger.info("UnknownHostException:Inet4Address.getLocalHost().getHostAddress()");
            e.printStackTrace();
        }

        HpayLogVO voHpayLog=new HpayLogVO();
        voHpayLog.setInterface_code(interfaceCode);
        Date now =new Date();
        voHpayLog.setStart_date(now);
        voHpayLog.setHost_type(HpayLogService.typeIp);
        voHpayLog.setHost(hostIp);
        voHpayLog.setTarget_type(targetType);
        voHpayLog.setTarget(target);
        voHpayLog.setStatus_code(HpayLogService.statusStandby);

        //인터벌 내로 완료가 있는지 체크 
        int todaySeq;
        try {
            todaySeq = hpayLogMDAO.selectMaxTodayWorkSeq(voHpayLog);
            todaySeq++;
        } catch (Exception e) {
            e.printStackTrace();
            todaySeq=0;
        }
        voHpayLog.setTask_seq(todaySeq);
        voHpayLog.setTask_uuid(UUID.randomUUID().toString());
        voHpayLog.setHpay_log_seq(0);
        update(voHpayLog);
        return voHpayLog;
    }
    
    
    /**
     * 서버 경합 결과 체크
     *
     * @param voHpayLog
     * @return
     */
    @Override
    public Boolean checkOrder(HpayLogVO voHpayLog) {
        try {
            Thread.sleep(5);
            String workHost=hpayLogMDAO.selectStandbyOrder(voHpayLog);
            logger.info("getTask_seq는"+voHpayLog.getTask_seq());
            
            logger.info(voHpayLog.getTask_uuid()+"-"+workHost);
            if (workHost==null) {
                return false; 
            } else if (workHost.equals(voHpayLog.getTask_uuid())) {
                logger.info("Task "+ voHpayLog.getTask_uuid() +" : "+HpayLogService.statusActive);
                Date now =new Date();
                voHpayLog.setStart_date(now);
                voHpayLog.setStatus_code(HpayLogService.statusActive);
                update(voHpayLog);
                return true;
            } else {
                logger.info("Task "+ voHpayLog.getTask_uuid() +" : "+HpayLogService.statusStandbyDone);
                voHpayLog.setStatus_code(HpayLogService.statusStandbyDone);
                update(voHpayLog);
                return false; 
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 로그서비스 종료
     *
     * @param voHpayLog
     */
    @Override
    public void update(HpayLogVO voHpayLog)  {         
        try {

            logger.info("hpayinterface"+voHpayLog.getInterface_code());
            //logger.info("===============Start_date():"+voHpayLog.getStart_date());
            //logger.info("===============Start_time():"+voHpayLog.getStart_time());
            if(voHpayLog.getStart_time() == null) {
                voHpayLog.setStart_time(voHpayLog.getStart_date()); //date
            }
            //logger.info("============date Start_time():"+voHpayLog.getStart_time());
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); //date 시간수정 00:00:00
            
            String tmp = sdf.format(voHpayLog.getStart_date());
            try {
                voHpayLog.setStart_date(sdf.parse(tmp));
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            //logger.info("*************Start_date():"+voHpayLog.getStart_date());
            //logger.info("*************Start_time():"+voHpayLog.getStart_time());
            
            if (voHpayLog.getHpay_log_seq()==0){
                int hpay_log_seq;
                hpay_log_seq = hpayLogMDAO.insertHpayLog(voHpayLog);
                voHpayLog.setHpay_log_seq(hpay_log_seq);
                logger.info("insert hpayLog "+voHpayLog.getHpay_log_seq()+"-"+voHpayLog.getInterface_code()+" : "+voHpayLog.getStatus_code());
            } else {
                hpayLogMDAO.updateHpayLog(voHpayLog);            
                logger.info("update hpayLog "+voHpayLog.getHpay_log_seq()+"-"+voHpayLog.getInterface_code()+" : "+voHpayLog.getStatus_code());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 건수 세팅(제공건수-성공건수=실패건수) 
     *
     * @param voHpayLog
     * @param countTotal
     * @param countSuccess
     */
    @Override
    public void setCount(HpayLogVO voHpayLog, int countTotal, int countSuccess) {
        voHpayLog.setData_count_total(countTotal);
        voHpayLog.setData_count_success(countSuccess);
        voHpayLog.setData_count_fail(countTotal-countSuccess);
    }

    /**
     * 로그서비스 상태 저장
     *
     * @param voHpayLog
     * @param Status
     */
    @Override
    public void setDone(HpayLogVO voHpayLog, String Status) {
        try{
            
        Date now =new Date();
        voHpayLog.setEnd_date(now);
        if(voHpayLog.getStart_time() == null) {
            voHpayLog.setStart_time(voHpayLog.getStart_date()); //date
        }
        voHpayLog.setElapsed_ms(voHpayLog.getEnd_date().getTime()-voHpayLog.getStart_time().getTime());        
        voHpayLog.setStatus_code(Status);
        } catch (Exception e) {
            logger.info("setDone Exception==>"+e.getMessage());
                 
        }
    }
    
    /**
     * 로그서비스 결과 저장
     *
     * @param voHpayLog
     * @param Status
     * @param errCode
     * @param errMsg
     */
    @Override
    public void setDone(HpayLogVO voHpayLog, String Status, String error_code, String error_msg) {
        
        try{
        Date now =new Date();
        voHpayLog.setEnd_date(now);
        
        if(voHpayLog.getStart_time() == null) {
            voHpayLog.setStart_time(voHpayLog.getStart_date()); //date
        }
        voHpayLog.setElapsed_ms(voHpayLog.getEnd_date().getTime()-voHpayLog.getStart_time().getTime());        
        voHpayLog.setStatus_code(Status);
        voHpayLog.setError_code(error_code);
        voHpayLog.setError_msg(error_msg);
        
        } catch (Exception e) {
            logger.info("setDone Exception==>"+e.getMessage());
                 
        }
    }
        
    /**
     * 인증키 결과 세팅
     *
     * @param voHpayLog
     * @param targetType
     * @param target
     */
    @Override
    public void setTarget(HpayLogVO voHpayLog, String targetType, String target) {
        voHpayLog.setTarget_type(targetType);
        voHpayLog.setTarget(target);
    }
}