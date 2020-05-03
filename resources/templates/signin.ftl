<#import "common/bootstrap.ftl" as b>


<@b.page>
<form method="post" action="/signin" class="form-horizontal container">
      <input type="hidden" name="action" value="add">
      <#if error??>
        ${error}<br><br>
      </#if>
      <div class="form-group">
        <label for="userName" class="col-sm-2 control-label">User Name</label>
        <div class="col-sm-4">
          <input type="name" name="userName" class="form-control" id="userName" placeholder="username" required>
        </div>
      </div>
      <div class="form-group">
        <label for="password" class="col-sm-2 control-label">Password</label>
        <div class="col-sm-4">
          <input type="password" name="password" class="form-control" id="password" placeholder="password" required>
        </div>
      </div>
        <div class="col-sm-offset-2 col-sm-4">
          <button type="submit" class="btn btn-primary btn-lg btn-block">Signin</button>
        </div>
    </form>
</@b.page>