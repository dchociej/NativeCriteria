package com.github.pnowy.nc;

import org.junit.jupiter.api.Tag;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

/**
 * Base class for MySQL tests.
 */
@Tag("MySqlGroup")
@ActiveProfiles(AbstractDbTest.PROFILE_MYSQL)
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public abstract class AbstractMySqlTest extends AbstractDbTest {}
