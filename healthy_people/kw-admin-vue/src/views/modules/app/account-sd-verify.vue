<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="名字" prop="realName">
      <el-input v-model="dataForm.realName" placeholder="名字"></el-input>
    </el-form-item>
    <el-form-item label="身份证" prop="idCard">
      <el-input v-model="dataForm.idCard" placeholder="身份证"></el-input>
    </el-form-item>
    <el-form-item label="认证状态" prop="realStatus">
      <el-input v-model="dataForm.realStatus" placeholder="认证状态"></el-input>
    </el-form-item>
    <el-form-item label="动力" prop="power">
      <el-input v-model="dataForm.power" placeholder="动力"></el-input>
    </el-form-item>
    <el-form-item label="邀请人id" prop="inviterId">
      <el-input v-model="dataForm.inviterId" placeholder="邀请人id"></el-input>
    </el-form-item>
    <el-form-item label="OPC" prop="parentMoney">
      <el-input v-model="dataForm.parentMoney" placeholder="OPC"></el-input>
    </el-form-item>
    <el-form-item label="MAC" prop="sonMoney">
      <el-input v-model="dataForm.sonMoney" placeholder="MAC"></el-input>
    </el-form-item>
    <el-form-item label="" prop="usdtToken">
      <el-input v-model="dataForm.usdtToken" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="支付宝账号" prop="alipayNumber">
      <el-input v-model="dataForm.alipayNumber" placeholder="支付宝账号"></el-input>
    </el-form-item>
    <el-form-item label="银行" prop="bank">
      <el-input v-model="dataForm.bank" placeholder="银行"></el-input>
    </el-form-item>
    <el-form-item label="分行" prop="branch">
      <el-input v-model="dataForm.branch" placeholder="分行"></el-input>
    </el-form-item>
    <el-form-item label="卡号" prop="bankCardno">
      <el-input v-model="dataForm.bankCardno" placeholder="卡号"></el-input>
    </el-form-item>
    <el-form-item label="vip_id" prop="vipId">
      <el-input v-model="dataForm.vipId" placeholder="vip_id"></el-input>
    </el-form-item>
    <el-form-item label="代理id" prop="proxyId">
      <el-input v-model="dataForm.proxyId" placeholder="代理id"></el-input>
    </el-form-item>
    <el-form-item label="冻结中金额" prop="frezenParentMoney">
      <el-input v-model="dataForm.frezenParentMoney" placeholder="冻结中金额"></el-input>
    </el-form-item>
    <el-form-item label="昵称" prop="nickname">
      <el-input v-model="dataForm.nickname" placeholder="昵称"></el-input>
    </el-form-item>
    <el-form-item label="战队id" prop="teamId">
      <el-input v-model="dataForm.teamId" placeholder="战队id"></el-input>
    </el-form-item>
    <el-form-item label="" prop="userId">
      <el-input v-model="dataForm.userId" placeholder=""></el-input>
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
          realName: '',
          idCard: '',
          realStatus: '',
          power: '',
          inviterId: '',
          parentMoney: '',
          sonMoney: '',
          usdtToken: '',
          alipayNumber: '',
          bank: '',
          branch: '',
          bankCardno: '',
          vipId: '',
          proxyId: '',
          frezenParentMoney: '',
          nickname: '',
          teamId: '',
          userId: ''
        },
        dataRule: {
          realName: [
            { required: true, message: '名字不能为空', trigger: 'blur' }
          ],
          idCard: [
            { required: true, message: '身份证不能为空', trigger: 'blur' }
          ],
          realStatus: [
            { required: true, message: '认证状态不能为空', trigger: 'blur' }
          ],
          power: [
            { required: true, message: '动力不能为空', trigger: 'blur' }
          ],
          inviterId: [
            { required: true, message: '邀请人id不能为空', trigger: 'blur' }
          ],
          parentMoney: [
            { required: true, message: 'OPC不能为空', trigger: 'blur' }
          ],
          sonMoney: [
            { required: true, message: 'MAC不能为空', trigger: 'blur' }
          ],
          usdtToken: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          alipayNumber: [
            { required: true, message: '支付宝账号不能为空', trigger: 'blur' }
          ],
          bank: [
            { required: true, message: '银行不能为空', trigger: 'blur' }
          ],
          branch: [
            { required: true, message: '分行不能为空', trigger: 'blur' }
          ],
          bankCardno: [
            { required: true, message: '卡号不能为空', trigger: 'blur' }
          ],
          vipId: [
            { required: true, message: 'vip_id不能为空', trigger: 'blur' }
          ],
          proxyId: [
            { required: true, message: '代理id不能为空', trigger: 'blur' }
          ],
          frezenParentMoney: [
            { required: true, message: '冻结中金额不能为空', trigger: 'blur' }
          ],
          nickname: [
            { required: true, message: '昵称不能为空', trigger: 'blur' }
          ],
          teamId: [
            { required: true, message: '战队id不能为空', trigger: 'blur' }
          ],
          userId: [
            { required: true, message: '不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/app/account/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.realName = data.account.realName
                this.dataForm.idCard = data.account.idCard
                this.dataForm.realStatus = data.account.realStatus
                this.dataForm.power = data.account.power
                this.dataForm.inviterId = data.account.inviterId
                this.dataForm.parentMoney = data.account.parentMoney
                this.dataForm.sonMoney = data.account.sonMoney
                this.dataForm.usdtToken = data.account.usdtToken
                this.dataForm.alipayNumber = data.account.alipayNumber
                this.dataForm.bank = data.account.bank
                this.dataForm.branch = data.account.branch
                this.dataForm.bankCardno = data.account.bankCardno
                this.dataForm.vipId = data.account.vipId
                this.dataForm.proxyId = data.account.proxyId
                this.dataForm.frezenParentMoney = data.account.frezenParentMoney
                this.dataForm.nickname = data.account.nickname
                this.dataForm.teamId = data.account.teamId
                this.dataForm.userId = data.account.userId
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
              url: this.$http.adornUrl(`/app/account/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'realName': this.dataForm.realName,
                'idCard': this.dataForm.idCard,
                'realStatus': this.dataForm.realStatus,
                'power': this.dataForm.power,
                'inviterId': this.dataForm.inviterId,
                'parentMoney': this.dataForm.parentMoney,
                'sonMoney': this.dataForm.sonMoney,
                'usdtToken': this.dataForm.usdtToken,
                'alipayNumber': this.dataForm.alipayNumber,
                'bank': this.dataForm.bank,
                'branch': this.dataForm.branch,
                'bankCardno': this.dataForm.bankCardno,
                'vipId': this.dataForm.vipId,
                'proxyId': this.dataForm.proxyId,
                'frezenParentMoney': this.dataForm.frezenParentMoney,
                'nickname': this.dataForm.nickname,
                'teamId': this.dataForm.teamId,
                'userId': this.dataForm.userId
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
