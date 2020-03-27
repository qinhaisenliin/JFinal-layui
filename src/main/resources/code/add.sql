/**add.html代码模板*/
#sql("add")
#[[
#@layout()
#define main()
  <form action="#(path)/portal${actionKey}/save" class="layui-form layui-form-pane f-form" method="post" autocomplete="off">
    #include("_form.html")
  </form>
#end

]]#

#end