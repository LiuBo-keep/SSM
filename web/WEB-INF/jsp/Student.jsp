<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="UTF-8">
    <title>学生列表</title>
    <link rel="stylesheet" type="text/css" href="../../easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="../../easyui/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="../../easyui/css/demo.css">
    <script type="text/javascript" src="../../easyui/jquery.min.js"></script>
    <script type="text/javascript" src="../../easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="../../easyui/js/validateExtends.js"></script>
    <script type="text/javascript">
        var clazzJsonArr =${clazzListJson};
        $(function () {
            var table;
            //datagrid初始化
            $('#dataList').datagrid({
                title: '学生列表',
                iconCls: 'icon-more',//图标
                border: true,
                collapsible: false,//是否可折叠的
                fit: true,//自动大小
                method: "post",
                url: "get_list?t=" + new Date().getTime(),
                idField: 'id',
                singleSelect: false,//是否单选
                pagination: true,//分页控件
                rownumbers: true,//行号
                sortName: 'id',
                sortOrder: 'DESC',
                remoteSort: false,
                columns: [[
                    {field: 'chk', checkbox: true, width: 50},
                    {field: 'id', title: 'ID', width: 50, sortable: true},
                    {field: 'username', title: '姓名', width: 150},
                    {field: 'sn', title: '学号', width: 150},
                    {field: 'sex', title: '性别', width: 150},
                    {
                        field: 'clazzId', title: '所属班级', width: 150, formatter: function (value, index, row) {
                            for (var i = 0; i < clazzJsonArr.length; i++) {
                                if (clazzJsonArr[i].id == value) {
                                    return clazzJsonArr[i].name;
                                }
                            }
                            return value;
                        }
                    },
                    {field: 'password', title: '密码', width: 150},

                ]],
                toolbar: "#toolbar"
            });
            //设置分页控件
            var p = $('#dataList').datagrid('getPager');
            $(p).pagination({
                pageSize: 10,//每页显示的记录条数，默认为10
                pageList: [10, 20, 30, 50, 100],//可以设置每页记录条数的列表
                beforePageText: '第',//页数文本框前显示的汉字
                afterPageText: '页    共 {pages} 页',
                displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',
            });
            //设置工具类按钮
            $("#add").click(function () {
                table = $("#addTable");
                $("#addDialog").dialog("open");
            });
            //修改
            $("#edit").click(function () {
                table = $("#editTable");
                var selectRows = $("#dataList").datagrid("getSelections");
                if (selectRows.length != 1) {
                    $.messager.alert("消息提醒", "请选择一条数据进行操作!", "warning");
                } else {
                    $("#editDialog").dialog("open");
                }
            });
            //删除
            $("#delete").click(function () {
                var selectRows = $("#dataList").datagrid("getSelections");
                var selectLength = selectRows.length;
                if (selectLength == 0) {
                    $.messager.alert("消息提醒", "请选择数据进行删除!", "warning");
                } else {
                    var ids = [];
                    $(selectRows).each(function (i, row) {
                        ids[i] = row.id;
                    });
                    $.messager.confirm("消息提醒", "将删除与用户相关的所有数据，确认继续？", function (r) {
                        if (r) {
                            $.ajax({
                                type: "post",
                                url: "delete",
                                data: {ids: ids},
                                dataType: 'json',
                                success: function (data) {
                                    if (data.type == "success") {
                                        $.messager.alert("消息提醒", "删除成功!", "info");
                                        //刷新表格
                                        $('#dataList').datagrid("reload");
                                        $("#dataList").datagrid("uncheckAll");
                                    } else {
                                        $.messager.alert("消息提醒", data.msg, "warning");
                                        return;
                                    }
                                }
                            });
                        }
                    });
                }
            });

            //设置添加窗口
            $("#addDialog").dialog({
                title: "学生班级",
                width: 450,
                height: 600,
                iconCls: "icon-add",
                modal: true,
                collapsible: false,
                minimizable: false,
                maximizable: false,
                draggable: true,
                closed: true,
                buttons: [
                    {
                        text: '添加',
                        plain: true,
                        iconCls: 'icon-user_add',
                        handler: function () {
                            var validate = $("#addForm").form("validate");
                            if (!validate) {
                                $.messager.alert("消息提醒", "请检查你输入的数据!", "warning");
                                return;
                            } else {
                                var data = $("#addForm").serialize();
                                $.ajax({
                                    type: "post",
                                    url: "add",
                                    data: data,
                                    dataType: 'json',
                                    success: function (data) {
                                        if (data.type == "success") {
                                            $.messager.alert("消息提醒", "添加成功!", "info");
                                            //关闭窗口
                                            $("#addDialog").dialog("close");
                                            //清空原表格数据
                                            $("#add_name").textbox('setValue', "");
                                            $("#add_remark").textbox('setValue', "");
                                            //重新刷新页面数据
                                            $('#dataList').datagrid("reload");

                                        } else {
                                            $.messager.alert("消息提醒", data.msg, "warning");
                                            return;
                                        }
                                    }
                                });
                            }
                        }
                    },
                ],
                onClose: function () {
                    $("#add_name").textbox('setValue', "");
                    $("#add_remark").textbox('setValue', "");
                }
            });


            //编辑用户信息
            $("#editDialog").dialog({
                title: "修改学生信息",
                width: 450,
                height: 600,
                iconCls: "icon-edit",
                modal: true,
                collapsible: false,
                minimizable: false,
                maximizable: false,
                draggable: true,
                closed: true,
                buttons: [
                    {
                        text: '提交',
                        plain: true,
                        iconCls: 'icon-edit',
                        handler: function () {
                            var validate = $("#editForm").form("validate");
                            if (!validate) {
                                $.messager.alert("消息提醒", "请检查你输入的数据!", "warning");
                                return;
                            } else {
                                var data = $("#editForm").serialize();

                                $.ajax({
                                    type: "post",
                                    url: "edit",
                                    data: data,
                                    dataType: 'json',
                                    success: function (data) {
                                        if (data.type == "success") {
                                            $.messager.alert("消息提醒", "修改成功!", "info");
                                            //关闭窗口
                                            $("#editDialog").dialog("close");
                                            //重新刷新页面数据
                                            $('#dataList').datagrid("reload");
                                            $('#dataList').datagrid("uncheckAll");

                                        } else {
                                            $.messager.alert("消息提醒", data.msg, "warning");
                                            return;
                                        }
                                    }
                                });
                            }
                        }
                    },
                ],
                onBeforeOpen: function () {
                    var selectRow = $("#dataList").datagrid("getSelected");
                    //设置值
                    $("#edit-id").val(selectRow.id);
                    $("#edit_name").textbox('setValue', selectRow.name);
                    $("#edit_gradeId").combobox('setValue', selectRow.gradeId);
                    $("#edit_remark").textbox('setValue', selectRow.remark);
                }
            });

            //初始化下拉框
            $("add_clazzId").combobox({
                width: "200",
                height: "30",
                valueField: "id",
                textField: "name",
                multiple: "false",
                editable: "false",
            });

            //搜索按钮
            $("#search_btn").click(function () {
                $("#dataList").datagrid('reload', {
                    name: $("#search-name").textbox('getValue'),
                    gradeId: $("#search-gradeId").combobox('getValue')
                });
            });

            //上传图片按钮
            $("#upload-btn").click(function () {
                if ($("#add-upload-photo").filebox("getValue")==''){
                    $.messager.alert("消息提醒","请选择图片文件","warning");
                    return;
                }
                $("#photoForm").submit();
            })
        });
        
        function uploaded(e) {
            console.log($(window.frames["photo_target"].document).find("body pre").text());
            var data=$(window.frames["photo_target"].document).find("body pre").text();
            console.log(typeof data);
            var jso=eval('(' + data + ')');
            console.log(jso.msg);
            if (jso.type == "success") {
                $.messager.alert("消息提醒", "图片上传成功!", "info");
                $("#photo-preview").attr("src",jso.src);
                $("#add_photo").val(jso.src);
                //关闭窗口
                $("#editDialog").dialog("close");

            } else {
                $.messager.alert("消息提醒", jso.msg, "warning");
                return;
            }
        }
    </script>
