package com.lostad.app.demo.util.vdll.data.msql;

import java.lang.annotation.*;
import static java.lang.annotation.ElementType.*;

//@Documented
@Retention(RetentionPolicy.RUNTIME) //提供反射
@Target(value = { CONSTRUCTOR, FIELD, LOCAL_VARIABLE, METHOD, PACKAGE, PARAMETER, TYPE }) //此处成为元注解 ，注解的定义需要元注解
public @interface AITag
{
	//此处需要注意写法
	public int id() default 0;

	//value 缺省字段
	public String value() default "varchar(100)";  //主要标注

	public String vsql() default "";  //特殊类型

	public String vsqlValue() default VSTag._PK;  //复合特殊类型
	
	public String vsComment() default "";  //复合特殊类型

}

/*
	public int id() default 0;
	public String value() default "varchar(100)";
	@ITag(id = 3, value = "varchar(100)")
*/