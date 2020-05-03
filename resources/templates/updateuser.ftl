<#import "common/bootstrap.ftl" as b>

<@b.page>
    <form method="post" action="/updateuser" class="form-horizontal container">
     <input type="hidden" name="date" value="${date?c}" />
     <input type="hidden" name="code" value="${code}" />
     <input type="hidden" name="action" value="update" />
     <input type="hidden" name="id" value="${id}" />
      <div class="form-group">
        <label for="userName" class="col-sm-2 control-label">UserName</label>
        <div class="col-sm-4">
          <input type="name" name="userName" class="form-control" placeholder="username" value=${updateUser.userId} required>
        </div>
      </div>
      <div class="form-group">
        <label for="firstName" class="col-sm-2 control-label">First Name</label>
        <div class="col-sm-4">
          <input type="name" name="firstName" class="form-control" placeholder="first name" value=${updateUser.firstName} required>
        </div>
      </div>
      <div class="form-group">
        <label for="lastName" class="col-sm-2 control-label">Last Name</label>
        <div class="col-sm-4">
          <input type="name" name="lastName" class="form-control" placeholder="last name" value=${updateUser.lastName} required>
        </div>
      </div>
      <div class="form-group">
        <label for="emailAddress" class="col-sm-2 control-label">Email Address</label>
        <div class="col-sm-4">
          <input type="name" name="emailAddress" class="form-control" placeholder="email address" value=${updateUser.emailAddress} required>
        </div>
      </div>
      <div class="form-group">
        <label for="orgName" class="col-sm-2 control-label">Organization/Company Name</label>
        <div class="col-sm-4">
          <input type="text" name="orgName" class="form-control" placeholder="Organization/Company Name" value=${updateUser.orgName} required>
        </div>
      </div>
      <div class="form-group">
        <label for="orgAddress" class="col-sm-2 control-label">Organization/Company Address</label>
        <div class="col-sm-4">
          <input type="text" name="orgAddress" class="form-control" id="orgAddress" placeholder="organization/company address" value=${updateUser.orgAddress} required>
        </div>
      </div>
      <div class="form-group">
        <label for="password" class="col-sm-2 control-label">PasswordHash</label>
        <div class="col-sm-4">
          <input type="text" name="passwordHash" class="form-control" id="passwordHash" placeholder="passwordHash" value=${updateUser.passwordHash} required>
        </div>
      </div>
      <div class="form-group">
        <div class="col-sm-offset-2 col-sm-4">
          <button type="submit" class="btn btn-primary btn-lg btn-block">Update</button>
        </div>
      </div>
    </form>
</@b.page>