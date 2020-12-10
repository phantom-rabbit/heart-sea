<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="操作人" prop="userId">
      <el-input v-model="dataForm.userId" placeholder="操作人"></el-input>
    </el-form-item>
    <el-form-item label="内容" prop="content">
      <el-input v-model="dataForm.content" placeholder="内容"></el-input>
    </el-form-item>
    <el-form-item label="手机号码" prop="mobile">
      <el-input v-model="dataForm.mobile" placeholder="手机号码"></el-input>
    </el-form-item>
    <el-form-item label="创建时间" prop="createDate">
      <el-input v-model="dataForm.createDate" placeholder="创建时间"></el-input>
    </el-form-item>
    <el-form-item label="返回消息" prop="returnMsg">
      <el-input v-model="dataForm.returnMsg" placeholder="返回消息"></el-input>
    </el-form-item>
    <el-form-item label="" prop="type">
      <el-input v-model="dataForm.type" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="success">
      <el-input v-model="dataForm.success" placeholder=""></el-input>
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
          content: '',
          mobile: '',
          createDate: '',
          returnMsg: '',
          type: '',
          success: ''
        },
        dataRule: {
          userId: [
            { required: true, message: '操作人不能为空', trigger: 'blur' }
          ],
          content: [
            { required: true, message: '内容不能为空', trigger: 'blur' }
          ],
          mobile: [
            { required: true, message: '手机号码不能为空', trigger: 'blur' }
          ],
          createDate: [
            { required: true, message: '创建时间不能为空', trigger: 'blur' }
          ],
          returnMsg: [
            { required: true, message: '返回消息不能为空', trigger: 'blur' }
          ],
          type: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          success: [
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
              url: this.$http.adornUrl(`/app/smslog/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.userId = data.smsLog.userId
                this.dataForm.content = data.smsLog.content
                this.dataForm.mobile = data.smsLog.mobile
                this.dataForm.createDate = data.smsLog.createDate
                this.dataForm.returnMsg = data.smsLog.returnMsg
                this.dataForm.type = data.smsLog.type
                this.dataForm.success = data.smsLog.success
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
              url: this.$http.adornUrl(`/app/smslog/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'userId': this.dataForm.userId,
                'content': this.dataForm.content,
                'mobile': this.dataForm.mobile,
                'createDate': this.dataForm.createDate,
                'returnMsg': this.dataForm.returnMsg,
                'type': this.dataForm.type,
                'success': this.dataForm.success
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
