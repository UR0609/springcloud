package com.ljryh.client.utils.generation.file;

import com.google.common.base.CaseFormat;
import com.ljryh.client.entity.generate.ColumnData;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * @author ljryh
 * @date 2021/11/15 15:55
 */
public class GenerateVue {

    public void GenerateVue(String parent, String child, List<ColumnData> list) {
        File file = new File(parent, child + ".vue");  //创建文件对象

        try {
            if (!file.exists()) {//如果文件不存在则新建文件
                file.createNewFile();
            }
            FileOutputStream output = new FileOutputStream(file);
            StringBuffer sb = new StringBuffer();
            // 查询
            sb.append("<template>\n" +
                    "  <div class=\"app-container\">\n" +
                    "    <div class=\"filter-container\" style=\"float:left;margin-top: 20px;\">\n");
            for (ColumnData columnData : list) {
                if (columnData.getDataQuery()) {
                    String columnName = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, columnData.getColumnName());
                    sb.append("      <el-input v-model=\"listQuery." + columnName + "\" placeholder=\"" + columnData.getColumnComment() + "\" style=\"width: 200px;\" class=\"filter-item\"\n" +
                            "                @keyup.enter.native=\"handleFilter\" clearable/>\n");
                }
            }
            sb.append("      <el-button class=\"filter-item\" type=\"primary\" icon=\"el-icon-search\" @click=\"handleFilter\">\n" +
                    "        查询\n" +
                    "      </el-button>\n" +
                    "      <el-button class=\"filter-item\" style=\"margin-left: 10px;\" type=\"success\" icon=\"el-icon-edit\"\n" +
                    "                 @click=\"handleCreate\">\n" +
                    "        添加\n" +
                    "      </el-button>\n" +
                    "    </div>\n");

            // 列表
            sb.append("<div>\n" +
                    "      <el-table\n" +
                    "          :key=\"tableKey\"\n" +
                    "          v-loading=\"listLoading\"\n" +
                    "          :data=\"tableData\"\n" +
                    "          border\n" +
                    "          fit\n" +
                    "          highlight-current-row\n" +
                    "          style=\"width: 100%\"\n" +
                    "      >\n");
            for (ColumnData columnData : list) {
                if (columnData.getDataShow()) {
                    if (columnData.getColumnName().equals("id")) {
                        sb.append("        <el-table-column label=\"ID\" prop=\"id\" align=\"center\" width=\"80\" v-if=\"show\">\n" +
                                "          <template slot-scope=\"scope\">\n" +
                                "            <span>{{ scope.row.id }}</span>\n" +
                                "          </template>\n" +
                                "        </el-table-column>\n\n");
                    } else {
                        String columnName = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, columnData.getColumnName());
                        sb.append("        <el-table-column label=\"" + columnData.getColumnComment() + "\" prop=\"" + columnName + "\" align=\"center\" min-width=\"200px\">\n" +
                                "          <template slot-scope=\"scope\">\n" +
                                "            <span>{{ scope.row." + columnName + " }}</span>\n" +
                                "          </template>\n" +
                                "        </el-table-column>\n\n");
                    }
                }
            }
            sb.append("<el-table-column label=\"操作\" align=\"center\" min-width=\"150\">\n" +
                    "          <template slot-scope=\"scope\">\n" +
                    "            <el-button\n" +
                    "                size=\"mini\"\n" +
                    "                @click=\"handleUpdate(scope.$index, scope.row)\">编辑\n" +
                    "            </el-button>\n" +
                    "            <el-button\n" +
                    "                size=\"mini\"\n" +
                    "                type=\"danger\"\n" +
                    "                @click=\"deleteData(scope.$index, scope.row)\">删除\n" +
                    "            </el-button>\n" +
                    "          </template>\n" +
                    "        </el-table-column>\n" +
                    "      </el-table>\n" +
                    "      <pagination v-show=\"total>0\" :total=\"total\" :page.sync=\"listQuery.page\" :limit.sync=\"listQuery.limit\"\n" +
                    "                  @pagination=\"getList\"/>\n" +
                    "    </div>\n");

