<template>
  <div class="app-container">
    <div class="filter-container" style="float:left;margin-top: 20px;">
      <el-input v-model="listQuery.name" placeholder="名称" style="width: 100px;" class="filter-item"
                @keyup.enter.native="handleFilter" clearable/>

      <el-button class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">
        查询
      </el-button>
      <!--      <el-button :loading="downloadLoading" class="filter-item" type="warning" icon="el-icon-download"-->
      <!--                 @click="handleDownload">-->
      <!--        导出-->
      <!--      </el-button>-->
      <el-button class="filter-item" style="margin-left: 10px;" type="success" icon="el-icon-edit"
                 @click="handleCreate">
        添加
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
        <el-table-column label="ID" prop="id" align="center" width="80" v-if="show">
          <template slot-scope="scope">
            <span>{{ scope.row.id }}</span>
          </template>
        </el-table-column>

        <el-table-column label="名称" prop="id" align="center" min-width="100px">
          <template slot-scope="scope">
            <span>{{ scope.row.name }}</span>
          </template>
        </el-table-column>

        <el-table-column label="表名" prop="id" align="center" min-width="100px">
          <template slot-scope="scope">
            <span>{{ scope.row.tableName }}</span>
          </template>
        </el-table-column>

        <el-table-column label="初始化状态" prop="id" align="center" min-width="100px">
          <template slot-scope="scope">
            <span>
              <!-- 判断 initType 如果 0：未初始，1：初始化，2：更新-->
              {{ scope.row.initType == 0 ? '未初始' : scope.row.initType == 1 ? '初始化' : '更新' }}
            </span>
          </template>
        </el-table-column>

        <el-table-column label="使用状态" prop="id" align="center" min-width="100px">
          <template slot-scope="scope">
            <!-- 判断 useType 如果 0：未使用，1：已使用-->
            <span>{{ scope.row.useType == 0 ? '未使用' : '已使用' }}</span>
          </template>
        </el-table-column>

        <el-table-column label="排序" prop="id" align="center" min-width="100px">
          <template slot-scope="scope">
            <span>{{ scope.row.sort }}</span>
          </template>
        </el-table-column>

        <el-table-column label="备注" prop="id" align="center" min-width="100px">
          <template slot-scope="scope">
            <span>{{ scope.row.remarks }}</span>
          </template>
        </el-table-column>

        <el-table-column label="操作" align="center" min-width="150px">
          <template slot-scope="scope">
<!--            绿色按钮-->

            <el-button
                size="mini"
                type="success"
                @click="databaseFieldDesign(scope.$index, scope.row)">设计
            </el-button>
            <el-button
                size="mini"
                type="warning"
                @click="databaseFieldGenerate(scope.$index, scope.row)">生成
            </el-button>
            <el-button
                size="mini"
                @click="handleUpdate(scope.$index, scope.row)">编辑
            </el-button>
            <el-button
                size="mini"
                type="danger"
                @click="deleteData(scope.$index, scope.row)">删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit"
                  @pagination="getList"/>
    </div>


    <!--  添加或修改 弹出页  -->
    <el-dialog :title="this.titleType" :visible.sync="dialogFormVisible">
      <el-form :model="dataFrom" :rules="dataRules" ref="dataFrom" label-position="left" label-width="70px"
               style="width: 400px; margin-left:50px;">
        <el-form-item label="名称" :label-width="formLabelWidth" prop="name">
          <el-input v-model="dataFrom.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="表名" :label-width="formLabelWidth" prop="tableName">
          <el-input v-model="dataFrom.tableName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="排序" :label-width="formLabelWidth" prop="sort">
          <el-input v-model="dataFrom.sort" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="备注" :label-width="formLabelWidth">
          <el-input type="textarea" :rows="4" v-model="dataFrom.remarks" maxlength="200" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <!--        <el-button type="primary" @click="dialogFormVisible = false">确 定</el-button>-->
        <el-button type="primary" @click="dialogStatus==='create'?createData():updateData()">确 定</el-button>
      </div>
    </el-dialog>

    <!--  字段设计弹出层  -->
    <el-dialog :title="this.titleType" :visible.sync="fieldDesignVisible"  style="overflow-x: hidden;width: 90%;height: 80%;">
      <el-table style="width: 100%" border :data="tableDataa">
        <template v-for="(item,index) in tableHead">
          <el-table-column :prop="item.column_name" :label="item.column_comment" :key="index" v-if="item.column_name != 'id'"></el-table-column>
        </template>
      </el-table>
    </el-dialog>

  </div>
</template>

<script>
import {parseTime} from '@/utils'
import {showEntity, showResult} from '@/utils/show-resutl';
import Pagination from '@/components/Pagination'
// import {Message} from "element-ui"; // secondary package based on el-pagination

