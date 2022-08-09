import request from "@/utils/request";
/**
 * 上传文件
 */
export function fileupload(data) {
  return request({
    url: "http://localhost:8661" + "/file/upload",
    method: "post",
    data,
	timeout:30 * 1000,
    headers: { "Content-Type": "multipart/form-data" },
  });
}
