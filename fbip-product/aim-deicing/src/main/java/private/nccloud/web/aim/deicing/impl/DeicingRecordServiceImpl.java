package nccloud.web.aim.deicing.impl;

import nc.bs.dao.BaseDAO;
import nc.bs.dao.DAOException;
import nc.jdbc.framework.SQLParameter;
import nc.jdbc.framework.processor.BeanListProcessor;
import nc.vo.am.common.util.CollectionUtils;
import nc.vo.am.common.util.StringUtils;
import nc.vo.pub.BusinessException;
import nc.vo.pub.VOStatus;
import nc.vo.pub.lang.UFDateTime;
import nc.vo.pub.lang.UFDouble;
import nc.vo.pubapp.pattern.model.entity.bill.AbstractBill;
import nccloud.framework.core.exception.ExceptionUtils;
import nccloud.util.SessionUtil;
import nccloud.web.aim.deicing.itf.IDeicingRecordService;
import nccloud.web.aim.deicing.vo.AggDeicingRecordVO;
import nccloud.web.aim.deicing.vo.BillPoolVO;
import nccloud.web.aim.deicing.vo.ConcentrationTestVO;
import nccloud.web.aim.deicing.vo.DeicingRecordVO;
import nccloud.web.aim.deicing.vo.RecyclingPoolVO;
import nccloud.web.aim.deicing.vo.TransportBillVO;
import nccloud.web.aim.deicing.vo.TransportTraceVO;

import java.util.Collection;

public class DeicingRecordServiceImpl implements IDeicingRecordService {

    private static final UFDouble CONCENTRATION_LIMIT = new UFDouble("500.00");
    private static final int TRANSPORT_TYPE_NORMAL = 1;
    private static final int TRANSPORT_TYPE_HAZARDOUS = 2;
    private static final int TRANSPORT_TYPE_RETREATMENT = 3;
    private static final int BILL_STATUS_CONFIRMED = 1;
    private static final int BILL_STATUS_TRANSPORTED = 2;
    private static final int BILL_STATUS_DISPOSED = 3;

    private static final int UNRECYCLED_TYPE_WEATHER = 1;
    private static final int UNRECYCLED_TYPE_APRON = 2;
    private static final int UNRECYCLED_TYPE_EQUIPMENT = 3;

    private BaseDAO dao;

    public DeicingRecordServiceImpl() {
        this.dao = new BaseDAO();
    }

    @Override
    public AggDeicingRecordVO saveDeicingRecord(AggDeicingRecordVO aggVO) throws BusinessException {
        validateRecycledVolume(aggVO.getParentVO());
        fillAuditFields(aggVO.getParentVO(), true);
        aggVO.getParentVO().setStatus(VOStatus.NEW);

        try {
            dao.insertVO(aggVO.getParentVO());

            if (aggVO.getChildrenVO() != null) {
                for (int i = 0; i < aggVO.getChildrenVO().length; i++) {
                    fillAuditFields(aggVO.getChildrenVO()[i], true);
                    aggVO.getChildrenVO()[i].setStatus(VOStatus.NEW);
                }
                dao.insertVOArray(aggVO.getChildrenVO());
            }

            updatePoolStock(aggVO.getParentVO().getPk_recycling_pool(),
                    aggVO.getParentVO().getRecycled_volume());

        } catch (DAOException e) {
            ExceptionUtils.wrapBusinessException("保存除冰记录失败");
        }
        return aggVO;
    }

    @Override
    public AggDeicingRecordVO updateDeicingRecord(AggDeicingRecordVO aggVO) throws BusinessException {
        DeicingRecordVO recordVO = aggVO.getParentVO();

        checkBillConfirmed(recordVO.getPk_deicing_record());

        validateRecycledVolume(recordVO);

        fillAuditFields(recordVO, false);
        recordVO.setStatus(VOStatus.UPDATED);

        try {
            dao.updateVO(recordVO);
        } catch (DAOException e) {
            ExceptionUtils.wrapBusinessException("更新除冰记录失败");
        }
        return aggVO;
    }