            // 弹出页
            sb.append("    <el-dialog :title=\"this.titleType\" :visible.sync=\"dialogFormVisible\">\n" +
                    "      <el-form :model=\"dataFrom\" :rules=\"dataRules\" ref=\"dataFrom\" label-position=\"left\" label-width=\"70px\"\n" +
                    "               style=\"width: 400px; margin-left:50px;\"\n>");
            for (ColumnData columnData : list) {
                if (columnData.getDataShow() && !columnData.getColumnName().equals("id")) {
                    String columnName = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, columnData.getColumnName());
                    sb.append("        <el-form-item label=\"" + columnData.getColumnComment() + "\" :label-width=\"formLabelWidth\" prop=\"" + columnName + "\">\n" +
                            "          <el-input v-model=\"dataFrom." + columnName + "\" autocomplete=\"off\"></el-input>\n" +
                            "        </el-form-item>\n");
                }
            }

            sb.append("      </el-form>\n" +
                    "      <div slot=\"footer\" class=\"dialog-footer\">\n" +
                    "        <el-button @click=\"dialogFormVisible = false\">取 消</el-button>\n" +
                    "        <el-button type=\"primary\" @click=\"dialogStatus==='create'?createData():updateData()\">确 定</el-button>\n" +
                    "      </div>\n" +
                    "    </el-dialog>\n" +
                    "  </div>\n" +
                    "</template>\n\n");

            // script
            sb.append("<script>\n" +
                    "import Pagination from \"@/components/Pagination\";\n" +
                    "import {showEntity, showResult} from \"@/utils/show-resutl\";\n" +
                    "import {parseTime} from \"@/utils\";\n" +
                    "import {validEmail, validPhone} from \"@/utils/validate\";\n" +
                    "\n" +
                    "export default {\n" +
                    "  name: \"" + child + "\",\n" +
                    "  components: {Pagination},\n" +
                    "  created() {\n" +
                    "    this.getList();\n" +
                    "  },\n" +
                    "  mounted() {\n" +
                    "  },\n");
            sb.append("  methods: {\n" +
                    "    // 列表加载\n" +
                    "    getList() {\n" +
                    "      this.listLoading = true\n" +
                    "      this.$axios({\n" +
                    "        method: \"POST\",\n" +
                    "        url: this.path + \"/list\",\n" +
                    "        data: {\n" +
                    "          pageNo: this.listQuery.page,\n" +
                    "          pageSize: this.listQuery.limit,\n");
            for (ColumnData columnData : list) {
                if (columnData.getDataQuery()) {
                    String columnName = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, columnData.getColumnName());
                    sb.append("          " + columnName + ": this.listQuery." + columnName + ",");
                }
            }
            sb.append("        },\n" +
                    "      }).then(result => {\n" +
                    "        if (result && result.status == 200) {\n" +
                    "          this.total = result.data.total;\n" +
                    "          this.tableData = result.data.records;\n" +
                    "          setTimeout(() => {\n" +
                    "            this.listLoading = false\n" +
                    "          }, 0.5 * 1000)\n" +
                    "        }\n" +
                    "      });\n" +
                    "    },\n" +
                    "    clearData() {\n" +
                    "      // 清空内容\n");
            for (ColumnData columnData : list) {
                String columnName = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, columnData.getColumnName());
                sb.append("      this.dataFrom." + columnName + " = '';\n");
            }
            sb.append("    },\n" +
                    "    // 新增弹出页\n" +
                    "    handleCreate() {\n" +
                    "      this.titleType = '添加权限';\n" +
                    "      this.dialogStatus = 'create';\n" +
                    "      this.dialogFormVisible = true;\n" +
                    "      this.clearData();\n" +
                    "    },\n" +
                    "    // 新增\n" +
                    "    createData() {\n" +
                    "      this.$refs['dataFrom'].validate((valid) => {\n" +
                    "        if (valid) {\n" +
                    "          this.$axios({\n" +
                    "            method: \"POST\",\n" +
                    "            url: this.path + \"/add\",\n" +
                    "            data: {\n");
            for (ColumnData columnData : list) {
                if (columnData.getDataShow() && !columnData.getColumnName().equals("id")) {
                    String columnName = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, columnData.getColumnName());
                    sb.append("              " + columnName + ": this.dataFrom." + columnName + ",\n");
                }
            }
            sb.append("            },\n" +
                    "          }).then(result => {\n" +
                    "            var judge = showResult(result);\n" +
                    "            if (judge) {\n" +
                    "              this.getList();\n" +
                    "              this.clearData();\n" +
                    "              this.dialogFormVisible = false;\n" +
                    "            }\n" +
                    "          });\n" +
                    "        }\n" +
                    "      })\n" +
                    "    },\n" +
                    "    // 修改弹出页\n" +
                    "    handleUpdate(index, row) {\n" +
                    "      this.$axios({\n" +
                    "        method: \"POST\",\n" +
                    "        url: this.path + \"/sel\",\n" +
                    "        data: {\n" +
                    "          id: row.id\n" +
                    "        },\n" +
                    "      }).then(result => {\n" +
                    "        let entity = showEntity(result);\n" +
                    "        // 隐藏域赋值\n" +
                    "        this.dataFrom.id = row.id;\n");
            for (ColumnData columnData : list) {
                if (columnData.getDataShow() && !columnData.getColumnName().equals("id")) {
                    String columnName = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, columnData.getColumnName());
                    sb.append("        this.dataFrom." + columnName + " = row." + columnName + ";\n");
                }
            }
            sb.append("        // 页面属性更改\n" +
                    "        this.titleType = '修改权限';\n" +
                    "        this.dialogStatus = 'updata';\n" +
                    "        this.dialogFormVisible = true;\n" +
                    "      });\n" +
                    "    },\n" +
                    "    // 修改\n" +
                    "    updateData() {\n" +
                    "      this.$refs['dataFrom'].validate((valid) => {\n" +
                    "        if (valid) {\n" +
                    "          this.$axios({\n" +
                    "            method: \"POST\",\n" +
                    "            url: this.path + \"/mod\",\n" +
                    "            data: {\n" +
                    "              id: this.dataFrom.id,");
            for (ColumnData columnData : list) {
                if (columnData.getDataShow() && !columnData.getColumnName().equals("id")) {
                    String columnName = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, columnData.getColumnName());
                    sb.append("              " + columnName + ": this.dataFrom." + columnName + ",\n");
                }
            }
            sb.append("            },\n" +
                    "          }).then(result => {\n" +
                    "            var judge = showResult(result);\n" +
                    "            if (judge) {\n" +
                    "              this.getList();\n" +
                    "              this.clearData();\n" +
                    "              this.dialogFormVisible = false;\n" +
                    "            }\n" +
                    "          });\n" +
                    "        }\n" +
                    "      })\n" +
                    "    },\n" +
                    "    // 删除\n" +
                    "    deleteData(index, row) {\n" +
                    "      this.$axios({\n" +
                    "        method: \"POST\",\n" +
                    "        url: this.path + \"/del\",\n" +
                    "        data: {\n" +
                    "          id: row.id\n" +
                    "        },\n" +
                    "      }).then(result => {\n" +
                    "        var judge = showResult(result);\n" +
                    "        if (judge) {\n" +
                    "          this.getList();\n" +
                    "          this.clearData();\n" +
                    "        }\n" +
                    "      });\n" +
                    "    },\n" +
                    "    // 查询\n" +
                    "    handleFilter() {\n" +
                    "      this.listQuery.page = 1\n" +
                    "      this.getList()\n" +
                    "    },\n" +
                    "    formatJson(filterVal, jsonData) {\n" +
                    "      return jsonData.map(v => filterVal.map(j => {\n" +
                    "        if (j === 'timestamp') {\n" +
                    "          return parseTime(v[j])\n" +
                    "        } else {\n" +
                    "          return v[j]\n" +
                    "        }\n" +
                    "      }))\n" +
                    "    }\n" +
                    "  },\n\n");

            sb.append("  data() {\n" +
                    "    return {\n" +
                    "      // 请求url\n" +
                    "      path: this.$route.path,\n" +
                    "      // 数据总数\n" +
                    "      total: 0,\n" +
                    "      // 分页组件\n" +
                    "      listQuery: {\n");
            for (ColumnData columnData : list) {
                if (columnData.getDataQuery()) {
                    String columnName = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, columnData.getColumnName());
                    sb.append("        " + columnName + ": '',\n");
                }
            }
            sb.append("        page: 1,\n" +
                    "        limit: 20,\n" +
                    "        sort: '+id'\n" +
                    "      },\n" +
                    "      // 列表延迟加载\n" +
                    "      listLoading: true,\n" +
                    "      tableKey: 0,\n" +
                    "      tableData: null,\n" +
                    "      // 弹出层\n" +
                    "      dialogFormVisible: false,\n" +
                    "      // 弹出页 定义字段长度\n" +
                    "      formLabelWidth: '120px',\n" +
                    "      // 提交到后台的url\n" +
                    "      dialogStatus: '',\n" +
                    "      // 弹出页的标题\n" +
                    "      titleType: '',\n" +
                    "      // 提交到后台的表单\n" +
                    "      dataFrom: {\n" +
                    "        id: '',\n");
            for (ColumnData columnData : list) {
                if (columnData.getDataShow() && !columnData.getColumnName().equals("id")) {
                    String columnName = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, columnData.getColumnName());
                    sb.append("        "+columnName+": '',\n");
                }
            }
            sb.append("      },\n" +
                    "      // 隐藏列用\n" +
                    "      show: false,\n" +
                    "      downloadLoading: false,\n" +
                    "    }\n" +
                    "  }\n" +
                    "}\n" +
                    "</script>\n" +
                    "\n" +
                    "<style scoped>\n" +
                    "\n" +
                    "</style>");
            byte[] bodybytes = sb.toString().getBytes();
            output.write(bodybytes);//信息数组写入文件中
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
