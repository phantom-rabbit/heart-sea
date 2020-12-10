import request from "@/utils/httpRequest";
export default {
    getHome (data) {
        return request({
            url: '/api/home/index',
            method: 'get',
            query: request.adornParams(data)
        })
    },

    getSdinfo(data) {
        return request({
            url: '/api/sd/info',
            method: 'get',
            query: request.adornParams(data)
        })
    },
    getNoticeList (data) {
        return request({
            url: '/api/notice/list',
            method: 'get',
            params: request.adornParams(data)
        })
    }
}