    @Override
    public void deleteDeicingRecord(String[] pks) throws BusinessException {
        if (pks == null || pks.length == 0) {
            return;
        }

        try {
            for (String pk : pks) {
                DeicingRecordVO recordVO = (DeicingRecordVO) dao.retrieveByPK(DeicingRecordVO.class, pk);
                if (recordVO != null) {
                    checkBillConfirmed(pk);
                    recordVO.setDr(1);
                    fillAuditFields(recordVO, false);
                    dao.updateVO(recordVO);
                }
            }
        } catch (DAOException e) {
            ExceptionUtils.wrapBusinessException("删除除冰记录失败");
        }
    }

    @Override
    public AggDeicingRecordVO queryDeicingRecord(String pk) throws BusinessException {
        if (StringUtils.isEmpty(pk)) {
            return null;
        }

        try {
            DeicingRecordVO parentVO = (DeicingRecordVO) dao.retrieveByPK(DeicingRecordVO.class, pk);
            if (parentVO == null) {
                return null;
            }

            AggDeicingRecordVO aggVO = new AggDeicingRecordVO();
            aggVO.setParent(parentVO);

            String condition = "pk_deicing_record = ? and dr = 0";
            SQLParameter param = new SQLParameter();
            param.addParam(pk);

            Collection<ConcentrationTestVO> tests = dao.retrieveByClause(ConcentrationTestVO.class, condition, param);
            Collection<TransportBillVO> bills = dao.retrieveByClause(TransportBillVO.class, condition, param);
            Collection<TransportTraceVO> traces = dao.retrieveByClause(TransportTraceVO.class, condition, param);
            Collection<BillPoolVO> billPools = new java.util.ArrayList<BillPoolVO>();

            if (bills != null && !bills.isEmpty()) {
                for (TransportBillVO bill : bills) {
                    BillPoolVO[] pools = queryBillPools(bill.getPk_transport_bill());
                    if (pools != null && pools.length > 0) {
                        for (BillPoolVO pool : pools) {
                            billPools.add(pool);
                        }
                    }
                }
            }

            int totalLen = (tests != null ? tests.size() : 0)
                    + (bills != null ? bills.size() : 0)
                    + (traces != null ? traces.size() : 0)
                    + billPools.size();

            if (totalLen > 0) {
                nc.vo.pub.SuperVO[] children = new nc.vo.pub.SuperVO[totalLen];
                int idx = 0;
                if (tests != null) {
                    for (ConcentrationTestVO vo : tests) {
                        children[idx++] = vo;
                    }
                }
                if (bills != null) {
                    for (TransportBillVO vo : bills) {
                        children[idx++] = vo;
                    }
                }
                if (traces != null) {
                    for (TransportTraceVO vo : traces) {
                        children[idx++] = vo;
                    }
                }
                for (BillPoolVO vo : billPools) {
                    children[idx++] = vo;
                }
                aggVO.setChildren(children);
            }

            return aggVO;
        } catch (DAOException e) {
            ExceptionUtils.wrapBusinessException("查询除冰记录失败");
        }
        return null;
    }

    @Override
    public AggDeicingRecordVO[] queryDeicingRecords(String whereClause) throws BusinessException {
        try {
            String condition = "dr = 0";
            if (StringUtils.isNotEmpty(whereClause)) {
                condition += " and " + whereClause;
            }
            condition += " order by creationtime desc";

            Collection<DeicingRecordVO> records = dao.retrieveByClause(DeicingRecordVO.class, condition);
            if (CollectionUtils.isEmpty(records)) {
                return new AggDeicingRecordVO[0];
            }

            AggDeicingRecordVO[] result = new AggDeicingRecordVO[records.size()];
            int idx = 0;
            for (DeicingRecordVO record : records) {
                AggDeicingRecordVO aggVO = new AggDeicingRecordVO();
                aggVO.setParent(record);
                result[idx++] = aggVO;
            }
            return result;
        } catch (DAOException e) {
            ExceptionUtils.wrapBusinessException("查询除冰记录列表失败");
        }
        return new AggDeicingRecordVO[0];
    }

