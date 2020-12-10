<template>
  <el-dialog
    :title="!dataForm.id ? '系统发放激活码' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="120px">
      <el-form-item label="是否指定用户" prop="isUser">
        <el-radio-group v-model="status">
          <el-radio :label="0" >否</el-radio>
          <el-radio :label="1" >是</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="分配给" prop="userId" v-if="status === 1">
        <el-select v-model="dataForm.userId" filterable placeholder="请选择手机号码">
          <el-option
            v-for="item in userList"
            :key="item.id"
            :label="item.mobile"
            :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="分配数量" prop="ct">
        <el-input-number v-model="dataForm.ct" :min="1" :max="10000" label="请输入"></el-input-number>
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
        userList: [],
        status: 1,
        dataForm: {
          userId: '',
          ct: 0
        },
        dataRule: {
          ct: [
            { required: true, message: '请输入数量', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init () {
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          this.$http({
            url: this.$http.adornUrl(`/app/user/all`),
            method: 'get'
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.userList = data.list
            }
          })
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (this.status === 1 && (this.dataForm.userId === '' || !this.dataForm.userId)) {
            this.$message.error('请选择分配给谁')
            return
          }
          if (valid) {
            this.$confirm(`确定执行吗`, '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
              let uid = this.dataForm.userId || 0;
              this.$http({
                url: this.$http.adornUrl(`/app/activationcode/general/${uid}/${this.dataForm.ct}`),
                method: 'post'
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
            })
          }
        })
      }
    }
  }
</script>
