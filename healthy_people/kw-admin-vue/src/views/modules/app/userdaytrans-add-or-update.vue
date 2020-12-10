<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="日期" prop="dateTime">
      <el-input v-model="dataForm.dateTime" placeholder="日期"></el-input>
    </el-form-item>
    <el-form-item label="购买金额" prop="buyAmount">
      <el-input v-model="dataForm.buyAmount" placeholder="购买金额"></el-input>
    </el-form-item>
    <el-form-item label="出售金额" prop="sellAmount">
      <el-input v-model="dataForm.sellAmount" placeholder="出售金额"></el-input>
    </el-form-item>
    <el-form-item label="用户id" prop="userId">
      <el-input v-model="dataForm.userId" placeholder="用户id"></el-input>
    </el-form-item>
    <el-form-item label="手续费" prop="fee">
      <el-input v-model="dataForm.fee" placeholder="手续费"></el-input>
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
          dateTime: '',
          buyAmount: '',
          sellAmount: '',
          userId: '',
          dateKey: 0,
          fee: ''
        },
        dataRule: {
          dateTime: [
            { required: true, message: '日期不能为空', trigger: 'blur' }
          ],
          buyAmount: [
            { required: true, message: '购买金额不能为空', trigger: 'blur' }
          ],
          sellAmount: [
            { required: true, message: '出售金额不能为空', trigger: 'blur' }
          ],
          userId: [
            { required: true, message: '用户id不能为空', trigger: 'blur' }
          ],
          fee: [
            { required: true, message: '手续费不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.dateKey = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.dateKey) {
            this.$http({
              url: this.$http.adornUrl(`/app/userdaytrans/info/${this.dataForm.dateKey}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.dateTime = data.userDaytrans.dateTime
                this.dataForm.buyAmount = data.userDaytrans.buyAmount
                this.dataForm.sellAmount = data.userDaytrans.sellAmount
                this.dataForm.userId = data.userDaytrans.userId
                this.dataForm.fee = data.userDaytrans.fee
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
              url: this.$http.adornUrl(`/app/userdaytrans/${!this.dataForm.dateKey ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'dateTime': this.dataForm.dateTime,
                'buyAmount': this.dataForm.buyAmount,
                'sellAmount': this.dataForm.sellAmount,
                'userId': this.dataForm.userId,
                'dateKey': this.dataForm.dateKey || undefined,
                'fee': this.dataForm.fee
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
