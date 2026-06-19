package nccloud.web.aim.deicing.vo;

import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFDateTime;
import nc.vo.pub.lang.UFDouble;
import nc.vo.pubapp.pattern.model.meta.entity.vo.IVOMeta;
import nc.vo.pubapp.pattern.model.meta.entity.vo.VOMetaFactory;

public class ConcentrationTestVO extends SuperVO {
    private static final long serialVersionUID = 1L;

    private String pk_concentration_test;
    private String pk_deicing_record;
    private String test_no;
    private UFDateTime test_time;
    private String tester;
    private UFDouble concentration_value;
    private Integer is_over_limit;
    private String test_result;
    private String test_method;
    private String sample_point;
    private String remark;

    private String pk_group;
    private String pk_org;
    private String creator;
    private UFDateTime creationtime;
    private String modifier;
    private UFDateTime modifiedtime;
    private Integer dr = 0;
    private UFDateTime ts;

    public static final String PK_CONCENTRATION_TEST = "pk_concentration_test";
    public static final String PK_DEICING_RECORD = "pk_deicing_record";
    public static final String CONCENTRATION_VALUE = "concentration_value";
    public static final String IS_OVER_LIMIT = "is_over_limit";
    public static final String TEST_RESULT = "test_result";

    @Override
    public String getTableName() {
        return "aim_deicing_test";
    }

    @Override
    public String getPKFieldName() {
        return "pk_concentration_test";
    }

    @Override
    public String getParentPKFieldName() {
        return "pk_deicing_record";
    }

    @Override
    public IVOMeta getMetaData() {
        return VOMetaFactory.getInstance().getVOMeta("aim.deicingtest");
    }

    public String getPk_concentration_test() {
        return pk_concentration_test;
    }

    public void setPk_concentration_test(String pk_concentration_test) {
        this.pk_concentration_test = pk_concentration_test;
    }

    public String getPk_deicing_record() {
        return pk_deicing_record;
    }

    public void setPk_deicing_record(String pk_deicing_record) {
        this.pk_deicing_record = pk_deicing_record;
    }

    public String getTest_no() {
        return test_no;
    }

    public void setTest_no(String test_no) {
        this.test_no = test_no;
    }

    public UFDateTime getTest_time() {
        return test_time;
    }

    public void setTest_time(UFDateTime test_time) {
        this.test_time = test_time;
    }

    public String getTester() {
        return tester;
    }

    public void setTester(String tester) {
        this.tester = tester;
    }

    public UFDouble getConcentration_value() {
        return concentration_value;
    }

    public void setConcentration_value(UFDouble concentration_value) {
        this.concentration_value = concentration_value;
    }

    public Integer getIs_over_limit() {
        return is_over_limit;
    }

    public void setIs_over_limit(Integer is_over_limit) {
        this.is_over_limit = is_over_limit;
    }

    public String getTest_result() {
        return test_result;
    }

    public void setTest_result(String test_result) {
        this.test_result = test_result;
    }

    public String getTest_method() {
        return test_method;
    }

    public void setTest_method(String test_method) {
        this.test_method = test_method;
    }

    public String getSample_point() {
        return sample_point;
    }

    public void setSample_point(String sample_point) {
        this.sample_point = sample_point;
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
