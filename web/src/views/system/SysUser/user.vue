<template>
  <div class="app-container">
    <div class="filter-container" style="float:left;margin-top: 20px;">
      <el-input v-model="listQuery.name" placeholder="姓名" style="width: 200px;" class="filter-item"
                @keyup.enter.native="handleFilter" clearable/>
      <el-input v-model="listQuery.username" placeholder="用户名" style="width: 200px;" class="filter-item"
                @keyup.enter.native="handleFilter" clearable/>

      <el-button class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">
        查询
      </el-button>
      <el-button :loading="downloadLoading" class="filter-item" type="warning" icon="el-icon-download"
                 @click="handleDownload">
        导出
      </el-button>
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

        <el-table-column label="姓名" prop="id" align="center" width="110px">
          <template slot-scope="scope">
            <span>{{ scope.row.name }}</span>
          </template>
        </el-table-column>

        <el-table-column label="用户名" prop="id" align="center" width="110px">
          <template slot-scope="scope">
            <span>{{ scope.row.username }}</span>
          </template>
        </el-table-column>

        <el-table-column label="密码" prop="id" align="center" width="110px">
          <template slot-scope="scope">
            <span>{{ scope.row.password }}</span>
          </template>
        </el-table-column>

        <el-table-column label="年龄" prop="id" align="center" width="110px">
          <template slot-scope="scope">
            <span>{{ scope.row.age }}</span>
          </template>
        </el-table-column>

        <el-table-column label="邮箱" prop="id" align="center" min-width="200px">
          <template slot-scope="scope">
            <span>{{ scope.row.email }}</span>
          </template>
        </el-table-column>

        <el-table-column label="电话" prop="id" align="center" min-width="200px">
          <template slot-scope="scope">
            <span>{{ scope.row.phone }}</span>
          </template>
        </el-table-column>

        <el-table-column label="备注" prop="id" align="center" min-width="200px" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <span>{{ scope.row.remarks }}</span>
          </template>
        </el-table-column>

        <el-table-column label="操作" min-width="200">
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
        <el-form-item label="用户名" :label-width="formLabelWidth" prop="username">
          <el-input v-model="dataFrom.username" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="密码" :label-width="formLabelWidth" prop="password">
          <el-input v-model="dataFrom.password" autocomplete="off" show-password></el-input>
        </el-form-item>
        <el-form-item label="姓名" :label-width="formLabelWidth" prop="name">
          <el-input v-model="dataFrom.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="年龄" :label-width="formLabelWidth" prop="age">
          <el-input v-model="dataFrom.age" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="电话" :label-width="formLabelWidth" prop="phone">
          <el-input v-model="dataFrom.phone" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" :label-width="formLabelWidth" prop="email">
          <el-input v-model="dataFrom.email" autocomplete="off"></el-input>
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
import {validEmail, validPhone} from '@/utils/validate';
import {showResult, showEntity} from '@/utils/show-resutl';
import axios from 'axios';
import Pagination from '@/components/Pagination' // secondary package based on el-pagination

const token = localStorage.getItem("token");

