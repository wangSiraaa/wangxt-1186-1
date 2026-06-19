package nccloud.web.aim.deicing.vo;

import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFDateTime;
import nc.vo.pub.lang.UFDouble;
import nc.md.model.MetaDataException;
import nc.vo.pubapp.pattern.model.meta.entity.vo.IVOMeta;
import nc.vo.pubapp.pattern.model.meta.entity.vo.VOMetaFactory;

public class RecyclingPoolVO extends SuperVO {
    private static final long serialVersionUID = 1L;

    private String pk_recycling_pool;
    private String pool_code;
    private String pool_name;
    private String location;
    private UFDouble design_capacity;
    private UFDouble current_stock;
    private Integer pool_status;
    private String remark;

    private String pk_group;
    private String pk_org;
    private String creator;
    private UFDateTime creationtime;
    private String modifier;
    private UFDateTime modifiedtime;
    private Integer dr = 0;
    private UFDateTime ts;

    public static final String PK_RECYCLING_POOL = "pk_recycling_pool";
    public static final String POOL_CODE = "pool_code";
    public static final String POOL_NAME = "pool_name";
    public static final String LOCATION = "location";
    public static final String DESIGN_CAPACITY = "design_capacity";
    public static final String CURRENT_STOCK = "current_stock";
    public static final String POOL_STATUS = "pool_status";

    @Override
    public String getTableName() {
        return "aim_deicing_pool";
    }

    @Override
    public String getPKFieldName() {
        return "pk_recycling_pool";
    }

    @Override
    public String getParentPKFieldName() {
        return null;
    }

    @Override
    public IVOMeta getMetaData() {
        return VOMetaFactory.getInstance().getVOMeta("aim.deicingpool");
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public UFDouble getDesign_capacity() {
        return design_capacity;
    }

    public void setDesign_capacity(UFDouble design_capacity) {
        this.design_capacity = design_capacity;
    }

    public UFDouble getCurrent_stock() {
        return current_stock;
    }

    public void setCurrent_stock(UFDouble current_stock) {
        this.current_stock = current_stock;
    }

    public Integer getPool_status() {
        return pool_status;
    }

    public void setPool_status(Integer pool_status) {
        this.pool_status = pool_status;
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
