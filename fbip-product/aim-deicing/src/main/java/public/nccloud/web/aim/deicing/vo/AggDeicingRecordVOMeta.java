package nccloud.web.aim.deicing.vo;

import nc.vo.pubapp.pattern.model.meta.entity.bill.AbstractBillMeta;

public class AggDeicingRecordVOMeta extends AbstractBillMeta {
    public AggDeicingRecordVOMeta() {
        this.init();
    }

    private void init() {
        this.setParent(DeicingRecordVO.class);
        this.addChildren(ConcentrationTestVO.class);
        this.addChildren(TransportBillVO.class);
        this.addChildren(TransportTraceVO.class);
        this.addChildren(BillPoolVO.class);
    }
}
