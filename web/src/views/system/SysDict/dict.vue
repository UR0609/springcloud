<template>
  <div class="app-container">
    <div class="filter-container" style="float:left;margin-top: 20px;">
      <el-input v-model="listQuery.name" placeholder="名称" style="width: 200px;" class="filter-item"
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

        <el-table-column label="名称" prop="id" align="center" width="200px">
          <template slot-scope="scope">
            <span>{{ scope.row.name }}</span>
          </template>
        </el-table-column>

        <el-table-column label="值" prop="id" align="center" width="200px">
          <template slot-scope="scope">
            <span>{{ scope.row.value }}</span>
          </template>
        </el-table-column>

        <el-table-column label="类型" prop="id" align="center" width="200px">
          <template slot-scope="scope">
            <span>{{ scope.row.type }}</span>
          </template>
        </el-table-column>

        <el-table-column label="排序" prop="id" align="center" width="200px">
          <template slot-scope="scope">
            <span>{{ scope.row.sort }}</span>
          </template>
        </el-table-column>

        <el-table-column label="备注" prop="id" align="center" width="200px">
          <template slot-scope="scope">
            <span>{{ scope.row.remarks }}</span>
          </template>
        </el-table-column>

        <el-table-column label="操作" align="center" min-width="100px">
          <template slot-scope="scope">
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
        <el-form-item label="值" :label-width="formLabelWidth" prop="value">
          <el-input v-model="dataFrom.value" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="类型" :label-width="formLabelWidth" prop="type">
          <el-input v-model="dataFrom.type" autocomplete="off"></el-input>
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



  </div>
</template>

<script>
import {parseTime} from '@/utils'
import {showEntity, showResult} from '@/utils/show-resutl';
import Pagination from '@/components/Pagination'
// import {Message} from "element-ui"; // secondary package based on el-pagination

export default {
  name: "SysDict",
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
      this.dataFrom.value = '';
      this.dataFrom.type = '';
      this.dataFrom.remarks = '';
    },
    // 新增弹出页
    handleCreate() {
      this.titleType = '添加字典';
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
              value: this.dataFrom.value,
              type: this.dataFrom.type,
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
        this.dataFrom.value = entity.value;
        this.dataFrom.type = entity.type;
        this.dataFrom.sort = entity.sort;
        this.dataFrom.remarks = entity.remarks;
        // 页面属性更改
        this.titleType = '修改字典';
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
              value: this.dataFrom.value,
              type: this.dataFrom.type,
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
    // 导出
    // handleDownload() {
    //   this.downloadLoading = true
    //   import('@/vendor/Export2Excel').then(excel => {
    //     this.$axios({
    //       method: "POST",
    //       url: this.path + "/exportExcel",
    //       data: {
    //         name: this.listQuery.name,
    //         username: this.listQuery.username,
    //       },
    //     }).then(result => {
    //       if (result && result.status == 200) {
    //         const tHeader = ['姓名', '用户名', '密码', '年龄', '邮箱', '电话', '备注', '创建时间']
    //         const filterVal = ['name', 'username', 'password', 'age', 'email', 'phone', 'remarks', 'createTime']
    //         const list = result.data;
    //         const data = this.formatJson(filterVal, list)
    //         excel.export_json_to_excel({
    //           header: tHeader,
    //           data,
    //           filename: '用户列表',
    //           autoWidth: true,
    //           bookType: 'xlsx'
    //         })
    //         this.downloadLoading = false
    //       } else {
    //         Message({
    //           showClose: true,
    //           message: "操作失败，请联系管理员",
    //           type: "error",
    //           duration: "3000"
    //         });
    //       }
    //     });
    //   })
    // },
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
      // 规则验证
      dataRules: {
        name: [
          {required: true, message: "请填写姓名", trigger: 'blur'},
          {min: 2, max: 18, message: '长度在 2 到 18 个字符', trigger: 'blur'}
        ],
        value: [
          {required: true, message: "请填写值", trigger: 'blur'},
          {min: 1, max: 18, message: '长度在 1 到 18 个字符', trigger: 'blur'}
        ],
        type: [
          {required: true, message: "请填写类型", trigger: 'blur'},
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
        value: '',
        type: '',
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

<style scoped>

</style>
