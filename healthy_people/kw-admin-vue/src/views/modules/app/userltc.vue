<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input ref="search" v-model="dataForm.name" placeholder="名称" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-select v-model="dataForm.ltcStatus" clearable placeholder="矿机状态">
          <el-option label="启动中" :value="1"></el-option>
          <el-option label="待启动" :value="0"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-select v-model="dataForm.settleStatus" clearable placeholder="采集状态">
          <el-option label="已采集" :value="1"></el-option>
          <el-option label="未采集" :value="0"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList()">查询</el-button>
        <el-button v-if="isAuth('app:userltc:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
        <el-button v-if="isAuth('app:userltc:delete')" type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button>
      </el-form-item>
    </el-form>
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
        prop="id"
        header-align="center"
        align="center"
        label="矿机id">
      </el-table-column>
      <el-table-column
        prop="name"
        header-align="center"
        align="center"
        label="矿机名称">
      </el-table-column>

      <el-table-column
        prop="parentPrice"
        header-align="center"
        align="center"
        label="OPC数量">
      </el-table-column>
      <el-table-column
        prop="incomeParentPerday"
        header-align="center"
        align="center"
        label="OPC每日收益">
      </el-table-column>
      <el-table-column
        prop="days"
        header-align="center"
        align="center"
        label="日期">
      </el-table-column>
      <el-table-column
        prop="maxHold"
        header-align="center"
        align="center"
        label="最大持有量">
      </el-table-column>
      <el-table-column
        prop="incomeSonPerday"
        header-align="center"
        align="center"
        label="MAC每日收益">
      </el-table-column>
      <el-table-column
        prop="power"
        header-align="center"
        align="center"
        label="动力值 ">
      </el-table-column>
      <el-table-column
        prop="ltcStatus"
        header-align="center"
        align="center"
        label="矿机状态">
        <template slot-scope="scope">
          <!--0：待启动，1：启动中-->
          {{scope.row.ltcStatus | ltcStatus}}

        </template>
      </el-table-column>
      <el-table-column
        prop="settleStatus"
        header-align="center"
        align="center"
        label="OPC采集状态 (需要用户采集动作)">
        <template slot-scope="scope">
          <!--0：未采集，1：已采集-->
          {{scope.row.settleStatus | settleStatus}}
        </template>
      </el-table-column>
      <el-table-column
        prop="ltcId"
        header-align="center"
        align="center"
        label="矿机配置id">
      </el-table-column>
      <el-table-column
        prop="createTime"
        header-align="center"
        align="center"
        label="创建时间">
      </el-table-column>
      <el-table-column
        prop="startTime"
        header-align="center"
        align="center"
        label="启动时间">
      </el-table-column>
      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="150"
        label="操作">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="addOrUpdateHandle(scope.row.id)">修改</el-button>
          <el-button type="text" size="small" @click="deleteHandle(scope.row.id)">删除</el-button>
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
  import AddOrUpdate from './userltc-add-or-update'
  import filters from '@/utils/filters'
  export default {
    data () {
      return {
        dataForm: {
          name: '',
          settleStatus: undefined,
          ltcStatus: undefined
        },
        dataList: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        dataListLoading: false,
        dataListSelections: [],
        addOrUpdateVisible: false
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
      // 获取数据列表
      getDataList () {
        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl('/app/userltc/list'),
          method: 'get',
          params: this.$http.adornParams({
            'page': this.pageIndex,
            'limit': this.pageSize,
            'name': this.dataForm.name,
            'settleStatus': this.dataForm.settleStatus,
            'ltcStatus': this.dataForm.ltcStatus
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
            url: this.$http.adornUrl('/app/userltc/delete'),
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
