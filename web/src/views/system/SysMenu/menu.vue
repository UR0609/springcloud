<template>
  <div class="app-container">
    <div class="filter-container" style="float:left;margin-top: 20px;">
      <!--      <el-input v-model="listQuery.roleName" placeholder="姓名" style="width: 200px;" class="filter-item"-->
      <!--                @keyup.enter.native="handleFilter" clearable/>-->
      <!--      <el-input v-model="listQuery.remarks" placeholder="用户名" style="width: 200px;" class="filter-item"-->
      <!--                @keyup.enter.native="handleFilter" clearable/>-->

      <!--      <el-button class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">-->
      <!--        查询-->
      <!--      </el-button>-->
      <el-button class="filter-item" style="margin-left: 10px;" type="success" icon="el-icon-edit"
                 @click="handleCreate">
        添加
      </el-button>
      <!--      <el-button :loading="downloadLoading" class="filter-item" type="warning" icon="el-icon-download"-->
      <!--                 @click="handleDownload">-->
      <!--        导出-->
      <!--      </el-button>-->
    </div>
    <div>
      <el-table
          :key="tableKey"
          v-loading="listLoading"
          :data="tableData"
          row-key="id"
          border
          fit
          highlight-current-row
          style="width: 100%"
          default-expand-all
          :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
      >
        <el-table-column label="ID" prop="id" align="center" width="80" v-if="show">
          <template slot-scope="scope">
            <span>{{ scope.row.id }}</span>
          </template>
        </el-table-column>

        <el-table-column label="菜单名称" prop="id" align="center" min-width="200px">
          <template slot-scope="scope">
            <span>{{ scope.row.name }}</span>
          </template>
        </el-table-column>

        <el-table-column label="跳转路径" prop="id" align="center" min-width="200px">
          <template slot-scope="scope">
            <span>{{ scope.row.path }}</span>
          </template>
        </el-table-column>

        <el-table-column label="菜单组件" prop="id" align="center" min-width="200px">
          <template slot-scope="scope">
            <span>{{ scope.row.component }}</span>
          </template>
        </el-table-column>

        <el-table-column label="图标" prop="id" align="center" min-width="200px">
          <template slot-scope="scope">
            <span>{{ scope.row.icon }}</span>
          </template>
        </el-table-column>

        <el-table-column label="排序" prop="id" align="center" min-width="50px">
          <template slot-scope="scope">
            <span>{{ scope.row.sort }}</span>
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
      <!--      <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit"-->
      <!--                  @pagination="getList"/>-->
    </div>


    <!--  添加或修改 弹出页  -->
    <el-dialog :title="this.titleType" :visible.sync="dialogFormVisible">
      <el-form :model="dataFrom" :rules="dataRules" ref="dataFrom" label-position="left" label-width="70px"
               style="width: 400px; margin-left:50px;">
        <el-form-item label="菜单名称" :label-width="formLabelWidth" prop="name">
          <el-input v-model="dataFrom.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="父级" :label-width="formLabelWidth" prop="parentId">
          <TreeSelect
              ref="TreeSelect"
              style="width: 100%"
              :props="props"
              :options="optionData"
              :value="valueId"
              :clearable="isClearable"
              :accordion="isAccordion"
              @getValue="getValue($event)"
          />
        </el-form-item>
        <el-form-item label="跳转路径" :label-width="formLabelWidth" prop="path">
          <el-input v-model="dataFrom.path" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="菜单组件" :label-width="formLabelWidth" prop="component">
          <el-input v-model="dataFrom.component" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="图标" :label-width="formLabelWidth">
          <el-input v-model="dataFrom.icon" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="排序" :label-width="formLabelWidth">
          <el-input v-model="dataFrom.sort" autocomplete="off"></el-input>
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
// import Pagination from "@/components/Pagination";
import {showEntity, showResult} from "@/utils/show-resutl";
import {Message} from "element-ui";
import {parseTime} from "@/utils";
import TreeSelect from "@/components/TreeSelect";

