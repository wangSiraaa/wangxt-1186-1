package nccloud.web.aim.deicing.vo;

import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFDateTime;
import nc.vo.pubapp.pattern.model.meta.entity.vo.IVOMeta;
import nc.vo.pubapp.pattern.model.meta.entity.vo.VOMetaFactory;

public class TransportTraceVO extends SuperVO {
    private static final long serialVersionUID = 1L;

    private String pk_transport_trace;
    private String pk_transport_bill;
    private String pk_deicing_record;
    private String trace_node;
    private Integer trace_order;
    private UFDateTime operate_time;
    private String operator;
    private String trace_status;
    private String trace_remark;
    private String location;

    private String pk_group;
    private String pk_org;
    private String creator;
    private UFDateTime creationtime;
    private String modifier;
    private UFDateTime modifiedtime;
    private Integer dr = 0;
    private UFDateTime ts;

    public static final String PK_TRANSPORT_TRACE = "pk_transport_trace";
    public static final String PK_TRANSPORT_BILL = "pk_transport_bill";
    public static final String TRACE_NODE = "trace_node";
    public static final String OPERATE_TIME = "operate_time";
    public static final String TRACE_STATUS = "trace_status";

    @Override
    public String getTableName() {
        return "aim_deicing_trace";
    }

    @Override
    public String getPKFieldName() {
        return "pk_transport_trace";
    }

    @Override
    public String getParentPKFieldName() {
        return "pk_transport_bill";
    }

    @Override
    public IVOMeta getMetaData() {
        return VOMetaFactory.getInstance().getVOMeta("aim.deicingtrace");
    }

    public String getPk_transport_trace() {
        return pk_transport_trace;
    }

    public void setPk_transport_trace(String pk_transport_trace) {
        this.pk_transport_trace = pk_transport_trace;
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

    public String getTrace_node() {
        return trace_node;
    }

    public void setTrace_node(String trace_node) {
        this.trace_node = trace_node;
    }

    public Integer getTrace_order() {
        return trace_order;
    }

    public void setTrace_order(Integer trace_order) {
        this.trace_order = trace_order;
    }

    public UFDateTime getOperate_time() {
        return operate_time;
    }

    public void setOperate_time(UFDateTime operate_time) {
        this.operate_time = operate_time;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getTrace_status() {
        return trace_status;
    }

    public void setTrace_status(String trace_status) {
        this.trace_status = trace_status;
    }

    public String getTrace_remark() {
        return trace_remark;
    }

    public void setTrace_remark(String trace_remark) {
        this.trace_remark = trace_remark;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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
