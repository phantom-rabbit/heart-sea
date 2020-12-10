<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="名称" prop="name">
      <el-input v-model="dataForm.name" placeholder="名称"></el-input>
    </el-form-item>
    <el-form-item label="推荐人数" prop="inviteNumber">
      <el-input v-model="dataForm.inviteNumber" placeholder="推荐人数"></el-input>
    </el-form-item>
    <el-form-item label="邀请奖励OPC" prop="awrdParent">
      <el-input v-model="dataForm.awrdParent" placeholder="邀请奖励OPC"></el-input>
    </el-form-item>
    <el-form-item label="邀请奖励动力" prop="awrdPower">
      <el-input v-model="dataForm.awrdPower" placeholder="邀请奖励动力"></el-input>
    </el-form-item>
    <el-form-item label="交易手续费" prop="transFee">
      <el-input v-model="dataForm.transFee" placeholder="交易手续费"></el-input>
    </el-form-item>
    <!--<el-form-item label="创建时间" prop="createTime">-->
      <!--<el-input v-model="dataForm.createTime" placeholder="创建时间"></el-input>-->
    <!--</el-form-item>-->
    <!--<el-form-item label="创建人id" prop="createUserId">-->
      <!--<el-input v-model="dataForm.createUserId" placeholder="创建人id"></el-input>-->
    <!--</el-form-item>-->
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
          name: '',
          inviteNumber: '',
          awrdParent: '',
          awrdPower: '',
          transFee: '',
          createTime: '',
          createUserId: ''
        },
        dataRule: {
          name: [
            { required: true, message: '名称不能为空', trigger: 'blur' }
          ],
          inviteNumber: [
            { required: true, message: '推荐人数不能为空', trigger: 'blur' }
          ],
          awrdParent: [
            { required: true, message: '邀请奖励OPC不能为空', trigger: 'blur' }
          ],
          awrdPower: [
            { required: true, message: '邀请奖励动力不能为空', trigger: 'blur' }
          ],
          transFee: [
            { required: true, message: '交易手续费不能为空', trigger: 'blur' }
          ],
          createTime: [
            { required: true, message: '创建时间不能为空', trigger: 'blur' }
          ],
          createUserId: [
            { required: true, message: '创建人id不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/app/vipconfig/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.name = data.vipConfig.name
                this.dataForm.inviteNumber = data.vipConfig.inviteNumber
                this.dataForm.awrdParent = data.vipConfig.awrdParent
                this.dataForm.awrdPower = data.vipConfig.awrdPower
                this.dataForm.transFee = data.vipConfig.transFee
                this.dataForm.createTime = data.vipConfig.createTime
                this.dataForm.createUserId = data.vipConfig.createUserId
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
              url: this.$http.adornUrl(`/app/vipconfig/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'name': this.dataForm.name,
                'inviteNumber': this.dataForm.inviteNumber,
                'awrdParent': this.dataForm.awrdParent,
                'awrdPower': this.dataForm.awrdPower,
                'transFee': this.dataForm.transFee,
                'createTime': this.dataForm.createTime,
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
