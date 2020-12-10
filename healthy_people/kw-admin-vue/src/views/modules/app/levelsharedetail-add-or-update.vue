<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="账户id" prop="userId">
      <el-input v-model="dataForm.userId" placeholder="账户id"></el-input>
    </el-form-item>
    <el-form-item label="分红金额" prop="shareMoney">
      <el-input v-model="dataForm.shareMoney" placeholder="分红金额"></el-input>
    </el-form-item>
    <el-form-item label="世代金额" prop="shareSdMoney">
      <el-input v-model="dataForm.shareSdMoney" placeholder="世代金额"></el-input>
    </el-form-item>
    <el-form-item label="创建时间" prop="createDate">
      <el-input v-model="dataForm.createDate" placeholder="创建时间"></el-input>
    </el-form-item>
    <el-form-item label="交易id" prop="transId">
      <el-input v-model="dataForm.transId" placeholder="交易id"></el-input>
    </el-form-item>
    <el-form-item label="汇总标计" prop="gatherStatus">
      <el-input v-model="dataForm.gatherStatus" placeholder="汇总标计"></el-input>
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
          shareMoney: '',
          shareSdMoney: '',
          createDate: '',
          transId: '',
          gatherStatus: ''
        },
        dataRule: {
          userId: [
            { required: true, message: '账户id不能为空', trigger: 'blur' }
          ],
          shareMoney: [
            { required: true, message: '分红金额不能为空', trigger: 'blur' }
          ],
          shareSdMoney: [
            { required: true, message: '世代金额不能为空', trigger: 'blur' }
          ],
          createDate: [
            { required: true, message: '创建时间不能为空', trigger: 'blur' }
          ],
          transId: [
            { required: true, message: '交易id不能为空', trigger: 'blur' }
          ],
          gatherStatus: [
            { required: true, message: '汇总标计不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/app/levelsharedetail/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.userId = data.levelShareDetail.userId
                this.dataForm.shareMoney = data.levelShareDetail.shareMoney
                this.dataForm.shareSdMoney = data.levelShareDetail.shareSdMoney
                this.dataForm.createDate = data.levelShareDetail.createDate
                this.dataForm.transId = data.levelShareDetail.transId
                this.dataForm.gatherStatus = data.levelShareDetail.gatherStatus
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
              url: this.$http.adornUrl(`/app/levelsharedetail/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'userId': this.dataForm.userId,
                'shareMoney': this.dataForm.shareMoney,
                'shareSdMoney': this.dataForm.shareSdMoney,
                'createDate': this.dataForm.createDate,
                'transId': this.dataForm.transId,
                'gatherStatus': this.dataForm.gatherStatus
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
