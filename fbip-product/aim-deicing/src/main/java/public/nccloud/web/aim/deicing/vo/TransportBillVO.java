package nccloud.web.aim.deicing.vo;

import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDateTime;
import nc.vo.pub.lang.UFDouble;
import nc.vo.pubapp.pattern.model.meta.entity.vo.IVOMeta;
import nc.vo.pubapp.pattern.model.meta.entity.vo.VOMetaFactory;

public class TransportBillVO extends SuperVO {
    private static final long serialVersionUID = 1L;

    private String pk_transport_bill;
    private String pk_deicing_record;
    private String bill_no;
    private String transport_unit;
    private String disposal_unit;
    private UFDate transport_date;
    private UFDouble transport_volume;
    private Integer transport_type;
    private Integer bill_status;
    private String confirm_person;
    private UFDateTime confirm_time;
    private String disposal_remark;
    private Integer is_disposed;
    private UFDateTime disposal_time;
    private String remark;

    private String pk_group;
    private String pk_org;
    private String creator;
    private UFDateTime creationtime;
    private String modifier;
    private UFDateTime modifiedtime;
    private Integer dr = 0;
    private UFDateTime ts;

    public static final String PK_TRANSPORT_BILL = "pk_transport_bill";
    public static final String PK_DEICING_RECORD = "pk_deicing_record";
    public static final String TRANSPORT_UNIT = "transport_unit";
    public static final String TRANSPORT_VOLUME = "transport_volume";
    public static final String BILL_STATUS = "bill_status";
    public static final String CONFIRM_TIME = "confirm_time";

    @Override
    public String getTableName() {
        return "aim_deicing_bill";
    }

    @Override
    public String getPKFieldName() {
        return "pk_transport_bill";
    }

    @Override
    public String getParentPKFieldName() {
        return "pk_deicing_record";
    }

    @Override
    public IVOMeta getMetaData() {
        return VOMetaFactory.getInstance().getVOMeta("aim.deicingbill");
    }

    public String getPk_transport_bill() {
        return pk_transport_bill;
    }

    public void setPk_transport_bill(String pk_transport_bill) {
        this.pk_transport_bill = pk_transport_bill;
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

    public String getTransport_unit() {
        return transport_unit;
    }

    public void setTransport_unit(String transport_unit) {
        this.transport_unit = transport_unit;
    }

    public String getDisposal_unit() {
        return disposal_unit;
    }

    public void setDisposal_unit(String disposal_unit) {
        this.disposal_unit = disposal_unit;
    }

    public UFDate getTransport_date() {
        return transport_date;
    }

    public void setTransport_date(UFDate transport_date) {
        this.transport_date = transport_date;
    }

    public UFDouble getTransport_volume() {
        return transport_volume;
    }

    public void setTransport_volume(UFDouble transport_volume) {
        this.transport_volume = transport_volume;
    }

    public Integer getTransport_type() {
        return transport_type;
    }

    public void setTransport_type(Integer transport_type) {
        this.transport_type = transport_type;
    }

    public Integer getBill_status() {
        return bill_status;
    }

    public void setBill_status(Integer bill_status) {
        this.bill_status = bill_status;
    }

    public String getConfirm_person() {
        return confirm_person;
    }

    public void setConfirm_person(String confirm_person) {
        this.confirm_person = confirm_person;
    }

    public UFDateTime getConfirm_time() {
        return confirm_time;
    }

    public void setConfirm_time(UFDateTime confirm_time) {
        this.confirm_time = confirm_time;
    }

    public String getDisposal_remark() {
        return disposal_remark;
    }

    public void setDisposal_remark(String disposal_remark) {
        this.disposal_remark = disposal_remark;
    }

    public Integer getIs_disposed() {
        return is_disposed;
    }

    public void setIs_disposed(Integer is_disposed) {
        this.is_disposed = is_disposed;
    }

    public UFDateTime getDisposal_time() {
        return disposal_time;
    }

    public void setDisposal_time(UFDateTime disposal_time) {
        this.disposal_time = disposal_time;
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
