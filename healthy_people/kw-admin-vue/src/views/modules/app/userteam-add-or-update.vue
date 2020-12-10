<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="邀请人ID" prop="inviterUserId">
      <el-input v-model="dataForm.inviterUserId" placeholder="邀请人ID"></el-input>
    </el-form-item>
    <el-form-item label="树结构如(user_id=6 inviter_id=7,mark=,1,6,7,) 可以清晰反应战队里所有人的id" prop="mark">
      <el-input v-model="dataForm.mark" placeholder="树结构如(user_id=6 inviter_id=7,mark=,1,6,7,) 可以清晰反应战队里所有人的id"></el-input>
    </el-form-item>
    <el-form-item label="" prop="addtime">
      <el-input v-model="dataForm.addtime" placeholder=""></el-input>
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
          userid: 0,
          inviterUserId: '',
          mark: '',
          addtime: ''
        },
        dataRule: {
          inviterUserId: [
            { required: true, message: '邀请人ID不能为空', trigger: 'blur' }
          ],
          mark: [
            { required: true, message: '树结构如(user_id=6 inviter_id=7,mark=,1,6,7,) 可以清晰反应战队里所有人的id不能为空', trigger: 'blur' }
          ],
          addtime: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.userid = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.userid) {
            this.$http({
              url: this.$http.adornUrl(`/app/userteam/info/${this.dataForm.userid}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.inviterUserId = data.userTeam.inviterUserId
                this.dataForm.mark = data.userTeam.mark
                this.dataForm.addtime = data.userTeam.addtime
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
              url: this.$http.adornUrl(`/app/userteam/${!this.dataForm.userid ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'userid': this.dataForm.userid || undefined,
                'inviterUserId': this.dataForm.inviterUserId,
                'mark': this.dataForm.mark,
                'addtime': this.dataForm.addtime
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
