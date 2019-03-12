/**
 * Copyright 2019 覃海林(qinhaisenlin@163.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */ 

package com.qinhailin.common.routes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注解配置控制器路由<br/>
 * viewPath为默认值"/"时,加载到JFinal路由时,和path相同
 * 
 * @author qinhailin
 * @date 2018年7月17日
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface ControllerBind {
	/**
	 * 控制器路径值
	 * 
	 * @return
	 * @author qinhailin
	 * @date 2018年7月17日
	 */
	String path();

	/**
	 * 控制器视图文件目录
	 * 
	 * @return
	 * @author qinhailin
	 * @date 2018年7月17日
	 */
	String viewPath() default "/";
}