    @Override
    public ConcentrationTestVO saveConcentrationTest(ConcentrationTestVO testVO) throws BusinessException {
        validateConcentration(testVO);
        fillAuditFields(testVO, true);
        testVO.setStatus(VOStatus.NEW);

        try {
            dao.insertVO(testVO);

            DeicingRecordVO recordVO = (DeicingRecordVO) dao.retrieveByPK(
                    DeicingRecordVO.class, testVO.getPk_deicing_record());
            if (recordVO != null && StringUtils.isNotEmpty(recordVO.getPk_recycling_pool())) {
                updatePoolMixedConcentration(recordVO.getPk_recycling_pool());
            }

            if (testVO.getIs_over_limit() != null && testVO.getIs_over_limit() == 1) {
                recordVO.setRecord_status(2);
            } else {
                recordVO.setRecord_status(1);
            }
            recordVO.setStatus(VOStatus.UPDATED);
            fillAuditFields(recordVO, false);
            dao.updateVO(recordVO);

        } catch (DAOException e) {
            ExceptionUtils.wrapBusinessException("保存浓度检测记录失败");
        }
        return testVO;
    }

    private void updatePoolMixedConcentration(String poolId) throws BusinessException {
        if (StringUtils.isEmpty(poolId)) {
            return;
        }
        try {
            String sql = "select dr, pk_deicing_record, pk_recycling_pool from aim_deicing_record " +
                    "where pk_recycling_pool = ? and dr = 0 and is_recycled = 1";
            SQLParameter param = new SQLParameter();
            param.addParam(poolId);
            Collection<DeicingRecordVO> records = dao.retrieveByClause(
                    DeicingRecordVO.class, "pk_recycling_pool = ? and dr = 0 and is_recycled = 1", param);

            if (CollectionUtils.isEmpty(records)) {
                updatePoolConcentration(poolId, UFDouble.ZERO_DBL);
                return;
            }

            UFDouble totalVolume = UFDouble.ZERO_DBL;
            UFDouble totalMass = UFDouble.ZERO_DBL;

            for (DeicingRecordVO record : records) {
                ConcentrationTestVO latestTest = getLatestConcentrationTest(record.getPk_deicing_record());
                if (latestTest != null && latestTest.getConcentration_value() != null
                        && record.getRecycled_volume() != null) {
                    totalVolume = totalVolume.add(record.getRecycled_volume());
                    totalMass = totalMass.add(
                            record.getRecycled_volume().multiply(latestTest.getConcentration_value()));
                }
            }

            UFDouble mixedConcentration = UFDouble.ZERO_DBL;
            if (totalVolume.compareTo(UFDouble.ZERO_DBL) > 0) {
                mixedConcentration = totalMass.div(totalVolume);
            }

            updatePoolConcentration(poolId, mixedConcentration);

        } catch (DAOException e) {
            ExceptionUtils.wrapBusinessException("更新回收池混合浓度失败");
        }
    }

    @Override
    public RecyclingPoolVO updatePoolConcentration(String poolId, UFDouble concentration) throws BusinessException {
        if (StringUtils.isEmpty(poolId) || concentration == null) {
            return null;
        }

        try {
            RecyclingPoolVO poolVO = (RecyclingPoolVO) dao.retrieveByPK(RecyclingPoolVO.class, poolId);
            if (poolVO == null) {
                return null;
            }

            poolVO.setMixed_concentration(concentration);
            fillAuditFields(poolVO, false);
            poolVO.setStatus(VOStatus.UPDATED);

            dao.updateVO(poolVO);
            return poolVO;
        } catch (DAOException e) {
            ExceptionUtils.wrapBusinessException("更新回收池浓度失败");
        }
        return null;
    }

