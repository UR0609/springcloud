<template>
  <div class="app-container">
    <div class="filter-container" style="float:left;margin-top: 20px;">
      <el-input v-model="listQuery.permissionName" placeholder="规则名称" style="width: 200px;" class="filter-item"
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

        <el-table-column label="按钮类型" prop="id" align="center" min-width="200px">
          <template slot-scope="scope">
            <span>{{ scope.row.permissionName }}</span>
          </template>
        </el-table-column>

        <el-table-column label="允许名称" prop="id" align="center" min-width="200px">
          <template slot-scope="scope">
            <span>{{ scope.row.permission }}</span>
          </template>
        </el-table-column>

        <el-table-column label="备注" prop="id" align="center" min-width="200px" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <span>{{ scope.row.remarks }}</span>
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
        <el-form-item label="按钮类型" :label-width="formLabelWidth" prop="permissionName">
          <el-input v-model="dataFrom.permissionName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="允许名称" :label-width="formLabelWidth" prop="permission">
          <el-input v-model="dataFrom.permission" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="排序" :label-width="formLabelWidth" prop="sort">
          <el-input v-model.number="dataFrom.sort" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="备注" :label-width="formLabelWidth">
          <el-input type="textarea" :rows="4" v-model="dataFrom.remarks" maxlength="200" autocomplete="off"></el-input>
        </el-form-item>
        <!--        <el-form-item label="活动区域" :label-width="formLabelWidth">-->
        <!--          <el-select v-model="form.region" placeholder="请选择活动区域">-->
        <!--            <el-option label="区域一" value="shanghai"></el-option>-->
        <!--            <el-option label="区域二" value="beijing"></el-option>-->
        <!--          </el-select>-->
        <!--        </el-form-item>-->
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
import {showResult, showEntity} from '@/utils/show-resutl';
import Pagination from '@/components/Pagination'

export default {
  name: "SysPermission",
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
      console.log(this.listQuery.page);
      console.log(this.listQuery.limit);
      this.listLoading = true
      this.$axios({
        method: "POST",
        url: this.path + "/list",
        data: {
          permissionName: this.listQuery.permissionName,
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
    clearData(){
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
    createData() {
      this.$refs['dataFrom'].validate((valid) => {
        if (valid) {
          this.$axios({
            method: "POST",
            url: this.path + "/add",
            data: {
              permissionName: this.dataFrom.permissionName,
              permission: this.dataFrom.permission,
              sort: this.dataFrom.sort,
              remarks: this.dataFrom.remarks
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
        this.dataFrom.roleName = entity.roleName;
        this.dataFrom.remarks = entity.remarks;
        // 页面属性更改
        this.titleType = '修改权限';
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
              permissionName: this.dataFrom.permissionName,
              permission: this.dataFrom.permission,
              sort: this.dataFrom.sort,
              remarks: this.dataFrom.remarks
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
          {type: 'number',message: '数量必须为数字值', trigger: 'blur'}
        ],
        remarks: [
          {max: 18, message: '长度小于 50 个字符', trigger: 'blur'}
        ]
      },
      // 请求url
      path: this.$route.path,
      // 数据总数
      total: 0,
      // 分页组件
      listQuery: {
        permissionName: '',
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
        permissionName: '',
        permission: '',
        sort: '',
        remarks: '',
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
