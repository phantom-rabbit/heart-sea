<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="" prop="userId">
      <el-input v-model="dataForm.userId" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="动力值" prop="power">
      <el-input v-model="dataForm.power" placeholder="动力值"></el-input>
    </el-form-item>
    <el-form-item label="母币" prop="parentMoney">
      <el-input v-model="dataForm.parentMoney" placeholder="母币"></el-input>
    </el-form-item>
    <el-form-item label="子币" prop="sonMoney">
      <el-input v-model="dataForm.sonMoney" placeholder="子币"></el-input>
    </el-form-item>
    <el-form-item label="动力源" prop="powerSource">
      <el-input v-model="dataForm.powerSource" placeholder="动力源"></el-input>
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
          power: '',
          parentMoney: '',
          sonMoney: '',
          powerSource: ''
        },
        dataRule: {
          userId: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          power: [
            { required: true, message: '动力值不能为空', trigger: 'blur' }
          ],
          parentMoney: [
            { required: true, message: '母币不能为空', trigger: 'blur' }
          ],
          sonMoney: [
            { required: true, message: '子币不能为空', trigger: 'blur' }
          ],
          powerSource: [
            { required: true, message: '动力源不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/app/accountmoney/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.userId = data.accountMoney.userId
                this.dataForm.power = data.accountMoney.power
                this.dataForm.parentMoney = data.accountMoney.parentMoney
                this.dataForm.sonMoney = data.accountMoney.sonMoney
                this.dataForm.powerSource = data.accountMoney.powerSource
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
              url: this.$http.adornUrl(`/app/accountmoney/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'userId': this.dataForm.userId,
                'power': this.dataForm.power,
                'parentMoney': this.dataForm.parentMoney,
                'sonMoney': this.dataForm.sonMoney,
                'powerSource': this.dataForm.powerSource
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
