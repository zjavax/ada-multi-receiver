package cn.zjavax.demo.cardano.asset;


import cn.zjavax.demo.BaseTest;
import io.blockfrost.sdk.api.exception.APIException;
import io.blockfrost.sdk.api.model.AccountAsset;
import io.blockfrost.sdk.api.model.Asset;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class AssetServiceProcess {

    @Autowired
    private BaseTest baseTest;

    private static String EMPTY_LOG = "";

    public   ArrayList<AssetBean> getAssetDecimalListByAssets(HashMap<String,String> assetMap)
    {
        ArrayList<AssetBean>  listB = new ArrayList<AssetBean>();

        assetMap.forEach((key, value) -> {

            AssetBean b = new  AssetBean();

            b.setAssetStr(key);

            Asset ass = null;
            try {
                System.out.println("key:"+key);

                ass = baseTest.assetServiceImpl.getAsset(key);

                if(null != ass && null != ass.getMetadata() &&  null != ass.getMetadata().getDecimals())
                {
                    b.setDecimal(ass.getMetadata().getDecimals());
                    System.out.println("getDecimals:"+ b.getDecimal());
                }
                else
                {
                    b.setDecimal(0);
                }
                if(b.getDecimal() > 0)
                {
                    BigDecimal  oDecimal = new BigDecimal(value);
                    b.setAmount( oDecimal.divide(new  BigDecimal( String.valueOf( Math.pow(10, b.getDecimal()) )), b.getDecimal() ,BigDecimal.ROUND_HALF_UP) );
                }
                else
                {
                    b.setAmount(new BigDecimal(value));
                }
            } catch (APIException e) {
                e.printStackTrace();
            }
            listB.add(b);



        });

        return listB;
    }


    public   ArrayList<AssetBean> getAssetList(String stack_address)
    {
        ArrayList<AssetBean>  listB = new ArrayList<AssetBean>();
        try {
            List<AccountAsset> list = baseTest.accountServiceImpl.getAccountAssets(stack_address,30,1);

            for(AccountAsset asset : list)
            {
                AssetBean b = new AssetBean();
                b.setOriginalAmount( new BigDecimal(asset.getQuantity()) );
                b.setAssetStr(asset.getUnit());

                try {
                    b.setName(getAssetName(asset.getUnit()));
                } catch (DecoderException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Asset ass = baseTest.assetServiceImpl.getAsset(asset.getUnit());

                //设置小数点
                if(null != ass && null != ass.getMetadata() )
                {
                    if(null != ass.getMetadata().getDecimals())
                    {
                        b.setDecimal(ass.getMetadata().getDecimals());
                    }
                    //System.out.println(ass.getMetadata().getDecimals());
                    if(null != ass.getMetadata().getLogo())
                    {
                        b.setLog(ass.getMetadata().getLogo());
                    }
                    else
                    {
                        b.setLog(EMPTY_LOG);
                    }
                }
                else
                {
                    b.setDecimal(0);
                    b.setLog(EMPTY_LOG);
                }

                if(b.getDecimal() > 0)
                {

                    b.setAmount( b.getOriginalAmount().divide(new  BigDecimal( String.valueOf( Math.pow(10, b.getDecimal()) )), b.getDecimal() ,BigDecimal.ROUND_HALF_UP) );
                }
                else
                {
                    b.setAmount(b.getOriginalAmount());
                }



                listB.add(b);


            }
        } catch (APIException e) {
            e.printStackTrace();
        }
        return listB;
    }




    public static String  getAssetName(String in_policyAsset) throws DecoderException, UnsupportedEncodingException {
        String t = in_policyAsset.substring(56,in_policyAsset.length());
        return new String(Hex.decodeHex(t.toCharArray()), "UTF-8");
    }


    public static void main(String[] args) {

        String s = "bca98c77ee3bffcbb80ce89fcafb6a93101c4dc80b505f88d2023a8e5348454c4c455931323732";

        try {
            getAssetName(s);
        } catch (DecoderException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
