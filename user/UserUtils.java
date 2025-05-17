package com.yf.exam.modules.user;

import com.yf.exam.core.api.ApiError;
import com.yf.exam.core.exception.ServiceException;
import com.yf.exam.modules.sys.user.dto.response.SysUserLoginDTO;
import org.apache.shiro.SecurityUtils;

/**
 * 用户静态工具类
 */
public class UserUtils {

    /**
     * 获取当前登录用户的ID
     * @param throwable 是否在获取失败时抛出异常
     * @return 用户ID
     */
    public static String getUserId(boolean throwable) {
        try {
            return ((SysUserLoginDTO) SecurityUtils.getSubject().getPrincipal()).getId();
        } catch (Exception e) {
            if (throwable) {
                throw new ServiceException(ApiError.ERROR_10010002);
            }
            return null;
        }
    }

    /**
     * 判断当前登录用户是否为管理员
     * @param throwable 是否在获取失败时抛出异常
     * @return 是否为管理员
     */
    public static boolean isAdmin(boolean throwable) {
        try {
            SysUserLoginDTO dto = ((SysUserLoginDTO) SecurityUtils.getSubject().getPrincipal());
            return dto.getRoles() != null && dto.getRoles().contains("sa");
        } catch (Exception e) {
            if (throwable) {
                throw new ServiceException(ApiError.ERROR_10010002);
            }
        }
        return false;
    }

    /**
     * 获取当前登录用户的ID，默认是会抛异常的
     * @return 用户ID
     */
    public static String getUserId() {
        return getUserId(true);
    }
}