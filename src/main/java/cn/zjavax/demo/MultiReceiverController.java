package cn.zjavax.demo;

import com.bloxbean.cardano.client.common.ADAConversionUtil;
import com.bloxbean.cardano.client.function.Output;
import com.bloxbean.cardano.client.function.TxBuilder;
import com.bloxbean.cardano.client.function.TxBuilderContext;
import com.bloxbean.cardano.client.function.TxOutputBuilder;
import com.bloxbean.cardano.client.transaction.spec.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

import static com.bloxbean.cardano.client.function.helper.ChangeOutputAdjustments.adjustChangeOutput;
import static com.bloxbean.cardano.client.function.helper.FeeCalculators.feeCalculator;
import static com.bloxbean.cardano.client.function.helper.InputBuilders.createFromSender;

/**
 * 一对多发送ada和资产
 *
 * @author <zjavax>
 * @since <pre>06/17/2022</pre>
 */
@RestController
public class MultiReceiverController {

    @Autowired
    private BaseTest baseTest;

    //        String senderMnemonic = "kit color frog trick speak employ suit sort bomb goddess jewel primary spoil fade person useless measure manage warfare reduce few scrub beyond era";
    //    127.0.0.1:8080/buildTx/addr_test1qqxnp3khzm7kcj9t23hskehat7428ghsenk0pfew4rqy5v9frnmht7uwrl073q4jvq20z82kh4rksyns540azhndqexqpvhgqr
//    [ {
//        "receiverAddress" : "addr_test1qq4pxvsevncnfd7ppmatyavej7kjcukwxugrnk9rsejufvpfqqgflwz7ahhqezd8mx5hfxmwh2stfagm8uwkxreya6rsqfxnz7",
//                "policyId" : "75d01e750b6e4986f4d26f92d70c70a9d337e834faebcc177a7e796a",
//                "assetName" : "TestCoin",
//                "amount" : 1.0,
//                "decimals" : 0
//    }, {
//        "receiverAddress" : "addr_test1qqqvjp4ffcdqg3fmx0k8rwamnn06wp8e575zcv8d0m3tjn2mmexsnkxp7az774522ce4h3qs4tjp9rxjjm46qf339d9sk33rqn",
//                "policyId" : "75d01e750b6e4986f4d26f92d70c70a9d337e834faebcc177a7e796a",
//                "assetName" : "TestCoin",
//                "amount" : 1.0,
//                "decimals" : 0
//    } ]
    @PostMapping("/buildTx/{senderAddress}")
    public String buildTx(@PathVariable String senderAddress, @RequestBody List<TransferAsset> transferAssetList) throws Exception{
        TxOutputBuilder txOutputBuilder=null;
        for (TransferAsset transferAsset : transferAssetList) {
            Long decimals = transferAsset.getDecimals();
            Output output = Output.builder()
                    .address(transferAsset.getReceiverAddress())
                    .policyId(transferAsset.getPolicyId())
                    .assetName(transferAsset.getAssetName())
                    .qty(ADAConversionUtil.assetFromDecimal(BigDecimal.valueOf(transferAsset.getAmount()), decimals))
                    .build();
            if(txOutputBuilder==null) {
                txOutputBuilder = output.outputBuilder();
            } else {
                txOutputBuilder.and(output.outputBuilder());
            }
        }
        TxBuilder txBuilder = (txOutputBuilder.buildInputs(createFromSender(senderAddress, senderAddress)))
                .andThen(feeCalculator(senderAddress, 1))
                .andThen(adjustChangeOutput(senderAddress, 1));


        Transaction unsignTransaction = TxBuilderContext.init(baseTest.utxoSupplier, baseTest.protocolParamsSupplier).build(txBuilder);

        return unsignTransaction.serializeToHex();
    }

}
