package com.gary.garytool.business.baseframe.model;

/**
 * Created by Gary on 2016/6/3/003.
 */
public class Order {
    //{"orderid":7322,
    // "carnum":"赣TTTGT7",
    // "proname":"釉蜡外洗",
    // "owname":"大笨强",
    // "owner_phone":"18883170884",
    // "techname":"何晓炜",
    // "tech_phone":"10083170880",
    // "remark":"",
    // "areaname":"凤凰城凤锦苑1街凤锦一街负一层(8-15栋)012车位",
    // "state_desc":"已取消",
    // "state":3,
    // "createtime":"2016-05-22 14:41","
    // finishtime":"2016-05-22T15:20:00",
    // "est_starttime":null,
    // "est_tech_finishtime":null,
    // "est_finishtime":null,
    // "starttime":null,
    // "tech_finishtime":null,
    // "owner_store_key_time":null,
    // "tech_take_key_time":null,
    // "tech_store_key_time":null,
    // "owner_take_key_time":null,
    // "estStartAndElapsedtime":"",
    // "actStartAndElapsedTime":"",
    // "caneceltime":"2016-05-22 15:20","cancel_reason":""}

    private String orderid;
    private String carnum;
    private String proname;
    private String owname;
    private String owner_phone;
    private String echname;
    private String tech_phone;
    private String remark;
    private String areaname;
    private String state_desc;
    private String state;
    private String createtime;
    private String finishtime;
    private String est_starttime;
    private String est_tech_finishtime;
    private String est_finishtime;
    private String starttime;
    private String tech_finishtime;
    private String owner_store_key_time;
    private String tech_take_key_time;
    private String tech_store_key_time;
    private String owner_take_key_time;
    private String estStartAndElapsedtime;
    private String actStartAndElapsedTime;
    private String caneceltime;

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getCarnum() {
        return carnum;
    }

    public void setCarnum(String carnum) {
        this.carnum = carnum;
    }

    public String getProname() {
        return proname;
    }

    public void setProname(String proname) {
        this.proname = proname;
    }

    public String getOwname() {
        return owname;
    }

    public void setOwname(String owname) {
        this.owname = owname;
    }

    public String getOwner_phone() {
        return owner_phone;
    }

    public void setOwner_phone(String owner_phone) {
        this.owner_phone = owner_phone;
    }

    public String getEchname() {
        return echname;
    }

    public void setEchname(String echname) {
        this.echname = echname;
    }

    public String getTech_phone() {
        return tech_phone;
    }

    public void setTech_phone(String tech_phone) {
        this.tech_phone = tech_phone;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAreaname() {
        return areaname;
    }

    public void setAreaname(String areaname) {
        this.areaname = areaname;
    }

    public String getState_desc() {
        return state_desc;
    }

    public void setState_desc(String state_desc) {
        this.state_desc = state_desc;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getFinishtime() {
        return finishtime;
    }

    public void setFinishtime(String finishtime) {
        this.finishtime = finishtime;
    }

    public String getEst_starttime() {
        return est_starttime;
    }

    public void setEst_starttime(String est_starttime) {
        this.est_starttime = est_starttime;
    }

    public String getEst_tech_finishtime() {
        return est_tech_finishtime;
    }

    public void setEst_tech_finishtime(String est_tech_finishtime) {
        this.est_tech_finishtime = est_tech_finishtime;
    }

    public String getEst_finishtime() {
        return est_finishtime;
    }

    public void setEst_finishtime(String est_finishtime) {
        this.est_finishtime = est_finishtime;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getTech_finishtime() {
        return tech_finishtime;
    }

    public void setTech_finishtime(String tech_finishtime) {
        this.tech_finishtime = tech_finishtime;
    }

    public String getOwner_store_key_time() {
        return owner_store_key_time;
    }

    public void setOwner_store_key_time(String owner_store_key_time) {
        this.owner_store_key_time = owner_store_key_time;
    }

    public String getTech_take_key_time() {
        return tech_take_key_time;
    }

    public void setTech_take_key_time(String tech_take_key_time) {
        this.tech_take_key_time = tech_take_key_time;
    }

    public String getTech_store_key_time() {
        return tech_store_key_time;
    }

    public void setTech_store_key_time(String tech_store_key_time) {
        this.tech_store_key_time = tech_store_key_time;
    }

    public String getOwner_take_key_time() {
        return owner_take_key_time;
    }

    public void setOwner_take_key_time(String owner_take_key_time) {
        this.owner_take_key_time = owner_take_key_time;
    }

    public String getEstStartAndElapsedtime() {
        return estStartAndElapsedtime;
    }

    public void setEstStartAndElapsedtime(String estStartAndElapsedtime) {
        this.estStartAndElapsedtime = estStartAndElapsedtime;
    }

    public String getActStartAndElapsedTime() {
        return actStartAndElapsedTime;
    }

    public void setActStartAndElapsedTime(String actStartAndElapsedTime) {
        this.actStartAndElapsedTime = actStartAndElapsedTime;
    }

    public String getCaneceltime() {
        return caneceltime;
    }

    public void setCaneceltime(String caneceltime) {
        this.caneceltime = caneceltime;
    }

    public Order()
    {

    }

    public Order(String orderid, String carnum, String proname, String owname, String owner_phone, String echname, String tech_phone, String remark, String areaname, String state_desc, String state, String createtime, String finishtime, String est_starttime, String est_tech_finishtime, String est_finishtime, String starttime, String tech_finishtime, String owner_store_key_time, String tech_take_key_time, String tech_store_key_time, String owner_take_key_time, String estStartAndElapsedtime, String actStartAndElapsedTime, String caneceltime) {
        this.orderid = orderid;
        this.carnum = carnum;
        this.proname = proname;
        this.owname = owname;
        this.owner_phone = owner_phone;
        this.echname = echname;
        this.tech_phone = tech_phone;
        this.remark = remark;
        this.areaname = areaname;
        this.state_desc = state_desc;
        this.state = state;
        this.createtime = createtime;
        this.finishtime = finishtime;
        this.est_starttime = est_starttime;
        this.est_tech_finishtime = est_tech_finishtime;
        this.est_finishtime = est_finishtime;
        this.starttime = starttime;
        this.tech_finishtime = tech_finishtime;
        this.owner_store_key_time = owner_store_key_time;
        this.tech_take_key_time = tech_take_key_time;
        this.tech_store_key_time = tech_store_key_time;
        this.owner_take_key_time = owner_take_key_time;
        this.estStartAndElapsedtime = estStartAndElapsedtime;
        this.actStartAndElapsedTime = actStartAndElapsedTime;
        this.caneceltime = caneceltime;
    }
}
