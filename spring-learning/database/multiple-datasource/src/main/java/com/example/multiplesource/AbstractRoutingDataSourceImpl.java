package com.example.multiplesource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.Map;
@Slf4j
public class AbstractRoutingDataSourceImpl extends AbstractRoutingDataSource {
    private static final ThreadLocal<String> DATABASE_NAME = new ThreadLocal<>();
    public AbstractRoutingDataSourceImpl(DataSource defaultTargetDatasource, Map<Object,Object> targetDatasources) {
        super.setDefaultTargetDataSource(defaultTargetDatasource);
        super.setTargetDataSources(targetDatasources);
        super.afterPropertiesSet();
    }
    public static void setDatabaseName(String key) {DATABASE_NAME.set(key);}
    public static String getDatabaseName() {
        return DATABASE_NAME.get();
    }
    public static void removeDatabaseName() {
        DATABASE_NAME.remove();
    }

    @Override
    protected Object determineCurrentLookupKey() {
        logger.info("----switch to another db---" + DATABASE_NAME.get());
        return DATABASE_NAME.get();
    }
}
