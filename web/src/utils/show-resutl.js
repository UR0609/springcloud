import { Message } from 'element-ui';

export function showResult(result) {
    var judge = false;
    if (result.status === 200) {
        const data = result.data;
        if (data.isSuccess === true) {
            Message({
                showClose: true,
                message: data.msg,
                type: "success",
                duration: "600"
            });
            judge = true;
        } else {
            Message({
                showClose: true,
                message: data.msg,
                type: "error",
                duration: "3000"
            });
        }
    } else {
        Message({
            showClose: true,
            message: "操作失败，请联系管理员",
            type: "error",
            duration: "3000"
        });
    }
    return judge
}

export function showEntity(result) {
    if (result.status === 200) {
        const data = result.data;
        if (data.isSuccess === true) {
            return data.data
        } else {
            Message({
                showClose: true,
                message: data.msg,
                type: "error",
                duration: "3000"
            });
        }
    } else {
        Message({
            showClose: true,
            message: "操作失败，请联系管理员",
            type: "error",
            duration: "3000"
        });
    }

}
