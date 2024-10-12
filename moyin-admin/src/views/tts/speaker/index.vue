<template>
  <div class="app-container">
    <el-form
      :model="queryData"
      ref="queryForm"
      size="small"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="名称" prop="name">
        <el-input
          v-model="queryData.name"
          placeholder="请输入发音人名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="性别" prop="gender">
        <el-select
          v-model="queryData.gender"
          placeholder="请选择性别"
          clearable
          style="width: 100%"
        >
          <el-option
            v-for="dict in dict.type.sys_user_sex"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="风格调用名" prop="styleCallName" label-width="100px">
        <el-input
          v-model="queryData.styleCallName"
          placeholder="请输入风格调用名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="领域" prop="domainIdSet">
        <el-select
          v-model="queryData.domainIdSet"
          placeholder="请选择领域"
          clearable
          style="width: 100%"
          multiple
        >
          <el-option
            v-for="(item, index) in DomainNameList"
            :key="index"
            :label="item.name"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="语言" prop="languageIdSet">
        <el-select
          v-model="queryData.languageIdSet"
          placeholder="请选择语言"
          clearable
          style="width: 100%"
          multiple
        >
          <el-option
            v-for="(item, index) in LanguageNameList"
            :key="index"
            :label="item.name"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="情绪" prop="emotionIdSet">
        <el-select
          v-model="queryData.emotionIdSet"
          placeholder="请选择情绪"
          clearable
          multiple
          style="width: 100%"
        >
          <el-option
            v-for="(item, index) in EmotionNameList"
            :key="index"
            :label="item.name"
            :value="item.id"
          />
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
          v-hasPermi="['moyin:speaker:add']"
          >新增</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['moyin:speaker:edit']"
          >修改</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['moyin:speaker:remove']"
          >删除</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['moyin:speaker:export']"
          >导出</el-button
        >
      </el-col>
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="voiceList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />

      <el-table-column label="头像" align="center">
        <template v-slot="scope">
          <img
            :src="scope.row.headerImage"
            style="width: 50px; height: 50px; border-radius: 50%"
            alt=""
          />
        </template>
      </el-table-column>

      <el-table-column label="发音人名称" align="center" prop="name" />
      <el-table-column
        label="性别"
        align="center"
        prop="gender"
        :formatter="moyinSexFormat"
      />
      <el-table-column label="年龄" align="center" prop="age" />

      <el-table-column label="行为" align="center" prop="behavior" />
      <!-- <el-table-column
        label="风格调用名"
        align="center"
        prop="styleCallName"
        width="180"
      /> -->
      <el-table-column label="状态" align="center" prop="status">
        <template #default="scope">
          <el-tag type="success" v-if="scope.row.status == 1">启用</el-tag>
          <el-tag type="danger" v-else="scope.row.status">禁用</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="领域" align="center" prop="domain" width="200" />
      <el-table-column label="语言" align="center" prop="language" />
      <el-table-column label="情绪" align="center" prop="emotion" width="200" />

      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
        fixed="right"
        min-width="120"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['moyin:speaker:edit']"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            style="color: #f56c6c"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['moyin:speaker:remove']"
            >删除</el-button
          >
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

    <!-- 添加或修改配音员对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="发音人名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入发音人名称" />
        </el-form-item>

        <el-form-item label="性别" prop="sex">
          <el-select
            v-model="form.gender"
            placeholder="请选择性别"
            clearable
            style="width: 100%"
          >
            <el-option
              v-for="dict in dict.type.sys_user_sex"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>

        <el-form-item label="年龄" prop="age">
          <el-input v-model="form.age" placeholder="请输入年龄" />
        </el-form-item>

        <el-form-item label="头像" prop="headerImage">
          <el-input v-model="form.headerImage" placeholder="请输入头像" />
        </el-form-item>

        <el-form-item label="风格调用名" prop="styleCallName">
          <el-input
            v-model="form.styleCallName"
            placeholder="请输入风格调用名"
          />
        </el-form-item>

        <el-form-item label="行为" prop="behavior">
          <el-input v-model="form.behavior" placeholder="请输入行为" />
        </el-form-item>

        <el-form-item label="状态" prop="status">
          <el-select
            v-model="form.status"
            placeholder="请选择状态"
            style="width: 100%"
          >
            <el-option label="启用" :value="1" />
            <el-option label="禁用" :value="0" />
          </el-select>
        </el-form-item>

        <el-form-item label="领域" prop="domainIdSet">
          <el-select
            v-model="form.domainIdSet"
            placeholder="请选择领域"
            clearable
            style="width: 100%"
            multiple
          >
            <el-option
              v-for="(item, index) in DomainNameList"
              :key="index"
              :label="item.name"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="语言" prop="languageIdSet">
          <el-select
            v-model="form.languageIdSet"
            placeholder="请选择语言"
            clearable
            style="width: 100%"
            multiple
          >
            <el-option
              v-for="(item, index) in LanguageNameList"
              :key="index"
              :label="item.name"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="情绪" prop="emotionIdSet">
          <el-select
            v-model="form.emotionIdSet"
            placeholder="请选择情绪"
            clearable
            multiple
            style="width: 100%"
          >
            <el-option
              v-for="(item, index) in EmotionNameList"
              :key="index"
              :label="item.name"
              :value="item.id"
            />
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
  listSpeaker,
  getSpeaker,
  delSpeaker,
  addSpeaker,
  updateSpeaker,
} from "@/api/tts/speaker";
import {
  emotionNameList,
  domainNameList,
  languageNameList,
} from "@/api/tts/dict";

