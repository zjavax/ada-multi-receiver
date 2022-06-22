package cn.zjavax.demo.entity;

import io.blockfrost.sdk.api.model.Pool;
import io.blockfrost.sdk.api.model.PoolMetadata;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PoolInfo {

    Pool pool;
    PoolMetadata poolMetadata;

}
