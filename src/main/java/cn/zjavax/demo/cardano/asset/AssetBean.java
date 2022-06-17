package cn.zjavax.demo.cardano.asset;

import java.math.BigDecimal;

public class AssetBean {




    private String assetStr;

    private int decimal;

    private BigDecimal amount;

    private BigDecimal originalAmount;


    private String name;



    private String log;

    public  AssetBean(){}

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAssetStr() {
        return assetStr;
    }

    public void setAssetStr(String assetStr) {
        this.assetStr = assetStr;
    }

    public int getDecimal() {
        return decimal;
    }

    public void setDecimal(int decimal) {
        this.decimal = decimal;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getOriginalAmount() {
        return originalAmount;
    }

    public void setOriginalAmount(BigDecimal originalAmount) {
        this.originalAmount = originalAmount;
    }

}