export default {
  name: "Speaker",
  dicts: [
    "moyin_domain",
    "moyin_language",
    "moyin_category",
    "moyin_emotion",
    "sys_user_sex",
  ],
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
      // 配音员表格数据
      voiceList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
      },
      queryData: {
        name: null,
        gender: null,
        styleCallName: null,
        domainIdSet: null,
        languageIdSet: null,
        emotionIdSet: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},

      EmotionNameList: [],
      DomainNameList: [],
      LanguageNameList: [],
    };
  },
  created() {
    this.getList();
    this.getEmotionNameList();
    this.getLanguageNameList();
    this.getDomainNameList();
  },
  methods: {
    getEmotionNameList() {
      emotionNameList().then((res) => {
        this.EmotionNameList = res.rows;
      });
    },
    getLanguageNameList() {
      languageNameList().then((res) => {
        this.LanguageNameList = res.rows;
      });
    },
    getDomainNameList() {
      domainNameList().then((res) => {
        this.DomainNameList = res.rows;
      });
    },
    moyinSexFormat(row) {
      return this.selectDictLabel(this.dict.type.sys_user_sex, row.gender);
    },
    moyinDomainFormat(row) {
      return this.selectDictLabel(this.dict.type.moyin_domain, row.domain);
    },
    moyinLanguageFormat(row) {
      return this.selectDictLabel(
        this.dict.type.moyin_category,
        row.categoryId
      );
    },
    moyinCategoryFormat(row) {
      return this.selectDictLabel(
        this.dict.type.moyin_category,
        row.categoryId
      );
    },
    moyinEmotionFormat(row) {
      return this.selectDictLabel(this.dict.type.moyin_emotion, row.emotionId);
    },
    /** 查询配音员列表 */
    getList() {
      this.loading = true;
      console.log("查询参数", this.queryParams, this.queryData);
      listSpeaker(this.queryParams, this.queryData).then((response) => {
        this.voiceList = response.rows;
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
        sex: null,
        age: null,
        styleCallName: null,
        domain: null,
        style: null,
        language: null,
        price: null,
        notes: null,
        categoryId: null,
        emotionId: null,
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
      this.title = "添加配音员";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getSpeaker(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改配音员";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          console.log("表单", this.form);
          if (this.form.id != null) {
            updateSpeaker(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addSpeaker(this.form).then((response) => {
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
        .confirm('是否确认删除配音员编号为"' + ids + '"的数据项？')
        .then(function () {
          return delSpeaker(ids);
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
        "tts/Speaker/export",
        {
          ...this.queryParams,
        },
        `Speaker_${new Date().getTime()}.xlsx`
      );
    },
  },
};
</script>
