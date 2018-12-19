package fr.provenzano.webemul.domain.util;

import java.sql.Types;

import org.hibernate.dialect.H2Dialect;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.StandardBasicTypes;

public class FixedH2Dialect extends H2Dialect {

    public FixedH2Dialect() {
        super();
        registerColumnType(Types.FLOAT, "real");
        registerFunction("date", new StandardSQLFunction("truncate", StandardBasicTypes.DATE));
    }
}
