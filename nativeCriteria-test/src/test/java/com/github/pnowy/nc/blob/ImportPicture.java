package com.github.pnowy.nc.blob;

import liquibase.change.custom.CustomSqlChange;
import liquibase.database.Database;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.CustomChangeException;
import liquibase.exception.SetupException;
import liquibase.exception.ValidationErrors;
import liquibase.resource.ResourceAccessor;
import liquibase.statement.SqlStatement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;

/**
 * Import picture to database.
 */
public class ImportPicture implements CustomSqlChange {
    private static final Logger log = LoggerFactory.getLogger(ImportPicture.class);

    private InputStream png;

    @Override
    public SqlStatement[] generateStatements(Database database) throws CustomChangeException {
        JdbcConnection connection = (JdbcConnection) database.getConnection();
        final String sql = "INSERT INTO IMAGE(ID, NAME, CONTENT) VALUES (?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, 1L);
            ps.setString(2, "pn.png");
            ps.setBlob(3, png);
            ps.executeUpdate();
        } catch (Exception e) {
            log.error("", e);
            throw new CustomChangeException("Cannot insert image data!", e);
        } finally {
            try {
                png.close();
            } catch (Exception ignore) {}
        }
        return new SqlStatement[0];
    }

    @Override
    public void setFileOpener(ResourceAccessor resourceAccessor) {
        try {
            png = resourceAccessor.get("liquibase/data/pn.png").openInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getConfirmationMessage() {
        return null;
    }

    @Override
    public void setUp() throws SetupException {

    }

    @Override
    public ValidationErrors validate(Database database) {
        return null;
    }

}