export default {
  name: "SysDatabase",
  components: {Pagination},
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
          name: this.listQuery.name == "" ? null : this.listQuery.name,
          pageNo: this.listQuery.page,
          pageSize: this.listQuery.limit
        },
      }).then(result => {
        if (result && result.status == 200) {
          this.total = result.data.total;
          this.tableData = result.data.records;
          setTimeout(() => {
            this.listLoading = false
          }, 0.5 * 1000)
        }
      });
    },
    clearData() {
      // 清空内容
      this.dataFrom.id = '';
      this.dataFrom.name = '';
      this.dataFrom.sort = '';
      this.dataFrom.tableName = '';
      this.dataFrom.initType = '';
      this.dataFrom.useType = '';
      this.dataFrom.remarks = '';
    },
    // 新增弹出页
    handleCreate() {
      this.titleType = '添加数据库';
      this.dialogStatus = 'create';
      this.dialogFormVisible = true;
    },
    // 新增
    createData() {
      this.$refs['dataFrom'].validate((valid) => {
        if (valid) {
          this.$axios({
            method: "POST",
            url: this.path + "/add",
            data: {
              name: this.dataFrom.name,
              sort: this.dataFrom.sort,
              tableName: this.dataFrom.tableName,
              // initType: this.dataFrom.initType,
              // useType: this.dataFrom.useType,
              remarks: this.dataFrom.remarks,
            },
          }).then(result => {
            var judge = showResult(result);
            if (judge) {
              this.getList();
              this.clearData();
              this.dialogFormVisible = false;
            }
          });
        }
      })
    },
    databaseFieldGenerate(index, row) {
      this.$axios({
        method: "POST",
        url: this.path + "/createTable",
        data: {
          id: row.id
        },
      }).then(result => {
        var judge = showResult(result);
        if (judge) {
          this.getList();
        }
      });
    },
    databaseFieldDesign(index, row) {
      console.log(row.id);
      this.fieldDesignVisible = true;
    },
    // 修改弹出页
    handleUpdate(index, row) {
      this.$axios({
        method: "POST",
        url: this.path + "/sel",
        data: {
          id: row.id
        },
      }).then(result => {
        var entity = showEntity(result);
        // 隐藏域赋值
        this.dataFrom.id = row.id;
        this.dataFrom.name = entity.name;
        this.dataFrom.tableName = entity.tableName;
        // this.dataFrom.initType = entity.initType;
        // this.dataFrom.useType = entity.useType;
        this.dataFrom.sort = entity.sort;
        this.dataFrom.remarks = entity.remarks;
        // 页面属性更改
        this.titleType = '修改数据库';
        this.dialogStatus = 'updata';
        this.dialogFormVisible = true;
      });
    },
    // 修改
    updateData() {
      this.$refs['dataFrom'].validate((valid) => {
        if (valid) {
          this.$axios({
            method: "POST",
            url: this.path + "/mod",
            data: {
              id: this.dataFrom.id,
              name: this.dataFrom.name,
              tableName: this.dataFrom.tableName,
              // initType: this.dataFrom.initType,
              // useType: this.dataFrom.useType,
              sort: this.dataFrom.sort,
              remarks: this.dataFrom.remarks,
            },
          }).then(result => {
            var judge = showResult(result);
            if (judge) {
              this.getList();
              this.clearData();
              this.dialogFormVisible = false;
            }
          });
        }
      })
    },
    // 删除
    deleteData(index, row) {
      this.$axios({
        method: "POST",
        url: this.path + "/del",
        data: {
          id: row.id
        },
      }).then(result => {
        var judge = showResult(result);
        if (judge) {
          this.getList();
          this.clearData();
        }
      });
    },
    // 查询
    handleFilter() {
      this.listQuery.page = 1
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
    },
  },

  data() {

    return {


// 表格数据
      tableDataa: [{
        column_age: '3',
        column_name: '鞠婧祎',
        column_sex: '女'
      },
        {
          column_age: '25',
          column_name: '魏大勋',
          column_sex: '男'
        },
        {
          column_age: '18',
          column_name: '关晓彤',
          column_sex: '女'
        }],
//javascript
// 表头数据
      tableHead:[
        {
          column_name: "column_name",column_comment:"姓名"
        },
        {
          column_name: "column_age",column_comment:"年龄"
        },
        {
          column_name: "column_sex",column_comment:"性别"
        }
      ],
      // 规则验证
      dataRules: {
        name: [
          {required: true, message: "请填写姓名", trigger: 'blur'},
          {min: 2, max: 18, message: '长度在 2 到 18 个字符', trigger: 'blur'}
        ],
        tableName: [
          {required: true, message: "请填表名", trigger: 'blur'},
          {min: 1, max: 18, message: '长度在 1 到 18 个字符', trigger: 'blur'}
        ],
      },
      // 请求url
      path: this.$route.path,
      // 数据总数
      total: 0,
      // 分页组件
      listQuery: {
        name: '',
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
      // 弹出层
      dialogFormVisible: false,
      fieldDesignVisible: false,
      // 弹出页 定义字段长度
      formLabelWidth: '120px',
      // 提交到后台的url
      dialogStatus: '',
      // 弹出页的标题
      titleType: '',
      // 提交到后台的表单
      dataFrom: {
        id: '',
        name: '',
        tableName: '',
        // initType: '',
        // useType: '',
        sort: '',
        remarks: '',
      },
      // 隐藏列用
      show: false,

      titleRole: '',
      dialogFormVisibleRole: false,
      options: [],

      downloadLoading: false,

    }
  }
}
</script>

<style>
</style>
