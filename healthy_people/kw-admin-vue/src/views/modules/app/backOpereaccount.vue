<template>
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="120px">

      <el-form-item label="手机号码" prop="userId">
        <el-select v-model="dataForm.userId" filterable placeholder="请选择手机号码">
          <el-option
            v-for="item in userList"
            :key="item.id"
            :label="item.mobile"
            :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item  label="分配类型" prop="payType">
      <el-select v-model="dataForm.payType" filterable placeholder="请选择分配类型">
        <el-option
          v-for="item in typeList"
          :key="item.id"
          :label="item.name"
          :value="item.id">
        </el-option>
      </el-select>
      </el-form-item>
      <el-form-item label="操作金额" prop="money">
        <el-input v-model="dataForm.money"  label="请输入"></el-input>
      </el-form-item>
      <el-form-item label="操作次数" prop="ct">
        <el-input-number v-model="dataForm.ct" :min="1" :max="10000" label="请输入"></el-input-number>
      </el-form-item>

      <el-form-item>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
      </el-form-item>
    </el-form>

</template>

<script>
  export default {
    data () {
      return {
        visible: false,
        userList: [],
        typeList: [
          { id: 'decute_manager',
            name: '后台扣除'},
          { id: 'user_auth_sd',
            name: '下级实名奖励市代'}
        ],
        status: 1,
        dataForm: {
          userId: '',
          ct: 0,
          money:'',
          payType:''
        },
        dataRule: {
          ct: [
            { required: true, message: '请输入数量', trigger: 'blur' }
          ],
          money: [
            { required: true, message: '请输入金额', trigger: 'blur' }
          ]
        }
      }
    },
    created(){
      this.init()
    },
    methods: {

      init () {
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          this.$http({
            url: this.$http.adornUrl(`/app/user/all`),
            method: 'get'
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.userList = data.list
            }
          })
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$confirm(`确定执行吗`, '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
            //  let uid = this.dataForm.userId || 0;
              this.$http({
                url: this.$http.adornUrl(`/app/account/opera`),
                method: 'post',
                data: this.$http.adornData({
                  'userId': this.dataForm.userId,
                  'money': this.dataForm.money,
                  'ct': this.dataForm.ct,
                  'payType': this.dataForm.payType,

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
            })
          }
        })
      }
    }
  }
</script>
