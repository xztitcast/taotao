package com.bovine.taotao.common.security.session;

import com.bovine.taotao.common.core.Principal;

/**
 * 认证Token管理器
 * @author eden
 * @date 2023/9/23 22:28:28
 */
public interface AuthenticationTokenWebManager {

    String TOKEN_KEY = "JC:SECURITY:TOKEN:";

    /**
     * 生成token
     * @param principal (主体)生态用户
     * @return
     */
    String createToken(Principal principal);

    /**
     * 生成token
     * @param principal 主体
     * @param isVisitor 是否是临时访问者
     * @return
     */
    String createToken(Principal principal, boolean isVisitor);

    /**
     * 获取用户信息
     * @param token 令牌
     * @return
     */
    Principal getByToken(String token);

    /**
     * 删除
     * @param token 令牌
     * @return
     */
    boolean remove(String token);
}
