package nccloud.web.aim.deicing.itf;

import nc.vo.pub.BusinessException;
import nccloud.web.aim.deicing.vo.AggDeicingRecordVO;
import nccloud.web.aim.deicing.vo.ConcentrationTestVO;
import nccloud.web.aim.deicing.vo.DeicingRecordVO;
import nccloud.web.aim.deicing.vo.RecyclingPoolVO;
import nccloud.web.aim.deicing.vo.TransportBillVO;
import nccloud.web.aim.deicing.vo.TransportTraceVO;

public interface IDeicingRecordService {

    AggDeicingRecordVO saveDeicingRecord(AggDeicingRecordVO aggVO) throws BusinessException;

    AggDeicingRecordVO updateDeicingRecord(AggDeicingRecordVO aggVO) throws BusinessException;

    void deleteDeicingRecord(String[] pks) throws BusinessException;

    AggDeicingRecordVO queryDeicingRecord(String pk) throws BusinessException;

    AggDeicingRecordVO[] queryDeicingRecords(String whereClause) throws BusinessException;

    ConcentrationTestVO saveConcentrationTest(ConcentrationTestVO testVO) throws BusinessException;

    TransportBillVO confirmTransportBill(TransportBillVO billVO) throws BusinessException;

    TransportTraceVO addTransportTrace(TransportTraceVO traceVO) throws BusinessException;

    void validateRecycledVolume(DeicingRecordVO recordVO) throws BusinessException;

    void validateConcentration(ConcentrationTestVO testVO) throws BusinessException;

    void validateBillConfirm(TransportBillVO billVO) throws BusinessException;

    RecyclingPoolVO updatePoolStock(String poolId, nc.vo.pub.lang.UFDouble volume) throws BusinessException;

    TransportTraceVO[] queryTransportTraces(String billId) throws BusinessException;

    RecyclingPoolVO[] queryRecyclingPools(String pkOrg) throws BusinessException;
}
