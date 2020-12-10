<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">

        <el-form-item>
          <el-form-item label="是否实名" prop="isAward">
            <el-select v-model="dataForm.isReal">
              <el-option
                v-for="item in awardList"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              >
              </el-option>
            </el-select>
          </el-form-item>

      <el-date-picker
        v-model="dataForm.startDate"
        type="datetime"
        format="yyyy-MM-dd"
        value-format="yyyy-MM-dd"
        placeholder="注册时间范围">
      </el-date-picker>
     >= -- >
      <el-date-picker
        v-model="dataForm.endDate"
        type="datetime"
        format="yyyy-MM-dd"
        value-format="yyyy-MM-dd"
        placeholder="注册时间范围">
      </el-date-picker>

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
        prop="reportCount"
        header-align="center"
        align="center"
        label="数量">
      </el-table-column>




    </el-table>

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
          endDate:'',
          isAward:'0'

        },
        asUser:0,
        awardList: [
          { id: '0',
            name: '未实名'},

          { id: '2',
            name: '已实名'},
          { id: '',
            name: '不指定'}
        ],
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
          url: this.$http.adornUrl('/app/user/reportlist'),
          method: 'get',
          params: this.$http.adornParams({
            'realStatus':this.dataForm.isReal,
            'startDate': this.dataForm.startDate,
            'endDate': this.dataForm.endDate
          })
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.dataList = data.list

        } else {
          this.dataList = []

        }
        this.dataListLoading = false
      })
      }
    }
  }
</script>
