package nccloud.web.aim.deicing.vo;

import nc.vo.pubapp.pattern.model.entity.bill.AbstractBill;
import nc.vo.pubapp.pattern.model.meta.entity.bill.BillMetaFactory;
import nc.vo.pubapp.pattern.model.meta.entity.bill.IBillMeta;

@nc.vo.annotation.AggVoInfo(parentVO = "nccloud.web.aim.deicing.vo.DeicingRecordVO")
public class AggDeicingRecordVO extends AbstractBill {
    private static final long serialVersionUID = 1L;

    @Override
    public IBillMeta getMetaData() {
        return BillMetaFactory.getInstance().getBillMeta(AggDeicingRecordVOMeta.class);
    }

    @Override
    public DeicingRecordVO getParentVO() {
        return (DeicingRecordVO) this.getParent();
    }
}
