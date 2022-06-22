package cn.zjavax.demo.blockfrostjava;

import cn.zjavax.demo.BaseTest_mainnet_zx;
import cn.zjavax.demo.entity.PoolInfo;
import cn.zjavax.demo.util.ResultPage;
import io.blockfrost.sdk.api.model.Pool;
import io.blockfrost.sdk.api.model.PoolMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PoolController {

    ResultPage resultPage = new ResultPage<Pool>();
    List<PoolInfo> poolInfoList = new ArrayList<>();

    @Autowired
    private BaseTest_mainnet_zx baseTest_mainnet_zx;


    public static final String POOLID = "pool1c86ul4pnqvu7jzag8fjdy6dgrn6pt4ad4vmyq038hyg0wl2kaed";

    @GetMapping("/pools/extended")
    public ResultPage<PoolInfo> getPools(@RequestParam int page, @RequestParam int results) throws Exception {
//        List<String> allPools = poolServiceImpl.getAllPools(); // 3180

        if(!poolInfoList.isEmpty()){
            return resultPage;
        }

        List<String> pools = baseTest_mainnet_zx.poolServiceImpl.getPools(1, 1); // 返回poolId
        for(String poolId:pools){
            Pool pool = baseTest_mainnet_zx.poolServiceImpl.getPool(POOLID);
            PoolMetadata poolMetadata = baseTest_mainnet_zx.poolServiceImpl.getPoolMetadata(poolId);
            PoolInfo poolInfo = PoolInfo.builder().pool(pool).poolMetadata(poolMetadata).build();

            poolInfoList.add(poolInfo);
        }
//        ResultPage resultPage = new ResultPage<Pool>();
        resultPage.setTotal_count(3180);
        resultPage.setResults(poolInfoList);
        return resultPage;
    }


}
