package com.flashexpress.mysql.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author xufengfeng
 * @date 2021/3/9
 */
@Getter
@Setter
@ToString
public class ColumnElement {
  private String name;
  private String columnType;
  private String length;
  private String defaultValue;
  private String comment;
}
