<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">




      <el-date-picker
        v-model="dataForm.startDate"
        type="datetime"
        format="yyyy-MM-dd"
        value-format="yyyy-MM-dd"
        placeholder="收入时间范围">
      </el-date-picker>
      --
      <el-date-picker
        v-model="dataForm.endDate"
        type="datetime"
        format="yyyy-MM-dd"
        value-format="yyyy-MM-dd"
        placeholder="收入时间范围">
      </el-date-picker>

        <el-form-item label="根据用户汇总" prop="asUser">
          <el-select v-model="dataForm.asUser">
            <el-option
              v-for="item in userList"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            >
            </el-option>
          </el-select>
          <!--<el-input v-model="dataForm.vipId" placeholder="vip_id"></el-input>-->
        </el-form-item>




      <el-form-item>
        <el-button @click="getDataList()">查询</el-button>
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
        v-if="dataForm.asUser===1"
        prop="userId"
        header-align="center"
        align="center"
        label="用户id">
      </el-table-column>

      <el-table-column
        prop="income"
        header-align="center"
        align="center"
        label="数量">
      </el-table-column>




    </el-table>
    <div class="el-table el-table--fit el-table--border el-table--enable-row-hover el-table--medium">
    <table>
    <tr class="el-table__row">
      <td rowspan="1" colspan="1" class="el-table_4_column_10 is-center el-table-column--selection">

        <div class="cell">总量</div></td>
      <td style="border-color: white">
        <div class="cell">{{this.sum}}</div>
      </td>
    </tr>
    </table>
    </div>
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

 import filters from '@/utils/filters'
  export default {
    data () {
      return {
        dataForm:{
          startDate: '',
          endDate:''

        },
        asUser:0,
        userList: [
          { id: 0,
            name: '否'},
          { id: 1,
            name: '是'}
        ],
        sum:0,
        dataList: [],
        dataListLoading: false,
        dataListSelections: [],
      }
    },
    filters: filters,
    methods: {
      // 获取数据列表
      getDataList () {
        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl('/app/ltclog/reportlist'),
          method: 'get',
          params: this.$http.adornParams({
            'key': this.dataForm.key,
            'startDate': this.dataForm.startDate,
            'endDate': this.dataForm.endDate,
            'asUser': this.dataForm.asUser,
          })
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.dataList = data.list
            this.sum = data.sum
        } else {
          this.dataList = []

        }
        this.dataListLoading = false
      })
      }
    }
  }
</script>
