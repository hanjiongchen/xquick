<template>
    <div class="login">
        <div class="store_header">
            <div class="store_avatar"><img src="../../assets/images/avatar_default.png" alt="头像" width="55" height="55"></div>
            <div class="store_name">litemall-vue</div>
        </div>

        <md-field-group>
            <md-field
                    v-model="account"
                    icon="username"
                    placeholder="请输入账号"
                    right-icon="clear-full"
                    name="user"
                    data-vv-as="帐号"
                    @right-click="clearText"/>
            <md-field
                    v-model="password" icon="lock"
                    placeholder="请输入密码"
                    :type="visiblePass ? 'text' : 'password'"
                    :right-icon="visiblePass ? 'eye-open' : 'eye-close'"
                    data-vv-as="密码"
                    name="password"
                    @right-click="visiblePass = !visiblePass"/>
            <div class="clearfix">
                <div class="float-l">
                    <router-link to="/login/registerGetCode">免费注册</router-link>
                </div>
                <div class="float-r">
                    <router-link to="/login/forget">忘记密码</router-link>
                </div>
            </div>

            <van-button size="large" type="danger" :loading="isLogining" @click="loginSubmit">登录</van-button>
        </md-field-group>
        <div class="text-desc text-center bottom_positon">xquick 提供技术支持</div>
    </div>
</template>

<script>
import Cookies from 'js-cookie'
import field from '@/components/field/';
import fieldGroup from '@/components/field-group/';
import {setLocalStorage} from '@/utils/local-storage';
import {emailReg, mobileReg} from '@/utils/validate';

import {Toast} from 'vant';

export default {
    name: 'login-request',
    components: {
        [field.name]: field,
        [fieldGroup.name]: fieldGroup,
        Toast
    },
    data() {
        return {
            account: '',
            password: '',
            visiblePass: false,
            isLogining: false,
            userInfo: {}
        };
    },

    methods: {
        clearText() {
            this.account = '';
        },

        validate() {

        },

        login() {
            let param = { type: 10, username: this.account, password: this.password }
            this.$http.post(`/uc/user/login`, param).then(({ data: res }) => {
                Toast.success('登录成功')
                // 将token保存到cookie
                Cookies.set('token', res.token)
                this.userInfo = res.user;
                setLocalStorage({
                    avatar: this.userInfo.avatar,
                    nickName: this.userInfo.nickName
                });
                this.routerRedirect();
            }).catch(resp => {
                Toast.fail(resp.msg)
            })
        },

        loginSubmit() {
            this.isLogining = true;
            try {
                this.validate();
                this.login();
                this.isLogining = false;
            } catch (err) {
                console.log(err.message);
                this.isLogining = false;
            }
        },

        routerRedirect() {
            // const { query } = this.$route;
            // this.$router.replace({
            //   name: query.redirect || 'home',
            //   query: query
            // });
            window.location = '#/user/';
        }
    }
};
</script>


<style lang="scss" scoped>
    @import '../../assets/scss/mixin';

    .login {
        position: relative;
        background-color: #fff;
    }

    .store_header {
        text-align: center;
        padding: 30px 0;

        .store_avatar img {
            border-radius: 50%;
        }

        .store_name {
            padding-top: 5px;
            font-size: 16px;
        }
    }

    .register {
        padding-top: 40px;
        color: $font-color-gray;

        a {
            color: $font-color-gray;
        }

        > div {
            width: 50%;
            box-sizing: border-box;
            padding: 0 20px;
        }

        .connect {
            @include one-border(right);
            text-align: right;
        }
    }

    .bottom_positon {
        position: absolute;
        bottom: 30px;
        width: 100%;
    }
</style>
