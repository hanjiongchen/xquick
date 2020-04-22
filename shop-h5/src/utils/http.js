import axios from 'axios'
import Cookies from 'js-cookie'
import {Dialog, Toast} from 'vant';

/**
 * axios实例
 */
const http = axios.create({
    baseURL: window.SITE_CONFIG['apiURL'],
    timeout: 1000 * 180,
    withCredentials: true
})

/**
 * 请求拦截
 */
http.interceptors.request.use(config => {
        config.headers['token'] = Cookies.get('token') || ''
        // 默认参数
        const defaults = {}
        // 防止缓存，GET请求默认带_t参数
        if (config.method === 'get') {
            config.params = {
                ...config.params,
                ...{'_t': new Date().getTime()}
            }
        }
        return config;
    },
    err => Promise.reject(err)
)

/**
 * 响应拦截
 */
http.interceptors.response.use(response => {
    if (response.data.code === 401) {
        Toast.fail('请登录');
        setTimeout(() => {
            window.location = '#/login/'
        }, 1500)
        return Promise.reject(response.data)
    } else if (response.data.code !== 0) {
        // 非5xx的错误属于业务错误，留给具体页面处理
        return Promise.reject(response.data)
    } else {
        // 正常返回,只要数据
        return response.data
    }
}, error => {
    Dialog.alert({
        title: '警告',
        message: '网络请求异常'
    });
    return Promise.reject(error)
})

export default http
