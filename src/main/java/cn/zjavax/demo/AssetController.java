package cn.zjavax.demo;

import cn.zjavax.demo.cardano.asset.AssetBean;
import cn.zjavax.demo.cardano.asset.AssetServiceProcess;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;


@RestController
public class AssetController {

    @Autowired
    private AssetServiceProcess assetServiceProcess;

    @ResponseBody
    @RequestMapping(value="/assets/{stackAddress}", method = RequestMethod.GET)
    public  ArrayList queryAsset(@PathVariable String stackAddress)  {
        // 直接将json信息打印出来
        System.out.println("stackAddress:"+stackAddress);
        return assetServiceProcess.getAssetList(stackAddress);
    }


    @ResponseBody
    @RequestMapping(value="/assetsDecimal", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public ArrayList assetsDecimal(@RequestBody JSONObject jsonParam)  {
        // 直接将json信息打印出来
        System.out.println(jsonParam.toJSONString());
//        JSONObject  jsonObject =    JSONObject.parseObject(jsonParam.toJSONString());
//        HashMap<String,String> map = (HashMap<String,String>)jsonObject;
//        AssetServiceProcess.getAssetDecimalListByAssets(map);
        //toMap(jsonParam);

        ObjectMapper mapper = new ObjectMapper();
        HashMap map = null;
        ArrayList<AssetBean>  listB = null ;
        try {

            map = mapper.readValue(jsonParam.toJSONString(), HashMap.class);
            System.out.println(map);
            listB = assetServiceProcess.getAssetDecimalListByAssets(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return listB;
    }

}
