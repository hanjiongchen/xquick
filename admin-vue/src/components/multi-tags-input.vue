<template>
    <div>
        <el-tag class="multi-tags" :key="item" v-for="item in keywords" closable :disable-transitions="false" @close="keywords.splice(keywords.indexOf(item), 1)">{{ item }}</el-tag>
        <el-input class="input-new-tag" v-if="tagInputVisible" v-model="tagInputValue" ref="tagInput" size="small" @keyup.enter.native="saveTagInputHandle" @blur="saveTagInputHandle"/>
        <el-button v-else class="button-new-tag" size="small" @click="showTagInput">+ 添加</el-button>
    </div>
</template>

<script>
    export default {
        name: "multi-tags-input"
    }

</script>

<style scoped>

</style>