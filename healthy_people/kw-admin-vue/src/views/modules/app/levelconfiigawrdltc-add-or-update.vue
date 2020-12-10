<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
      <el-form-item label="选择配置" prop="configId" >
        <el-select v-model="dataForm.configId" placeholder="选择配置">
          <el-option
            v-for="item in configList"
            :key="item.id"
            :label="item.name"
            :value="item.id">
          </el-option>
        </el-select>
        <!--<el-input v-model="dataForm." placeholder=""></el-input>-->
      </el-form-item>


    <el-form-item label="奖励矿机" prop="awardLtcId">
      <el-select v-model="dataForm.awardLtcId" placeholder="奖励矿机id">
        <el-option
          v-for="item in dataList"
          :key="item.id"
          :label="item.name"
          :value="item.id">
        </el-option>
      </el-select>
      <!--<el-input v-model="dataForm." placeholder=""></el-input>-->
    </el-form-item>
    <!--<el-form-item label="创建时间" prop="createTime">-->
      <!--<el-input v-model="dataForm.createTime" placeholder="创建时间"></el-input>-->
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
        dataList: [],
        configList: [],
        dataForm: {
          id: 0,
          configId: '',
          awardLtcId: '',
          createTime: ''
        },
        dataRule: {
          awardLtcId: [
            { required: true, message: '奖励矿机id不能为空', trigger: 'blur' }
          ],
          createTime: [
            { required: true, message: '创建时间不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.$http({
          url: this.$http.adornUrl('/app/levelconfiig/list'),
          method: 'get'
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.configList = data.page.list
          }
        })
        this.$http({
          url: this.$http.adornUrl('/app/ltcconfig/list'),
          method: 'get'
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.dataList = data.page.list
          }
        })
        this.dataForm.id = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/app/levelconfiigawrdltc/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.awardLtcId = data.levelConfiigAwrdLtc.awardLtcId
                this.dataForm.createTime = data.levelConfiigAwrdLtc.createTime
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
              url: this.$http.adornUrl(`/app/levelconfiigawrdltc/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'configId': this.dataForm.configId,
                'awardLtcId': this.dataForm.awardLtcId,
                'createTime': this.dataForm.createTime
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
