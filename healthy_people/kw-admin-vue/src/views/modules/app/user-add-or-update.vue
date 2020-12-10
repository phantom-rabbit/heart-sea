<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
      <el-form-item label="手机号码" prop="mobile">
        <el-input v-model="dataForm.mobile"  placeholder="手机号码"></el-input>
      </el-form-item>
    <el-form-item label="用户名" prop="userName">
      <el-input v-model="dataForm.userName" placeholder="用户名"></el-input>
    </el-form-item>
    <!--<el-form-item label="密码" prop="password">-->
      <!--<el-input type="password" v-model="dataForm.password" placeholder="密码"></el-input>-->
    <!--</el-form-item>-->
    <el-form-item label="状态" prop="status">
      <el-radio-group v-model="dataForm.status">
        <el-radio :label="0" >启用</el-radio>
        <el-radio :label="1" >禁用</el-radio>
      </el-radio-group>
    </el-form-item>

    <el-form-item label="邀请码" prop="inviteCode">
      <el-input v-model="dataForm.inviteCode" disabled placeholder="邀请码"></el-input>
    </el-form-item>
      <el-form-item label="登录时间" prop="lastLoginTime">
        <el-input v-model="dataForm.lastLoginTime"  disabled placeholder="登录时间"></el-input>
      </el-form-item>
      <el-form-item label="登录IP" prop="lastLoginIp">
        <el-input v-model="dataForm.lastLoginIp" disabled placeholder="登录IP"></el-input>
      </el-form-item>
    <el-form-item label="注册时间" prop="createDate">
      <el-input v-model="dataForm.createDate" disabled placeholder="注册时间"></el-input>
    </el-form-item>
    <el-form-item label="注册IP" prop="createIp">
      <el-input v-model="dataForm.createIp" disabled placeholder="注册IP"></el-input>
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
          userName: '',
          password: '',
          lastLoginTime: '',
          lastLoginIp: '',
          mobile: '',
          inviteCode: '',
          createDate: '',
          createIp: '',
          status: ''
        },
        dataRule: {
          userName: [
            { required: true, message: '用户名不能为空', trigger: 'blur' }
          ],
          password: [
            { required: true, message: '密码不能为空', trigger: 'blur' }
          ],
          mobile: [
            { required: true, message: '手机号码不能为空', trigger: 'blur' }
          ],
          status: [
            { required: true, message: '状态：0 启用 1 禁用不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/app/user/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.userName = data.user.userName
                this.dataForm.password = data.user.password
                this.dataForm.lastLoginTime = data.user.lastLoginTime
                this.dataForm.lastLoginIp = data.user.lastLoginIp
                this.dataForm.mobile = data.user.mobile
                this.dataForm.inviteCode = data.user.inviteCode
                this.dataForm.createDate = data.user.createDate
                this.dataForm.createIp = data.user.createIp
                this.dataForm.status = data.user.status
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
              url: this.$http.adornUrl(`/app/user/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'userName': this.dataForm.userName,
                'password': this.dataForm.password,
                'lastLoginTime': this.dataForm.lastLoginTime,
                'lastLoginIp': this.dataForm.lastLoginIp,
                'mobile': this.dataForm.mobile,
                'inviteCode': this.dataForm.inviteCode,
                'createDate': this.dataForm.createDate,
                'createIp': this.dataForm.createIp,
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
