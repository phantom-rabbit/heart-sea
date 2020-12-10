<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="挂买人id" prop="buyerId">
      <el-input v-model="dataForm.buyerId" placeholder="挂买人id"></el-input>
    </el-form-item>
    <el-form-item label="挂买总量" prop="amount">
      <el-input v-model="dataForm.amount" placeholder="挂买总量"></el-input>
    </el-form-item>
    <el-form-item label="售卖人id" prop="sellerId">
      <el-input v-model="dataForm.sellerId" placeholder="售卖人id"></el-input>
    </el-form-item>
    <el-form-item label="创建时间" prop="createTime">
      <el-input v-model="dataForm.createTime" placeholder="创建时间"></el-input>
    </el-form-item>
    <el-form-item label="支付方式(支付宝/usdt/银行卡)" prop="payType">
      <el-input v-model="dataForm.payType" placeholder="支付方式(支付宝/usdt/银行卡)"></el-input>
    </el-form-item>
    <el-form-item label="状态 (0待上传交易凭证1完成)" prop="status">
      <el-input v-model="dataForm.status" placeholder="状态 (0待上传交易凭证1完成)"></el-input>
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
          amount: '',
          sellerId: '',
          createTime: '',
          payType: '',
          status: ''
        },
        dataRule: {
          buyerId: [
            { required: true, message: '挂买人id不能为空', trigger: 'blur' }
          ],
          amount: [
            { required: true, message: '挂买总量不能为空', trigger: 'blur' }
          ],
          sellerId: [
            { required: true, message: '售卖人id不能为空', trigger: 'blur' }
          ],
          createTime: [
            { required: true, message: '创建时间不能为空', trigger: 'blur' }
          ],
          payType: [
            { required: true, message: '支付方式(支付宝/usdt/银行卡)不能为空', trigger: 'blur' }
          ],
          status: [
            { required: true, message: '状态 (0待上传交易凭证1完成)不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/app/buytrans/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.buyerId = data.buyTrans.buyerId
                this.dataForm.amount = data.buyTrans.amount
                this.dataForm.sellerId = data.buyTrans.sellerId
                this.dataForm.createTime = data.buyTrans.createTime
                this.dataForm.payType = data.buyTrans.payType
                this.dataForm.status = data.buyTrans.status
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
              url: this.$http.adornUrl(`/app/buytrans/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'buyerId': this.dataForm.buyerId,
                'amount': this.dataForm.amount,
                'sellerId': this.dataForm.sellerId,
                'createTime': this.dataForm.createTime,
                'payType': this.dataForm.payType,
                'status': this.dataForm.status
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
