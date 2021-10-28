/**
 * Copyright (C) <2021>  <chen junwen>
 * <p>
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License along with this program.  If
 * not, see <http://www.gnu.org/licenses/>.
 */
package io.mycat.sqlhandler.dql;

import com.alibaba.druid.sql.ast.statement.SQLShowColumnsStatement;
import io.mycat.MetaClusterCurrent;
import io.mycat.MycatDataContext;
import io.mycat.api.collector.RowBaseIterator;
import io.mycat.prototypeserver.mysql.MySQLResultSet;
import io.mycat.prototypeserver.mysql.PrototypeService;
import io.mycat.sqlhandler.AbstractSQLHandler;
import io.mycat.sqlhandler.SQLRequest;
import io.mycat.Response;
import io.vertx.core.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;


/**
 * @ chenjunwen
 */

public class ShowColumnsSQLHandler extends AbstractSQLHandler<SQLShowColumnsStatement> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShowColumnsSQLHandler.class);
    @Override
    protected Future<Void> onExecute(SQLRequest<SQLShowColumnsStatement> request, MycatDataContext dataContext, Response response){
        PrototypeService prototypeService = getPrototypeService();
        Optional<MySQLResultSet> baseIterator = prototypeService.handleSql(request.getAst());
        if(baseIterator.isPresent()){
            RowBaseIterator rowBaseIterator = baseIterator.get().build();
            return response.sendResultSet(rowBaseIterator);
        }
        SQLShowColumnsStatement ast = request.getAst();
        return response.proxySelectToPrototype(ast.toString());
    }
}
