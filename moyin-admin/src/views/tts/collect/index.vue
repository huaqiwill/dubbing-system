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
      <el-form-item label="配音员" prop="speakerName">
        <el-input
          v-model="queryParams.speakerName"
          placeholder="请输入配音员名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="用户名" prop="userName">
        <el-input
          v-model="queryParams.userName"
          placeholder="请输入用户名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="备注" prop="speakerNotes">
        <el-input
          v-model="queryParams.speakerNotes"
          placeholder="请输入备注"
          clearable
          @keyup.enter.native="handleQuery"
        />
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
          v-hasPermi="['moyin:collect:add']"
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
          v-hasPermi="['moyin:collect:edit']"
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
          v-hasPermi="['moyin:collect:remove']"
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
          v-hasPermi="['moyin:collect:export']"
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
      :data="CollectList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" prop="id" />
      <el-table-column label="用户" align="center" prop="userName" />
      <el-table-column label="配音员" align="center" prop="speakerName" />
      <el-table-column label="备注" align="center" prop="speakerNotes" />
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
            v-hasPermi="['moyin:collect:edit']"
            >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            class="text-danger"
            @click="handleDelete(scope.row)"
            v-hasPermi="['moyin:collect:remove']"
            >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改配音员分类对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="用户" prop="name">
          <el-select
            v-model="form.userId"
            placeholder="请选择用户"
            style="width: 100%"
          >
            <el-option
              :value="item.id"
              :label="item.name"
              v-for="(item, index) in userNameList"
              :key="index"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="配音员" prop="speakerId">
          <el-select
            v-model="form.speakerId"
            placeholder="请选择配音员"
            style="width: 100%"
          >
            <el-option
              :value="item.id"
              :label="item.name"
              v-for="(item, index) in speakerNameList"
              :key="index"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="备注" prop="status">
          <el-input
            v-model="form.speakerNotes"
            placeholder="请输入备注（2-8位长度）"
          ></el-input>
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
  listCollect,
  getCollect,
  delCollect,
  addCollect,
  updateCollect,
} from "@/api/tts/collect";
import { userNameList, speakerNameList } from "@/api/tts/dict";

export default {
  name: "Collect",
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
      // 配音员分类表格数据
      CollectList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        speakerName: null,
        userName: null,
        speakerNotes: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},

      userNameList: [],
      speakerNameList: [],
    };
  },
  created() {
    this.getList();
    this.getUserNameList();
    this.getSpeakerNameList();
  },
  methods: {
    getUserNameList() {
      userNameList().then((res) => {
        this.userNameList = res.rows;
        console.log(this.userNameList);
      });
    },
    getSpeakerNameList() {
      speakerNameList().then((res) => {
        this.speakerNameList = res.rows;
        console.log(this.speakerNameList);
      });
    },
    /** 查询配音员分类列表 */
    getList() {
      this.loading = true;
      listCollect(this.queryParams).then((response) => {
        this.CollectList = response.rows;
        this.total = response.total;
        this.loading = false;
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
      this.queryParams.pageNum = 1;
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
      this.title = "添加配音员分类";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getCollect(id).then((response) => {
        this.form = response.data;
        console.log(this.form);
        this.open = true;
        this.title = "修改配音员分类";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateCollect(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addCollect(this.form).then((response) => {
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
        .confirm('是否确认删除配音员分类编号为"' + ids + '"的数据项？')
        .then(function () {
          return delCollect(ids);
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
        "tts/Collect/export",
        {
          ...this.queryParams,
        },
        `Collect_${new Date().getTime()}.xlsx`
      );
    },
  },
};
</script>
