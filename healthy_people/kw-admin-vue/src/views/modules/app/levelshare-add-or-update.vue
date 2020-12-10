<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="用户id" prop="userId">
      <el-input v-model="dataForm.userId" placeholder="用户id"></el-input>
    </el-form-item>
    <el-form-item label="分红金额" prop="shareMoney">
      <el-input v-model="dataForm.shareMoney" placeholder="分红金额"></el-input>
    </el-form-item>
    <el-form-item label="市代分红" prop="shareSdMoney">
      <el-input v-model="dataForm.shareSdMoney" placeholder=""></el-input>
    </el-form-item>

      <el-form-item label="市代MAC分红" prop="shareSonMoney">
        <el-input v-model="dataForm.shareSonMoney" placeholder=""></el-input>
      </el-form-item>

      <el-form-item label="分红状态">
        <el-select v-model="dataForm.shareStatus" placeholder="分红状态">
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
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
          shareStatus: '',
          shareSonMoney: ''
        },
        options: [{
          value: '0',
          label: '未分'
        }, {
          value: '1',
          label: '已分'
        }
        ],
        dataRule: {
          userId: [
            { required: true, message: '用户id不能为空', trigger: 'blur' }
          ],
          shareMoney: [
            { required: true, message: '分红金额不能为空', trigger: 'blur' }
          ],
          shareSdMoney: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          createDate: [
            { required: true, message: '产生时间不能为空', trigger: 'blur' }
          ],
          shareSonMoney: [
            { required: true, message: 'MAC分红不能为空', trigger: 'blur' }
          ],


          shareStatus: [
            { required: true, message: '分红状态不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/app/levelshare/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.userId = data.levelShare.userId
                this.dataForm.shareMoney = data.levelShare.shareMoney
                this.dataForm.shareSdMoney = data.levelShare.shareSdMoney
                this.dataForm.createDate = data.levelShare.createDate
                this.dataForm.shareStatus = data.levelShare.shareStatus
              this.dataForm.shareSonMoney = data.levelShare.shareSonMoney

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
              url: this.$http.adornUrl(`/app/levelshare/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'userId': this.dataForm.userId,
                'shareMoney': this.dataForm.shareMoney,
                'shareSdMoney': this.dataForm.shareSdMoney,
                'createDate': this.dataForm.createDate,
                'shareStatus': this.dataForm.shareStatus,
                'shareSonMoney' : this.dataForm.shareSonMoney
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
