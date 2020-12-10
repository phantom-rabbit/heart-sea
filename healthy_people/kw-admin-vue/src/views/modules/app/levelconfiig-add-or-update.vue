<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form inline :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()"
             label-width="200px">
      <el-form-item label="名称" prop="name">
        <el-input v-model="dataForm.name" placeholder="名称"></el-input>
      </el-form-item>
      <el-form-item label="直推人数要求" prop="inviteCount">
        <el-input v-model="dataForm.inviteCount" placeholder="邀请人数要求"></el-input>
      </el-form-item>
      <el-form-item label="团队动力值要求" prop="teamPower">
        <el-input v-model="dataForm.teamPower" placeholder="团队动力要求"></el-input>
      </el-form-item>
       <el-form-item label="团队动力源要求" prop="teamPowerSource">
        <el-input v-model="dataForm.teamPowerSource" placeholder="团队能源要求"></el-input>
      </el-form-item>

      <el-form-item label="直推同级人数" prop="lowerCount">
        <el-input v-model="dataForm.lowerCount" placeholder="直推同级人数"></el-input>
      </el-form-item>
      <!--<el-form-item label="下级是否直推" prop="isDirect">-->
      <!--<el-input v-model="dataForm.isDirect" placeholder="下级是否直推"></el-input>-->
      <!--</el-form-item>-->


      <el-form-item label="直推同级人数是否限制" prop="isDirect">
        <el-radio-group v-model="dataForm.isDirect">
          <el-radio :label="0">否</el-radio>
          <el-radio :label="1">是</el-radio>
        </el-radio-group>
      </el-form-item>

      <el-form-item label="实名要求" prop="isReal">
        <el-radio-group v-model="dataForm.isReal">
          <el-radio :label="0">否</el-radio>
          <el-radio :label="1">是</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="是否限制大区" prop="isLowerAnyTeam">
        <el-radio-group v-model="dataForm.isLowerAnyTeam">
          <el-radio :label="0">否</el-radio>
          <el-radio :label="1">是</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="大区动力值或源满足" prop="lowerAnyTeamPower">
        <el-input v-model="dataForm.lowerAnyTeamPower" placeholder="大区"></el-input>
      </el-form-item>


      <el-form-item label="交易加权分红(同级)" prop="transShare">
        <el-input v-model="dataForm.transShare" placeholder="交易加权分红(同级)"></el-input>
      </el-form-item>

      <el-form-item label="级别顺序" prop="orderNum">
        <el-input-number v-model="dataForm.orderNum" :min="1" :max="10000"></el-input-number>
        <span>设置越大级别越高</span>
      </el-form-item>
      <!--<el-form-item label="创建时间" prop="createTime">-->
      <!--<el-input v-model="dataForm.createTime" placeholder="创建时间"></el-input>-->
      <!--</el-form-item>-->
      <!--<el-form-item label="创建人" prop="createUserId">-->
      <!--<el-input v-model="dataForm.createUserId" placeholder="创建人"></el-input>-->
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
    data() {
      return {
        visible: false,

        options: [{
          value: 0,
          label: '不需要'
        }, {
          value: 1,
          label: '需要'
        }],
        dataForm: {
          id: 0,
          name: '',
          inviteCount: '',
          teamPower: '',
          lowerCount: '',
          isReal: '',
          isDirect: '',
          transShare: '',
          lowerAnyTeamPower: 0,
          teamPowerSource: 0,
          isLowerAnyTeam: 0,
          createTime: '',
          createUserId: '',
          orderNum: 1
        },
        dataRule: {
          name: [
            {required: true, message: '名称不能为空', trigger: 'blur'}
          ],
          inviteCount: [
            {required: true, message: '邀请人数要求不能为空', trigger: 'blur'}
          ],
          teamPower: [
            {required: true, message: '团队动力要求不能为空', trigger: 'blur'}
          ],
          lowerCount: [
            {required: true, message: '下级人数要求不能为空', trigger: 'blur'}
          ],
          isReal: [
            {required: true, message: '实名要求不能为空', trigger: 'blur'}
          ],
          transShare: [
            {required: true, message: '交易加权分红(同级)不能为空', trigger: 'blur'}
          ],
          createTime: [
            {required: true, message: '创建时间不能为空', trigger: 'blur'}
          ],
          createUserId: [
            {required: true, message: '创建人不能为空', trigger: 'blur'}
          ],
          isDirect: [
            {required: true, message: '是否直推必选', trigger: 'blur'}
          ]
        }
      }
    },
    methods: {
      init(id) {
        this.dataForm.id = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/app/levelconfiig/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.name = data.levelConfiig.name
                this.dataForm.inviteCount = data.levelConfiig.inviteCount
                this.dataForm.teamPower = data.levelConfiig.teamPower
                this.dataForm.lowerCount = data.levelConfiig.lowerCount
                this.dataForm.isReal = data.levelConfiig.isReal
                this.dataForm.transShare = data.levelConfiig.transShare
                this.dataForm.createTime = data.levelConfiig.createTime
                this.dataForm.createUserId = data.levelConfiig.createUserId
                this.dataForm.isDirect = data.levelConfiig.isDirect
                this.dataForm.orderNum = data.levelConfiig.orderNum
                this.dataForm.lowerAnyTeamPower = data.levelConfiig.lowerAnyTeamPower
                this.dataForm.isLowerAnyTeam = data.levelConfiig.isLowerAnyTeam
                this.dataForm.teamPowerSource = data.levelConfiig.teamPowerSource
              }
            })
          }
        })
      },
      // 表单提交
      dataFormSubmit() {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/app/levelconfiig/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'name': this.dataForm.name,
                'inviteCount': this.dataForm.inviteCount,
                'teamPower': this.dataForm.teamPower,
                'lowerCount': this.dataForm.lowerCount,
                'isReal': this.dataForm.isReal,
                'transShare': this.dataForm.transShare,
                'createTime': this.dataForm.createTime,
                'createUserId': this.dataForm.createUserId,
                'isDirect': this.dataForm.isDirect,
                'orderNum': this.dataForm.orderNum,
                'lowerAnyTeamPower': this.dataForm.lowerAnyTeamPower,
                'isLowerAnyTeam': this.dataForm.isLowerAnyTeam,
                'teamPowerSource': this.dataForm.teamPowerSource,
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