export default {
  name: "SysMenu",
  computed: {
    /* 转树形数据 */
    optionData() {
      let cloneData = JSON.parse(JSON.stringify(this.selectTreeList)); // 对源数据深度克隆
      return cloneData.filter(father => {
        // 循环所有项，并添加children属性
        let branchArr = cloneData.filter(child => father.id == child.parentId); // 返回每一项的子级数组
        branchArr.length > 0 ? (father.children = branchArr) : ""; //给父级添加一个children属性，并赋值
        return father.parentId == 0; //返回第一层
      });
    }
  },
  components: {TreeSelect:TreeSelect},
  created() {
    // 在模板渲染成html前调用
    this.getList();
    this.getSelectTree();
    // this.optionData(this.selectTreeList);
  },
  mounted() {
    // 在模板渲染成html后调用
  },
  methods: {
    // select树形
    getSelectTree() {
      this.$axios({
        method: "POST",
        url: this.path + "/selectTree",
        data: {},
      }).then(result => {
        if (result && result.status == 200) {
          this.selectTreeList = result.data;
        }
      });
    },
    // 列表加载
    getList() {
      this.listLoading = true
      this.$axios({
        method: "POST",
        url: this.path + "/list",
        data: {
          // pageNo: this.listQuery.page,
          // pageSize: this.listQuery.limit
        },
      }).then(result => {
        if (result && result.status == 200) {
          // this.total = result.data.total;
          this.tableData = result.data;
          setTimeout(() => {
            this.listLoading = false
          }, 0.5 * 1000)
        }
      });
    },
    clearData(){
      // 清空内容
      this.dataFrom.id = '';
      this.dataFrom.name = '';
      this.dataFrom.parentId = '';
      this.dataFrom.path = '';
      this.dataFrom.component = '';
      this.dataFrom.icon = '';
      this.dataFrom.sort = '';
      this.dataFrom.remarks = '';
      this.$nextTick(()=>{
        this.$refs.TreeSelect.clearHandle();
      })
    },
    // 新增弹出页
    handleCreate() {
      this.titleType = '添加菜单';
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
              parentId: this.dataFrom.parentId,
              path: this.dataFrom.path,
              component: this.dataFrom.component,
              icon: this.dataFrom.icon,
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
        this.dataFrom.parentId = entity.parentId;
        this.dataFrom.path = entity.path;
        this.dataFrom.component = entity.component;
        this.dataFrom.icon = entity.icon;
        this.dataFrom.sort = entity.sort;
        this.dataFrom.remarks = entity.remarks;
        // 页面属性更改
        this.titleType = '修改菜单';
        this.dialogStatus = 'updata';
        this.dialogFormVisible = true;
        this.$nextTick(()=>{
          this.$refs.TreeSelect.clearHandle();
          if(entity.parentId !== 0){
            this.$refs.TreeSelect.valueIDAssignment(entity.parentId);
          }
        })
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
              parentId: this.dataFrom.parentId,
              path: this.dataFrom.path,
              component: this.dataFrom.component,
              icon: this.dataFrom.icon,
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
          // localStorage.setItem("updateStatus", "update");
        }
      });
    },
    // 查询
    // handleFilter() {
    //   this.listQuery.page = 1
    //   this.getList()
    // },
    // 导出
    handleDownload() {
      this.downloadLoading = true
      import('@/vendor/Export2Excel').then(excel => {
        this.$axios({
          method: "POST",
          url: this.path + "/exportExcel",
          data: {
            // name: this.listQuery.name,
            // username: this.listQuery.username,
          },
        }).then(result => {
          if (result && result.status == 200) {
            const tHeader = ['规则名称', '备注', '创建时间']
            const filterVal = ['roleName', 'remarks', 'createTime']
            const list = result.data;
            const data = this.formatJson(filterVal, list)
            excel.export_json_to_excel({
              header: tHeader,
              data,
              filename: '用户列表',
              autoWidth: true,
              bookType: 'xlsx'
            })
            this.downloadLoading = false
          } else {
            Message({
              showClose: true,
              message: "操作失败，请联系管理员",
              type: "error",
              duration: "3000"
            });
          }
        });
      })
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
    // select树形取值取值
    getValue(value) {
      this.dataFrom.parentId = value;
    },
  },
  data() {
    return {
      // 规则验证
      dataRules: {
        name: [
          {required: true, message: "请填写规则名称", trigger: 'blur'},
          {min: 1, max: 10, message: '长度在 1 到 10 个字符', trigger: 'blur'}
        ],
        parentId: [
          {required: true, message: "请选择父级", trigger: 'blur'}
        ],
        path: [
          {required: true, message: "请填写跳转路径", trigger: 'blur'},
          {min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur'}
        ],
        component: [
          {required: true, message: "请填写菜单组件", trigger: 'blur'},
          {min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur'}
        ],
      },
      // 请求url
      path: this.$route.path,
      // 数据总数
      // total: 0,
      // 分页组件
      // listQuery: {
      //   roleName: '',
      //   remarks: '',
      //   page: 1,
      //   limit: 20,
      //   importance: undefined,
      //   title: undefined,
      //   type: undefined,
      //   sort: '+id'
      // },
      // 列表延迟加载
      listLoading: true,
      tableKey: 0,
      tableData: [],
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
        parentId: '',
        path: '',
        component: '',
        icon: '',
        sort: '',
        remarks: '',
      },
      // 隐藏列用
      show: false,

      // select树形需要的值
      isClearable: true, // 可清空（可选）
      isAccordion: true, // 可收起（可选）
      valueId: null, // 初始ID（可选）
      props: {
        // 配置项（必选）
        value: "id",
        label: "name",
        children: "children"
        // disabled:true
      },
      // 选项列表（必选）
      selectTreeList: [
        {id: 1, parentId: 0, name: "一级菜单A"},
        {id: 2, parentId: 0, name: "一级菜单B"},
        {id: 3, parentId: 0, name: "一级菜单C"},
        {id: 4, parentId: 1, name: "二级菜单A-A",},
        {id: 5, parentId: 1, name: "二级菜单A-B",},
        {id: 6, parentId: 2, name: "二级菜单B-A",},
        {id: 7, parentId: 4, name: "三级菜单A-A-A",},
        {id: 8, parentId: 7, name: "四级菜单A-A-A-A",},
        {id: 9, parentId: 0, name: "一级菜单C",},
        {id: 10, parentId: 0, name: "一级菜单end",}
      ],

      downloadLoading: false,
    }
  }
}
</script>

<style scoped>

</style>
