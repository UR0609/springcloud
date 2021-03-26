<template>
  <div>
    <el-table
        :data="tableData"
        v-loading="listLoading"
        border
        stripe
        height="700">
      <el-table-column
          v-for="info in headerData" :key="info.key"
          :property="info.key"
          :label="info.label"
      >
        <template slot-scope="scope">
          {{ scope.row[scope.column.property] }}
        </template>
      </el-table-column>
      <el-table-column label="启用状态">
        <template slot-scope="scope">
          <el-switch
              v-model="scope.row.ifUse">
          </el-switch>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import {showResult} from "@/utils/show-resutl";

export default {
  name: "EmpTest",
  created() {
    this.getHeader();
    this.getList();
  },
  mounted() {
    // 在模板渲染成html后调用
  },
  methods: {
    getHeader() {
      this.$axios({
        method: "POST",
        url: this.path + "/header",
        data: {},
      }).then(result => {
        console.log(result);
        if (result && result.status == 200) {
          this.headerData = result.data;
        }
      });
    },
    getList() {
      // 请求url
      this.$axios({
        method: "POST",
        url: this.path + "/list",
        data: {},
      }).then(result => {
        console.log(result);
        if (result && result.status == 200) {
          setTimeout(() => {
            this.tableData = result.data;
            this.listLoading = false
          }, 0.5 * 1000)
        }
      });
    },
  },
  data() {
    return {
      listLoading: true,
      path: this.$route.path,
      headerData: [],
      // headerData: [
      //   {
      //     label: '编码',
      //     key: 'code'
      //   },
      //   {
      //     label: '姓名',
      //     key: 'name'
      //   },
      //   {
      //     label: '权限描述',
      //     key: 'description'
      //   }
      // ],
      tableData: [],
      // rightsDate: [{
      //   "id": 221,
      //   "code": "01",
      //   "name": "西药开立权限",
      //   "description": "医生对西药处方权限",
      //   "ifUse": "0"
      // }, {
      //   "id": 222,
      //   "code": "02",
      //   "name": "草药开立权限",
      //   "description": "医生对草药处方权限",
      //   "ifUse": "0"
      // }, {
      //   "id": 223,
      //   "code": "03",
      //   "name": "成药开立权限",
      //   "description": "医生对成药处方权限",
      //   "ifUse": "0"
      // }, {
      //   "id": 224,
      //   "code": "04",
      //   "name": "麻醉开立权限",
      //   "description": "医生对麻醉处方权限",
      //   "ifUse": "0"
      // },
      //   {
      //     "id": 225,
      //     "code": "05",
      //     "name": "精一开立权限",
      //     "description": "医生对精一处方权限",
      //     "ifUse": "0"
      //   }
      // ]
    }
  }
}
</script>

<style scoped>

</style>
