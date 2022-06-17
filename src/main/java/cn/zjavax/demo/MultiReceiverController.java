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
public class MultiReceiverController extends BaseTest {

    @Autowired
    private BaseTest baseTest;


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

        System.out.println("txHexDraft=\"" +unsignTransaction.serializeToHex()+"\"");
        return unsignTransaction.serializeToHex();
    }

}
