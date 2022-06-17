package cn.zjavax.demo;

import cn.zjavax.demo.config.EncryptedPropertyConfigurer;
import com.bloxbean.cardano.client.api.ProtocolParamsSupplier;
import com.bloxbean.cardano.client.api.UtxoSupplier;
import com.bloxbean.cardano.client.api.helper.FeeCalculationService;
import com.bloxbean.cardano.client.api.helper.TransactionHelperService;
import com.bloxbean.cardano.client.api.helper.UtxoTransactionBuilder;
import com.bloxbean.cardano.client.backend.api.*;
import com.bloxbean.cardano.client.backend.blockfrost.common.Constants;
import com.bloxbean.cardano.client.backend.blockfrost.service.BFBackendService;
import io.blockfrost.sdk.impl.AccountServiceImpl;
import io.blockfrost.sdk.impl.AssetServiceImpl;
import org.springframework.stereotype.Component;

@Component
public class BaseTest {

    protected BackendService backendService;
    protected FeeCalculationService feeCalculationService;
    protected TransactionHelperService transactionHelperService;
    protected TransactionService transactionService;
    protected BlockService blockService;
    protected AssetService assetService;
    protected NetworkInfoService networkInfoService;
    protected UtxoService utxoService;
    protected EpochService epochService;
    protected UtxoTransactionBuilder utxoTransactionBuilder;

    protected UtxoSupplier utxoSupplier;
    protected ProtocolParamsSupplier protocolParamsSupplier;

    public AccountServiceImpl accountServiceImpl;
    public AssetServiceImpl assetServiceImpl;   // protected AssetService assetService; 二者应该是一样的

    public BaseTest() {

        String blockfrostCardanoTestnetProjectId = EncryptedPropertyConfigurer.map.get("blockfrost.cardano.testnet.project.id");

        backendService =
                new BFBackendService(Constants.BLOCKFROST_TESTNET_URL, blockfrostCardanoTestnetProjectId);
//                new KoiosBackendService(KOIOS_TESTNET_URL);
        accountServiceImpl  = new AccountServiceImpl(Constants.BLOCKFROST_TESTNET_URL, blockfrostCardanoTestnetProjectId);
        assetServiceImpl    = new AssetServiceImpl(Constants.BLOCKFROST_TESTNET_URL, blockfrostCardanoTestnetProjectId);

        feeCalculationService = backendService.getFeeCalculationService();
        transactionHelperService = backendService.getTransactionHelperService();
        transactionService = backendService.getTransactionService();
        blockService = backendService.getBlockService();
        assetService = backendService.getAssetService();
        utxoService = backendService.getUtxoService();
        networkInfoService = backendService.getNetworkInfoService();
        epochService = backendService.getEpochService();
        utxoTransactionBuilder = backendService.getUtxoTransactionBuilder();
        utxoSupplier = new DefaultUtxoSupplier(backendService.getUtxoService());
        protocolParamsSupplier = new DefaultProtocolParamsSupplier(epochService);

    }


}
