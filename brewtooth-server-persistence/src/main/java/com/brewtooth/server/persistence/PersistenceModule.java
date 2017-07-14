package com.brewtooth.server.persistence;

import com.brewtoot.server.core.domain.Hop;
import com.brewtoot.server.core.domain.Malt;
import com.brewtoot.server.core.domain.Yeast;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.mybatis.guice.MyBatisModule;

import javax.sql.DataSource;

public abstract class PersistenceModule extends MyBatisModule {

    @Override
    protected void initialize() {
        environmentId("brew-tooth");
        bindTransactionFactoryType(JdbcTransactionFactory.class);
        bindDataSourceProvider(this::configureDataSource);
        addMappers();
        addTypeHandlers();
        addSimpleAliases();
        addServices();
    }

    private void addSimpleAliases() {
        addSimpleAlias(Hop.class);
        addSimpleAlias(Malt.class);
        addSimpleAlias(Yeast.class);
    }

    private void addMappers() {
    }

    protected abstract DataSource configureDataSource();

    private void addTypeHandlers() {
    }

    private void addServices() {
    }
}
