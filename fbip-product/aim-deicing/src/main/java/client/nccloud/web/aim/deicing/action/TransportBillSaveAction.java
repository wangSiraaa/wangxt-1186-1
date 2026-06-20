package nccloud.web.aim.deicing.action;

import nccloud.framework.core.exception.ExceptionUtils;
import nccloud.framework.core.json.IJson;
import nccloud.framework.service.ServiceLocator;
import nccloud.framework.web.action.itf.ICommonAction;
import nccloud.framework.web.container.IRequest;
import nccloud.framework.web.json.JsonFactory;
import nccloud.web.aim.deicing.itf.IDeicingRecordService;
import nccloud.web.aim.deicing.vo.TransportBillVO;

import java.util.Map;

public class TransportBillSaveAction implements ICommonAction {

    @Override
    public Object doAction(IRequest request) {
        try {
            IJson json = JsonFactory.create();
            @SuppressWarnings("unchecked")
            Map<String, Object> map = json.fromJson(request.read(), Map.class);

            String billData = map != null ? (String) map.get("billData") : null;
            if (billData == null || billData.isEmpty()) {
                ExceptionUtils.wrapBusinessException("联单数据不能为空");
            }

            TransportBillVO billVO = json.fromJson(billData, TransportBillVO.class);

            IDeicingRecordService service = ServiceLocator.find(IDeicingRecordService.class);
            TransportBillVO result = service.saveTransportBill(billVO);

            return result;
        } catch (Exception e) {
            ExceptionUtils.wrapException(e);
            return null;
        }
    }
}
