import request from "@/utils/httpRequest";
export default {
    getBuyList (data) {
        return request({
            url: '/api/transaction/list',
            method: 'post',
            data: request.adornData(data,true,"form"),
            headers: {
                'Content-type': 'application/x-www-form-urlencoded'
            }
        })
    }
    ,

    getTransList (data) {
        return request({
            url: '/api/buytrans/list',
            method: 'post',
            data: request.adornData(data,true,"form"),
            headers: {
                'Content-type': 'application/x-www-form-urlencoded'
            }
        })
    },
    getTransDetail (data) {
        return request({
            url: '/api/transaction/detail',
            method: 'post',
            data: request.adornData(data,true,"form"),
            headers: {
                'Content-type': 'application/x-www-form-urlencoded'
            }
        })
    },

    getIndexData (data) {
        return request({
            url: '/api/transaction/index',
            method: 'post',
            data: request.adornParams(data,true,"form"),
            headers: {
                'Content-type': 'application/x-www-form-urlencoded'
            }
        })
    },
    sell (data) {
        return request({
            url: '/api/transaction/sell',
            method: 'post',
            data: request.adornData(data)

        })
    },
    cancle (data) {
        return request({
            url: '/api/purpose/cancle',
            method: 'post',
            data: request.adornData(data)
        })
    },


    submitTrans (data) {
        return request({
            url: '/api/transaction/confirm',
            method: 'post',
            data: request.adornData(data)

        })
    },




    publishBuy (data) {
        return request({
            url: '/api/transaction/bublish',
            method: 'post',
            data: request.adornData(data),

        })
    },



    uploadPhoto (data) {
        return request({
            url: '/api/transaction/photo',
            method: 'post',
            data: request.adornData(data),

        })
    },






    upload (data) {
        return request({
            url: '/api/upload',
            method: 'post',
            data: data,
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        })
    }


}
