package com.qinhailin.common.directive;

import java.io.IOException;
import java.util.Date;

import com.jfinal.template.Directive;
import com.jfinal.template.Env;
import com.jfinal.template.io.Writer;
import com.jfinal.template.stat.Scope;

/**
 * 输出当前时间指令
 * @author QinHaiLin
 *
 */
public class MyNowDirective extends Directive {

	@Override
	public void exec(Env env, Scope scope, Writer writer) {
		try {
			writer.write(new Date(), "yyyy-MM-dd");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
