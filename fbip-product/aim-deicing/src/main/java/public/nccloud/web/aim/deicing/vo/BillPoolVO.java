package nccloud.web.aim.deicing.vo;

import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFDateTime;
import nc.vo.pub.lang.UFDouble;
import nc.vo.pubapp.pattern.model.meta.entity.vo.IVOMeta;
import nc.vo.pubapp.pattern.model.meta.entity.vo.VOMetaFactory;

public class BillPoolVO extends SuperVO {
    private static final long serialVersionUID = 1L;

    private String pk_bill_pool;
    private String pk_transport_bill;
    private String pk_recycling_pool;
    private String pool_code;
    private String pool_name;
    private UFDouble pool_volume;
    private UFDouble pool_concentration;
    private String trace_start_node;
    private String trace_end_node;
    private String remark;

    private String pk_group;
    private String pk_org;
    private String creator;
    private UFDateTime creationtime;
    private String modifier;
    private UFDateTime modifiedtime;
    private Integer dr = 0;
    private UFDateTime ts;

    public static final String PK_BILL_POOL = "pk_bill_pool";
    public static final String PK_TRANSPORT_BILL = "pk_transport_bill";
    public static final String PK_RECYCLING_POOL = "pk_recycling_pool";
    public static final String POOL_CODE = "pool_code";
    public static final String POOL_NAME = "pool_name";
    public static final String POOL_VOLUME = "pool_volume";
    public static final String POOL_CONCENTRATION = "pool_concentration";
    public static final String TRACE_START_NODE = "trace_start_node";
    public static final String TRACE_END_NODE = "trace_end_node";

    @Override
    public String getTableName() {
        return "aim_deicing_bill_pool";
    }

    @Override
    public String getPKFieldName() {
        return "pk_bill_pool";
    }

    @Override
    public String getParentPKFieldName() {
        return "pk_transport_bill";
    }

    @Override
    public IVOMeta getMetaData() {
        return VOMetaFactory.getInstance().getVOMeta("aim.deicingbillpool");
    }

    public String getPk_bill_pool() {
        return pk_bill_pool;
    }

    public void setPk_bill_pool(String pk_bill_pool) {
        this.pk_bill_pool = pk_bill_pool;
    }

    public String getPk_transport_bill() {
        return pk_transport_bill;
    }

    public void setPk_transport_bill(String pk_transport_bill) {
        this.pk_transport_bill = pk_transport_bill;
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

    public String getPool_name() {
        return pool_name;
    }

    public void setPool_name(String pool_name) {
        this.pool_name = pool_name;
    }

    public UFDouble getPool_volume() {
        return pool_volume;
    }

    public void setPool_volume(UFDouble pool_volume) {
        this.pool_volume = pool_volume;
    }

    public UFDouble getPool_concentration() {
        return pool_concentration;
    }

    public void setPool_concentration(UFDouble pool_concentration) {
        this.pool_concentration = pool_concentration;
    }

    public String getTrace_start_node() {
        return trace_start_node;
    }

    public void setTrace_start_node(String trace_start_node) {
        this.trace_start_node = trace_start_node;
    }

    public String getTrace_end_node() {
        return trace_end_node;
    }

    public void setTrace_end_node(String trace_end_node) {
        this.trace_end_node = trace_end_node;
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
