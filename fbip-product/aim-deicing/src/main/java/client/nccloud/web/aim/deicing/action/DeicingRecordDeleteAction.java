package nccloud.web.aim.deicing.action;

import nccloud.framework.core.exception.ExceptionUtils;
import nccloud.framework.core.json.IJson;
import nccloud.framework.service.ServiceLocator;
import nccloud.framework.web.action.itf.ICommonAction;
import nccloud.framework.web.container.IRequest;
import nccloud.framework.web.json.JsonFactory;
import nccloud.web.aim.deicing.itf.IDeicingRecordService;

import java.util.List;
import java.util.Map;

public class DeicingRecordDeleteAction implements ICommonAction {

    @Override
    public Object doAction(IRequest request) {
        try {
            IJson json = JsonFactory.create();
            @SuppressWarnings("unchecked")
            Map<String, Object> map = json.fromJson(request.read(), Map.class);

            @SuppressWarnings("unchecked")
            List<String> pks = map != null ? (List<String>) map.get("pks") : null;
            if (pks == null || pks.isEmpty()) {
                ExceptionUtils.wrapBusinessException("请选择要删除的记录");
            }

            String[] pkArray = pks.toArray(new String[0]);

            IDeicingRecordService service = ServiceLocator.find(IDeicingRecordService.class);
            service.deleteDeicingRecord(pkArray);

            return true;
        } catch (Exception e) {
            ExceptionUtils.wrapException(e);
            return null;
        }
    }
}
