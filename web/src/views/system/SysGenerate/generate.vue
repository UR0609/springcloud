<template>
  <div class="app-container">
    <div class="filter-container" style="float:left;margin-top: 20px;">
      <el-input v-model="listQuery.tableName" placeholder="表名称" style="width: 200px;" class="filter-item"
                @keyup.enter.native="handleFilter" clearable/>

      <el-button class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">
        查询
      </el-button>
    </div>
    <div>
      <el-table
          :key="tableKey"
          v-loading="listLoading"
          :data="tableData"
          border
          fit
          highlight-current-row
          style="width: 100%"
      >

        <el-table-column label="表名" prop="id" align="center" min-width="200px">
          <template slot-scope="scope">
            <span>{{ scope.row.tableName }}</span>
          </template>
        </el-table-column>

        <el-table-column label="数据行数" prop="id" align="center" min-width="200px">
          <template slot-scope="scope">
            <span>{{ scope.row.tableRows }}</span>
          </template>
        </el-table-column>

        <el-table-column label="索引长度" prop="id" align="center" min-width="200px" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <span>{{ scope.row.indexLength }}</span>
          </template>
        </el-table-column>

        <el-table-column label="自增主键" prop="id" align="center" min-width="200px" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <span>{{ scope.row.autoIncrement }}</span>
          </template>
        </el-table-column>

        <el-table-column label="备注" prop="id" align="center" min-width="200px" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <span>{{ scope.row.tableComment }}</span>
          </template>
        </el-table-column>

        <el-table-column label="编码集" prop="id" align="center" min-width="200px" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <span>{{ scope.row.tableCollation }}</span>
          </template>
        </el-table-column>

        <el-table-column label="创建时间" prop="id" align="center" min-width="200px">
          <template slot-scope="scope">
            <span>{{ scope.row.createTime }}</span>
          </template>
        </el-table-column>

        <el-table-column label="操作" align="center" min-width="150">
          <template slot-scope="scope">
            <el-button
                size="mini"
                @click="handleGeneration(scope.$index, scope.row)">查看
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <!--      <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit"-->
      <!--                  @pagination="getList"/>-->
    </div>


    <!--  添加或修改 弹出页  -->
    <el-dialog :title="this.titleType" :visible.sync="dialogFormVisible">
      <el-table
          :key="columnKey"
          :data="columnData"
          border
          fit
          highlight-current-row
          style="width: 100%"
      >
        <el-table-column label="字段名称" prop="id" align="center" min-width="150px">
          <template slot-scope="scope">
            <span>{{ scope.row.columnName }}</span>
          </template>
        </el-table-column>
        <el-table-column label="字段类型" prop="id" align="center" min-width="150px">
          <template slot-scope="scope">
            <span>{{ scope.row.columnType }}</span>
          </template>
        </el-table-column>
        <el-table-column label="备注" prop="id" align="center" min-width="150px">
          <template slot-scope="scope">
            <span>{{ scope.row.columnComment }}</span>
          </template>
        </el-table-column>
        <el-table-column label="是否显示" prop="id" align="center" min-width="150px">
          <template slot-scope="scope">
            <el-switch
                v-model="scope.row.dataShow ">
              <!--                active-color="#13ce66"-->
              <!--                inactive-color="#ff4949">-->
            </el-switch>
          </template>
        </el-table-column>
        <el-table-column label="是否查询" prop="id" align="center" min-width="150px">
          <template slot-scope="scope">
            <el-switch
                v-model="scope.row.dataQuery ">
              <!--                active-color="#13ce66"-->
              <!--                inactive-color="#ff4949">-->
            </el-switch>
          </template>
        </el-table-column>
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <!--        <el-button type="primary" @click="dialogFormVisible = false">确 定</el-button>-->
        <el-button type="primary" @click="generateCode()">生 成</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
// import Pagination from "@/components/Pagination";
import {showEntity} from "@/utils/show-resutl";
import {parseTime} from "@/utils";

export default {
  name: "SysGenerate",
  // components: {Pagination},
  created() {
    // 在模板渲染成html前调用
    this.getList();
  },
  mounted() {
    // 在模板渲染成html后调用
  },
  methods: {
    // 列表加载
    getList() {
      this.listLoading = true
      this.$axios({
        method: "POST",
        url: this.path + "/list",
        data: {
          tableName: this.listQuery.tableName,
        },
      }).then(result => {
        if (result && result.status == 200) {
          this.tableData = result.data;
          setTimeout(() => {
            this.listLoading = false
          }, 0.5 * 1000)
        }
      });
    },
    clearData() {
      // 清空内容
      this.dataFrom.id = '';
      this.dataFrom.permissionName = '';
      this.dataFrom.permission = '';
      this.dataFrom.sort = '';
      this.dataFrom.remarks = '';
    },
    // 新增弹出页
    handleCreate() {
      this.titleType = '添加权限';
      this.dialogStatus = 'create';
      this.dialogFormVisible = true;
    },
    // 新增
    generateCode() {
      // generate

      console.log(this.columnData)
      this.$axios({
        method: "POST",
        url: this.path + "/generateCode",
        data: {
          tableName:this.dataFrom.tableName,
          data: this.columnData
        },
      }).then(result => {
        if (result && result.status == 200) {
          return;
        }
      });
    },
    // 查看表内字段
    handleGeneration(index, row) {
      this.dataFrom.tableName = row.tableName;
      // alert(row.tableName);
      this.$axios({
        method: "POST",
        url: this.path + "/sel",
        data: {
          tableName: row.tableName
        },
      }).then(result => {
        this.columnData = showEntity(result);
        console.log(this.columnData);
        this.dialogFormVisible = true;
      });
    },
    // 查询
    handleFilter() {
      this.getList()
    },
    formatJson(filterVal, jsonData) {
      return jsonData.map(v => filterVal.map(j => {
        if (j === 'timestamp') {
          return parseTime(v[j])
        } else {
          return v[j]
        }
      }))
    }
  },
  data() {
    return {
      // 规则验证
      dataRules: {
        permissionName: [
          {required: true, message: "请填写按钮类型", trigger: 'blur'},
          {min: 1, max: 10, message: '长度在 1 到 10 个字符', trigger: 'blur'}
        ],
        permission: [
          {required: true, message: "请填写允许名称", trigger: 'blur'},
          {min: 1, max: 10, message: '长度在 1 到 10 个字符', trigger: 'blur'}
        ],
        sort: [
          {type: 'number', message: '数量必须为数字值', trigger: 'blur'}
        ],
        remarks: [
          {max: 18, message: '长度小于 50 个字符', trigger: 'blur'}
        ]
      },
      // 请求url
      path: this.$route.path,
      // 数据总数
      // 分页组件
      listQuery: {
        tableName: '',
        page: 1,
        limit: 20,
        importance: undefined,
        title: undefined,
        type: undefined,
        sort: '+id'
      },
      // 列表延迟加载
      listLoading: true,
      tableKey: 0,
      tableData: null,
      columnKey: 0,
      columnData: null,
      // 弹出层
      dialogFormVisible: false,
      // 弹出页 定义字段长度
      formLabelWidth: '120px',
      // 提交到后台的url
      dialogStatus: '',
      // 弹出页的标题
      titleType: '',
      // 提交到后台的表单
      dataFrom: {
        id: '',
        permissionName: '',
        permission: '',
        sort: '',
        remarks: '',
        tableName: '',
      },
      // 隐藏列用
      show: false,

      downloadLoading: false,
    }
  }
}
</script>

<style scoped>

</style>
