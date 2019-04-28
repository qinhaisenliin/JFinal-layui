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

package com.qinhailin.common.kit;

import java.util.List;

/**
 * SqlKit
 */
public class SqlKit {

	/**
	 * 将 id 列表 join 起来，用逗号分隔，并且用小括号括起来
	 */
	public static void joinIds(List<Integer> idList, StringBuilder ret) {
		ret.append("(");
		boolean isFirst = true;
		for (Integer id : idList) {
			if (isFirst) {
				isFirst = false;
			} else {
				ret.append(", ");
			}
			ret.append(id.toString());
		}
		ret.append(")");
	}
	
	/**
	 * 将 id 列表 join 起来，用逗号分隔，并且用小括号括起来
	 * @param ids
	 * @return
	 * @author QinHaiLin
	 * @date 2019年3月13日
	 */
	public static String joinIds(List<String> ids) {
		StringBuilder ret=new StringBuilder();
		ret.append("(");
		boolean isFirst = true;
		for (String id : ids) {
			if (isFirst) {
				isFirst = false;
			} else {
				ret.append(", ");
			}
			ret.append("'").append(id.toString()).append("'");
		}
		ret.append(")");
		return ret.toString();
	}
}

