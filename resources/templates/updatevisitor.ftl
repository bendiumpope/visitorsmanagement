<#import "common/bootstrap.ftl" as b>

<@b.page>
<form method="post" action="/updatevisitor" class="form-horizontal">
  <input type="hidden" name="date" value="${date?c}" />
  <input type="hidden" name="code" value="${code}" />
  <input type="hidden" name="action" value="update" />
  <input type="hidden" name="id" value="${id}" />
  <div class="form-group">
    <label for="staffName" class="col-sm-2 control-label">Staff Name</label>
    <div class="col-sm-4">
      <input type="name" name="staffName" class="form-control" id="staffName" placeholder="who do you want to visit"  value="${visitor.staffName}" />
    </div>
  </div>
  <div class="form-group">
    <label for="faculty" class="col-sm-2 control-label">Faculty</label>
    <div class="col-sm-4">
      <input type="text" name="faculty" class="form-control" id="faculty" placeholder="faculty" value="${visitor.faculty}" />
    </div>
  </div>
  <div class="form-group">
      <label for="dept" class="col-sm-2 control-label">Department</label>
      <div class="col-sm-4">
        <input type="text" name="dept" class="form-control" id="dept" placeholder="faculty" value="${visitor.dept}" />
      </div>
  </div>
  <div class="form-group">
      <label for="visitReason" class="col-sm-2 control-label">Reason of Visit</label>
      <div class="col-sm-4">
        <input type="text" name="visitReason" class="form-control" id="visitReason" placeholder="Reason of visit" value="${visitor.visitReason}" />
      </div>
   </div>
  <div class="form-group">
    <div class="col-sm-offset-2 col-sm-4">
      <button type="submit" value="Submit" class="btn btn-primary btn-lg btn-block">Update</button>
    </div>
  </div>
</form>
</@b.page>