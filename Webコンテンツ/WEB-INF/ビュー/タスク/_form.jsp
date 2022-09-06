<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<label for="content_tsk">タスク名</label>
<br />
<input type="text" name="content" id="content_tsk"
	値= "${tasks.content}"  />
<br />
<br />

<input type="hidden" name="_token" value="${_token}" />
<button type="submit">追加</button>
