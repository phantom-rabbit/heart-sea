<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="会员ID" prop="userId">
      <el-input v-model="dataForm.userId" placeholder="会员ID"></el-input>
    </el-form-item>
    <el-form-item label="矿机ID" prop="ltcId">
      <el-input v-model="dataForm.ltcId" placeholder="矿机ID"></el-input>
    </el-form-item>
    <el-form-item label="收益" prop="income">
      <el-input v-model="dataForm.income" placeholder="收益"></el-input>
    </el-form-item>
    <el-form-item label="状态： 0 待领取  1 已领取   2 已作废" prop="status">
      <el-input v-model="dataForm.status" placeholder="状态： 0 待领取  1 已领取   2 已作废"></el-input>
    </el-form-item>
    <el-form-item label="产生日期" prop="createDate">
      <el-input v-model="dataForm.createDate" placeholder="产生日期"></el-input>
    </el-form-item>
    <el-form-item label="领取时间" prop="incomeTime">
      <el-input v-model="dataForm.incomeTime" placeholder="领取时间"></el-input>
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
          ltcId: '',
          income: '',
          status: '',
          createDate: '',
          incomeTime: ''
        },
        dataRule: {
          userId: [
            { required: true, message: '会员ID不能为空', trigger: 'blur' }
          ],
          ltcId: [
            { required: true, message: '矿机ID不能为空', trigger: 'blur' }
          ],
          income: [
            { required: true, message: '收益不能为空', trigger: 'blur' }
          ],
          status: [
            { required: true, message: '状态： 0 待领取  1 已领取   2 已作废不能为空', trigger: 'blur' }
          ],
          createDate: [
            { required: true, message: '产生日期不能为空', trigger: 'blur' }
          ],
          incomeTime: [
            { required: true, message: '领取时间不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/app/ltclog/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.userId = data.ltcLog.userId
                this.dataForm.ltcId = data.ltcLog.ltcId
                this.dataForm.income = data.ltcLog.income
                this.dataForm.status = data.ltcLog.status
                this.dataForm.createDate = data.ltcLog.createDate
                this.dataForm.incomeTime = data.ltcLog.incomeTime
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
              url: this.$http.adornUrl(`/app/ltclog/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'userId': this.dataForm.userId,
                'ltcId': this.dataForm.ltcId,
                'income': this.dataForm.income,
                'status': this.dataForm.status,
                'createDate': this.dataForm.createDate,
                'incomeTime': this.dataForm.incomeTime
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
