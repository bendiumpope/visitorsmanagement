<#import "common/bootstrap.ftl" as b>

<@b.page>
<form method="post" action="/createvisitors" class="form-horizontal container">
  <input type="hidden" name="date" value="${date?c}" />
  <input type="hidden" name="code" value="${code}" />
  <input type="hidden" name="action" value="add">
  <div class="form-group">
    <label for="staffName" class="col-sm-2 control-label">Staff Name</label>
    <div class="col-sm-4">
      <input type="name" name="staffName" class="form-control" id="staffName" placeholder="who do you want to visit">
    </div>
  </div>
  <div class="form-group">
    <label for="faculty" class="col-sm-2 control-label">Faculty</label>
    <div class="col-sm-4">
      <input type="text" name="faculty" class="form-control" id="faculty" placeholder="faculty">
    </div>
  </div>
  <div class="form-group">
      <label for="dept" class="col-sm-2 control-label">Department</label>
      <div class="col-sm-4">
        <input type="text" name="dept" class="form-control" id="dept" placeholder="department">
      </div>
  </div>
  <div class="form-group">
      <label for="visitReason" class="col-sm-2 control-label">Reason of Visit</label>
      <div class="col-sm-4">
        <input type="text" name="visitReason" class="form-control" id="visitReason" placeholder="Reason of visit">
      </div>
   </div>
  <div class="form-group">
    <div class="col-sm-offset-2 col-sm-4">
      <button type="submit" value="Submit" class="btn btn-primary btn-lg btn-block">Submit</button>
    </div>
  </div>
</form>
</@b.page>