# doppler
日志监控系统

#主要功能
doppler系统是在开源软件jd-hydra的基础上二次开发形成的日志监控系统，主要功能有，调用链跟踪，服务监控，异常监控，业务打点日志记录及查询等；

#适合场景
* 项目是基于dubbo开发的分布式服务项目;
* 每天请求量在6000万以下的中小型项目;

#整体架构图
(http://dangdangdotcom.github.io/sharding-jdbc/img/architecture.png)

#日志收集步骤介绍
* Hydra-client异步发送异常/日志到hydra-collector/hydra-manager
* hydra-collector/hydra-manager异步发送异常/日志到mysql
* Doppler-server从mysql读取日志
* doppler-web展示日志

#日志收集架构优点
* 对应用层透明；对业务零侵入
* 可线性扩展
* 低消耗；100并发以下的服务调用情况下，性损率都在10%以内
* 快速的数据分析；（能实时查询性能指标）

#对项目的要求
dubbo项目的项目信息(container,日志,注册中心地址,项目名称,端口)必须配置在dubbo.properties中(这也是dubbo的标准配置);

#如何使用doppler监控
参看部署文档 docs/《多普勒(Dopper)3.0部署使用文档》



	
