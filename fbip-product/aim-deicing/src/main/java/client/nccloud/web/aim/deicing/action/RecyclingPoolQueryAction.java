package nccloud.web.aim.deicing.action;

import nccloud.framework.core.exception.ExceptionUtils;
import nccloud.framework.core.json.IJson;
import nccloud.framework.service.ServiceLocator;
import nccloud.framework.web.action.itf.ICommonAction;
import nccloud.framework.web.container.IRequest;
import nccloud.framework.web.json.JsonFactory;
import nccloud.web.aim.deicing.itf.IDeicingRecordService;
import nccloud.web.aim.deicing.vo.RecyclingPoolVO;

import java.util.Map;

public class RecyclingPoolQueryAction implements ICommonAction {

    @Override
    public Object doAction(IRequest request) {
        try {
            IJson json = JsonFactory.create();
            @SuppressWarnings("unchecked")
            Map<String, Object> map = json.fromJson(request.read(), Map.class);

            String pkOrg = map != null ? (String) map.get("pkOrg") : null;

            IDeicingRecordService service = ServiceLocator.find(IDeicingRecordService.class);
            RecyclingPoolVO[] result = service.queryRecyclingPools(pkOrg);

            return result;
        } catch (Exception e) {
            ExceptionUtils.wrapException(e);
            return null;
        }
    }
}
