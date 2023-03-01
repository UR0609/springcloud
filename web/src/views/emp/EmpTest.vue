<template>
  <div>
    <el-table class="tableBox" :data="tableData" align="center" border style="width: 100%;" :cell-style="myclass"
              highlight-current-row>
      <el-table-column align="center" v-for="(item, index) in headData" :key="index" :prop="item.name"
                       :label="item.description" :v-if="true">
        <template slot-scope="scope">
          <span>{{ scope.row[index].name }}</span>
        </template>
      </el-table-column>
    </el-table>
    <div>
<!--      <el-button type="primary" @click="addtableData">添加</el-button>-->
      <el-button type="primary" @click="present ">提交</el-button>
    </div>
  </div>
</template>

<script>
// import {showResult} from "@/utils/show-resutl";

export default {
  created() {
    // 在模板渲染成html前调用
    this.getHeadData();
    this.getTableData();

  },
  mounted() {
  },
  methods: {
    getHeadData() {
      this.$axios({
        method: "POST",
        // url: this.path + "/getheadData",
        url: "/sys/database/content/getheadData",
        data: {
          databaseId: 1,
        },
      }).then(result => {
        if (result && result.status == 200) {
          console.log(result.data, "表头数据");
          this.headData = result.data;
          // setTimeout(() => {
          //   this.listLoading = false
          // }, 0.5 * 1000)
        }
      });
    },
    getTableData() {
      this.$axios({
        method: "POST",
        url: "/sys/database/content/getTableData",
        data: {
          databaseId: 1,
          pageNo: 2,
          pageSize: 3,
          // birthdayStart: "2000-01-01 00:00:00",
          // birthdayEnd: "2004-01-01 00:00:00",
          // sex: 0,
          // name: '喜',
        },
      }).then(result => {
        if (result && result.status == 200) {
          console.log(result.data, "表头数据");
          this.headData = result.data;
          // setTimeout(() => {
          //   this.listLoading = false
          // }, 0.5 * 1000)
        }
      });
    },
    // 删除
    deleteData(index, row) {
      console.log(row.id, "参数");
    },
    //根据条件修改单元格字体样式（sfcb字段为false且不为null时）
    myclass({row, columnIndex}) {
      if (row[columnIndex] && !row[columnIndex].sfcb && row[columnIndex].sfcb != null) {
        return "color: red";
      }
    },
    // 提交操作
    present() {
      let result = [];
      // 通过双层循环拿到所需字段
      this.tableData.forEach((item) => {
        let data = {};
        item.forEach((e) => {
          // 将字段名、字段值以键值对的形式赋值
          data[e.zdm] = e.value;
        });
        // 每一行数据为一个对象添加到数组中 [{},{},{},...]
        result.push(data);
      });
      console.log(result, "参数");
      // 执行接口操作
    },
  },
  data() {
    return {
      centerDialogVisible: true, //弹框显隐状态
      // 模拟表头数据
      headData: [],
      // 模拟表格数据
      tableData: [],
    };
  },

};
</script>

<style scoped>
.tableBox {
  margin-bottom: 20px;
}

/* 通过显隐控制input框的状态 */
.tableBox .el-input {
  display: none;
}

.tableBox .current-row .el-input {
  display: block;
}

.tableBox .current-row .el-input + span {
  display: none;
}
</style>
