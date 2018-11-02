package com.shanghai.common.rabbitmq;
/**
* @author: YeJR 
* @version: 2018年5月24日 下午4:25:01
* 定义了RabbitMQ需要的常量
*/
public class RabbitMqEnum {
	
	/**
	 * 数据交换方式内部枚举类
	 * @author YeJR
	 *
	 */
	public enum Exchange {
		/**
		 * direct点对点交换机
		 */
		CONTRACT_DIRECT("CONTRACT_DIRECT", "点对点"),
		/**
		 * fanout交换机
		 */
		CONTRACT_FANOUT("CONTRACT_FANOUT", "消息分发"),
		/**
		 * topic交换机
		 */
        CONTRACT_TOPIC("CONTRACT_TOPIC", "消息订阅");
		
		private String code;
        private String name;

        Exchange(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }
        
	}
	
	/**
	 * 队列名称内部枚举类
	 * @author YeJR
	 *
	 */
	public enum QueueName {
		/**
		 * 系统通知队列名称
		 */
		SYSTEMNOTIFY("SYSTEMNOTIFY", "通知队列"),
		/**
		 * 邮件通知队列
		 */
		EMAILNOTIFY("EMAILNOTIFY", "邮件队列"),
		/**
		 * 短信通知队列
		 */
		SMSNOTIFY("SMSNOTIFY", "短信队列");

        private String code;
        private String name;

        QueueName(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }

	}
	
	/**
	 * routing_key 路由键 内部枚举类
	 * @author YeJR
	 */
	public enum RoutingKeyEnum {
		/**
		 * 系统通知routing_key
		 */
        SYSTEMTIPKEY("sysTip", "系统提示队列"),
        /**
		 * 邮件通知routing_key
		 */
        EMAILKEY("*.email.*", "邮件队列"),
        /**
		 * 短信通知routing_key
		 */
        SMSKEY("sms.#", "短信队列");

        private String code;
        private String name;

        RoutingKeyEnum(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }
   
	}
	
}
