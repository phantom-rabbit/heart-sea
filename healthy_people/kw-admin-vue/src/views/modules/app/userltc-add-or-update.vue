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
    <el-form-item label="最大持有量" prop="maxHold">
      <el-input v-model="dataForm.maxHold" placeholder="最大持有量"></el-input>
    </el-form-item>
    <el-form-item label="MAC每日收益" prop="incomeSonPerday">
      <el-input v-model="dataForm.incomeSonPerday" placeholder="MAC每日收益"></el-input>
    </el-form-item>
    <el-form-item label="动力值 " prop="power">
      <el-input v-model="dataForm.power" placeholder="动力值 "></el-input>
    </el-form-item>
    <el-form-item label="矿机状态0待启动 1启动中" prop="ltcStatus">
      <el-input v-model="dataForm.ltcStatus" placeholder="矿机状态0待启动 1启动中"></el-input>
    </el-form-item>
    <!--<el-form-item label="OPC采集状态 (需要用户采集动作)" prop="settleStatus">-->
      <!--<el-input v-model="dataForm.settleStatus" placeholder="OPC采集状态 (需要用户采集动作)"></el-input>-->
    <!--</el-form-item>-->
    <el-form-item label="矿机id" prop="ltcId">
      <el-input v-model="dataForm.ltcId" placeholder="矿机id"></el-input>
    </el-form-item>
    <el-form-item label="创建时间" prop="createTime">
      <el-input v-model="dataForm.createTime" placeholder="创建时间"></el-input>
    </el-form-item>
    <el-form-item label="启动时间" prop="startTime">
      <el-input v-model="dataForm.startTime" placeholder="启动时间"></el-input>
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
          ltcStatus: '',
          settleStatus: '',
          ltcId: '',
          createTime: '',
          startTime: ''
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
            { required: true, message: '最大持有量不能为空', trigger: 'blur' }
          ],
          incomeSonPerday: [
            { required: true, message: 'MAC每日收益不能为空', trigger: 'blur' }
          ],
          power: [
            { required: true, message: '动力值 不能为空', trigger: 'blur' }
          ],
          ltcStatus: [
            { required: true, message: '矿机状态0待启动 1启动中不能为空', trigger: 'blur' }
          ],
          settleStatus: [
            { required: true, message: 'OPC采集状态 (需要用户采集动作)不能为空', trigger: 'blur' }
          ],
          ltcId: [
            { required: true, message: '矿机id不能为空', trigger: 'blur' }
          ],
          createTime: [
            { required: true, message: '创建时间不能为空', trigger: 'blur' }
          ],
          startTime: [
            { required: true, message: '启动时间不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/app/userltc/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.name = data.userLtc.name
                this.dataForm.parentPrice = data.userLtc.parentPrice
                this.dataForm.incomeParentPerday = data.userLtc.incomeParentPerday
                this.dataForm.days = data.userLtc.days
                this.dataForm.maxHold = data.userLtc.maxHold
                this.dataForm.incomeSonPerday = data.userLtc.incomeSonPerday
                this.dataForm.power = data.userLtc.power
                this.dataForm.ltcStatus = data.userLtc.ltcStatus
                this.dataForm.settleStatus = data.userLtc.settleStatus
                this.dataForm.ltcId = data.userLtc.ltcId
                this.dataForm.createTime = data.userLtc.createTime
                this.dataForm.startTime = data.userLtc.startTime
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
              url: this.$http.adornUrl(`/app/userltc/${!this.dataForm.id ? 'save' : 'update'}`),
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
                'ltcStatus': this.dataForm.ltcStatus,
                'settleStatus': this.dataForm.settleStatus,
                'ltcId': this.dataForm.ltcId,
                'createTime': this.dataForm.createTime,
                'startTime': this.dataForm.startTime
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
