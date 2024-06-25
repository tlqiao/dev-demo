package com.example.multiplesource;

import com.example.multiplesource.config.SwitchDataSource;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.reflect.Method;

public class DataSourceAspect {
    //通过切面编程实现不同数据库的切换
    private final Logger logger = LoggerFactory.getLogger(getClass());
    //defining where the jointpoint need to be applied
    @Pointcut("@annotation(com.example.multiplesource.config.SwitchDataSource)")
    public void annotationPointCut() {
    }
    // setting the lookup key using the annotation passed value
    @Before("annotationPointCut()")
    public void before(JoinPoint joinPoint){
        MethodSignature sign =  (MethodSignature)joinPoint.getSignature();
        Method method = sign.getMethod();
        SwitchDataSource annotation = method.getAnnotation(SwitchDataSource.class);
        if(annotation != null){
            AbstractRoutingDataSourceImpl.setDatabaseName(annotation.value());
            logger.info("Switch DataSource to [{}] in Method [{}]",
                    annotation.value(), joinPoint.getSignature());
        }
    }
    // restoring to default datasource after the execution of the method
    @After("annotationPointCut()")
    public void after(JoinPoint point){
        if(null != AbstractRoutingDataSourceImpl.getDatabaseName()) {
            AbstractRoutingDataSourceImpl.removeDatabaseName();
        }
    }
}
