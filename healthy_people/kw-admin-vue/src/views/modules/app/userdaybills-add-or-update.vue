<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="" prop="dateTime">
      <el-input v-model="dataForm.dateTime" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="动力值" prop="power">
      <el-input v-model="dataForm.power" placeholder="动力值"></el-input>
    </el-form-item>
    <el-form-item label="动力源" prop="powerSource">
      <el-input v-model="dataForm.powerSource" placeholder="动力源"></el-input>
    </el-form-item>
    <el-form-item label="用户id" prop="userId">
      <el-input v-model="dataForm.userId" placeholder="用户id"></el-input>
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
          power: '',
          powerSource: '',
          userId: '',
          dateKey: 0,
        },
        dataRule: {
          dateTime: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          power: [
            { required: true, message: '动力值不能为空', trigger: 'blur' }
          ],
          powerSource: [
            { required: true, message: '动力源不能为空', trigger: 'blur' }
          ],
          userId: [
            { required: true, message: '用户id不能为空', trigger: 'blur' }
          ],
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
              url: this.$http.adornUrl(`/app/userdaybills/info/${this.dataForm.dateKey}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.dateTime = data.userDaybills.dateTime
                this.dataForm.power = data.userDaybills.power
                this.dataForm.powerSource = data.userDaybills.powerSource
                this.dataForm.userId = data.userDaybills.userId
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
              url: this.$http.adornUrl(`/app/userdaybills/${!this.dataForm.dateKey ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'dateTime': this.dataForm.dateTime,
                'power': this.dataForm.power,
                'powerSource': this.dataForm.powerSource,
                'userId': this.dataForm.userId,
                'dateKey': this.dataForm.dateKey || undefined,
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
