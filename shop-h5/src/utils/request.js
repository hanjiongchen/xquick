import axios from 'axios'
import {Dialog, Toast} from 'vant';

// create an axios instance
const service = axios.create({
    baseURL: process.env.VUE_APP_BASE_API, // api 的 base_url
    timeout: 5000 // request timeout
})

// request interceptor
service.interceptors.request.use(
    config => {
        if (!config.headers['token']) {
            config.headers['token'] = `${window.localStorage.getItem('Authorization') || ''}`;
        }
        return config;
    },
    err => Promise.reject(err)
)

// response interceptor
service.interceptors.response.use(
    response => {
        const res = response.data

        if (res.code === 401) {
            Toast.fail('请登录');
            setTimeout(() => {
                window.location = '#/login/'
            }, 1500)
            return Promise.reject('error')
        } else if (res.code !== 0) {
            // 非5xx的错误属于业务错误，留给具体页面处理
            Toast.fail(res.msg);
            return Promise.reject(response)
        } else {
            // 正常返回,只要数据
            return res
        }
    }, error => {
        console.log('err' + error)// for debug
        Dialog.alert({
            title: '警告',
            message: '网络请求异常'
        });
        return Promise.reject(error)
    })

export default service