export default {
  name: "SysUser",
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
      axios({
        method: "POST",
        url: this.path + "/list",
        data: {
          name: this.listQuery.name,
          username: this.listQuery.username,
          pageNo: this.listQuery.page,
          pageSize: this.listQuery.limit
        },
        headers: {
          token: token
        }
      }).then(result => {
        if (result && result.status == 200) {
          this.total = result.data.total;
          this.tableData = result.data.records;
          console.log(result.data.records);
          setTimeout(() => {
            this.listLoading = false
          }, 0.5 * 1000)
        }
      });
    },
    // 新增弹出页
    handleCreate() {
      this.titleType = '添加用户';
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
              username: this.dataFrom.username,
              password: this.dataFrom.password,
              name: this.dataFrom.name,
              age: this.dataFrom.age,
              email: this.dataFrom.email,
              phone: this.dataFrom.phone,
              remarks: this.dataFrom.remarks,
            },
            headers: {
              token: this.token
            }
          }).then(result => {
            var judge = showResult(result);
            if (judge) {
              this.getList();
              this.dialogFormVisible = false;
            }
          });
        }
      })
    },
    // 修改弹出页
    handleUpdate(index, row) {
      console.log(row.id);
      this.$axios({
        method: "POST",
        url: this.path + "/sel",
        data: {
          id: row.id
        },
        headers: {
          token: this.token
        }
      }).then(result => {
        var entity = showEntity(result);
        // 隐藏域赋值
        this.dataFrom.id = row.id;
        this.dataFrom.username = entity.username;
        this.dataFrom.password = entity.password;
        this.dataFrom.name = entity.name;
        this.dataFrom.age = entity.age;
        this.dataFrom.email = entity.email;
        this.dataFrom.phone = entity.phone;
        this.dataFrom.remarks = entity.remarks;
        // 页面属性更改
        this.titleType = '修改用户';
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
              username: this.dataFrom.username,
              password: this.dataFrom.password,
              name: this.dataFrom.name,
              age: this.dataFrom.age,
              email: this.dataFrom.email,
              phone: this.dataFrom.phone,
              remarks: this.dataFrom.remarks,
            },
            headers: {
              token: this.token
            }
          }).then(result => {
            var judge = showResult(result);
            if (judge) {
              this.getList();
              this.dialogFormVisible = false;
              this.dataFrom.id = '';
            }
          });
        }
      })
    },
    // 删除
    deleteData(index, row) {
      console.log(index, row);
      this.$axios({
        method: "POST",
        url: this.path + "/del",
        data: {
          id: row.id
        },
        headers: {
          token: this.token
        }
      }).then(result => {
        var judge = showResult(result);
        if (judge) {
          this.getList();
        }
      });
    },
    // 查询
    handleFilter() {
      this.listQuery.page = 1
      this.getList()
    },
    // 导出
    handleDownload() {
      this.downloadLoading = true
    },

  },
  data() {
    var checkAge = (rule, value, callback) => {
      // if (!value) {
      //   return callback(new Error('年龄不能为空'))
      // }
      if (value !== '') {
        setTimeout(() => {
          const reg = /^[0-9]*$/
          let result = reg.test(value);
          if (result) {
            callback();
          } else {
            callback(new Error("请输入正确的数字"));
          }
        }, 100)
      } else {
        callback()
      }
    }
    // 年龄验证方法
    var checkPhone = (rule, value, callback) => {
      // // if (!value) {
      // //   return callback(new Error('电话号码不能为空'))
      // // }
      if (value !== '') {
        setTimeout(() => {
          var result = validPhone(value);
          if (result) {
            callback()
          } else {
            callback(new Error('电话号码格式不正确'))
          }
        }, 100)
      } else {
        callback()
      }
    }
    // 邮箱验证方法
    var checkEmail = (rule, value, callback) => {
      // if (!value) {
      //   return callback(new Error('邮箱不能为空'))
      // }
      if (value !== '') {
        setTimeout(() => {
          var result = validEmail(value);
          if (result) {
            callback()
          } else {
            callback(new Error('请输入正确的邮箱格式'))
          }
        }, 100)
      } else {
        callback()
      }
    }
    return {
      // 规则验证
      dataRules: {
        username: [
          {required: true, message: "请填写用户名", trigger: 'blur'},
          {min: 5, max: 18, message: '长度在 6 到 18 个字符', trigger: 'blur'}
        ],
        password: [
          {required: true, message: "请填写密码", trigger: 'blur'},
          {min: 6, max: 18, message: '长度在 6 到 18 个字符', trigger: 'blur'}
        ],
        name: [
          {required: true, message: "请填写姓名", trigger: 'blur'},
          {min: 2, max: 18, message: '长度在 6 到 18 个字符', trigger: 'blur'}
        ],
        age: [
          {validator: checkAge, trigger: 'blur'}
        ],
        phone: [
          {validator: checkPhone, trigger: 'blur'}
        ],
        email: [
          {validator: checkEmail, trigger: 'blur'}
        ]
      },
      // 请求url
      path: this.$route.path,
      token: this.getToken(),
      // 数据总数
      total: 0,
      // 分页组件
      listQuery: {
        name: '',
        username: '',
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
        username: '',
        password: '',
        name: '',
        age: '',
        email: '',
        phone: '',
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
