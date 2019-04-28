增删改查SQL模板

查询模板
#sql("find")
  SELECT * FROM #(tableName)
  #for(x : cond)
    #(for.first ? "where": "and") #(x.key)   #para(x.value)
  #end
  #(groupOrder)
#end

保存模板
#sql("save")
	INSERT INTO #(tableName) (
	#for(x : cond)
    	 #(for.first ? "": ",")#(x.key)
  	#end
	) VALUES (
	#for(x : cond)
    	 #(for.first ? "": ",")#para(x.value)
  	#end
	)
#end

修改模板
#sql("update")
	UPDATE #(tableName) SET
	#for(x : cond)
    	 #(for.first ? "": ",")#(x.key) #para(x.value)
  	#end
#end

删除模板
#sql("delete")
	DELETE FROM #(tableName) 
	#for(x : cond)
    	#(for.first ? "where": "and") #(x.key) #para(x.value)
  	#end
#end


