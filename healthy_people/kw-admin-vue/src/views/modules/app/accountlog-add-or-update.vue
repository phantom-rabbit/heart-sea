<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="用户id" prop="userId">
      <el-input v-model="dataForm.userId" placeholder="用户id"></el-input>
    </el-form-item>
    <el-form-item label="记录种类" prop="logType">
      <el-input v-model="dataForm.logType" placeholder="记录种类"></el-input>
    </el-form-item>
    <el-form-item label="备注" prop="mark">
      <el-input v-model="dataForm.mark" placeholder="备注"></el-input>
    </el-form-item>
    <el-form-item label="操作金额" prop="money">
      <el-input v-model="dataForm.money" placeholder="操作金额"></el-input>
    </el-form-item>
    <el-form-item label="操作后金额" prop="account">
      <el-input v-model="dataForm.account" placeholder="操作后金额"></el-input>
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
          userId: '',
          logType: '',
          mark: '',
          money: '',
          account: '',
          createTime: ''
        },
        dataRule: {
          userId: [
            { required: true, message: '用户id不能为空', trigger: 'blur' }
          ],
          logType: [
            { required: true, message: '记录种类不能为空', trigger: 'blur' }
          ],
          mark: [
            { required: true, message: '备注不能为空', trigger: 'blur' }
          ],
          money: [
            { required: true, message: '操作金额不能为空', trigger: 'blur' }
          ],
          account: [
            { required: true, message: '操作后金额不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/app/accountlog/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.userId = data.accountLog.userId
                this.dataForm.logType = data.accountLog.logType
                this.dataForm.mark = data.accountLog.mark
                this.dataForm.money = data.accountLog.money
                this.dataForm.account = data.accountLog.account
                this.dataForm.createTime = data.accountLog.createTime
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
              url: this.$http.adornUrl(`/app/accountlog/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'userId': this.dataForm.userId,
                'logType': this.dataForm.logType,
                'mark': this.dataForm.mark,
                'money': this.dataForm.money,
                'account': this.dataForm.account,
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
