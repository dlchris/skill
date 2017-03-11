<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/tag.jsp"%>
<html>
<head>
<title>xx平台</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<LINK rel="stylesheet" type="text/css" href="${baseurl}js/easyui/styles/default.css">
<%@ include file="/WEB-INF/jsp/common_css.jsp"%>
<%@ include file="/WEB-INF/jsp/common_js.jsp"%>
</head>
<body>
	<div style="margin: 20px 0;"></div>
	<div class="easyui-panel" title="密码修改" style="width: 400px">
		<div style="padding: 10px 60px 20px 60px">
			<form id="ff" class="easyui-form" method="post">
				<table cellpadding="5">
					<tr>
						<td>旧密码:</td>
						<td>
							<input class="easyui-textbox" type="password" name="oldPassword" data-options="required:true"/>
						</td>
					</tr>
					<tr>
						<td>新密码:</td>
						<td>
							<input id="newPassword" class="easyui-textbox" type="password" name="newPassword" data-options="required:true"/>
						</td>
					</tr>
					<tr>
						<td>新密码确认:</td>
						<td>
							<input id="newPassword2" class="easyui-textbox" type="password" name="newPassword2" data-options="required:true" validType="equals['#newPassword']"/>
						</td>
					</tr>
				</table>
				<input type="hidden" name="userId" value="${userId}">
			</form>
			<div style="text-align: center; padding: 5px">
				<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()">提交</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()">清空</a>
			</div>
		</div>
	</div>
	<script>
		function submitForm() {
			$('#ff').form('submit', {
				url:"${baseurl}user/updatepwd.action",
				onSubmit : function() {
					return $(this).form('enableValidation').form('validate');
				},
				success:function(data){
					if(data==1){
						$.messager.alert('信息','密码已更新');
					}else{
						$.messager.alert('信息','密码更新失败');
					}
					
			    }
			});
		}

		function clearForm() {
			$('#ff').form('clear');
		}

		$.extend($.fn.validatebox.defaults.rules, {
		    equals: {
				validator: function(value,param){
					return value == $(param[0]).val();
				},
				message: '密码不一致.'
		    }
		});
	</script>
</body>
</html>