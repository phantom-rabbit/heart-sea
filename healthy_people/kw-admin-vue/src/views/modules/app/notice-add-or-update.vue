<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="标题" prop="title">
      <el-input v-model="dataForm.title" placeholder="标题"></el-input>
    </el-form-item>
      <el-form-item label="分类" prop="typeId">
        <el-select v-model="dataForm.typeId" filterable placeholder="请选择">
          <el-option label="通知公告" :value="0">  </el-option>
          <el-option label="教程指南" :value="1">  </el-option>
          <el-option label="规则" :value="2">  </el-option>
          <el-option label="环保学院" :value="3">  </el-option>
        </el-select>
      </el-form-item>
    <el-form-item label="内容" prop="content">
      <!--<el-input v-model="dataForm.content" placeholder="内容"></el-input>-->
      <vue-ueditor-wrap v-model="dataForm.content" :config="{UEDITOR_HOME_URL:'/2005072347/static/plugins/ueditor-1.4.3.3/'}"></vue-ueditor-wrap>
    </el-form-item>
      <el-form-item label="是否显示" prop="status">
        <el-radio-group v-model="dataForm.isShow">
          <el-radio :label="0" >不显示</el-radio>
          <el-radio :label="1" >显示</el-radio>
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
          title: '',
          content: '',
          createDate: '',
          isShow: 1,
          typeId: 0,
          updateDate: ''
        },
        dataRule: {
          title: [
            { required: true, message: '标题不能为空', trigger: 'blur' }
          ],
          typeId: [
            { required: true, message: '分类不能为空', trigger: 'blur' }
          ],
          content: [
            { required: true, message: '内容不能为空', trigger: 'blur' }
          ],
          createDate: [
            { required: true, message: '创建时间不能为空', trigger: 'blur' }
          ],
          show: [
            { required: true, message: '是否显示不能为空', trigger: 'blur' }
          ],
          updateDate: [
            { required: true, message: '更新时间不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/app/notice/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.title = data.notice.title
                this.dataForm.typeId = data.notice.typeId
                this.dataForm.content = data.notice.content
                this.dataForm.createDate = data.notice.createDate
                this.dataForm.isShow = data.notice.isShow
                this.dataForm.updateDate = data.notice.updateDate
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
              url: this.$http.adornUrl(`/app/notice/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'title': this.dataForm.title,
                'content': this.dataForm.content,
                'typeId': this.dataForm.typeId,
                'createDate': this.dataForm.createDate,
                'isShow': this.dataForm.isShow,
                'updateDate': this.dataForm.updateDate
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
