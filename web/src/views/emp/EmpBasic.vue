<template>
  <div id="app">
    <el-row>
      <el-col :span="24">
        <el-table
            size="mini"
            :data="master_user.data"
            border
            style="width: 100%"
            highlight-current-row
        >
          <el-table-column type="index"></el-table-column>
          <el-table-column
              v-for="(item,index) in master_user.columns"
              :label="item.label"
              :prop="item.prop"
              :width="item.width"
              :key="index"
          >
            <template slot-scope="scope">
                    <span v-if="scope.row.isSet && item.type == 'date'">
                      <el-date-picker
                          :clearable="false"
                          size="small"
                          style="width:133px"
                          v-model="master_user.sel[item.prop]"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="选择时间"
                      ></el-date-picker>
                    </span>
              <span v-else-if="scope.row.isSet">
                      <el-input
                          size="mini"
                          placeholder="请输入内容"
                          v-model="master_user.sel[item.prop]"
                      ></el-input>
                    </span>
              <span v-else>{{scope.row[item.prop]}}</span>
            </template>
          </el-table-column>
          <el-table-column label="操作" width>
            <template slot-scope="scope">
                    <span
                        v-show="!scope.row.show"
                        class="el-tag el-tag--success el-tag--mini"
                        style="cursor: pointer;"
                        @click.stop="saveRow(scope.row,scope.$index)"
                    >保存</span>
              <span
                  v-show="scope.row.show"
                  class="el-tag el-tag--primary el-tag--mini"
                  style="cursor: pointer;"
                  @click="editRow(scope.row,scope.$index)"
              >编辑</span>
              <span
                  class="el-tag el-tag--danger el-tag--mini"
                  style="cursor: pointer;"
                  @click="deleteRow(scope.$index,master_user.data)"
              >删除</span>
            </template>
          </el-table-column>
        </el-table>
      </el-col>
      <el-col :span="24">
        <div class="el-table-add-row" style="width: 99.2%;" @click="add()">
          <span>+ 添加</span>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: 'EmpBasic',
  data() {
    return {
      master_user: {
        sel: null, //选中行
        columns: [
          {
            prop: "itemName",
            label: "条目名称",
            width: 140,
          },
          {
            prop: "itemMoney",
            label: "条目金额",
            width: 130,
          },
          {
            prop: "remarks",
            label: "摘要",
            width: 230,
          },
          {
            prop: "itemTime",
            label: "条目时间",
            width: 150,
            type:'date'
          },
        ],
        data: [],
      },
    }
  },
  created() {
    this.this.master_user.data.map((i) => {
      i.show = false;
      return i;
    });
  },
  methods: {
    add() {
      for (let i of this.master_user.data) {
        if (i.isSet) return this.$message.warning("请先保存当前编辑项");
      }
      let j = {
        "type": "",
        "addport": "",
        "user": "",
        "pwd": "",
        "info": "",
        "isSet": true,
      };
      this.master_user.data.push(j);
      this.master_user.sel = JSON.parse(JSON.stringify(j));
    },
    saveRow(row, index) { //保存
      console.log(index);
      row.show = true; //判断状态显示对应按钮
      let data = JSON.parse(JSON.stringify(this.master_user.sel));
      for (let k in data) {
        row[k] = data[k] //将sel里面的value赋值给这一行
      }
      row.isSet = false;
    },
    editRow(row) { //编辑
      for (let i of this.master_user.data) {
        if (i.isSet) return this.$message.warning("请先保存当前编辑11项");
      }
      row.show = false; //判断状态显示对应按钮
      this.master_user.sel = row
      row.isSet = true
    },
    deleteRow(index, rows) { //删除
      rows.splice(index, 1)
    }
  },
  components: {}
}
</script>

<style>
.el-table-add-row {
  margin-top: 10px;
  width: 100%;
  height: 34px;
  border: 1px dashed #c1c1cd;
  border-radius: 3px;
  cursor: pointer;
  justify-content: center;
  display: flex;
  line-height: 34px;
}
</style>
