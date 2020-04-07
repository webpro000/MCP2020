package com.hpay.common.vo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * <pre>
 * Statements
 * </pre>
 *
 * @ClassName   : HpayLogVO.java
 * @Description : 클래스 설명을 기술합니다.
 * @author O1484
 * @since 2019. 6. 19.
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2019. 6. 19.     O1484      최초 생성
 * </pre>
 */

public class HpayLogVO {
    private int hpay_log_seq;
    private String interface_code;
    private String method;
    private String status_code;
    private Date start_date;
    private Date start_time;
    private Date end_date;
    private Date end_time;
    private long elapsed_ms;
    private String host_type;
    private String host;
    private String target_type;
    private String target;
    private int data_count_total;
    private int data_count_success;
    private int data_count_fail;
    private String error_code;
    private String error_msg;
    private int task_seq;
    private String task_uuid;
    
    /*
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "HpayLogVO [hpay_log_seq=" + hpay_log_seq + ", interface_code=" + interface_code + ", method=" + method
                + ", status_code=" + status_code + ", start_date=" + start_date + ", start_time=" + start_time
                + ", end_date=" + end_date + ", end_time=" + end_time + ", elapsed_ms=" + elapsed_ms + ", host_type="
                + host_type + ", host=" + host + ", target_type=" + target_type + ", target=" + target
                + ", data_count_total=" + data_count_total + ", data_count_success=" + data_count_success
                + ", data_count_fail=" + data_count_fail + ", error_code=" + error_code + ", error_msg=" + error_msg
                + ", task_seq=" + task_seq + ", task_uuid=" + task_uuid + "]";
    }
    public String getReturnData(Map<String, String> ReturnData){
        try {
            ObjectMapper mapper = new ObjectMapper(); 
            return mapper.writeValueAsString(ReturnData);
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"interfaceCode\":\""+this.interface_code+"\", \"resultMessage\":\""+e.getMessage()+"\"}";
        }        
    }
    public String getStr_start_date(){
        DateFormat format_date= new SimpleDateFormat("yyyy-MM-dd");
        return format_date.format(start_date);
    }

    public String getReturnData(String type){
        try {
            ObjectMapper mapper = new ObjectMapper(); 
            Map<String, String> ReturnData=new LinkedHashMap<String, String>();
            switch(type) {
                case "fail":
                    ReturnData.put("interfaceCode", this.interface_code);
                    ReturnData.put("resultCode", this.error_code);
                    ReturnData.put("resultMessage", this.error_msg);
                    break;
            }
            return mapper.writeValueAsString(ReturnData);
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"interfaceCode\":\""+this.interface_code+"\", \"resultMessage\":\""+e.getMessage()+"\"}";
        }
    }
    public int getHpay_log_seq() {
        return hpay_log_seq;
    }
    public void setHpay_log_seq(int hpay_log_seq) {
        this.hpay_log_seq = hpay_log_seq;
    }
    public String getInterface_code() {
        return interface_code;
    }
    public void setInterface_code(String interface_code) {
        this.interface_code = interface_code;
    }
    public Date getStart_date() {
        return start_date;
    }
    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }
    public Date getStart_time() {
        return start_time;
    }
    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }
    public Date getEnd_date() {
        if (end_date==null){
            return null;
        } else {
            return end_date;            
        }
    }
    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }
    public Date getEnd_time() {
        if (end_time==null){
            return null;
        } else {
            return end_time;            
        }
    }
    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }
    public long getElapsed_ms() {
        return elapsed_ms;
    }
    public void setElapsed_ms(long elapsed_ms) {
        this.elapsed_ms = elapsed_ms;
    }
    public String getHost_type() {
        return host_type;
    }
    public void setHost_type(String host_type) {
        this.host_type = host_type;
    }
    public String getHost() {
        return host;
    }
    public void setHost(String host) {
        this.host = host;
    }
    public String getTarget_type() {
        return target_type;
    }
    public void setTarget_type(String target_type) {
        this.target_type = target_type;
    }
    public String getTarget() {
        return target;
    }
    public void setTarget(String target) {
        this.target = target;
    }
    public int getData_count_total() {
        return data_count_total;
    }
    public void setData_count_total(int data_count_total) {
        this.data_count_total = data_count_total;
    }
    public int getData_count_success() {
        return data_count_success;
    }
    public void setData_count_success(int data_count_success) {
        this.data_count_success = data_count_success;
    }
    public int getData_count_fail() {
        return data_count_fail;
    }
    public void setData_count_fail(int data_count_fail) {
        this.data_count_fail = data_count_fail;
    }
    public String getStatus_code() {
        return status_code;
    }
    public void setStatus_code(String status_code) {
        this.status_code = status_code;
    }
    public int getTask_seq() {
        return task_seq;
    }
    public void setTask_seq(int task_seq) {
        this.task_seq= task_seq;
    }
    public String getTask_uuid() {
        return task_uuid;
    }
    public void setTask_uuid(String task_uuid) {
        this.task_uuid = task_uuid;
    }
    public String getMethod() {
        if (method==null){
            return "";
        } else {
            return method;            
        }
    }
    public void setMethod(String method) {
        this.method = method;
    }
    public String getError_code() {
        return error_code;
    }
    public void setError_code(String error_code) {
        this.error_code = error_code;
    }
    public String getError_msg() {
        return error_msg;
    }
    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }
}