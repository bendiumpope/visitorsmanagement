<#import "common/bootstrap.ftl" as b>

<@b.page>
    <form method="post" action="/signup" class="form-horizontal container">
      <input type="hidden" name="action" value="add">
      <#if error??>
        ${error}<br><br>
      </#if>
      <div class="form-group">
        <label for="userName" class="col-sm-2 control-label">UserName</label>
        <div class="col-sm-4">
          <input type="name" name="userName" class="form-control" placeholder="username" required>
        </div>
      </div>
      <div class="form-group">
        <label for="firstName" class="col-sm-2 control-label">First Name</label>
        <div class="col-sm-4">
          <input type="name" name="firstName" class="form-control" placeholder="first name" required>
        </div>
      </div>
      <div class="form-group">
        <label for="lastName" class="col-sm-2 control-label">Last Name</label>
        <div class="col-sm-4">
          <input type="name" name="lastName" class="form-control" placeholder="last name" required>
        </div>
      </div>
      <div class="form-group">
        <label for="emailAddress" class="col-sm-2 control-label">Email Address</label>
        <div class="col-sm-4">
          <input type="name" name="emailAddress" class="form-control" placeholder="email address" required>
        </div>
      </div>
      <div class="form-group">
        <label for="orgName" class="col-sm-2 control-label">Organization/Company Name</label>
        <div class="col-sm-4">
          <input type="text" name="orgName" class="form-control" placeholder="Organization/Company Name" required>
        </div>
      </div>
      <div class="form-group">
        <label for="orgAddress" class="col-sm-2 control-label">Organization/Company Address</label>
        <div class="col-sm-4">
          <input type="text" name="orgAddress" class="form-control" id="orgAddress" placeholder="organization/company address" required>
        </div>
      </div>
      <div class="form-group">
        <label for="password" class="col-sm-2 control-label">Password</label>
        <div class="col-sm-4">
          <input type="password" name="password" class="form-control" id="password" placeholder="password" required>
        </div>
      </div>
      <div class="form-group">
          <label for="confirmPassword" class="col-sm-2 control-label">Confirm Password</label>
          <div class="col-sm-4">
            <input type="password" name="confirmPassword" class="form-control" id="confirmPassword" placeholder="confirm password">
          </div>
      </div>
      <div class="form-group">
        <div class="col-sm-offset-2 col-sm-4">
          <button type="submit" class="btn btn-primary btn-lg btn-block">Signup</button>
        </div>
      </div>
    </form>
</@b.page>