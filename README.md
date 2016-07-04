# doppler
日志监控系统

#主要功能
`doppler`系统是在开源软件`jd-hydra`的基础上二次开发形成的日志监控系统，主要功能有，调用链跟踪，服务监控，异常监控，日志检索，业务打点日志记录及查询等；可以替代dubbo的监控；

#适合场景
* 适合基于`dubbo`开发的分布式服务项目的监控;
* `QPS`不超过6000万的中小型项目;
* 对日志需要检索

#整体架构图
![整体架构图](https://raw.githubusercontent.com/fxltsbl3855/doppler/master/pic_for_readme/arch.png)

#日志收集步骤介绍
* `Hydra-client`异步发送异常/日志到`hydra-collector/hydra-manager`
* `hydra-collector/hydra-manager`异步发送异常/日志到`mysql`
* `Doppler-server`从`mysql`读取日志
* `doppler-web`展示日志

#日志收集架构优点
* 对应用层透明；对业务零侵入(利用`dubbo`的`filter`)
* 对日志进行关键字检索（使用`mysql`的`fulltext index`）
* 可线性扩展
* 低消耗；100并发以下的服务调用情况下，性损率都在10%以内
* 快速的数据分析；（能实时查询性能指标）

#对项目的要求
`dubbo`项目的项目信息(`container`,日志,注册中心地址,项目名称,端口)必须配置在`dubbo.properties`中(这也是`dubbo`的标准配置);

#如何使用doppler监控
参看部署文档 docs/《多普勒(Dopper)3.0部署使用文档》

#规划
后续会针对大数据量日志/请求做部分架构调整,使用`ES+Hbase`做大数据存储及海量数据检索

	
