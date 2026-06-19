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

public class DeicingRecordListAction implements ICommonAction {

    @Override
    public Object doAction(IRequest request) {
        try {
            IJson json = JsonFactory.create();
            @SuppressWarnings("unchecked")
            Map<String, Object> map = json.fromJson(request.read(), Map.class);

            String whereClause = map != null ? (String) map.get("whereClause") : null;

            IDeicingRecordService service = ServiceLocator.find(IDeicingRecordService.class);
            AggDeicingRecordVO[] result = service.queryDeicingRecords(whereClause);

            return result;
        } catch (Exception e) {
            ExceptionUtils.wrapException(e);
            return null;
        }
    }
}
