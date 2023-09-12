package com.bovine.taotao.admin.web.support;

import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.SM2;
import com.alibaba.fastjson2.JSON;
import com.bovine.taotao.common.core.utils.AESUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.BadCredentialsException;

import static com.bovine.taotao.common.core.Constant.DELIMITER;
import static com.bovine.taotao.common.core.Constant.SM_HEX;

/**
 * 元数据解析器
 * @author eden
 * @date 2023/9/2 14:26:26
 */
public final class MetadataEncoderParser {

    private static final long timestamp = 3000L;

    private static SM2 sm2 = SmUtil.sm2("6c785a61e440cf06a61bff868b59d17c410c48a21cf7cb06ae905d7554df0b23", null);

    /**
     * 解析
     * @param encode 密文
     * @return
     */
    public static String parse(String encode) {
        String cipher = sm2.decryptStr(SM_HEX.concat(encode), KeyType.PrivateKey);
        Metadata metadata = JSON.parseObject(cipher, Metadata.class);
        String data = AESUtil.decrypt(metadata.getAesKey(), metadata.getBinary(), metadata.getIv());
        if(data == null) {
            throw new BadCredentialsException("AES解密异常!");
        }
        int indexOf = data.indexOf(DELIMITER);
        if(indexOf == -1) {
            throw new BadCredentialsException("解析数据格式异常!");
        }
        String meta = data.substring(0, indexOf);
        String time = data.substring(indexOf + 1);
        if(!time.matches("[0-9]+")) {
            throw new BadCredentialsException("解析时间戳异常!");
        }
        if((System.currentTimeMillis() - Long.valueOf(time)) > timestamp) {
            throw new BadCredentialsException("加密数据超时异常!");
        }
        return meta;
    }

    /**
     * 元数据对象
     */
    @Getter
    @Setter
    private static class Metadata {

        private String aesKey;

        private String iv;

        private String binary;

    }
}
