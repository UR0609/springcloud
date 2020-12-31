import axios from 'axios'
import router from '../router'
import {Message} from 'element-ui'
import store from '@/store'

const baseURL = 'http://localhost:8661'
// create an axios instance
const service = axios.create({
    baseURL: baseURL, // url = base url + request url
    // withCredentials: true, // send cookies when cross-domain requests
    timeout: 5000 // request timeout
})

// request interceptor
service.interceptors.request.use(
    config => {
        let token = localStorage.getItem("token");
        // 给请求头添加token
        if (store.state.token === undefined) {
            config.headers['token'] = token;
        }
        return config
    },
    error => {
        console.log(error) // for debug
        Promise.reject(error)
    }
)

// response interceptor
service.interceptors.response.use(
    /**
     * If you want to get http information such as headers or status
     * Please return  response => response
     */

    /**
     * Determine the request status by custom code
     * Here is just an example
     * You can also judge the status by HTTP Status Code
     */
    response => {
        /**
         * code为非20000是抛错 可结合自己业务进行修改
         */

        const res = response.data;
        if (res.status === 200) {
            return res;
        } else if (res.status === 403) {
            // code为603代表token已经失效,
            // 提示用户,然后跳转到登陆页面
            router.push('/')
        } else {
            Promise.reject(res.msg);
        }
    },
    error => {
        console.log('err' + error) // for debug
        Message({
            message: error.message,
            type: 'error',
            duration: 5 * 1000
        })
        return Promise.reject(error)
    }
)

export default service
