<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input ref="search" v-model="dataForm.sellerId" placeholder="售卖人id" clearable></el-input>
      </el-form-item>

      <el-form-item>
        <el-input ref="search" v-model="dataForm.buyerId" placeholder="挂买人id" clearable></el-input>
      </el-form-item>

      <el-form-item>


      <el-date-picker
        v-model="dataForm.startDate"
        type="datetime"
        format="yyyy-MM-dd"
        value-format="yyyy-MM-dd"
        placeholder="交易结束时间">
      </el-date-picker>
--
        <el-date-picker
          v-model="dataForm.endDate"
          type="datetime"
          format="yyyy-MM-dd"
          value-format="yyyy-MM-dd"
          placeholder="交易结束时间">
        </el-date-picker>
      </el-form-item>




      <el-form-item>
        <el-button @click="getDataList()">查询</el-button>
        <!--<el-button v-if="isAuth('app:buytrans:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>-->
        <!--<el-button v-if="isAuth('app:buytrans:delete')" type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button>-->
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

      <el-table-column type="expand" width="80" label="详情">
        <template slot-scope="props">
          <div class="table-expand">
            <p>挂买人：{{props.row.buyerMobile}}</p>
            <p>售卖人：{{props.row.sellerMobile}}</p>
            <p>挂买总量：{{props.row.amount}}</p>
            <p>价格：{{props.row.price}}</p>
            <p>手续费：{{props.row.fee}}</p>
            <p>买家提交时间：{{props.row.buyerSubmitTime}}</p>
            <p>卖家提交时间：{{props.row.sellerSubmitTime}}</p>
            <p>
              支付方式：
              <span v-if="props.row.status===1">支付宝</span>
              <span v-if="props.row.status===2">usdt</span>
              <span v-if="props.row.status===3">银行卡</span>
            </p>
            <p>
              状态：
              <span v-if="props.row.status===0">待上传交易凭证</span>
              <span v-if="props.row.status===1">已上传</span>
              <span v-if="props.row.status===2">完成</span>
              <span v-if="props.row.status===-1">交易关闭</span>
            </p>
            <p>打款凭证：<img v-if="props.row.photo" :src="props.row.photo" style="width: 100px;height: 100px"  @click="openImg(props.row.photo)"></p>
          </div>
        </template>
      </el-table-column>
      <el-table-column
        type="selection"
        header-align="center"
        align="center"
        width="50">
      </el-table-column>
      <el-table-column
        prop="id"
        header-align="center"
        align="center"
        width="80"
        label="id">
      </el-table-column>
      <el-table-column
        prop="buyerId"
        header-align="center"
        align="center"
        width="80"
        label="挂买人id">
      </el-table-column>
      <el-table-column
        prop="buyerMobile"
        header-align="center"
        align="center"
        width="120"
        label="挂买人">
      </el-table-column>
      <el-table-column
        prop="amount"
        header-align="center"
        align="center"
        label="挂买总量">
      </el-table-column>
      <el-table-column
        prop="sellerId"
        header-align="center"
        align="center"
        width="80"
        label="售卖人id">
      </el-table-column>
      <el-table-column
        prop="sellerMobile"
        header-align="center"
        align="center"
        width="120"
        label="售卖人">
      </el-table-column>

      <el-table-column
        prop="price"
        header-align="center"
        align="center"
        width="80"
        label="价格">
      </el-table-column>
      <el-table-column
        prop="fee"
        header-align="center"
        align="center"
        width="80"
        label="手续费">
      </el-table-column>

      <!--<el-table-column-->
        <!--prop="buyerSubmitTime"-->
        <!--header-align="center"-->
        <!--align="center"-->
        <!--label="买家提交">-->
      <!--</el-table-column>-->

      <!--<el-table-column-->
        <!--prop="sellerSubmitTime"-->
        <!--header-align="center"-->
        <!--align="center"-->
        <!--label="卖家提交">-->
      <!--</el-table-column>-->



      <el-table-column
        prop="photo"
        header-align="center"
        align="center"
        width="150"
        label="打款照片">
        <template slot-scope="scope">
          <img v-if="scope.row.photo" :src="scope.row.photo" style="width: 50px;height: 50px"  @click="openImg(scope.row.photo)">
        </template>
      </el-table-column>

      <el-table-column
        prop="payType"
        header-align="center"
        align="center"
        width="80"
        label="支付方式">
        <template slot-scope="scope">
          <span v-if="scope.row.status===1">支付宝</span>
          <span v-if="scope.row.status===2">usdt</span>
          <span v-if="scope.row.status===3">银行卡</span>

        </template>

      </el-table-column>
      <el-table-column
        prop="status"
        header-align="center"
        align="center"
        label="状态">
        <template slot-scope="scope">
          <span v-if="scope.row.status===0">待上传交易凭证</span>
          <span v-if="scope.row.status===1">已上传</span>
          <span v-if="scope.row.status===2">完成</span>
          <span v-if="scope.row.status===-1">交易关闭</span>
        </template>

      </el-table-column>



      <el-table-column
        prop="settleStatus"
        header-align="center"
        align="center"
        label="状态">
        <template slot-scope="scope">
          <span v-if="scope.row.settleStatus===0">未结算</span>
          <span v-if="scope.row.settleStatus===1">已结算</span>
        </template>

      </el-table-column>

      <el-table-column
        prop="createTime"
        header-align="center"
        align="center"
        label="创建时间">
      </el-table-column>
      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="100"
        label="操作">
        <template slot-scope="scope">
          <el-button type="text" size="small" v-if="scope.row.status===0" @click="cancleOrder(scope.row.id)">取消订单</el-button>
          <el-button type="text" size="small" v-if="scope.row.status===1" @click="submitOrder(scope.row.id)">完成订单</el-button>
          <!--<el-button type="text" size="small" @click="addOrUpdateHandle(scope.row.id)">修改</el-button>-->
          <!--<el-button type="text" size="small" @click="deleteHandle(scope.row.id)">删除</el-button>-->
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

<script>
  import AddOrUpdate from './buytrans-add-or-update'
  export default {
    data () {
      return {
        imgVisible: false,
        defaultImg: '',
        dataForm: {
          startDate:'',
          endDate:'',
          sellerId: '',
          buyerId:''
        },
        dataList: [],
        pageIndex: 0,
        pageSize: 10,
        totalPage: 0,
        dataListLoading: false,
        dataListSelections: [],
        addOrUpdateVisible: false
      }
    },
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
          url: this.$http.adornUrl('/app/buytrans/list'),
          method: 'get',
          params: this.$http.adornParams({
            'page': this.pageIndex,
            'limit': this.pageSize,
            'startDate': this.dataForm.startDate,
            'endDate': this.dataForm.endDate,
            'sellerId': this.dataForm.sellerId,
            'buyerId': this.dataForm.buyerId,

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
      // 新增 / 修改
      addOrUpdateHandle (id) {
        this.addOrUpdateVisible = true
        this.$nextTick(() => {
          this.$refs.addOrUpdate.init(id)
        })
      },
      cancleOrder (id) {

        this.$confirm('确定取消'+id+'订单？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
          url: this.$http.adornUrl('/app/buytrans/cancle'),
          method: 'post',
          data: this.$http.adornData(id, false)
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
      submitOrder (id) {

        this.$confirm('确定完成'+id+'订单？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
          url: this.$http.adornUrl('/app/buytrans/confirm'),
          method: 'post',
          data: this.$http.adornData(id, false)
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
            url: this.$http.adornUrl('/app/buytrans/delete'),
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

<style>
  .table-expand{

  }
</style>
