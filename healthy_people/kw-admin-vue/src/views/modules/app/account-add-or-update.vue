<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="120px">
    <el-form-item label="名字" prop="realName" v-if="false">
      <el-input v-model="dataForm.realName" placeholder="名字"></el-input>
    </el-form-item>
    <el-form-item label="身份证" prop="idCard" v-if="false">
      <el-input v-model="dataForm.idCard" placeholder="身份证"></el-input>
    </el-form-item>
    <el-form-item label="认证状态" prop="realStatus" v-if="false">
      <el-input v-model="dataForm.realStatus" placeholder="认证状态"></el-input>
    </el-form-item>
    <el-form-item label="动力" prop="power" v-if="false">
      <el-input v-model="dataForm.power" placeholder="动力"></el-input>
    </el-form-item>
    <el-form-item label="邀请人id" prop="inviterId" v-if="false">
      <el-input v-model="dataForm.inviterId" placeholder="邀请人id"></el-input>
    </el-form-item>
    <el-form-item label="OPC" prop="parentMoney" v-if="false">
      <el-input v-model="dataForm.parentMoney" placeholder="OPC"></el-input>
    </el-form-item>
    <el-form-item label="MAC" prop="sonMoney" v-if="false">
      <el-input v-model="dataForm.sonMoney" placeholder="MAC"></el-input>
    </el-form-item>
    <el-form-item label="USDT地址" prop="usdtToken">
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


    <el-form-item label="VIP等级" prop="vipId">
      <el-select v-model="dataForm.vipId">
        <el-option
          v-for="item in dataVipList"
          :key="item.id"
          :label="item.name"
          :value="item.id"
        >
        </el-option>
      </el-select>
      <!--<el-input v-model="dataForm.vipId" placeholder="vip_id"></el-input>-->
    </el-form-item>

      <el-form-item label="级别" prop="levelId">
        <el-select v-model="dataForm.levelId">
          <el-option
            v-for="item in dataLevelList"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          >
          </el-option>
        </el-select>
        <!--<el-input v-model="dataForm.vipId" placeholder="vip_id"></el-input>-->
      </el-form-item>
    <el-form-item label="代理id" prop="proxyId"  v-if="false">
      <el-input v-model="dataForm.proxyId" placeholder="代理id"></el-input>
    </el-form-item>
    <el-form-item label="冻结中金额" prop="frezenParentMoney"  v-if="false">
      <el-input v-model="dataForm.frezenParentMoney" placeholder="冻结中金额"></el-input>
    </el-form-item>
    <el-form-item label="昵称" prop="nickname"  v-if="false">
      <el-input v-model="dataForm.nickname" placeholder="昵称"></el-input>
    </el-form-item>
    <el-form-item label="战队id" prop="teamId"  v-if="false">
      <el-input v-model="dataForm.teamId" placeholder="战队id"></el-input>
    </el-form-item>
    <el-form-item label="用户id" prop="userId"  v-if="false">
      <el-input v-model="dataForm.userId" placeholder=""></el-input>
    </el-form-item>
      <el-form-item v-if="false"  label="创世合伙人状态" prop="createwordStatus" >
        <el-select v-model="dataForm.createwordStatus">
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
        options: [{
          value: 0,
          label: '否'
        }, {
          value: 1,
          label: '是'
        }],
        visible: false,
        dataVipList: [],
        dataLevelList: [],
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
          levelId: '',
          vipId: '',
          proxyId: '',
          frezenParentMoney: '',
          nickname: '',
          teamId: '',
          userId: '',
          createwordStatus: ''
        },
        dataRule: {
          createwordStatus: [
            { required: true, message: '创世合伙人', trigger: 'blur' }
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
                this.dataForm.levelId = data.account.levelId
                this.dataForm.proxyId = data.account.proxyId
                this.dataForm.frezenParentMoney = data.account.frezenParentMoney
                this.dataForm.nickname = data.account.nickname
                this.dataForm.teamId = data.account.teamId
                this.dataForm.userId = data.account.userId
                this.dataForm.createwordStatus = data.account.createwordStatus
              }
            })
          }

          this.$http({
            url: this.$http.adornUrl('/app/vipconfig/list'),
            method: 'get',
            params: this.$http.adornParams({
              'page': 1,
              'limit': 100
            })
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.dataVipList = data.page.list
            }
          })

          this.$http({
            url: this.$http.adornUrl('/app/levelconfiig/list'),
            method: 'get',
            params: this.$http.adornParams({
              'page': 1,
              'limit': 100
            })
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.dataLevelList = data.page.list
            }
          })
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
                'levelId': this.dataForm.levelId,
                'proxyId': this.dataForm.proxyId,
                'frezenParentMoney': this.dataForm.frezenParentMoney,
                'nickname': this.dataForm.nickname,
                'teamId': this.dataForm.teamId,
                'userId': this.dataForm.userId,
                'createwordStatus':this.dataForm.createwordStatus
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
