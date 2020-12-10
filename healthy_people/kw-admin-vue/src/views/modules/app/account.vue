<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input ref="search" v-model="dataForm.key" placeholder="手机或用户id" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-select v-model="dataForm.sdStatus" placeholder="申请市代状态">
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList()">查询</el-button>
      </el-form-item>
    </el-form>

    <el-dialog width="400px" :visible.sync="imgVisible" class="img-dialog">
      <el-card :body-style="{ padding: '0px' }">
        <img :src="dialogImgUrl" width="100%" height="100%">
      </el-card>
    </el-dialog>


    <el-table
      :data="dataList"
      border
      v-loading="dataListLoading"
      @selection-change="selectionChangeHandle"
      style="width: 100%;">
      <el-table-column
        type="selection"
        header-align="center"
        align="center"
        width="50">
      </el-table-column>

      <el-table-column
        prop="userId"
        header-align="center"
        align="center"
        label="用户ID">
      </el-table-column>

      <el-table-column
        prop="mobile"
        header-align="center"
        align="center"
        label="手机">
      </el-table-column>
      <el-table-column
        prop="sdStatus"
        header-align="center"
        align="center"
        label="申请市代">
        <template slot-scope="scope">
          <span v-if="scope.row.sdStatus===-1">失败</span>
          <span v-if="scope.row.sdStatus===0">待选城市</span>
          <span v-if="scope.row.sdStatus===1">待初审</span>
          <span v-if="scope.row.sdStatus===2">待打款</span>
          <span v-if="scope.row.sdStatus===3">待终审</span>
          <span v-if="scope.row.sdStatus===4">成功</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="sdProvinceCity"
        header-align="center"
        align="center"
        label="市代城市">
      </el-table-column>
      <el-table-column
        prop="sdPhoto"
        header-align="center"
        align="center"
        width="200"
        label="打款照片">
        <template slot-scope="scope">
          <img :src="scope.row.sdPhoto" style="width: 100px;height: 100px"  @click="openImg(scope.row.sdPhoto)">
        </template>
      </el-table-column>
      <!--<el-table-column-->
        <!--prop="power"-->
        <!--header-align="center"-->
        <!--align="center"-->
        <!--label="动力">-->
      <!--</el-table-column>-->
      <!--<el-table-column-->
        <!--prop="parentMoney"-->
        <!--header-align="center"-->
        <!--align="center"-->
        <!--label="OPC">-->
      <!--</el-table-column>-->
      <!--<el-table-column-->
        <!--prop="sonMoney"-->
        <!--header-align="center"-->
        <!--align="center"-->
        <!--label="MAC">-->
      <!--</el-table-column>-->
      <el-table-column
        prop="usdtToken"
        header-align="center"
        align="center"
        label="USDTTOKEN">
      </el-table-column>
      <el-table-column
        prop="alipayNumber"
        header-align="center"
        align="center"
        label="支付宝账号">
      </el-table-column>
      <el-table-column
        prop="bank"
        header-align="center"
        align="center"
        label="银行">
      </el-table-column>
      <el-table-column
        prop="branch"
        header-align="center"
        align="center"
        label="分行">
      </el-table-column>
      <el-table-column
        prop="bankCardno"
        header-align="center"
        align="center"
        label="卡号">
      </el-table-column>
      <el-table-column
        prop="vipName"
        header-align="center"
        align="center"
        label="vip级别">
      </el-table-column>
      <el-table-column
        prop="levelName"
        header-align="center"
        align="center"
        label="等级">
      </el-table-column>


      <!--<el-table-column-->
      <!--prop="proxyId"-->
      <!--header-align="center"-->
      <!--align="center"-->
      <!--label="代理id">-->
      <!--</el-table-column>-->

      <el-table-column
        prop="teamId"
        header-align="center"
        align="center"
        label="战队id">
      </el-table-column>


      <el-table-column
        prop="createwordStatus"
        header-align="center"
        align="center"
        label="创世合伙人">
        <template slot-scope="scope">
          <span v-if="scope.row.createwordStatus===1">是</span>
          <span v-if="scope.row.createwordStatus===0">否</span>

        </template>
      </el-table-column>

      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        prop="sdStatus"
        width="150"
        label="操作">
        <template slot-scope="scope">
          <el-button type="text" size="small" v-if="scope.row.sdStatus===4" @click="sdLastVerify(scope.row.id,-2)">取消市代</el-button>
          <el-button type="text" size="small" @click="addOrUpdateHandle(scope.row.id)">修改</el-button>
          <!--<el-button type="text" size="small" @click="addOrUpdateHandle(scope.row.id)">修改</el-button>-->
          <el-button type="text" size="small" v-if="scope.row.sdStatus===1"  @click="sdFirstVerify(scope.row.id,1)">市代初审通过</el-button>
          <el-button type="text" size="small" v-if="scope.row.sdStatus===1"  @click="sdFirstVerify(scope.row.id,0)">市代初审拒绝</el-button>
          <el-button type="text" size="small" v-if="scope.row.sdStatus===3" @click="sdLastVerify(scope.row.id,1)">市代终审通过</el-button>
          <el-button type="text" size="small" v-if="scope.row.sdStatus===3" @click="sdLastVerify(scope.row.id,0)">市代终审拒绝,回打款流程</el-button>
          <el-button type="text" size="small" v-if="scope.row.sdStatus===3" @click="sdLastVerify(scope.row.id,-1)">市代终审拒绝,回初试状态</el-button>

        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="sizeChangeHandle"
      @current-change="currentChangeHandle"
      :current-page="pageIndex"
      :page-sizes="[10, 20, 50, 100]"
      :page-size="pageSize"
      :total="totalPage"
      layout="total, sizes, prev, pager, next, jumper">
    </el-pagination>
    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>
  </div>
