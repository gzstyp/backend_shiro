package com.yinlz.datasource;

import com.yinlz.datasource.DataSourceType.DataBaseType;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

/*

spring execution 表达式详解 https://blog.csdn.net/z3881006/article/details/79020166

在使用spring框架配置AOP的时候，不管是通过XML配置文件还是注解的方式都需要定义pointcut"切入点"
例如定义切入点表达式 execution(* com.sample.service.impl..*.*(..))
execution()是最常用的切点函数，其语法如下所示：
整个表达式可以分为五个部分：
1、execution(): 表达式主体。
2、第一个*号：表示返回类型，*号表示所有的类型。
3、包名：表示需要拦截的包名，后面的两个句点表示当前包和当前包的所有子包，com.sample.service.impl包、子孙包下所有类的方法。
4、第二个*号：表示类名，*号表示所有的类。
5、*(..):最后这个星号表示方法名，*号表示所有的方法，后面括弧里面表示方法的参数，两个句点表示任何参数。

表达式支持匹配多个  https://blog.csdn.net/qq_41684939/article/details/97396344
如 : "execution(* com.ws..*.*(*)) || execution(* com.db..*.*(*))" ;

*/

@Aspect
@Component
public class DataSourceAop {

    @Before("execution(* com.yinlz.dao..*.execute(..))")
	public void setDataSourceWrite(){
		DataSourceType.setDataBaseType(DataBaseType.Write);
	}
	
    @Before("execution(* com.yinlz.dao..*.query*(..))")
	public void setDataSourceSlave() {
        final int random = ThreadLocalRandom.current().nextInt(2);//0,1的值
        switch (random){
            case 0:
                DataSourceType.setDataBaseType(DataBaseType.Slave1);
                break;
            case 1:
                DataSourceType.setDataBaseType(DataBaseType.Slave2);
                break;
            default:
                DataSourceType.setDataBaseType(DataBaseType.Slave2);
                break;
        }
	}
}