    @Override
    public TransportBillVO saveTransportBill(TransportBillVO billVO, BillPoolVO[] poolVOs) throws BusinessException {
        validateTransportBillSave(billVO);

        if (poolVOs != null && poolVOs.length > 0) {
            validateBillPools(poolVOs);
        }

        validateTransportTypeByConcentration(billVO.getPk_deicing_record(), billVO.getTransport_type());

        billVO.setBill_status(0);
        billVO.setIs_disposed(0);
        fillAuditFields(billVO, true);
        billVO.setStatus(VOStatus.NEW);

        try {
            dao.insertVO(billVO);

            if (poolVOs != null && poolVOs.length > 0) {
                for (BillPoolVO poolVO : poolVOs) {
                    poolVO.setPk_transport_bill(billVO.getPk_transport_bill());
                    fillAuditFields(poolVO, true);
                    poolVO.setStatus(VOStatus.NEW);
                    dao.insertVO(poolVO);
                }
            }

            addTransportTrace(billVO.getPk_deicing_record(), billVO.getPk_transport_bill(),
                    "创建联单", "外运单位创建转运联单，状态为待确认");

        } catch (DAOException e) {
            ExceptionUtils.wrapBusinessException("保存外运联单失败");
        }
        return billVO;
    }

    private void validateBillPools(BillPoolVO[] poolVOs) throws BusinessException {
        if (poolVOs == null || poolVOs.length == 0) {
            return;
        }
        for (BillPoolVO poolVO : poolVOs) {
            if (StringUtils.isEmpty(poolVO.getPk_recycling_pool())) {
                ExceptionUtils.wrapBusinessException("来源池主键不能为空");
            }
            if (poolVO.getPool_volume() == null
                    || poolVO.getPool_volume().compareTo(UFDouble.ZERO_DBL) <= 0) {
                ExceptionUtils.wrapBusinessException("来源池转运量必须大于0");
            }
        }
    }

    @Override
    public TransportBillVO confirmTransportBill(TransportBillVO billVO) throws BusinessException {
        validateBillConfirm(billVO);

        TransportBillVO oldBill = (TransportBillVO) dao.retrieveByPK(
                TransportBillVO.class, billVO.getPk_transport_bill());
        if (oldBill != null && oldBill.getBill_status() != null
                && oldBill.getBill_status() >= BILL_STATUS_CONFIRMED) {
            ExceptionUtils.wrapBusinessException("联单已确认，不可重复确认");
        }

        validateTransportTypeByConcentration(billVO.getPk_deicing_record(), billVO.getTransport_type());

        billVO.setBill_status(BILL_STATUS_CONFIRMED);
        billVO.setConfirm_person(SessionUtil.getUserId());
        billVO.setConfirm_time(new UFDateTime());
        fillAuditFields(billVO, false);
        billVO.setStatus(VOStatus.UPDATED);

        try {
            dao.updateVO(billVO);

            lockRecycledVolume(billVO.getPk_deicing_record());

            addTransportTrace(billVO.getPk_deicing_record(), billVO.getPk_transport_bill(),
                    "联单确认", "外运单位已确认转运联单，回收量已锁定");

        } catch (DAOException e) {
            ExceptionUtils.wrapBusinessException("确认外运联单失败");
        }
        return billVO;
    }

    private void lockRecycledVolume(String recordId) throws BusinessException {
        if (StringUtils.isEmpty(recordId)) {
            return;
        }
        try {
            DeicingRecordVO recordVO = (DeicingRecordVO) dao.retrieveByPK(
                    DeicingRecordVO.class, recordId);
            if (recordVO != null) {
                recordVO.setRecord_status(3);
                recordVO.setStatus(VOStatus.UPDATED);
                fillAuditFields(recordVO, false);
                dao.updateVO(recordVO);
            }
        } catch (DAOException e) {
            ExceptionUtils.wrapBusinessException("锁定回收量失败");
        }
    }

