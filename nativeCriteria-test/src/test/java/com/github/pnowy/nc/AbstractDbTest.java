package com.github.pnowy.nc;


import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.github.pnowy.nc.core.NativeCriteria;
import com.github.pnowy.nc.core.jpa.JpaQueryProvider;
import com.github.pnowy.nc.spring.SpringNativeCriteria;

@DataJpaTest
@Tag("DefaultGroup")
@ExtendWith(SpringExtension.class)
@Transactional(readOnly = true)
public abstract class AbstractDbTest {

    static final String PROFILE_POSTGRESQL = "postgresql";
    static final String PROFILE_SQLSERVER = "sqlserver";
    static final String PROFILE_MYSQL = "mysql";

    @Autowired
    protected TestEntityManager entityManager;

    protected NativeCriteria createNativeCriteria(String tableName, String tableAlias) {
        return new NativeCriteria(new JpaQueryProvider(entityManager.getEntityManager()), tableName, tableAlias);
    }

    protected SpringNativeCriteria createSpringNativeCriteria(String tableName, String tableAlias) {
        return new SpringNativeCriteria(new JpaQueryProvider(entityManager.getEntityManager()), tableName, tableAlias);
    }

}
