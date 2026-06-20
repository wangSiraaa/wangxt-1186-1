package nccloud.web.aim.deicing.action;

import nccloud.framework.core.exception.ExceptionUtils;
import nccloud.framework.core.json.IJson;
import nccloud.framework.service.ServiceLocator;
import nccloud.framework.web.action.itf.ICommonAction;
import nccloud.framework.web.container.IRequest;
import nccloud.framework.web.json.JsonFactory;
import nccloud.web.aim.deicing.itf.IDeicingRecordService;
import nccloud.web.aim.deicing.vo.BillPoolVO;

import java.util.Map;

public class BillPoolQueryAction implements ICommonAction {

    @Override
    public Object doAction(IRequest request) {
        try {
            IJson json = JsonFactory.create();
            @SuppressWarnings("unchecked")
            Map<String, Object> map = json.fromJson(request.read(), Map.class);

            String billId = map != null ? (String) map.get("billId") : null;
            if (billId == null || billId.isEmpty()) {
                ExceptionUtils.wrapBusinessException("联单主键不能为空");
            }

            IDeicingRecordService service = ServiceLocator.find(IDeicingRecordService.class);
            BillPoolVO[] result = service.queryBillPools(billId);

            return result;
        } catch (Exception e) {
            ExceptionUtils.wrapException(e);
            return null;
        }
    }
}
