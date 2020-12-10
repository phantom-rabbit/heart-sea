import request from "@/utils/httpRequest";
export default {
    getLtcConfig (data) {
        return request({
            url: '/api/ltcConfig/list',
            method: 'post',
            data: request.adornData(data,true,"form"),
            headers: {
                'Content-type': 'application/x-www-form-urlencoded'
            }
        })
    },
    startLtc (data) {
        return request({
            url: '/api/ltc/start',
            method: 'post',
            data: request.adornData(data,true,"form"),
            headers: {
                'Content-type': 'application/x-www-form-urlencoded'
            }
        })
    },
    getUserLtcList (data) {
        return request({
            url: '/api/ltc/list',
            method: 'post',
            data: request.adornData(data,true,"form"),
            headers: {
                'Content-type': 'application/x-www-form-urlencoded'
            }
        })
    },
    buyLtc (data) {
        return request({
            url: '/api/ltc/buy',
            method: 'post',
            data: request.adornData(data,true,"form"),
            headers: {
                'Content-type': 'application/x-www-form-urlencoded'
            }
        })
    }
}
