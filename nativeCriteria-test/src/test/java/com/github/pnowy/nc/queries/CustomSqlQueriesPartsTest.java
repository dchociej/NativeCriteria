package com.github.pnowy.nc.queries;

import com.github.pnowy.nc.AbstractDbTest;
import com.github.pnowy.nc.core.CriteriaResult;
import com.github.pnowy.nc.core.NativeCriteria;
import com.github.pnowy.nc.core.NativeExps;
import com.github.pnowy.nc.sqlserver.SqlServerCustomOrderTest;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test which demonstrate usage of custom SQL parts for native criteria.
 */
public class CustomSqlQueriesPartsTest extends AbstractDbTest {

    private static final Logger log = LoggerFactory.getLogger(SqlServerCustomOrderTest.class);

    @Test
    public void shouldUseCustomWhere() throws Exception {
        NativeCriteria nc = createNativeCriteria("ADDRESS", "a");
        nc.add(NativeExps.customSql("a.city=:city", "city", "Warsaw"));
        CriteriaResult result = nc.criteriaResult();
        while (result.next()) {
            String currentRecordDesc = result.getCurrentRecordDesc();
            log.info(currentRecordDesc);
        }
        assertThat(result.getRowsNumber()).isEqualTo(2);
        log.info("{}", nc.getQueryInfo());
    }

    @Test
    public void shouldUseCustomJoinQuery() throws Exception {
        NativeCriteria nc = createSpringNativeCriteria("SUPPLIER", "s");
        nc.addJoin(NativeExps.customSql("JOIN ADDRESS a ON a.supplier_id=s.id"));
        CriteriaResult result = nc.criteriaResult();
        while (result.next()) {
            String currentRecordDesc = result.getCurrentRecordDesc();
            log.info(currentRecordDesc);
        }
        assertThat(result.getRowsNumber()).isGreaterThan(0);
    }

    @Test
    public void shouldUseCustomProjection() throws Exception {
        NativeCriteria nc = createSpringNativeCriteria("ADDRESS", "a");
        nc.setProjection(NativeExps.projection().addProjection("a.street", "street").addProjection(NativeExps.customSql("a.city as city")));

        nc.criteriaResult();
        String sql = nc.getQueryInfo().getSql();
        assertThat(sql).contains("a.city as city");
    }

    @Test
    public void shouldUseCustomOrder() throws Exception {
        NativeCriteria nc = createSpringNativeCriteria("ADDRESS", "a");
        nc.setOrder(NativeExps.customSql("ORDER BY a.id"));
        nc.criteriaResult();
        String sql = nc.getQueryInfo().getSql();
        assertThat(sql).contains("ORDER BY a.id");
    }
}
