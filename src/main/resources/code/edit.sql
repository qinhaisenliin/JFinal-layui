/**edit.html代码模板*/
#sql("edit")
#[[
#@layout()
#define main()
  <form action="#(path)/portal${actionKey}/update" class="layui-form layui-form-pane f-form" method="post" autocomplete="off">
      #include("_form.html")
  </form>
#end

]]#

#end