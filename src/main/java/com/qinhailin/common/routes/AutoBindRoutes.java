package com.qinhailin.common.routes;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.config.Routes;
import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.jfinal.log.Log;

/**
 * 自动配置路由
 * 
 * @author QinHaiLin
 *
 */
public class AutoBindRoutes extends Routes {

    private boolean autoScan = true;

	private List<Class<? extends Controller>> excludeClasses = new ArrayList<>();

	private boolean includeAllJarsInLib = false;

	private List<String> includeJars = new ArrayList<String>();

    protected final Log logger = Log.getLog(getClass());

    private String suffix = "Controller";

	public AutoBindRoutes() {
		excludeClasses.add(Controller.class);
	}
    public AutoBindRoutes autoScan(boolean autoScan) {
        this.autoScan = autoScan;
        return this;
    }

	/**
	 * 添加要排除的class类
	 * 
	 * @param clazzes
	 * @return
	 * @author QinHaiLin
	 * @date 2018年7月17日
	 */
    @SuppressWarnings("unchecked")
    public AutoBindRoutes addExcludeClasses(Class<? extends Controller>... clazzes) {
        if (clazzes != null) {
            for (Class<? extends Controller> clazz : clazzes) {
                excludeClasses.add(clazz);
            }
        }
        return this;
    }

	/**
	 * 添加要排除的class类
	 * 
	 * @param clazzes
	 * @return
	 * @author QinHaiLin
	 * @date 2018年7月17日
	 */
    public AutoBindRoutes addExcludeClasses(List<Class<? extends Controller>> clazzes) {
        excludeClasses.addAll(clazzes);
        return this;
    }

	/**
	 * 添加指定的jar包
	 * 
	 * @param jars
	 * @return
	 * @author QinHaiLin
	 * @date 2018年7月17日
	 */
    public AutoBindRoutes addJars(String... jars) {
        if (jars != null) {
            for (String jar : jars) {
                includeJars.add(jar);
            }
        }
        return this;
    }

	/**
	 * 配置路由
	 */
    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void config() {
        List<Class<? extends Controller>> controllerClasses = ClassSearcher.of(Controller.class)
                .includeAllJarsInLib(includeAllJarsInLib).injars(includeJars).search();
        ControllerBind controllerBind = null;
        for (Class controller : controllerClasses) {
			// 排除指定类
            if (excludeClasses.contains(controller)) {
                continue;
            }
            controllerBind = (ControllerBind) controller.getAnnotation(ControllerBind.class);
            if (controllerBind != null) {
            	String actionkey=controllerBind.path();
            	String viewPath=controllerBind.viewPath();
            	if("/".equals(viewPath)&&!actionkey.equals("/portal")){
            		viewPath=actionkey;
            	}            	
            	this.add(actionkey, controller,viewPath );
            }else{
            	// 排除不是自动配置的类
                if (!autoScan) {
                    continue;
                }
            	//jar包中的controller
            	this.add(controllerKey(controller), controller,controllerKey(controller));
            }
        }
    }

	/**
	 * 截取类名作为控制器key值
	 * 
	 * @param clazz
	 * @return
	 * @author QinHaiLin
	 * @date 2018年7月17日
	 */
    private String controllerKey(Class<Controller> clazz) {
		if (!clazz.getSimpleName().endsWith(suffix)) {
			return "/" + StrKit.firstCharToLowerCase(clazz.getSimpleName());
		}
        String controllerKey = "/" + StrKit.firstCharToLowerCase(clazz.getSimpleName());
        controllerKey = controllerKey.substring(0, controllerKey.indexOf(suffix));
        return controllerKey;
    }

	/**
	 * 是否读取lib目录下jar包中的控制器类，<br/>
	 * 在打jar包部署时，必须设置为true,<br/>
	 * 同时，需要把项目源码的jar复制一份到WEB-INF/lib目录下
	 * 否则报404错误,开发时可以为false。
	 * 
	 * @param includeAllJarsInLib
	 * @return
	 * @author QinHaiLin
	 * @date 2018年7月17日
	 */
    public AutoBindRoutes includeAllJarsInLib(boolean includeAllJarsInLib) {
        this.includeAllJarsInLib = includeAllJarsInLib;
        return this;
    }

	/**
	 * 设置截取标识类
	 * 
	 * @param suffix
	 * @return
	 * @author QinHaiLin
	 * @date 2018年7月17日
	 */
    public AutoBindRoutes suffix(String suffix) {
        this.suffix = suffix;
        return this;
    }

}
