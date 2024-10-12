<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      size="small"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="敏感词" prop="word">
        <el-input
          v-model="queryParams.word"
          placeholder="请输入敏感词"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="词库" prop="lexiconId">
        <el-select
          v-model="queryParams.lexiconId"
          placeholder="请选择词库"
          clearable
          @keyup.enter.native="handleQuery"
        >
          <el-option
            :value="item.id"
            :label="item.name"
            v-for="(item, index) in LexiconNameList"
            :key="index"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button
          type="primary"
          icon="el-icon-search"
          size="mini"
          @click="handleQuery"
        >
          搜索
        </el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
          >重置</el-button
        >
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['moyin:sensitive:add']"
        >
          新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['moyin:sensitive:edit']"
        >
          修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['moyin:sensitive:remove']"
        >
          删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['moyin:sensitive:export']"
        >
          导出
        </el-button>
      </el-col>
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>
    <el-table
      v-loading="loading"
      :data="SensitiveList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" prop="id" />
      <el-table-column label="敏感词" align="center" prop="word" />
      <el-table-column label="词库" align="center" prop="lexiconName" />
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['moyin:sensitive:edit']"
            >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['moyin:sensitive:remove']"
            class="text-danger"
            >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :pSensitive.sync="queryParams.pSensitiveNum"
      :limit.sync="queryParams.pSensitiveSize"
      @pagination="getList"
    />

    <!-- 添加或修改敏感词敏感词对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="敏感词" prop="word">
          <el-input v-model="form.word" placeholder="请输入敏感词名称" />
        </el-form-item>
        <el-form-item label="词库" prop="lexiconId">
          <el-select
            v-model="form.lexiconId"
            placeholder="请选择词库"
            style="width: 100%"
          >
            <el-option
              :value="item.id"
              :label="item.name"
              v-for="(item, index) in LexiconNameList"
              :key="index"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listSensitive,
  getSensitive,
  delSensitive,
  addSensitive,
  updateSensitive,
} from "@/api/tts/sensitive";
import { lexiconNameList } from "@/api/tts/dict";

export default {
  name: "Sensitive",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 敏感词敏感词表格数据
      SensitiveList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pSensitiveNum: 1,
        pSensitiveSize: 10,
        word: null,
        lexiconId: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},

      //
      LexiconNameList: [],
    };
  },
  created() {
    this.getList();
    this.getLexiconNameList();
  },
  methods: {
    /** 查询敏感词敏感词列表 */
    getList() {
      this.loading = true;
      listSensitive(this.queryParams).then((response) => {
        this.SensitiveList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    getLexiconNameList() {
      lexiconNameList().then((res) => {
        this.LexiconNameList = res.rows;
        console.log(this.LexiconNameList);
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        name: null,
        status: null,
        createTime: null,
        updateTime: null,
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pSensitiveNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加敏感词敏感词";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getSensitive(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改敏感词敏感词";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateSensitive(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addSensitive(this.form).then((response) => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal
        .confirm('是否确认删除敏感词敏感词编号为"' + ids + '"的数据项？')
        .then(function () {
          return delSensitive(ids);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download(
        "tts/Sensitive/export",
        {
          ...this.queryParams,
        },
        `Sensitive_${new Date().getTime()}.xlsx`
      );
    },
  },
};
</script>
