package com.qinhailin.common.routes;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.config.Routes;
import com.jfinal.core.Controller;
import com.jfinal.log.Log;

/**
 * 自动配置路由
 * 
 * @author QinHaiLin
 *
 */
public class AutoBindRoutes extends Routes {

	private List<Class<? extends Controller>> excludeClasses = new ArrayList<>();

	private boolean includeAllJarsInLib = false;

	private List<String> includeJars = new ArrayList<String>();

    protected final Log logger = Log.getLog(getClass());

    private String packageName="";
    private String trgetName="*Controller.class";
	public AutoBindRoutes() {
		excludeClasses.add(Controller.class);
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
     * 查找指定package
     * @param packageName 如 ：com.qinhailin
     * @return
     */
    public AutoBindRoutes setPackageName(String packageName){
    	this.packageName=packageName;
    	return this;
    }
    
    /**
     * 查找指定类型文件
     * @param targetName 如：*Controller.class
     * @return
     */
    public AutoBindRoutes setTargetName(String targetName){
    	this.trgetName=targetName;
    	return this;
    }

	/**
	 * 配置路由
	 */
    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void config() {
        List<Class<? extends Controller>> controllerClasses = ClassSearcher.of(Controller.class)
                .includeAllJarsInLib(includeAllJarsInLib).injars(includeJars)
                .setPackageName(packageName).setTargetName(trgetName).search();
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
            }
        }
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

}
