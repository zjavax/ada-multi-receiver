package cn.zjavax.demo.blockfrostjava;

import cn.zjavax.demo.BaseTest_mainnet_zx;
import io.blockfrost.sdk.api.exception.APIException;
import io.blockfrost.sdk.api.model.Epoch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/epochs")
public class EpochsController {

    @Autowired
    private BaseTest_mainnet_zx baseTest_mainnet_zx;

    @GetMapping("/lastestEpoch")
    public Epoch getLatestEpoch() throws APIException {
        Epoch latestEpoch = baseTest_mainnet_zx.epochServiceImpl.getLatestEpoch();
        return latestEpoch;
    }
}
