<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="名称" prop="name">
      <el-input v-model="dataForm.name" placeholder="名称"></el-input>
    </el-form-item>
    <el-form-item label="OPC数量" prop="parentPrice">
      <el-input v-model="dataForm.parentPrice" placeholder="OPC数量"></el-input>
    </el-form-item>
    <el-form-item label="OPC每日收益" prop="incomeParentPerday">
      <el-input v-model="dataForm.incomeParentPerday" placeholder="OPC每日收益"></el-input>
    </el-form-item>
    <el-form-item label="日期" prop="days">
      <el-input v-model="dataForm.days" placeholder="日期"></el-input>
    </el-form-item>
    <el-form-item label="总持有" prop="maxHold">
      <el-input v-model="dataForm.maxHold" placeholder="总持有"></el-input>
    </el-form-item>
    <el-form-item label="MAC每日收益" prop="incomeSonPerday">
      <el-input v-model="dataForm.incomeSonPerday" placeholder="MAC每日收益"></el-input>
    </el-form-item>
    <el-form-item label="动力值 " prop="power">
      <el-input v-model="dataForm.power" placeholder="动力值 "></el-input>
    </el-form-item>

      <el-form-item label="配置状态 " prop="configStatus">
        <el-input v-model="dataForm.configStatus" placeholder="配置状态 "></el-input>
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
          name: '',
          id: 0,
          parentPrice: '',
          incomeParentPerday: '',
          days: '',
          maxHold: '',
          incomeSonPerday: '',
          power: '',
          createTime: '',
          createUserId: '',
          configStatus:''
        },
        dataRule: {
          name: [
            { required: true, message: '名称不能为空', trigger: 'blur' }
          ],
          parentPrice: [
            { required: true, message: 'OPC数量不能为空', trigger: 'blur' }
          ],
          incomeParentPerday: [
            { required: true, message: 'OPC每日收益不能为空', trigger: 'blur' }
          ],
          days: [
            { required: true, message: '日期不能为空', trigger: 'blur' }
          ],
          maxHold: [
            { required: true, message: '总持有不能为空', trigger: 'blur' }
          ],
          incomeSonPerday: [
            { required: true, message: 'MAC每日收益不能为空', trigger: 'blur' }
          ],
          power: [
            { required: true, message: '动力值 不能为空', trigger: 'blur' }
          ],
          createTime: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          createUserId: [
            { required: true, message: '用户id不能为空', trigger: 'blur' }
          ],
          configStatus: [
            { required: true, message: '配置状态不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/app/ltcconfig/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.name = data.ltcConfig.name
                this.dataForm.parentPrice = data.ltcConfig.parentPrice
                this.dataForm.incomeParentPerday = data.ltcConfig.incomeParentPerday
                this.dataForm.days = data.ltcConfig.days
                this.dataForm.maxHold = data.ltcConfig.maxHold
                this.dataForm.incomeSonPerday = data.ltcConfig.incomeSonPerday
                this.dataForm.power = data.ltcConfig.power
                this.dataForm.createTime = data.ltcConfig.createTime
                this.dataForm.createUserId = data.ltcConfig.createUserId
                this.dataForm.configStatus = data.ltcConfig.configStatus


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
              url: this.$http.adornUrl(`/app/ltcconfig/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'name': this.dataForm.name,
                'id': this.dataForm.id || undefined,
                'parentPrice': this.dataForm.parentPrice,
                'incomeParentPerday': this.dataForm.incomeParentPerday,
                'days': this.dataForm.days,
                'maxHold': this.dataForm.maxHold,
                'incomeSonPerday': this.dataForm.incomeSonPerday,
                'power': this.dataForm.power,
                'createTime': this.dataForm.createTime,
                'createUserId': this.dataForm.createUserId,
                'configStatus':this.dataForm.configStatus
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
