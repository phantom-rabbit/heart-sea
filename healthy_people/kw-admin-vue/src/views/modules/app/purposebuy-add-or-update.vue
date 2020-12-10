<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="挂买人id" prop="buyerId">
      <el-input v-model="dataForm.buyerId" placeholder="挂买人id"></el-input>
    </el-form-item>
    <el-form-item label="挂买总量" prop="total">
      <el-input v-model="dataForm.total" placeholder="挂买总量"></el-input>
    </el-form-item>
    <el-form-item label="起量" prop="buyStart">
      <el-input v-model="dataForm.buyStart" placeholder="起量"></el-input>
    </el-form-item>
    <el-form-item label="已购买" prop="hasBuy">
      <el-input v-model="dataForm.hasBuy" placeholder="已购买"></el-input>
    </el-form-item>
    <el-form-item label="支付方式(支付宝/usdt/银行卡)" prop="payType">
      <el-input v-model="dataForm.payType" placeholder="支付方式(支付宝/usdt/银行卡)"></el-input>
    </el-form-item>
    <el-form-item label="预购买(别人点击确定 我尚未付款前 金额增加最红 , pre_buy+has_buy<=total)" prop="preBuy">
      <el-input v-model="dataForm.preBuy" placeholder="预购买(别人点击确定 我尚未付款前 金额增加最红 , pre_buy+has_buy<=total)"></el-input>
    </el-form-item>
    <el-form-item label="" prop="status">
      <el-input v-model="dataForm.status" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="createTime">
      <el-input v-model="dataForm.createTime" placeholder=""></el-input>
    </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        visible: false,
        dataForm: {
          id: 0,
          buyerId: '',
          total: '',
          buyStart: '',
          hasBuy: '',
          payType: '',
          preBuy: '',
          status: '',
          createTime: ''
        },
        dataRule: {
          buyerId: [
            { required: true, message: '挂买人id不能为空', trigger: 'blur' }
          ],
          total: [
            { required: true, message: '挂买总量不能为空', trigger: 'blur' }
          ],
          buyStart: [
            { required: true, message: '起量不能为空', trigger: 'blur' }
          ],
          hasBuy: [
            { required: true, message: '已购买不能为空', trigger: 'blur' }
          ],
          payType: [
            { required: true, message: '支付方式(支付宝/usdt/银行卡)不能为空', trigger: 'blur' }
          ],
          preBuy: [
            { required: true, message: '预购买(别人点击确定 我尚未付款前 金额增加最红 , pre_buy+has_buy<=total)不能为空', trigger: 'blur' }
          ],
          status: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          createTime: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.id = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/app/purposebuy/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.buyerId = data.purposeBuy.buyerId
                this.dataForm.total = data.purposeBuy.total
                this.dataForm.buyStart = data.purposeBuy.buyStart
                this.dataForm.hasBuy = data.purposeBuy.hasBuy
                this.dataForm.payType = data.purposeBuy.payType
                this.dataForm.preBuy = data.purposeBuy.preBuy
                this.dataForm.status = data.purposeBuy.status
                this.dataForm.createTime = data.purposeBuy.createTime
              }
            })
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/app/purposebuy/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'buyerId': this.dataForm.buyerId,
                'total': this.dataForm.total,
                'buyStart': this.dataForm.buyStart,
                'hasBuy': this.dataForm.hasBuy,
                'payType': this.dataForm.payType,
                'preBuy': this.dataForm.preBuy,
                'status': this.dataForm.status,
                'createTime': this.dataForm.createTime
              })
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.$message({
                  message: '操作成功',
                  type: 'success',
                  duration: 1500,
                  onClose: () => {
                    this.visible = false
                    this.$emit('refreshDataList')
                  }
                })
              } else {
                this.$message.error(data.msg)
              }
            })
          }
        })
      }
    }
  }
</script>
