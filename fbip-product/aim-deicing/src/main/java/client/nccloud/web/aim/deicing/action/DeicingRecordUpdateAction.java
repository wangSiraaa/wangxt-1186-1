package nccloud.web.aim.deicing.action;

import nccloud.framework.core.exception.ExceptionUtils;
import nccloud.framework.core.json.IJson;
import nccloud.framework.service.ServiceLocator;
import nccloud.framework.web.action.itf.ICommonAction;
import nccloud.framework.web.container.IRequest;
import nccloud.framework.web.json.JsonFactory;
import nccloud.web.aim.deicing.itf.IDeicingRecordService;
import nccloud.web.aim.deicing.vo.AggDeicingRecordVO;

import java.util.Map;

public class DeicingRecordUpdateAction implements ICommonAction {

    @Override
    public Object doAction(IRequest request) {
        try {
            IJson json = JsonFactory.create();
            @SuppressWarnings("unchecked")
            Map<String, Object> map = json.fromJson(request.read(), Map.class);

            String billData = map != null ? (String) map.get("billData") : null;
            if (billData == null || billData.isEmpty()) {
                ExceptionUtils.wrapBusinessException("单据数据不能为空");
            }

            AggDeicingRecordVO aggVO = json.fromJson(billData, AggDeicingRecordVO.class);

            IDeicingRecordService service = ServiceLocator.find(IDeicingRecordService.class);
            AggDeicingRecordVO result = service.updateDeicingRecord(aggVO);

            return result;
        } catch (Exception e) {
            ExceptionUtils.wrapException(e);
            return null;
        }
    }
}
