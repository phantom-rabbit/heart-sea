import request from "@/utils/httpRequest";
export default {
    getUserInfo (data) {
        return request({
            url: '/api/userInfo',
            method: 'get',
            params: request.adornParams(data)
        })
    },
    sendRegisterSms (data) {
        return request({
            url: '/api/register/sms',
            method: 'post',
            data: request.adornData(data)
        })
    },
    register (data) {
        return request({
            url: '/api/register',
            method: 'post',
            data: request.adornData(data)
        })
    },
    login (data) {
        return request({
            url: '/api/login',
            method: 'post',
            data: request.adornData(data)
        })
    }
}
