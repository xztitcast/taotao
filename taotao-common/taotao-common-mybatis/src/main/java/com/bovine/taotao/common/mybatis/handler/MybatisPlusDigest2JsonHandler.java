package com.bovine.taotao.common.mybatis.handler;

import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.SM2;
import com.baomidou.mybatisplus.extension.handlers.AbstractJsonTypeHandler;

/**
 * 数据库SM2国密加解密
 * @author eden
 * @date 2023/9/16 20:36:36
 */
public class MybatisPlusDigest2JsonHandler extends AbstractJsonTypeHandler<String> {

    private SM2 sm2;

    public MybatisPlusDigest2JsonHandler() {
        this.sm2 = new SM2("MIGTAgEAMBMGByqGSM49AgEGCCqBHM9VAYItBHkwdwIBAQQgSSMCUE4eGgcpA3tTUsL0ddVtzoQ9jg8k7txN0oor92qgCgYIKoEcz1UBgi2hRANCAAT5FKgGfEwA/nDUcn6iBZmyPc0qW0BPYwI6MoxRdgPVO8bTjR5tcg7vB7iNK0iBkJ8jiEvINKQAbcz1cvbQgj80",
                "MFkwEwYHKoZIzj0CAQYIKoEcz1UBgi0DQgAE+RSoBnxMAP5w1HJ+ogWZsj3NKltAT2MCOjKMUXYD1TvG040ebXIO7we4jStIgZCfI4hLyDSkAG3M9XL20II/NA==");
    }

    @Override
    protected String parse(String json) {
        return sm2.decryptStr(json, KeyType.PrivateKey);
    }

    @Override
    protected String toJson(String obj) {
        return sm2.encryptHex(obj, KeyType.PrivateKey);
    }
}