    @Override
    public void validateTransportTypeByConcentration(String recordId, Integer transportType) throws BusinessException {
        if (StringUtils.isEmpty(recordId) || transportType == null) {
            return;
        }

        ConcentrationTestVO testVO = getLatestConcentrationTest(recordId);
        if (testVO != null && testVO.getIs_over_limit() != null && testVO.getIs_over_limit() == 1) {
            if (transportType == TRANSPORT_TYPE_NORMAL) {
                ExceptionUtils.wrapBusinessException(
                        "浓度超限，不能按普通废水外运，请选择危废处置或再处理方式");
            }
            if (transportType != TRANSPORT_TYPE_HAZARDOUS
                    && transportType != TRANSPORT_TYPE_RETREATMENT) {
                ExceptionUtils.wrapBusinessException(
                        "浓度超限，只能选择危废处置或再处理方式");
            }
        }
    }

    @Override
    public TransportTraceVO addTransportTrace(TransportTraceVO traceVO) throws BusinessException {
        fillAuditFields(traceVO, true);
        traceVO.setStatus(VOStatus.NEW);

        try {
            dao.insertVO(traceVO);
        } catch (DAOException e) {
            ExceptionUtils.wrapBusinessException("添加外运轨迹失败");
        }
        return traceVO;
    }

    @Override
    public void validateRecycledVolume(DeicingRecordVO recordVO) throws BusinessException {
        if (recordVO.getIs_recycled() == null) {
            ExceptionUtils.wrapBusinessException("请选择是否回收");
        }

        if (recordVO.getIs_recycled() == 0) {
            validateUnrecycledType(recordVO);
            if (StringUtils.isEmpty(recordVO.getUnrecycled_reason())) {
                ExceptionUtils.wrapBusinessException("未回收的架次必须填写原因说明");
            }
            recordVO.setRecycled_volume(UFDouble.ZERO_DBL);
        } else {
            if (recordVO.getRecycled_volume() == null
                    || recordVO.getRecycled_volume().compareTo(UFDouble.ZERO_DBL) <= 0) {
                ExceptionUtils.wrapBusinessException("回收量必须大于0");
            }
            if (StringUtils.isEmpty(recordVO.getPk_recycling_pool())) {
                ExceptionUtils.wrapBusinessException("请选择回收池");
            }
        }
    }

    @Override
    public void validateUnrecycledType(DeicingRecordVO recordVO) throws BusinessException {
        if (recordVO.getUnrecycled_type() == null) {
            ExceptionUtils.wrapBusinessException("请选择未回收原因分类");
        }
        int type = recordVO.getUnrecycled_type();
        if (type != UNRECYCLED_TYPE_WEATHER
                && type != UNRECYCLED_TYPE_APRON
                && type != UNRECYCLED_TYPE_EQUIPMENT) {
            ExceptionUtils.wrapBusinessException("未回收原因分类不正确，只能选择天气、机位或设备故障");
        }
    }

    @Override
    public void validateConcentration(ConcentrationTestVO testVO) throws BusinessException {
        if (testVO.getConcentration_value() == null) {
            ExceptionUtils.wrapBusinessException("请输入浓度检测值");
        }

        if (testVO.getConcentration_value().compareTo(CONCENTRATION_LIMIT) > 0) {
            testVO.setIs_over_limit(1);
            testVO.setTest_result("浓度超限，需按危废处置");
        } else {
            testVO.setIs_over_limit(0);
            testVO.setTest_result("浓度合格，可按普通废水处置");
        }

        if (testVO.getTester() == null) {
            testVO.setTester(SessionUtil.getUserId());
        }
        if (testVO.getTest_time() == null) {
            testVO.setTest_time(new UFDateTime());
        }
    }

