<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="150px">
      <el-form-item label="创世人" prop="userId">
        <el-select v-model="dataForm.userId" filterable placeholder="选择创始人">
          <el-option
            v-for="item in userList"
            :key="item.id"
            :label="item.mobile"
            :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="创世节点名称" prop="name">
        <el-input v-model="dataForm.name" placeholder="创世节点名称" style="width: 300px"></el-input>
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
        dataForm: {
          id: 0,
          userId: '',
          createDate: '',
          name: ''
        },
        dataRule: {
          userId: [
            { required: true, message: '创建人不能为空', trigger: 'blur' }
          ],
          name: [
            { required: true, message: '创世节点名称不能为空', trigger: 'blur' }
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
          this.$http({
            url: this.$http.adornUrl(`/app/user/all`),
            method: 'get'
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.userList = data.list
            }
          })
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/app/genesis/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.userId = data.genesis.userId
                this.dataForm.name = data.genesis.name
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
              url: this.$http.adornUrl(`/app/genesis/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'userId': this.dataForm.userId,
                'name': this.dataForm.name
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