</head>
<body>
<!-- 数据列表 -->
<table id="dataList" cellspacing="0" cellpadding="0">

</table>
<!-- 工具栏 -->
<div id="toolbar">
    <div style="float: left;"><a id="add" href="javascript:;" class="easyui-linkbutton"
                                 data-options="iconCls:'icon-add',plain:true">添加</a></div>
    <div style="float: left;" class="datagrid-btn-separator"></div>
    <div style="float: left;"><a id="edit" href="javascript:;" class="easyui-linkbutton"
                                 data-options="iconCls:'icon-edit',plain:true">修改</a></div>
    <div style="float: left;" class="datagrid-btn-separator"></div>
    <div><a id="delete" href="javascript:;" class="easyui-linkbutton"
            data-options="iconCls:'icon-some-delete',plain:true">删除</a>
        班级名：<input id="search-name" class="easyui-textbox" name="name"/>
        搜索年级：<select id="search-gradeId" class="easyui-combobox" name="gradeId" style="width: 150px">
            <option value="">全部</option>
            <c:forEach items="${gradeList}" var="grade">
                <option value="${grade.id}">${grade.name}</option>
            </c:forEach>
        </select>
        <a id="search_btn" href="javascript:;" class="easyui-linkbutton"
           data-options="iconCls:'icon-search',plain:true">搜索</a>
    </div>