    public void validateTransportBillSave(TransportBillVO billVO) throws BusinessException {
        if (StringUtils.isEmpty(billVO.getPk_deicing_record())) {
            ExceptionUtils.wrapBusinessException("除冰记录主键不能为空");
        }
        if (StringUtils.isEmpty(billVO.getTransport_unit())) {
            ExceptionUtils.wrapBusinessException("请填写转运单位");
        }
        if (billVO.getTransport_date() == null) {
            ExceptionUtils.wrapBusinessException("请选择转运日期");
        }
        if (billVO.getTransport_volume() == null
                || billVO.getTransport_volume().compareTo(UFDouble.ZERO_DBL) <= 0) {
            ExceptionUtils.wrapBusinessException("转运量必须大于0");
        }
        if (billVO.getTransport_type() == null) {
            ExceptionUtils.wrapBusinessException("请选择外运类型");
        }
    }

    @Override
    public void validateBillConfirm(TransportBillVO billVO) throws BusinessException {
        if (StringUtils.isEmpty(billVO.getTransport_unit())) {
            ExceptionUtils.wrapBusinessException("请填写转运单位");
        }
        if (billVO.getTransport_volume() == null
                || billVO.getTransport_volume().compareTo(UFDouble.ZERO_DBL) <= 0) {
            ExceptionUtils.wrapBusinessException("转运量必须大于0");
        }
        if (billVO.getTransport_type() == null) {
            ExceptionUtils.wrapBusinessException("请选择外运类型");
        }
    }

    @Override
    public RecyclingPoolVO updatePoolStock(String poolId, UFDouble volume) throws BusinessException {
        if (StringUtils.isEmpty(poolId) || volume == null || volume.compareTo(UFDouble.ZERO_DBL) <= 0) {
            return null;
        }

        try {
            RecyclingPoolVO poolVO = (RecyclingPoolVO) dao.retrieveByPK(RecyclingPoolVO.class, poolId);
            if (poolVO == null) {
                return null;
            }

            UFDouble currentStock = poolVO.getCurrent_stock() != null
                    ? poolVO.getCurrent_stock() : UFDouble.ZERO_DBL;
            poolVO.setCurrent_stock(currentStock.add(volume));
            fillAuditFields(poolVO, false);
            poolVO.setStatus(VOStatus.UPDATED);

            dao.updateVO(poolVO);
            return poolVO;
        } catch (DAOException e) {
            ExceptionUtils.wrapBusinessException("更新回收池库存失败");
        }
        return null;
    }

    @Override
    public TransportTraceVO[] queryTransportTraces(String billId) throws BusinessException {
        if (StringUtils.isEmpty(billId)) {
            return new TransportTraceVO[0];
        }

        try {
            String condition = "pk_transport_bill = ? and dr = 0 order by trace_order asc, operate_time asc";
            SQLParameter param = new SQLParameter();
            param.addParam(billId);

            Collection<TransportTraceVO> traces = dao.retrieveByClause(TransportTraceVO.class, condition, param);
            if (CollectionUtils.isEmpty(traces)) {
                return new TransportTraceVO[0];
            }
            return traces.toArray(new TransportTraceVO[0]);
        } catch (DAOException e) {
            ExceptionUtils.wrapBusinessException("查询外运轨迹失败");
        }
        return new TransportTraceVO[0];
    }

    private TransportTraceVO addTransportTrace(String recordId, String billId,
                                               String traceNode, String remark) throws BusinessException {
        TransportTraceVO traceVO = new TransportTraceVO();
        traceVO.setPk_deicing_record(recordId);
        traceVO.setPk_transport_bill(billId);
        traceVO.setTrace_node(traceNode);
        traceVO.setTrace_order(getNextTraceOrder(billId));
        traceVO.setOperate_time(new UFDateTime());
        traceVO.setOperator(SessionUtil.getUserId());
        traceVO.setTrace_status("正常");
        traceVO.setTrace_remark(remark);
        return addTransportTrace(traceVO);
    }

