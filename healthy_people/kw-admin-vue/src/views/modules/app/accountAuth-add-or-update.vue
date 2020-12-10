<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="名字" prop="realName">
      <el-input v-model="dataForm.realName" placeholder="名字"></el-input>
    </el-form-item>
    <el-form-item label="身份证" prop="idCard">
      <el-input v-model="dataForm.idCard" placeholder="身份证"></el-input>
    </el-form-item>
    <el-form-item label="状态" prop="realStatus">
      <el-radio-group v-model="dataForm.realStatus">
        <el-radio :label="-1" >失败</el-radio>
        <el-radio :label="0" >未提交</el-radio>
        <el-radio :label="2" >通过</el-radio>
      </el-radio-group>
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
        options: [{
          value: 0,
          label: '否'
        }, {
          value: 1,
          label: '是'
        }],
        visible: false,
        dataForm: {
          id: 0,
          realName: '',
          idCard: '',
          realStatus: ''
        },
        dataRule: {
          realStatus: [
            { required: true, message: '认证状态不可为空', trigger: 'blur' }
          ],
          idCard: [
            { required: true, message: '身份证号码不可为空', trigger: 'blur' }
          ],
          realName: [
            { required: true, message: '姓名不可为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/app/account/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.id = data.account.id
                this.dataForm.realName = data.account.realName
                this.dataForm.idCard = data.account.idCard
                this.dataForm.realStatus = data.account.realStatus
                this.dataForm.userId = data.account.userId
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
              url: this.$http.adornUrl(`/app/account/auth`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'realName': this.dataForm.realName,
                'idCard': this.dataForm.idCard,
                'realStatus': this.dataForm.realStatus,
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