</template>
<style>
  .img-dialog {
  .el-dialog__header {
    padding: 0!important;
  }
  }
</style>
<script>
 import AddOrUpdate from './account-add-or-update'
 import filters from '@/utils/filters'
  export default {
    data () {
      return {
        imgVisible: false,
        defaultImg: '',

        dataForm: {
          userId: ''
        },

        options: [{
          value: '0',
          label: '等待选择城市'
        }, {
          value: '1',
          label: '等待我方初审'
        }, {
          value: '2',
          label: '等待usdt打款'
        }, {
          value: '3',
          label: '等待终审'
        }, {
          value: '4',
          label: '完成'
        }, {
          value: '-1',
          label: '失败'
        }],
        dataList: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        dataListLoading: false,
        dataListSelections: [],
        addOrUpdateVisible: false,
        dialogImgUrl: ''
      }
    },
    filters: filters,
    components: {
      AddOrUpdate
    },
    activated () {
      this.$refs.search.focus()
      this.getDataList()
    },
    methods: {
      openImg(url) {
        if (url) {
          this.imgVisible = true
          this.dialogImgUrl = url
        }
      },
      // 获取数据列表
      getDataList () {
        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl('/app/account/list'),
          method: 'get',
          params: this.$http.adornParams({
            'page': this.pageIndex,
            'limit': this.pageSize,
            'sdStatus': this.dataForm.sdStatus,
            'key': this.dataForm.key
          })
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.dataList = data.page.list
            this.totalPage = data.page.totalCount
        } else {
          this.dataList = []
          this.totalPage = 0
        }
        this.dataListLoading = false
      })
      },
      // 每页数
      sizeChangeHandle (val) {
        this.pageSize = val
        this.pageIndex = 1
        this.getDataList()
      },
      // 当前页
      currentChangeHandle (val) {
        this.pageIndex = val
        this.getDataList()
      },
      // 多选
      selectionChangeHandle (val) {
        this.dataListSelections = val
      },
      // 删除
      sdFirstVerify (id,status) {

        this.$confirm(id+'初审'+(status==0?'不通过':'通过'), '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
          url: this.$http.adornUrl('/app/account/firstverify'),
          method: 'post',
          data: this.$http.adornData({id:id,status:status}, false)
        }).then(({data}) => {
          if (data && data.code === 0) {
          this.$message({
            message: '操作成功',
            type: 'success',
            duration: 1500,
            onClose: () => {
            this.getDataList()
        }
        })
        } else {
          this.$message.error(data.msg)
        }
      })
      })
      },
      sdLastVerify (id,status) {

        this.$confirm(id+'终审'+(status==1  ?'通过':'不通过'), '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
          url: this.$http.adornUrl('/app/account/lastverify'),
          method: 'post',
          data: this.$http.adornData({id:id,status:status}, false)
        }).then(({data}) => {
          if (data && data.code === 0) {
          this.$message({
            message: '操作成功',
            type: 'success',
            duration: 1500,
            onClose: () => {
            this.getDataList()
        }
        })
        } else {
          this.$message.error(data.msg)
        }
      })
      })
      },

    // 新增 / 修改
    addOrUpdateHandle (id) {
      this.addOrUpdateVisible = true
      this.$nextTick(() => {
        this.$refs.addOrUpdate.init(id)
    })
    },
      // 删除
      deleteHandle (id) {
        var ids = id ? [id] : this.dataListSelections.map(item => {
          return item.id
        })
        this.$confirm(`确定对[id=${ids.join(',')}]进行[${id ? '删除' : '批量删除'}]操作?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
          url: this.$http.adornUrl('/app/account/delete'),
          method: 'post',
          data: this.$http.adornData(ids, false)
        }).then(({data}) => {
          if (data && data.code === 0) {
          this.$message({
            message: '操作成功',
            type: 'success',
            duration: 1500,
            onClose: () => {
              this.getDataList()
        }
        })
        } else {
          this.$message.error(data.msg)
        }
      })
      })
      }
    }
  }
</script>
