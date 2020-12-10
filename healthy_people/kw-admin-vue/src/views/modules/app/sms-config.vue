<template>
  <el-dialog
    title="短信配置"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="120px">
      <el-form-item size="mini" label="短信服务商">
        <el-radio-group v-model="dataForm.type">
          <el-radio :label="1">MoveK</el-radio>
          <el-radio :label="2">阿里云</el-radio>
          <el-radio :label="3">腾讯云</el-radio>
          <el-radio :label="4">聚合</el-radio>
        </el-radio-group>
      </el-form-item>

      <el-form-item size="mini" label="是否启用短信">
        <el-radio-group v-model="dataForm.enable">
          <el-radio :label="1">是</el-radio>
          <el-radio :label="0">否</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="sign">
        <el-input v-model="dataForm.sign" placeholder="签名"></el-input>
      </el-form-item>
      <template v-if="dataForm.type === 1">
        <el-form-item label="Uid">
          <el-input v-model="dataForm.movekUid" placeholder="movekUid"></el-input>
        </el-form-item>
        <el-form-item label="URL">
          <el-input v-model="dataForm.movekUrl" placeholder="URL"></el-input>
        </el-form-item>
        <el-form-item label="movekAccessKeyId">
          <el-input v-model="dataForm.movekAccessKeyId" placeholder="AccessKey"></el-input>
        </el-form-item>
        <el-form-item label="movekAccessKeySecret">
          <el-input v-model="dataForm.movekAccessKeySecret" placeholder="SecretKey"></el-input>
        </el-form-item>
      </template>
      <template v-else-if="dataForm.type === 2">
        <el-form-item label="AccessKeyId">
          <el-input v-model="dataForm.aliyunAccessKeyId" placeholder="阿里云AccessKeyId"></el-input>
        </el-form-item>
        <el-form-item label="AccessKeySecret">
          <el-input v-model="dataForm.aliyunAccessKeySecret" placeholder="阿里云AccessKeySecret"></el-input>
        </el-form-item>
        <el-form-item label="所属地区">
          <el-input v-model="dataForm.aliyunRegionId" placeholder="如：sh（可选值 ，华南：gz 华北：tj 华东：sh）"></el-input>
        </el-form-item>
      </template>
      <template v-else-if="dataForm.type === 3">
        <el-form-item label="AppId">
          <el-input v-model="dataForm.qcloudAppId" placeholder="腾讯AppId"></el-input>
        </el-form-item>
        <el-form-item label="SecretId">
          <el-input v-model="dataForm.qcloudSecretId" placeholder="腾讯SecretId"></el-input>
        </el-form-item>
        <el-form-item label="SecretKey">
          <el-input v-model="dataForm.qcloudSecretKey" placeholder="腾讯SecretKey"></el-input>
        </el-form-item>
      </template>
      <template v-else-if="dataForm.type === 4">
        <el-form-item label="API URL">
          <el-input v-model="dataForm.juheDomain" placeholder="聚合API URL"></el-input>
        </el-form-item>
        <el-form-item label="APPKEY">
          <el-input v-model="dataForm.juheSecretKey" placeholder="聚合APPKEY"></el-input>
        </el-form-item>
        <el-form-item label="短信模版ID">
          <el-input v-model="dataForm.juheTplId" placeholder="聚合模版ID"></el-input>
        </el-form-item>
      </template>
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
        dataForm: {},
        dataRule: {}
      }
    },
    methods: {
      init (id) {
        this.visible = true
        this.$http({
          url: this.$http.adornUrl('/app/sms/config'),
          method: 'get',
          params: this.$http.adornParams()
        }).then(({data}) => {
          this.dataForm = data && data.code === 0 ? data.config : []
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl('/app/sms/saveConfig'),
              method: 'post',
              data: this.$http.adornData(this.dataForm)
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.$message({
                  message: '操作成功',
                  type: 'success',
                  duration: 1000,
                  onClose: () => {
                    this.visible = false
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