    private int getNextTraceOrder(String billId) {
        try {
            String sql = "select max(trace_order) from aim_deicing_trace where pk_transport_bill = ? and dr = 0";
            SQLParameter param = new SQLParameter();
            param.addParam(billId);
            Object result = dao.executeQuery(sql, param, new nc.jdbc.framework.processor.ColumnProcessor());
            if (result != null) {
                return ((Number) result).intValue() + 1;
            }
        } catch (Exception e) {
        }
        return 1;
    }

    private ConcentrationTestVO getLatestConcentrationTest(String recordId) {
        try {
            String condition = "pk_deicing_record = ? and dr = 0 order by test_time desc";
            SQLParameter param = new SQLParameter();
            param.addParam(recordId);

            Collection<ConcentrationTestVO> tests = dao.retrieveByClause(ConcentrationTestVO.class, condition, param);
            if (CollectionUtils.isNotEmpty(tests)) {
                return tests.iterator().next();
            }
        } catch (DAOException e) {
        }
        return null;
    }

    @Override
    public RecyclingPoolVO[] queryRecyclingPools(String pkOrg) throws BusinessException {
        try {
            String condition = "dr = 0";
            SQLParameter param = new SQLParameter();
            if (StringUtils.isNotEmpty(pkOrg)) {
                condition += " and pk_org = ?";
                param.addParam(pkOrg);
            }
            condition += " order by pool_code";

            Collection<RecyclingPoolVO> pools = dao.retrieveByClause(RecyclingPoolVO.class, condition, param);
            if (CollectionUtils.isEmpty(pools)) {
                return new RecyclingPoolVO[0];
            }
            return pools.toArray(new RecyclingPoolVO[0]);
        } catch (DAOException e) {
            ExceptionUtils.wrapBusinessException("查询回收池列表失败");
        }
        return new RecyclingPoolVO[0];
    }

    @Override
    public BillPoolVO[] queryBillPools(String billId) throws BusinessException {
        if (StringUtils.isEmpty(billId)) {
            return new BillPoolVO[0];
        }

        try {
            String condition = "pk_transport_bill = ? and dr = 0 order by creationtime asc";
            SQLParameter param = new SQLParameter();
            param.addParam(billId);

            Collection<BillPoolVO> pools = dao.retrieveByClause(BillPoolVO.class, condition, param);
            if (CollectionUtils.isEmpty(pools)) {
                return new BillPoolVO[0];
            }
            return pools.toArray(new BillPoolVO[0]);
        } catch (DAOException e) {
            ExceptionUtils.wrapBusinessException("查询联单来源池明细失败");
        }
        return new BillPoolVO[0];
    }

    private void checkBillConfirmed(String recordId) throws BusinessException {
        try {
            String condition = "pk_deicing_record = ? and bill_status >= ? and dr = 0";
            SQLParameter param = new SQLParameter();
            param.addParam(recordId);
            param.addParam(BILL_STATUS_CONFIRMED);

            Collection<TransportBillVO> bills = dao.retrieveByClause(TransportBillVO.class, condition, param);
            if (CollectionUtils.isNotEmpty(bills)) {
                ExceptionUtils.wrapBusinessException("外运联单已确认，回收量不可修改");
            }
        } catch (DAOException e) {
            ExceptionUtils.wrapBusinessException("校验联单状态失败");
        }
    }

    private void fillAuditFields(nc.vo.pub.SuperVO vo, boolean isNew) {
        String userId = SessionUtil.getUserId();
        String groupId = SessionUtil.getGroupId();
        String orgId = SessionUtil.getOrgId();
        UFDateTime now = new UFDateTime();

        vo.setAttributeValue("pk_group", groupId);
        vo.setAttributeValue("pk_org", orgId);

        if (isNew) {
            vo.setAttributeValue("creator", userId);
            vo.setAttributeValue("creationtime", now);
        }
        vo.setAttributeValue("modifier", userId);
        vo.setAttributeValue("modifiedtime", now);
    }
}
