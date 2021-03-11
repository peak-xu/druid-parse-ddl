package com.flashexpress.mysql.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author xufengfeng
 * @date 2021/3/11
 */
@Getter
@Setter
public class AlterTableElement extends TableElement {
  public AlterTableElement(String tableName, List<ColumnElement> columns) {
    super(tableName, columns);
  }

  @Override
  public String toString() {
    return super.toString();
  }
}
