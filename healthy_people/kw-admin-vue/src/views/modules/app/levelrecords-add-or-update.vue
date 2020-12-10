<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form inline :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="200px">
    <el-form-item label="申请人:">
      <div>{{dataForm.mobile}}</div>
    </el-form-item>
    <el-form-item label="申请升级等级:">
     <div>{{dataForm.levelName}}</div>
    </el-form-item>
    <el-form-item label="直推人数:" >
      <div>{{dataForm.inviterCount}}</div>
    </el-form-item>
    <el-form-item label="团队动力值:" >
      <div>{{dataForm.power}}</div>
    </el-form-item>
    <el-form-item label="团队动力源:" >
      <div>{{dataForm.powerSource}}</div>
    </el-form-item>
    <el-form-item label="直推好友同等级数:" >
      <div>{{dataForm.eqLevelCount}}</div>
    </el-form-item>
    <el-form-item label="大区动力值:" >
      <div>{{dataForm.dqPower}}</div>
    </el-form-item>
    <el-form-item label="大区动力源:">
      <div>{{dataForm.dqPowerSource}}</div>
    </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-radio-group v-model="dataForm.status">
          <el-radio :label="-1" >不通过</el-radio>
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
          userId: '',
          levelId: '',
          inviterCount: '',
          power: '',
          powerSource: '',
          eqLevelCount: '',
          updateTime: '',
          dqPower: '',
          levelName: '',
          dqPowerSource: '',
          status: ''
        },
        dataRule: {
          status: [
            { required: true, message: '审核状态 -1 失败 0 待审核 1 成功不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/app/levelrecords/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.userId = data.levelRecords.userId
                this.dataForm.levelId = data.levelRecords.levelId
                this.dataForm.inviterCount = data.levelRecords.inviterCount
                this.dataForm.power = data.levelRecords.power
                this.dataForm.powerSource = data.levelRecords.powerSource
                this.dataForm.eqLevelCount = data.levelRecords.eqLevelCount
                this.dataForm.updateTime = data.levelRecords.updateTime
                this.dataForm.dqPower = data.levelRecords.dqPower
                this.dataForm.dqPowerSource = data.levelRecords.dqPowerSource
                this.dataForm.status = data.levelRecords.status
                this.dataForm.levelName = data.levelRecords.levelName
                this.dataForm.mobile = data.levelRecords.mobile
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
              url: this.$http.adornUrl(`/app/levelrecords/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'userId': this.dataForm.userId,
                'levelId': this.dataForm.levelId,
                'inviterCount': this.dataForm.inviterCount,
                'power': this.dataForm.power,
                'powerSource': this.dataForm.powerSource,
                'eqLevelCount': this.dataForm.eqLevelCount,
                'updateTime': this.dataForm.updateTime,
                'dqPower': this.dataForm.dqPower,
                'dqPowerSource': this.dataForm.dqPowerSource,
                'status': this.dataForm.status
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
