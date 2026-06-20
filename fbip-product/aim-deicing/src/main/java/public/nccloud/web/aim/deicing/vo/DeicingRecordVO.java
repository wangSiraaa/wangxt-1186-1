package nccloud.web.aim.deicing.vo;

import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDateTime;
import nc.vo.pub.lang.UFDouble;
import nc.vo.pubapp.pattern.model.meta.entity.vo.IVOMeta;
import nc.vo.pubapp.pattern.model.meta.entity.vo.VOMetaFactory;

public class DeicingRecordVO extends SuperVO {
    private static final long serialVersionUID = 1L;

    private String pk_deicing_record;
    private String bill_no;
    private Integer bill_status;
    private UFDate deicing_date;
    private String flight_no;
    private String flight_sortie;
    private String aircraft_type;
    private String aircraft_reg_no;
    private String pk_apron;
    private String apron_operator;
    private UFDateTime deicing_start_time;
    private UFDateTime deicing_end_time;
    private UFDouble deicing_fluid_usage;
    private Integer is_recycled;
    private UFDouble recycled_volume;
    private Integer unrecycled_type;
    private String unrecycled_reason;
    private String pk_recycling_pool;
    private String pool_code;
    private Integer record_status;
    private String remark;

    private String pk_group;
    private String pk_org;
    private String creator;
    private UFDateTime creationtime;
    private String modifier;
    private UFDateTime modifiedtime;
    private Integer dr = 0;
    private UFDateTime ts;

    public static final String PK_DEICING_RECORD = "pk_deicing_record";
    public static final String BILL_NO = "bill_no";
    public static final String BILL_STATUS = "bill_status";
    public static final String FLIGHT_NO = "flight_no";
    public static final String FLIGHT_SORTIE = "flight_sortie";
    public static final String IS_RECYCLED = "is_recycled";
    public static final String RECYCLED_VOLUME = "recycled_volume";
    public static final String UNRECYCLED_TYPE = "unrecycled_type";
    public static final String UNRECYCLED_REASON = "unrecycled_reason";
    public static final String PK_RECYCLING_POOL = "pk_recycling_pool";
    public static final String RECORD_STATUS = "record_status";
    
    public static final int UNRECYCLED_TYPE_WEATHER = 1;
    public static final int UNRECYCLED_TYPE_APRON = 2;
    public static final int UNRECYCLED_TYPE_EQUIPMENT = 3;

    @Override
    public String getTableName() {
        return "aim_deicing_record";
    }

    @Override
    public String getPKFieldName() {
        return "pk_deicing_record";
    }

    @Override
    public String getParentPKFieldName() {
        return null;
    }

    @Override
    public IVOMeta getMetaData() {
        return VOMetaFactory.getInstance().getVOMeta("aim.deicingrecord");
    }

    public String getPk_deicing_record() {
        return pk_deicing_record;
    }

    public void setPk_deicing_record(String pk_deicing_record) {
        this.pk_deicing_record = pk_deicing_record;
    }

    public String getBill_no() {
        return bill_no;
    }

    public void setBill_no(String bill_no) {
        this.bill_no = bill_no;
    }

    public Integer getBill_status() {
        return bill_status;
    }

    public void setBill_status(Integer bill_status) {
        this.bill_status = bill_status;
    }

    public UFDate getDeicing_date() {
        return deicing_date;
    }

    public void setDeicing_date(UFDate deicing_date) {
        this.deicing_date = deicing_date;
    }

    public String getFlight_no() {
        return flight_no;
    }

    public void setFlight_no(String flight_no) {
        this.flight_no = flight_no;
    }

    public String getFlight_sortie() {
        return flight_sortie;
    }

    public void setFlight_sortie(String flight_sortie) {
        this.flight_sortie = flight_sortie;
    }

    public String getAircraft_type() {
        return aircraft_type;
    }

    public void setAircraft_type(String aircraft_type) {
        this.aircraft_type = aircraft_type;
    }

    public String getAircraft_reg_no() {
        return aircraft_reg_no;
    }

    public void setAircraft_reg_no(String aircraft_reg_no) {
        this.aircraft_reg_no = aircraft_reg_no;
    }

    public String getPk_apron() {
        return pk_apron;
    }

    public void setPk_apron(String pk_apron) {
        this.pk_apron = pk_apron;
    }

    public String getApron_operator() {
        return apron_operator;
    }

    public void setApron_operator(String apron_operator) {
        this.apron_operator = apron_operator;
    }

    public UFDateTime getDeicing_start_time() {
        return deicing_start_time;
    }

    public void setDeicing_start_time(UFDateTime deicing_start_time) {
        this.deicing_start_time = deicing_start_time;
    }

    public UFDateTime getDeicing_end_time() {
        return deicing_end_time;
    }

    public void setDeicing_end_time(UFDateTime deicing_end_time) {
        this.deicing_end_time = deicing_end_time;
    }

    public UFDouble getDeicing_fluid_usage() {
        return deicing_fluid_usage;
    }

    public void setDeicing_fluid_usage(UFDouble deicing_fluid_usage) {
        this.deicing_fluid_usage = deicing_fluid_usage;
    }

    public Integer getIs_recycled() {
        return is_recycled;
    }

    public void setIs_recycled(Integer is_recycled) {
        this.is_recycled = is_recycled;
    }

    public UFDouble getRecycled_volume() {
        return recycled_volume;
    }

    public void setRecycled_volume(UFDouble recycled_volume) {
        this.recycled_volume = recycled_volume;
    }

    public Integer getUnrecycled_type() {
        return unrecycled_type;
    }

    public void setUnrecycled_type(Integer unrecycled_type) {
        this.unrecycled_type = unrecycled_type;
    }

    public String getUnrecycled_reason() {
        return unrecycled_reason;
    }

    public void setUnrecycled_reason(String unrecycled_reason) {
        this.unrecycled_reason = unrecycled_reason;
    }

    public String getPk_recycling_pool() {
        return pk_recycling_pool;
    }

    public void setPk_recycling_pool(String pk_recycling_pool) {
        this.pk_recycling_pool = pk_recycling_pool;
    }

    public String getPool_code() {
        return pool_code;
    }

    public void setPool_code(String pool_code) {
        this.pool_code = pool_code;
    }

    public Integer getRecord_status() {
        return record_status;
    }

    public void setRecord_status(Integer record_status) {
        this.record_status = record_status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPk_group() {
        return pk_group;
    }

    public void setPk_group(String pk_group) {
        this.pk_group = pk_group;
    }

    public String getPk_org() {
        return pk_org;
    }

    public void setPk_org(String pk_org) {
        this.pk_org = pk_org;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public UFDateTime getCreationtime() {
        return creationtime;
    }

    public void setCreationtime(UFDateTime creationtime) {
        this.creationtime = creationtime;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public UFDateTime getModifiedtime() {
        return modifiedtime;
    }

    public void setModifiedtime(UFDateTime modifiedtime) {
        this.modifiedtime = modifiedtime;
    }

    public Integer getDr() {
        return dr;
    }

    public void setDr(Integer dr) {
        this.dr = dr;
    }

    public UFDateTime getTs() {
        return ts;
    }

    public void setTs(UFDateTime ts) {
        this.ts = ts;
    }
}
