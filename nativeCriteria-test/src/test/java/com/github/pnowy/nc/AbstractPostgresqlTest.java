package com.github.pnowy.nc;

import org.junit.jupiter.api.Tag;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

/**
 * Base class for Postgresql tests.
 */
@Tag("PostgresqlGroup")
@ActiveProfiles(AbstractDbTest.PROFILE_POSTGRESQL)
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public abstract class AbstractPostgresqlTest extends AbstractDbTest {}
