# doppler
日志监控系统

主要功能<br>
doppler系统是在开源软件jd-hydra的基础上二次开发形成的日志监控系统，主要功能有，调用链跟踪，服务监控，异常监控，业务打点日志记录及查询等；

适合场景<br>
项目是基于dubbo开发的分布式服务项目;<br>
每天请求量在6000万以下的中小型项目;<br>

对项目的要求<br>
对dubbox项目的配置方式有一个要求：<br>
	dubbo项目的项目信息必须配置在dubbo.properties中(这也是dubbo的标准配置)；项目信息：<br>
		dubbo.container=spring<br>
		dubbo.application.logger=slf4j<br>
		dubbo.application.name={project_name}<br>
		dubbo.registry.address={zk_address}<br>
		dubbo.protocol.dubbo.port={port}<br>

如何使用doppler监控<br>
参看部署文档《多普勒(Dopper)3.0部署文档》


	
