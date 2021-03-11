package com.flashexpress.mysql.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author xufengfeng
 * @date 2021/3/8
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
public class TableElement {
  private String tableName;
  private List<ColumnElement> columns;
}
