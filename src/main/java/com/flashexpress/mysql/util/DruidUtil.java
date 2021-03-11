package com.flashexpress.mysql.util;

import static java.util.Objects.nonNull;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLAlterTableAddColumn;
import com.alibaba.druid.sql.ast.statement.SQLAlterTableItem;
import com.alibaba.druid.sql.ast.statement.SQLAlterTableStatement;
import com.alibaba.druid.sql.ast.statement.SQLColumnDefinition;
import com.alibaba.druid.sql.ast.statement.SQLSelectOrderByItem;
import com.alibaba.druid.sql.ast.statement.SQLTableElement;
import com.alibaba.druid.sql.dialect.mysql.ast.MySqlPrimaryKey;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlCreateTableStatement;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import com.flashexpress.mysql.dto.AlterTableElement;
import com.flashexpress.mysql.dto.ColumnElement;
import com.flashexpress.mysql.dto.CreateTableElement;
import com.flashexpress.mysql.dto.TableElement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

/**
 * @author xufengfeng
 * @date 2021/3/8
 */
@Slf4j
public class DruidUtil {
  public static TableElement getTableElements(String ddlStr) {
    MySqlStatementParser parser = new MySqlStatementParser(ddlStr);
    SQLStatement statement = parser.parseStatement();
    TableElement tableElement = null;
    //CREATE
    if (statement instanceof MySqlCreateTableStatement) {
      ArrayList<ColumnElement> columnElements = new ArrayList<>();
      List<String> tablePK = null;
      MySqlCreateTableStatement createTableStatement = (MySqlCreateTableStatement) statement;
      List<SQLTableElement> tableElementList = createTableStatement.getTableElementList();
      for (SQLTableElement element : tableElementList) {
        // 单独定义的主键，PRIMARY KEY (name，id)
        if (element instanceof MySqlPrimaryKey) {
          List<SQLSelectOrderByItem> pkList = ((MySqlPrimaryKey) element).getColumns();
          tablePK = pkList.stream().map(pk -> pk.getExpr().toString()).collect(Collectors.toList());
        }
        if (element instanceof SQLColumnDefinition) {
          ColumnElement columnElement = new ColumnElement();
          SQLColumnDefinition columnDefinition = (SQLColumnDefinition) element;
          String columnName = columnDefinition.getColumnName();
          String columnType = columnDefinition.getDataType().getName();
          List<SQLExpr> arguments = columnDefinition.getDataType().getArguments();
          log.info("columnName:{},columnType:{},length:{}", columnName, columnType, arguments.toString());
          String length = arguments.size() > 0 ? arguments.get(0).toString() : null;
          String columnComment = nonNull(columnDefinition.getComment()) ? columnDefinition.getComment().toString() : "";
          columnElement.setName(columnName);
          columnElement.setColumnType(columnType);
          columnElement.setLength(length);
          columnElement.setDefaultValue(nonNull(columnDefinition.getDefaultExpr()) ? columnDefinition.getDefaultExpr().toString() : null);
          columnElement.setComment(columnComment);
          columnElements.add(columnElement);
        }
      }
      String tableName = createTableStatement.getName().toString();
      String tableComment = createTableStatement.getComment().toString();
      tableElement = new CreateTableElement(tableName, columnElements, tablePK, tableComment);
    }
    //ALTER
    if (statement instanceof SQLAlterTableStatement) {
      List<ColumnElement> columnElements = new ArrayList<>();
      SQLAlterTableStatement alterTableStatement = (SQLAlterTableStatement) statement;
      String tableName = alterTableStatement.getTableName();
      List<SQLAlterTableItem> items = alterTableStatement.getItems();
      for (SQLAlterTableItem item : items) {
        if (item instanceof SQLAlterTableAddColumn) {
          ((SQLAlterTableAddColumn) item).getColumns().stream().forEach(c -> {
            ColumnElement columnElement = new ColumnElement();
            columnElement.setName(c.getColumnName());
            columnElement.setColumnType(c.getDataType().getName());
            List<SQLExpr> arguments = c.getDataType().getArguments();
            columnElement.setLength(arguments.size() > 0 ? arguments.get(0).toString() : null);
            columnElement.setDefaultValue(nonNull(c.getDefaultExpr()) ? c.getDefaultExpr().toString() : null);
            columnElement.setComment(c.getComment().toString());
            columnElements.add(columnElement);
          });
        }
      }
      tableElement = new AlterTableElement(tableName, columnElements);
    }

    return tableElement;
  }


}
