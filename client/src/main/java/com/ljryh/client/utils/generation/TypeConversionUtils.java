package com.ljryh.client.utils.generation;

import com.ljryh.client.entity.generate.ColumnData;

import java.util.List;

/**
 * @author ljryh
 * @date 2021/10/23 14:07
 */
public class TypeConversionUtils {

    private static final String LONG_TYPE = "Long";
    private static final String STRING_TYPE = "String";
    private static final String INTEGER_TYPE = "Integer";
    private static final String BYTE_TYPE = "Byte";
    private static final String SHORT_TYPE = "Short";
    private static final String DATE_TYPE = "LocalDateTime";
    private static final String FLOAT_TYPE = "Float";
    private static final String DOUBLE_TYPE = "Double";

    public static List<ColumnData> TypeConversion(List<ColumnData> list) {

        for(ColumnData columnData : list){
            columnData.setColumnType(dataTypeJudge(columnData.getColumnType()));
        }

        return list;
    }

    private static String dataTypeJudge(String s) {
        String str = new String();
        switch (s) {
            case "int":
                str = INTEGER_TYPE;
                break;
            case "bigint":
            case "decimal":
            case "numeric":
                str = LONG_TYPE;
                break;
            case "varchar":
            case "char":
                str = STRING_TYPE;
                break;
            case "double":
                str = DOUBLE_TYPE;
                break;
            case "bit":
            case "tinyint":
                str = BYTE_TYPE;
                break;
            case "smallint":
                str = SHORT_TYPE;
                break;
            case "float":
                str = FLOAT_TYPE;
                break;
            case "date":
            case "time":
            case "datetime":
            case "timestamp":
                str = DATE_TYPE;
                break;
            default:
                System.out.println(s);
                str = STRING_TYPE;
        }
        return str;
    }

}
