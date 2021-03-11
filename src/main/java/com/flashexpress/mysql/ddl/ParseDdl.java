package com.flashexpress.mysql.ddl;

import com.flashexpress.mysql.constants.DdlConstants;
import com.flashexpress.mysql.dto.TableElement;
import com.flashexpress.mysql.util.DruidUtil;

/**
 * @author xufengfeng
 * @date 2021/3/8
 */
public class ParseDdl {
  public static void main(String[] args) {
    String ddlStr = DdlConstants.ALTER_DDL;
    TableElement tableElement = DruidUtil.getTableElements(ddlStr);
    System.out.println(tableElement.toString());
  }
}
