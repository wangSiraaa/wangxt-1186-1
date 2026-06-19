package nccloud.web.aim.deicing.action;

import nccloud.framework.core.exception.ExceptionUtils;
import nccloud.framework.core.json.IJson;
import nccloud.framework.service.ServiceLocator;
import nccloud.framework.web.action.itf.ICommonAction;
import nccloud.framework.web.container.IRequest;
import nccloud.framework.web.json.JsonFactory;
import nccloud.web.aim.deicing.itf.IDeicingRecordService;
import nccloud.web.aim.deicing.vo.ConcentrationTestVO;

import java.util.Map;

public class ConcentrationTestSaveAction implements ICommonAction {

    @Override
    public Object doAction(IRequest request) {
        try {
            IJson json = JsonFactory.create();
            @SuppressWarnings("unchecked")
            Map<String, Object> map = json.fromJson(request.read(), Map.class);

            String testData = map != null ? (String) map.get("testData") : null;
            if (testData == null || testData.isEmpty()) {
                ExceptionUtils.wrapBusinessException("检测数据不能为空");
            }

            ConcentrationTestVO testVO = json.fromJson(testData, ConcentrationTestVO.class);

            IDeicingRecordService service = ServiceLocator.find(IDeicingRecordService.class);
            ConcentrationTestVO result = service.saveConcentrationTest(testVO);

            return result;
        } catch (Exception e) {
            ExceptionUtils.wrapException(e);
            return null;
        }
    }
}
