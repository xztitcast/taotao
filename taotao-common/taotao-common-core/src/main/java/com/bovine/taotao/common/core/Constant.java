package com.bovine.taotao.common.core;

public interface Constant {
	
	/**
	 * 机构常量
	 */
	String TISSID = "tisid";
	
	/**
	 * token标识符常量
	 */
	String TOKEN_SSID = "tokenssid";
	
	/**
	 * redis限流通过后响应给前端的响应头KEY
	 */
	String LIMIT_TOKEN_HEADER = "X-RateLimit-Pass-Token";

	/**
	 * 经度
	 */
	String LNG = "lng";

	/**
	 * 维度
	 */
	String LAT = "lat";

	/**
	 * 系统
	 * @author eden
	 * @time 2022年7月22日 下午3:48:58
	 */
	public interface Sys {
		
		int SUPER_ADMIN = 1;
        
        String MENU_NAME = "一级菜单";
        
        String SYS_TOKEN = "token";
        
	}
	
	/**
	 * 菜单类型
	 * @author eden
	 * @time 2022年7月22日 下午3:48:53
	 */
    public enum MenuType {
        /**
         * 目录
         */
    	CATALOG(0),
        /**
         * 菜单
         */
        MENU(1),
        /**
         * 按钮
         */
        BUTTON(2);

        private int value;

        MenuType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
    
    /**
     * 定时任务状态
     * @author eden
     * @time 2022年7月22日 下午3:48:41
     */
    public enum ScheduleStatus {
        /**
         * 正常
         */
    	NORMAL(0),
        /**
         * 暂停
         */
    	PAUSE(1);

        private int value;

        ScheduleStatus(int value) {
            this.value = value;
        }
        
        public int getValue() {
            return value;
        }
    }
    
    /**
     * 字典模板
     * @author eden
     * @date 2022年11月20日 下午6:31:29
     */
    public interface DictTemplate {
    	
    	String TABLE = "sys_dict";
    	
    	String TEST_KEY = "TEST_KEY";
    }

	/**
	 * 是否有效状态 描述
	 * @author eden
	 * @date 2022年11月20日 下午6:31:29
	 */
	public enum ValidStatus {
		/**
		 * 无效
		 */
		IN_VALID(0),
		/**
		 * 有效
		 */
		VALID(1);

		private int value;

		ValidStatus(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}


	/**
	 * 有效公告条数限制 描述
	 * @author eden
	 * @date 2022年11月20日 下午6:31:29
	 */
	public interface ValidNoticeLimit {
		int VALID_LIMIT = 5;
	}

	/**
	 * 审核状态 描述
	 * 申请状态 1:待审核 2:审核中 3:审核成功 4:审核失败
	 * @author eden
	 * @date 2022年11月20日 下午6:31:29
	 */
	public enum AuditStatus {

		/**
		 * 待审核
		 */
		AUDIT_WAIT(1),

		/**
		 * 审核中
		 */
		AUDIT_IN(2),

		/**
		 * 审核成功
		 */
		AUDIT_SUCCESS(3),

		/**
		 * 审核失败
		 */
		AUDIT_FAIL(4);

		private int value;

		AuditStatus(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}


	/**
     * Redis Key接口统一进行管理
     * 常亮命名必须全部大写
     * @author eden
     * @date 2023年2月19日 上午9:05:17
     */
    public interface RedisKey {
    	
    	/**
    	 * 设备KEY
    	 */
    	String GATEWAY_DEVICEID_STR_KEY = "jc:gateway:deviceId:";
    	
    	/**
    	 * 防重复请求KEY
    	 */
    	String GATEWAY_DUPLICATE_STR_KEY = "jc:gateway:duplicate:";
    	
    	/**
    	 * 令牌桶限流后通行证KEY
    	 */
    	String GATEWAY_LIMITER_RATE_STR_KEY = "jc:gateway:limiter:rate:";
    	
    	/**
    	 * 网关生成TOKEN令牌的KEY
    	 */
    	String GATEEAY_LIMITER_TOKEN_VALUE_STR_KEY = "jc:gateway:limiter:token:";
    	
    	/**
    	 * 网关限流通行证KEY
    	 */
    	String GATEWAY_REQUEST_FLOW_COUNT_STR_KEY = "jc:gateway:request:flow:count";
    	
    	/**
    	 * 后台管理session id 
    	 */
    	String SYS_SESSION_ID_STR_KEY = "jc:sys:sessionId:";
    	
    	/**
    	 * 后台管理行政区
    	 */
    	String SYS_REGION_STR_KEY = "jc:sys:region:";
    	
    	/**
    	 * 小城登录KEY
    	 */
    	String SSO_MINI_LOGIN_STR_KEY = "jc:sso:";

		/**
		 * 后台管系统登录锁定KEY
		 */
		String SYS_LOGIN_LOCKED_KEY = "jc:sys:locked:";
    	
    }
    
}
