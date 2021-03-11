package com.flashexpress.mysql.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * @author xufengfeng
 * @date 2021/3/11
 */
@Setter
@Getter
public class CreateTableElement extends TableElement {
  private List<String> tablePK;
  private String tableComment;

  public CreateTableElement(String tableName, List<ColumnElement> columns, List<String> tablePK, String tableComment) {
    super(tableName, columns);
    this.tablePK = tablePK;
    this.tableComment = tableComment;
  }

  @Override
  public String toString() {
    return "CreateTableElement{" +
        "tablePK=" + tablePK +
        ", tableComment=" + tableComment + "} " + super.toString();
  }
}
