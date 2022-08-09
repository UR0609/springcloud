<template>
  <div>
    这是：综合信息统计
    <el-upload
        class="upload-demo"
        :action="uploadurl"
        :on-preview="handlePreview"
        :on-remove="handleRemove"
        :before-remove="beforeRemove"
        :http-request="selfUpload2"
        multiple
        :limit="3"
        :on-exceed="handleExceed"
        :file-list="fileList">
      <el-button size="small" type="primary">点击上传</el-button>
      <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
    </el-upload>
  </div>
</template>

<script>
import {fileupload} from "@/utils/message";

export default {
  data() {
    return {
      uploadurl: "http://localhost:8661" + "/file/upload",
      fileList: [{name: 'food.jpeg', url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'}, {name: 'food2.jpeg', url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'}]
    };
  },
  methods: {
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    handlePreview(file) {
      console.log(file);
    },
    handleExceed(files, fileList) {
      this.$message.warning(`当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
    },
    beforeRemove(file) {
      return this.$confirm(`确定移除 ${ file.name }？`);
    },
    selfUpload2(param) {
      const formData = new FormData();
      formData.append("file", param.file);
      fileupload(formData)
          .then((res) => {
            console.log(res);
          })
          .catch((err) => {
            this.fileList = [];
            console.log(err);
          });
    },
  }
}

</script>

<style scoped>

</style>
