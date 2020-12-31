import {Message} from 'element-ui';

export function showResult(result) {
    let judge = false;
    if (result.isSuccess === true) {
        Message({
            showClose: true,
            message: result.msg,
            type: "success",
            duration: "600"
        });
        judge = true;
    } else {
        Message({
            showClose: true,
            message: result.msg,
            type: "error",
            duration: "3000"
        });
    }
    return judge
}

export function showEntity(result) {
    if (result.isSuccess === true) {
        return result.data
    } else {
        Message({
            showClose: true,
            message: result.msg,
            type: "error",
            duration: "3000"
        });
    }
}