</div>

<!-- 添加窗口 -->
<div id="addDialog" style="padding: 10px;">
    <form id="photoForm" method="post" enctype="multipart/form-data" action="upload_photo" target="photo_target">
        <table id="addTable1" cellpadding="8">
            <tr >
                <td>预览头像:</td>
                <td>
                    <img id="photo-preview" alt="照片" style="max-width: 100px; max-height: 100px;" title="照片" src="/photo/0227aa0c322e4515a02fefe8285bbca3.jpg" />
                </td>
            </tr>
            <tr >
                <td>学生头像:</td>
                <td>
                    <input id="add-upload-photo" class="easyui-filebox" name="photo" data-options="prompt:'选择照片'" style="width:200px;">
                    <a id="upload-btn" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">上传图片</a>
                </td>
            </tr>
        </table>
    </form>
    <form id="addForm" method="post">
        <table id="addTable2" cellpadding="8">
            <input id="add_photo" type="hidden" name="photo" value="/photo/0227aa0c322e4515a02fefe8285bbca3.jpg"  />
            <tr >
                <td>学生姓名:</td>
                <td>
                    <input id="add_username"  class="easyui-textbox" style="width: 200px; height: 30px;" type="text" name="username" data-options="required:true, missingMessage:'请填写学生姓名'"  />
                </td>
            </tr>
            <tr >
                <td>登录密码:</td>
                <td>
                    <input id="add_password"  class="easyui-textbox" style="width: 200px; height: 30px;" type="password" name="password" data-options="required:true, missingMessage:'请填写登录密码'"  />
                </td>
            </tr>
            <tr >
                <td>所属班级:</td>
                <td>
                    <select id="add_clazzId"  class="easyui-combobox" style="width: 200px;" name="clazzId" data-options="required:true, missingMessage:'请选择所属班级'">
                        <c:forEach items="${ clazzList}" var="clazz">
                            <option value="${clazz.id }">${clazz.name }</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr >
                <td>学生性别:</td>
                <td>
                    <select id="add_sex"  class="easyui-combobox" style="width: 200px;" name="sex" data-options="required:true, missingMessage:'请选择学生性别'">
                        <option value="男">男</option>
                        <option value="女">女</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>备注:</td>
                <td><input id="add_remark" style="width: 256px; height: 180px;" class="easyui-textbox" type="text" name="remark" data-options="multiline:true"  /></td>
            </tr>
        </table>
    </form>
</div>

<!-- 修改窗口 -->
<div id="editDialog" style="padding: 10px">
    <form id="editForm" method="post">
        <input type="hidden" name="id" id="edit-id">
        <table id="editTable" border=0 cellpadding="8">
            <tr>
                <td>学生名:</td>
                <td>
                    <input id="edit_name" class="easyui-textbox" style="width: 200px; height: 30px;" type="text"
                           name="name" data-options="required:true, validType:'repeat', missingMessage:'请填写年级名'"/>
                </td>
            </tr>
            <tr>
                <td>所属年级:</td>
                <td>
                    <select id="edit_gradeId" class="easyui-combobox" style="width: 200px;" name="gradeId"
                            data-options="required:true, missingMessage:'请选择所属年级'">
                        <c:forEach items="${gradeList}" var="grade">
                            <option value="${grade.id}">${grade.name}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td>备注:</td>
                <td><input id="edit_remark" style="width: 256px; height: 180px;" class="easyui-textbox" type="text"
                           name="remark" data-options="multiline:true"/></td>
            </tr>
        </table>
    </form>
</div>

<!-- 提交表单处理iframe框架 -->
<iframe id="photo_target" name="photo_target" onload="uploaded(this)"></iframe>
</body>
</html>