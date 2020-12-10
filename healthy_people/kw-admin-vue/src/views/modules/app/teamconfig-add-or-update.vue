<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="180px">
    <el-form-item label="战队名" prop="team">
      <el-input v-model="dataForm.team" placeholder="战队名"></el-input>
    </el-form-item>
    <el-form-item label="战队领导人id" prop="leaderId">
      <el-input v-model="dataForm.leaderId" placeholder="战队领导人id"></el-input>
    </el-form-item>
    <!--<el-form-item label="创建时间" prop="createTime">-->
      <!--<el-input v-model="dataForm.createTime" placeholder="创建时间"></el-input>-->
    <!--</el-form-item>-->
    <!--<el-form-item label="创建人" prop="createUserId">-->
      <!--<el-input v-model="dataForm.createUserId" placeholder="创建人"></el-input>-->
    <!--</el-form-item>-->
      <el-form-item label="状态" prop="status">
        <el-radio-group v-model="dataForm.status">
          <el-radio :label="-1" >失败</el-radio>
          <el-radio :label="0" >待审核</el-radio>
          <el-radio :label="1" >通过</el-radio>
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
        visible: false,
        dataForm: {
          id: 0,
          status: '',
          team: '',
          leaderId: '',
          createTime: '',
          createUserId: ''
        },
        dataRule: {
          team: [
            { required: true, message: '战队名不能为空', trigger: 'blur' }
          ],
          status: [
            { required: true, message: '状态不能为空', trigger: 'blur' }
          ],
          leaderId: [
            { required: true, message: '战队领导人id不能为空', trigger: 'blur' }
          ],
          createTime: [
            { required: true, message: '创建时间不能为空', trigger: 'blur' }
          ],
          createUserId: [
            { required: true, message: '创建人不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/app/teamconfig/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.team = data.teamConfig.team
                this.dataForm.leaderId = data.teamConfig.leaderId
                this.dataForm.createTime = data.teamConfig.createTime
                this.dataForm.createUserId = data.teamConfig.createUserId
                this.dataForm.status = data.teamConfig.status
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
              url: this.$http.adornUrl(`/app/teamconfig/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'team': this.dataForm.team,
                'leaderId': this.dataForm.leaderId,
                'createTime': this.dataForm.createTime,
                'status': this.dataForm.status,
                'createUserId': this.dataForm.createUserId
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
